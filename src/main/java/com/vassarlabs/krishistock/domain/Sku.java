package com.vassarlabs.krishistock.domain;

public record Sku(
        String code,
        Product product,
        int unitKg
) {
}
