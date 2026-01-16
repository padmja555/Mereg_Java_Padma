package AdminPages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Random;

public class ReplyAdminMessagePage {

    WebDriver driver;
    WebDriverWait wait;
    Random random;

    public ReplyAdminMessagePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.random = new Random();
    }

    // ===== Locators =====

    private By messageTab =
            By.xpath("//a[.//h4[normalize-space()='Messages']]");
    private By AdminTab =
            By.xpath("//div[@role='tab']//div[normalize-space()='Parent']\r\n"
            		+ "");
    private By messageTitle =
            By.xpath("//h1[normalize-space()='Messages']");

    private By messageList =
            By.xpath("//div[contains(@class,'message')]");

    private By replyTextarea =
            By.xpath("//textarea[contains(@placeholder,'Type your reply')]");

    private By uploadInput =
            //By.xpath("//button[contains(@aria-label,'Attach')");
    		By.xpath("//input[@type='file']");
    ////button[contains(@aria-label,'Attach')]

    private By sendReplyButton =
            By.xpath("//button[contains(.,'Send Reply')]");
    
    By fileUploadInput = By.xpath("//input[@type='file']"); 
    
    // The visible label/button that users click 
    By fileUploadLabel = By.xpath("//label[@for='fileInput'] | //*[contains(text(),'Upload Attachment')] | //button[contains(text(),'Attach File')]"); 
    private static final String HIDDEN_CLASS_NAME = "input-fileupload-hidden"; 


    // ===== Actions =====

    public void clickOnMessageTab() {
        wait.until(ExpectedConditions.elementToBeClickable(messageTab)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(messageTitle));
    }
    
    public void clickOnAdminTab() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By staffTab = By.xpath("//div[@role='tab']//div[normalize-space()='Admin']");
        wait.until(ExpectedConditions.elementToBeClickable(staffTab)).click();
    }


        // STEP 1: Click Admin tab
        public void clickAdminTab() {
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@role='tab']//div[normalize-space()='Admin']")
            )).click();
        }

        // STEP 2: Open any message
        public void openFirstMessage() {
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'message-item')]")
            )).click();
        }
    


    public void waitForMessageList() {

        By staffMessages = By.xpath(
            "//div[contains(@class,'cursor-pointer') or contains(@class,'message')]"
        );

        wait.until(ExpectedConditions.visibilityOfElementLocated(staffMessages));

        System.out.println("✅ Admin messages loaded");
    }
    
    
    public void openRandomMessage() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        By adminMessageLocator = By.xpath(
            "//div[contains(@class,'message-header')][.//h3[normalize-space()='Admin MeReg']]"
        );

        // 1️⃣ Wait for messages to load
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(adminMessageLocator));

        // 2️⃣ Re-fetch elements EVERY time (prevents stale)
        List<WebElement> messages = driver.findElements(adminMessageLocator);

        if (messages.isEmpty()) {
            throw new RuntimeException("❌ No Admin messages found");
        }

        // 3️⃣ Pick random message
        WebElement randomMessage = messages.get(
            new Random().nextInt(messages.size())
        );

        // 4️⃣ Scroll into view
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            randomMessage
        );

        // 5️⃣ Click using JS (avoids React detach issues)
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].click();",
            randomMessage
        );

        System.out.println("✅ Random Admin message opened");
    }

    /** Click any random message */
    /*
    public void openRandomMessage() {

        By staffMessages = By.xpath(
            "//div[contains(@class,'cursor-pointer') or contains(@class,'message')]"
        );

        List<WebElement> messages = wait.until(
            ExpectedConditions.numberOfElementsToBeMoreThan(staffMessages, 0)
        );

        WebElement randomMessage =
            messages.get(new Random().nextInt(messages.size()));

        wait.until(ExpectedConditions.elementToBeClickable(randomMessage)).click();
    }
    /*
    
    /** Type random reply */
    public void typeRandomReply() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        System.out.println("DEBUG: Typing reply...");

        // 1️⃣ Wait for reply textarea
        WebElement replyBox = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//textarea[contains(@placeholder,'Type your reply')]")
            )
        );

        replyBox.click();

        String replyText = "Automation reply " + System.currentTimeMillis();
        replyBox.sendKeys(replyText);

        System.out.println("DEBUG: Reply typed: " + replyText);

        // 2️⃣ Click Send Reply button
        WebElement sendReplyBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//span[normalize-space()='Send Reply'] or contains(text(),'Send')]")
            )
        );

        sendReplyBtn.click();

        System.out.println("DEBUG: Send Reply clicked");
    }
    
    /** Upload attachment */
    public void uploadRandomFile() {
        File file = new File("src/test/resources/testfile.pdf");
        if (!file.exists()) {
            throw new RuntimeException("❌ testfile.pdf not found");
        }

        driver.findElement(uploadInput)
              .sendKeys(file.getAbsolutePath());
    }

    /** Send reply */
    
    public void clickSendReply() {

        WebElement sendReplyBtn = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[.//span[normalize-space()='Send Reply']]")
            )
        );

        wait.until(driver -> sendReplyBtn.isEnabled());

        sendReplyBtn.click();
    }
 
    public void waitForPageLoad() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
            );
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("⚠️ Page load wait interrupted");
        }
    }
    
    
    public void uploadMultipleFilesFromSameFolder(String folderPath, String... fileNames) {

        WebElement fileInput = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']"))
        );

        // Make sure input is interactable (important for hidden inputs)
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
            "arguments[0].style.display='block';" +
            "arguments[0].style.visibility='visible';" +
            "arguments[0].removeAttribute('hidden');",
            fileInput
        );

        StringBuilder filesToUpload = new StringBuilder();

        for (String fileName : fileNames) {

            // 🛡️ Trim accidental leading/trailing spaces
            String cleanName = fileName.trim();

            File file = new File(folderPath + File.separator + cleanName);

            if (!file.exists()) {
                throw new RuntimeException("❌ File NOT found: " + file.getAbsolutePath());
            }

            filesToUpload.append(file.getAbsolutePath()).append("\n");
        }

        fileInput.sendKeys(filesToUpload.toString().trim());
        System.out.println("✅ Multiple files uploaded successfully");
    }

}
