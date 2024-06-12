package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.IOException;

public class ExtentManager {

    /**
     * Path where html report should be generated
     * Load extent-config.xml file configuration to generate the report
     */

    public static ExtentReports extent;
    public static ExtentTest test;
    public static ExtentSparkReporter htmlReporter;

    public static void setExtent() throws IOException {
        htmlReporter = new ExtentSparkReporter(
                "target/test-output/ExtentReport/"
                        + "TestExecutionReport"
                        + ".html");
        htmlReporter.loadXMLConfig("src/main/resources/" + "extent-config.xml");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);


    }


    public static void endReport() {
        extent.flush();
    }
}
