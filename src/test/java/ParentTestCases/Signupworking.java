package ParentTestCases;

import Base.BaseDriver;
import AdminPages.ForgotPasswordPage;
import AdminPages.LoginPage;
import utils.EmailUtil;
import org.testng.annotations.Test;

public class ForgotPasswordTest extends BaseDriver {
    private static final String EMAIL = "padmavathi555divyakolu@gmail.com";
    private static final String EMAIL_PASSWORD = "qizk fsjc dvam ajha";
    private static final String EMAIL_SUBJECT = "MeReg - Email Verification OTP";

    @Test
    public void testForgotPasswordWithOTP() throws Exception {
        try {
            System.out.println("=== Starting Forgot Password Test ===");
            
            // 1. Initialize Login Page
            System.out.println("Initializing login page...");
            LoginPage loginPage = new LoginPage(driver);
            
            // 2. Navigate to Forgot Password Page
            System.out.println("Navigating to forgot password page...");
            ForgotPasswordPage forgotPasswordPage = loginPage.clickForgotPassword();
            
            // Verify page object
            if (forgotPasswordPage == null) {
                throw new RuntimeException("ForgotPasswordPage initialization failed");
            }
            
            // 3. Enter Email
            System.out.println("Entering email address...");
            forgotPasswordPage.enterEmail(EMAIL);
            
            // 4. Send OTP
            System.out.println("Requesting OTP...");
            forgotPasswordPage.clickSendOtp();
            
            // 5. Wait for Email
            System.out.println("Waiting 20 seconds for OTP email...");
            Thread.sleep(20000);
            
            // 6. Retrieve OTP
            System.out.println("Retrieving OTP from email...");
            String otp = EmailUtil.fetchOTPFromEmail(EMAIL, EMAIL_PASSWORD, EMAIL_SUBJECT);
            System.out.println("OTP retrieved: " + otp);
            
            System.out.println("=== Test Completed Successfully ===");
            
        } catch (Exception e) {
            System.err.println("!!! TEST FAILED !!!");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}