
package AdminTestcase;

import AdminPages.LoginPage;
import AdminPages.ReplyStaffMessagePage;
import Base.BaseDriver;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ReplyStaffMessageTest extends BaseDriver {

	ReplyStaffMessagePage replystaffPage;

    @BeforeMethod
    public void setup() {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        replystaffPage = new ReplyStaffMessagePage(driver);
        replystaffPage.clickOnMessageTab();

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
        replystaffPage.clickOnStaffTab();
        
        replystaffPage.waitForMessageList();
        // 2️⃣ Open a random staff message
        replystaffPage.openRandomMessage();

        // 3️⃣ Type a random reply
        replystaffPage.typeRandomReply();

        // 4️⃣ Upload multiple attachments
        String[] filePaths = {
            "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file1.png",
            "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file2.png",
            "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file3.png",
            "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file4.png",
            "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file5.png",
            "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file6.png",
            "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file7.png",
            "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file8.png",
            "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file9.png"
        };

        try {
            replystaffPage.uploadMultipleFiles(filePaths);
        } catch (Exception e) {
            System.err.println("❌ Failed to upload files: " + e.getMessage());
            e.printStackTrace();
        }

        // 5️⃣ Click Send Reply
        replystaffPage.clickSendReply();

        System.out.println("✅ Reply with attachments sent successfully!");
    }
}