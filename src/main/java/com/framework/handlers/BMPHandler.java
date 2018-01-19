package com.framework.handlers;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.BlacklistEntry;
import org.openqa.selenium.Proxy;

import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by Aniket on 6/29/2015.
 */
public class BMPHandler {

    BrowserMobProxy proxy;

    String harFolder;

    public BMPHandler(String env, String classname, String browser) {
        String rootPath = System.getProperty("user.dir");
        this.harFolder = rootPath + "/execution/" + env + "/" + classname + "/" + browser.toUpperCase() + "_HTML_Reports/Hars";
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
            proxy.waitForQuiescence(2,10, TimeUnit.SECONDS);
            Har har = proxy.endHar();
            FileOutputStream fos = new FileOutputStream(harFolder + "/" + strTestName + ".har");
            har.writeTo(fos);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean isProxyRunning(){
        return proxy.isStarted();
    }

    public void whitelistRequests(Collection<String> patterns, int statusCode){
        proxy.whitelistRequests(patterns, statusCode);
    }

    public void setBlacklist(Collection<BlacklistEntry> patterns){
        proxy.setBlacklist(patterns);
    }

}
