package SuperAdminTestcase;

import Base.BaseDriver;
import SuperAdminPage.SuperChildBackbuttonPage;
import SuperAdminPage.SuperGuardianPaymentPage;
import SuperAdminPage.SuperLoginPage;
import SuperAdminPage.SuperOnlinePaymentPage;
import SuperAdminPage.SuperPaymentPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class SuperChildBackbuttonTest extends BaseDriver {
 @Test(priority = 2) 
    public void testPaymentFlowWithSpecificGuardian() throws InterruptedException {
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
            payment.selectGuardianByName("Radhika CC");
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
        
        SuperChildBackbuttonPage onlinepayment1 = new SuperChildBackbuttonPage(driver);
        Thread.sleep(1000);
        onlinepayment1.clickbackButton();
    }
}
