package AdminTestcase;

import AdminPages.LoginPage;
import AdminPages.ReplyParentMessagePage;
import Base.BaseDriver;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ReplyParentMessageTest extends BaseDriver {

	ReplyParentMessagePage replyparentPage;

    @BeforeMethod
    public void setup() {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        replyparentPage = new ReplyParentMessagePage(driver);
        replyparentPage.clickOnMessageTab();

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
        // 1️⃣ Click the Staff tab properly
    	replyparentPage.clickOnParentTab();
        
    	replyparentPage.waitForMessageList();
        // 2️⃣ Open a random staff message
    	replyparentPage.openRandomMessage();

        // 3️⃣ Type a random reply
    	replyparentPage.typeRandomReply();
    	    String folderPath = 
    	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots";

    	    replyparentPage.uploadMultipleFilesFromSameFolder(
    	    	    folderPath,
    	    	    "file1.png",
    	    	    "file2.png",
    	    	    "file3.png",
    	    	    "file10 (2).txt",
    	    	    "file10 (3).txt",
    	    	    "Student-Enrollment-Form.pdf",
    	    	    "employeeList .pdf",  // ⚠️ SPACE INCLUDED
    	    	    "Salary.pdf"
    	    	);

    	    replyparentPage.clickSendReply();

    	    System.out.println("✅ Message sent with PNG + TXT + PDF attachments");
    	}


        // 4️⃣ Upload multiple attachments
}
