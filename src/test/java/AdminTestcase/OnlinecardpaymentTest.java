package AdminTestcase;

import AdminPages.LoginPage;
import AdminPages.ChildPayment;
import AdminPages.Onlinecardpayment;
import AdminPages.Paymentpage;
import Base.BaseDriver;
import org.testng.annotations.Test;

public class OnlinecardpaymentTest extends BaseDriver {

    @Test
    public void onlineCardPaymentFlowTest() throws InterruptedException {
        // 1. Login
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();
        
        // Wait for dashboard to load
        Thread.sleep(3000); 
        //2. payment
        Paymentpage paymentpage = new Paymentpage(driver);
        paymentpage.clickOnPaymentPage();
        paymentpage.getpaymentpageTitleText();

        // 2. Navigate to Payment and select child information      
        ChildPayment childPayment = new ChildPayment(driver, wait);
        //childPayment.clickOnPaymentPage();
        //childPayment.getpaymentpageTitleText();
        Thread.sleep(3000);
        childPayment.clickOnGurdian();
        Thread.sleep(2000);
        childPayment.selectRandomChild();
        Thread.sleep(3000);
        
        // FIX: Pass String arguments by converting the integers
        childPayment.selectRandomPaymentOptionAndEnterAmount("500", "500");
        childPayment.Procedbuttonpayment();
        
        // Wait for the Payment Summary page to load
        Thread.sleep(3000); 

        // 3. Verify Payment Summary and click Pay Securely button          
        Onlinecardpayment onlineCardPaymentPage = new Onlinecardpayment(driver, wait);
        onlineCardPaymentPage.verifyPaymentSummaryPage();    
        onlineCardPaymentPage.clickPaySecurelyButton();
        
        // The test case ends here, without completing the payment or verification steps.
    }
}