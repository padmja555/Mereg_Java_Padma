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

public class ReplyStaffMessagePage {

    WebDriver driver;
    WebDriverWait wait;
    Random random;

    public ReplyStaffMessagePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.random = new Random();
    }

    // ===== Locators =====

    private By messageTab =
            By.xpath("//a[.//h4[normalize-space()='Messages']]");
    private By staffTab =
            By.xpath("//div[@role='tab']//div[normalize-space()='Staff']\r\n"
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

    public String getMessageTitleText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(messageTitle)
        ).getText().trim();
    }
    
    public void clickOnStaffTab() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By staffTab = By.xpath("//div[@role='tab']//div[normalize-space()='Staff']");
        wait.until(ExpectedConditions.elementToBeClickable(staffTab)).click();
    }


    public void waitForMessageList() {

        By staffMessages = By.xpath(
            "//div[contains(@class,'cursor-pointer') or contains(@class,'message')]"
        );

        wait.until(ExpectedConditions.visibilityOfElementLocated(staffMessages));

        System.out.println("✅ Staff messages loaded");
    }
    public void openRandomMessage() {

        By adminMeRegMessages =
            By.xpath("//div[contains(@class,'message-header')][.//h3[normalize-space()='Admin MeReg']]");

        List<WebElement> messages = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(adminMeRegMessages)
        );

        if (messages.isEmpty()) {
            throw new RuntimeException("❌ No Admin MeReg messages found");
        }

        WebElement randomMessage = messages.get(random.nextInt(messages.size()));

        // 🔥 Click INNER element, not container
        WebElement clickable =
            randomMessage.findElement(By.xpath(".//h3"));

        wait.until(ExpectedConditions.elementToBeClickable(clickable)).click();

        // 🔥 Wait until reply panel loads
        wait.until(ExpectedConditions.presenceOfElementLocated(replyTextarea));
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
        Actions actions = new Actions(driver);

        System.out.println("DEBUG: Starting typeRandomReply()");
        System.out.println("DEBUG: Current URL: " + driver.getCurrentUrl());

        try {
            // 1️⃣ Click Staff tab
            WebElement staffTab = driver.findElement(By.xpath("//div[@role='tab']//div[normalize-space()='Staff']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", staffTab);
            wait.until(ExpectedConditions.attributeToBe(staffTab, "aria-selected", "true"));
            System.out.println("DEBUG: Staff tab clicked");

            // 2️⃣ Wait for Staff messages container
            WebElement staffContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.id("staffMessagesContainer") // <-- use real container ID
            ));

            // 3️⃣ Get all messages inside container
            List<WebElement> messageDetails = staffContainer.findElements(
                    By.xpath(".//div[contains(@class,'message') or contains(@class,'detail')]")
            );
            System.out.println("DEBUG: Message detail containers found: " + messageDetails.size());

            if (messageDetails.isEmpty()) {
                System.out.println("DEBUG: No staff messages found, skipping reply step.");
                return;
            }

            // 4️⃣ Hover over first message
            WebElement firstMessage = messageDetails.get(0);
            actions.moveToElement(firstMessage).perform();

            // 5️⃣ Find reply button inside the message
            WebElement replyButton = null;
            try {
                replyButton = firstMessage.findElement(By.xpath(".//button[contains(text(),'Reply')]"));
                System.out.println("DEBUG: Reply button found inside message");
            } catch (NoSuchElementException e) {
                System.out.println("DEBUG: Reply button not found in first message");
                return; // skip if no reply button
            }

            // 6️⃣ Click Reply button via JS
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", replyButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", replyButton);
            System.out.println("DEBUG: Reply button clicked");

            // 7️⃣ Type reply
            WebElement replyBox = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@contenteditable='true'] | //textarea | //div[contains(@class,'editor')]")
            ));
            replyBox.click();
            String replyText = "Automation reply " + System.currentTimeMillis();
            replyBox.sendKeys(replyText);
            System.out.println("DEBUG: Typed reply: " + replyText);

            // 8️⃣ Click Send button if exists
            try {
                WebElement sendBtn = driver.findElement(By.xpath("//button[contains(text(),'Send')]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendBtn);
                System.out.println("DEBUG: Send button clicked");
            } catch (NoSuchElementException e) {
                System.out.println("DEBUG: Send button not found, reply may be auto-saved");
            }

        } catch (Exception e) {
            System.out.println("DEBUG: Error in typeRandomReply(): " + e.getMessage());
            e.printStackTrace();
        }
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
        wait.until(ExpectedConditions.elementToBeClickable(sendReplyButton))
            .click();
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
    public void uploadMultipleFiles(String... paths) {

	    WebElement input = wait.until(
	        ExpectedConditions.presenceOfElementLocated(
	            By.xpath("//input[@type='file']")
	        )
	    );

	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript(
	        "arguments[0].style.display='block';" +
	        "arguments[0].style.visibility='visible';" +
	        "arguments[0].removeAttribute('hidden');",
	        input
	    );

	    StringBuilder validFiles = new StringBuilder();

	    for (String path : paths) {
	        File file = new File(path);
	        if (!file.exists()) {
	            throw new RuntimeException("❌ File NOT found: " + file.getAbsolutePath());
	        }
	        validFiles.append(file.getAbsolutePath()).append("\n");
	    }

	    input.sendKeys(validFiles.toString().trim());
	    System.out.println("✅ Files uploaded successfully");
	}



}
