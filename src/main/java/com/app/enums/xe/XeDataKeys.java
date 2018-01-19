package com.app.enums.xe;

/**
 * Created by I337111 on 23/2/2017.
 */
public enum XeDataKeys {

    CUSTOMER_NAME ("customer_name"),
    TRAINING_NAME ("training_name"),
    TRAINING_ID ("training_id"),
    NEW_TRAINING_ID ("new_training_id"),
    INVALID_TRAINING_ID("invalid_training_id"),
    ACTIVE_MODEL_ID ("active_model_id"),
    INVALID_MODEL_ID ("invalid_model_id"),
    ACTIVE_MODEL_METADATA ("active_model_metadata"),
    INACTIVE_MODEL_ID("inactive_model_id"),
    TRAINING_METADATA("training_metadata"),
    TENANT_NAME("tenant_name"),
    SERVICE_ID("service_id"),
    PLAN_ID("plan_id"),
    APP_GUID("app_guid"),
    ORG_GUID("org_guid"),
    SPACE_GUID("space_guid"),
    INSTANCE_ID("instance_id"),
    PERSISTENT_INSTANCE_ID("persistent_instance_id"),
    SERVICE_BINDING_ID("service_binding_id");


    private final String desc;
    XeDataKeys(String s) {
        desc = s;
    }
    public String toString() {
        return this.desc;
    }
}
