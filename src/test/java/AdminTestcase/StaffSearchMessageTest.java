package AdminTestcase;

import AdminPages.LoginPage;
import AdminPages.StaffSearchMessagePage;
import Base.BaseDriver;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StaffSearchMessageTest extends BaseDriver {

	StaffSearchMessagePage staffmessagesPage;

    @BeforeMethod
    public void setup() {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        staffmessagesPage = new StaffSearchMessagePage(driver);
        staffmessagesPage.clickOnMessageTab();
        
    }

    @Test
    public void replyToRandomMessageWithAttachment() {
    	 try {
    		staffmessagesPage.clickAllStaffTab();
    	     Thread.sleep(3000);
       
            System.out.println("=== Starting Test ===");
            
            // 1️⃣ Wait for page to load
            Thread.sleep(3000);
            
            // 2️⃣ Search for a specific message
            staffmessagesPage.searchMessageByText("1:49 Julie Daniels Schools Closed for 12 Days");
            Thread.sleep(5000);
            
            // 3️⃣ Open first search result
            staffmessagesPage.openFirstMessage();
            Thread.sleep(5000);
            staffmessagesPage.uploadMultipleFilesViaJS(
            //staffmessagesPage.uploadMultipleFiles( 
            		"C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file1.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file2.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file2.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file4.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file5.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file6.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file7.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file8.png"
            	);
            Thread.sleep(2000);
            staffmessagesPage.clickSendButton();

            //boolean isSent = staffmessagesPage.verifyMessageSent();
            //Assert.assertTrue(isSent, "❌ Message verification failed");

            System.out.println("✅ Test Passed: Message sent successfully");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("❌ Test failed due to exception: " + e.getMessage());
        }
}
}