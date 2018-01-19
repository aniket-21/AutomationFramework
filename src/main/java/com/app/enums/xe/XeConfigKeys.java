package com.app.enums.xe;

/**
 * Created by I337111 on 23/2/2017.
 */
public enum XeConfigKeys {

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
