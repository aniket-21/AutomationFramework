package com.app.enums.xe;

/**
 * Created by I337111 on 23/2/2017.
 */
public enum XeDataKeys {

    CURRENCY_TO_ADD ("currencyToAdd");


    private final String desc;
    XeDataKeys(String s) {
        desc = s;
    }
    public String toString() {
        return this.desc;
    }
}
