

package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.*;

public class AdminDelateMessagePage {

    WebDriver driver;
    WebDriverWait wait;
    Random random = new Random();
    
    // ========= LOCATORS =========
    private By messagesTab = By.xpath("//*[normalize-space()='Messages']");
    private By adminTab = By.xpath("//div[contains(text(),'Admin')]");
    
    // Three dots menu
    private By threeDotsButton = By.xpath("//button[contains(@class,'mat-menu-trigger') or @aria-label='More options']");
    private By menuOptions = By.xpath("//div[contains(@class,'mat-menu-content')]//button");
    private By selectChatOption = By.xpath("//button[contains(.,'Select Chat') or contains(.,'check_box')]");
    
    // Checkboxes
    private By allCheckboxes = By.xpath("//input[@type='checkbox']");
    private By messageRows = By.xpath("//div[contains(@class,'message-item') or contains(@class,'chat-item') or .//input[@type='checkbox']]");
    
    // Buttons
    private By cancelSelectionButton = By.xpath("//button[contains(.,'Cancel Selection')]");
    private By deleteSelectedButtonMenu = By.xpath("//button[contains(.,'Delete Selected')]");
    
    public AdminDelateMessagePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    // ========= BASIC NAVIGATION =========
    public void clickOnMessageTab() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(messagesTab)).click();
            System.out.println("✅ Messages page opened");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.err.println("❌ Failed to click Messages tab: " + e.getMessage());
        }
    }
    
    public void clickAdminTab() {
        try {
            System.out.println("Clicking on Staff tab...");
            WebElement staffTabElement = wait.until(ExpectedConditions.elementToBeClickable(adminTab));
            clickWithJavaScript(staffTabElement);
            System.out.println("✅ Staff tab clicked");
            Thread.sleep(3000);
        } catch (Exception e) {
            System.err.println("❌ Failed to click Staff tab: " + e.getMessage());
        }
    }

    // ========= THREE DOTS MENU =========
    public void openThreeDotsMenu() {
        try {
            System.out.println("Opening three dots menu...");
            Thread.sleep(1000);
            
            // Find the three dots button
            WebElement threeDots = wait.until(ExpectedConditions.elementToBeClickable(threeDotsButton));
            clickWithJavaScript(threeDots);
            System.out.println("✅ Three dots menu opened");
            Thread.sleep(1000);
            
        } catch (Exception e) {
            System.err.println("❌ Failed to open three dots menu: " + e.getMessage());
        }
    }
    
    public void clickSelectChatOption() {
        try {
            System.out.println("Clicking 'Select Chat' option...");
            
            // Find and click the Select Chat option
            WebElement selectOption = wait.until(ExpectedConditions.elementToBeClickable(selectChatOption));
            clickWithJavaScript(selectOption);
            System.out.println("✅ Clicked menu option: " + selectOption.getText());
            Thread.sleep(2000);
            
        } catch (Exception e) {
            System.err.println("❌ Failed to click Select Chat option: " + e.getMessage());
        }
    }

    // ========= CHECKBOX OPERATIONS =========
    public void cancelAllCheckboxes() {
        try {
            System.out.println("Canceling/unselecting all checkboxes...");
            
            // Try to find and click "Cancel Selection" button
            try {
                WebElement cancelButton = driver.findElement(cancelSelectionButton);
                if (cancelButton.isDisplayed()) {
                    clickWithJavaScript(cancelButton);
                    System.out.println("✅ Clicked 'Cancel Selection' button");
                    Thread.sleep(1000);
                    return;
                }
            } catch (Exception e) {
                // Continue to uncheck individually
            }
            
            // Uncheck all checkboxes
            List<WebElement> checkboxes = driver.findElements(allCheckboxes);
            for (WebElement checkbox : checkboxes) {
                try {
                    if (checkbox.isSelected()) {
                        clickWithJavaScript(checkbox);
                    }
                } catch (Exception e) {
                    // Skip if element is stale
                }
            }
            
            System.out.println("✅ All checkboxes unselected");
            Thread.sleep(1000);
            
        } catch (Exception e) {
            System.err.println("❌ Failed to cancel checkboxes: " + e.getMessage());
        }
    }
    
    public void selectRandomMessage() {
        try {
            System.out.println("Selecting one random message...");
            
            // Get all checkboxes (hidden inputs)
            List<WebElement> checkboxes = driver.findElements(allCheckboxes);
            
            // Filter to only message checkboxes (skip any that might be in headers or other places)
            List<WebElement> messageCheckboxes = new ArrayList<>();
            for (WebElement checkbox : checkboxes) {
                try {
                    // Check if it's inside a message row
                    WebElement parent = findParentElement(checkbox, 5); // Look up to 5 levels
                    if (parent != null) {
                        String parentClass = parent.getAttribute("class");
                        if (parentClass != null && 
                            (parentClass.contains("message") || 
                             parentClass.contains("chat") || 
                             parentClass.contains("item"))) {
                            messageCheckboxes.add(checkbox);
                        }
                    }
                } catch (Exception e) {
                    // Skip this checkbox
                }
            }
            
            if (messageCheckboxes.isEmpty()) {
                System.err.println("❌ No message checkboxes found");
                return;
            }
            
            System.out.println("Found " + messageCheckboxes.size() + " message checkboxes");
            
            // Select a random checkbox
            int randomIndex = random.nextInt(messageCheckboxes.size());
            WebElement randomCheckbox = messageCheckboxes.get(randomIndex);
            
            // Check if it's already selected
            boolean wasSelected = randomCheckbox.isSelected();
            System.out.println("Checkbox " + randomIndex + " was selected: " + wasSelected);
            
            // If already selected, try to find an unselected one
            if (wasSelected) {
                for (int i = 0; i < messageCheckboxes.size(); i++) {
                    WebElement checkbox = messageCheckboxes.get(i);
                    try {
                        if (!checkbox.isSelected()) {
                            randomCheckbox = checkbox;
                            randomIndex = i;
                            System.out.println("Found unselected checkbox at index: " + i);
                            break;
                        }
                    } catch (Exception e) {
                        // Skip
                    }
                }
            }
            
            // Click the checkbox - try multiple approaches
            boolean clicked = false;
            
            // Approach 1: Click the checkbox directly
            try {
                clickWithJavaScript(randomCheckbox);
                Thread.sleep(1000);
                clicked = true;
            } catch (Exception e) {
                System.err.println("Direct click failed: " + e.getMessage());
            }
            
            // Approach 2: Click the parent element
            if (!clicked) {
                try {
                    WebElement parent = findParentElement(randomCheckbox, 3);
                    if (parent != null) {
                        clickWithJavaScript(parent);
                        Thread.sleep(1000);
                        clicked = true;
                    }
                } catch (Exception e) {
                    System.err.println("Parent click failed: " + e.getMessage());
                }
            }
            
            // Approach 3: Use ActionChains
            if (!clicked) {
                try {
                    new Actions(driver).moveToElement(randomCheckbox).click().perform();
                    Thread.sleep(1000);
                    clicked = true;
                } catch (Exception e) {
                    System.err.println("ActionChains click failed: " + e.getMessage());
                }
            }
            
            if (clicked) {
                System.out.println("✅ Selected random checkbox at index: " + randomIndex);
            } else {
                System.err.println("❌ Failed to select checkbox after multiple attempts");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Failed to select random message: " + e.getMessage());
        }
    }
    
    // Helper method to find parent element
    private WebElement findParentElement(WebElement element, int levels) {
        try {
            WebElement parent = element;
            for (int i = 0; i < levels; i++) {
                parent = parent.findElement(By.xpath(".."));
            }
            return parent;
        } catch (Exception e) {
            return null;
        }
    }
    
    // Get count of selected messages
    public int getSelectedMessageCount() {
        try {
            int count = 0;
            List<WebElement> checkboxes = driver.findElements(allCheckboxes);
            
            for (WebElement checkbox : checkboxes) {
                try {
                    if (checkbox.isSelected()) {
                        count++;
                    }
                } catch (Exception e) {
                    // Skip stale elements
                }
            }
            
            System.out.println("Selected message count: " + count);
            return count;
            
        } catch (Exception e) {
            System.err.println("Error getting selected message count: " + e.getMessage());
            return 0;
        }
    }

    // ========= DELETE OPERATIONS =========
    public void clickDeleteSelectedButton() {
        try {
            System.out.println("Clicking 'Delete Selected' button...");
            
            // First open three dots menu
            openThreeDotsMenu();
            Thread.sleep(1000);
            
            // Look for Delete Selected option in the menu
            List<WebElement> menuItems = driver.findElements(menuOptions);
            boolean found = false;
            
            for (WebElement item : menuItems) {
                try {
                    String text = item.getText().trim();
                    System.out.println("Menu option found: " + text);
                    
                    if (text.contains("Delete Selected") || text.contains("Delete")) {
                        clickWithJavaScript(item);
                        System.out.println("✅ Clicked 'Delete Selected' option");
                        Thread.sleep(1500);
                        found = true;
                        break;
                    }
                } catch (Exception e) {
                    // Continue to next item
                }
            }
            
            if (!found) {
                System.err.println("❌ 'Delete Selected' option not found in menu");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Failed to click Delete Selected button: " + e.getMessage());
        }
    }
    
    public void confirmDeletionInPopup() {
        try {
            System.out.println("Confirming deletion in popup...");
            
            // Wait a bit for popup to appear
            Thread.sleep(1000);
            
            // Method 1: Check for alert
            try {
                Alert alert = driver.switchTo().alert();
                System.out.println("Alert found with text: " + alert.getText());
                alert.accept();
                System.out.println("✅ Accepted alert confirmation");
                Thread.sleep(2000);
                return;
            } catch (Exception e) {
                // No alert, continue
            }
            
            // Method 2: Look for confirmation dialog with OK button
            List<WebElement> buttons = driver.findElements(By.tagName("button"));
            for (WebElement button : buttons) {
                try {
                    String buttonText = button.getText().trim();
                    if (button.isDisplayed() && button.isEnabled() && 
                        (buttonText.equalsIgnoreCase("OK") || 
                         buttonText.equalsIgnoreCase("Yes") || 
                         buttonText.contains("Confirm") || 
                         buttonText.contains("Delete"))) {
                        System.out.println("Found confirmation button: " + buttonText);
                        clickWithJavaScript(button);
                        System.out.println("✅ Clicked confirmation button: " + buttonText);
                        Thread.sleep(2000);
                        return;
                    }
                } catch (Exception e) {
                    // Continue
                }
            }
            
            // Method 3: Look for modal/dialog
            List<WebElement> dialogs = driver.findElements(By.xpath("//div[contains(@class,'dialog') or contains(@class,'modal') or contains(@class,'popup')]"));
            for (WebElement dialog : dialogs) {
                if (dialog.isDisplayed()) {
                    // Look for OK button inside dialog
                    List<WebElement> dialogButtons = dialog.findElements(By.tagName("button"));
                    for (WebElement button : dialogButtons) {
                        String buttonText = button.getText().trim();
                        if (buttonText.equalsIgnoreCase("OK") || buttonText.contains("Confirm")) {
                            clickWithJavaScript(button);
                            System.out.println("✅ Clicked OK in dialog");
                            Thread.sleep(2000);
                            return;
                        }
                    }
                }
            }
            
            System.err.println("❌ No confirmation popup found");
            
        } catch (Exception e) {
            System.err.println("❌ Failed to confirm deletion: " + e.getMessage());
        }
    }
    
    public void deleteSelectedMessages() {
        try {
            System.out.println("Deleting selected messages...");
            
            // Click delete button
            clickDeleteSelectedButton();
            
            // Confirm deletion in popup
            confirmDeletionInPopup();
            
            System.out.println("✅ Deletion process completed");
            Thread.sleep(3000);
            
        } catch (Exception e) {
            System.err.println("❌ Failed to delete messages: " + e.getMessage());
        }
    }
    
    public void selectAllMessages() {
        try {
            System.out.println("Selecting all messages...");
            
            // First check if there's a "Select All" checkbox
            try {
                WebElement selectAll = driver.findElement(By.xpath("//*[contains(text(),'Select All')]/preceding-sibling::input[@type='checkbox']"));
                if (selectAll != null && selectAll.isDisplayed()) {
                    if (!selectAll.isSelected()) {
                        clickWithJavaScript(selectAll);
                        System.out.println("✅ Clicked 'Select All' checkbox");
                        Thread.sleep(2000);
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("No 'Select All' checkbox found, selecting individually...");
            }
            
            // Select all individual checkboxes
            List<WebElement> checkboxes = driver.findElements(allCheckboxes);
            int selected = 0;
            
            for (WebElement checkbox : checkboxes) {
                try {
                    if (!checkbox.isSelected()) {
                        clickWithJavaScript(checkbox);
                        selected++;
                        Thread.sleep(100); // Small delay
                    }
                } catch (Exception e) {
                    // Skip stale elements
                }
            }
            
            System.out.println("✅ Selected " + selected + " messages individually");
            Thread.sleep(1000);
            
        } catch (Exception e) {
            System.err.println("❌ Failed to select all messages: " + e.getMessage());
        }
    }

    // ========= UTILITY METHODS =========
    private void clickWithJavaScript(WebElement element) {
        try {
            // Scroll to element
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", 
                element
            );
            Thread.sleep(300);
            
            // Click with JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            
        } catch (Exception e) {
            try {
                // Fallback: Use Actions
                new Actions(driver).moveToElement(element).click().perform();
            } catch (Exception e2) {
                try {
                    // Last resort: Direct click
                    element.click();
                } catch (Exception e3) {
                    System.err.println("All click methods failed: " + e3.getMessage());
                }
            }
        }
    }
    
    public void takeScreenshot(String screenshotName) {
        try {
            File directory = new File("screenshots");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            String fileName = "screenshots/" + screenshotName + "_" + System.currentTimeMillis() + ".png";
            Files.write(new File(fileName).toPath(), screenshot);
            
            System.out.println("📸 Screenshot saved: " + fileName);
            
        } catch (Exception e) {
            System.err.println("❌ Failed to save screenshot: " + e.getMessage());
        }
    }
    
    // Debug method to see what's on the page
    public void debugPage() {
        try {
            System.out.println("=== DEBUG PAGE INFO ===");
            
            // Check for three dots button
            System.out.println("Three dots buttons: " + driver.findElements(threeDotsButton).size());
            
            // Check for checkboxes
            List<WebElement> checkboxes = driver.findElements(allCheckboxes);
            System.out.println("Total checkboxes: " + checkboxes.size());
            
            // Check for message rows
            System.out.println("Message rows: " + driver.findElements(messageRows).size());
            
            // Check for Cancel Selection button
            try {
                WebElement cancelBtn = driver.findElement(cancelSelectionButton);
                System.out.println("Cancel Selection button exists: " + cancelBtn.isDisplayed());
            } catch (Exception e) {
                System.out.println("No Cancel Selection button found");
            }
            
        } catch (Exception e) {
            System.out.println("Debug failed: " + e.getMessage());
        }
    }
}