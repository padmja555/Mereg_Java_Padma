package AdminTestcase;

import Base.BaseDriver;
import AdminPages.LoginPage;
import AdminPages.Onlinecardpayment;
import AdminPages.PaymentSummaryPage;
import AdminPages.ChildPayment;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class PaymentChildrenBackbuttonTest extends BaseDriver {

    @Test
    public void Payment1Test() throws InterruptedException {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        ChildPayment payment1 = new ChildPayment(driver, wait); 

        payment1.clickOnPaymentPage();
        payment1.clickOnGurdian();
        Thread.sleep(3000);
        payment1.selectRandomChild();
        Thread.sleep(3000);
        payment1.selectRandomPaymentOptionAndEnterAmount("1000", "500");
        Thread.sleep(3000);
        payment1.Procedbuttonpayment();

        Onlinecardpayment summarypage = new Onlinecardpayment(driver, wait);
        String title = payment1.getpaymentpageTitleText();
        System.out.println("Payment Summary: " + title);
        Assert.assertEquals(title, "Payment Summary", "Payment Summary title mismatch");
        summarypage.clickbackButton();
    }
}


