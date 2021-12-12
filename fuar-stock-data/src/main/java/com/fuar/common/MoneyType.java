package com.fuar.common;

public enum MoneyType {
    USD("Dolar", "$"),
    EUR("Euro", "E"),
    TL("Türk Lirasi", "T");

    private String label;   // Dollar
    private String symbol;  // $

    MoneyType(String label, String symbol) {
        this.label = label;
        this.symbol = symbol;
    }
}
