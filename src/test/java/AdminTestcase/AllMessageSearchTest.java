package AdminTestcase;

import AdminPages.LoginPage;
import AdminPages.AllMessageSearchPage;
import Base.BaseDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AllMessageSearchTest extends BaseDriver {

    AllMessageSearchPage messagesPage;

    @BeforeMethod
    public void setup() {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        messagesPage = new AllMessageSearchPage(driver);
        messagesPage.clickOnMessageTab();
    }

    @Test
    public void replyToRandomMessageWithAttachment() {
        try {
            System.out.println("=== Starting Test ===");
            
            // 1️⃣ Wait for page to load
            Thread.sleep(3000);
            
            // 2️⃣ Search for a specific message
            messagesPage.searchMessageByText("Automation Subject 3cd12b");
            Thread.sleep(5000);
            
            // 3️⃣ Open first search result
            messagesPage.openFirstMessage();
            Thread.sleep(5000);
            
            // 4️⃣ Reply with attachment
            //String folderPath = "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots";
            //String folderPath = "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots";
            // 4️⃣ Reply with attachment
            String screenshotsFolder = "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\";

            messagesPage.replyWithAttachment(
                "Automation reply message",
                "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file6.png",
                "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file7.png",
                "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\Salary.pdf",
                "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\employee.pdf",
                "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\payments.xlsx"                  // This file exists (from PDF section)
            );
            
            System.out.println("✅ Test completed successfully");
            
        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}