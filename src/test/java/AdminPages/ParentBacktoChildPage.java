package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ParentBacktoChildPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Parent/Guardian form fields
    @FindBy(name = "firstName")
    private WebElement firstNameInput;

    @FindBy(name = "lastName")
    private WebElement lastNameInput;

    @FindBy(name = "relationship")
    private WebElement relationshipSelect;

    // Back to Child Info button - located at the bottom
    @FindBy(xpath = "//button[contains(text(),'Back to Child Info')]")
    private WebElement backToChildInfoButton;

    // Alternative locators for the back button
    @FindBy(xpath = "//button[normalize-space()='Back to Child Info']")
    private WebElement backToChildInfoButtonAlt;

    public ParentBacktoChildPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void enterFirstName(String firstName) {
        try {
            wait.until(ExpectedConditions.visibilityOf(firstNameInput)).sendKeys(firstName);
        } catch (Exception e) {
            System.out.println("Error entering first name: " + e.getMessage());
            throw e;
        }
    }

    public void enterRandomFirstName() {
        String[] firstNames = {"John", "Jane", "Michael", "Sarah", "David", "Emily"};
        String randomName = firstNames[(int) (Math.random() * firstNames.length)];
        enterFirstName(randomName);
        System.out.println("Entered first name: " + randomName);
    }

    public void enterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOf(lastNameInput)).sendKeys(lastName);
    }

    public void enterRandomLastName() {
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia"};
        String randomName = lastNames[(int) (Math.random() * lastNames.length)];
        enterLastName(randomName);
        System.out.println("Entered last name: " + randomName);
    }

    public void selectRandomRelationship() {
        try {
            wait.until(ExpectedConditions.visibilityOf(relationshipSelect));
            
            // If it's a dropdown, you might need to handle it differently
            relationshipSelect.click();
            
            // Example for dropdown selection - adjust based on your actual HTML
            WebElement fatherOption = driver.findElement(By.xpath("//option[contains(text(),'Father')]"));
            fatherOption.click();
            
            System.out.println("Selected relationship: Father");
        } catch (Exception e) {
            System.out.println("Error selecting relationship: " + e.getMessage());
        }
    }

    public void clickBackToChildInfo() {
        try {
            System.out.println("Attempting to click Back to Child Info button...");
            
            // Scroll to the bottom of the page to make the button visible
            scrollToBottom();
            
            // Wait for the button to be visible and clickable
            WebElement backButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Back to Child Info')]")));
            
            // Scroll specifically to the button to ensure it's in view
            scrollToElement(backButton);
            
            // Click using JavaScript for reliability
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", backButton);
            
            System.out.println("Successfully clicked Back to Child Info button");
            
        } catch (Exception e) {
            System.out.println("Standard back button click failed, trying alternative methods...");
            clickBackToChildInfoWithFallback();
        }
    }

    private void clickBackToChildInfoWithFallback() {
        String[] buttonXPaths = {
        		"(//button[@class=\"mat-focus-indicator mat-stepper-previous btn btn-primary back-btn mat-button mat-button-base\"])[1]",
            "//button[contains(text(),'Back to Child Info')]",
            "//button[normalize-space()='Back to Child Info']",
            "//a[contains(text(),'Back to Child Info')]",
            "//input[@value='Back to Child Info']",
            "//*[contains(text(),'Back to Child Info')]",
            "//button[contains(@class,'back')]",
            "//button[contains(@id,'back')]"
        };
        
        for (String xpath : buttonXPaths) {
            try {
                System.out.println("Trying XPath: " + xpath);
                
                // Scroll to bottom first
                scrollToBottom();
                
                WebElement backButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                scrollToElement(backButton);
                
                // Try regular click first
                try {
                    backButton.click();
                    System.out.println("Clicked using regular click with XPath: " + xpath);
                    return;
                } catch (Exception e1) {
                    // If regular click fails, use JavaScript
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].click();", backButton);
                    System.out.println("Clicked using JavaScript with XPath: " + xpath);
                    return;
                }
                
            } catch (Exception e) {
                System.out.println("Failed with XPath " + xpath + ": " + e.getMessage());
                // Continue to next XPath
            }
        }
        
        throw new RuntimeException("Could not find or click Back to Child Info button with any locator");
    }

    private void scrollToBottom() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            System.out.println("Scrolled to bottom of page");
            
            // Wait a moment for the scroll to complete
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("Error scrolling to bottom: " + e.getMessage());
        }
    }

    private void scrollToElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
            System.out.println("Scrolled to element");
            
            // Wait for scroll to complete
            Thread.sleep(300);
        } catch (Exception e) {
            System.out.println("Error scrolling to element: " + e.getMessage());
        }
    }

    public void waitForPageToLoad() {
        try {
            // Wait for key elements on the parent/guardian page
            wait.until(ExpectedConditions.visibilityOf(firstNameInput));
            System.out.println("Parent/Guardian page loaded successfully");
        } catch (Exception e) {
            System.out.println("Parent page load wait completed with warning: " + e.getMessage());
        }
    }

    public boolean isOnParentGuardianPage() {
        try {
            String currentUrl = driver.getCurrentUrl().toLowerCase();
            String pageSource = driver.getPageSource().toLowerCase();
            
            boolean isParentPage = pageSource.contains("parent") || 
                                  pageSource.contains("guardian") ||
                                  currentUrl.contains("parent") || 
                                  currentUrl.contains("guardian");
            
            System.out.println("Is on parent/guardian page: " + isParentPage);
            return isParentPage;
        } catch (Exception e) {
            System.out.println("Error checking parent page: " + e.getMessage());
            return false;
        }
    }
}