package AdminTestcase;


import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import AdminPages.CreateAccountPage;
import AdminPages.ForgotPasswordPage;
import AdminPages.CreateAccountPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

@Listeners(listeners.TestListener.class)
public class CreateAccountTest {
    private WebDriver driver;
    //private AdminPages creataccount;;
    CreateAccountPage Account;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications", "--start-maximized");
        driver = new ChromeDriver(options);
        driver.get("https://mereg.netlify.app/");
        Account= new CreateAccountPage(driver);
    }

    @Test
    public void testAccountRegistrationWithOTP() throws Exception {
        // Navigate to Create Account
    	Account.clickCreateAccountLink();

        // Fill form
    	Account.fillRegistrationForm();

        // Submit registration
    	Account.submitRegistrationForm();

        // Wait & fetch OTP
        String otp = Account.fetchMeRegOTPWithRetry(3, 15000);
        System.out.println("Fetched OTP: " + otp);

        // Enter OTP
        Account.enterOTP(otp);

        // Verify account
        Account.clickVerifyButton();

        // Add assertion on success URL or message
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(d -> d.getCurrentUrl().contains("success"));

        System.out.println(" Account creation successful!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
