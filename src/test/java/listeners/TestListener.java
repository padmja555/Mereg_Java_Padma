package listeners;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.media.entity.MediaEntityBuilder;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentManager;
import utils.Screenshot;

import java.io.IOException;
import java.lang.reflect.Field;

public class TestListener implements ITestListener {

    // Shared ExtentReports instance
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        System.out.println("📘 Test Suite started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        System.out.println("📗 Test Suite completed: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = getTestName(result);
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);

        Object[] parameters = result.getParameters();
        if (parameters != null && parameters.length > 0) {
            test.get().info("🔧 Test Parameters: " + java.util.Arrays.toString(parameters));
        }

        test.get().info("🚀 Starting test: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "✅ Test passed successfully");
        logExecutionTime(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "❌ Test Failed: " + result.getThrowable().getMessage());
        test.get().log(Status.FAIL, result.getThrowable());

        // Try to get WebDriver from test class
        WebDriver driver = getDriverFromTest(result);

        if (driver != null) {
            captureAndAttachScreenshot(driver, result.getMethod().getMethodName());
        } else {
            test.get().log(Status.WARNING, "⚠️ WebDriver not found for screenshot");
        }

        logExecutionTime(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "⏭️ Test Skipped: " +
                (result.getThrowable() != null ? result.getThrowable().getMessage() : "No reason specified."));
        logExecutionTime(result);
    }

    // ====================== Utility Methods ======================

    private WebDriver getDriverFromTest(ITestResult result) {
        try {
            // Try to find WebDriver field in the test class
            Field driverField = result.getInstance().getClass().getDeclaredField("driver");
            driverField.setAccessible(true);
            return (WebDriver) driverField.get(result.getInstance());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Fallback: try from test context
            return (WebDriver) result.getTestContext().getAttribute("driver");
        }
    }

    private void captureAndAttachScreenshot(WebDriver driver, String methodName) {
        try {
            String screenshotPath = Screenshot.captureScreenshotForReport(driver, methodName);
            test.get().fail("📸 Screenshot of failure",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } catch (IOException e) {
            test.get().log(Status.WARNING, "⚠️ Failed to capture screenshot: " + e.getMessage());
        }
    }

    private String getTestName(ITestResult result) {
        String name = result.getMethod().getMethodName();
        String className = result.getTestClass().getRealClass().getSimpleName();
        return className + "." + name;
    }

    private void logExecutionTime(ITestResult result) {
        long duration = result.getEndMillis() - result.getStartMillis();
        test.get().info("🕒 Execution time: " + duration + " ms");
    }
}
