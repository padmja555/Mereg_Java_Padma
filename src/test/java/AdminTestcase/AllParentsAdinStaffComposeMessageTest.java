package AdminTestcase;

import AdminPages.AMessagePage;
import AdminPages.AllParentsAdinStaffComposeMessagePage;
import AdminPages.LoginPage;
import Base.BaseDriver;

import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

public class AllParentsAdinStaffComposeMessageTest extends BaseDriver {

    AMessagePage message;
    AllParentsAdinStaffComposeMessagePage page;

    @BeforeMethod
    //@BeforeMethod
    public void setupTest() {

        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        message = new AMessagePage(driver);
        message.clickOnMessage();

        page = new AllParentsAdinStaffComposeMessagePage(driver);
        //page.selectAllParentsTab();
        page.clickNewMessage();
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        page.selectAllParentsTab();
    }

    @Test
    public void sendMessageWithAttachments() throws InterruptedException {

        page.selectPrimaryAllParents();
     // Thread.sleep(2000);

        //String[] additional = {"Parent", "Staff"};
       // page.selectAdditionalRecipients(additional);
        page.selectFirstSixAdditionalRecipients();
        Thread.sleep(2000);

        page.selectRandomMessageType();
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        page.enterRandomSubject();
        page.enterFormattedMessage();
        //page.uploadFilesUsingSystemDialog(
        page.uploadMultipleFiles(
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
        /*

        List<String> files = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            files.add(
              "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file" + i + ".png"
            );
        }*/
        Thread.sleep(2000);
        //page.uploadFiles(files);
        page.clickSendButton();
    }

    @AfterClass
    public void tearDown() {
        // driver.quit(); // enable when stable
    }
}
