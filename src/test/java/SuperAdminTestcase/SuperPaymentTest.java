package SuperAdminTestcase;

import Base.BaseDriver;
import SuperAdminPage.SuperLoginPage;
import SuperAdminPage.SuperPaymentPage;
import AdminPages.LoginPage;
import AdminPages.Paymentpage;
import AdminPages.reportpage;

import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class SuperPaymentTest extends BaseDriver {

    @Test
    public void PaymentTest() throws InterruptedException {
        SuperLoginPage superloginPage = new SuperLoginPage(driver);
        superloginPage.enterEmail("mereg2025@gmail.com");
        superloginPage.enterPassword("SuperAdmin@123");
        superloginPage.clickSignIn();
        System.out.println("Login successful");


        SuperPaymentPage payment = new SuperPaymentPage(driver);
        Thread.sleep(2000);
        try {
			payment.clickOnPaymentPage();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        String title = payment.getpaymentpageTitleText();
        System.out.println("Payment Title: " + title);
        Assert.assertEquals(title, "Payment", "Payment page title mismatch");
    }
}
