package AdminTestcase;

import AdminPages.LoginPage;
import AdminPages.AllMessagedropdownPage;
import Base.BaseDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AllMessagedropdownTest extends BaseDriver {

    AllMessagedropdownPage replyPages;

    @BeforeMethod
    public void setup() {

        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        replyPages = new AllMessagedropdownPage(driver);
        replyPages.clickOnMessageTab();
    }

    @Test
    public void replyToRandomMessageWithAttachment() {

        // ✅ FIXED: method call
        replyPages.selectRandomFilterOption();

        replyPages.openRandomMessage();
        replyPages.typeRandomReply();

        try {
            replyPages.uploadMultipleFiles(
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

        replyPages.clickSendReply();
    }
}
