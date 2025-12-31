package AdminTestcase;

import Base.BaseDriver;
import AdminPages.ChildpaymentPage;
import AdminPages.LoginPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ChildpaymentTest extends BaseDriver {

    @Test
    public void verifyPaymentAndZipcodeFlow() throws InterruptedException {
        // Step 1: Login
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        // Step 2: Navigate to payment details and select a guardian/child
        ChildpaymentPage paymentPage = new ChildpaymentPage(driver);
        paymentPage.goToPaymentPage();
        Thread.sleep(2000);
        paymentPage.selectGuardian("Padma Pamidi");
        Thread.sleep(2000);
        paymentPage.selectChild("Sri Latha");
        Thread.sleep(2000);
        paymentPage.clickProceedPayment();

        // Step 3: Use the paymentPage object to test the country logic
        // No need to create a new instance of the page object
        /*
        // Scenario 1: Select United States and verify ZIP code field is visible
        System.out.println("Testing ZIP code visibility for United States...");
        paymentPage.selectCountry("United States");
        Assert.assertTrue(paymentPage.isZipcodeFieldDisplayed(), "Zipcode field should be visible for United States");
        System.out.println("Successfully verified that the zipcode field is visible for the United States.");

        // Scenario 2: Select India and verify ZIP code field is NOT visible
        System.out.println("Testing ZIP code visibility for India...");
        paymentPage.selectCountry("India");
        Assert.assertFalse(paymentPage.isZipcodeFieldDisplayed(), "Zipcode field should NOT be visible for India");
        System.out.println("Successfully verified that the zipcode field is NOT visible for India.");
    }
    */

}};