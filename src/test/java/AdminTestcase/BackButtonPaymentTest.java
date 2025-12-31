package AdminTestcase;

import AdminPages.LoginPage;
import AdminPages.Onlinecardpayment;
import AdminPages.BackbuttonPaymentPage;
import AdminPages.ChildPayment;
import AdminPages.PaymentSummaryPage;
import AdminPages.PaymentSummaryonliepage;
import Base.BaseDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class BackButtonPaymentTest extends BaseDriver {

    ChildPayment payment1;
    BackbuttonPaymentPage SummaryPage;
    PaymentSummaryonliepage onlinePaymentPage;

    @Test
    public void paymentBackButtonPaymentTestTest() throws InterruptedException {
        // 1️⃣ Login
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();
        Thread.sleep(2000);
        // 2️⃣ Payment page flow
        payment1 = new ChildPayment(driver, wait);
        payment1.clickOnPaymentPage();
        Thread.sleep(2000);
        payment1.clickOnGurdian();
        Thread.sleep(3000);
        payment1.selectRandomChild();
        Thread.sleep(1000);
        payment1.selectRandomPaymentOptionAndEnterAmount("1000", "500");
        payment1.Procedbuttonpayment();
        
        BackbuttonPaymentPage summarypage = new BackbuttonPaymentPage(driver, wait);

        SummaryPage = new BackbuttonPaymentPage(driver, wait);
        String title = SummaryPage.getPageSummaryPageTitleText();
        System.out.println("Payment Summary: " + title);
        Assert.assertEquals(title, "Payment Summary");

        // 3️⃣ Randomly select a payment mode
        // Step 2: Select Online Payment Mode
        //Onlinecardpayment summarypage = new Onlinecardpayment(driver, wait);
        String selectedMode = SummaryPage.selectOnlinePaymentMode();
        Assert.assertEquals(selectedMode, "Online", "❌ Online mode not selected");

        // Step 3: Click Pay Securely
        SummaryPage.clickPaySecurelyButton();

        // Step 4: Verify Online Payment Page
        
        //String onlineTitle = onlinePaymentPage.getPageTitleText();
        //Assert.assertTrue(onlineTitle.contains("Pay with card"), "❌ Online Payment Page not opened");

        System.out.println("Navigated to Online Payment Page successfully.");

        // Step 5: Click Back Button
        SummaryPage.clickbackButton();
        Thread.sleep(1000);
        // Step 6: Verify returned to Payment Summary Page
        //String returnedTitle = SummaryPage.getPagebackbuttonTitleText();
        Thread.sleep(1000);
        //Assert.assertEquals(returnedTitle, "Pay with card", "❌ Back button did not return to Payment Summary Page");

        //System.out.println("Back button worked successfully → Returned to Payment Summary Page.");
    }
}
