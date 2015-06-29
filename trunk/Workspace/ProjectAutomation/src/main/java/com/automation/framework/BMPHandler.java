package com.automation.framework;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import org.openqa.selenium.Proxy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Aniket on 6/29/2015.
 */
public class BMPHandler {

    BrowserMobProxy proxy;
    String harFolderPath;
    HashMap<String, String> Environment = new HashMap<String, String>();

    public BMPHandler(HashMap<String, String> GEnvironment){
        Environment = GEnvironment;
    }

    public Proxy startBrowserMobProxy() throws UnknownHostException {
        // start the proxy
        proxy = new BrowserMobProxyServer();
        proxy.start(9090, InetAddress.getLocalHost());
        return ClientUtil.createSeleniumProxy(proxy);
    }

    public void stopBrowserMobProxy(){
        //stop proxy
        proxy.stop();
    }

    public void createNewHarPage(String pageName){
        //createNewHarPage
        proxy.newPage(pageName);
    }

    public void createNewHar(String harName){
        //createNewHarPage
        proxy.newHar(harName);
    }

    public void generateHar(String strTestName) {
        //stop capturing
        try {
            proxy.waitForQuiescence(5,60, TimeUnit.SECONDS);
            Har har = proxy.endHar();
            FileOutputStream fos = new FileOutputStream(Environment.get("HARFOLDER") + "/" + strTestName + ".har");
            har.writeTo(fos);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean isProxyRunning(){
        return proxy.isStarted();
    }

}
