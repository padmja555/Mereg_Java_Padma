
package AdminTestcase;

import AdminPages.AMessagePage;
import AdminPages.IndidualStaffMessagePage;
import AdminPages.ComposeMessagePage;
import AdminPages.LoginPage;
import Base.BaseDriver;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



    public class IndidualStaffMessageTest extends BaseDriver {

        AMessagePage message;
        IndidualStaffMessagePage composePage;

        @BeforeMethod
        public void setupTest() {

            LoginPage login = new LoginPage(driver);
            login.enterEmail("srasysife@gmail.com");
            login.enterPassword("Admin@123");
            login.clickSignIn();

            message = new AMessagePage(driver);
            message.clickOnMessage();

            composePage = new IndidualStaffMessagePage(driver);
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
            
            composePage.selectRandomRecipients(4); 
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
        	    composePage.uploadMultipleFiles(
        	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file1.png",
        	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file2.png",
        	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file3.png",
        	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file4.png",
        	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file5.png",
        	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file6.png",
        	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file7.png",
        	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file8.png",
        	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file9.png",
        	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\Students List.pdf"
        	    );
        	} catch (Exception e) {
        	    e.printStackTrace();
        	}
        	 try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            composePage.clickSendMessage();
        }
    }

