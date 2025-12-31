package AdminTestcase;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import AdminPages.Childinfo;
import AdminPages.Parentpage;
import Base.BaseDriver;
import AdminPages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

@Listeners(listeners.TestListener.class)
public class ParentNegativeTest extends BaseDriver {

    // Test for empty first name
    @Test(priority = 1)
    public void testEmptyFirstName() {
        try {
            loginAndNavigateToParentPage();
            Parentpage parentPage = new Parentpage(driver);
            
            // Clear first name field
            clearField(parentPage.firstname);
            
            // Fill other required fields
            fillOtherRequiredFields(parentPage);
            
            // Try to proceed
            parentPage.proceedToMedicalInfoButton();
            
            // Verify error message
            String errorMessage = getFieldErrorMessage("firstName");
            Assert.assertTrue(errorMessage.contains("required") || errorMessage.contains("First name") || errorMessage.contains("empty"),
                            "Expected error message for empty first name. Actual: " + errorMessage);
            System.out.println("✓ Empty first name test passed: " + errorMessage);
            
        } catch (Exception e) {
            captureFailure("Empty First Name Test");
            Assert.fail("Empty first name test failed: " + e.getMessage());
        }
    }

    // Test for empty last name
    @Test(priority = 2)
    public void testEmptyLastName() {
        try {
            loginAndNavigateToParentPage();
            Parentpage parentPage = new Parentpage(driver);
            
            // Clear last name field
            clearField(parentPage.lastname);
            
            // Fill other required fields
            fillRequiredFieldsWithoutLastName(parentPage);
            
            // Try to proceed
            parentPage.proceedToMedicalInfoButton();
            
            // Verify error message
            String errorMessage = getFieldErrorMessage("lastName");
            Assert.assertTrue(errorMessage.contains("required") || errorMessage.contains("Last name") || errorMessage.contains("empty"),
                            "Expected error message for empty last name. Actual: " + errorMessage);
            System.out.println("✓ Empty last name test passed: " + errorMessage);
            
        } catch (Exception e) {
            captureFailure("Empty Last Name Test");
            Assert.fail("Empty last name test failed: " + e.getMessage());
        }
    }

    // Test for invalid email format
    @Test(priority = 3)
    public void testInvalidEmailFormat() {
        try {
            loginAndNavigateToParentPage();
            Parentpage parentPage = new Parentpage(driver);
            
            // Fill required fields
            fillRequiredFields(parentPage);
            
            // Enter invalid email
            enterInvalidEmail("invalid-email");
            
            // Try to proceed
            parentPage.proceedToMedicalInfoButton();
            
            // Verify error message
            String errorMessage = getFieldErrorMessage("email");
            Assert.assertTrue(errorMessage.contains("invalid") || errorMessage.contains("valid") || errorMessage.contains("format"),
                            "Expected error message for invalid email. Actual: " + errorMessage);
            System.out.println("✓ Invalid email test passed: " + errorMessage);
            
        } catch (Exception e) {
            captureFailure("Invalid Email Test");
            Assert.fail("Invalid email test failed: " + e.getMessage());
        }
    }

    // Test for empty phone number
    @Test(priority = 4)
    public void testEmptyPhoneNumber() {
        try {
            loginAndNavigateToParentPage();
            Parentpage parentPage = new Parentpage(driver);
            
            // Clear phone number field
            clearField(parentPage.phonenumber);
            
            // Fill other required fields
            fillRequiredFieldsWithoutPhone(parentPage);
            
            // Try to proceed
            parentPage.proceedToMedicalInfoButton();
            
            // Verify error message
            String errorMessage = getFieldErrorMessage("phoneNumber");
            Assert.assertTrue(errorMessage.contains("required") || errorMessage.contains("Phone") || errorMessage.contains("empty"),
                            "Expected error message for empty phone number. Actual: " + errorMessage);
            System.out.println("✓ Empty phone number test passed: " + errorMessage);
            
        } catch (Exception e) {
            captureFailure("Empty Phone Number Test");
            Assert.fail("Empty phone number test failed: " + e.getMessage());
        }
    }

    // Test for empty address
    @Test(priority = 5)
    public void testEmptyAddress() {
        try {
            loginAndNavigateToParentPage();
            Parentpage parentPage = new Parentpage(driver);
            
            // Clear address field
            clearField(parentPage.address1);
            
            // Fill other required fields
            fillRequiredFieldsWithoutAddress(parentPage);
            
            // Try to proceed
            parentPage.proceedToMedicalInfoButton();
            
            // Verify error message
            String errorMessage = getFieldErrorMessage("addressLine1");
            Assert.assertTrue(errorMessage.contains("required") || errorMessage.contains("Address") || errorMessage.contains("empty"),
                            "Expected error message for empty address. Actual: " + errorMessage);
            System.out.println("✓ Empty address test passed: " + errorMessage);
            
        } catch (Exception e) {
            captureFailure("Empty Address Test");
            Assert.fail("Empty address test failed: " + e.getMessage());
        }
    }

    // Test for empty country selection
    @Test(priority = 6)
    public void testEmptyCountrySelection() {
        try {
            loginAndNavigateToParentPage();
            Parentpage parentPage = new Parentpage(driver);
            
            // Clear country selection
            clearCountrySelection();
            
            // Fill other required fields
            fillRequiredFieldsWithoutCountry(parentPage);
            
            // Try to proceed
            parentPage.proceedToMedicalInfoButton();
            
            // Verify error message
            String errorMessage = getFieldErrorMessage("country");
            Assert.assertTrue(errorMessage.contains("required") || errorMessage.contains("Country") || errorMessage.contains("select"),
                            "Expected error message for empty country. Actual: " + errorMessage);
            System.out.println("✓ Empty country test passed: " + errorMessage);
            
        } catch (Exception e) {
            captureFailure("Empty Country Test");
            Assert.fail("Empty country test failed: " + e.getMessage());
        }
    }

    // Test for invalid phone number format
    @Test(priority = 7)
    public void testInvalidPhoneNumberFormat() {
        try {
            loginAndNavigateToParentPage();
            Parentpage parentPage = new Parentpage(driver);
            
            // Fill required fields
            fillRequiredFields(parentPage);
            
            // Enter invalid phone number
            enterInvalidPhoneNumber("abc123");
            
            // Try to proceed
            parentPage.proceedToMedicalInfoButton();
            
            // Verify error message
            String errorMessage = getFieldErrorMessage("phoneNumber");
            Assert.assertTrue(errorMessage.contains("invalid") || errorMessage.contains("valid") || errorMessage.contains("number"),
                            "Expected error message for invalid phone number. Actual: " + errorMessage);
            System.out.println("✓ Invalid phone number test passed: " + errorMessage);
            
        } catch (Exception e) {
            captureFailure("Invalid Phone Number Test");
            Assert.fail("Invalid phone number test failed: " + e.getMessage());
        }
    }

    // Test for multiple empty fields
    @Test(priority = 8)
    public void testMultipleEmptyFields() {
        try {
            loginAndNavigateToParentPage();
            Parentpage parentPage = new Parentpage(driver);
            
            // Clear multiple required fields
            clearField(parentPage.firstname);
            clearField(parentPage.lastname);
            clearField(parentPage.address1);
            
            // Fill only minimal fields
            parentPage.selectRelationship();
            parentPage.selectRandomCountry();
            parentPage.selectState();
            parentPage.enterRandomCity();
            parentPage.enterRandomZipCode();
            parentPage.selectRandomPhoneType();
            parentPage.enterRandomCountryCode();
            parentPage.enterRandomPhoneNumber();
            
            // Try to proceed
            parentPage.proceedToMedicalInfoButton();
            
            // Verify multiple error messages
            List<String> errorMessages = getAllErrorMessages();
            Assert.assertTrue(errorMessages.size() >= 2, 
                            "Expected multiple error messages. Found: " + errorMessages.size());
            
            boolean hasNameError = errorMessages.stream()
                .anyMatch(msg -> msg.toLowerCase().contains("first name") || msg.toLowerCase().contains("last name"));
            boolean hasAddressError = errorMessages.stream()
                .anyMatch(msg -> msg.toLowerCase().contains("address"));
                
            Assert.assertTrue(hasNameError || hasAddressError, 
                            "Expected error messages for name or address fields");
            System.out.println("✓ Multiple empty fields test passed. Errors: " + errorMessages);
            
        } catch (Exception e) {
            captureFailure("Multiple Empty Fields Test");
            Assert.fail("Multiple empty fields test failed: " + e.getMessage());
        }
    }

    // ========== HELPER METHODS ==========

    private void loginAndNavigateToParentPage() {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("dashboard"));
        
        Childinfo childinfo = new Childinfo(driver);
        childinfo.clickOnenrollment();
        childinfo.enterRandomFirstAndLastName();
        childinfo.selectGender();
        childinfo.enterrandomDOB();
        childinfo.ClickonProceedtoGuardian();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h2[text()='Parent/Guardian info']")));
    }

    private void clearField(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        field.clear();
        
        // Trigger blur event to activate validation
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
            "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));", field);
    }

    private void enterInvalidEmail(String invalidEmail) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[@type='email']")));
        emailField.clear();
        emailField.sendKeys(invalidEmail);
    }

    private void enterInvalidPhoneNumber(String invalidPhone) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement phoneField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[@name='phoneNumber']")));
        phoneField.clear();
        phoneField.sendKeys(invalidPhone);
    }

    private void clearCountrySelection() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement countryDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-select[@name='country']")));
            
            // Try to clear selection by clicking and selecting empty option if available
            countryDropdown.click();
            
            List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//mat-option")));
            
            for (WebElement option : options) {
                if (option.getText().trim().isEmpty() || option.getText().equals("Select")) {
                    option.click();
                    return;
                }
            }
            
            // If no empty option, click outside to cancel
            countryDropdown.click();
            
        } catch (Exception e) {
            System.out.println("Could not clear country selection: " + e.getMessage());
        }
    }

    private String getFieldErrorMessage(String fieldName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        
        try {
            // Try different error message locations
            String[] xpathPatterns = {
                "//input[@name='" + fieldName + "']/following-sibling::mat-error",
                "//mat-form-field[.//input[@name='" + fieldName + "']]//mat-error",
                "//*[contains(text(),'" + fieldName + "') and contains(@class,'error')]",
                "//*[contains(text(),'" + fieldName + "') and contains(@class,'invalid')]"
            };
            
            for (String xpath : xpathPatterns) {
                try {
                    WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
                    if (errorElement.isDisplayed()) {
                        return errorElement.getText().trim();
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            return "No error message found";
            
        } catch (Exception e) {
            return "Error retrieving message: " + e.getMessage();
        }
    }

    private List<String> getAllErrorMessages() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        
        try {
            List<WebElement> errorElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//mat-error, //div[contains(@class,'error')], //span[contains(@class,'error')]")));
            
            return errorElements.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(text -> !text.isEmpty())
                .toList();
            
        } catch (Exception e) {
            return List.of("No error messages found");
        }
    }

    private void captureFailure(String testName) {
        try {
            // Take screenshot on failure
            takeScreenshot(testName + "_FAILED");
            System.out.println("Screenshot captured for failed test: " + testName);
        } catch (Exception e) {
            System.out.println("Could not capture screenshot: " + e.getMessage());
        }
    }

    private void takeScreenshot(String screenshotName) {
        try {
            // Your screenshot implementation here
            System.out.println("Taking screenshot: " + screenshotName);
        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }
    }

    // Helper methods for filling partial forms
    private void fillRequiredFields(Parentpage parentPage) {
        parentPage.enterRandomFirstName();
        parentPage.enterRandomLastName();
        parentPage.selectRelationship();
        parentPage.enterRandomAddress1();
        parentPage.selectRandomCountry();
        parentPage.selectState();
        parentPage.enterRandomCity();
        parentPage.enterRandomZipCode();
        parentPage.selectRandomPhoneType();
        parentPage.enterRandomCountryCode();
        parentPage.enterRandomPhoneNumber();
    }

    private void fillOtherRequiredFields(Parentpage parentPage) {
        parentPage.enterRandomLastName();
        parentPage.selectRelationship();
        parentPage.enterRandomAddress1();
        parentPage.selectRandomCountry();
        parentPage.selectState();
        parentPage.enterRandomCity();
        parentPage.enterRandomZipCode();
        parentPage.selectRandomPhoneType();
        parentPage.enterRandomCountryCode();
        parentPage.enterRandomPhoneNumber();
    }

    private void fillRequiredFieldsWithoutLastName(Parentpage parentPage) {
        parentPage.enterRandomFirstName();
        parentPage.selectRelationship();
        parentPage.enterRandomAddress1();
        parentPage.selectRandomCountry();
        parentPage.selectState();
        parentPage.enterRandomCity();
        parentPage.enterRandomZipCode();
        parentPage.selectRandomPhoneType();
        parentPage.enterRandomCountryCode();
        parentPage.enterRandomPhoneNumber();
    }

    private void fillRequiredFieldsWithoutPhone(Parentpage parentPage) {
        parentPage.enterRandomFirstName();
        parentPage.enterRandomLastName();
        parentPage.selectRelationship();
        parentPage.enterRandomAddress1();
        parentPage.selectRandomCountry();
        parentPage.selectState();
        parentPage.enterRandomCity();
        parentPage.enterRandomZipCode();
    }

    private void fillRequiredFieldsWithoutAddress(Parentpage parentPage) {
        parentPage.enterRandomFirstName();
        parentPage.enterRandomLastName();
        parentPage.selectRelationship();
        parentPage.selectRandomCountry();
        parentPage.selectState();
        parentPage.enterRandomCity();
        parentPage.enterRandomZipCode();
        parentPage.selectRandomPhoneType();
        parentPage.enterRandomCountryCode();
        parentPage.enterRandomPhoneNumber();
    }

    private void fillRequiredFieldsWithoutCountry(Parentpage parentPage) {
        parentPage.enterRandomFirstName();
        parentPage.enterRandomLastName();
        parentPage.selectRelationship();
        parentPage.enterRandomAddress1();
        parentPage.enterRandomCity();
        parentPage.enterRandomZipCode();
        parentPage.selectRandomPhoneType();
        parentPage.enterRandomCountryCode();
        parentPage.enterRandomPhoneNumber();
    }
}