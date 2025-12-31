package SuperAdminTestcase;

import Base.BaseDriver;
import SuperAdminPage.SuperEnrollmentPage;
import SuperAdminPage.SuperLoginPage;
import AdminPages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class SuperEnrollmentTest extends BaseDriver {

    @Test
    public void verifyEnrollmentPageNavigation() throws InterruptedException {
        // Login
        SuperLoginPage superloginPage = new SuperLoginPage(driver);
        superloginPage.enterEmail("srasysife@gmail.com");
        superloginPage.enterPassword("Admin@123");
        superloginPage.clickSignIn();
        System.out.println("Login successful");

        // Navigate to Enrollment Page
        SuperEnrollmentPage superenrollmentPage = new SuperEnrollmentPage(driver);
        superenrollmentPage.clickOnEnrollment();
        System.out.println("Clicked on Enrollment link");

        // Verify Enrollment Page Title
        String actualTitle = superenrollmentPage.getEnrollmentTitleText();
        String expectedTitle = "MeReg Workspace";   // Move to constant/config if possible
        Assert.assertEquals(actualTitle, expectedTitle, 
            "Enrollment page title did not match!");
        System.out.println("Verified Enrollment page title successfully");
    }
}
