package com.qa.api.tests;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class ValdateDispose {
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

    @AfterTest
    public void tearDown()
    {
        playwright.close();
    }
}
