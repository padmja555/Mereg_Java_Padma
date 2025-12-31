package AdminTestcase;

import Base.BaseDriver;
import AdminPages.LoginPage;
import AdminPages.ApproveStatusReject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class ApproveStatusRejectTest extends BaseDriver {

    @Test
    public void testRejectRegistration() throws InterruptedException {
        // Assume test data setup is done outside this method to have a pending registration
        // For example: A child named "Verma" with a Pending status
        final String CHILD_NAME = "Jasthi";
        final String REJECTION_REASON = "Incomplete documentation provided";

        // Step 1: Login
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();
        Thread.sleep(5000);

        // Step 2: Search for the child
        ApproveStatusReject statusPage = new ApproveStatusReject(driver);
        statusPage.searchChildByName(CHILD_NAME);
        Thread.sleep(3000);
        
        // Step 3: Click the pending button for the child
        // This is the step that will fail if no pending child exists
        statusPage.clickPendingButtonForChild(CHILD_NAME);
        Thread.sleep(3000);

        // Step 4: Verify pending approval status on the details page
        boolean isPendingDisplayed = statusPage.isPendingApprovalDisplayed();
        Assert.assertTrue(isPendingDisplayed, "Pending Approval text not displayed on the details page.");
        Thread.sleep(3000);

        // Step 5: Enter the reason for rejection
        statusPage.enterRejectReason(REJECTION_REASON);
        Thread.sleep(2000);

        // Step 6: Click the reject registration button
        statusPage.clickRejectRegistration();
        Thread.sleep(3000);

        // Step 7: Verify successful rejection message
        boolean isRejected = statusPage.isSuccessfullyRejected();
        Assert.assertTrue(isRejected, "Child registration was not successfully rejected.");

        System.out.println("Child registration rejection test completed successfully.");
    }
}