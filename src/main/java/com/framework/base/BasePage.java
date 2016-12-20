package com.framework.base;

import com.framework.core.Reporting;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

/**
 * Created by I337111 on 20/12/2016.
 */
public class BasePage {

    protected BasePage() {

    }

    protected  <T> T getNewInstanceOfClass(Class<T> clazz, WebDriver driver, HashMap<String, String> dictionary, HashMap<String, String> environment, Reporting reporter) {
        if(this.getClass().equals(clazz)) {
            return (T)this;
        }

        try {
            return (T) clazz.getDeclaredConstructors()[0].newInstance(driver, dictionary, environment, reporter);
        }
        catch(Exception e) {
            return null;
        }

    }
}
