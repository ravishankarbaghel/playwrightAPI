package com.qa.api.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import data.ResfulBookerData;
import data.RestfullBooker_BookingDates;
import data.TokenData;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class TokenAuthorizationTest extends TestBase{

    /**
     * Here we will be generating a token before making an delete, put call
     * flow
     * as per API used first POST call to generate token
     * Then using the generated token to Make Put and Delete call
     * <p>
     * API used : https://restful-booker.herokuapp.com/apidoc/index.html#api-Auth-CreateToken
     */

    Playwright playwright;
    APIRequest apiRequest;
    APIRequestContext apiRequestContext;
    RestfullBooker_BookingDates restfullBookerBookingDates;
    ResfulBookerData resfulBookerData = ResfulBookerData.builder().firstname("TestUser").lastname("APITest" + System.currentTimeMillis())
            .totalprice(String.valueOf(new Random().nextInt(1000))).depositpaid(String.valueOf(new Random().nextBoolean()))
            .bookingdates(RestfullBooker_BookingDates.builder().checkin("2024-04-14").checkout("2024-04-14").build()).additionalneeds("Breakfast").build();

    TokenData tokenData = TokenData.builder().username("admin").password("password123").build();

    @BeforeTest
    public void setUp() {
        playwright = Playwright.create();
        apiRequest = playwright.request();
        apiRequestContext = apiRequest.newContext();
    }

    @AfterTest
    public void tearDown() {
        playwright.close();
    }
static String TOKEN_ID;
    @Test(priority = 1)
    public void postCalltoGenerateToken() throws IOException {
        APIResponse getTokenResponse = apiRequestContext.post("https://restful-booker.herokuapp.com/auth", RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setData(tokenData));

        Assert.assertEquals(getTokenResponse.status(), 200);
        System.out.println(getTokenResponse.text());

        ObjectMapper tokenObj = new ObjectMapper();
        JsonNode jToken = tokenObj.readTree(getTokenResponse.body());
        TOKEN_ID=jToken.elements().next().asText();
        System.out.println("Token ID :"+ TOKEN_ID);
    }


    @Test(description = "Creating and Updating Booking ID using PUT Method",priority = 2)
    public void updateBookingID()
    {
        APIResponse getBookingResponse = apiRequestContext.post("https://restful-booker.herokuapp.com/booking", RequestOptions.create()
                .setHeader("Content-Type", "application/json")
                .setHeader("Cookie",TOKEN_ID)
                .setData(resfulBookerData));
        System.out.println(getBookingResponse.text());
        System.out.println(getBookingResponse.status());
        System.out.println(getBookingResponse.statusText());
        Assert.assertEquals(getBookingResponse.status(),200);


    }
}
