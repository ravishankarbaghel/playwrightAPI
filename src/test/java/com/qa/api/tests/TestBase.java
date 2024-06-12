package com.qa.api.tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import reports.ExtentManager;

import java.io.IOException;

public class TestBase {
    @BeforeSuite
    public void beforeSuite() throws IOException {
        ExtentManager.setExtent();
    }

    @AfterSuite
    public void afterSuite() {
        ExtentManager.endReport();
    }
}
