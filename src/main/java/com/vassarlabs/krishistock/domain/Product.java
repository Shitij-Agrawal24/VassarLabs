package com.vassarlabs.krishistock.domain;

public enum Product {
    PADDY_SEED(20),
    DAP(50),
    UREA(90),
    MOP(25);

    private final int kgPerAcre;

    Product(int kgPerAcre) {
        this.kgPerAcre = kgPerAcre;
    }

    public int kgPerAcre() {
        return kgPerAcre;
    }
}

