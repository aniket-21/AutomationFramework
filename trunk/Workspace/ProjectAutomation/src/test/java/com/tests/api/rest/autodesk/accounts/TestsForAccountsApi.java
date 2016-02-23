package com.tests.api.rest.autodesk.accounts;

import org.testng.annotations.*;

import java.lang.reflect.Method;

import com.jayway.restassured.RestAssured.*;

/**
 * Created by gadrea on 2/23/2016.
 */


public class TestsForAccountsApi {

    @BeforeMethod
    public void setUp() throws Exception {

    }

    @AfterMethod
    public void tearDown() throws Exception {

    }
    @Test
    public void testGetUserApi(Method methodName){
        System.out.println("Executing test " + methodName.getName());
    }
}
