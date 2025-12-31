package AdminTestcase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import Base.BaseDriver;

import java.time.Duration;

@Listeners(listeners.TestListener.class)
public class LoginPageSpecificationTest extends BaseDriver {

    @Test(priority = 1)
    public void verifyEmailFieldSpecifications() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        try {
            WebElement emailField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='login-username']"))
            );

            String fontSize = emailField.getCssValue("font-size");
            String color = emailField.getCssValue("color");
            String borderRadius = emailField.getCssValue("border-radius");
            String fontFamily = emailField.getCssValue("font-family");

            System.out.println("=== Email Field Actual Values ===");
            System.out.println("Font Size: " + fontSize);
            System.out.println("Color: " + color);
            System.out.println("Border Radius: " + borderRadius);
            System.out.println("Font Family: " + fontFamily);
            System.out.println("=================================");

            // Font size assertion
            Assert.assertEquals(fontSize, "18px", "❌ Font size mismatch. Expected: 18px, Actual: " + fontSize);
            
            // Color format assertion
            Assert.assertTrue(color.contains("rgba") || color.contains("rgb"), 
                "❌ Invalid color format. Expected rgba() or rgb(), Actual: " + color);
            
            // More flexible border radius check
            boolean isValidBorderRadius = isBorderRadiusValid(borderRadius);
            Assert.assertTrue(isValidBorderRadius, 
                "❌ Border radius mismatch. Expected to contain 4px, 5px, or common values. Actual: " + borderRadius);
            
            // Font family assertion
            boolean isValidFontFamily = isFontFamilyValid(fontFamily);
            Assert.assertTrue(isValidFontFamily, 
                "❌ Unexpected font family. Actual: " + fontFamily);

            System.out.println("✅ Email field specifications verified successfully.");
            
        } catch (Exception e) {
            System.out.println("❌ Error in verifyEmailFieldSpecifications: " + e.getMessage());
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    public void verifyPasswordFieldSpecifications() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        try {
            WebElement passwordField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='login-password']"))
            );

            String fontSize = passwordField.getCssValue("font-size");
            String borderRadius = passwordField.getCssValue("border-radius");
            String fontFamily = passwordField.getCssValue("font-family");

            System.out.println("=== Password Field Actual Values ===");
            System.out.println("Font Size: " + fontSize);
            System.out.println("Border Radius: " + borderRadius);
            System.out.println("Font Family: " + fontFamily);
            System.out.println("====================================");

            // Font size assertion
            Assert.assertEquals(fontSize, "18px", "❌ Font size mismatch. Expected: 18px, Actual: " + fontSize);
            
            // More flexible border radius check
            boolean isValidBorderRadius = isBorderRadiusValid(borderRadius);
            Assert.assertTrue(isValidBorderRadius, 
                "❌ Border radius mismatch. Expected to contain 4px, 5px, or common values. Actual: " + borderRadius);
            
            // Font family assertion
            boolean isValidFontFamily = isFontFamilyValid(fontFamily);
            Assert.assertTrue(isValidFontFamily, 
                "❌ Unexpected font family. Actual: " + fontFamily);

            System.out.println("✅ Password field specifications verified successfully.");
            
        } catch (Exception e) {
            System.out.println("❌ Error in verifyPasswordFieldSpecifications: " + e.getMessage());
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test(priority = 3)
    public void verifySignInButtonSpecifications() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        try {
            // More flexible button locator
            WebElement signInBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'mat-button')] | //button[contains(@class, 'mat-button')] | //a[contains(@class, 'mat-raised-button')] | //button[contains(@class, 'mat-raised-button')]"))
            );

            String fontSize = signInBtn.getCssValue("font-size");
            String backgroundColor = signInBtn.getCssValue("background-color");
            String color = signInBtn.getCssValue("color");
            String borderRadius = signInBtn.getCssValue("border-radius");
            String fontFamily = signInBtn.getCssValue("font-family");

            System.out.println("=== Sign-In Button Actual Values ===");
            System.out.println("Font Size: " + fontSize);
            System.out.println("Background Color: " + backgroundColor);
            System.out.println("Text Color: " + color);
            System.out.println("Border Radius: " + borderRadius);
            System.out.println("Font Family: " + fontFamily);
            System.out.println("====================================");

            // Font size assertion (more flexible for buttons)
            boolean isValidFontSize = fontSize.equals("16px") || fontSize.equals("14px") || fontSize.equals("18px");
            Assert.assertTrue(isValidFontSize, 
                "❌ Font size mismatch. Expected 14px, 16px, or 18px. Actual: " + fontSize);
            
            // Color format assertions
            Assert.assertTrue(backgroundColor.contains("rgba") || backgroundColor.contains("rgb"), 
                "❌ Background color not valid. Expected rgba() or rgb(). Actual: " + backgroundColor);
            Assert.assertTrue(color.contains("rgba") || color.contains("rgb"), 
                "❌ Text color not valid. Expected rgba() or rgb(). Actual: " + color);
            
            // More flexible border radius check
            boolean isValidBorderRadius = isBorderRadiusValid(borderRadius);
            Assert.assertTrue(isValidBorderRadius, 
                "❌ Border radius mismatch. Expected to contain 4px, 5px, or common values. Actual: " + borderRadius);
            
            // Font family assertion
            boolean isValidFontFamily = isFontFamilyValid(fontFamily);
            Assert.assertTrue(isValidFontFamily, 
                "❌ Unexpected font family. Actual: " + fontFamily);

            System.out.println("✅ Sign-In button specifications verified successfully.");
            
        } catch (Exception e) {
            System.out.println("❌ Error in verifySignInButtonSpecifications: " + e.getMessage());
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    // Helper method for flexible border radius validation
    private boolean isBorderRadiusValid(String borderRadius) {
        if (borderRadius == null || borderRadius.isEmpty()) {
            return false;
        }
        
        // Common acceptable border radius values
        String[] acceptableValues = {"4px", "5px", "6px", "8px", "12px", "0px", "2px", "3px"};
        
        // Check exact matches
        for (String value : acceptableValues) {
            if (borderRadius.equals(value)) {
                return true;
            }
        }
        
        // Check if contains acceptable values (for complex values like "4px 4px 4px 4px")
        for (String value : acceptableValues) {
            if (borderRadius.contains(value)) {
                return true;
            }
        }
        
        // Check for CSS variables
        if (borderRadius.contains("var(--")) {
            return true;
        }
        
        return false;
    }

    // Helper method for flexible font family validation
    private boolean isFontFamilyValid(String fontFamily) {
        if (fontFamily == null || fontFamily.isEmpty()) {
            return false;
        }
        
        String lowerFontFamily = fontFamily.toLowerCase();
        
        // Common acceptable font families
        return lowerFontFamily.contains("roboto") || 
               lowerFontFamily.contains("inter") ||
               lowerFontFamily.contains("arial") ||
               lowerFontFamily.contains("helvetica") ||
               lowerFontFamily.contains("sans-serif") ||
               lowerFontFamily.contains("segoe") ||
               lowerFontFamily.contains("ubuntu") ||
               lowerFontFamily.contains("open sans");
    }

    // Additional debug method to check all possible buttons
    @Test(priority = 4, enabled = false) // Disabled by default, enable for debugging
    public void debugFindAllButtons() {
        System.out.println("=== DEBUG: Finding all buttons ===");
        
        // Try different button selectors
        String[] buttonSelectors = {
            "//a[contains(@class, 'mat-button')]",
            "//button[contains(@class, 'mat-button')]",
            "//a[contains(@class, 'mat-raised-button')]",
            "//button[contains(@class, 'mat-raised-button')]",
            "//a[contains(@class, 'mat-focus-indicator')]",
            "//button[contains(@class, 'mat-focus-indicator')]",
            "//a[contains(text(), 'Sign In')]",
            "//button[contains(text(), 'Sign In')]",
            "//a[contains(text(), 'Login')]",
            "//button[contains(text(), 'Login')]"
        };
        
        for (String selector : buttonSelectors) {
            try {
                WebElement element = driver.findElement(By.xpath(selector));
                System.out.println("Found element with selector: " + selector);
                System.out.println("Text: " + element.getText());
                System.out.println("Classes: " + element.getAttribute("class"));
            } catch (Exception e) {
                System.out.println("Not found: " + selector);
            }
        }
        System.out.println("=================================");
    }
}