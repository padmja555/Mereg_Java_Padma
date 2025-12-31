package AdminTestcase;

import Base.BaseDriver;

import AdminPages.LoginPage;
import AdminPages.ChildStatusPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class ChildStatusTest extends BaseDriver {

    @Test
    public void childStatusTest() throws InterruptedException {
        // Step 1: Login
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();
        
        ChildStatusPage childStatus = new ChildStatusPage(driver);
        childStatus.searchChildByName("Johnson");
        Thread.sleep(3000);
        childStatus.clickPendingButtonForChild("Johnson");
        Thread.sleep(3000);

        boolean isPendingDisplayed = childStatus.isPendingApprovalDisplayed();
        Thread.sleep(3000);
        Assert.assertTrue(isPendingDisplayed, "Pending Approval text not displayed");
        Thread.sleep(3000);

        childStatus.clickApproveRegistration();
      

        System.out.println("Child registration approval test completed.");
    }
}
