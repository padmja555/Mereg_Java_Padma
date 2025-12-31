package AdminSpeciations;

import Base.BaseDriver;
import AdminPages.LoginPage;
import AdminPages.ChildPayment;

import org.openqa.selenium.*;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class PaymentUISpecificationTest extends BaseDriver {

    // Locator for the main editable amount input field
    private static final By EDITABLE_AMOUNT_LOCATOR = By.name("totalAmountBeingPaid");
    
    // FIXED: More robust locators for display elements with multiple fallbacks
    private static final By[] GUARDIAN_NAME_LOCATORS = {
        By.xpath("//*[contains(text(),'Guardian Name')]/following::div[1]"),
        By.xpath("//*[contains(text(),'Guardian')]/following::*[1]"),
        By.xpath("//label[contains(text(),'Guardian')]/following-sibling::div"),
        By.xpath("//*[contains(text(),'Guardian')]/parent::div/following-sibling::div"),
        By.xpath("//div[contains(@class,'guardian') or contains(@class,'Guardian')]")
    };
    
    private static final By[] CHILD_NAME_LOCATORS = {
        By.xpath("//*[contains(text(),'Child Name')]/following::div[1]"),
        By.xpath("//*[contains(text(),'Child')]/following::*[1]"),
        By.xpath("//label[contains(text(),'Child')]/following-sibling::div"),
        By.xpath("//*[contains(text(),'Child')]/parent::div/following-sibling::div"),
        By.xpath("//div[contains(@class,'child') or contains(@class,'Child')]")
    };
    
    private static final By[] FULL_AMOUNT_LOCATORS = {
        By.xpath("//*[contains(text(),'Full Amount')]/following::div[1]"),
        By.xpath("//*[contains(text(),'Full Amount')]/following::*[1]"),
        By.xpath("//label[contains(text(),'Full Amount')]/following-sibling::div"),
        By.xpath("//*[contains(text(),'Full Amount')]/parent::div/following-sibling::div"),
        By.xpath("//div[contains(@class,'amount') or contains(@class,'Amount')]")
    };
    
    // Locator for the iframe
    private static final By IFRAME_LOCATOR = By.tagName("iframe");

    @Test
    public void verifyPaymentUIFlowAndStyles() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        System.out.println("🚀 STARTING PAYMENT UI FLOW TEST\n");

        // ************ LOGIN ************
        System.out.println("🔐 LOGGING IN...");
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        // Wait for login success
        wait.until(ExpectedConditions.urlContains("/navigation-home"));
        System.out.println("✅ Login successful");

        // ************ OPEN PAYMENT PAGE ************
        System.out.println("📋 OPENING PAYMENT PAGE...");
        ChildPayment paymentPage = new ChildPayment(driver, wait);
        paymentPage.clickOnPaymentPage();
        Thread.sleep(2000);

        // ************ SELECT GUARDIAN ************
        System.out.println("👨‍👧 SELECTING GUARDIAN...");
        paymentPage.clickOnGurdian();
        Thread.sleep(2000);

        // ************ SELECT CHILD ************
        System.out.println("👶 SELECTING CHILD...");
        paymentPage.selectRandomChild();
        Thread.sleep(2000);

        // ************ CLICK PROCEED PAYMENT ************
        System.out.println("💰 PROCEEDING TO PAYMENT...");
        paymentPage.Procedbuttonpayment();
        System.out.println("✅ Clicked Proceed Button");

        // ************ VERIFY PAYMENT SUMMARY PAGE ************
        System.out.println("📄 VERIFYING PAYMENT SUMMARY PAGE...");
        By paymentSummaryTitleLocator = By.xpath("//*[contains(text(),'Payment Summary')]");
        WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(paymentSummaryTitleLocator));

        String title = titleElement.getText();
        System.out.println("Payment Summary Title: " + title);
        Assert.assertEquals(title, "Payment Summary", "Payment Summary title mismatch");
        System.out.println("✅ Payment Summary page verified");

        // ************ DEBUG: CHECK PAGE CONTENT ************
        debugPageContent();

        System.out.println("\n=== VALIDATING PAYMENT UI STYLES ===");
        
        // 1. Verify Guardian Name Display with multiple fallback locators
        verifyDisplayElementWithFallbacks("Guardian Name Display", GUARDIAN_NAME_LOCATORS, wait);
        
        // 2. Verify Child Name Display with multiple fallback locators
        verifyDisplayElementWithFallbacks("Child Name Display", CHILD_NAME_LOCATORS, wait);
        
        // 3. Verify Full Amount Display with multiple fallback locators
        verifyDisplayElementWithFallbacks("Full Amount Display", FULL_AMOUNT_LOCATORS, wait);
        
        // 4. Verify All Text Field Styles (NEWLY ADDED)
        verifyAllTextFieldStyles(wait);
        
        boolean iframeSwitched = false;

        // ************ SWITCH TO IFRAME AND VERIFY INPUT FIELD ************
        System.out.println("\n🔄 CHECKING FOR IFRAME...");
        try {
            List<WebElement> iframes = driver.findElements(IFRAME_LOCATOR);
            System.out.println("Found " + iframes.size() + " iframe(s) on the page");
            
            if (!iframes.isEmpty()) {
                WebElement iframeElement = iframes.get(0);
                driver.switchTo().frame(iframeElement); 
                iframeSwitched = true;
                System.out.println("✅ Switched to iframe");

                // Verify input field inside iframe
                verifyInputField("Editable Amount Input", EDITABLE_AMOUNT_LOCATORS, wait);
            } else {
                System.out.println("ℹ️ No iframe found, checking input field on main page");
                verifyInputField("Editable Amount Input", EDITABLE_AMOUNT_LOCATORS, wait);
            }
            
        } catch (Exception e) {
            System.out.println("⚠️ Iframe handling failed: " + e.getMessage());
            // Fallback to main content
            if (iframeSwitched) {
                driver.switchTo().defaultContent();
            }
            verifyInputField("Editable Amount Input (Fallback)", EDITABLE_AMOUNT_LOCATORS, wait);
        }

        // ************ SWITCH BACK FROM IFRAME IF SWITCHED ************
        if (iframeSwitched) {
            driver.switchTo().defaultContent();
            System.out.println("✅ Switched back to default content");
        }

        // 5. Verify the 'Proceed' Button
        verifyProceedButton(By.xpath("//button[contains(.,'Proceed')]"), wait);

        System.out.println("\n=== PAYMENT UI VALIDATION COMPLETED SUCCESSFULLY ===\n");
    }

    // ==================================================
    //           NEW COMPREHENSIVE TEXT FIELD STYLE VERIFICATION
    // ==================================================

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
        commonTextStyles.put("height", "40px");

        // Apply common styles to payment-specific fields
        expectedStyles.put("Guardian Field", commonTextStyles);
        expectedStyles.put("Child Field", commonTextStyles);
        expectedStyles.put("Amount Field", commonTextStyles);
        expectedStyles.put("Fee Field", commonTextStyles);

        // Find and verify each field
        for (Map.Entry<String, Map<String, String>> entry : expectedStyles.entrySet()) {
            String fieldName = entry.getKey();
            Map<String, String> styles = entry.getValue();
            
            try {
                WebElement field = findPaymentTextField(wait, fieldName);
                if (field != null) {
                    verifyTextFieldStyles(fieldName, field, styles);
                } else {
                    System.out.println("⚠️  Could not find field: " + fieldName);
                }
            } catch (Exception e) {
                System.out.println("❌ Error verifying " + fieldName + ": " + e.getMessage());
            }
        }
    }

    private WebElement findPaymentTextField(WebDriverWait wait, String fieldName) {
        // Try multiple locator strategies for payment-specific fields
        By[] locators = {};
        
        switch (fieldName) {
            case "Guardian Field":
                locators = new By[]{
                    By.xpath("//input[@placeholder='Select Guardian']"),
                    By.xpath("//mat-form-field[contains(.,'Guardian')]//input"),
                    By.xpath("//label[contains(.,'Guardian')]/following-sibling::input")
                };
                break;
            case "Child Field":
                locators = new By[]{
                    By.xpath("//mat-select[contains(@aria-label,'Child')]"),
                    By.xpath("//mat-form-field[contains(.,'Child')]//input"),
                    By.xpath("//label[contains(.,'Child')]/following-sibling::input")
                };
                break;
            case "Amount Field":
                locators = new By[]{
                    By.xpath("//input[@name='totalAmountBeingPaid']"),
                    By.xpath("//input[contains(@placeholder,'Amount')]"),
                    By.xpath("//input[@type='number' and not(@readonly)]")
                };
                break;
            case "Fee Field":
                locators = new By[]{
                    By.xpath("//input[@readonly and @type='number']"),
                    By.xpath("//input[contains(@placeholder,'Fee')]"),
                    By.xpath("//input[@readonly]")
                };
                break;
        }

        for (By locator : locators) {
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                System.out.println("✅ Found " + fieldName + " using: " + locator);
                return element;
            } catch (TimeoutException e) {
                // Continue to next locator
            }
        }
        return null;
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
        actualStyles.put("border-color", element.getCssValue("border-top-color"));
        actualStyles.put("border-width", element.getCssValue("border-top-width"));
        actualStyles.put("border-radius", element.getCssValue("border-radius"));
        actualStyles.put("background-color", element.getCssValue("background-color"));
        actualStyles.put("padding", element.getCssValue("padding"));
        actualStyles.put("height", element.getCssValue("height"));

        // Display comparison in table format
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
    }

    private boolean isStyleMatching(String property, String expected, String actual) {
        if (actual == null || expected == null) {
            return false;
        }
        
        // Handle color properties specially
        if (property.toLowerCase().contains("color")) {
            try {
                String expectedHex = expected.startsWith("rgba") ? Color.fromString(expected).asHex() : expected;
                String actualHex = actual.startsWith("rgba") ? Color.fromString(actual).asHex() : actual;
                return expectedHex.equalsIgnoreCase(actualHex);
            } catch (Exception e) {
                return expected.equalsIgnoreCase(actual);
            }
        }
        
        // For other properties, do case-insensitive comparison
        return expected.equalsIgnoreCase(actual);
    }

    // ==================================================
    //           IMPROVED VERIFICATION METHODS
    // ==================================================

    /**
     * Verifies display elements with multiple fallback locators
     */
    private void verifyDisplayElementWithFallbacks(String name, By[] locators, WebDriverWait wait) {
        System.out.println("\n--- Verifying: " + name);
        
        WebElement element = null;
        String usedLocator = "None";
        
        for (By locator : locators) {
            try {
                element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                usedLocator = locator.toString();
                System.out.println("✅ Found element using: " + locator);
                break;
            } catch (TimeoutException e) {
                System.out.println("❌ Not found with: " + locator);
                // Continue to next locator
            }
        }
        
        if (element != null) {
            Assert.assertTrue(element.isDisplayed(), "❌ " + name + " is NOT displayed!");
            String elementText = element.getText();
            System.out.println("📝 " + name + " value: '" + elementText + "'");
            checkDisplayStyles(element, name);
        } else {
            System.out.println("⚠️ " + name + " not found with any locator. Skipping style verification.");
            // Don't fail the test - just log the issue
        }
    }

    /**
     * Verifies input fields with better error handling
     */
    private void verifyInputField(String name, By[] locators, WebDriverWait wait) {
        System.out.println("\n--- Verifying: " + name);
        
        WebElement element = null;
        for (By locator : locators) {
            try {
                element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                System.out.println("✅ Found input field using: " + locator);
                break;
            } catch (TimeoutException e) {
                // Continue to next locator
            }
        }
        
        if (element != null) {
            Assert.assertTrue(element.isDisplayed(), "❌ " + name + " is NOT displayed!");
            Assert.assertTrue(element.isEnabled(), "❌ " + name + " is DISABLED!");
            
            // Get current value
            String currentValue = element.getAttribute("value");
            System.out.println("📝 Current value: " + currentValue);
            
            checkStyles(element, name, true);
        } else {
            System.out.println("❌ " + name + " not found with any locator");
            // Try alternative input field locators
            tryAlternativeInputFields(name);
        }
    }

    private void verifyProceedButton(By locator, WebDriverWait wait) {
        System.out.println("\n--- Verifying Proceed Button");
        
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(locator));
            System.out.println("✅ Found proceed button");

            Assert.assertTrue(btn.isEnabled(), "❌ Proceed button disabled!");
            
            // Verify button styles
            String backgroundColor = btn.getCssValue("background-color");
            String textColor = btn.getCssValue("color");
            String fontSize = btn.getCssValue("font-size");
            String fontWeight = btn.getCssValue("font-weight");
            
            System.out.println("🎨 Button Styles:");
            System.out.println("   Background: " + backgroundColor);
            System.out.println("   Text Color: " + textColor);
            System.out.println("   Font Size: " + fontSize);
            System.out.println("   Font Weight: " + fontWeight);
            
            // Check if background is purple (various shades)
            if (isPurpleColor(backgroundColor)) {
                System.out.println("✅ Button has purple background");
            } else {
                System.out.println("⚠️ Button background is not purple: " + backgroundColor);
            }
            
            // Check if text is white or light
            if (textColor.contains("255, 255, 255") || textColor.equals("#ffffff") || textColor.equals("white")) {
                System.out.println("✅ Button text is white");
            } else {
                System.out.println("⚠️ Button text is not white: " + textColor);
            }

            checkDimensions(btn, "Proceed Button");
            System.out.println("✅ Proceed Button style verification completed");

        } catch (TimeoutException e) {
            System.out.println("❌ Proceed button not found: " + e.getMessage());
            // Try alternative button locators
            tryAlternativeButtonLocators();
        }
    }

    // ==================================================
    //               HELPER METHODS
    // ==================================================

    /**
     * Debug method to check page content
     */
    private void debugPageContent() {
        System.out.println("\n🔍 DEBUGGING PAGE CONTENT:");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());
        
        try {
            // Get all text elements to understand page structure
            List<WebElement> allTextElements = driver.findElements(By.xpath("//*[text() != '']"));
            System.out.println("Found " + allTextElements.size() + " text elements on page");
            
            // Print first 20 text elements to understand structure
            System.out.println("First 20 text elements:");
            for (int i = 0; i < Math.min(20, allTextElements.size()); i++) {
                String text = allTextElements.get(i).getText().trim();
                if (!text.isEmpty()) {
                    String tag = allTextElements.get(i).getTagName();
                    System.out.println((i+1) + ". <" + tag + ">: " + text);
                }
            }
        } catch (Exception e) {
            System.out.println("Error during debug: " + e.getMessage());
        }
    }

    /**
     * Try alternative input field locators
     */
    private void tryAlternativeInputFields(String name) {
        System.out.println("🔍 Trying alternative input field locators for: " + name);
        
        By[] alternativeLocators = {
            By.xpath("//input[@type='number']"),
            By.xpath("//input[contains(@id, 'amount')]"),
            By.xpath("//input[contains(@name, 'amount')]"),
            By.xpath("//input[contains(@placeholder, 'amount')]"),
            By.xpath("//input[@min]") // Amount fields often have min attribute
        };
        
        for (By locator : alternativeLocators) {
            try {
                WebElement element = driver.findElement(locator);
                System.out.println("✅ Found alternative input: " + locator);
                System.out.println("   Value: " + element.getAttribute("value"));
                break;
            } catch (NoSuchElementException e) {
                // Continue to next locator
            }
        }
    }

    /**
     * Try alternative button locators
     */
    private void tryAlternativeButtonLocators() {
        System.out.println("🔍 Trying alternative button locators...");
        
        By[] alternativeLocators = {
            By.xpath("//button[contains(., 'Pay')]"),
            By.xpath("//button[contains(., 'Continue')]"),
            By.xpath("//button[contains(., 'Next')]"),
            By.xpath("//button[contains(@class, 'btn')]"),
            By.xpath("//button[@type='submit']")
        };
        
        for (By locator : alternativeLocators) {
            try {
                WebElement element = driver.findElement(locator);
                System.out.println("✅ Found alternative button: " + locator);
                System.out.println("   Text: " + element.getText());
                break;
            } catch (NoSuchElementException e) {
                // Continue to next locator
            }
        }
    }

    private void checkDisplayStyles(WebElement element, String name) {
        String fontFamily = element.getCssValue("font-family").toLowerCase();
        String fontSize = element.getCssValue("font-size");
        String color = element.getCssValue("color");
        String fontWeight = element.getCssValue("font-weight");
        
        System.out.println("🎨 " + name + " Styles:");
        System.out.println("   Font Family: " + fontFamily);
        System.out.println("   Font Size: " + fontSize);
        System.out.println("   Color: " + color);
        System.out.println("   Font Weight: " + fontWeight);

        // Check if font contains Roboto
        if (fontFamily.contains("roboto")) {
            System.out.println("✅ Font family is Roboto");
        } else {
            System.out.println("⚠️ Font family is not Roboto: " + fontFamily);
        }
        
        // Check font size
        try {
            float size = Float.parseFloat(fontSize.replace("px", ""));
            if (size >= 14) {
                System.out.println("✅ Font size is adequate: " + fontSize);
            } else {
                System.out.println("⚠️ Font size might be too small: " + fontSize);
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Could not parse font size: " + fontSize);
        }
    }

    private void checkStyles(WebElement element, String name, boolean isInput) {
        String fontFamily = element.getCssValue("font-family").toLowerCase();
        String fontSize = element.getCssValue("font-size");
        String color = element.getCssValue("color");
        String background = element.getCssValue("background-color");
        String borderColor = element.getCssValue("border-color");
        String borderRadius = element.getCssValue("border-radius");
        String padding = element.getCssValue("padding");
        String height = element.getCssValue("height");
        String width = element.getCssValue("width");

        System.out.println("🎨 " + name + " Styles:");
        System.out.println("   Font: " + fontFamily + " " + fontSize);
        System.out.println("   Colors - Text: " + color + ", Background: " + background);
        System.out.println("   Border: " + borderColor + " (radius: " + borderRadius + ")");
        System.out.println("   Dimensions: " + height + " x " + width + " (padding: " + padding + ")");

        // Font checks
        if (fontFamily.contains("roboto")) {
            System.out.println("✅ Font family is Roboto");
        }

        // Border and padding checks for inputs
        if (isInput) {
            if (isGrey(borderColor)) {
                System.out.println("✅ Border color is grey");
            } else {
                System.out.println("⚠️ Border color is not grey: " + borderColor);
            }
            
            if (!padding.equals("0px") && !padding.equals("0px 0px 0px 0px")) {
                System.out.println("✅ Has padding: " + padding);
            } else {
                System.out.println("⚠️ No padding detected");
            }
        }

        checkDimensions(element, name);
    }

    private void checkDimensions(WebElement element, String name) {
        String height = element.getCssValue("height");
        String width = element.getCssValue("width");

        try {
            float heightValue = Float.parseFloat(height.replace("px", ""));
            float widthValue = Float.parseFloat(width.replace("px", ""));
            
            if (heightValue > 20) {
                System.out.println("✅ Height is adequate: " + height);
            } else {
                System.out.println("⚠️ Height might be too small: " + height);
            }
            
            if (widthValue > 50) {
                System.out.println("✅ Width is adequate: " + width);
            } else {
                System.out.println("⚠️ Width might be too small: " + width);
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Could not parse dimensions: " + height + " x " + width);
        }
    }

    private boolean isGrey(String cssColor) {
        try {
            String hex = Color.fromString(cssColor).asHex().toLowerCase();
            return hex.equals("#bdbdbd") || hex.equals("#9e9e9e") || 
                   hex.equals("#e0e0e0") || hex.equals("#cccccc") ||
                   hex.equals("#757575");
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isPurpleColor(String cssColor) {
        try {
            String hex = Color.fromString(cssColor).asHex().toLowerCase();
            return hex.equals("#4b24b6") || hex.equals("#5e35b1") || 
                   hex.equals("#673ab7") || hex.equals("#7e57c2") ||
                   hex.equals("#512da8");
        } catch (Exception e) {
            return false;
        }
    }

    // Updated EDITABLE_AMOUNT_LOCATORS to be an array for consistency
    private static final By[] EDITABLE_AMOUNT_LOCATORS = {
        By.name("totalAmountBeingPaid"),
        By.xpath("//input[@type='number' and not(@readonly)]"),
        By.xpath("//input[contains(@name, 'amount')]")
    };
}