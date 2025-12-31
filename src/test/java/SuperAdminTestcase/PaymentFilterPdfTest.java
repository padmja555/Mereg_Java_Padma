
package SuperAdminTestcase;

import Base.BaseDriver;
import SuperAdminPage.ManagementPage;
import SuperAdminPage.PaymementFilterExcelPage;
import SuperAdminPage.PaymentFilterPage;
import SuperAdminPage.PaymentFilterPdfPage;
import SuperAdminPage.SuperLoginPage;
//import utils.ExcelReader;

import java.awt.Desktop;
import java.io.File;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class PaymentFilterPdfTest extends BaseDriver {

    @Test(priority = 1)
    public void testApplyFilterPdfWithRandomDates() {
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
            PaymentFilterPdfPage pdfPage = new PaymentFilterPdfPage(driver);
            pdfPage.enterRandomChildLastName();
            Thread.sleep(2000);
            pdfPage.clickPDFDownload();
            Thread.sleep(2000);

            // ===== Verify Excel Download =====
            String downloadPath = System.getProperty("user.home") + "\\Downloads";
            File latestPDF = null;
            int attempts = 0;

            while (attempts < 20) { // check every 0.5 sec, up to 10 seconds
                File dir = new File(downloadPath);
                File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".pdf"));

                if (files != null && files.length > 0) {
                    // Sort by last modified (latest first)
                    java.util.Arrays.sort(files, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
                    latestPDF = files[0]; // pick the newest PDF
                    break;
                }
                Thread.sleep(500);
                attempts++;
            }

            if (latestPDF != null) {
                System.out.println("✅ Latest PDF found: " + latestPDF.getName());

                // Open PDF in default system viewer
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(latestPDF);
                    System.out.println("✅ PDF opened in default PDF viewer");
                } else {
                    System.out.println("❌ Desktop not supported. Cannot open PDF automatically");
                }

            } else {
                Assert.fail("❌ No downloaded PDF file found!");
            }

            System.out.println("✅ PDF Download Test Passed");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("❌ PDF Download Test Failed: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
                System.out.println("✅ Browser closed successfully");
            }
        } catch (Exception e) {
            System.out.println("❌ Error closing browser: " + e.getMessage());
        }
    }
}
