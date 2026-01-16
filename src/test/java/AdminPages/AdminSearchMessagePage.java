
package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.time.Duration;
import java.util.*;

public class AdminSearchMessagePage {

    WebDriver driver;
    WebDriverWait wait;
    Random random = new Random();

    // ========= LOCATORS =========
    private By messagesTab = By.xpath("//*[normalize-space()='Messages']");
    private By searchInput = By.xpath("//input[contains(@placeholder,'Search Messages')]");
    private By messageCards = By.xpath("//div[contains(@class,'message')]");
    private By replyTextArea = By.xpath("//textarea[contains(@placeholder,'Reply') or contains(@placeholder,'Type your reply')]");
    private By fileUploadInput = By.xpath("//input[@type='file']");
    private By sendReplyButton = By.xpath("//button[.//span[normalize-space()='Send Reply'] or contains(text(),'Send')]");
    //By fileUploadInput = By.cssSelector("input[type='file']");

    // The visible label/button that users click 
    By fileUploadLabel = By.xpath("//label[@for='fileInput'] | //*[contains(text(),'Upload Attachment')] | //button[contains(text(),'Attach File')]"); 
    private static final String HIDDEN_CLASS_NAME = "input-fileupload-hidden"; 
 
    By body = By.tagName("body");
    
    // Search box locators
    private By searchBox = By.xpath("//input[@placeholder='Search Messages']");
    private By messageItems = By.xpath("//div[contains(@class,'message-item') or contains(@class,'message-row')]");
    private By messageSubjects = By.xpath("//div[contains(@class,'subject') or contains(@class,'message-subject')]");
    private By messagePreview = By.xpath("//div[contains(@class,'preview') or contains(@class,'snippet')]");

    public AdminSearchMessagePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    // ========= BASIC PAGE ACTIONS =========
    public void clickOnMessageTab() {
        wait.until(ExpectedConditions.elementToBeClickable(messagesTab)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(messageCards));
        System.out.println("✅ Messages page opened");
    }
    
    public void waitForMessageList() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(messageCards));
        System.out.println("📨 Messages list loaded");
    }

    // ========= SEARCH FUNCTIONALITY (Using your pattern) =========
    public void searchMessageByText(String searchText) {
        try {
            System.out.println("🔍 Searching for: " + searchText);
            
            WebElement searchInput = findSearchBox();
            if (searchInput != null) {
                searchInput.clear();
                searchInput.sendKeys(searchText);
                System.out.println("✅ Successfully entered search text: " + searchText);
                
                // Wait for search results to load
                waitForSearchResults();
                
                // Press Enter to trigger search
                searchInput.sendKeys(Keys.ENTER);
            } else {
                System.out.println("⚠️ Search box not found on Messages page");
                takeScreenshot("search_box_not_found");
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to search message: " + e.getMessage());
            takeScreenshot("search_failed");
        }
    }
    
    // Find search box using multiple strategies (modeled after your findInputField)
    private WebElement findSearchBox() {
        List<By> locators = List.of(
            searchBox,
            By.xpath("//input[contains(@placeholder,'Search')]"),
            By.xpath("//input[@type='search']"),
            By.xpath("//mat-form-field//input"),
            By.xpath("//input[contains(@id,'search')]"),
            By.xpath("//input[contains(@class,'search')]"),
            By.xpath("//input[@matinput]"),
            By.xpath("//input")
        );

        for (By locator : locators) {
            try {
                List<WebElement> elements = driver.findElements(locator);
                for (WebElement element : elements) {
                    if (element.isDisplayed() && element.isEnabled()) {
                        String placeholder = element.getAttribute("placeholder");
                        if (placeholder != null && 
                            (placeholder.contains("Search") || placeholder.contains("Filter") || 
                             placeholder.contains("Messages"))) {
                            System.out.println("✅ Found search box with placeholder: " + placeholder);
                            return element;
                        }
                        // If it's the only input in the search area
                        if (isInSearchSection(element)) {
                            return element;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("⚠️ Not found: " + locator);
            }
        }
        System.out.println("❌ No search box found with any locator");
        return null;
    }
    
    private boolean isInSearchSection(WebElement element) {
        try {
            WebElement parent = element.findElement(By.xpath("./ancestor::div[contains(@class,'search') or contains(@class,'filter')]"));
            return parent != null;
        } catch (Exception e) {
            return false;
        }
    }
    
    private void waitForSearchResults() {
        try {
            Thread.sleep(2000); // Wait for search to complete
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'message') and contains(@class,'visible')]")));
            System.out.println("✅ Search results loaded");
        } catch (Exception e) {
            System.out.println("Search wait completed with exception: " + e.getMessage());
        }
    }

    // ========= MESSAGE SELECTION =========
    public void clickAllAdminTab() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            
            // Wait for the All Staff tab to be visible and clickable
            WebElement allStaffTab = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(),'Admin')]")));
            
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


    // Open first message
    public void openFirstMessage() {
        try {
            List<WebElement> messages = getVisibleMessageElements();
            if (!messages.isEmpty()) {
                WebElement firstMessage = messages.get(0);
                String messageText = getMessageText(firstMessage);
                System.out.println("Clicking first message: " + messageText);
                clickMessageElement(firstMessage);
                System.out.println("📩 First message opened");
            } else {
                System.out.println("⚠️ No messages found to open");
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to open first message: " + e.getMessage());
        }
    }
    
    // Open random message
    public void openRandomMessage() {
        try {
            List<WebElement> messages = getVisibleMessageElements();
            if (messages.isEmpty()) {
                System.out.println("⚠️ No messages found");
                return;
            }
            
            WebElement randomMessage = messages.get(random.nextInt(messages.size()));
            String messageText = getMessageText(randomMessage);
            System.out.println("Selected random message: " + messageText);
            clickMessageElement(randomMessage);
            System.out.println("📩 Random message opened");
        } catch (Exception e) {
            System.err.println("❌ Failed to open random message: " + e.getMessage());
        }
    }
    
    // Click on message containing specific text
    public void clickMessageContainingText(String text) {
        try {
            List<WebElement> allMessages = getVisibleMessageElements();
            
            for (WebElement message : allMessages) {
                String messageText = getMessageText(message).toLowerCase();
                if (messageText.contains(text.toLowerCase())) {
                    System.out.println("Found message containing '" + text + "': " + messageText);
                    clickMessageElement(message);
                    System.out.println("✅ Clicked message containing text: " + text);
                    return;
                }
            }
            
            System.out.println("⚠️ No message found containing text: " + text);
            
        } catch (Exception e) {
            System.err.println("❌ Failed to click message with text: " + e.getMessage());
        }
    }
    
    // Get all visible message elements
    private List<WebElement> getVisibleMessageElements() {
        List<WebElement> allMessages = new ArrayList<>();
        
        // Try multiple locators to find message elements
        List<By> messageLocators = List.of(
            messageItems,
            By.xpath("//div[contains(@class,'message')]"),
            By.xpath("//tr[contains(@class,'message')]"),
            By.xpath("//li[contains(@class,'message')]"),
            By.xpath("//*[contains(text(),'Automation Subject')]/ancestor::div[contains(@class,'message')]"),
            By.xpath("//div[contains(text(),'Automation Subject')]"),
            By.xpath("//*[contains(text(),'Automation')]")
        );
        
        for (By locator : messageLocators) {
            try {
                List<WebElement> elements = driver.findElements(locator);
                for (WebElement element : elements) {
                    if (element.isDisplayed()) {
                        allMessages.add(element);
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }
        
        return allMessages;
    }
    
    // Get text from message element
    private String getMessageText(WebElement messageElement) {
        try {
            // Try to get subject first
            try {
                WebElement subject = messageElement.findElement(By.xpath(".//div[contains(@class,'subject')]"));
                return subject.getText().trim();
            } catch (Exception e) {
                // If no subject, get any text content
                return messageElement.getText().trim();
            }
        } catch (Exception e) {
            return "";
        }
    }
    
    // Robust click method (modeled after your clickElement)
    private void clickMessageElement(WebElement element) {
        try {
            System.out.println("Clicking message element...");
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            Thread.sleep(500);
            
            // Try JavaScript click first
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            Thread.sleep(1000);
            
            System.out.println("✅ Message clicked successfully");
            
        } catch (Exception e) {
            try {
                // Fallback to regular click
                element.click();
                Thread.sleep(1000);
            } catch (Exception e2) {
                // Fallback to Actions click
                new Actions(driver).moveToElement(element).click().perform();
                try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        }
    }

    // ========= REPLY FUNCTIONALITY =========
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

    private void clickReplyButton() {
        try {
            List<WebElement> replyButtons = driver.findElements(By.xpath(
                "//button[contains(text(),'Reply') or contains(@aria-label,'Reply') or @id='replyBtn' or contains(@class,'reply')]"
            ));
            
            if (!replyButtons.isEmpty() && replyButtons.get(0).isDisplayed()) {
                System.out.println("Found Reply button, clicking...");
                replyButtons.get(0).click();
                waitForElement(By.xpath("//textarea"), 5); // Wait for textarea to appear
            } else {
                System.out.println("No Reply button found, assuming reply box is already visible");
            }
        } catch (Exception e) {
            System.out.println("Reply button not found or not clickable: " + e.getMessage());
        }
    }

    private void waitForElement(By locator, int seconds) {
        try {
            WebDriverWait elementWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            elementWait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("Element not found within " + seconds + " seconds: " + locator);
        }
    }

    private WebElement findReplyBox() {
        // Try multiple locators
        String[] locators = {
            "//textarea[contains(@placeholder,'Reply') or contains(@placeholder,'Type')]",
            "//div[@contenteditable='true']",
            "//textarea[@id='messageText']",
            "//div[contains(@class,'reply-box')]//textarea",
            "//form//textarea",
            "//mat-form-field[contains(.,'Reply')]//textarea",
            "//textarea[@placeholder='Write a reply...']",
            "//textarea[@placeholder='Type your message here...']",
            "//textarea"
        };
        
        for (String locator : locators) {
            try {
                List<WebElement> elements = driver.findElements(By.xpath(locator));
                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    System.out.println("✅ Found reply box with: " + locator);
                    return elements.get(0);
                }
            } catch (Exception e) {
                continue;
            }
        }
        throw new RuntimeException("No reply box found with any locator");
    }

    
    private void attachFile(String filePath) {
        try {
            WebElement fileInput = driver.findElement(fileUploadInput);
            fileInput.sendKeys(filePath);
            System.out.println("✅ File attached: " + filePath);
        } catch (Exception e) {
            System.err.println("❌ Failed to attach file: " + e.getMessage());
            // Try alternative file input locators
            try {
                WebElement fileInput = driver.findElement(By.xpath("//input[@type='file' and @accept]"));
                fileInput.sendKeys(filePath);
            } catch (Exception e2) {
                System.err.println("❌ Alternative file input also failed");
            }
        }
    }

    public void clickSendButton() {
        try {
            WebElement sendButton = driver.findElement(sendReplyButton);
            sendButton.click();
            System.out.println("✅ Send button clicked");
        } catch (Exception e) {
            System.err.println("❌ Failed to click send button: " + e.getMessage());
            // Try alternative send button locators
            try {
                WebElement sendButton = driver.findElement(By.xpath("//button[contains(text(),'Send')]"));
                sendButton.click();
            } catch (Exception e2) {
                System.err.println("❌ Alternative send button also failed");
            }
        }
    }

    // ========= UTILITY METHODS =========
    private void takeScreenshot(String name) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String dest = System.getProperty("user.dir") + "/screenshots/" + name + "_" + System.currentTimeMillis() + ".png";
            new File(System.getProperty("user.dir") + "/screenshots").mkdirs(); // Create directory if doesn't exist
            // FileUtils.copyFile(source, new File(dest)); // You need Apache Commons IO for this
            System.out.println("📸 Screenshot saved: " + name);
        } catch (Exception e) {
            System.out.println("⚠️ Could not take screenshot: " + e.getMessage());
        }
    }

    // Debug method for Messages page
    public void debugMessagesPage() {
        try {
            System.out.println("=== DEBUG: Messages Page Elements ===");
            
            // Search box
            List<WebElement> searchInputs = driver.findElements(By.xpath("//input"));
            System.out.println("Input fields found: " + searchInputs.size());
            for (WebElement input : searchInputs) {
                if (input.isDisplayed()) {
                    System.out.println("Input - Placeholder: " + input.getAttribute("placeholder") + 
                                     ", Type: " + input.getAttribute("type") +
                                     ", ID: " + input.getAttribute("id") +
                                     ", Class: " + input.getAttribute("class"));
                }
            }
            
            // Messages
            List<WebElement> messages = getVisibleMessageElements();
            System.out.println("Messages found: " + messages.size());
            for (int i = 0; i < Math.min(messages.size(), 5); i++) {
                System.out.println("Message " + (i+1) + ": " + getMessageText(messages.get(i)));
            }
            
            // Buttons
            List<WebElement> buttons = driver.findElements(By.xpath("//button"));
            System.out.println("Buttons found: " + buttons.size());
            for (int i = 0; i < Math.min(buttons.size(), 5); i++) {
                WebElement button = buttons.get(i);
                if (button.isDisplayed()) {
                    System.out.println("Button " + (i+1) + ": " + button.getText());
                }
            }
            
        } catch (Exception e) {
            System.out.println("Debug failed: " + e.getMessage());
        }
    }
    
    // Clear search
    public void clearSearch() {
        try {
            WebElement searchInput = findSearchBox();
            if (searchInput != null) {
                searchInput.clear();
                System.out.println("✅ Search cleared");
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to clear search: " + e.getMessage());
        }
    }
}