package SuperAdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SuperDateFilterPage {

    private WebDriver driver;
    private WebDriverWait wait; 

    // Locators
    // *** FIX 1: Using 'contains' on placeholder to be robust against "Date From" vs "From Date" ***
    private By dateFromInput = By.xpath("//input[contains(@placeholder, 'From')]"); 
    private By dateToInput = By.xpath("//input[contains(@placeholder, 'To')]");   
    private By paymentStatusDropdown = By.xpath("//label[contains(text(),'Payment Status')]/following-sibling::select");
    private By paymentMethodDropdown = By.xpath("//label[contains(text(),'Payment Method')]/following-sibling::select");
    private By applyFiltersButton = By.xpath("//button[contains(text(),'Apply Filters')]");
    // Locator for the table/container displaying history
    private By paymentHistoryTable = By.xpath("//div[@class='table-responsive' or .//table[contains(@class,'payment-history')]]");

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    public SuperDateFilterPage(WebDriver driver) {
        this.driver = driver;
        // *** FIX 2: Increased WebDriverWait to 15 seconds to account for slow page rendering ***
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); 
    }

    public void openPage() {
        driver.get("https://mereg-hotfix.netlify.app/navigation-home/management");
    }

    // Public methods now correctly pass LocalDate to the helper
    public void setDateFrom(LocalDate fromDate) {
        System.out.println("Setting Date From: " + formatter.format(fromDate));
        enterDateWithRetry(dateFromInput, fromDate);
    }

    public void setDateTo(LocalDate toDate) {
        System.out.println("Setting Date To: " + formatter.format(toDate));
        enterDateWithRetry(dateToInput, toDate);
    }

    /**
     * Helper method to reliably set the date using JavaScript Executor and retries.
     * @param locator The By locator for the date input field.
     * @param date The LocalDate object to be entered.
     */
    private void enterDateWithRetry(By locator, LocalDate date) {
        String dateString = formatter.format(date);
        
        // *** FIX 3: Simplified wait to focus on a single, robust condition (clickable) ***
        WebElement dateField = wait.until(ExpectedConditions.elementToBeClickable(locator));
        
        JavascriptExecutor js = (JavascriptExecutor) driver;

        int attempts = 0;
        while (attempts < 3) {
            try {
                // 1. Clear the field reliably using JS
                js.executeScript("arguments[0].value = '';", dateField);
                
                // 2. Inject the date string directly using JavaScript
                js.executeScript("arguments[0].value = arguments[1];", dateField, dateString);

                // 3. Trigger validation by tabbing out
                dateField.sendKeys(Keys.TAB); 
                
                // Wait briefly for UI to update/validate
                Thread.sleep(500);

                // 4. Verify if input retained the value
                String currentValue = dateField.getAttribute("value").trim();
                
                if (dateString.equals(currentValue)) { 
                    System.out.println("   --> Successfully set date to: " + dateString);
                    return; // success
                }

                attempts++;
            } catch (Exception e) {
                attempts++;
                System.out.println("Retrying date entry... (" + attempts + ")");
            }
        }
        // Throw an exception if all attempts fail
        throw new RuntimeException("Failed to enter date '" + dateString + "' after 3 attempts.");
    }
    
    public void selectPaymentStatus(String status) {
        wait.until(ExpectedConditions.elementToBeClickable(paymentStatusDropdown));
        Select select = new Select(driver.findElement(paymentStatusDropdown));
        select.selectByVisibleText(status);
    }

    public void selectPaymentMethod(String method) {
        wait.until(ExpectedConditions.elementToBeClickable(paymentMethodDropdown));
        Select select = new Select(driver.findElement(paymentMethodDropdown));
        select.selectByVisibleText(method);
    }

    public void clickApplyFilters() {
        wait.until(ExpectedConditions.elementToBeClickable(applyFiltersButton)).click();
    }

    // Method to verify if payment history is displayed
	public boolean verifyPaymentHistoryDisplayed() {
        try {
            // Re-using the class-level wait instance
            WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(paymentHistoryTable));
            
            // Check if the table has data rows (more than just the header)
            int rowCount = table.findElements(By.tagName("tr")).size();

            // Return true if visible and has more than 1 row (header + data)
            return rowCount > 1;

        } catch (Exception e) {
            // If the element is not found within the timeout, assume it's not displayed
            return false;
        }
	}
}
