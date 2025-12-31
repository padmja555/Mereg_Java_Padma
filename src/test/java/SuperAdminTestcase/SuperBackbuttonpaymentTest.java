package SuperAdminTestcase;

import AdminPages.LoginPage;
import AdminPages.Onlinecardpayment;
import AdminPages.BackbuttonPaymentPage;
import AdminPages.ChildPayment;
import AdminPages.PaymentSummaryPage;
import AdminPages.PaymentSummaryonliepage;
import Base.BaseDriver;
import SuperAdminPage.SuperBackbuttonpaymentPage;
import SuperAdminPage.SuperGuardianPaymentPage;
import SuperAdminPage.SuperLoginPage;
import SuperAdminPage.SuperOnlinePaymentPage;
import SuperAdminPage.SuperPaymentPage;

import java.util.concurrent.TimeoutException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class SuperBackbuttonpaymentTest extends BaseDriver {

    ChildPayment payment1;
    BackbuttonPaymentPage SummaryPage;
    SuperOnlinePaymentPage onlinePaymentPage;

    @Test
    public void paymentBackButtonPaymentTestTest() throws InterruptedException {
        // 1️⃣ Login as Super Admin
        SuperLoginPage superloginPage = new SuperLoginPage(driver);
        superloginPage.enterEmail("mereg2025@gmail.com");
        superloginPage.enterPassword("SuperAdmin@123");
        superloginPage.clickSignIn();
        System.out.println("✅ Login successful");

        // 2️⃣ Payment page flow
        SuperPaymentPage payment = new SuperPaymentPage(driver);
        try {
            payment.clickOnPaymentPage();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        // 3️⃣ Guardian & Child Selection
        SuperGuardianPaymentPage paymentp = new SuperGuardianPaymentPage(driver);
        paymentp.selectGuardianByName("Radhika CC");
        Thread.sleep(2000);
        paymentp.selectRandomChild();
        Thread.sleep(2000);
        paymentp.debugRadioButtons();
        paymentp.selectCustomAmount();
        paymentp.clickProceedButton();
        System.out.println("✅ TEST PASSED: Specific guardian payment completed");

        // 4️⃣ Online Payment Summary
        onlinePaymentPage = new SuperOnlinePaymentPage(driver, wait);
        onlinePaymentPage.verifyPaymentSummaryPage();
        onlinePaymentPage.clickPaySecurelyButton();

        // 5️⃣ Backbutton Payment Page
        SuperBackbuttonpaymentPage backbuttonpayment = new SuperBackbuttonpaymentPage(driver, wait);

        // Verify the page title
        Assert.assertTrue(backbuttonpayment.getPageTitleText().contains("Pay with card"));

        // Enter card details
        backbuttonpayment.enterCardDetails(
            "testuser@example.com",
            "4242424242424242",
            "12/34",
            "123",
            "Test User",
            "United States",
            "10001"
        );
        System.out.println("✅ Online payment completed");

        // Optionally get title again
        String title = backbuttonpayment.getPageTitleText();
        System.out.println("Page title: " + title);

        // Click back button
        backbuttonpayment.clickbackButton();
        System.out.println("✅ Back button clicked successfully");
    }


}

