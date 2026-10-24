package com.vassarlabs.krishistock.domain;

public record Farmer(
        String id,
        double landholdingAcres
        ) {
        public double quota(Product product) {
                return landholdingAcres*product.kgPerAcre();
        }
}
