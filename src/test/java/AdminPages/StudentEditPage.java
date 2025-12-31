package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class StudentEditPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public StudentEditPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Student Details') or contains(text(), 'Edit')]")));
    }

    public void clickEdit() {
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(., 'Edit')]")));
        js.executeScript("arguments[0].click();", editButton);
        
        // Wait for form to be editable
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//mat-form-field//input | //mat-select")));
    }

    public void fillBasicStudentInfo(String firstName, String lastName) {
        System.out.println("=== FILLING BASIC STUDENT INFO ===");
        
        // Fill basic fields that are commonly present
        fillFieldIfPresent("First Name", firstName);
        fillFieldIfPresent("Last Name", lastName);
        
        // Fill other common fields optionally
        fillFieldIfPresent("Middle Name", "Test");
        selectDropdownIfPresent("Gender", "Female");
    }

    public void fillAddressInfo() {
        System.out.println("=== FILLING ADDRESS INFO ===");
        
        // Based on screenshot structure
        fillFieldIfPresent("Address Line 1", "123 Main Street");
        fillFieldIfPresent("Address Line 2", "Apt 101");
        
        selectDropdownIfPresent("Country", "United States");
        selectDropdownIfPresent("State", "California"); 
        fillFieldIfPresent("City", "Test City");
        fillFieldIfPresent("Zip Code", "12345");
    }

    public void fillPhoneInformation() {
        System.out.println("=== FILLING PHONE INFORMATION ===");
        
        // Primary phone (from screenshot)
        selectDropdownIfPresent("Phone Type", "Cell");
        selectDropdownIfPresent("Country Code", "+1 (US)");
        fillFieldIfPresent("Phone Number", "3012565287");
        
        // Alternate phone (from screenshot)
        fillAlternatePhoneInfo();
    }

    public boolean testAlternatePhoneFields() {
        System.out.println("=== TESTING ALTERNATE PHONE FIELDS ===");
        return fillAlternatePhoneInfo();
    }

    private boolean fillAlternatePhoneInfo() {
        boolean filledAnyField = false;
        
        // Try different label variations for alternate phone
        String[][] alternateFields = {
            {"Alternate Phone Type", "Home"},
            {"Alt Phone Type", "Home"}, 
            {"Secondary Phone Type", "Home"},
            {"Alternate Country Code", "+44 (UK)"},
            {"Alt Country Code", "+44 (UK)"},
            {"Secondary Country Code", "+44 (UK)"},
            {"Alternate Phone Number", "442012345678"},
            {"Alt Phone Number", "442012345678"},
            {"Secondary Phone Number", "442012345678"}
        };
        
        for (String[] field : alternateFields) {
            String label = field[0];
            String value = field[1];
            
            if (label.contains("Type") || label.contains("Code")) {
                if (selectDropdownIfPresent(label, value)) {
                    filledAnyField = true;
                }
            } else {
                if (fillFieldIfPresent(label, value)) {
                    filledAnyField = true;
                }
            }
        }
        
        return filledAnyField;
    }

    private boolean fillFieldIfPresent(String label, String value) {
        try {
            WebElement field = findFieldByLabel(label);
            if (field != null) {
                WebElement input = field.findElement(By.xpath(".//input"));
                input.clear();
                input.sendKeys(value);
                System.out.println("✓ Filled " + label + ": " + value);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean selectDropdownIfPresent(String label, String option) {
        try {
            WebElement field = findFieldByLabel(label);
            if (field != null) {
                WebElement dropdown = field.findElement(By.xpath(".//mat-select"));
                dropdown.click();
                
                // Select the option
                WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//mat-option//span[contains(., '" + option + "')]")));
                optionElement.click();
                
                System.out.println("✓ Selected " + label + ": " + option);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private WebElement findFieldByLabel(String label) {
        try {
            // Try different XPath strategies for Angular Material fields
            String[] xpaths = {
                String.format("//mat-form-field[.//mat-label[text()='%s']]", label),
                String.format("//mat-form-field[.//span[text()='%s']]", label),
                String.format("//*[contains(@class, 'mat-form-field')][.//*[text()='%s']]", label)
            };
            
            for (String xpath : xpaths) {
                List<WebElement> elements = driver.findElements(By.xpath(xpath));
                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    return elements.get(0);
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public void clickSave() {
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(., 'Save')]")));
        saveButton.click();
        
        // Wait for save to complete
        wait.until(ExpectedConditions.or(
            ExpectedConditions.urlContains("students"),
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(), 'success') or contains(text(), 'saved')]"))
        ));
    }

    public boolean isSaveSuccessful() {
        try {
            // Check for success indicators
            boolean isOnStudentsPage = driver.getCurrentUrl().contains("students");
            boolean hasSuccessMessage = !driver.findElements(
                By.xpath("//*[contains(text(), 'success') or contains(text(), 'saved')]")).isEmpty();
            
            return isOnStudentsPage || hasSuccessMessage;
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateToStudentsPage() {
        try {
            driver.get("https://mereg.netlify.app/navigation-home/students");
            waitForPageLoad();
        } catch (Exception e) {
            System.out.println("Error navigating to students page: " + e.getMessage());
        }
    }
}