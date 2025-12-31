package SuperAdminTestcase;

import Base.BaseDriver;
import SuperAdminPage.SuperGuardianPaymentPage;
import SuperAdminPage.SuperLoginPage;
import SuperAdminPage.SuperPaymentPage;
import SuperAdminPage.TwoChildrensPaymentPage;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class TwoChildrensPaymentTest extends BaseDriver {

    @Test(priority = 1)
    public void testCompletePaymentFlow() {
        System.out.println("STARTING TEST: Complete Payment Flow");

        try {
            // Step 1: Login
            SuperLoginPage superloginPage = new SuperLoginPage(driver);
            superloginPage.enterEmail("mereg2025@gmail.com");
            superloginPage.enterPassword("SuperAdmin@123");
            superloginPage.clickSignIn();
            System.out.println("✅ Login successful");
            Thread.sleep(3000);

            // Step 2: Navigate to Payment
            SuperPaymentPage paymentPage = new SuperPaymentPage(driver);
            paymentPage.clickOnPaymentPage();
            System.out.println("✅ Navigated to Payment Page");
            Thread.sleep(3000);

            // Step 3: Initialize payment page
            TwoChildrensPaymentPage payment = new TwoChildrensPaymentPage(driver);

            // Step 4: Select Guardian
            //payment.selectGuardianAndChildren();
            //payment.clickOnGurdian();
            Thread.sleep(2000);
           //payment.selectGuardian1("Prasad");
           payment.clickOnGuardianByIndex(38);
            Thread.sleep(2000);
            payment.selectTwoChildren();
            

            // Step 5: Select Child
           // payment.selectFirstChildrens();
            Thread.sleep(2000);

            // Step 6: DEBUG radio buttons before selection
            payment.debugRadioButtons();

            // Step 7: Select Payment Type - USE CORRECT METHOD NAME
            payment.selectCustomAmount(); // NOT chooseCustomAmount()
            Thread.sleep(1000);

            // Step 8: Enter Amount
            payment.enterRandomAmount();
            Thread.sleep(1000);

            // Step 9: Proceed
            payment.clickProceedButton();

            System.out.println("🎉 TEST PASSED: Complete payment flow executed successfully");

        } catch (Exception e) {
            System.err.println("❌ TEST FAILED: " + e.getMessage());
            Assert.fail("❌ Complete payment flow test failed: " + e.getMessage());
        }
    }
    /*
    @Test(priority = 2) 
    public void testPaymentFlowWithSpecificGuardian() {
        System.out.println("🚀 STARTING TEST: Payment Flow with Specific Guardian");

        try {
            // Login
            SuperLoginPage superloginPage = new SuperLoginPage(driver);
            superloginPage.enterEmail("mereg2025@gmail.com");
            superloginPage.enterPassword("SuperAdmin@123");
            superloginPage.clickSignIn();
            Thread.sleep(3000);

            // Navigate to Payment
            SuperPaymentPage paymentPage = new SuperPaymentPage(driver);
            paymentPage.clickOnPaymentPage();
            Thread.sleep(3000);

            // Select specific guardian and child
            SuperGuardianPaymentPage payment = new SuperGuardianPaymentPage(driver);
            payment.selectGuardianByName("Meghana Mandalapu");
            Thread.sleep(2000);
            
            payment.selectRandomChild();
            Thread.sleep(2000);
            
            // DEBUG and select custom amount
            payment.debugRadioButtons();
            payment.selectCustomAmount(); // CORRECT METHOD NAME
            payment.clickProceedButton();

            System.out.println("✅ TEST PASSED: Specific guardian payment completed");

        } catch (Exception e) {
            Assert.fail("❌ Specific guardian test failed: " + e.getMessage());
        }
    }
    */
}
