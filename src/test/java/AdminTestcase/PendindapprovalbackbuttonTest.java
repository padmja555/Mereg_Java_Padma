package AdminTestcase;

import Base.BaseDriver;
import AdminPages.LoginPage;
import AdminPages.PendindapprovalbackbuttonPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class PendindapprovalbackbuttonTest extends BaseDriver {

    @Test
    public void backToDashboardTest() throws InterruptedException {
        // Step 1: Login
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        // Step 2: Search for the child and click the Pending button
        PendindapprovalbackbuttonPage pendingPage = new PendindapprovalbackbuttonPage(driver);
        pendingPage.searchChildByName("Johnson");
        Thread.sleep(3000); // Wait for search results
        pendingPage.clickPendingButtonForChild("Johnson");
        Thread.sleep(3000); // Wait for the details page to load

        // Step 3: Verify the "Pending Approval" page is displayed
        boolean isPendingDisplayed = pendingPage.isPendingApprovalDisplayed();
        Assert.assertTrue(isPendingDisplayed, "Pending Approval text not displayed");
        Thread.sleep(3000);

        // Step 4: Click the "Back to Dashboard" button
        pendingPage.clickBackToDashboard();
        Thread.sleep(3000); // Wait for navigation back

        // Step 5: Verify navigation back to the dashboard by checking for a dashboard element
        boolean isDashboardVisible = driver.getCurrentUrl().endsWith("/dashboard"); // Simple URL check
        Assert.assertTrue(isDashboardVisible, "Failed to navigate back to the dashboard.");

        System.out.println("Child registration 'Back to Dashboard' test completed successfully.");
    }
}