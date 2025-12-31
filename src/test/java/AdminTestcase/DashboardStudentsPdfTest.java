package AdminTestcase;

import java.awt.Desktop;
import java.io.File;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import AdminPages.LoginPage;
import AdminPages.studentspage;
import AdminPages.DashboardStudentsPdfPage;
import Base.BaseDriver;

public class DashboardStudentsPdfTest extends BaseDriver {

    LoginPage loginPage;
    DashboardStudentsPdfPage pdfPage;

    @Test
    public void testPDFDownloadFromStudentsDashboard() {
        try {
            System.out.println("=== Starting PDF Download Test ===");

            loginPage = new LoginPage(driver);
            pdfPage = new DashboardStudentsPdfPage(driver);

            // Step 1: Login
            loginPage.enterEmail("srasysife@gmail.com");
            loginPage.enterPassword("Admin@123");
            loginPage.clickSignIn();
            System.out.println("✅ Logged in successfully");

            // Step 2: Navigate to Students tab
            studentspage studentlistPage = new studentspage(driver);
            studentlistPage.clickOnStudents();
            Thread.sleep(2000);
            System.out.println("✅ Navigated to Students Dashboard");

            // Step 3: Enter child last name
            pdfPage.enterChildLastName("DD");

            // Step 4: Click PDF download button
            pdfPage.clickPDFDownload();

            // Step 5: Wait for the PDF to appear in Downloads folder
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
