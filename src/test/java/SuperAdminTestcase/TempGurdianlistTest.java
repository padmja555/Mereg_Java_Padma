package SuperAdminTestcase;

import Base.BaseDriver;
import SuperAdminPage.SuperLoginPage;
import SuperAdminPage.SuperPaymentPage;
import SuperAdminPage.SuperSelectGuardianPage;
import SuperAdminPage.TempGurdianlistPage;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class TempGurdianlistTest extends BaseDriver {

    @Test(priority = 1)
    public void testGuardianChildAndAmountFlow() {
        System.out.println("🚀 START TEST: Guardian-Child-Amount Flow");

        try {
            // Step 1: Login
            SuperLoginPage loginPage = new SuperLoginPage(driver);
            loginPage.enterEmail("mereg2025@gmail.com");
            loginPage.enterPassword("SuperAdmin@123");
            loginPage.clickSignIn();
            System.out.println("✅ Logged in successfully");
            Thread.sleep(3000);

            // Step 2: Navigate to Payment Page
            SuperPaymentPage paymentPage = new SuperPaymentPage(driver);
            paymentPage.clickOnPaymentPage();
            System.out.println("✅ Navigated to Payment Page");
            Thread.sleep(3000);

            // Step 3: Select guardian, child, enter amount, proceed
            TempGurdianlistPage guardianli = new TempGurdianlistPage(driver);
            guardianli.selectGuardianAndChildWithValidAmount();
            Thread.sleep(1000);
            guardianli.selectRandomGuardian();
            guardianli.selectRandomChild();
            guardianli.getAmountFromField();
            guardianli.clickProceedToPayment();

            System.out.println("TEST PASSED: Guardian payment flow executed successfully");

        } catch (Exception e) {
            System.err.println("❌ TEST FAILED: " + e.getMessage());
            Assert.fail("❌ Guardian payment flow failed: " + e.getMessage());
        }
    }
}
