package com.framework.handlers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.app.enums.xe.XeConfigKeys;
import com.app.enums.xe.XeDataKeys;

import java.io.*;

/**
 * Created by I337111 on 23/2/2017.
 */
public class DataHandler {

    String env;
    JsonParser parser = new JsonParser();

    public DataHandler() {
        //Set env
        this.env = getEnv();
    }

    private String getEnv() {
        String env = System.getProperty("envName").trim().toLowerCase();
        if(env.isEmpty()) {
            System.out.println("No environment set. Exiting...");
            System.exit(-1); //Create an Invalid Exception Class later
        }
        else if(!env.equals("dev") && !env.equals("integration") && !env.equals("staging") && !env.equals("production")) {
            System.out.println(env + " is not a valid environment. Exiting...");
            System.exit(-1); //Create an Invalid Exception Class later
        }

        return env;
    }

    private JsonObject getAppConfigJson()  {

        String app_config_path = "config/mlpconfig.json";
        try {
            File file = new File(System.getProperty("user.dir") + "/" + app_config_path);
            JsonObject jsonObject = (JsonObject) parser.parse(new FileReader(file));
            return jsonObject.getAsJsonObject("appconfig");
        }
        catch(FileNotFoundException ex) {
            System.out.println(app_config_path + " file not found");
            System.exit(-1);
        }
        catch(Exception ex) {
            System.out.println("Exception " + ex + " occurred while accessing config file");
            System.exit(-1);
        }
        return null;
    }

    private JsonObject getAppDataJson()  {
        String appdata_file_path = "data/staticdata.json";
        try {
            File file = new File(System.getProperty("user.dir") + "/" + appdata_file_path);
            JsonObject jsonObject = (JsonObject) parser.parse(new FileReader(file));
            return jsonObject.getAsJsonObject("appdata");
        }
        catch(FileNotFoundException ex) {
            System.out.println(appdata_file_path + " file not found");
            System.exit(-1);
        }
        catch(Exception ex) {
            System.out.println("Exception " + ex + " occurred while accessing config file");
            System.exit(-1);
        }
        return null;
    }

    public JsonElement getAppConfig(XeConfigKeys configKey) {
        try {
            JsonObject appConfig = getAppConfigJson();
            JsonObject envConfig = appConfig.getAsJsonObject("env").getAsJsonObject(env);
            return envConfig.get(configKey.toString());
        }
        catch (NullPointerException ex) {
            System.out.println("Exception " + ex + " occurred while fetching value for key " + configKey);
            throw ex;
        }
    }

    public JsonElement getAppData(XeDataKeys dataKey) {
        try {
            JsonObject appData = getAppDataJson();
            JsonObject envData = appData.getAsJsonObject("env").getAsJsonObject(env);
            return envData.get(dataKey.toString());
        }
        catch (NullPointerException ex) {
            System.out.println("Exception " + ex + " occurred while fetching value for key " + dataKey);
            throw ex;
        }
    }
}
