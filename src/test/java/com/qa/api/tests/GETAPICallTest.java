package com.qa.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class GETAPICallTest {
    Playwright playwright;
    APIRequest apiRequest;
    APIRequestContext apiRequestContext;

    @BeforeTest
    public void setUp() {
        playwright = Playwright.create();
        apiRequest = playwright.request();
        apiRequestContext = apiRequest.newContext();
    }

    @Test
    public void getSpecificUserDetails() {
        APIResponse response = apiRequestContext.get("https://gorest.co.in/public/v2/users",
                RequestOptions.create().setQueryParam("gender", "male"));
        int statusCode = response.status();
        System.out.println("respone code : " + statusCode);
        Assert.assertEquals(response.ok(), true);
        System.out.println("Test Case 1");

    }

    @Test
    public void getUserAPITest() throws IOException {


        APIResponse response = apiRequestContext.get("https://gorest.co.in/public/v2/users");
        int statusCode = response.status();
        System.out.println("respone code : " + statusCode);
        Assert.assertEquals(statusCode, 200);

        String statusText = response.statusText();
        System.out.println("Status Text :" + statusText);


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode JresposeBody = objectMapper.readTree(response.body());
        String apiBody = JresposeBody.toPrettyString();
        System.out.println(apiBody);

        System.out.println("-------------------Printing Headers-----------------------");
        Map<String, String> headersMap = response.headers();
        // System.out.println(headersMap);
        Assert.assertEquals(headersMap.get("content-type"), "application/json; charset=utf-8");
        System.out.println("Test Case 2");
    }

    @AfterTest
    public void close() {
        playwright.close();
    }
}
