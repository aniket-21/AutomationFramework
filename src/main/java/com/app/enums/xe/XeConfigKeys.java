package com.app.enums.xe;

import com.app.enums.IConfigKeys;

/**
 * Created by I337111 on 23/2/2017.
 */
public enum XeConfigKeys implements IConfigKeys{

    PACKAGE_NAME ("packageName"),
    LAUNCHER_ACTIVITY ("launcherActivity");

    private final String desc;
    XeConfigKeys(String s) {
        desc = s;
    }
    public String toString() {
        return this.desc;
    }

}
