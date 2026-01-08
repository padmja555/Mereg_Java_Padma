package AdminTestcase;

import AdminPages.LoginPage;
import AdminPages.ReplyMessagePage;
import Base.BaseDriver;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ReplyMessageTest extends BaseDriver {

    ReplyMessagePage replyPage;

    @BeforeMethod
    public void setup() {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        replyPage = new ReplyMessagePage(driver);
        replyPage.clickOnMessageTab();
    }
/*
    @Test
    public void verifyMessagesPageTitle() {
        String title = replyPage.getMessageTitleText();
        Assert.assertEquals(title, "Messages", "❌ Message title mismatch");
    }
    */

    @Test
    public void replyToRandomMessageWithAttachment() {
        replyPage.openRandomMessage();
        replyPage.typeRandomReply();
        //replyPage.uploadRandomFile();
      
       	try {
       		replyPage.uploadMultipleFiles(
    	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file1.png",
    	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file2.png",
    	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file3.png",
    	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file4.png",
    	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file5.png",
    	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file6.png",
    	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file7.png",
    	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file8.png",
    	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file9.png"
    	    );
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}


        replyPage.clickSendReply();
    }
}
