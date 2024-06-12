package com.qa.api.tests;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import data.UserData;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class UpdateUserPutCallWithPOJOLonbokTest {

    /**
     * Flow of Test
     * 1.Make a Post call from ther get the ID
     * 2.Make a Put Call from there update any value from for the created ID
     * 3.Make a Get Call to Validate if the created ID has been Updated
     */
    static Logger logger = Logger.getLogger(UpdateUserPutCallWithPOJOLonbokTest.class.getName());

    Playwright playwright;
    APIRequest apiRequest;
    APIRequestContext apiRequestContext;
    static String email;
    static UserData userData = UserData.builder().name("New User").email(genrateEmailAddress()).gender("Male").status("active").build();

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

    public static String genrateEmailAddress() {
        email = "TestUser" + System.currentTimeMillis() + "@testmail.com";
        return email;
    }

    static String ID;

    @Test(priority = 1)
    public void postCall() throws JsonProcessingException {


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
    public void putCall() throws JsonProcessingException {
        userData = UserData.builder().build();
        userData.setName("NewUser2");
        userData.setGender("female");
        APIResponse putResponse = apiRequestContext.put("https://gorest.co.in/public/v2/users/" + ID, RequestOptions.create()
                .setHeader("content-type", "application/json")
                .setHeader("Authorization", "Bearer ec0a08ec6879f19e412d690df7f819a83217304a3f3952137452000e3d9b9595")
                .setData(userData));

        //Assertions
        Assert.assertEquals(putResponse.status(), 200);
        String putResponseText = putResponse.text();
        ObjectMapper putObjectMapper = new ObjectMapper();
        UserData putActUserData = putObjectMapper.readValue(putResponseText, UserData.class);
        //storing th
        String PutID = putActUserData.getId();
        Assert.assertEquals(PutID, ID);
        Assert.assertEquals(userData.getGender(), putActUserData.getGender());
        logger.info("Gender-->" + putActUserData.getGender());
        Assert.assertEquals(userData.getName(), putActUserData.getName());
        logger.info("Name -->" + putActUserData.getName());
    }

    @Test(priority = 3)
    public void getCall() throws JsonProcessingException {
        APIResponse getResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users/"+ID,RequestOptions.create()
                        .setHeader("Authorization", "Bearer ec0a08ec6879f19e412d690df7f819a83217304a3f3952137452000e3d9b9595"));
        logger.info(getResponse.url());
        Assert.assertEquals(getResponse.status(), 200);
        UserData getUserData = new ObjectMapper().readValue(getResponse.text(), UserData.class);
        Assert.assertEquals(userData.getName(), getUserData.getName());
        Assert.assertEquals(ID,getUserData.getId());

    }
}
