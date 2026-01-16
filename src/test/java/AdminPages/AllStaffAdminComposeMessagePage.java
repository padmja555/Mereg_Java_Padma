package AdminPages;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
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

public class AllStaffAdminComposeMessagePage {
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
    //By fileUploadInput = By.xpath("//input[@type='file']"); 
    By fileUploadInput = By.cssSelector("input[type='file']");

    // The visible label/button that users click 
    By fileUploadLabel = By.xpath("//label[@for='fileInput'] | //*[contains(text(),'Upload Attachment')] | //button[contains(text(),'Attach File')]"); 
    
    By sendButton = By.xpath("//button[contains(.,'Send')]");
    By successMessage = By.xpath("//*[contains(text(),'successfully') or contains(text(),'sent')]");
    By successToast = By.xpath(
    	    "//snack-bar-container//*[contains(text(),'Message')]"
    	);

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

    public AllStaffAdminComposeMessagePage(WebDriver driver) {
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
    
    public void uploadMultipleFiles(String... absolutePaths) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement input = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("fileInput"))
        );

        // Angular hides input → force it visible
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
            "arguments[0].removeAttribute('hidden');" +
            "arguments[0].style.display='block';" +
            "arguments[0].style.visibility='visible';",
            input
        );

        // Multiple files → newline separated
        String files = String.join("\n", absolutePaths);

        input.sendKeys(files);

        System.out.println("✅ Files uploaded successfully");
    }


    // Method to upload multiple files via JavaScript
    public void uploadMultipleFilesViaJS(String... filePaths) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            // Find all file input elements
            List<WebElement> fileInputs = driver.findElements(fileUploadInput);
            
            if (fileInputs.isEmpty()) {
                System.out.println("❌ No file input found");
                return;
            }
            
            // Use the first file input element
            WebElement fileInput = fileInputs.get(0);
            
            // Make the input visible for debugging (optional)
            js.executeScript("arguments[0].style.display = 'block'; arguments[0].style.visibility = 'visible';", fileInput);
            
            // Create a string with all file paths separated by newline
            StringBuilder allPaths = new StringBuilder();
            for (int i = 0; i < filePaths.length; i++) {
                allPaths.append(filePaths[i]);
                if (i < filePaths.length - 1) {
                    allPaths.append("\n"); // Newline separates multiple files
                }
            }
            
            System.out.println("📁 Uploading files: " + allPaths.toString());
            
            // Send all file paths at once
            fileInput.sendKeys(allPaths.toString());
            
            System.out.println("✅ " + filePaths.length + " files uploaded successfully");
            Thread.sleep(2000);
            
        } catch (Exception e) {
            System.err.println("❌ Upload failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    
    // --- Method 2: Using hidden input + JS manipulation ---
    public void uploadFilesByJS(String... absoluteFilePaths) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(fileUploadInput));

            // Force visibility
            js.executeScript(
                "arguments[0].classList.remove('" + HIDDEN_CLASS_NAME + "');" +
                "arguments[0].style.display='block'; arguments[0].style.visibility='visible';" +
                "arguments[0].style.height='1px'; arguments[0].style.opacity='1';",
                fileInput
            );

            // Combine file paths with newline for multi-file upload
            StringBuilder combinedPaths = new StringBuilder();
            for (String path : absoluteFilePaths) {
                File file = new File(path);
                if (!file.exists()) {
                    System.out.println("❌ File not found: " + path);
                    continue;
                }
                if (combinedPaths.length() > 0) combinedPaths.append("\n");
                combinedPaths.append(file.getAbsolutePath());
            }

            if (combinedPaths.length() > 0) {
                fileInput.sendKeys(combinedPaths.toString());
                System.out.println("✅ Files uploaded successfully via hidden input: " + combinedPaths);
            } else {
                System.out.println("⚠️ No valid files provided for upload.");
            }

            // Optional: hide input again
            js.executeScript("arguments[0].style.display='none'; arguments[0].style.visibility='hidden';", fileInput);
            Thread.sleep(2000);
        } catch (Exception e) {
            System.err.println("❌ Upload failed: " + e.getMessage());
        }
    }

    
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
    
    public void uploadFilesUsingSystemDialog(String... filePaths) {
        try {
            // Combine multiple files using newline
            String files = String.join("\n", filePaths);

            // Copy to clipboard
            StringSelection selection = new StringSelection(files);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);

            Thread.sleep(2000); // wait for dialog to appear

            Robot robot = new Robot();

            // CTRL + V
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            Thread.sleep(1000);

            // ENTER
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            System.out.println("✅ Files uploaded via system dialog");

        } catch (Exception e) {
            throw new RuntimeException("❌ System dialog upload failed", e);
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
//***************
    //this is new code added deepseeker
    /**
     * Main method to upload files using the best available approach
     */
    public void uploadAttachments(String... filePaths) {
        try {
            System.out.println("🚀 Starting file upload for " + filePaths.length + " files");
            
            // Try method 1: Direct JavaScript approach (most reliable)
            if (tryDirectJSUpload(filePaths)) {
                return;
            }
            
            // Try method 2: Sequential upload
            if (trySequentialUpload(filePaths)) {
                return;
            }
            
            // Try method 3: Robot approach (fallback)
            tryRobotUpload(filePaths);
            
        } catch (Exception e) {
            System.err.println("❌ All upload methods failed: " + e.getMessage());
        }
    }

    private boolean tryDirectJSUpload(String[] filePaths) {
        try {
            WebElement fileInput = findFileInput();
            if (fileInput == null) return false;
            
            // Build the file list string
            StringBuilder fileList = new StringBuilder();
            for (String path : filePaths) {
                File file = new File(path);
                if (file.exists()) {
                    if (fileList.length() > 0) fileList.append("\n");
                    fileList.append(file.getAbsolutePath());
                } else {
                    System.out.println("⚠️ File not found: " + path);
                }
            }
            
            if (fileList.length() == 0) {
                System.out.println("❌ No valid files found");
                return false;
            }
            
            // Upload all files at once
            fileInput.sendKeys(fileList.toString());
            System.out.println("✅ Direct upload successful for " + filePaths.length + " files");
            return true;
            
        } catch (Exception e) {
            System.out.println("⚠️ Direct JS upload failed, trying next method...");
            return false;
        }
    }

    private boolean trySequentialUpload(String[] filePaths) {
        try {
            for (String path : filePaths) {
                File file = new File(path);
                if (!file.exists()) {
                    System.out.println("⚠️ Skipping non-existent file: " + path);
                    continue;
                }
                
                // Find fresh file input for each upload
                WebElement fileInput = findFileInput();
                if (fileInput == null) return false;
                
                // Upload single file
                fileInput.sendKeys(file.getAbsolutePath());
                Thread.sleep(1000); // Wait between uploads
                
                // If the app supports multiple attachments, click "Add more" button
                clickAddMoreButtonIfExists();
            }
            System.out.println("✅ Sequential upload completed");
            return true;
        } catch (Exception e) {
            System.out.println("⚠️ Sequential upload failed");
            return false;
        }
    }

    private void tryRobotUpload(String[] filePaths) throws Exception {
        System.out.println("🔄 Attempting Robot-based upload...");
        //uploadFilesWithDialog(filePaths);
    }

    private WebElement findFileInput() {
        try {
            // Try to find file input with multiple attribute
            List<WebElement> inputs = driver.findElements(By.cssSelector("input[type='file']"));
            
            for (WebElement input : inputs) {
                String multiple = input.getAttribute("multiple");
                if (multiple != null && Boolean.parseBoolean(multiple)) {
                    System.out.println("✅ Found multiple file upload input");
                    return input;
                }
            }
            
            // Return first file input if no multiple attribute found
            if (!inputs.isEmpty()) {
                return inputs.get(0);
            }
            
            // Try other selectors
            inputs = driver.findElements(By.xpath("//input[@type='file'] | //input[contains(@class, 'file')]"));
            return inputs.isEmpty() ? null : inputs.get(0);
            
        } catch (Exception e) {
            System.err.println("❌ Error finding file input: " + e.getMessage());
            return null;
        }
    }

    private void clickAddMoreButtonIfExists() {
        try {
            // Look for buttons to add more attachments
            List<WebElement> addButtons = driver.findElements(
                By.xpath("//button[contains(.,'Add') or contains(.,'Attach') or contains(.,'Upload')]")
            );
            
            for (WebElement button : addButtons) {
                if (button.isDisplayed() && button.isEnabled()) {
                    button.click();
                    Thread.sleep(500);
                    break;
                }
            }
        } catch (Exception e) {
            // Ignore if no add button found
        }
    }
    /*
    public void uploadFilesSequentially(String... filePaths) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (String path : filePaths) {
            try {
                File file = new File(path);
                if (!file.exists()) {
                    System.out.println("❌ File not found: " + path);
                    continue;
                }

                WebElement fileInput = wait.until(
                    ExpectedConditions.presenceOfElementLocated(fileUploadInput)
                );

                // Force visibility
                js.executeScript(
                    "arguments[0].style.display='block';" +
                    "arguments[0].style.visibility='visible';" +
                    "arguments[0].style.opacity='1';",
                    fileInput
                );

                fileInput.sendKeys(file.getAbsolutePath());
                System.out.println("✅ Uploaded file: " + file.getName());

                Thread.sleep(1500); // wait for upload to finish

            } catch (Exception e) {
                System.out.println("❌ Failed to upload file: " + path + " - " + e.getMessage());
            }
        }
    }
*/
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