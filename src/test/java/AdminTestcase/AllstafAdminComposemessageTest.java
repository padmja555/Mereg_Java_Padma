package AdminTestcase;

import AdminPages.AllStaffAdminComposeMessagePage;
import AdminPages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AllstafAdminComposemessageTest {

    WebDriver driver;
    AllStaffAdminComposeMessagePage messagePage;

    private final String ADMIN_EMAIL = "srasysife@gmail.com";
    private final String ADMIN_PASSWORD = "Admin@123";
    private final String ADMIN_URL = "https://mereg-dev.netlify.app/";
    private final String MESSAGE_PAGE_URL = ADMIN_URL + "navigation-home/admin-new-messages";

    @BeforeClass
    public void setup() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(ADMIN_URL);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(ADMIN_EMAIL);
        loginPage.enterPassword(ADMIN_PASSWORD);
        loginPage.clickSignIn();

        Thread.sleep(6000);

        driver.navigate().to(MESSAGE_PAGE_URL);
        messagePage = new AllStaffAdminComposeMessagePage(driver);
        messagePage.waitForPageLoad();

        System.out.println("✅ Setup completed successfully");
    }

    @Test(priority = 1)
    public void testFullMessageCompositionAndSend() {
        try {
            driver.navigate().to(MESSAGE_PAGE_URL);
            messagePage.waitForPageLoad();

            messagePage.selectPrimaryRecipient("All Staff");

            List<String> additionalRecipients = Arrays.asList(
                    "Maheswar Gorantla",
                    "Thomas K Dawns",
                    "Super Admin MeReg (Super Admin)",
                    "padma Divyakolu (Parent)",
                    "Saketh M (Parent)"
            );

            messagePage.selectAdditionalRecipientsWithRetry(additionalRecipients, 3);

            messagePage.selectMessageType("Regular Message");
            messagePage.enterRandomSubject();
            messagePage.enterRandomMessageContent();

            // ✅ CALL the method (do NOT declare it)
            messagePage.uploadMultipleFiles( 
            		"C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file1.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file2.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file2.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file4.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file5.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file6.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file7.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file8.png"
            	);

            /*
            messagePage.uploadFilesWithDialog(
            //messagePage.uploadAttachments(
            //DPP			  
            //messagePage.uploadFilesSequentially(
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file1.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file2.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file2.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file4.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file5.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file6.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file7.png",
                    "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file8.png"
            );
            */
            Thread.sleep(2000);
            messagePage.clickSendButton();

            boolean isSent = messagePage.verifyMessageSent();
            Assert.assertTrue(isSent, "❌ Message verification failed");

            System.out.println("✅ Test Passed: Message sent successfully");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("❌ Test failed due to exception: " + e.getMessage());
        }
    }
    /**
     * Add a screenshot method for debugging
     */
    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String screenshotName = "screenshot_" + timestamp + ".png";
                FileUtils.copyFile(screenshot, new File("screenshots/" + screenshotName));
                System.out.println("📸 Screenshot saved: " + screenshotName);
            } catch (Exception e) {
                System.out.println("❌ Failed to take screenshot: " + e.getMessage());
            }
        }
    }

    public void testSimpleMessageWithoutAttachments() {
        try {
            driver.navigate().to(MESSAGE_PAGE_URL);
            messagePage.waitForPageLoad();

            messagePage.selectPrimaryRecipient("All Staff");
            messagePage.selectMessageType("Regular Message");
            messagePage.enterRandomSubject();
            messagePage.enterRandomMessageContent();

            // Send WITHOUT attachments first
            messagePage.clickSendButton();
            
            boolean isSent = messagePage.verifyMessageSent();
            Assert.assertTrue(isSent, "❌ Simple message without attachments failed");
            
            System.out.println("✅ Test Passed: Simple message sent successfully");

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("❌ Simple message test failed: " + e.getMessage());
        }}


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            //driver.quit();
        }
    }
}
