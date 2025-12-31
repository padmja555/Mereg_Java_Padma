package SuperAdminPage;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class PaymentFilterPage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    // ===== CORRECTED LOCATORS - Based on your application structure =====
    private By dateFromInput = By.xpath("//input[@placeholder='Due from' or contains(@class, 'date') or @name='dueFrom']");
    private By dateToInput = By.xpath("//input[@placeholder='Due To' or contains(@class, 'date') or @name='dueTo']");
    private By applyFilterBtn = By.xpath("//button[normalize-space()='Apply Filters']");
    private By paymentHistoryTable = By.xpath("//table | //div[contains(@class, 'table')] | //*[contains(text(), 'Plan Details')]");

    public PaymentFilterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    // ===== Wait for page to load =====
    public void waitForPageLoad() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(applyFilterBtn));
            System.out.println("✅ Payment Reports page loaded successfully");
        } catch (Exception e) {
            System.out.println("❌ Page not loaded properly: " + e.getMessage());
        }
    }

    // ===== Set From Date =====
    public void setFromDate() {
        try {
            // Try multiple locator strategies
            WebElement fromInput = findDateField("from");
            
            if (fromInput != null) {
                // Clear existing value
                fromInput.clear();
                Thread.sleep(500);
                
                // Set new date
                fromInput.sendKeys("10/08/2025");
                System.out.println("✅ From Date set to: 10/08/2025");
                Thread.sleep(1000);
            } else {
                System.out.println("❌ Could not find From Date field");
                // Try JavaScript approach as fallback
                setDateViaJavaScript("10/08/2025", "from");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error setting From Date: " + e.getMessage());
        }
    }

    // ===== Set To Date =====
    public void setToDate() {
        try {
            // Try multiple locator strategies
            WebElement toInput = findDateField("to");
            
            if (toInput != null) {
                // Clear existing value
                toInput.clear();
                Thread.sleep(500);
                
                // Set new date
                toInput.sendKeys("10/12/2025");
                System.out.println("✅ To Date set to: 10/10/2025");
                Thread.sleep(1000);
            } else {
                System.out.println("❌ Could not find To Date field");
                // Try JavaScript approach as fallback
                setDateViaJavaScript("10/12/2025", "to");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error setting To Date: " + e.getMessage());
        }
    }

    // ===== Find date field using multiple strategies =====
    private WebElement findDateField(String fieldType) {
        try {
            // Strategy 1: Try placeholder-based locator
            String placeholder = fieldType.equals("from") ? "Due from" : "Due To";
            By placeholderLocator = By.xpath("//input[@placeholder='" + placeholder + "']");
            
            if (driver.findElements(placeholderLocator).size() > 0) {
                return driver.findElement(placeholderLocator);
            }
            
            // Strategy 2: Try by input index (usually first two inputs are date fields)
            int index = fieldType.equals("from") ? 0 : 1;
            By indexLocator = By.xpath("(//input)[" + (index + 1) + "]");
            
            if (driver.findElements(indexLocator).size() > index + 1) {
                return driver.findElement(indexLocator);
            }
            
            // Strategy 3: Try by containing text in nearby elements
            By nearbyLocator = By.xpath("//*[contains(text(), '" + placeholder + "')]//following::input[1]");
            if (driver.findElements(nearbyLocator).size() > 0) {
                return driver.findElement(nearbyLocator);
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error finding " + fieldType + " date field: " + e.getMessage());
        }
        return null;
    }

    // ===== Set date via JavaScript as fallback =====
    private void setDateViaJavaScript(String date, String fieldType) {
        try {
            // Try to find any date input fields and set value via JavaScript
            String script = """
                var inputs = document.getElementsByTagName('input');
                var targetIndex = %d;
                if (inputs.length > targetIndex) {
                    inputs[targetIndex].value = '%s';
                    // Trigger change event
                    var event = new Event('input', { bubbles: true });
                    inputs[targetIndex].dispatchEvent(event);
                    return true;
                }
                return false;
            """.formatted(fieldType.equals("from") ? 0 : 1, date);
            
            Boolean result = (Boolean) js.executeScript(script);
            if (result) {
                System.out.println("✅ " + fieldType.toUpperCase() + " Date set via JavaScript: " + date);
            } else {
                System.out.println("❌ Could not set date via JavaScript");
            }
        } catch (Exception e) {
            System.out.println("❌ JavaScript date setting failed: " + e.getMessage());
        }
    }

    // ===== Click Apply Filters =====
    public void clickApplyFilters() {
        try {
            WebElement applyBtn = wait.until(ExpectedConditions.elementToBeClickable(applyFilterBtn));
            applyBtn.click();
            System.out.println("✅ Apply Filters clicked");
            
            // Wait for results to load
            Thread.sleep(3000);
            
        } catch (Exception e) {
            System.out.println("❌ Failed to click Apply Filters: " + e.getMessage());
        }
    }

    // ===== Verify Payment Table Appears =====
    public boolean verifyPaymentHistoryDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(paymentHistoryTable));
            System.out.println("✅ Payment History displayed");
            return true;
        } catch (TimeoutException e) {
            System.out.println("❌ Payment History not visible");
            return false;
        }
    }

    // ===== Debug: Check element presence =====
    public void debugCheckElements() {
        System.out.println("=== Debug Element Check ===");
        
        // Check date fields with multiple strategies
        checkFieldPresence("Date From", "//input[@placeholder='Due from']");
        checkFieldPresence("Date To", "//input[@placeholder='Due To']");
        checkFieldPresence("First Input", "(//input)[1]");
        checkFieldPresence("Second Input", "(//input)[2]");
        
        System.out.println("Apply Filters button present: " + isElementPresent(applyFilterBtn));
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());
        
        // List all input fields on page
        listAllInputFields();
    }

    private void checkFieldPresence(String fieldName, String xpath) {
        try {
            boolean present = driver.findElements(By.xpath(xpath)).size() > 0;
            System.out.println(fieldName + " present: " + present + " [xpath: " + xpath + "]");
        } catch (Exception e) {
            System.out.println(fieldName + " check failed: " + e.getMessage());
        }
    }

    private void listAllInputFields() {
        try {
            java.util.List<WebElement> inputs = driver.findElements(By.tagName("input"));
            System.out.println("=== All Input Fields Found (" + inputs.size() + ") ===");
            for (int i = 0; i < inputs.size(); i++) {
                WebElement input = inputs.get(i);
                String placeholder = input.getAttribute("placeholder");
                String value = input.getAttribute("value");
                String name = input.getAttribute("name");
                System.out.println("Input[" + i + "]: placeholder='" + placeholder + "', value='" + value + "', name='" + name + "'");
            }
        } catch (Exception e) {
            System.out.println("Could not list input fields: " + e.getMessage());
        }
    }

    private boolean isElementPresent(By locator) {
        try {
            return driver.findElements(locator).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}