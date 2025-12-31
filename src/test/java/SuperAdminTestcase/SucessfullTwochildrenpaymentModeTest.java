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

@Listeners(listeners.TestListener.class)
public class SucessfullTwochildrenpaymentModeTest extends BaseDriver {

    @Test
    public void SuccessfullTwochildrenpaymentTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            // 1️⃣ Login
            SuperLoginPage superloginPage = new SuperLoginPage(driver);
            superloginPage.enterEmail("mereg2025@gmail.com");
            superloginPage.enterPassword("SuperAdmin@123");
            superloginPage.clickSignIn();
            System.out.println("✅ Login successful");

            // 2️⃣ Navigate to Payment Page
            SuperPaymentPage paymentPage = new SuperPaymentPage(driver);
            try {
                paymentPage.clickOnPaymentPage();
            } catch (Exception e) {
                System.err.println("⚠️ Could not navigate to payment page: " + e.getMessage());
            }

            // 3️⃣ Select Guardian and Children
            TwoChildrensPaymentPage paymentSelectionPage = new TwoChildrensPaymentPage(driver);
            Thread.sleep(2000);
            //paymentSelectionPage.clickOnGurdian();
            paymentSelectionPage.clickOnGuardianByIndex(37);
            Thread.sleep(2000);
            paymentSelectionPage.selectTwoChildren();
            Thread.sleep(2000);
            paymentSelectionPage.debugRadioButtons();
            paymentSelectionPage.selectCustomAmount();
            Thread.sleep(1000);
            paymentSelectionPage.enterRandomAmount();
            Thread.sleep(1000);
            paymentSelectionPage.clickProceedButton();

            System.out.println("🎉 Successfully navigated to Payment Summary Page.");

            // 4️⃣ Payment Summary Page
            SuperSuccesfullPaymentPage paymentSummaryPage = new SuperSuccesfullPaymentPage(driver, wait);
            String title = paymentSummaryPage.getPageTitleText();
            System.out.println("📄 Payment Summary Title: " + title);
            Assert.assertTrue(title.contains("Payment Summary") || title.contains("Pay with card"),
                    "❌ Unexpected Payment Page Title!");

            // 5️⃣ Select Payment Mode dynamically
            String chequeFile = "C:\\Srasys-2021\\Attachment\\sri.txt";
            String mode = paymentSummaryPage.selectRandomPaymentModeDynamically(chequeFile);
            System.out.println("Selected Mode: " + mode);

            // 6️⃣ Store main window (for Online payments)
            String mainWindow = driver.getWindowHandle();

            // 7️⃣ Handle based on Payment Mode
            switch (mode.toLowerCase()) {
                case "online":
                    System.out.println("Processing Online Payment...");
                    paymentSummaryPage.clickPaySecurelyButton();

                    // Switch to Stripe-like checkout
                    switchToNewTab(driver, mainWindow);

                    Assert.assertTrue(paymentSummaryPage.getPageTitleText().toLowerCase().contains("pay with card"),
                            "❌ Not on card payment page!");

                    // Fill card details
                    paymentSummaryPage.enterCardDetails(
                            "testuser@example.com",
                            "4242424242424242",
                            "12/34",
                            "123",
                            "Test User",
                            "India",
                            "560001"
                    );
                    paymentSummaryPage.clickPayButton();
                    System.out.println("Online payment completed successfully.");
                    break;

                case "cheque":
                    System.out.println("Processing Cheque Payment...");
                    paymentSummaryPage.processChequePayment(chequeFile);
                    paymentSummaryPage.clickPaySecurelyButton();
                    System.out.println("Cheque payment submitted successfully.");
                    break;

                case "cash":
                    System.out.println("Processing Cash Payment...");
                    paymentSummaryPage.processCashPayment();
                    paymentSummaryPage.clickPaySecurelyButton();
                    System.out.println("Cash payment submitted successfully.");
                    break;

                default:
                    System.out.println("⚠️ Unknown payment mode selected: " + mode);
            }

            System.out.println("Payment flow test completed successfully.");

        } catch (Exception e) {
            System.err.println("❌ Test failed due to exception: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed due to unexpected error.");
        } finally {
            try {
                driver.quit();
                System.out.println("Browser closed successfully.");
            } catch (Exception e) {
                System.err.println("Error while closing browser: " + e.getMessage());
            }
        }
    }

    /**
     * 🔄 Utility to switch to a newly opened browser tab (e.g., Stripe Checkout)
     */
    private void switchToNewTab(WebDriver driver, String mainWindow) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        System.out.println("Waiting for Stripe checkout tab or new window...");

        boolean newTabOpened = wait.until(d -> driver.getWindowHandles().size() > 1);

        if (newTabOpened) {
            Set<String> allWindows = driver.getWindowHandles();
            for (String handle : allWindows) {
                if (!handle.equals(mainWindow)) {
                    driver.switchTo().window(handle);
                    System.out.println("Switched to new Stripe checkout tab: " + driver.getTitle());
                    return;
                }
            }
        }

        // If no new tab, check if payment form is visible on the same page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Pay with card')]")));
        System.out.println("✅ Staying on same window — payment form is visible.");
    }
}
