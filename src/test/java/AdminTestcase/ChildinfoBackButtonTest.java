package AdminTestcase;

import Base.BaseDriver;
import AdminPages.ChildinfoBackButtonPage;
import AdminPages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class ChildinfoBackButtonTest extends BaseDriver {

    @Test
    public void verifyBackButtonFunctionality() throws InterruptedException {
        // 1. Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("srasysife@gmail.com");
        loginPage.enterPassword("Admin@123");
        loginPage.clickSignIn();

        // 2. Navigate to Child Info Page
        ChildinfoBackButtonPage childInfoPage = new ChildinfoBackButtonPage(driver);
        childInfoPage.clickOnenrollment();
        
        Thread.sleep(3000); // Wait for the child info page to load

        // Debug: Print all buttons to see what's available
        childInfoPage.debugPrintAllButtons();

        // 3. Click the "Back to Home" button
        childInfoPage.clickBackToHome();
        
        Thread.sleep(3000); // Wait for the dashboard to load

        // 4. Verify that the user is on the Dashboard
        boolean onDashboard = childInfoPage.isOnDashboard();
        Assert.assertTrue(onDashboard, "Failed to return to the Dashboard page after clicking 'Back to Home'.");
        
        System.out.println("Successfully verified 'Back to Home' button functionality.");
    }
}