package com.vassarlabs.krishistock.domain;

import java.util.Map;

public record Reservation(
        String reservationId,
        String farmerId,
        String depotId,
        Product product,
        int totalKg,
        Map<String, Integer> selectedBags
) {

}
