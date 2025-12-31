package AdminTestcase;

import Base.BaseDriver;
import AdminPages.TwoChildrenspage;
import AdminPages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class TwochildrensTest extends BaseDriver {

    @Test
    public void ChildInfo_AddTwoChildren() throws InterruptedException {
        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("srasysife@gmail.com");
        loginPage.enterPassword("Admin@123");
        loginPage.clickSignIn();

        // Step 2: Navigate to Child Info Page
        TwoChildrenspage twoChildPage = new TwoChildrenspage(driver);
        twoChildPage.clickOnenrollment();

        // Step 3: Enter details for first child (index 0)
        twoChildPage.enterRandomChildDetails(0);

        // Step 4: Add another child
        twoChildPage.addAnotherChild();

        // Step 5: Enter details for second child (index 1)
        twoChildPage.enterRandomChildDetails(1);

        // Step 6: Proceed to Guardian Info Page
        twoChildPage.ClickonProceedtoGuardian();

        // Step 7: Verification
        Assert.assertTrue(
            twoChildPage.isOnParentGuardianPage(),
            "Failed to navigate to Parent/Guardian Info page"
        );
        
        System.out.println("Successfully added two children and navigated to Parent/Guardian page.");
    }
}
