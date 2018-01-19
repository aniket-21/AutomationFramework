package com.app.enums.xe;

/**
 * Created by I337111 on 23/2/2017.
 */
public enum XeConfigKeys {

    MLP_MODEL_BASE_URL ("mlp_model_base_url"),
    MLP_TRAINING_BASE_URL ("mlp_training_base_url"),
    MLP_UAA_BASE_URL ("mlp_uaa_base_url"),
    MLP_SERVICE_BROKER_BASE_URL("mlp_service_broker_base_url");

    private final String desc;
    XeConfigKeys(String s) {
        desc = s;
    }
    public String toString() {
        return this.desc;
    }

}
