package com.qa.api.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import data.UserData;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.logging.Logger;

public class DeleteUserTest {

    static Logger logger = Logger.getLogger(DeleteUserTest.class.getName());
    Playwright playwright;
    APIRequest apiRequest;
    APIRequestContext apiRequestContext;
    static String email;
    static UserData userData = UserData.builder().name("New User").email(genrateEmailAddress()).gender("Male").status("active").build();

    public static String genrateEmailAddress() {
        email = "TestUser" + System.currentTimeMillis() + "@testmail.com";
        return email;
    }

    @BeforeTest
    public void SetUp() {
        playwright = Playwright.create();
        apiRequest = playwright.request();
        apiRequestContext = apiRequest.newContext();
    }

    @AfterTest
    public void tearDown() {
        playwright.close();
    }

    static String ID;

    @Test(priority = 1)
    public void postCall() throws JsonProcessingException {

//please generate your token API URL : https://gorest.co.in/public/v2/users
        APIResponse postResponse = apiRequestContext.post("https://gorest.co.in/public/v2/users", RequestOptions.create()
                .setHeader("content-type", "application/json")
                .setHeader("Authorization", "Bearer ec0a08ec6879f19e412d690df7f819a83217304a3f3952137452000e3d9b9595")
                .setData(userData));

        //Assertions
        Assert.assertEquals(postResponse.status(), 201);
        String responseText = postResponse.text();

        ObjectMapper postObjectMapper = new ObjectMapper();
        UserData actUserData = postObjectMapper.readValue(responseText, UserData.class);
        ID = actUserData.getId();
        logger.info("The ID is : " + ID);
        Assert.assertNotNull(actUserData.getId());
    }

    @Test(priority = 2)
    public void deleteCall() throws JsonProcessingException {


        APIResponse postResponse = apiRequestContext.delete("https://gorest.co.in/public/v2/users/" + ID,
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer ec0a08ec6879f19e412d690df7f819a83217304a3f3952137452000e3d9b9595"));

        //Assertions
        Assert.assertEquals(postResponse.status(), 204);
        Assert.assertEquals(postResponse.statusText(), "No Content");
    }
}