package AdminTestcase;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import AdminPages.ForgotPasswordPage;

//import PageObject.adminpage1;
import io.github.bonigarcia.wdm.WebDriverManager;
import AdminPages.Childinfo;
import AdminPages.ForgotPasswordPage;

@Listeners(listeners.TestListener.class)
public class ForgetpasswordTest {

    //private static final ForgotPasswordPage ForgotPasswordPage1;
	WebDriver driver;
    //adminpage1 adminPage;
	ForgotPasswordPage ForgotPasswordPage1;
    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.get("https://mereg.netlify.app/");
        //adminPage = new adminpage1(driver);
        ForgotPasswordPage Forgetpasswoedpa = new ForgotPasswordPage(driver);
       // Forgetpasswoedpa.clickOnEnrollment();
    }

    @Test
    public void forgotPasswordFlow() throws Exception {
        // Click "Forgot Password?"
    	ForgotPasswordPage1.clickForgotPassword();

        // Enter email
    	ForgotPasswordPage1.enterEmail("tohiw33350@namestal.com");

        // Click Send OTP
    	ForgotPasswordPage1.clickSendOtp();

        // Wait for OTP email
        Thread.sleep(8000);

        // Fetch OTP from Gmail
        String otp = ForgotPasswordPage1.getOTPFromGmail();
        System.out.println("Fetched OTP: " + otp);

        // Enter OTP
        ForgotPasswordPage1.enterOtp(otp);

        // Verify OTP
        ForgotPasswordPage1.clickVerifyOtp();
        Thread.sleep(2000);

        // Enter new password
        ForgotPasswordPage1.enterNewPassword("Testing$123");

        // Enter confirm password
        ForgotPasswordPage1.enterConfirmPassword("Testing$123");

        // Reset Password
        ForgotPasswordPage1.clickResetPassword();

        Thread.sleep(9000);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}