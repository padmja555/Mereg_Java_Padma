package AdminTestcase;

import AdminPages.AMessagePage;
import AdminPages.AllParentComposeMeesagePage;
import AdminPages.ComposeMessagePage;
import AdminPages.LoginPage;
import Base.BaseDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AllParentComposeMessageTest extends BaseDriver {

	AMessagePage message;
    AllParentComposeMeesagePage composePage;

    @BeforeMethod
    public void setupTest() {

        // Login
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        // Navigate to Messages page
        message = new AMessagePage(driver);
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        message.clickOnMessage();

        // Initialize compose page
        composePage = new AllParentComposeMeesagePage(driver);
    }

    @Test
    public void sendAllParentsMessageWithRandomStaffCC() {

        composePage.openComposeMessage();
        composePage.selectAllParentsTab();
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        composePage.selectPrimaryAllParents();
        composePage.selectRandomStaffCC();
        composePage.selectRandomMessageType();
        composePage.enterRandomSubjectAndMessage();
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        composePage.clickSendMessage();
    }
}
