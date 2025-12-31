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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ComposeMessagePage {
    WebDriver driver;
    WebDriverWait wait;
    Random random = new Random();

    // Locators
    By newMessageButton = By.xpath("//button[contains(.,'New Message')]");
    
    // Dropdowns (Combined locators for Angular mat-select and Mui Select)
    By primaryRecipientDropdown = By.xpath("//*[contains(text(),'Primary Recipient')]/following::mat-select[1] | //label[contains(.,'Primary Recipient')]/following::div[contains(@class,'mat-select')][1] | //div[./label[contains(text(),'Primary Recipient')]]//div[contains(@class,'MuiSelect-select') or contains(@class,'mat-select-trigger')]");
    
    // ROBUST LOCATOR FOR ADDITIONAL RECIPIENTS
    By additionalRecipientsDropdown = By.xpath(
        "//div[./label[contains(text(),'Additional Recipients')]]//div[contains(@class,'MuiInputBase-root') or contains(@class,'mat-select-trigger')] | " + 
        "//label[contains(text(),'Additional Recipients')]/following-sibling::div[1]/div" 
    );
    
    By messageTypeDropdown = By.xpath("//*[contains(text(),'Message Type')]/following::mat-select[1] | //label[contains(.,'Message Type')]/following::div[contains(@class,'mat-select')][1]");
    
    // Options for all dropdowns 
    By dropdownOptions = By.xpath("//mat-option//span | //ul[@role='listbox']//li | //div[contains(@class,'MuiPopover-root')]//li/span[text()] | //div[contains(@class,'MuiPaper-root')]//li");

    // Locator for the search input field within the recipient dropdown panel (if applicable)
    By dropdownSearchInput = By.xpath("//input[contains(@placeholder,'Search') or contains(@placeholder,'recipient')]");
    
    // Input fields
    By subjectField = By.xpath("//input[contains(@placeholder,'Subject') or contains(@placeholder,'subject')]");
    By messageBodyField = By.xpath("//textarea | //div[@contenteditable='true']");
    
    // File upload
    // The actual hidden input field where the file path must be sent.
    By fileUploadInput = By.xpath("//input[@type='file']"); 
    // The visible label/button that users click 
    By fileUploadLabel = By.xpath("//label[@for='fileInput'] | //*[contains(text(),'Upload Attachment')] | //button[contains(text(),'Attach File')]"); 
    
    By sendButton = By.xpath("//button[contains(.,'Send')]");
    By successMessage = By.xpath("//*[contains(text(),'successfully') or contains(text(),'sent')]");

    By body = By.tagName("body");
    
   
    /*public void uploadAttachments() throws InterruptedException {
        // Define folder path (you can change this easily)
        String folderPath = "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots";

        // Get all PNG files from folder
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));

        if (files == null || files.length == 0) {
            System.out.println("⚠️ No .png files found in: " + folderPath);
            return;
        }

        // Loop through all files and upload them
        for (File file : files) {
            System.out.println("Uploading file: " + file.getName());

            // Locate the file input each time (in case it resets after each upload)
            WebElement uploadInput = driver.findElement(By.xpath("//input[@type='file']"));

            // Ensure input is visible to interact
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].classList.remove('input-fileupload-hidden');", uploadInput);

            // Upload the file
            uploadInput.sendKeys(file.getAbsolutePath());

            // Wait for upload to complete (adjust as needed)
            Thread.sleep(1500);
        }

        // Click "Send Message" after all uploads
        WebElement sendButton = driver.findElement(By.xpath("//button[contains(.,'Send Message')]"));
        sendButton.click();

        System.out.println("✅ All attachments uploaded and message sent successfully.");

        Thread.sleep(3000); // Just to observe result before closing
    }


    // CONSTANTS: List of potential files (using the paths you provided)
    private static final List<String> POTENTIAL_FILES = Arrays.asList(
        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\Screenshot 2023-08-28 151751.png",
        "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\Screenshot 2023-08-28 160720.png"
    );
	*/
    // CONSTANT: The specific hidden class name you mentioned, if applicable.
    private static final String HIDDEN_CLASS_NAME = "input-fileupload-hidden"; 

    public ComposeMessagePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); 
    }

    // --- Utility Methods ---

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
        String filePath = "C:\\Users\\padma.DESKTOP-ODC64OD\\OneDrive\\Pictures\\Screenshots\\file1.png";

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


    /**
     * Attempts to upload a file by forcefully removing common CSS styles/classes 
     * that hide the file input element, making it accessible to sendKeys().
     * This method is the most robust way to handle file uploads when the visible button is tricky.
     * * @param absoluteFilePath The absolute path of the file to upload.
     */
    
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
    
    /**
     * Uploads multiple files by sending all combined paths to the input element.
     * This method utilizes the robust JS visibility technique internally.
     * * @param absoluteFilePaths Array of absolute paths.
     */
    public void uploadMultipleFiles(String... absoluteFilePaths) {
        try {
            if (absoluteFilePaths == null || absoluteFilePaths.length == 0) {
                System.out.println("⚠️ No file paths provided for upload.");
                return;
            }

            // 1. Resolve absolute paths and combine with newline separator
            StringBuilder pathsToUpload = new StringBuilder();
            
            for (String absPath : absoluteFilePaths) {
                File file = new File(absPath);
                
                if (!file.exists()) {
                    System.out.println("❌ File not found at path: " + absPath + ". Skipping.");
                    continue; 
                }
                
                if (pathsToUpload.length() > 0) {
                    pathsToUpload.append("\n"); // Crucial separator for multi-file upload
                }
                pathsToUpload.append(file.getAbsolutePath());
                System.out.println("📎 Preparing: " + file.getName());
            }

            if (pathsToUpload.length() > 0) {
                // 2. Call the single-file set method, passing the combined path string
                // Note: The method signature takes a single string, which for multi-upload, is the newline-separated path list.
                uploadFileByRemovingHidingStyles(pathsToUpload.toString());
            } else {
                 System.out.println("⚠️ No valid files were prepared for upload.");
            }
            
        } catch (Exception e) {
            System.out.println("❌ File upload failed: " + e.getMessage());
        }
    }

    // --- Core Action Methods ---

    // ... (All other methods like clickNewMessageTab, selectFromDropdown, enterSubject, etc., remain the same) ...

    
    public void clickNewMessageTab() {
        try {
            waitForPageLoad();
            WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(newMessageButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
            System.out.println("✅ Clicked 'New Message' tab");
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("❌ Failed to click New Message tab: " + e.getMessage());
        }
    }
    
    public void pressEscapeToCloseDropdown() {
        try {
            // Attempt 1: Press ESC key (standard method)
            new Actions(driver).sendKeys(Keys.ESCAPE).perform();
            System.out.println("✅ Pressed ESC");
            Thread.sleep(500);
            
            // Attempt 2 (Fallback): Click on the body element to dismiss the dropdown
            if (driver.findElements(By.xpath("//mat-select-panel | //div[contains(@class,'MuiMenu-paper')]")).size() > 0) {
                 driver.findElement(body).click();
                 System.out.println("✅ Clicked body to close persisting dropdown.");
                 Thread.sleep(1000);
            }
            
        } catch (Exception e) {
            System.out.println("❌ Failed to close dropdown (ESC/Body click): " + e.getMessage());
        }
    }

    /**
     * Generic dropdown selection using combined locators. 
     */
    public void selectFromDropdown(By dropdownLocator, String optionText, boolean useSearch) {
         try {
             System.out.println("🔍 Selecting '" + optionText + "' from dropdown...");
             
             WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
             ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
             Thread.sleep(2000);
             
             // Check if a search input is available and requested
             if (useSearch) {
                 List<WebElement> searchInputs = driver.findElements(dropdownSearchInput);
                 if (!searchInputs.isEmpty()) {
                     searchInputs.get(0).sendKeys(optionText);
                     System.out.println("✅ Entered search string: " + optionText);
                     Thread.sleep(1000); 
                 }
             }

             List<WebElement> options = driver.findElements(dropdownOptions);
             
             boolean optionFound = false;
             for (WebElement option : options) {
                 String currentText = option.getText().trim();
                 
                 boolean matchFound = false;
                 
                 // 1. Prioritize case-insensitive exact match
                 if (currentText.equalsIgnoreCase(optionText)) {
                     matchFound = true;
                 } 
                 // 2. Fallback to 'contains'
                 else if (!currentText.isEmpty() && currentText.contains(optionText)) {
                     matchFound = true;
                 }

                 if (matchFound) {
                     ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                     System.out.println("✅ Selected: " + currentText);
                     optionFound = true;
                     Thread.sleep(1500);
                     break;
                 }
             }
             
             if (!optionFound) {
                 pressEscapeToCloseDropdown(); 
                 System.out.println("⚠️ Option '" + optionText + "' not found. Closed dropdown.");
             } else {
                 pressEscapeToCloseDropdown(); 
             }
             
         } catch (Exception e) {
             System.out.println("❌ Dropdown selection failed: " + e.getMessage());
             pressEscapeToCloseDropdown(); 
         }
     }
     
    public void selectPrimaryRecipient(String recipient) {
        try {
            System.out.println("🎯 Selecting Primary Recipient (with Search): " + recipient);
            selectFromDropdown(primaryRecipientDropdown, recipient, true); 
        } catch (Exception e) {
            System.out.println("❌ Primary Recipient selection failed: " + e.getMessage());
        }
    }

    public void clickAllStaffTab() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            
            // Wait for the All Staff tab to be visible and clickable
            WebElement allStaffTab = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[normalize-space(text())='All Staff']")));
            
            wait.until(ExpectedConditions.elementToBeClickable(allStaffTab));
            
            // Scroll into view and click
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", allStaffTab);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", allStaffTab);
            
            System.out.println("✅ Clicked on 'All Staff' tab successfully.");
            
        } catch (TimeoutException e) {
            System.out.println("❌ Timeout waiting for 'All Staff' tab to appear.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("⚠️ Error clicking on 'All Staff' tab: " + e.getMessage());
        }
    }


    public void selectMessageType(String messageType) {
        try {
            System.out.println("🎯 Selecting Message Type: " + messageType);
            selectFromDropdown(messageTypeDropdown, messageType, false);
        } catch (Exception e) {
            System.out.println("❌ Message Type selection failed: " + e.getMessage());
        }
    }

    public void selectAdditionalRecipientBySearch(String searchString) throws Exception {
        try {
            System.out.println("🎯 Searching and Selecting Additional Recipient: " + searchString);
            
            WebElement addRecipients = wait.until(ExpectedConditions.elementToBeClickable(additionalRecipientsDropdown));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addRecipients);
            System.out.println("✅ Clicked Additional Recipients dropdown.");
            Thread.sleep(1500); 

            try {
                WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownSearchInput));
                searchInput.sendKeys(searchString);
                System.out.println("✅ Entered search string: " + searchString);
                Thread.sleep(1000); 
            } catch (TimeoutException | NoSuchElementException e) {
                addRecipients.sendKeys(searchString);
                System.out.println("✅ Typed search string directly into input element: " + searchString);
                Thread.sleep(1000);
            }
            
            List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownOptions));
            
            boolean optionFound = false;
            for (WebElement option : options) {
                String name = option.getText().trim();
                if (name.contains(searchString)) { 
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                    System.out.println("✅ Selected match: " + name);
                    optionFound = true;
                    Thread.sleep(500); 
                    break; 
                }
            }

            if (!optionFound) {
                 System.out.println("⚠️ Search match not found for: " + searchString);
            }
            
            pressEscapeToCloseDropdown();
            
        } catch (Exception e) {
            System.out.println("❌ Additional Recipient search failed: " + e.getMessage());
            pressEscapeToCloseDropdown();
            throw e;
        }
    }
    
    public void selectAdditionalRecipients(List<String> recipientNames) throws Exception {
        try {
            System.out.println("🎯 Selecting Additional Recipients: " + recipientNames);
            
            WebElement addRecipients = wait.until(ExpectedConditions.elementToBeClickable(additionalRecipientsDropdown));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addRecipients);
            
            System.out.println("✅ Clicked Additional Recipients dropdown using JS click.");
            Thread.sleep(1500); 
            
            try {
                WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownSearchInput));
                searchInput.sendKeys(recipientNames.get(0));
                System.out.println("✅ Entered search string: " + recipientNames.get(0));
                Thread.sleep(1000); 
            } catch (TimeoutException | NoSuchElementException e) {
                System.out.println("⚠️ Search input not found, continuing without search input interaction.");
            }
            
            List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownOptions));
            System.out.println("📋 Found " + options.size() + " options in Additional Recipients list.");
            
            for (WebElement option : options) {
                String name = option.getText().trim(); 
                
                if (recipientNames.stream().anyMatch(name::contains)) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                    System.out.println("✅ Selected: " + name);
                    Thread.sleep(500); 
                }
            }
            
            pressEscapeToCloseDropdown();
            
        } catch (Exception e) {
            System.out.println("❌ Additional Recipients selection failed: " + e.getMessage());
            pressEscapeToCloseDropdown();
            throw e;
        }
    }
    
    public void selectAdditionalRecipientsWithRetry(List<String> recipientNames, int maxAttempts) throws Exception {
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                System.out.println("🔄 Attempt " + attempt + " to select Additional Recipients");
                selectAdditionalRecipients(recipientNames);
                System.out.println("✅ Successfully selected Additional Recipients on attempt " + attempt);
                return;
            } catch (Exception e) {
                System.out.println("❌ Attempt " + attempt + " failed: " + e.getMessage());
                if (attempt < maxAttempts) {
                    System.out.println("🔄 Retrying in 2 seconds...");
                    Thread.sleep(2000);
                } else {
                    System.out.println("❌ Failed to select Additional Recipients after " + maxAttempts + " attempts");
                    throw e;
                }
            }
        }
    }
    /*
    public List<String> selectRandomFilePaths() {
        List<String> availableFiles = new ArrayList<>(POTENTIAL_FILES);
        
        if (availableFiles.isEmpty()) {
            System.out.println("⚠️ No valid files found on the system to select randomly (based on POTENTIAL_FILES list).");
            return Collections.emptyList(); 
        }
        
        List<String> selectedFiles = new ArrayList<>();
        int maxSelectable = Math.min(availableFiles.size(), 2); 
        int filesToSelect = random.nextInt(maxSelectable) + 1; 
        
        Collections.shuffle(availableFiles);
        
        for (int i = 0; i < filesToSelect; i++) {
            selectedFiles.add(availableFiles.get(i));
        }
        
        System.out.println("✅ Randomly selected files for upload: " + selectedFiles);
        return selectedFiles;
    }

    public void uploadRandomFiles() {
        List<String> randomPaths = selectRandomFilePaths();
        if (!randomPaths.isEmpty()) {
            uploadMultipleFiles(randomPaths.toArray(new String[0]));
        } else {
             System.out.println("❌ Cannot upload random files: Selection returned empty list.");
        }
    }
    */
    public void enterSubject(String subject) {
         try {
             WebElement subjectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(subjectField));
             subjectElement.clear();
             subjectElement.sendKeys(subject);
             System.out.println("✅ Entered subject: " + subject);
         } catch (Exception e) {
             System.out.println("❌ Failed to enter subject: " + e.getMessage());
         }
     }
    
    public void enterRandomSubject() {
         String randomSubject = "Automation Test Subject " + System.currentTimeMillis();
         enterSubject(randomSubject);
     }
     
    public void enterRandomMessageContent() {
         String messageContent = "This is an automated test message.\n\nTesting robust dropdown selection logic.";
         enterMessageContent(messageContent);
     }
     
    public void enterMessageContent(String content) {
        try {
            List<WebElement> fields = driver.findElements(messageBodyField);
            for (WebElement field : fields) {
                if (field.isDisplayed() && field.isEnabled()) {
                    field.clear();
                    field.sendKeys(content);
                    System.out.println("✅ Entered message content.");
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to enter message content: " + e.getMessage());
        }
    }
    
    public void clickSendButton() {
        try {
            WebElement sendBtn = wait.until(ExpectedConditions.elementToBeClickable(sendButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendBtn);
            System.out.println("✅ Clicked Send button");
            Thread.sleep(4000);
        } catch (Exception e) {
            System.out.println("❌ Failed to click Send: " + e.getMessage());
        }
    }

    public boolean verifyMessageSent() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            System.out.println("✅ Message sent successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("❌ Success message not found: " + e.getMessage());
            return false;
        }
    }

    public boolean testAdditionalRecipientsWithMultipleSelections() {
        try {
            System.out.println("🚀 Executing full test flow for Additional Recipients...");
            
            clickAllStaffTab(); 
            Thread.sleep(3000);
            
            List<String> recipientsToSelect = Arrays.asList("Díya Reddy", "Suraj Konangi", "John Doe");
            selectAdditionalRecipientsWithRetry(recipientsToSelect, 3);
            
            selectMessageType("Regular Message");
            enterRandomSubject();
            enterRandomMessageContent();
            
            // Using the multi-file method which uses the new JS technique internally:
            //uploadMultipleFiles(POTENTIAL_FILES.get(0), POTENTIAL_FILES.get(1));
            
            clickSendButton();
            
            return verifyMessageSent();
            
        } catch (Exception e) {
            System.out.println("❌ Additional Recipients multiple selections test failed in wrapper: " + e.getMessage());
            return false;
        }
    }
}