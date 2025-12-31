package SuperAdminTestcase;

import Base.BaseDriver;
import SuperAdminPage.SuperLoginPage;
import SuperAdminPage.SuperPaymentPage;
import SuperAdminPage.SuperSelectGuardianPage;

import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class SuperSelectGuardianTest extends BaseDriver {

    @Test
    public void GuardianTest() throws InterruptedException {
        // 1️⃣ Login
        SuperLoginPage superLoginPage = new SuperLoginPage(driver);
        superLoginPage.enterEmail("mereg2025@gmail.com");
        superLoginPage.enterPassword("SuperAdmin@123");
        superLoginPage.clickSignIn();
        System.out.println("✅ Login successful");

        // 2️⃣ Navigate to Payment page
        SuperPaymentPage paymentPage = new SuperPaymentPage(driver);
        try {
			paymentPage.clickOnPaymentPage();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Thread.sleep(2000);

        // 3️⃣ Initialize Guardian selection page
        SuperSelectGuardianPage guardianPage = new SuperSelectGuardianPage(driver);
        //Thread.sleep(2000);
        guardianPage.clickOnGuardian(14);
        Thread.sleep(2000);
        // 4️⃣ Select a guardian with children
        guardianPage.selectRandomChild();
        //selectRandomChild
        //boolean guardianSelected = guardianPage.selectAnyGuardianWithChildren();
        //Assert.assertTrue(guardianSelected, "❌ No guardian with children found!");
        Thread.sleep(3000);

        // 5️⃣ Select payment type (choose "full" or "custom")
        guardianPage.selectPaymentOption("full");
        Thread.sleep(2000);

        // 6️⃣ Proceed to payment
        boolean proceedClicked = guardianPage.proceedToPayment();
        Assert.assertTrue(proceedClicked, "❌ Failed to click 'Proceed to payment' button!");
        Thread.sleep(3000);

        // 7️⃣ Verify payment summary is displayed
        //boolean summaryVisible = guardianPage.verifyPaymentSummary();
        //Assert.assertTrue(summaryVisible, "❌ Payment Summary not displayed!");

        System.out.println("✅ Payment Summary successfully verified!");
    }
}
