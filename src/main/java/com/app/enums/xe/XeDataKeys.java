package com.app.enums.xe;

import com.app.enums.IConfigKeys;

/**
 * Created by I337111 on 23/2/2017.
 */
public enum XeDataKeys implements IConfigKeys {

    CURRENCY_TO_ADD ("currencyToAdd");

    private final String desc;
    XeDataKeys(String s) {
        desc = s;
    }
    public String toString() {
        return this.desc;
    }
}
