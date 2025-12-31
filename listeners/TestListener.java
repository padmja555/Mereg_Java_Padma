package listeners;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.media.entity.MediaEntityBuilder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;
import utils.ExtentManager;
import utils.Screenshot;
import java.io.IOException;
import java.lang.reflect.Field;

public class TestListener implements ITestListener {

    private static final ExtentReports extent = ExtentManager.getInstance();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        System.out.println("🟢 Test Suite Started: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getTestClass().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "✅ Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "❌ Test Failed");
        test.get().log(Status.FAIL, result.getThrowable());

        WebDriver driver = getDriverFromTest(result);

        if (driver != null) {
            try {
                // Capture screenshot and get relative path
                String relativePath = Screenshot.captureScreenshotForReport(driver, result.getMethod().getMethodName());

                // Embed in Extent report
                test.get().fail("📸 Screenshot of failure",
                        MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build());

            } catch (IOException e) {
                test.get().log(Status.WARNING, "⚠️ Failed to capture screenshot: " + e.getMessage());
            }
        } else {
            test.get().log(Status.WARNING, "⚠️ WebDriver not found; no screenshot captured");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "⏭️ Test Skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        System.out.println("📗 Test Suite Completed: " + context.getName());
    }

    // 🔍 Helper: retrieve WebDriver instance from test class
    private WebDriver getDriverFromTest(ITestResult result) {
        try {
            Field driverField = result.getInstance().getClass().getDeclaredField("driver");
            driverField.setAccessible(true);
            return (WebDriver) driverField.get(result.getInstance());
        } catch (Exception e) {
            return (WebDriver) result.getTestContext().getAttribute("driver");
        }
    }
}
