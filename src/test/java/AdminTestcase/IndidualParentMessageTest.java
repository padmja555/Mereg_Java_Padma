package AdminTestcase;

import AdminPages.AMessagePage;
import AdminPages.IndidualParentMessagePage;
import AdminPages.ComposeMessagePage;
import AdminPages.LoginPage;
import Base.BaseDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



    public class IndidualParentMessageTest extends BaseDriver {

        AMessagePage message;
        IndidualParentMessagePage composePage;

        @BeforeMethod
        public void setupTest() {

            LoginPage login = new LoginPage(driver);
            login.enterEmail("srasysife@gmail.com");
            login.enterPassword("Admin@123");
            login.clickSignIn();

            message = new AMessagePage(driver);
            message.clickOnMessage();

            composePage = new IndidualParentMessagePage(driver);
        }

        @Test
        public void sendAllIndidualMessageWithRandomStaffCC() {

            composePage.openComposeMessage();
            composePage.selectAllIndidualTab();
            composePage.selectThreeCategories(); 
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            composePage.selectRandomRecipients(8); 
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            composePage.selectRandomMessageType();
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            composePage.enterRandomSubjectAndMessage();
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	try {
				composePage.uploadFileWithDialog();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            composePage.clickSendMessage();
        }
    }

