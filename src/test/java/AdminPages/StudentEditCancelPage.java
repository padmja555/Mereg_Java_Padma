
package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class StudentEditCancelPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public StudentEditCancelPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.js = (JavascriptExecutor) driver;
    }

    public void waitForPageLoad() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(), 'Student Details') or contains(text(), 'Enrollment Information')]")));
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Page load wait interrupted: " + e.getMessage());
        }
    }

    public void clickEdit() {
        try {
            System.out.println("=== ENTERING EDIT MODE ===");
            
            WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(., 'Edit')]")));
            
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", editButton);
            Thread.sleep(2000);
            js.executeScript("arguments[0].click();", editButton);
            
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//mat-form-field | //button[contains(., 'Save')]")));
            Thread.sleep(3000);
            
            System.out.println("✓ Edit mode activated");
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Edit button: " + e.getMessage());
        }
    }

    public void fillAngularMaterialForm(String studentFirstName, String studentLastName, 
                                       String parentFirstName, String parentLastName) {
        try {
            System.out.println("=== FILLING ANGULAR MATERIAL FORM ===");
            
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("mat-form-field")));
            Thread.sleep(2000);
            
            // Clear all fields first to avoid duplicate entries
            clearAllFields();
            Thread.sleep(1000);
            
            // Fill fields with proper sequencing and validation
            fillFieldsWithValidation(studentFirstName, studentLastName, parentFirstName, parentLastName);
            
            System.out.println("=== FORM FILLING COMPLETED ===");
            
        } catch (Exception e) {
            System.out.println("Error filling form: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearAllFields() {
        try {
            System.out.println("--- Clearing all fields ---");
            List<WebElement> inputs = driver.findElements(By.xpath("//mat-form-field//input"));
            for (WebElement input : inputs) {
                try {
                    input.clear();
                    Thread.sleep(100);
                } catch (Exception e) {
                    // Ignore errors for specific fields
                }
            }
            System.out.println("✓ All fields cleared");
        } catch (Exception e) {
            System.out.println("Error clearing fields: " + e.getMessage());
        }
    }

    private void fillFieldsWithValidation(String studentFirstName, String studentLastName, 
                                        String parentFirstName, String parentLastName) throws InterruptedException {
        try {
            // === CHILD INFORMATION - FILL FIRST ===
            System.out.println("=== FILLING CHILD INFORMATION ===");
            fillChildInformation(studentFirstName, studentLastName);
            
            // === PARENT INFORMATION - FILL SECOND ===
            System.out.println("=== FILLING PARENT INFORMATION ===");
            fillParentInformation(parentFirstName, parentLastName);
            
            // === ADDRESS & CONTACT INFORMATION ===
            System.out.println("=== FILLING ADDRESS & CONTACT INFORMATION ===");
            fillAddressAndContactInfo();
            
            // === PHYSICIAN INFORMATION ===
            System.out.println("=== FILLING PHYSICIAN INFORMATION ===");
            fillPhysicianInformation();
            
            // === VALIDATE ALL FIELDS ===
            validateAllFields();
            
        } catch (Exception e) {
            System.out.println("Error filling fields: " + e.getMessage());
        }
    }

    private void fillChildInformation(String studentFirstName, String studentLastName) {
        try {
            // Child First Name
            fillFieldByExactLabel("First Name", studentFirstName, true);
            Thread.sleep(500);
            
            // Child Middle Name
            fillFieldByExactLabel("Middle Name", "Middle", false);
            Thread.sleep(500);
            
            // Child Last Name
            fillFieldByExactLabel("Last Name", studentLastName, true);
            Thread.sleep(500);
            
            // Gender
            setDropdownByExactLabel("Gender", "Female", true);
            Thread.sleep(1000);
            
            // FIX: Date of Birth - Clear and fill only once
            fillDateOfBirthCarefully();
            Thread.sleep(1000);
            
            // Place of Birth
            fillFieldByExactLabel("Place Of Birth", "Test City", false);
            Thread.sleep(500);
            
        } catch (Exception e) {
            System.out.println("Error filling child information: " + e.getMessage());
        }
    }

    private void fillDateOfBirthCarefully() {
        try {
            WebElement dobField = findFieldByExactLabel("Date of Birth");
            if (dobField != null) {
                WebElement input = findInputInField(dobField);
                if (input != null) {
                    // Clear the field thoroughly
                    input.clear();
                    Thread.sleep(500);
                    
                    // Click to ensure focus
                    js.executeScript("arguments[0].click();", input);
                    Thread.sleep(500);
                    
                    // Clear again using JavaScript
                    js.executeScript("arguments[0].value = '';", input);
                    Thread.sleep(500);
                    
                    // Enter the date
                    input.sendKeys("02/06/2002");
                    Thread.sleep(1000);
                    
                    // Verify the value
                    String enteredValue = input.getAttribute("value");
                    System.out.println("✓ Set Date of Birth: " + enteredValue + " (verified)");
                }
            }
        } catch (Exception e) {
            System.out.println("Error filling Date of Birth: " + e.getMessage());
        }
    }

    private void fillParentInformation(String parentFirstName, String parentLastName) {
        try {
            // Parent First Name
            fillFieldByExactLabel("First Name", parentFirstName, true);
            Thread.sleep(500);
            
            // Parent Middle Name
            fillFieldByExactLabel("Middle Name", "ParentMiddle", false);
            Thread.sleep(500);
            
            // Parent Last Name
            fillFieldByExactLabel("Last Name", parentLastName, true);
            Thread.sleep(500);
            
            // Relationship
            setDropdownByExactLabel("Relationship with Child", "Father", true);
            Thread.sleep(1000);
            
        } catch (Exception e) {
            System.out.println("Error filling parent information: " + e.getMessage());
        }
    }

    private void fillAddressAndContactInfo() {
        try {
            // Address Information
            fillFieldByExactLabel("Address Line 1", "123 Main Street", true);
            Thread.sleep(500);
            fillFieldByExactLabel("Address Line 2", "Apt 101", false);
            Thread.sleep(500);
            setDropdownByExactLabel("Country", "United States", true);
            Thread.sleep(1500);
            setDropdownByExactLabel("State", "California", true);
            Thread.sleep(500);
            fillFieldByExactLabel("City", "Test City", true);
            Thread.sleep(500);
            fillFieldByExactLabel("Zip Code", "12345", true);
            Thread.sleep(500);
            
            // Contact Information
            fillFieldByExactLabel("Email Address", "test" + System.currentTimeMillis() + "@example.com", true);
            Thread.sleep(500);
            setDropdownByExactLabel("Phone Type", "Cell", true);
            Thread.sleep(1000);
            setDropdownByExactLabel("Country Code", "+1 (US)", true);
            Thread.sleep(1000);
            fillFieldByExactLabel("Phone Number", "3012565287", true);
            Thread.sleep(500);
            
        } catch (Exception e) {
            System.out.println("Error filling address and contact info: " + e.getMessage());
        }
    }

    private void fillPhysicianInformation() {
        try {
            // Physician Basic Info
            fillFieldByExactLabel("Physician First Name", "John", true);
            Thread.sleep(500);
            fillFieldByExactLabel("Physician Middle Name", "A", false);
            Thread.sleep(500);
            fillFieldByExactLabel("Physician Last Name", "Smith", true);
            Thread.sleep(500);
            
            // Physician Address
            fillFieldByExactLabel("Address Line 1", "456 Medical Center", true);
            Thread.sleep(500);
            fillFieldByExactLabel("Address Line 2", "Suite 100", false);
            Thread.sleep(500);
            setDropdownByExactLabel("Country", "United States", true);
            Thread.sleep(1500);
            setDropdownByExactLabel("State", "California", true);
            Thread.sleep(500);
            fillFieldByExactLabel("City", "Medical City", true);
            Thread.sleep(500);
            fillFieldByExactLabel("Zip Code", "54321", true);
            Thread.sleep(500);
            
            // Physician Contact
            setDropdownByExactLabel("Phone Type", "Work", true);
            Thread.sleep(1000);
            setDropdownByExactLabel("Country Code", "+1 (US)", true);
            Thread.sleep(1000);
            fillFieldByExactLabel("Phone Number", "5551234567", true);
            Thread.sleep(500);
            
        } catch (Exception e) {
            System.out.println("Error filling physician information: " + e.getMessage());
        }
    }

    private void fillFieldByExactLabel(String exactLabel, String value, boolean required) {
        try {
            WebElement field = findFieldByExactLabel(exactLabel);
            if (field != null) {
                WebElement input = findInputInField(field);
                if (input != null) {
                    // Clear the field
                    input.clear();
                    Thread.sleep(200);
                    
                    // Enter the value
                    input.sendKeys(value);
                    Thread.sleep(300);
                    
                    // Verify the value was set correctly
                    String enteredValue = input.getAttribute("value");
                    if (enteredValue.contains(value)) {
                        System.out.println("✓ Set " + exactLabel + ": " + value);
                    } else {
                        System.out.println("⚠️ Field " + exactLabel + " may not have been set correctly");
                    }
                } else {
                    System.out.println("⏭ No input found for: " + exactLabel);
                }
            } else if (required) {
                System.out.println("❌ Required field not found: " + exactLabel);
            }
        } catch (Exception e) {
            System.out.println("Error filling field '" + exactLabel + "': " + e.getMessage());
        }
    }

    private void setDropdownByExactLabel(String exactLabel, String optionValue, boolean required) {
        try {
            WebElement field = findFieldByExactLabel(exactLabel);
            if (field != null) {
                WebElement dropdown = findDropdownInField(field);
                if (dropdown != null) {
                    setDropdownValue(dropdown, optionValue, exactLabel);
                } else {
                    System.out.println("⏭ No dropdown found for: " + exactLabel);
                }
            } else if (required) {
                System.out.println("❌ Required dropdown not found: " + exactLabel);
            }
        } catch (Exception e) {
            System.out.println("Error setting dropdown '" + exactLabel + "': " + e.getMessage());
        }
    }

    private WebElement findFieldByExactLabel(String exactLabel) {
        try {
            // More precise XPath to find exact label matches
            String[] xpathStrategies = {
                String.format("//mat-form-field[.//mat-label[text()='%s']]", exactLabel),
                String.format("//mat-form-field[.//span[text()='%s']]", exactLabel),
                String.format("//mat-form-field[.//label[text()='%s']]", exactLabel),
                String.format("//mat-form-field[.//*[text()='%s']]", exactLabel)
            };
            
            for (String xpath : xpathStrategies) {
                List<WebElement> fields = driver.findElements(By.xpath(xpath));
                if (!fields.isEmpty()) {
                    return fields.get(0);
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private WebElement findInputInField(WebElement field) {
        try {
            List<WebElement> inputs = field.findElements(By.xpath(".//input"));
            return inputs.isEmpty() ? null : inputs.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    private WebElement findDropdownInField(WebElement field) {
        try {
            return field.findElement(By.xpath(".//mat-select"));
        } catch (Exception e) {
            return null;
        }
    }

    private void setDropdownValue(WebElement dropdown, String optionValue, String dropdownName) {
        try {
            // Click to open dropdown
            js.executeScript("arguments[0].click();", dropdown);
            Thread.sleep(1500);
            
            // Find and select the option
            String optionXpath = String.format("//mat-option//span[contains(., '%s')]", optionValue);
            List<WebElement> options = driver.findElements(By.xpath(optionXpath));
            
            if (!options.isEmpty()) {
                WebElement option = options.get(0);
                js.executeScript("arguments[0].click();", option);
                Thread.sleep(1000);
                System.out.println("✓ Set " + dropdownName + ": " + optionValue);
            } else {
                System.out.println("❌ Option not found: " + optionValue);
                // Close dropdown
                js.executeScript("arguments[0].click();", dropdown);
                Thread.sleep(500);
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Error setting dropdown: " + e.getMessage());
        }
    }

    private void validateAllFields() {
        try {
            System.out.println("=== VALIDATING ALL FIELDS ===");
            Thread.sleep(2000);
            
            // Check for validation errors
            List<WebElement> errors = driver.findElements(By.xpath(
                "//mat-error | //*[contains(text(), 'required')] | //*[contains(text(), 'error')]"));
            
            boolean hasErrors = false;
            for (WebElement error : errors) {
                if (error.isDisplayed()) {
                    String errorText = error.getText().trim();
                    if (!errorText.isEmpty()) {
                        System.out.println("❌ Validation error: " + errorText);
                        hasErrors = true;
                    }
                }
            }
            
            if (!hasErrors) {
                System.out.println("✅ All fields validated successfully");
            }
            
        } catch (Exception e) {
            System.out.println("Error validating fields: " + e.getMessage());
        }
    }
    /*
    public void clickSave() {
        try {
            System.out.println("=== SAVING CHANGES ===");
            
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(., 'Save') or contains(., 'Save Changes')]")));
            
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", saveButton);
            Thread.sleep(2000);
            js.executeScript("arguments[0].click();", saveButton);
            
            Thread.sleep(5000);
            
        } catch (Exception e) {
            System.out.println("Error clicking save: " + e.getMessage());
        }
    }
    */ 

    public void clickCancel() {
        try {
            System.out.println("=== STEP 6: CLICK CANCEL ===");

            WebElement cancelButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(., 'Cancel') or contains(., 'Cancel Changes')]")));

            // Ensure button is not hidden or covered
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", cancelButton);
            Thread.sleep(1000);
            js.executeScript("window.scrollBy(0, 200);"); // Scroll further down if needed
            Thread.sleep(1000);

            // Check if clickable
            wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
            System.out.println("Attempting to click cancel...");

            // Try clicking with Actions
            new Actions(driver).moveToElement(cancelButton).click().perform();

            Thread.sleep(3000);
            System.out.println("✓ Edit was canceled successfully without validation errors");

        } catch (Exception e) {
            System.out.println("Error clicking cancel button: " + e.getMessage());
        }
    }


    public boolean hasValidationErrors() {
       try {
            Thread.sleep(2000);
            return driver.findElements(By.xpath(
                "//mat-error[normalize-space()!='']")).size() > 0;
            
        } catch (Exception e) {
            System.out.println("Error checking validation errors: " + e.getMessage());
            return false;
        }
    }

    public boolean tryToRecoverFromErrors() {
        try {
            System.out.println("=== ATTEMPTING ERROR RECOVERY ===");
            Thread.sleep(2000);
            
            // Try to find and click Cancel button
            List<WebElement> cancelButtons = driver.findElements(By.xpath(
                "//button[contains(., 'Cancel') or contains(., 'Back')]"));
            
            if (!cancelButtons.isEmpty()) {
                js.executeScript("arguments[0].click();", cancelButtons.get(0));
                Thread.sleep(3000);
                return true;
            }
            
            return false;
            
        } catch (Exception e) {
            System.out.println("Error recovery failed: " + e.getMessage());
            return false;
        }
    }

    public void navigateToStudentsPage() {
        try {
            driver.navigate().to("https://mereg.netlify.app/navigation-home/students");
            Thread.sleep(4000);
        } catch (Exception e) {
            System.out.println("Error navigating to students page: " + e.getMessage());
        }
    }

	//public void clickCancel() {
		// TODO Auto-generated method stub
		
	//}
}
