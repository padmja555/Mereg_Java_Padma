
package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.time.Duration;
import java.util.*;

public class AllMessagePaginationPage {

    WebDriver driver;
    WebDriverWait wait;
    Random random = new Random();

    // ========= LOCATORS =========
    
    By pageIndicator = By.xpath("//div[contains(text(),'Showing page')]");

    private By messageCards = By.xpath("//div[contains(@class,'message')]");
    private By messagesTab = By.xpath("//*[normalize-space()='Messages']");
    By paginationContainer = By.xpath("//button[normalize-space()='Previous']/parent::div");
    By nextButton = By.xpath("//button[normalize-space()='Next']");
    By previousButton = By.xpath("//button[normalize-space()='Previous']");
    By pageNumbers = By.xpath("//button[normalize-space()='1' or normalize-space()='2' or normalize-space()='3']");
    
    private By searchInput = By.xpath("//input[contains(@placeholder,'Search Messages')]");
    //private By messageCards = By.xpath("//div[contains(@class,'message')]");
    private By replyTextArea = By.xpath("//textarea[contains(@placeholder,'Reply') or contains(@placeholder,'Type your reply')]");
    private By fileUploadInput = By.xpath("//input[@type='file']");
    private By sendReplyButton = By.xpath("//button[.//span[normalize-space()='Send Reply'] or contains(text(),'Send')]");
    
    // Search box locators
    private By searchBox = By.xpath("//input[@placeholder='Search Messages']");
    private By messageItems = By.xpath("//div[contains(@class,'message-item') or contains(@class,'message-row')]");
    private By messageSubjects = By.xpath("//div[contains(@class,'subject') or contains(@class,'message-subject')]");
    private By messagePreview = By.xpath("//div[contains(@class,'preview') or contains(@class,'snippet')]");
	
    public AllMessagePaginationPage(WebDriver driver) {
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
    
    public void clickNextPage() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By nextButton = By.xpath("//button[normalize-space()='Next']");
        By activePageButton = By.xpath("//button[@aria-current='true' or contains(@class,'active')]");

        // Get current active page number
        String currentPage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(activePageButton)
        ).getText();

        // Click Next
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();

        // ✅ Wait until active page number changes
        wait.until(driver -> {
            WebElement newActive = driver.findElement(activePageButton);
            return !newActive.getText().equals(currentPage);
        });
    }


    
    public void clickPreviousPage() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By previousButton = By.xpath("//button[normalize-space()='Previous']");
        By activePageButton = By.xpath("//button[@aria-current='true' or contains(@class,'active')]");

        // Capture current active page
        String currentPage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(activePageButton)
        ).getText();

        // Click Previous
        wait.until(ExpectedConditions.elementToBeClickable(previousButton)).click();

        // ✅ Wait until active page changes
        wait.until(driver -> {
            WebElement newActive = driver.findElement(activePageButton);
            return !newActive.getText().equals(currentPage);
        });
    }
    

    
    public void clickRandomPageNumber() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By pageButtons = By.xpath(
            "//button[not(normalize-space()='Previous') and not(normalize-space()='Next')]"
        );
        By activePageButton = By.xpath("//button[@aria-current='true' or contains(@class,'active')]");

        String currentPage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(activePageButton)
        ).getText();

        List<WebElement> pages = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(pageButtons)
        );

        int randomIndex = new Random().nextInt(pages.size());
        pages.get(randomIndex).click();

        wait.until(driver -> {
            WebElement newActive = driver.findElement(activePageButton);
            return !newActive.getText().equals(currentPage);
        });
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
    public void replyWithAttachment(String replyMessage, String filePath, String... additionalFiles) {
        try {
            System.out.println("=== Starting reply with attachment ===");
            
            // Step 1: First click the Reply button if it exists
            clickReplyButton();
            Thread.sleep(2000);
            
            // Step 2: Look for reply box with multiple strategies
            WebElement replyBox = findReplyBox();
            
            // Step 3: Enter text
            replyBox.clear();
            replyBox.sendKeys(replyMessage);
            System.out.println("✅ Entered reply text: " + replyMessage);
            
            // Step 4: Handle attachment
            attachFile(filePath);
            System.out.println("✅ Attached file: " + filePath);
            
            // Step 5: Send the reply
            clickSendButton();
            System.out.println("✅ Reply sent successfully");
            
        } catch (Exception e) {
            takeScreenshot("reply_failed");
            throw new RuntimeException("❌ No reply box found: " + e.getMessage());
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

    private void clickSendButton() {
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