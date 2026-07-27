package com.routeye.framework.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.routeye.framework.config.ConfigReader;
import com.routeye.framework.core.DriverFactory;
import com.routeye.framework.utils.ScreenshotUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Wire this up in testng.xml under <listeners>. It:
 *  - starts/flushes a single ExtentReports HTML report for the whole run
 *  - creates one ExtentTest entry per @Test method
 *  - captures & attaches a screenshot automatically when a test fails
 */
public class TestListener implements ITestListener {

    private static ExtentReports extent;
    private static final Map<Long, ExtentTest> testMap = new HashMap<>();

    private static synchronized ExtentReports getExtent() {
        if (extent == null) {
            String path = ConfigReader.get("report.path", "test-output/ExtentReport.html");
            ExtentSparkReporter spark = new ExtentSparkReporter(path);
            spark.config().setDocumentTitle("Routeye Automation Report");
            spark.config().setReportName("Routeye - Regression Run");
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }

    @Override
    public void onStart(ITestContext context) {
        getExtent();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = getExtent().createTest(result.getMethod().getMethodName());
        testMap.put(Thread.currentThread().getId(), test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testMap.get(Thread.currentThread().getId()).log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = testMap.get(Thread.currentThread().getId());
        test.log(Status.FAIL, result.getThrowable());

        try {
            String path = ScreenshotUtil.capture(DriverFactory.getDriver(), result.getMethod().getMethodName());
            if (path != null) {
                test.addScreenCaptureFromPath(path);
            }
        } catch (Exception e) {
            test.log(Status.WARNING, "Could not attach screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testMap.get(Thread.currentThread().getId()).log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        getExtent().flush();
    }
}
