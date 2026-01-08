
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

public class IndidualParentMessagePage {

    WebDriver driver;
    WebDriverWait wait;
    Random random = new Random();

    private By newMessageBtn =
            By.xpath("//button[contains(@class,'new-message-btn')]");
    ////button[contains(@class,'new-message-btn')]

    private By allIndidualTab =
            By.xpath("//span[normalize-space()='Individual']/parent::*");

    private By primaryRecipientDropdown =
            By.xpath("//div[contains(@class,'mat-select-value')]");

    //private By primaryAllRecipientDropdownOption =
           // By.xpath("//mat-select[@formcontrolname='primaryRecipient']");

    private By additionalRecipientsDropdown =
            By.xpath("(//mat-select)[6]");

    private By staffOptions =
            By.xpath("//mat-optgroup[@label='Staff']//mat-option");
    
    private By adminOptions =
            By.xpath("//mat-optgroup[@label='Admin']//mat-option");
    
    private By parentOptions =
            By.xpath("//mat-optgroup[@label='Parent']//mat-option");

    private By messageTypeDropdown =
            By.xpath("//mat-select[@formcontrolname='messageType']");

    private By messageTypeOptions =
            By.xpath("//mat-option//span");

    private By subjectField =
            By.xpath("//input[@placeholder='Type message subject']");

    private By messageBody =
            By.xpath("//div[@contenteditable='true']");
    private By boldButton = By.xpath("//mat-icon[normalize-space()='format_bold']");
    private By italicButton = By.xpath("//mat-icon[normalize-space()='format_italic']");
    private By underlineButton = By.xpath("//mat-icon[normalize-space()='format_underlined']");

    By fileUploadInput = By.xpath("//input[@type='file']"); 
    // The visible label/button that users click 
    By fileUploadLabel = By.xpath("//label[@for='fileInput'] | //*[contains(text(),'Upload Attachment')] | //button[contains(text(),'Attach File')]"); 
    private static final String HIDDEN_CLASS_NAME = "input-fileupload-hidden"; 
    
    // Message editor should be contenteditable
    //private By messageBody = By.xpath("//div[@contenteditable='true']");


    private By sendBtn =
            By.xpath("//span[contains(text(),'Send Message')]");

    public IndidualParentMessagePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    private void closeDropdown() {
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }

    public void openComposeMessage() {
        wait.until(ExpectedConditions.elementToBeClickable(newMessageBtn)).click();
    }

    public void selectAllIndidualTab() {
        wait.until(ExpectedConditions.elementToBeClickable(allIndidualTab)).click();
    }

    public void selectThreeCategories() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1️⃣ Open Select Category dropdown
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-select[@formcontrolname='individualCategory']")
        ));
        js.executeScript("arguments[0].click();", dropdown);

        // 2️⃣ Select required options
        selectCategoryOption("Parent");
        //selectCategoryOption("Staff");
        //selectCategoryOption("Admin");

        // 3️⃣ Close dropdown
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }
//**********************************************
  /*
    public void selectRandomRecipients(int totalCount) {

        // Open Recipients dropdown
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-select[@formcontrolname=\"individualCategory\"]")
        )).click();

        // Get ALL recipients
        List<WebElement> allRecipients = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//mat-option//span[@class='mat-option-text']")
                )
        );

        Collections.shuffle(allRecipients);

        int count = Math.min(totalCount, allRecipients.size());

        for (int i = 0; i < count; i++) {
            allRecipients.get(i).click();
        }

        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }
    */
    
    public void selectRandomRecipients(int count) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Random random = new Random();

        By dropdown = By.xpath("//mat-select[@formcontrolname='primaryRecipient']");
        By optionsLocator = By.xpath("//mat-option[not(@aria-disabled='true')]");

        System.out.println("Selecting " + count + " random recipients");

        // Open dropdown initially to count options
        WebElement matSelect = wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", matSelect);
        js.executeScript("arguments[0].click();", matSelect);

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(optionsLocator));
        List<WebElement> allOptions = driver.findElements(optionsLocator);

        if (allOptions.isEmpty()) {
            throw new RuntimeException("No recipients found");
        }

        int available = allOptions.size();
        int selectCount = Math.min(count, available);
        System.out.println("Found recipients: " + available);

        // Create shuffled indices
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < available; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        // Close dropdown before multi-select loop
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();

        // Select recipients one by one
        for (int i = 0; i < selectCount; i++) {

            // Reopen dropdown every time
            matSelect = wait.until(ExpectedConditions.elementToBeClickable(dropdown));
            js.executeScript("arguments[0].click();", matSelect);

            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(optionsLocator));
            List<WebElement> options = driver.findElements(optionsLocator);

            WebElement option = options.get(indices.get(i));

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", option);
            wait.until(ExpectedConditions.elementToBeClickable(option));
            js.executeScript("arguments[0].click();", option);

            // Small pause for Angular change detection
            try { Thread.sleep(150); } catch (InterruptedException ignored) {}
        }

        // Ensure dropdown is closed
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }
    
    public void selectRandomMessageType() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 1️⃣ Click Message Type dropdown
        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//mat-select[@formcontrolname='messageType']")
                )
        );
        dropdown.click();

        // 2️⃣ Wait for overlay options
        By optionsLocator = By.xpath(
                "//div[contains(@class,'cdk-overlay-pane')]//mat-option//span"
        );
        wait.until(ExpectedConditions.visibilityOfElementLocated(optionsLocator));

        // 3️⃣ Get fresh options
        List<WebElement> options = driver.findElements(optionsLocator);

        // 4️⃣ Select random option
        int randomIndex = new Random().nextInt(options.size());

        // ⚠️ Re-locate AGAIN before click (critical)
        WebElement optionToClick = driver.findElements(optionsLocator).get(randomIndex);

        // 5️⃣ Click immediately (no reuse)
        optionToClick.click();
    }

    /*public void enterRandomSubjectAndMessage() {
        wait.until(ExpectedConditions.elementToBeClickable(subjectField))
                .sendKeys("Automation Test Message");

        wait.until(ExpectedConditions.elementToBeClickable(messageBody))
                .sendKeys("This message is sent by Selenium automation.");
    }*/
    public void enterRandomSubjectAndMessage() {

        Random random = new Random();

        // ----- Random Subject -----
        String subjectText = "Automation Subject " + UUID.randomUUID().toString().substring(0, 6);
        wait.until(ExpectedConditions.elementToBeClickable(subjectField))
                .sendKeys(subjectText);

        // ----- Random Message Content -----
        String[] messages = {
                "This is an automated test message.",
                "System generated notification for testing.",
                "Random message content created by automation.",
                "Selenium automation is sending this message.",
                "Test message with random formatting applied."
        };
        String messageText = messages[random.nextInt(messages.length)];

        // ----- Formatting buttons -----
        List<By> formattingButtons = Arrays.asList(
                boldButton,
                italicButton,
                underlineButton
        );

        Collections.shuffle(formattingButtons);
        int formatCount = random.nextInt(formattingButtons.size()) + 1;

        for (int i = 0; i < formatCount; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(formattingButtons.get(i))).click();
        }

        // ----- Message Editor -----
        WebElement messageEditor = wait.until(
                ExpectedConditions.elementToBeClickable(messageBody)
        );
        messageEditor.click();
        messageEditor.sendKeys(messageText);

        // ----- Turn formatting OFF -----
        for (int i = 0; i < formatCount; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(formattingButtons.get(i))).click();
        }
    }

    
    private void selectCategoryOption(String optionText) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        By optionLocator = By.xpath(
            "//div[contains(@class,'cdk-overlay-pane')]//mat-option//span[normalize-space()='" 
            + optionText + "']/preceding-sibling::mat-pseudo-checkbox"
        );

        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));

        // click only if not already selected
        if (!checkbox.getAttribute("class").contains("mat-pseudo-checkbox-checked")) {
            js.executeScript("arguments[0].click();", checkbox);
        }
    }
    /*
    public void clickSendButton() {
        try {
            WebElement sendBtn1 = wait.until(ExpectedConditions.elementToBeClickable(sendBtn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendBtn1);
            System.out.println("✅ Clicked Send button");
            Thread.sleep(4000);
        } catch (Exception e) {
            System.out.println("❌ Failed to click Send: " + e.getMessage());
        }
    }
*/
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
   


    public void clickSendMessage() {

        By sendBtn = By.xpath("//button//span[normalize-space()='Send Message']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // wait for button to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(sendBtn));

        // wait until enabled
        wait.until(driver -> driver.findElement(sendBtn).isEnabled());

        WebElement button = driver.findElement(sendBtn);

        // scroll into view
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", button);

        // click using JS
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", button);
    }

	public void uploadMultipleFiles(String string, String string2, String string3, String string4, String string5,
			String string6, String string7, String string8, String string9, String string10) {
		// TODO Auto-generated method stub
		
	}


}
