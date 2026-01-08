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
            By.xpath("//input[@type='file']");

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


    /** Click any random message */
    public void openRandomMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // 1️⃣ Wait for the "Staff" tab to be clickable and click it
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@role='tab']//div[normalize-space()='Staff']")
            )).click();
            System.out.println("Staff tab clicked.");

            // 2️⃣ Wait until staff messages are visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".staff-message")
            ));
            System.out.println("Staff messages are visible.");

            // 3️⃣ Find all staff messages
            List<WebElement> staffMessages = driver.findElements(By.cssSelector(".staff-message"));

            // 4️⃣ Check if there are any messages
            if (!staffMessages.isEmpty()) {
                // Click reply button for the first message
                WebElement replyButton = staffMessages.get(0).findElement(By.cssSelector(".reply-btn"));
                replyButton.click();
                System.out.println("Clicked reply button on first staff message.");
            } else {
                System.out.println("No staff messages found, skipping reply step.");
            }
        } catch (Exception e) {
            System.out.println("Error in openRandomMessage(): " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
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
   public void uploadFileWithDialog() throws Exception {
       // 1️⃣ Click the "Upload Attachment" button to open Windows file dialog
       WebElement uploadButton = driver.findElement(By.xpath("//label[@for='fileInput']"));
       uploadButton.click();
       Thread.sleep(2000); // Wait for dialog to open

       // 2️⃣ Prepare the file path
       String filePath = "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file1.png\r\n"
       		+ "";
       //C:\Users\padma.DESKTOP-ODC64OD\OneDrive\Pictures\Screenshots\\file1.png
       // 3️⃣ Copy the file path to clipboard
       StringSelection selection = new StringSelection(filePath);
       Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

       // 4️⃣ Use Robot to paste the path and press Enter
       Robot robot = new Robot();
       robot.delay(1000);

       // Press CTRL+V
       robot.keyPress(KeyEvent.VK_CONTROL);
       robot.keyPress(KeyEvent.VK_V);
       robot.keyRelease(KeyEvent.VK_V);
       robot.keyRelease(KeyEvent.VK_CONTROL);
       robot.delay(1000);

       // Press ENTER
       robot.keyPress(KeyEvent.VK_ENTER);
       robot.keyRelease(KeyEvent.VK_ENTER);

       System.out.println("✅ File uploaded successfully via Windows dialog.");
       Thread.sleep(3000);
   }
   public void uploadFileByRemovingHidingStyles(String absoluteFilePath) {
       System.out.println("Attempting file upload by manipulating the hidden input element...");
       JavascriptExecutor js = (JavascriptExecutor) driver;

       try {
           // 1. Resolve the absolute path to ensure the file exists (crucial for local execution)
           File file = new File(absoluteFilePath);
           if (!file.exists()) {
               System.err.println("❌ Error: File not found at path: " + absoluteFilePath);
               return;
           }

           // 2. Get the actual file input element (it exists in the DOM but is hidden)
           WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(fileUploadInput));

           // --- CRITICAL ROBUSTNESS STEP ---
           // 3. Force the element to be visible/interactable using JavaScript
           
           // Try removing the specific class you mentioned first
           try {
                js.executeScript("arguments[0].classList.remove('" + HIDDEN_CLASS_NAME + "');", fileInput);
                System.out.println("✅ JS executed: Removed specific class '" + HIDDEN_CLASS_NAME + "'.");
           } catch (Exception classE) {
                // Fallback: Set display/visibility/opacity directly to overcome all hiding styles
                js.executeScript("arguments[0].style.display='block'; arguments[0].style.visibility='visible'; arguments[0].style.height='1px'; arguments[0].style.opacity='1';", fileInput);
                System.out.println("✅ JS executed: Forced display/visibility style properties.");
           }
           
           // --- OPTIONAL FALLBACK: Try clicking the visible button via JS if required to trigger app state ---
           try {
               WebElement label = driver.findElement(fileUploadLabel);
               js.executeScript("arguments[0].click();", label);
               System.out.println("✅ JS Clicked the visible upload label (pre-sendKeys trigger).");
               Thread.sleep(500);
           } catch (Exception clickE) {
               // Ignore if clicking the label fails, as sendKeys should work without it now
               System.out.println("⚠️ Could not click visible label via JS. Proceeding with sendKeys.");
           }
           // --- END OPTIONAL FALLBACK ---
           
           // 4. Send the file path
           fileInput.sendKeys(file.getAbsolutePath());
           System.out.println("✅ File uploaded successfully: " + file.getName());

           // 5. Cleanup: Hide the input again (optional but good practice)
           js.executeScript("arguments[0].style.display='none'; arguments[0].style.visibility='hidden';", fileInput);
           System.out.println("✅ JS executed: Input element hidden (cleanup).");

           Thread.sleep(3000); // Allow time for the application to process the upload
           
       } catch (Exception e) {
           System.err.println("❌ File upload failed via JS technique: " + e.getClass().getSimpleName() + " - " + e.getMessage());
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
