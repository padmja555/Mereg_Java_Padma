package AdminTestcase;

import org.testng.annotations.Listeners;

import org.testng.annotations.Test;
import org.testng.Assert;
import AdminPages.Childinfo;
import AdminPages.Parentpage;
import AdminPages.LoginPage;
import AdminPages.PhysicianInfoPage;
import Base.BaseDriver;

//import org.bouncycastle.oer.its.ieee1609dot2.basetypes.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Listeners(listeners.TestListener.class)
public class PhysicianInfoNegativeTest extends BaseDriver {
    
    @Test(priority = 1)
    public void testEmptyRequiredFields() {
        try {
            // Login and navigate to physician info page
            navigateToPhysicianInfoPage();
            
            PhysicianInfoPage medicalinfo = new PhysicianInfoPage(driver);
            
            // Click proceed without filling any required fields
            medicalinfo.proceedToNext();
            Thread.sleep(2000);
            
            // Verify error messages for required fields
            Assert.assertTrue(isErrorMessageDisplayed(), "Error messages should be displayed for required fields");
            System.out.println("Test Case 1 Passed: Empty required fields validation working correctly");
            
        } catch (Exception e) {
            System.out.println("Error in empty required fields test: " + e.getMessage());
            Assert.fail("Empty required fields test failed: " + e.getMessage());
        }
    }
    
    @Test(priority = 2)
    public void testInvalidPhoneNumberFormat() {
        try {
            navigateToPhysicianInfoPage();
            
            PhysicianInfoPage medicalinfo = new PhysicianInfoPage(driver);
            
            // Fill valid information for other fields
            medicalinfo.enterRandomPhysicianFirstName();
            medicalinfo.enterRandomPhysicianLastName();
            medicalinfo.enterRandomPhysicianAddress1();
            medicalinfo.selectRandomPhysicianCountry();
            medicalinfo.selectPhysicianState();
            medicalinfo.enterRandomPhysicianCity();
            medicalinfo.enterRandomPhysicianZipCode();
            medicalinfo.selectRandomPhysicianPhoneType();
            medicalinfo.selectRandomPhysicianCountryCode();
            
            // Enter invalid phone number (less than 10 digits)
            enterText(medicalinfo.physicianPhoneNumber, "12345", "Invalid Phone Number");
            
            medicalinfo.proceedToNext();
            Thread.sleep(2000);
            
            // Verify error message for invalid phone format
            Assert.assertTrue(isPhoneFormatErrorDisplayed(), "Phone format error should be displayed");
            System.out.println("Test Case 2 Passed: Invalid phone number format properly rejected");
            
        } catch (Exception e) {
            System.out.println("Error in invalid phone format test: " + e.getMessage());
            Assert.fail("Invalid phone format test failed: " + e.getMessage());
        }
    }
    
    @Test(priority = 3)
    public void testInvalidZipCodeFormat() {
        try {
            navigateToPhysicianInfoPage();
            
            PhysicianInfoPage medicalinfo = new PhysicianInfoPage(driver);
            
            // Fill valid information for other fields
            medicalinfo.enterRandomPhysicianFirstName();
            medicalinfo.enterRandomPhysicianLastName();
            medicalinfo.enterRandomPhysicianAddress1();
            medicalinfo.selectRandomPhysicianCountry();
            medicalinfo.selectPhysicianState();
            medicalinfo.enterRandomPhysicianCity();
            medicalinfo.selectRandomPhysicianPhoneType();
            medicalinfo.selectRandomPhysicianCountryCode();
            medicalinfo.enterRandomPhysicianPhoneNumber();
            
            // Enter invalid zip code (non-numeric)
            enterText(medicalinfo.physicianZipCode, "ABCDE", "Invalid Zip Code");
            
            medicalinfo.proceedToNext();
            Thread.sleep(2000);
            
            // Verify error message for invalid zip code
            Assert.assertTrue(isZipCodeErrorDisplayed(), "Zip code error should be displayed");
            System.out.println("Test Case 3 Passed: Invalid zip code format properly rejected");
            
        } catch (Exception e) {
            System.out.println("Error in invalid zip code test: " + e.getMessage());
            Assert.fail("Invalid zip code test failed: " + e.getMessage());
        }
    }
    
    @Test(priority = 4)
    public void testMaxLengthValidation() {
        try {
            navigateToPhysicianInfoPage();
            
            PhysicianInfoPage medicalinfo = new PhysicianInfoPage(driver);
            
            // Generate very long strings
            String veryLongName = generateString(256); // Longer than typical max length
            String veryLongAddress = generateString(501); // Longer than typical max length
            
            // Test max length for first name
            enterText(medicalinfo.physicianFirstName, veryLongName, "Very Long First Name");
            
            // Test max length for address
            enterText(medicalinfo.physicianAddress1, veryLongAddress, "Very Long Address");
            
            medicalinfo.proceedToNext();
            Thread.sleep(2000);
            
            // Verify that values are truncated or error is shown
            Assert.assertTrue(isMaxLengthErrorDisplayed() || areFieldsTruncated(), 
                "Max length validation should work");
            System.out.println("Test Case 4 Passed: Maximum length validation working correctly");
            
        } catch (Exception e) {
            System.out.println("Error in max length validation test: " + e.getMessage());
            Assert.fail("Max length validation test failed: " + e.getMessage());
        }
    }
    
    @Test(priority = 5)
    public void testSpecialCharactersInNameFields() {
        try {
            navigateToPhysicianInfoPage();
            
            PhysicianInfoPage medicalinfo = new PhysicianInfoPage(driver);
            
            // Enter names with special characters
            enterText(medicalinfo.physicianFirstName, "John@123", "Name with special chars");
            enterText(medicalinfo.physicianLastName, "Doe#456", "Name with special chars");
            
            medicalinfo.proceedToNext();
            Thread.sleep(2000);
            
            // Verify error or successful submission based on requirements
            boolean hasError = isNameFormatErrorDisplayed();
            Assert.assertTrue(hasError, "Special characters in name fields should cause validation error");
            System.out.println("Test Case 5 Passed: Special characters in name fields properly rejected");
            
        } catch (Exception e) {
            System.out.println("Error in special characters test: " + e.getMessage());
            Assert.fail("Special characters test failed: " + e.getMessage());
        }
    }
    /*
    @Test(priority = 6)
    public void testStateSelectionWithoutCountry() {
        try {
            navigateToPhysicianInfoPage();
            
            PhysicianInfoPage medicalinfo = new PhysicianInfoPage(driver);
            
            // Clear country selection if any
            // This depends on your application implementation
            
            // Try to select state without selecting country first
            boolean stateDropdownEnabled = medicalinfo.isElementEnabled(medicalinfo.physicianStateDropdown);
            
            // Verify that state selection is disabled or shows appropriate message
            Assert.assertFalse(stateDropdownEnabled, 
                "State should not be selectable without country selection");
            System.out.println("Test Case 6 Passed: State selection properly restricted without country");
            
        } catch (Exception e) {
            System.out.println("Error in state without country test: " + e.getMessage());
            Assert.fail("State without country test failed: " + e.getMessage());
        }
    }

    @Test(priority = 6)
    public void testStateSelectionWithoutCountry() {
        navigateToPhysicianInfoPage();
        
        PhysicianInfoPage medicalinfo = new PhysicianInfoPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        
        try {
            // Clear country selection
            medicalinfo.clearCountrySelection();
            
            // Wait for any UI updates to complete
            wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(
                medicalinfo.physicianCountryDropdown, "class", "loading")));
            
            // Test multiple aspects of disabled state
            WebElement stateDropdown = medicalinfo.physicianStateDropdown;
            
            // Check if dropdown is interactable
            boolean isEnabled = stateDropdown.isEnabled();
            boolean isDisplayed = stateDropdown.isDisplayed();
            boolean hasDisabledAttr = stateDropdown.getAttribute("disabled") != null;
            boolean hasDisabledClass = stateDropdown.getAttribute("class").contains("disabled");
            
            // The dropdown should either be not enabled, have disabled attribute, or have disabled class
            boolean isEffectivelyDisabled = !isEnabled || hasDisabledAttr || hasDisabledClass;
            
            Assert.assertTrue(isEffectivelyDisabled, 
                "State dropdown should be disabled when no country is selected. " +
                "Enabled: " + isEnabled + ", DisabledAttr: " + hasDisabledAttr + 
                ", DisabledClass: " + hasDisabledClass);
            
            System.out.println("Test Case 6 Passed: State selection properly restricted without country");
            
        } catch (NoSuchElementException e) {
            Assert.fail("State dropdown element not found: " + e.getMessage());
        } catch (TimeoutException e) {
            Assert.fail("UI did not update within timeout after clearing country selection");
        } catch (Exception e) {
            Assert.fail("Test failed with unexpected error: " + e.getMessage());
        }
    }
        */
    
    @Test(priority = 7)
    public void testEmergencyInfoWithoutUsePhysicianCheckbox() {
        try {
            navigateToPhysicianInfoPage();
            
            PhysicianInfoPage medicalinfo = new PhysicianInfoPage(driver);
            
            // Fill physician info
            medicalinfo.enterRandomPhysicianFirstName();
            medicalinfo.enterRandomPhysicianLastName();
            medicalinfo.enterRandomPhysicianAddress1();
            medicalinfo.selectRandomPhysicianCountry();
            medicalinfo.selectPhysicianState();
            medicalinfo.enterRandomPhysicianCity();
            medicalinfo.enterRandomPhysicianZipCode();
            medicalinfo.selectRandomPhysicianPhoneType();
            medicalinfo.selectRandomPhysicianCountryCode();
            medicalinfo.enterRandomPhysicianPhoneNumber();
            
            // Don't check "Use physician for all" checkbox
            // Leave emergency care fields empty
            
            medicalinfo.proceedToNext();
            Thread.sleep(2000);
            
            // Verify error messages for required emergency care fields
            Assert.assertTrue(isEmergencyInfoErrorDisplayed(), 
                "Emergency info errors should be displayed when not using physician for all");
            System.out.println("Test Case 7 Passed: Emergency info validation working correctly");
            
        } catch (Exception e) {
            System.out.println("Error in emergency info test: " + e.getMessage());
            Assert.fail("Emergency info test failed: " + e.getMessage());
        }
    }
    
    @Test(priority = 8)
    public void testDuplicatePhoneNumbers() {
        try {
            navigateToPhysicianInfoPage();
            
            PhysicianInfoPage medicalinfo = new PhysicianInfoPage(driver);
            
            // Fill valid information
            medicalinfo.enterRandomPhysicianFirstName();
            medicalinfo.enterRandomPhysicianLastName();
            medicalinfo.enterRandomPhysicianAddress1();
            medicalinfo.selectRandomPhysicianCountry();
            medicalinfo.selectPhysicianState();
            medicalinfo.enterRandomPhysicianCity();
            medicalinfo.enterRandomPhysicianZipCode();
            medicalinfo.selectRandomPhysicianPhoneType();
            medicalinfo.selectRandomPhysicianCountryCode();
            
            // Get the phone number entered for physician
            String phoneNumber = "1234567890";
            enterText(medicalinfo.physicianPhoneNumber, phoneNumber, "Physician Phone Number");
            
            // Enter the same phone number for alternate phone
            enterText(medicalinfo.alternatePhoneNumber, phoneNumber, "Duplicate Phone Number");
            
            medicalinfo.proceedToNext();
            Thread.sleep(2000);
            
            // Verify if duplicate phone numbers are allowed or not
            boolean hasError = isDuplicatePhoneErrorDisplayed();
            Assert.assertTrue(hasError, "Duplicate phone numbers should cause validation error");
            System.out.println("Test Case 8 Passed: Duplicate phone numbers properly rejected");
            
        } catch (Exception e) {
            System.out.println("Error in duplicate phone test: " + e.getMessage());
            Assert.fail("Duplicate phone test failed: " + e.getMessage());
        }
    }
    
    @Test(priority = 9)
    public void testSQLInjectionAttempt() {
        try {
            navigateToPhysicianInfoPage();
            
            PhysicianInfoPage medicalinfo = new PhysicianInfoPage(driver);
            
            // Attempt SQL injection in text fields
            String sqlInjection = "'; DROP TABLE users; --";
            enterText(medicalinfo.physicianFirstName, sqlInjection, "SQL Injection Attempt");
            enterText(medicalinfo.physicianAddress1, sqlInjection, "SQL Injection Attempt");
            
            medicalinfo.proceedToNext();
            Thread.sleep(2000);
            
            // Verify that the application doesn't crash and handles it gracefully
            Assert.assertTrue(isAppStillResponsive(), "Application should not crash on SQL injection attempt");
            System.out.println("Test Case 9 Passed: SQL injection attempt properly handled");
            
        } catch (Exception e) {
            System.out.println("Error in SQL injection test: " + e.getMessage());
            Assert.fail("SQL injection test failed: " + e.getMessage());
        }
    }
    
    @Test(priority = 10)
    public void testXSSAttempt() {
        try {
            navigateToPhysicianInfoPage();
            
            PhysicianInfoPage medicalinfo = new PhysicianInfoPage(driver);
            
            // Attempt XSS in text fields
            String xssAttempt = "<script>alert('XSS')</script>";
            enterText(medicalinfo.physicianFirstName, xssAttempt, "XSS Attempt");
            enterText(medicalinfo.physicianAddress1, xssAttempt, "XSS Attempt");
            
            medicalinfo.proceedToNext();
            Thread.sleep(2000);
            
            // Verify that the script doesn't execute
            Assert.assertTrue(isXSSPrevented(), "XSS attempt should be prevented");
            System.out.println("Test Case 10 Passed: XSS attempt properly prevented");
            
        } catch (Exception e) {
            System.out.println("Error in XSS test: " + e.getMessage());
            Assert.fail("XSS test failed: " + e.getMessage());
        }
    }
    
    // Helper methods for navigation and verification
    private void navigateToPhysicianInfoPage() {
        try {
            // Login to the application
            LoginPage login = new LoginPage(driver);
            login.enterEmail("srasysife@gmail.com");
            login.enterPassword("Admin@123");
            login.clickSignIn();
            Thread.sleep(5000);

            // Navigate to the parent info page via child info
            Childinfo childinfo = new Childinfo(driver);
            childinfo.clickOnenrollment();
            Thread.sleep(3000);
            
            childinfo.enterRandomFirstAndLastName();
            Thread.sleep(1500);
            
            childinfo.selectGender();
            Thread.sleep(1500);
            
            childinfo.enterrandomDOB();
            Thread.sleep(1500);
            
            childinfo.ClickonProceedtoGuardian();
            Thread.sleep(5000);

            // Fill out the parent/guardian information
            Parentpage parentguardianinfo = new Parentpage(driver);
            
            parentguardianinfo.enterRandomFirstName();
            Thread.sleep(1000);
            
            parentguardianinfo.enterRandomMiddleName();
            Thread.sleep(1000);
            
            parentguardianinfo.enterRandomLastName();
            Thread.sleep(1000);
            
            parentguardianinfo.selectRelationship();
            Thread.sleep(2000);
            
            parentguardianinfo.enterRandomAddress1();
            Thread.sleep(1000);
            
            parentguardianinfo.enterRandomAddress2();
            Thread.sleep(1000);
            
            parentguardianinfo.selectRandomCountry();
            Thread.sleep(2000);
            
            parentguardianinfo.selectState();
            Thread.sleep(2000);
            
            parentguardianinfo.enterRandomCity();
            Thread.sleep(1000);
            
            parentguardianinfo.enterRandomZipCode();
            Thread.sleep(1000);
            
            parentguardianinfo.selectRandomPhoneType();
            Thread.sleep(2000);
            
            parentguardianinfo.enterRandomCountryCode();
            Thread.sleep(2000);
            
            parentguardianinfo.enterRandomPhoneNumber();
            Thread.sleep(2000);
            
            // Click proceed to medical info
            parentguardianinfo.proceedToMedicalInfoButton();
            Thread.sleep(8000);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to physician info page", e);
        }
    }
    
    private void enterText(By locator, String text, String fieldName) {
        try {
            WebElement element = driver.findElement(locator);
            element.clear();
            element.sendKeys(text);
            System.out.println("Entered " + fieldName + ": " + text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter text in field: " + fieldName, e);
        }
    }
    
    private boolean isErrorMessageDisplayed() {
        // Implementation to check if error messages are displayed
        try {
            // Look for common error message elements
            return driver.findElements(By.xpath("//mat-error | //div[contains(@class, 'error')] | //span[contains(@class, 'error')]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean isPhoneFormatErrorDisplayed() {
        // Implementation to check for phone format error specifically
        try {
            return driver.findElements(By.xpath("//*[contains(text(), 'phone') and contains(text(), 'format')] | " +
                                               "//*[contains(text(), 'phone') and contains(text(), 'invalid')]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean isZipCodeErrorDisplayed() {
        // Implementation to check for zip code error specifically
        try {
            return driver.findElements(By.xpath("//*[contains(text(), 'zip')] | " +
                                               "//*[contains(text(), 'postal')] | " +
                                               "//*[contains(text(), 'code')]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    private String generateString(int length) {
        // Generate a long string of specified length
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("a");
        }
        return sb.toString();
    }
    
    private boolean isMaxLengthErrorDisplayed() {
        // Check if max length error is displayed
        try {
            return driver.findElements(By.xpath("//*[contains(text(), 'max') and contains(text(), 'length')] | " +
                                               "//*[contains(text(), 'too long')] | " +
                                               "//*[contains(text(), 'maximum')]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean areFieldsTruncated() {
        // Check if long values were truncated
        try {
            WebElement firstNameField = driver.findElement(By.xpath("(//input[@name='firstName'])[3]"));
            String firstNameValue = firstNameField.getAttribute("value");
            return firstNameValue.length() < 256; // Assuming max length is less than 256
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean isNameFormatErrorDisplayed() {
        // Check for name format errors
        try {
            return driver.findElements(By.xpath("//*[contains(text(), 'name') and contains(text(), 'invalid')] | " +
                                               "//*[contains(text(), 'name') and contains(text(), 'format')] | " +
                                               "//*[contains(text(), 'special characters')]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean isEmergencyInfoErrorDisplayed() {
        // Check for emergency info errors
        try {
            return driver.findElements(By.xpath("//*[contains(text(), 'emergency')] | " +
                                               "//*[contains(text(), 'care facility')]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean isDuplicatePhoneErrorDisplayed() {
        // Check for duplicate phone error
        try {
            return driver.findElements(By.xpath("//*[contains(text(), 'duplicate') and contains(text(), 'phone')] | " +
                                               "//*[contains(text(), 'same') and contains(text(), 'phone')]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean isAppStillResponsive() {
        // Check if application is still responsive
        try {
            // Try to interact with any element on the page
            driver.findElement(By.xpath("//h2[contains(text(), 'Physician')]")).isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private boolean isXSSPrevented() {
        // Check if XSS was prevented by verifying the script tag is not rendered
        try {
            // Check if the script tag appears in the page source as text (not as executable code)
            String pageSource = driver.getPageSource();
            return pageSource.contains("<script>") && 
                   !driver.findElements(By.tagName("script")).toString().contains("alert('XSS')");
        } catch (Exception e) {
            return false;
        }
    }
}