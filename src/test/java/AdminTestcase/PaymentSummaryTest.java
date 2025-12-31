package AdminTestcase;

import AdminPages.LoginPage;
import AdminPages.ChildPayment;
import AdminPages.PaymentSummaryPage;
import AdminPages.PaymentSummaryonliepage;
import Base.BaseDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class PaymentSummaryTest extends BaseDriver {

    ChildPayment payment1;
    PaymentSummaryPage paymentSummaryPage;
    PaymentSummaryonliepage onlinePaymentPage;
 

    @Test
    public void paymentSummaryPageTest() throws InterruptedException {
        // 1️⃣ Login
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        // 2️⃣ Payment page flow
        payment1 = new ChildPayment(driver, wait);
        payment1.clickOnPaymentPage();
        payment1.clickOnGurdian();
        Thread.sleep(3000);
        payment1.selectRandomChild();
        payment1.selectRandomPaymentOptionAndEnterAmount("1000", "500");
        payment1.Procedbuttonpayment();

        paymentSummaryPage = new PaymentSummaryPage(driver, wait);
        String title = paymentSummaryPage.getPageSummaryPageTitleText();
        System.out.println("Payment Summary: " + title);
        Assert.assertEquals(title, "Payment Summary");

        // 3️⃣ Randomly select a payment mode
        String chequeFilePath = "C:\\Upload\\Padma.txt"; // Replace with valid file path
        String selectedMode = paymentSummaryPage.selectRandomPaymentModeDynamically(chequeFilePath);
        System.out.println("Selected Payment Mode: " + selectedMode);

        // 4️⃣ Handle each payment type
        onlinePaymentPage = new PaymentSummaryonliepage(driver, wait); // Instantiate once

        if (selectedMode.equalsIgnoreCase("Online")) {
            paymentSummaryPage.clickPaySecurelyButton();

            Assert.assertTrue(onlinePaymentPage.getPageTitleText().contains("Pay with card"));

            onlinePaymentPage.enterCardDetails(
                "testuser@example.com",
                "4242424242424242",
                "12/34",
                "123",
                "Test User",
                "United States", // or "United Kingdom"
                "10001"          // ZIP / Postal Code
            );
            onlinePaymentPage.clickPayButton();
            System.out.println("✅ Online payment completed.");

        } else if (selectedMode.equalsIgnoreCase("Cheque")) {
            onlinePaymentPage.enterChequeNumber("CHQ-123456");
            paymentSummaryPage.clickPaySecurelyButton();
            System.out.println("✅ Cheque payment submitted.");

        } else if (selectedMode.equalsIgnoreCase("Cash")) {
            onlinePaymentPage.enterCashReceiptNumber("CASH-987654");
            paymentSummaryPage.clickPaySecurelyButton();
            System.out.println("✅ Cash payment submitted.");
        }
    }
}