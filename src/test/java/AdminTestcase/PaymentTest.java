package AdminTestcase;

import Base.BaseDriver;
import AdminPages.LoginPage;
import AdminPages.Paymentpage;
import AdminPages.reportpage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class PaymentTest extends BaseDriver {

    @Test
    public void PaymentTest() throws InterruptedException {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        Paymentpage payment = new Paymentpage(driver);
        Thread.sleep(5000);
        payment.clickOnPaymentPage();

        String title = payment.getpaymentpageTitleText();
        System.out.println("Payment Title: " + title);
        Assert.assertEquals(title, "Payment", "Payment page title mismatch");
    }
}