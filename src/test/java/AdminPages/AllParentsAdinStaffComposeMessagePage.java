package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.util.*;

public class AllParentsAdinStaffComposeMessagePage {

    WebDriver driver;
    WebDriverWait wait;
    Random random = new Random();

    public AllParentsAdinStaffComposeMessagePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ---------------- LOCATORS ----------------
    private By allParentsTab =
            By.xpath("//span[normalize-space()='All Parents']");
    private By newMessageBtn =
            By.xpath("//button[contains(.,'New Message')]");
    private By primaryRecipientDropdown =
            By.xpath("//label[contains(text(),'Primary Recipient')]/following::mat-select[1]");

    private By allParentsCheckbox =
            By.xpath("//mat-option//span[normalize-space()='All Parents']");
    /*
    private By primaryRecipientDropdown =
            By.xpath("//mat-select[@formcontrolname='primaryRecipient']");
    private By allParentsCheckbox =
            By.xpath("//mat-checkbox//span[normalize-space()='All Parents']");
	*/
    private By additionalRecipientsDropdown =
            By.xpath("(//mat-select)[2]");

    private By messageTypeDropdown =
            By.xpath("//mat-select[@formcontrolname='messageType']");

    private By messageTypeOptions =
            By.xpath("//mat-option//span");


    private By subject =
            By.xpath("//input[@placeholder='Type message subject']");
    ////input[@placeholder='Type message subject']

    private By editor =
            By.xpath("//div[@contenteditable='true']");

    private By fileInput =
            By.cssSelector("input[type='file']");

    private By sendButton =
            By.xpath("//button[contains(.,'Send')]");
    private By fileUploadInput = By.cssSelector("input[type='file']");

    private    By fileUploadLabel = By.xpath("//label[@for='fileInput'] | //*[contains(text(),'Upload Attachment')] | //button[contains(text(),'Attach File')]"); 
    private By successMessage = By.xpath("//*[contains(text(),'successfully') or contains(text(),'sent')]");
    private By successToast = By.xpath(
    	    "//snack-bar-container//*[contains(text(),'Message')]"
    	);

    // ---------------- UTIL ----------------
    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }

    private void closeDropdown() {
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }

    // ---------------- ACTIONS ----------------
    public void selectAllParentsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(allParentsTab)).click();
    }
    
    

    public void clickNewMessage() {
        wait.until(ExpectedConditions.elementToBeClickable(newMessageBtn)).click();
    }
    
//selectPrimaryAllParents
    // ✅ PRIMARY RECIPIENT – ALL PARENTS
    /*
    public void selectPrimaryAllParents() {

        // 1️⃣ Click Primary Recipient dropdown
        WebElement dropdown = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//mat-select[@formcontrolname='primaryRecipient']")
                )
        );
        jsClick(dropdown);

        // 2️⃣ Locate the REAL checkbox input
        WebElement checkboxInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//mat-checkbox[.//span[normalize-space()='All Parents']]//input[@type='checkbox']")
                )
        );

        // 3️⃣ Click checkbox via JavaScript (Angular-safe)
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", checkboxInput);

        // 4️⃣ Close dropdown
        dropdown.sendKeys(Keys.ESCAPE);
    }*/
    //****************@ndmethod

    
    public void selectPrimaryAllParents() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1️⃣ Click Primary Recipient dropdown
        WebElement dropdown = wait.until(
                ExpectedConditions.presenceOfElementLocated(primaryRecipientDropdown)
        );
        js.executeScript("arguments[0].click();", dropdown);

        // 2️⃣ Click "All Parents" option directly (NO overlay)
        WebElement allParents = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//mat-option[.//span[normalize-space()='All Parents']]")
                )
        );
        js.executeScript("arguments[0].click();", allParents);

        // 3️⃣ Close dropdown safely
        dropdown.sendKeys(Keys.ESCAPE);
    }

    // ✅ ADDITIONAL RECIPIENTS
    public void selectFirstSixAdditionalRecipients() {

        // Open dropdown
        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(additionalRecipientsDropdown)
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);

        // Get first 6 unchecked options
        List<WebElement> options = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("(//mat-option[@aria-selected='false'])[position()<=6]")
                )
        );

        for (WebElement option : options) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
        }

        // Close dropdown
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }
    
 // ✅ RANDOM MESSAGE TYPE (STABLE)
    public void selectRandomMessageType() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1️⃣ Open the dropdown
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(messageTypeDropdown));
        js.executeScript("arguments[0].click();", dropdown);

        // 2️⃣ Wait for overlay to appear
        By overlayPanel = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(overlayPanel));

        // 3️⃣ Get all options
        List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(overlayPanel));

        // 4️⃣ Pick a random option safely with retry
        int attempts = 0;
        while (attempts < 3) {
            try {
                int randomIndex = new Random().nextInt(options.size());
                WebElement optionToClick = wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.xpath("(//div[contains(@class,'cdk-overlay-pane')]//mat-option)[" + (randomIndex + 1) + "]")
                        )
                );
                js.executeScript("arguments[0].click();", optionToClick);
                break; // clicked successfully
            } catch (StaleElementReferenceException e) {
                attempts++;
                options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(overlayPanel)); // re-fetch elements
            }
        }

        // 5️⃣ Wait until dropdown closes
        wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayPanel));
    }
   public void enterRandomSubject() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(subject))
                .sendKeys("Automation Subject " + System.currentTimeMillis());
    }

    public void enterFormattedMessage() {

        WebElement box = wait.until(ExpectedConditions.elementToBeClickable(editor));
        box.click();

        box.sendKeys("This is ");
        box.sendKeys(Keys.CONTROL, "b", "bold ", Keys.CONTROL, "b");
        box.sendKeys(Keys.CONTROL, "i", "italic ", Keys.CONTROL, "i");
        box.sendKeys(Keys.CONTROL, "u", "underline", Keys.CONTROL, "u");
    }

    public void uploadMultipleFiles(String... filePaths) {

        // Locate file input
        WebElement fileInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']"))
        );

        StringBuilder validPaths = new StringBuilder();

        for (String path : filePaths) {
            File file = new File(path);

            if (!file.exists()) {
                System.out.println("⚠️ Skipping missing file: " + path);
                continue;
            }

            validPaths.append(file.getAbsolutePath()).append("\n");
        }

        if (validPaths.length() == 0) {
            throw new RuntimeException("❌ No valid files found to upload");
        }

        // Remove last newline
        validPaths.setLength(validPaths.length() - 1);

        // Upload
        fileInput.sendKeys(validPaths.toString());

        System.out.println("✅ Files uploaded successfully");
    }
    
    public void uploadFilesUsingSystemDialog(String... filePaths) {
        try {
            WebElement uploadButton = driver.findElement(fileUploadLabel);
            uploadButton.click();  // open system dialog
            Thread.sleep(3000);    // wait for dialog to appear

            // Combine file paths with newline
            String files = String.join("\n", filePaths);

            // Copy to clipboard
            StringSelection selection = new StringSelection(files);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

            Robot robot = new Robot();
            robot.setAutoDelay(500);

            // Press CTRL+V
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            // Press ENTER
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            System.out.println("✅ Files uploaded via system dialog");

            Thread.sleep(2000); // optional wait for upload to complete
        } catch (Exception e) {
            throw new RuntimeException("❌ System dialog upload failed", e);
        }
    }


    public boolean clickSendButton() {
        try {
            // Wait until send button is clickable
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement sendBtn = wait.until(ExpectedConditions.elementToBeClickable(sendButton));

            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sendBtn);

            // Click using JS
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendBtn);

            System.out.println("✅ Send button clicked successfully");

            // Optional: wait for message to be sent
            Thread.sleep(3000);

            // Call your verification method
            return verifyMessageSent();

        } catch (Exception e) {
            System.out.println("❌ Failed to click Send: " + e.getMessage());
            return false;
        }
    }

	public boolean verifyMessageSent() {
		// TODO Auto-generated method stub
		return false;
	}


    
}

