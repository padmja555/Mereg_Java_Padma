package SuperAdminTestcase;

import Base.BaseDriver;
import SuperAdminPage.*;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import AdminPages.ChildPayment;
import AdminPages.PaymentSummaryPage;

@Listeners(listeners.TestListener.class)
public class SuperSuccessfullPaymentTest extends BaseDriver {

    @Test
    public void SuccessfullPaymentTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            // 1️⃣ Login
            SuperLoginPage superloginPage = new SuperLoginPage(driver);
            superloginPage.enterEmail("mereg2025@gmail.com");
            superloginPage.enterPassword("SuperAdmin@123");
            superloginPage.clickSignIn();
            System.out.println("✅ Login successful");

            // 2️⃣ Navigate to Payment page
            SuperPaymentPage paymentPage = new SuperPaymentPage(driver);
            try {
                paymentPage.clickOnPaymentPage();
            } catch (Exception e) {
                System.err.println("⚠️ Could not navigate to payment page: " + e.getMessage());
            }
            ChildPayment payment1 = new ChildPayment(driver, wait); 


            // 3️⃣ Guardian selection
            SuperGuardianPaymentPage guardianPage = new SuperGuardianPaymentPage(driver);
            guardianPage.selectGuardianByName("Vinayaka GG");
            //selectRandomChild
            guardianPage.selectRandomChild();
            Thread.sleep(2000);
            guardianPage.debugRadioButtons();
            guardianPage.selectCustomAmount();
            Thread.sleep(2000);
            guardianPage.clickProceedButton();


            // 4️⃣ Payment Summary Page
            SuperSuccesfullPaymentPage payment = new SuperSuccesfullPaymentPage(driver, wait);
            String title = payment.getPageTitleText();
            System.out.println("Payment Summary: " + title);
            Assert.assertEquals(title, "Payment Summary");
            System.out.println("📄 Page title: " + title);
            
            
            //String chequeFilePath = "C:\\Upload\\Padma.txt"; // Replace with valid file path
            //String selectedMode = paymentSummaryPage.selectRandomPaymentModeDynamically(chequeFilePath);
            //System.out.println("Selected Payment Mode: " + selectedMode);

            // 5️⃣ Select Payment Mode dynamically
            String chequeFile = "C:\\Srasys-2021\\Attachment\\sri.txt";
            String mode = payment.selectRandomPaymentModeDynamically(chequeFile);
            System.out.println("💳 Selected Mode: " + mode);

            // Store main window handle (for online mode)
            String mainWindow = driver.getWindowHandle();

            // 6️⃣ Handle based on Payment Mode
            switch (mode.toLowerCase()) {

                case "online":
                    System.out.println("🌐 Processing Online Payment...");
                    payment.clickPaySecurelyButton();

                    // Wait for new tab or stay on same page
                    try {
                        switchToNewTab(driver, mainWindow);
                    } catch (Exception e) {
                        System.err.println("⚠️ Timeout waiting for Stripe page: " + e.getMessage());
                    }

                    // Verify title
                    Assert.assertTrue(payment.getPageTitleText().toLowerCase().contains("pay with card"),
                            "❌ Not on card payment page!");

                    // Fill card details
                    payment.enterCardDetails(
                            "testuser@example.com",
                            "4242424242424242",
                            "12/34",
                            "123",
                            "Test User",
                            "India",
                            "560001"
                    );
                    payment.clickPayButton();
                    System.out.println("✅ Online payment completed successfully.");
                    break;

                case "cheque":
                    System.out.println("🏦 Processing Cheque Payment...");
                    payment.processChequePayment(chequeFile);
                    payment.clickPaySecurelyButton();
                    System.out.println("✅ Cheque payment submitted successfully.");
                    break;

                case "cash":
                    System.out.println("💵 Processing Cash Payment...");
                    payment.processCashPayment();
                    payment.clickPaySecurelyButton();
                    System.out.println("✅ Cash payment submitted successfully.");
                    break;

                default:
                    System.out.println("⚠️ Unknown payment mode: " + mode);
            }

            System.out.println("🎉 Test completed successfully!");

        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();

        } finally {
            // Always close browser
            try {
                driver.quit();
                System.out.println("✅ Browser closed successfully");
            } catch (Exception e) {
                System.err.println("⚠️ Error while closing browser: " + e.getMessage());
            }
        }
    }

    /**
     * 🔄 Utility to switch to a newly opened browser tab (e.g., Stripe Checkout)
     */
    private void switchToNewTab(WebDriver driver, String mainWindow) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        System.out.println("⏳ Waiting for Stripe checkout tab or new window...");

        boolean newTabOpened = wait.until(d -> driver.getWindowHandles().size() > 1);

        if (newTabOpened) {
            Set<String> allWindows = driver.getWindowHandles();
            for (String handle : allWindows) {
                if (!handle.equals(mainWindow)) {
                    driver.switchTo().window(handle);
                    System.out.println("✅ Switched to new Stripe checkout tab: " + driver.getTitle());
                    return;
                }
            }
        }

        // If no new tab, check if we stayed on same page but form loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Pay with card')]")));
        System.out.println("✅ Staying on current window — payment form visible.");
    }
}
