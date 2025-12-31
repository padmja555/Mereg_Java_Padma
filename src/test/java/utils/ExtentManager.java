package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) setupExtentReport();
        return extent;
    }

    private static void setupExtentReport() {
        String reportDir = System.getProperty("user.dir") + File.separator + "test-output";
        new File(reportDir).mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = reportDir + File.separator + "AutomationReport_" + timeStamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle("Automation Test Report");
        spark.config().setReportName("Test Execution Report");
        spark.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(spark);
    }
}
