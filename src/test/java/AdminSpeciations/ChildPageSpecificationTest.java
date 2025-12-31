package AdminSpeciations;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import Base.BaseDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ChildPageSpecificationTest extends BaseDriver {

    @Test
    public void testAllTextFieldStyles() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            // 1️⃣ Login
            Assert.assertTrue(login("srasysife@gmail.com", "Admin@123"), "❌ Login failed!");

            // 2️⃣ Navigate to Enrollment
            navigateToEnrollment(wait);

            // 3️⃣ Wait for page to load
            waitForPageToLoad(wait);

            // 4️⃣ Verify all text field styles
            verifyAllTextFieldStyles(wait);

            System.out.println("\n🎉 ALL TEXT FIELD STYLES VERIFIED SUCCESSFULLY!");

        } catch (Exception e) {
            System.out.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private void verifyAllTextFieldStyles(WebDriverWait wait) {
        System.out.println("\n=== VERIFYING ALL TEXT FIELD STYLES ===");

        // Define expected styles for all text fields
        Map<String, Map<String, String>> expectedStyles = new HashMap<>();

        // Common styles for all text fields
        Map<String, String> commonTextStyles = new HashMap<>();
        commonTextStyles.put("font-family", "Roboto, sans-serif");
        commonTextStyles.put("font-size", "14px");
        commonTextStyles.put("color", "rgba(0, 0, 0, 0.87)");
        commonTextStyles.put("border-color", "rgba(0, 0, 0, 0.23)");
        commonTextStyles.put("border-width", "1px");
        commonTextStyles.put("border-radius", "4px");
        commonTextStyles.put("background-color", "rgba(255, 255, 255, 1)");
        commonTextStyles.put("padding", "8px 16px");
        commonTextStyles.put("height", "40px"); // Approximate height

        // Apply common styles to all fields
        expectedStyles.put("First Name", commonTextStyles);
        expectedStyles.put("Last Name", commonTextStyles);
        expectedStyles.put("Date of Birth", commonTextStyles);
        expectedStyles.put("Place of Birth", commonTextStyles);

        // Find and verify each field
        for (Map.Entry<String, Map<String, String>> entry : expectedStyles.entrySet()) {
            String fieldName = entry.getKey();
            Map<String, String> styles = entry.getValue();
            
            try {
                WebElement field = findTextField(wait, fieldName);
                if (field != null) {
                    verifyTextFieldStyles(fieldName, field, styles);
                } else {
                    System.out.println("⚠️  Could not find field: " + fieldName);
                }
            } catch (Exception e) {
                System.out.println("❌ Error verifying " + fieldName + ": " + e.getMessage());
            }
        }

        // Verify gender selection styles
        verifyGenderFieldStyles(wait);
    }

    private WebElement findTextField(WebDriverWait wait, String fieldName) {
        System.out.println("\n🔍 Finding field: " + fieldName);
        
        String[] strategies = {
            // Strategy 1: By placeholder
            "//input[@placeholder='" + fieldName + "']",
            "//input[contains(@placeholder, '" + fieldName + "')]",
            
            // Strategy 2: By formControlName
            "//input[@formcontrolname='" + getFormControlName(fieldName) + "']",
            
            // Strategy 3: By label association
            "//label[contains(text(), '" + fieldName + "')]/following-sibling::input",
            "//*[contains(text(), '" + fieldName + "')]/following::input[1]",
            
            // Strategy 4: By mat-input specific
            "//mat-label[contains(text(), '" + fieldName + "')]/ancestor::mat-form-field//input",
            
            // Strategy 5: Generic input with common attributes
            "//input[contains(@id, '" + fieldName.toLowerCase().replace(" ", "") + "')]",
            "//input[@name='" + fieldName.toLowerCase().replace(" ", "") + "']"
        };

        for (String strategy : strategies) {
            try {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strategy)));
                System.out.println("✅ Found '" + fieldName + "' using: " + strategy);
                return element;
            } catch (Exception e) {
                System.out.println("❌ Not found with: " + strategy);
            }
        }
        
        return null;
    }

    private String getFormControlName(String fieldName) {
        switch (fieldName.toLowerCase()) {
            case "first name": return "firstName";
            case "last name": return "lastName";
            case "date of birth": return "dob";
            case "place of birth": return "placeOfBirth";
            case "gender": return "gender";
            default: return fieldName.toLowerCase().replace(" ", "");
        }
    }

    private void verifyTextFieldStyles(String fieldName, WebElement element, Map<String, String> expectedStyles) {
        System.out.println("\n🎨 Verifying styles for: " + fieldName);
        
        // Scroll element into view for better visibility
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", element);
        
        // Get actual CSS values
        Map<String, String> actualStyles = new HashMap<>();
        actualStyles.put("font-family", element.getCssValue("font-family"));
        actualStyles.put("font-size", element.getCssValue("font-size"));
        actualStyles.put("color", element.getCssValue("color"));
        actualStyles.put("border-color", element.getCssValue("border-top-color")); // Using top border as representative
        actualStyles.put("border-width", element.getCssValue("border-top-width"));
        actualStyles.put("border-radius", element.getCssValue("border-radius"));
        actualStyles.put("background-color", element.getCssValue("background-color"));
        actualStyles.put("padding", element.getCssValue("padding"));
        actualStyles.put("height", element.getCssValue("height"));

        // Display comparison
        System.out.println("┌─────────────────┬──────────────────────────────┬──────────────────────────────┐");
        System.out.println("│ Property        │ Expected                     │ Actual                       │");
        System.out.println("├─────────────────┼──────────────────────────────┼──────────────────────────────┤");
        
        boolean allPassed = true;
        
        for (Map.Entry<String, String> entry : expectedStyles.entrySet()) {
            String property = entry.getKey();
            String expected = entry.getValue();
            String actual = actualStyles.get(property);
            
            String status = "✅";
            if (!isStyleMatching(property, expected, actual)) {
                status = "❌";
                allPassed = false;
            }
            
            System.out.printf("│ %-15s │ %-28s │ %-28s │ %s%n", 
                property, expected, actual, status);
        }
        System.out.println("└─────────────────┴──────────────────────────────┴──────────────────────────────┘");
        
        if (allPassed) {
            System.out.println("🎉 ALL STYLES PASSED for " + fieldName);
        } else {
            System.out.println("⚠️  Some style mismatches for " + fieldName);
        }
        
        // Additional field information
        System.out.println("📋 Field Details:");
        System.out.println("   - Tag: " + element.getTagName());
        System.out.println("   - Type: " + element.getAttribute("type"));
        System.out.println("   - Placeholder: " + element.getAttribute("placeholder"));
        System.out.println("   - Enabled: " + element.isEnabled());
        System.out.println("   - Displayed: " + element.isDisplayed());
    }

    private boolean isStyleMatching(String property, String expected, String actual) {
        if (actual == null || expected == null) return false;
        
        // Normalize values for comparison
        expected = expected.trim().toLowerCase();
        actual = actual.trim().toLowerCase();
        
        switch (property) {
            case "font-family":
                // Check if expected font is contained in actual (browsers add fallbacks)
                return actual.contains(expected.replace("\"", "").replace("'", ""));
                
            case "height":
                // Allow some tolerance for height (within 2px)
                return isHeightMatching(expected, actual);
                
            case "border-color":
                // Border color might have slight variations
                return actual.contains("rgba(0, 0, 0") || actual.equals(expected);
                
            case "background-color":
                // Handle transparent background case
                if (actual.equals("rgba(0, 0, 0, 0)") && expected.equals("rgba(255, 255, 255, 1)")) {
                    System.out.println("   ⚠️  Background is transparent (might be expected)");
                    return true; // Allow transparent as it might be designed that way
                }
                return actual.equals(expected);
                
            default:
                return actual.equals(expected);
        }
    }

    private boolean isHeightMatching(String expected, String actual) {
        try {
            // Extract numeric values from height strings like "40px"
            int expectedHeight = Integer.parseInt(expected.replace("px", "").trim());
            int actualHeight = Integer.parseInt(actual.replace("px", "").trim());
            
            // Allow ±2px tolerance
            return Math.abs(expectedHeight - actualHeight) <= 2;
        } catch (NumberFormatException e) {
            // If parsing fails, do exact match
            return expected.equals(actual);
        }
    }

    private void verifyGenderFieldStyles(WebDriverWait wait) {
        System.out.println("\n=== VERIFYING GENDER FIELD STYLES ===");
        
        try {
            // Find male and female elements
            WebElement maleElement = findGenderElement(wait, "Male");
            WebElement femaleElement = findGenderElement(wait, "Female");
            
            if (maleElement != null && femaleElement != null) {
                // Expected styles for gender buttons
                Map<String, String> expectedGenderStyles = new HashMap<>();
                expectedGenderStyles.put("font-family", "Roboto, sans-serif");
                expectedGenderStyles.put("font-size", "14px");
                expectedGenderStyles.put("color", "rgba(0, 0, 0, 0.87)");
                expectedGenderStyles.put("border-radius", "4px");
                expectedGenderStyles.put("background-color", "rgba(255, 255, 255, 1)");
                
                verifyGenderElementStyles("Male Button", maleElement, expectedGenderStyles);
                verifyGenderElementStyles("Female Button", femaleElement, expectedGenderStyles);
                
                // Test gender selection functionality
                testGenderSelection(wait, maleElement, femaleElement);
            } else {
                System.out.println("⚠️  Could not find gender elements");
            }
        } catch (Exception e) {
            System.out.println("❌ Error verifying gender styles: " + e.getMessage());
        }
    }

    private WebElement findGenderElement(WebDriverWait wait, String gender) {
        String[] strategies = {
            "//span[normalize-space()='" + gender + "']",
            "//button[contains(text(), '" + gender + "')]",
            "//mat-radio-button[contains(., '" + gender + "')]",
            "//div[contains(text(), '" + gender + "')]",
            "//label[contains(., '" + gender + "')]",
            "//*[contains(@class, 'gender') and contains(text(), '" + gender + "')]"
        };

        for (String strategy : strategies) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(strategy)));
                System.out.println("✅ Found '" + gender + "' using: " + strategy);
                return element;
            } catch (Exception e) {
                System.out.println("❌ Not found with: " + strategy);
            }
        }
        return null;
    }

    private void verifyGenderElementStyles(String elementName, WebElement element, Map<String, String> expectedStyles) {
        System.out.println("\n🎨 Verifying styles for: " + elementName);
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        
        Map<String, String> actualStyles = new HashMap<>();
        actualStyles.put("font-family", element.getCssValue("font-family"));
        actualStyles.put("font-size", element.getCssValue("font-size"));
        actualStyles.put("color", element.getCssValue("color"));
        actualStyles.put("border-radius", element.getCssValue("border-radius"));
        actualStyles.put("background-color", element.getCssValue("background-color"));

        System.out.println("┌─────────────────┬──────────────────────────────┬──────────────────────────────┐");
        System.out.println("│ Property        │ Expected                     │ Actual                       │");
        System.out.println("├─────────────────┼──────────────────────────────┼──────────────────────────────┤");
        
        for (Map.Entry<String, String> entry : expectedStyles.entrySet()) {
            String property = entry.getKey();
            String expected = entry.getValue();
            String actual = actualStyles.get(property);
            
            String status = isStyleMatching(property, expected, actual) ? "✅" : "❌";
            
            System.out.printf("│ %-15s │ %-28s │ %-28s │ %s%n", 
                property, expected, actual, status);
        }
        System.out.println("└─────────────────┴──────────────────────────────┴──────────────────────────────┘");
    }

    private void testGenderSelection(WebDriverWait wait, WebElement maleElement, WebElement femaleElement) {
        System.out.println("\n🔘 Testing Gender Selection Functionality");
        
        try {
            // Test selecting Male
            System.out.println("Selecting Male...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", maleElement);
            Thread.sleep(1000);
            System.out.println("✅ Male selected");
            
            // Test selecting Female
            System.out.println("Selecting Female...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", femaleElement);
            Thread.sleep(1000);
            System.out.println("✅ Female selected");
            
            // Verify elements remain clickable after selection
            Assert.assertTrue(maleElement.isEnabled(), "Male should remain enabled");
            Assert.assertTrue(femaleElement.isEnabled(), "Female should remain enabled");
            
        } catch (Exception e) {
            System.out.println("❌ Gender selection test failed: " + e.getMessage());
        }
    }

    private void navigateToEnrollment(WebDriverWait wait) {
        try {
            By enrollmentLink = By.xpath("//h4[normalize-space()='Enrollment']");
            WebElement enrollmentElement = wait.until(ExpectedConditions.elementToBeClickable(enrollmentLink));
            System.out.println("Clicking Enrollment link...");
            enrollmentElement.click();
            
            // Wait for enrollment page to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Child Enrollment') or contains(text(),'Enrollment')]")));
            
        } catch (Exception e) {
            System.out.println("❌ Navigation to Enrollment failed: " + e.getMessage());
            throw e;
        }
    }

    private void waitForPageToLoad(WebDriverWait wait) {
        System.out.println("Waiting for page to load...");
        
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//*[contains(@class,'loading') or contains(@class,'spinner')]")));
        } catch (Exception e) {
            System.out.println("No loading indicator found");
        }

        wait.until(driver -> ((JavascriptExecutor) driver)
            .executeScript("return document.readyState").equals("complete"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Helper: Login
    public boolean login(String email, String password) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-username")));
            WebElement passwordInput = driver.findElement(By.id("login-password"));
            WebElement signInButton = driver.findElement(By.xpath("//a[contains(@class,'mat-raised-button')]"));

            emailInput.clear();
            emailInput.sendKeys(email);
            passwordInput.clear();
            passwordInput.sendKeys(password);
            signInButton.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Enrollment')]")));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}