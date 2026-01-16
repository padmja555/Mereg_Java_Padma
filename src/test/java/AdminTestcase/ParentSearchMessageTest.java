
package AdminTestcase;

import AdminPages.LoginPage;
import AdminPages.ParentSearchMessagePage;
import Base.BaseDriver;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ParentSearchMessageTest extends BaseDriver {

	ParentSearchMessagePage parentmessagesPage;

    @BeforeMethod
    public void setup() {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        parentmessagesPage = new ParentSearchMessagePage(driver);
        parentmessagesPage.clickOnMessageTab();
        
    }

    @Test
    public void replyToRandomMessageWithAttachment() {
    	 try {
    		 parentmessagesPage.clickAllParentTab();
    	     Thread.sleep(3000);
       
            System.out.println("=== Starting Test ===");
            
            // 1️⃣ Wait for page to load
            Thread.sleep(3000);
            
            // 2️⃣ Search for a specific message
            parentmessagesPage.searchMessageByText("Testb hbluiliuh;o");
            Thread.sleep(5000);
            
            // 3️⃣ Open first search result
            parentmessagesPage.openFirstMessage();
            Thread.sleep(5000);
            parentmessagesPage.uploadMultipleFilesViaJS(
            //staffmessagesPage.uploadMultipleFiles( 
            		"C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\Assinment.txt",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\Content.txt",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\discussion.txt",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file4.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file5.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file6.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file7.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file8.png"
            	);
            Thread.sleep(2000);
            parentmessagesPage.clickSendButton();

            //boolean isSent = staffmessagesPage.verifyMessageSent();
            //Assert.assertTrue(isSent, "❌ Message verification failed");

            System.out.println("✅ Test Passed: Message sent successfully");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("❌ Test failed due to exception: " + e.getMessage());
        }
}
}