package com.app.enums.identity;

import com.app.enums.IConfigKeys;

/**
 * Created by I337111 on 23/2/2017.
 */
public enum IdentityDataKeys implements IConfigKeys {

    USERNAME("username"),
    PASSWORD("password");

    private final String desc;
    IdentityDataKeys(String s) {
        desc = s;
    }
    public String toString() {
        return this.desc;
    }
}
