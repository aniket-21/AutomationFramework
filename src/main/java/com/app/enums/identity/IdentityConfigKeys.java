package com.app.enums.identity;

import com.app.enums.IConfigKeys;

/**
 * Created by I337111 on 23/2/2017.
 */
public enum IdentityConfigKeys implements IConfigKeys {

    URL("url");

    private final String desc;
    IdentityConfigKeys(String s) {
        desc = s;
    }
    public String toString() {
        return this.desc;
    }

}
