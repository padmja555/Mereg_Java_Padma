package AdminTestcase;

import java.io.File;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import AdminPages.LoginPage;
import AdminPages.adminpage1;
import AdminPages.studentspage;
import AdminPages.DashboardStudentsExcelPage;
import Base.BaseDriver;
import utils.ExcelReader;

public class DashboardStudentsExcelTest extends BaseDriver {

    LoginPage loginPage;
    DashboardStudentsExcelPage excelPage;

    @Test
  
    public void testExcelDownloadFromStudentsDashboard() {
        try {
            System.out.println("=== Starting Excel Download Test ===");

            loginPage = new LoginPage(driver);
            excelPage = new DashboardStudentsExcelPage(driver);

            // Step 1: Login
            loginPage.enterEmail("srasysife@gmail.com");
            loginPage.enterPassword("Admin@123");
            loginPage.clickSignIn();
            System.out.println("✅ Logged in successfully");

            studentspage studentlistPage = new studentspage(driver);
            studentlistPage.clickOnStudents();

            Thread.sleep(2000);

            // Step 2: Enter child last name
            excelPage.enterChildLastName("DD");

            // Step 3: Click Excel download button
            excelPage.clickExcelDownload();
            String downloadPath = System.getProperty("user.home") + "\\Downloads";

            // Step 4: Wait until the latest Excel file appears
            File latestExcel = null;
            int attempts = 0;
            while (attempts < 10) { // check every 0.5 sec
                latestExcel = ExcelReader.getLatestDownloadedFile(downloadPath);
                if (latestExcel != null) break;
                Thread.sleep(500);
                attempts++;
            }

            if (latestExcel != null) {
                System.out.println("✅ Latest Excel found: " + latestExcel.getName());

                // Step 5: Open in Excel application
                try {
                    Runtime.getRuntime().exec("cmd /c start excel \"" + latestExcel.getAbsolutePath() + "\"");
                    System.out.println("✅ Excel file opened in Excel application");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Step 6: Read Excel content in console
                ExcelReader.readExcel(latestExcel.getAbsolutePath());
            } else {
                Assert.fail("❌ No downloaded Excel file found!");
            }

            System.out.println("✅ Excel Download Test Passed");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Excel Download Test Failed: " + e.getMessage());
        }
    }
}