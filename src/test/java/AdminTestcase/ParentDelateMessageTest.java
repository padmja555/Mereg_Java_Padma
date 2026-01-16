package AdminTestcase;

import AdminPages.LoginPage;
import AdminPages.ParentDelateMessagePage;
import Base.BaseDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ParentDelateMessageTest extends BaseDriver {

	ParentDelateMessagePage checkparentDeletePage;

    @BeforeMethod
    public void setup() {
        try {
            LoginPage login = new LoginPage(driver);
            login.enterEmail("srasysife@gmail.com");
            login.enterPassword("Admin@123");
            login.clickSignIn();
            Thread.sleep(3000);
            
            checkparentDeletePage = new ParentDelateMessagePage(driver);
            checkparentDeletePage.clickOnMessageTab();
            checkparentDeletePage.clickParentTab();
            
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Setup failed: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteAllMessages() {
        try {
            System.out.println("\n=== Test: Delete All Messages ===");
            
            // 1. Open three dots menu and enable checkboxes
            System.out.println("Step 1: Opening three dots menu...");
            checkparentDeletePage.openThreeDotsMenu();
            checkparentDeletePage.clickSelectChatOption();
            Thread.sleep(2000);
            
            // 2. Cancel any existing selections
            System.out.println("Step 2: Canceling checkboxes...");
            checkparentDeletePage.cancelAllCheckboxes();
            Thread.sleep(2000);
            
            // 3. Select ALL messages
            System.out.println("Step 3: Selecting all messages...");
            checkparentDeletePage.selectAllMessages();
            Thread.sleep(2000);
            
            // Verify selection
            int selectedCount = checkparentDeletePage.getSelectedMessageCount();
            System.out.println("Messages selected: " + selectedCount);
            
            // If still 0, try manual workaround
            if (selectedCount == 0) {
                System.out.println("Trying alternative selection method...");
                // Try clicking a few checkboxes manually
                // You might need to manually select at least one
            }
            
            Assert.assertTrue(selectedCount > 0, "❌ Should have at least 1 message selected");
            
            // 4. Delete selected messages
            System.out.println("Step 4: Deleting messages...");
            checkparentDeletePage.deleteSelectedMessages();
            
            // 5. Take screenshot after deletion
            System.out.println("Step 5: Taking screenshot...");
            checkparentDeletePage.takeScreenshot("after_deletion");
            
            System.out.println("✅ Test completed");
            
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("❌ Test failed due to exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testSelectRandomMessageAndDelete() {
        try {
            System.out.println("\n=== Test: Select Random Message -> Delete ===");
            
            // Debug page first
            checkparentDeletePage.debugPage();
            
            // 1. Open three dots menu and enable checkboxes
            System.out.println("Step 1: Enabling checkboxes...");
            checkparentDeletePage.openThreeDotsMenu();
            checkparentDeletePage.clickSelectChatOption();
            Thread.sleep(2000);
            
            // 2. Cancel any selections
            System.out.println("Step 2: Canceling checkboxes...");
            checkparentDeletePage.cancelAllCheckboxes();
            Thread.sleep(2000);
            
            // 3. Select ONE random message
            System.out.println("Step 3: Selecting random message...");
            checkparentDeletePage.selectRandomMessage();
            Thread.sleep(2000);
            
            // Verify selection
            int selectedCount = checkparentDeletePage.getSelectedMessageCount();
            System.out.println("Messages selected: " + selectedCount);
            
            // If 0, try selecting again
            if (selectedCount == 0) {
                System.out.println("No messages selected, trying again...");
                checkparentDeletePage.selectRandomMessage();
                Thread.sleep(2000);
                selectedCount = checkparentDeletePage.getSelectedMessageCount();
                System.out.println("After retry, selected: " + selectedCount);
            }
            
            Assert.assertTrue(selectedCount > 0, "❌ Should have at least 1 message selected");
            
            // 4. Take screenshot before deletion
            System.out.println("Step 4: Taking screenshot before deletion...");
            checkparentDeletePage.takeScreenshot("before_deletion");
            
            // 5. Delete the selected message
            System.out.println("Step 5: Deleting message...");
            checkparentDeletePage.deleteSelectedMessages();
            
            // 6. Take screenshot after deletion
            System.out.println("Step 6: Taking screenshot after deletion...");
            checkparentDeletePage.takeScreenshot("after_deletion");
            
            System.out.println("✅ Test completed");
            
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("❌ Test failed due to exception: " + e.getMessage());
        }
    }
}