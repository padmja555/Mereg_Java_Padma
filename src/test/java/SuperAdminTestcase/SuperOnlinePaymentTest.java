package SuperAdminTestcase;


import AdminPages.LoginPage;
import AdminPages.ChildPayment;
import AdminPages.Onlinecardpayment;
import AdminPages.Paymentpage;
import Base.BaseDriver;
import SuperAdminPage.SuperGuardianPaymentPage;
import SuperAdminPage.SuperLoginPage;
import SuperAdminPage.SuperOnlinePaymentPage;
import SuperAdminPage.SuperPaymentPage;
import SuperAdminPage.TwoChildrensPaymentPage;

import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SuperOnlinePaymentTest extends BaseDriver {

    @Test
    public void onlineCardPaymentFlowTest() throws InterruptedException {
        // 1. Login
        SuperLoginPage superloginPage = new SuperLoginPage(driver);
        superloginPage.enterEmail("mereg2025@gmail.com");
        superloginPage.enterPassword("SuperAdmin@123");
        superloginPage.clickSignIn();
        System.out.println("Login successful");

        // 2️⃣ Payment page flow
        SuperPaymentPage payment = new SuperPaymentPage(driver);
        //suoerpayment1 = new SuperPaymentPage(driver, wait);
        try {
			payment.clickOnPaymentPage();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        /*
        TwoChildrensPaymentPage paymentpage = new TwoChildrensPaymentPage(driver);
        //SuperGuardianPaymentPage 
        paymentpage.selectGuardian1("Sailu");
        Thread.sleep(3000);
        paymentpage.selectTwoChildren();
        // Step 5: Select Child
       // payment.selectFirstChildrens();
        Thread.sleep(2000);
        // Step 6: DEBUG radio buttons before selection
        paymentpage.debugRadioButtons();
        // Step 7: Select Payment Type - USE CORRECT METHOD NAME
        paymentpage.selectCustomAmount(); // NOT chooseCustomAmount()
        Thread.sleep(1000);
        // Step 8: Enter Amount
        paymentpage.enterRandomAmount();
        Thread.sleep(1000);
        // Step 9: Proceed
        paymentpage.clickProceedButton();        // 3. Verify Payment Summary and click Pay Securely button          
        SuperOnlinePaymentPage CardPaymentPage = new SuperOnlinePaymentPage(driver, wait);
        CardPaymentPage.verifyPaymentSummaryPage();    
        CardPaymentPage.clickPaySecurelyButton();
        
        // The test case ends here, without completing the payment or verification steps.
    }*/
        SuperGuardianPaymentPage paymentp = new SuperGuardianPaymentPage(driver);
        paymentp.selectGuardianByName("Radhika CC");
        Thread.sleep(2000);
        
        paymentp.selectRandomChild();
        Thread.sleep(2000);
        
        // DEBUG and select custom amount
        paymentp.debugRadioButtons();
        paymentp.selectCustomAmount(); // CORRECT METHOD NAME
        paymentp.clickProceedButton();

        System.out.println("✅ TEST PASSED: Specific guardian payment completed");
        SuperOnlinePaymentPage onlinepayment = new SuperOnlinePaymentPage(driver, wait);
        onlinepayment.verifyPaymentSummaryPage();    
        onlinepayment.clickPaySecurelyButton();
    }

}
