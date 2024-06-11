package com.qa.api.tests;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ValdateDisposeTest {
    Playwright playwright;
    APIRequestContext apiRequestContext;
    APIRequest apiRequest;
    @BeforeTest
    public void setUp()
    {
        playwright = Playwright.create();
        apiRequest=playwright.request();
        apiRequestContext=apiRequest.newContext();
    }
@Test
public void testDisposeMethod()
{
    //response 1
    APIResponse response = apiRequestContext.get("https://gorest.co.in/public/v2/users");
    Assert.assertEquals(response.statusText(),"OK");
    System.out.println("----data before reponse dispose-------------");
    System.out.println(response.text());
     //now using dispose method at response level

    response.dispose();
    //trying to check if after using dispose method , do we get response body trying to use try catch block
    System.out.println("----data after reponse dispose-------------");
    try
    {
        System.out.println(response.text());
    }catch (PlaywrightException e)
    {
        System.out.println("Playwright Exception as response has been dispose");
    }
    System.out.println( "though response body is disposed, but still we can get status code : "+ response.statusText());


    ///response 2

    APIResponse response2 = apiRequestContext.get("https://reqres.in/api/users/2");
    System.out.println(response2.text());

    System.out.println("----data after disponsing requestContext------------");
    apiRequestContext.dispose();
    try
    {
        System.out.println(response2.text());
    }catch (PlaywrightException e)
    {
        System.out.println("Playwright Exception as requestContext has been disposed");
    }
    try
    {
        System.out.println(response.text());
    }catch (PlaywrightException e)
    {
        System.out.println("Playwright Exception as Overall requestContext has been disposed");
    }
}
    @AfterTest
    public void tearDown()
    {
        playwright.close();
    }
}
