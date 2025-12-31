package AdminSpeciations;

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
public class LoginSpecificationTest extends BaseDriver {

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

            Assert.assertEquals(fontSize, "18px", "❌ Font size mismatch. Expected: 18px, Actual: " + fontSize);
            Assert.assertTrue(color.contains("rgba") || color.contains("rgb"),
                    "❌ Invalid color format. Expected rgba() or rgb(), Actual: " + color);
            Assert.assertTrue(isInputFieldBorderRadiusValid(borderRadius),
                    "❌ Border radius mismatch. Expected 4px, 5px, or similar. Actual: " + borderRadius);
            Assert.assertTrue(isFontFamilyValid(fontFamily),
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

            Assert.assertEquals(fontSize, "18px", "❌ Font size mismatch. Expected: 18px, Actual: " + fontSize);
            Assert.assertTrue(isInputFieldBorderRadiusValid(borderRadius),
                    "❌ Border radius mismatch. Expected 4px, 5px, or similar. Actual: " + borderRadius);
            Assert.assertTrue(isFontFamilyValid(fontFamily),
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
            WebElement signInBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//a[contains(@class, 'mat-button')] | " +
                                     "//button[contains(@class, 'mat-button')] | " +
                                     "//a[contains(@class, 'mat-raised-button')] | " +
                                     "//button[contains(@class, 'mat-raised-button')]"))
            );

            // Get CSS properties
            String btnHeight       = signInBtn.getCssValue("height");
            String btnWidth        = signInBtn.getCssValue("width");
            String btnColor        = signInBtn.getCssValue("color");
            String btnFontSize     = signInBtn.getCssValue("font-size");
            String btnBgColor      = signInBtn.getCssValue("background-color");
            String btnPadding      = signInBtn.getCssValue("padding");
            String btnBorderRadius = signInBtn.getCssValue("border-radius");
            String btnFontFamily   = signInBtn.getCssValue("font-family");

            // Log CSS values
            System.out.println("=== Sign-In Button CSS Properties ===");
            System.out.println("Height: " + btnHeight);
            System.out.println("Width: " + btnWidth);
            System.out.println("Text Color: " + btnColor);
            System.out.println("Font Size: " + btnFontSize);
            System.out.println("Background Color: " + btnBgColor);
            System.out.println("Padding: " + btnPadding);
            System.out.println("Border Radius: " + btnBorderRadius);
            System.out.println("Font Family: " + btnFontFamily);
            System.out.println("====================================");

            // Validate CSS properties
            Assert.assertTrue(btnHeight.matches("\\d+px"), "❌ Invalid height: " + btnHeight);
            Assert.assertTrue(btnWidth.matches("\\d+px"), "❌ Invalid width: " + btnWidth);
            Assert.assertTrue(btnColor.contains("rgba") || btnColor.contains("rgb"), "❌ Invalid text color: " + btnColor);
            Assert.assertTrue(btnFontSize.matches("\\d+px"), "❌ Invalid font size: " + btnFontSize);
            Assert.assertTrue(btnBgColor.contains("rgba") || btnBgColor.contains("rgb"), "❌ Invalid background color: " + btnBgColor);
            Assert.assertTrue(btnPadding.matches(".*\\d+px.*"), "❌ Invalid padding: " + btnPadding);
            Assert.assertTrue(isButtonBorderRadiusValid(btnBorderRadius), "❌ Invalid border radius: " + btnBorderRadius);
            Assert.assertTrue(isFontFamilyValid(btnFontFamily), "❌ Unexpected font family: " + btnFontFamily);

            System.out.println("✅ Sign-In button specifications verified successfully.");

        } catch (Exception e) {
            System.out.println("❌ Error in verifySignInButtonSpecifications: " + e.getMessage());
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    // Helper method for input field border radius validation
    private boolean isInputFieldBorderRadiusValid(String borderRadius) {
        if (borderRadius == null || borderRadius.isEmpty()) return false;
        String[] acceptableValues = {"0px","2px","3px","4px","5px","6px","8px","12px"};
        for (String value : acceptableValues) if (borderRadius.contains(value)) return true;
        return borderRadius.contains("var(--");
    }

    // Helper method for button border radius validation (includes percentage values)
    private boolean isButtonBorderRadiusValid(String borderRadius) {
        if (borderRadius == null || borderRadius.isEmpty()) return false;
        String[] acceptablePixelValues = {"0px","2px","3px","4px","5px","6px","8px","12px","16px","20px","24px"};
        String[] acceptablePercentageValues = {"25%","30%","40%","50%","60%"};
        for (String value : acceptablePixelValues) if (borderRadius.contains(value)) return true;
        for (String value : acceptablePercentageValues) if (borderRadius.contains(value)) return true;
        return borderRadius.contains("var(--");
    }

    // Flexible font family validation
    private boolean isFontFamilyValid(String fontFamily) {
        if (fontFamily == null || fontFamily.isEmpty()) return false;
        String lowerFontFamily = fontFamily.toLowerCase();
        return lowerFontFamily.contains("roboto") || lowerFontFamily.contains("inter") ||
               lowerFontFamily.contains("arial") || lowerFontFamily.contains("helvetica") ||
               lowerFontFamily.contains("sans-serif") || lowerFontFamily.contains("segoe") ||
               lowerFontFamily.contains("ubuntu") || lowerFontFamily.contains("open sans");
    }
}
