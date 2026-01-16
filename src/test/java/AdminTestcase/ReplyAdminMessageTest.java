
package AdminTestcase;

import AdminPages.LoginPage;
import AdminPages.ReplyAdminMessagePage;
import Base.BaseDriver;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ReplyAdminMessageTest extends BaseDriver {

	ReplyAdminMessagePage replyadminPage;

    @BeforeMethod
    public void setup() {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        replyadminPage = new ReplyAdminMessagePage(driver);
        replyadminPage.clickOnMessageTab();

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
    	replyadminPage.clickOnAdminTab();
        
    	replyadminPage.waitForMessageList();
        // 2️⃣ Open a random staff message
    	replyadminPage.openRandomMessage();

        // 3️⃣ Type a random reply
    	replyadminPage.typeRandomReply();   // ✅ type only

    	String folderPath =
    	        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots";

    	replyadminPage.uploadMultipleFilesFromSameFolder(
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




    	// Click Send Reply ONCE, after all uploads
    	replyadminPage.clickSendReply();

    	System.out.println("✅ Message sent with PNG + TXT + PDF attachments");
    }
}
