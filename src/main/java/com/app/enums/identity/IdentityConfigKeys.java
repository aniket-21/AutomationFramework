package com.app.enums.identity;

/**
 * Created by I337111 on 23/2/2017.
 */
public enum IdentityConfigKeys {

    URL("url");

    private final String desc;
    IdentityConfigKeys(String s) {
        desc = s;
    }
    public String toString() {
        return this.desc;
    }

}
