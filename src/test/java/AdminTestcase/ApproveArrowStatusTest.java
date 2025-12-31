
package AdminTestcase;

import Base.BaseDriver;

import AdminPages.LoginPage;
import AdminPages.ApproveArrowStatusPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
@Listeners(listeners.TestListener.class)

public class ApproveArrowStatusTest extends BaseDriver {

    @Test
    public void ApproveArrowStatusTest() throws InterruptedException {
        // Step 1: Login
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        // Wait for login to complete
        Thread.sleep(5000);

        // Step 2: Navigate to Dashboard and search
        ApproveArrowStatusPage approvePage = new ApproveArrowStatusPage(driver);
        approvePage.navigateToDashboard();
        Thread.sleep(5000); // Wait for dashboard to load
        
        approvePage.clickPaginationArrow();
        Thread.sleep(4000); // Wait for pagination
        
        approvePage.searchChildByName("Mehta");
        Thread.sleep(4000); // Wait for search results
        
        // Step 3: Click the pending button for the child
        approvePage.clickPendingButtonForChild("Mehta");

        // Step 4: Verify "Pending Approval" status
        boolean isPendingDisplayed = approvePage.isPendingApprovalDisplayed();
        Assert.assertTrue(isPendingDisplayed, "Pending Approval text not displayed");
        
        // Step 5: Click the approve button
        approvePage.clickApproveRegistration();
        Thread.sleep(4000); // Wait for approval process
        
        // Step 6: Verify successful approval
        boolean isApproved = approvePage.isSuccessfullyApproved();
        Assert.assertTrue(isApproved, "Child registration was not successfully approved.");

        System.out.println("Child registration approval test completed.");
    }
}