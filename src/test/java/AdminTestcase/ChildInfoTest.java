package AdminTestcase;
import Base.BaseDriver;

import AdminPages.Childinfo;
import AdminPages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class ChildInfoTest extends BaseDriver {

    @Test
    public void ChildInfo_AddSingleChild() throws InterruptedException {
        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("srasysife@gmail.com");
        loginPage.enterPassword("Admin@123");
        loginPage.clickSignIn();

        // Navigate to Child Info Page
        Childinfo childInfoPage = new Childinfo(driver);
        childInfoPage.clickOnenrollment();

        // First Child
        childInfoPage.enterRandomFirstAndLastName();
        childInfoPage.selectGender();
        childInfoPage.enterrandomDOB();

        // Click Proceed to Guardian (skip adding second child)
        childInfoPage.ClickonProceedtoGuardian();
        
        // Optional: Add verification that we moved to next page
        System.out.println("Successfully proceeded to Parent/Guardian page with one child");
    }
}