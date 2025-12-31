package SuperAdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class SuperStudentListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public SuperStudentListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Locators
    private By studentsTableLocator = By.xpath("//table[.//button[contains(., 'View Details')]]");
    private By viewDetailsButtons = By.xpath("//button[contains(., 'View Details')]");
    
    // Updated pagination dropdown locators
    private By itemsPerPageDropdown = By.xpath("//div[contains(@class,'paginator-page-size')]//mat-select | //mat-form-field[contains(.,'Items per page')] | //select[contains(@aria-label, 'items per page')]");
    private By paginationDropdown = By.xpath("//mat-select[contains(@aria-label, 'Items per page') or contains(@role, 'combobox')]");
    
    // Wait for student list
    public boolean isStudentListDisplayed() {
        try {
            System.out.println("Waiting for student table to be visible...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(studentsTableLocator));

            System.out.println("Table located. Checking for 'View Details' buttons...");
            List<WebElement> buttons = driver.findElements(viewDetailsButtons);
            System.out.println("Number of 'View Details' buttons found: " + buttons.size());

            return !buttons.isEmpty();
        } catch (Exception e) {
            System.err.println("Student list not displayed: " + e.getMessage());
            return false;
        }
    }

    // Click a random View Details button
    public void clickRandomViewDetails() {
        List<WebElement> buttons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(viewDetailsButtons));
        if (buttons.isEmpty()) {
            throw new RuntimeException("No 'View Details' buttons found.");
        }
        Random rand = new Random();
        WebElement btn = buttons.get(rand.nextInt(buttons.size()));
        
        // Scroll to the button first
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
    }

    // Enhanced method to select items per page
    public void selectItemsPerPage(String value) {
        try {
            System.out.println("Selecting '" + value + "' items per page...");
            
            // First, scroll to the bottom where pagination usually is
            scrollToBottom();
            
            // Try multiple dropdown locators
            WebElement dropdown = findPaginationDropdown();
            
            if (dropdown == null) {
                throw new RuntimeException("Pagination dropdown not found");
            }

            // Click the dropdown
            clickWithRetry(dropdown);
            System.out.println("Dropdown clicked successfully.");

            // Select the option
            selectDropdownOption(value);

            // Wait for table to refresh with more items
            waitForTableRefresh();
            
            // Verify the change worked
            verifyItemsPerPageChanged(value);

            System.out.println("Successfully selected '" + value + "' items per page.");

        } catch (Exception e) {
            System.err.println("Failed to select items per page: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private WebElement findPaginationDropdown() {
        List<By> dropdownLocators = List.of(
            By.xpath("//mat-select[contains(@aria-label, 'Items per page')]"),
            By.xpath("//div[contains(@class,'paginator-page-size')]//mat-select"),
            By.xpath("//mat-form-field[contains(.,'Items per page')]"),
            By.xpath("//select[contains(@aria-label, 'items per page')]"),
            By.xpath("//mat-select[contains(@role, 'combobox')]"),
            By.xpath("//div[contains(@class,'mat-paginator-page-size')]//mat-select")
        );

        for (By locator : dropdownLocators) {
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                if (element.isDisplayed()) {
                    System.out.println("Found pagination dropdown with locator: " + locator);
                    return element;
                }
            } catch (Exception e) {
                System.out.println("Dropdown not found with locator: " + locator);
            }
        }
        return null;
    }

    private void clickWithRetry(WebElement element) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", element);
                Thread.sleep(1000);
                
                // Try JavaScript click first
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                
                // Verify dropdown opened by checking for options
                Thread.sleep(2000);
                if (areDropdownOptionsVisible()) {
                    return;
                }
            } catch (Exception e) {
                System.out.println("Click attempt " + attempt + " failed: " + e.getMessage());
                if (attempt == 3) {
                    throw new RuntimeException("All click attempts failed");
                }
            }
        }
    }

    private boolean areDropdownOptionsVisible() {
        try {
            By optionsLocator = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
            List<WebElement> options = driver.findElements(optionsLocator);
            return !options.isEmpty() && options.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void selectDropdownOption(String value) {
        List<By> optionLocators = List.of(
            By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option//span[normalize-space()='" + value + "']"),
            By.xpath("//div[contains(@class,'cdk-overlay-container')]//mat-option//span[normalize-space()='" + value + "']"),
            By.xpath("//mat-option//span[contains(text(),'" + value + "')]")
        );

        for (By locator : optionLocators) {
            try {
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(locator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                System.out.println("Selected option: " + value);
                return;
            } catch (Exception e) {
                System.out.println("Option not found with locator: " + locator);
            }
        }
        throw new RuntimeException("Could not select option: " + value);
    }

    private void scrollToBottom() {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Scroll to bottom failed: " + e.getMessage());
        }
    }

    private void waitForTableRefresh() {
        try {
            // Wait for table to potentially reload
            Thread.sleep(3000);
            
            // Wait for View Details buttons to be present
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(viewDetailsButtons));
        } catch (Exception e) {
            System.out.println("Table refresh wait completed with exception: " + e.getMessage());
        }
    }

    private void verifyItemsPerPageChanged(String expectedValue) {
        try {
            // Wait a bit for the table to update
            Thread.sleep(3000);
            
            List<WebElement> buttons = driver.findElements(viewDetailsButtons);
            System.out.println("After changing to " + expectedValue + " items, found " + buttons.size() + " View Details buttons");
            
            // You can add more verification logic here if needed
        } catch (Exception e) {
            System.out.println("Verification failed: " + e.getMessage());
        }
    }

    // Method to get current number of visible students
    public int getVisibleStudentCount() {
        List<WebElement> buttons = driver.findElements(viewDetailsButtons);
        return buttons.size();
    }
}