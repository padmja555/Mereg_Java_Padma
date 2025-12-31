package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screenshot {

    // Save screenshot to test-output/screenshots/
    public static String captureScreenshot(WebDriver driver, String screenshotName) throws IOException {
        String folderPath = System.getProperty("user.dir") + File.separator +
                "test-output" + File.separator + "screenshots";

        File screenshotDir = new File(folderPath);
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs(); // create folder if missing
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = screenshotName + "_" + timeStamp + ".png";
        String fullPath = folderPath + File.separator + fileName;

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File(fullPath);
        FileUtils.copyFile(srcFile, destFile);

        return fullPath;
    }

    // Return relative path for ExtentReport
    public static String captureScreenshotForReport(WebDriver driver, String screenshotName) throws IOException {
        String fullPath = captureScreenshot(driver, screenshotName);
        return "screenshots" + File.separator + new File(fullPath).getName();
    }
}
