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

public class PostCallTest {


    Playwright playwright;
    APIRequest apiRequest;
    APIRequestContext apiRequestContext;
    static String email;

    public static String genrateEmailAddress() {
        email = "TestUser" + System.currentTimeMillis() + "@testmail.com";
        return email;
    }

    UserData userData;

    @BeforeTest
    public void setUp() {
        playwright = Playwright.create();
        apiRequest = playwright.request();
        apiRequestContext = apiRequest.newContext();
    }

    @AfterTest
    public void close() {
        playwright.close();
    }


    @Test
    public void postCall() throws JsonProcessingException {
        //using lombok builder class to generate data
        userData = UserData.builder().name("TestUser").email(genrateEmailAddress()).gender("male").status("active").build();
        //doing post call
        APIResponse response = apiRequestContext.post("https://gorest.co.in/public/v2/users", RequestOptions.create()
                .setHeader("content-type", "application/json")
                .setHeader("Authorization", "Bearer ec0a08ec6879f19e412d690df7f819a83217304a3f3952137452000e3d9b9595")
                .setData(userData));

        //response assertions
        Assert.assertEquals(response.status(), 201);
        Assert.assertEquals(response.statusText(), "Created");

        String responseText = response.text();
        System.out.println(responseText);
       //deserialization to do assertions
        ObjectMapper mapper = new ObjectMapper();
        UserData actUser = mapper.readValue(responseText, UserData.class);


        Assert.assertEquals(actUser.getName(), userData.getName());
        Assert.assertEquals(actUser.getEmail(), userData.getEmail());
        Assert.assertEquals(actUser.getGender(), userData.getGender());
        Assert.assertEquals(actUser.getStatus(), userData.getStatus());
        Assert.assertNotNull(actUser.getId());


    }

}
