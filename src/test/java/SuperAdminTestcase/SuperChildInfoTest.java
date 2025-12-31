package SuperAdminTestcase;

import Base.BaseDriver;
import SuperAdminPage.SuperChildInfoPage;
import SuperAdminPage.SuperEnrollmentPage;
import SuperAdminPage.SuperLoginPage;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class SuperChildInfoTest extends BaseDriver {

    @Test
    public void ChildInfo_AddSingleChild() throws InterruptedException {
        // Login
        SuperLoginPage superloginPage = new SuperLoginPage(driver);
        superloginPage.enterEmail("mereg2025@gmail.com");
        superloginPage.enterPassword("SuperAdmin@123");
        superloginPage.clickSignIn();
        System.out.println("Login successful");

        // Navigate to Enrollment Page
        SuperEnrollmentPage superenrollmentPage = new SuperEnrollmentPage(driver);
        superenrollmentPage.clickOnEnrollment();
        System.out.println("Clicked on Enrollment link");

        // Verify Enrollment Page Title
        //String actualTitle = superenrollmentPage.getEnrollmentTitleText();

;
        // Navigate to Child Info Page
		SuperChildInfoPage superchildPage = new SuperChildInfoPage(driver);
		superchildPage.enterRandomFirstAndLastName();
		superchildPage.selectGender();
		superchildPage.enterrandomDOB();
		superchildPage.ClickonProceedtoGuardian();
        // Optional: Add verification that we moved to next page
        System.out.println("Successfully proceeded to Enrollment  Parent/Guardian page with one child");




}
}

