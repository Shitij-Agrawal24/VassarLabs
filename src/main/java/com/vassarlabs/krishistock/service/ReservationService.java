package com.vassarlabs.krishistock.service;

import com.vassarlabs.krishistock.domain.Farmer;
import com.vassarlabs.krishistock.domain.Product;
import com.vassarlabs.krishistock.domain.Reservation;
import com.vassarlabs.krishistock.domain.Sku;

import java.util.HashMap;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ReservationService {
    private final Map<String, Sku> skus = new HashMap<>();
    private final Map<String, Map<String, Integer>> stock = new HashMap<>();
    private final Map<String, Map<Product, Integer>> usedQuota = new HashMap<>();
    private final List<Reservation> reservations = new ArrayList<>();
    private final AtomicInteger id_gen = new AtomicInteger(1);

    public void addSku(Sku sku) {
        skus.put(sku.code(), sku);
    }

    public void addStock(String depotId, String skuCode, int bags) {
        if(stock.containsKey(depotId)) {
            Map<String, Integer> temp = stock.get(depotId);
            temp.put(skuCode, temp.getOrDefault(skuCode, 0)+bags);
        } else {
            Map<String, Integer> temp = new HashMap<>();
            temp.put(skuCode, bags);
            stock.put(depotId, temp);
        }
    }

    public Reservation reserve(
            Farmer farmer,
            String depotId,
            Product product
    ) {
        Map<Product, Integer> farmerQuota = usedQuota.computeIfAbsent(farmer.id(), k->new HashMap<>());

        int usedKg = farmerQuota.getOrDefault(product, 0);
        double quota = farmer.quota(product);
        double remainsQuota = quota - usedKg;

        if(remainsQuota<=0) {
            System.out.println("Quota Exhausted");
            return null;
        }

        Map<String, Integer> depotStock = stock.get(depotId);
        if(depotStock == null) {
            System.out.println("Depot not found");
            return null;
        }

        List<Sku> skusAvailable = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: depotStock.entrySet()) {
            Integer availableBags = entry.getValue();
            Sku sku = skus.get(entry.getKey());

            if(availableBags!=null && availableBags>0 && sku!=null && sku.product()==product) {
                skusAvailable.add(sku);
            }
        }
        if(skusAvailable.isEmpty()) {
            System.out.println("No Stock available.");
            return null;
        }

        skusAvailable.sort((a, b) -> {
            return Integer.compare(b.unitKg(), a.unitKg());
        });

        int smallestSkuKg = Integer.MAX_VALUE;
        for(Sku sku: skusAvailable) {
            if(sku.unitKg()<smallestSkuKg)
                smallestSkuKg = sku.unitKg();
        }

        int maxAllowedKg = (int)Math.ceil(remainsQuota) + smallestSkuKg - 1;

        Map<String, Integer> currentSelect = new HashMap<>();
        Map<String, Integer> bestSelect = new HashMap<>();

        int[] bestKg = {0};

        findBestCombination(skusAvailable, depotStock, 0, maxAllowedKg, remainsQuota,
                0, currentSelect, bestKg, bestSelect);

        if(bestKg[0]==0) {
            System.out.println("No Stock combination possible.");
            return null;
        }

        for(Map.Entry<String, Integer> entry: bestSelect.entrySet()) {
            String skuCode = entry.getKey();
            int selectBags = entry.getValue();
            int availableBags = depotStock.get(skuCode);
            depotStock.put(skuCode, availableBags-selectBags);
        }

        farmerQuota.put(product, usedKg+bestKg[0]);

        Reservation reservation = new Reservation("R"+id_gen.getAndIncrement(),
                farmer.id(),
                depotId,
                product,
                bestKg[0],
//                ReservationStatus.HELD,
                new HashMap<>(bestSelect));
        reservations.add(reservation);
        return reservation;



    }


    private void findBestCombination(
            List<Sku> skusAvailable,
            Map<String, Integer> depotStock,
            int index,
            int maxAllowedKg,
            double targetKg,
            int currentKgs,
            Map<String, Integer> currentSelect,
            int[] bestKg,
            Map<String, Integer> bestSelect
    ) {
        if(index == skusAvailable.size()) {
            if(currentKgs==0 || currentKgs>maxAllowedKg) {
                return;
            }

            double currentDiff = Math.abs(currentKgs-targetKg);
            double bestDiff = Math.abs(bestKg[0] - targetKg);

            if(bestKg[0]==0 || currentDiff<=bestDiff) {
                bestKg[0] = currentKgs;
                bestSelect.clear();
                bestSelect.putAll(currentSelect);
            }

            return;
        }

        Sku sku = skusAvailable.get(index);
        int availableBags = depotStock.get(sku.code());
        int remainingKg = maxAllowedKg - currentKgs;
        int maxBags = Math.min(availableBags, remainingKg/sku.unitKg());

        for(int qty=0; qty<=maxBags; qty++) {
            if(qty>0)
                currentSelect.put(sku.code(), qty);
            else
                currentSelect.remove(sku.code());

            findBestCombination(skusAvailable, depotStock, index+1, maxAllowedKg, targetKg,
                    currentKgs+qty* sku.unitKg(), currentSelect, bestKg, bestSelect);
        }

        currentSelect.remove(sku.code());
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

//    public List<Reservation> getstock(depotId, skuId) {
//        return stock.;
//    }
}

//@Transaction
//public void reserveStock() {
//    for(Map.Entry<String, Integer> entry: bestSelect.entrySet()) {
//        String skuCode = entry.getKey();
//        int selectBags = entry.getValue();
//        int availableBags = depotStock.get(skuCode);
//        depotStock.put(skuCode, availableBags-selectBags);
//    }
//}
//
//UPDATE
//..
//..
//..
//
//
//version = 2
//version = 3