/*
package AdminTestcase;
import AdminPages.AllstaffMessagePage;
import AdminPages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class AllStaffMessageTest {

    WebDriver driver;
    AllStaffMessagePage messagePage;

    private final String ADMIN_URL = "https://mereg-dev.netlify.app/";
    private final String ADMIN_EMAIL = "srasysife@gmail.com";
    private final String ADMIN_PASSWORD = "Admin@123";
    private final String MESSAGE_PAGE_URL = ADMIN_URL + "navigation-home/admin-new-messages";

    @BeforeClass
    public void setup() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // 1️⃣ Login
        driver.get(ADMIN_URL);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(ADMIN_EMAIL);
        loginPage.enterPassword(ADMIN_PASSWORD);
        loginPage.clickSignIn();
        Thread.sleep(6000);

        // 2️⃣ Navigate to message page
        driver.navigate().to(MESSAGE_PAGE_URL);
        messagePage = new AllStaffMessagePage(driver);
        messagePage.waitForPageLoad();

        System.out.println("✅ Logged in and navigated to All Staff Message page.");
    }

    @Test(priority = 1)
    public void testSendAllStaffMessage() throws Exception {
        System.out.println("🚀 Starting All Staff Message Test");

        messagePage.clickAllStaffTab();
        messagePage.selectPrimaryRecipient("All Staff");

        List<String> names = Arrays.asList("Maheswar Gorntla", "Thomas K Dawans", "Super Admin MeReg", "Divya Reddy", "Suraj Konangi", "John Doe");
        messagePage.selectAdditionalRecipientsRandomly(names, 4);

        messagePage.selectMessageType("Regular Message");
        messagePage.enterSubject("Automation Test " + System.currentTimeMillis());
        messagePage.enterMessage("This is an automated message for testing All Staff page.");

        String filePath = "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\Screenshot 2023-08-28 151751.png";
        messagePage.uploadFile(filePath);

        messagePage.clickSend();

        Assert.assertTrue(messagePage.verifyMessageSent(), "❌ Message sending failed.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("✅ Browser closed.");
        }
    }
}

*/
package AdminTestcase;

import AdminPages.ComposeMessagePage;
import AdminPages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

// Assuming 'BaseDriver' is the base class provided in your environment
public class ComposeMessageTest { 

    WebDriver driver;
    ComposeMessagePage messagePage;
    
    // Login credentials provided in the original context (assuming these are valid)
    private final String ADMIN_EMAIL = "srasysife@gmail.com"; 
    private final String ADMIN_PASSWORD = "Admin@123";
    private final String ADMIN_URL = "https://mereg.netlify.app/"; // Base URL
    private final String MESSAGE_PAGE_URL = ADMIN_URL + "navigation-home/admin-new-messages";

    @BeforeClass
    public void setup() {
        try {
            // WebDriver setup
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            
            driver.get(ADMIN_URL);
            
            // 1. Login
            LoginPage loginPage = new LoginPage(driver);
            loginPage.enterEmail(ADMIN_EMAIL); 
            loginPage.enterPassword(ADMIN_PASSWORD);
            loginPage.clickSignIn();
            
            Thread.sleep(6000); // Wait for post-login redirection and page loading
            
            // 2. Navigate and Initialize Page Object
            driver.navigate().to(MESSAGE_PAGE_URL);
            messagePage = new ComposeMessagePage(driver);
            messagePage.waitForPageLoad();
            
            Assert.assertNotNull(messagePage, "Failed to initialize ComposeMessagePage object.");
            
            System.out.println("Setup completed successfully. Logged in and on Compose Message page.");
            
        } catch (Exception e) {
            System.out.println("Setup failed: " + e.getMessage());
            // It's crucial to quit the driver if setup fails
            if (driver != null) driver.quit(); 
            throw new RuntimeException("Setup failed due to login or initialization error.", e);
        }
    }

    /**
     * Helper method to reset the page state between tests by navigating and waiting.
     */
    private void renavigateToMessagePage() throws InterruptedException {
        driver.navigate().to(MESSAGE_PAGE_URL);
        messagePage.waitForPageLoad();
        Thread.sleep(2000); // Wait for components to fully load/render
    }

    /**
     * Test Case 1: Verifies the full message composition and sending flow
     * using specific recipient names and file paths.
     */
    @Test(priority = 1, description = "Verify successful message composition, selection of multiple recipients, file upload, and sending.")
    public void testFullMessageCompositionAndSend() {
        System.out.println("--- Starting Test 1: Full Message Send Flow ---");
        try {
            // Ensure we start on a clean page state
            renavigateToMessagePage();
            
            // 1. Select Recipients
            messagePage.selectPrimaryRecipient("All Staff");
            
            //List<String> additionalRecipients = Arrays.asList("Díya Reddy","John Doe" ,"Suraj Konangi");
            List<String> additionalRecipients = Arrays.asList("Maheswar Gorantla ","Thomas K Dawns" );

            messagePage.selectAdditionalRecipientsWithRetry(additionalRecipients, 3);
            
            // 2. Select Type, Enter Subject/Content
            messagePage.selectMessageType("Regular Message");
            messagePage.enterRandomSubject();
            messagePage.enterRandomMessageContent();
            
            // 3. Upload Files 
            // NOTE: The paths below must be valid, absolute paths to existing files on your local machine
            // for the upload to succeed. The log shows these files were not found.
            /*messagePage.uploadMultipleFiles(
                "C:\\Srasys-2021\\Attachment\\test_file_1.txt", 
                "C:\\Srasys-2021\\Attachment\\test_file_2.pdf"
            );
            */
            messagePage.uploadFileWithDialog();
            
            // 4. Send Message
            messagePage.clickSendButton();
            
            // 5. Assert Success
            boolean isSent = messagePage.verifyMessageSent();
            Assert.assertTrue(isSent, "Message failed to send. Success message not displayed.");
            
            System.out.println("Test 1 Passed: Message sent successfully.");
            
        } catch (Exception e) {
            System.out.println("Test 1 Failed: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}