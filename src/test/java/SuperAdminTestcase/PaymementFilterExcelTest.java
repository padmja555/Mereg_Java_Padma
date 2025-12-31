package SuperAdminTestcase;

import Base.BaseDriver;
import SuperAdminPage.ManagementPage;
import SuperAdminPage.PaymementFilterExcelPage;
import SuperAdminPage.PaymentFilterPage;
import SuperAdminPage.SuperLoginPage;
import utils.ExcelReader;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PaymementFilterExcelTest extends BaseDriver {

    @Test(priority = 1)
    public void testApplyFilterexcelWithRandomDates() {
        try {
            System.out.println("=== Navigating to Management Page ===");

            // ===== Login =====
            SuperLoginPage superloginPage = new SuperLoginPage(driver);
            superloginPage.enterEmail("mereg2025@gmail.com");
            superloginPage.enterPassword("SuperAdmin@123");
            superloginPage.clickSignIn();
            System.out.println("✅ Login successful");
            Thread.sleep(2000);

            // ===== Open Management Page =====
            ManagementPage managementPage = new ManagementPage(driver);
            managementPage.clickOnmanagementPage();
            Thread.sleep(2000);

            // ===== Apply Filter Dates =====
            PaymentFilterPage filterPage = new PaymentFilterPage(driver);
            filterPage.waitForPageLoad();
            // Debug: Check what elements are available
            filterPage.debugCheckElements();

            filterPage.setFromDate();  // Fixed: October 1, 2025
            filterPage.setToDate();    // Fixed: October 6, 2025
            filterPage.clickApplyFilters();
            Thread.sleep(3000);

            // ===== Search Random Child and Download Excel =====
            PaymementFilterExcelPage excelPage = new PaymementFilterExcelPage(driver);
            excelPage.enterRandomChildLastName();
            Thread.sleep(3000);
            excelPage.clickExcelDownload();
            Thread.sleep(3000);

            // ===== Verify Excel Download =====
            String downloadPath = System.getProperty("user.home") + "\\Downloads";
            File latestExcel = null;
            int attempts = 0;

            while (attempts < 10) { // check every 0.5 sec up to ~5 seconds
                latestExcel = ExcelReader.getLatestDownloadedFile(downloadPath);
                if (latestExcel != null) break;
                Thread.sleep(500);
                attempts++;
            }

            if (latestExcel != null) {
                System.out.println("✅ Latest Excel found: " + latestExcel.getName());

                // ===== Open Excel Application =====
                try {
                    Runtime.getRuntime().exec("cmd /c start excel \"" + latestExcel.getAbsolutePath() + "\"");
                    System.out.println("✅ Excel file opened in Excel application");
                } catch (Exception e) {
                    System.out.println("⚠ Unable to open Excel automatically. File is saved in Downloads folder.");
                }

                // ===== Read Excel Content in Console =====
                ExcelReader.readExcel(latestExcel.getAbsolutePath());
            } else {
                Assert.fail("❌ No downloaded Excel file found!");
            }

            System.out.println("✅ Excel Download Test Passed");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("❌ Excel Download Test Failed: " + e.getMessage());
        }
    }
}
