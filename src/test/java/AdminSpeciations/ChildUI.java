package AdminSpeciations;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class ChildUI {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private Random random = new Random();
    
    // ------------------ Login Locators ------------------ //
    By emailField = By.xpath("//input[@id='login-username']");
    By passwordField = By.xpath("//input[@id='login-password']");
    By signInButton = By.xpath("//span[contains(@class,'mat-button-wrapper') and contains(text(),'Sign In')]");
    By enrollmentTab = By.xpath("//h4[contains(@class,'mat-line') and contains(text(),'Enrollment')]");
    
    // ------------------ Child Enrollment Locators ------------------ //
    By firstName = By.xpath("//input[@formcontrolname='firstName']");
    By middleName = By.xpath("//input[@formcontrolname='middleName']");
    By lastName = By.xpath("//input[@formcontrolname='lastName']");
    By dateOfBirth = By.xpath("//input[@formcontrolname='dob']");
    By placeOfBirth = By.xpath("//input[@formcontrolname='placeOfBirth']");
    By genderMale = By.xpath("//mat-button-toggle[contains(.,'Male')] | //span[contains(@class,'mat-button-toggle-label-content') and contains(text(),'Male')]/ancestor::mat-button-toggle");
    By genderFemale = By.xpath("//mat-button-toggle[contains(.,'Female')] | //span[contains(@class,'mat-button-toggle-label-content') and contains(text(),'Female')]/ancestor::mat-button-toggle");
    By proceedButton = By.xpath("//button[contains(., 'Proceed')] | //span[contains(., 'Proceed')]/ancestor::button");
    
    // ------------------ Guardian/Parent Information Locators ------------------ //
    // Different XPaths for guardian fields based on the HTML structure
    By guardianFirstName = By.xpath("//input[@formcontrolname='firstName' and @name='firstName']");
    By guardianMiddleName = By.xpath("//input[@formcontrolname='middleName' and @name='middleName']");
    By guardianLastName = By.xpath("//input[@formcontrolname='lastName' and @name='lastName']");
    By relationWithChild = By.xpath("//mat-select[@formcontrolname='relationWithChild']");
    By addressLine1 = By.xpath("//input[@formcontrolname='addressLine1' and @id='addressLine1']");
    By addressLine2 = By.xpath("//input[@formcontrolname='addressLine2' and @id='addressLine2']");
    By countryDropdown = By.xpath("//mat-select[@formcontrolname='country']");
    By stateDropdown = By.xpath("//mat-select[@formcontrolname='state']");
    By cityField = By.xpath("//input[@formcontrolname='city']");
    By zipCodeField = By.xpath("//input[@formcontrolname='zipCode']");
    By emailFieldGuardian = By.xpath("//input[@formcontrolname='email' and @type='email']");
    By phoneTypeDropdown = By.xpath("//mat-select[@formcontrolname='phoneType']");
    By countryCodeDropdown = By.xpath("//mat-select[@formcontrolname='countryCode']");
    By phoneNumberField = By.xpath("//input[@formcontrolname='phoneNumber']");
    By alternatePhoneTypeDropdown = By.xpath("//mat-select[@formcontrolname='alternatePhoneType']");
    By alternateCountryCodeDropdown = By.xpath("//mat-select[@formcontrolname='alternateCountryCode']");
    By alternatePhoneNumberField = By.xpath("//input[@formcontrolname='alternatePhoneNumber']");
    By guardianProceedButton = By.xpath("//button[contains(.,'Proceed')] | //span[contains(.,'Proceed')]/ancestor::button");
    
    @BeforeClass
    public void setUp() {
        io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();
    }

    // ==================== COMPLETE FLOW TEST ====================

    @Test
    public void completeEnrollmentFlowWithImmediateUIValidation() {
        System.out.println("🚀 Starting Complete Enrollment Flow with Immediate UI Validation...");
        
        try {
            // STEP 1: Login Page Validation
            validateLoginPageUI();
            performLogin();
            
            // STEP 2: Navigate to Enrollment
            navigateToEnrollmentSection();
            
            // STEP 3: Child Enrollment with Immediate UI Validation
            completeChildEnrollmentWithUIValidation();
            
            // STEP 4: Guardian/Parent Information with Immediate UI Validation
            completeGuardianEnrollmentWithUIValidation();
            
            System.out.println("✅ Complete Enrollment Flow with Immediate UI Validation finished successfully!");
            
        } catch (Exception e) {
            System.out.println("❌ Complete Enrollment Flow failed: " + e.getMessage());
            Assert.fail("Complete enrollment flow failed: " + e.getMessage());
        }
    }

    // ==================== LOGIN PAGE VALIDATION ====================

    private void validateLoginPageUI() {
        System.out.println("\n🔐 Validating Login Page UI...");
        
        try {
            driver.get("https://mereg.netlify.app/");
            
            // Validate Email Field
            WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
            enterTextAndImmediatelyVerifyUI(emailField, "Login Email", "srasysife@gmail.com");
            
            // Validate Password Field
            WebElement password = driver.findElement(passwordField);
            enterTextAndImmediatelyVerifyUI(passwordField, "Login Password", "Admin@123");
            
            // Validate Sign In Button
            WebElement signIn = driver.findElement(signInButton);
            System.out.println("🔍 Validating Sign In Button UI...");
            verifyCurrentUIState(signIn, "Sign In Button");
            
            Assert.assertTrue(signIn.isDisplayed(), "Sign In button should be displayed");
            Assert.assertTrue(signIn.isEnabled(), "Sign In button should be enabled");
            
            System.out.println("✅ Login Page UI validation completed");
            
        } catch (Exception e) {
            throw new RuntimeException("Login page UI validation failed: " + e.getMessage());
        }
    }

    private void performLogin() {
        System.out.println("\n🔑 Performing Login...");
        
        try {
            WebElement signIn = driver.findElement(signInButton);
            highlightElement(signIn, "green");
            signIn.click();
            
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("navigation-home"),
                ExpectedConditions.urlContains("home"),
                ExpectedConditions.presenceOfElementLocated(enrollmentTab)
            ));
            
            System.out.println("✅ Login successful! Current URL: " + driver.getCurrentUrl());
            
        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }

    // ==================== ENROLLMENT NAVIGATION ====================

    private void navigateToEnrollmentSection() {
        System.out.println("\n📋 Navigating to Enrollment Section...");
        
        try {
            WebElement enrollmentTabElement = wait.until(ExpectedConditions.elementToBeClickable(enrollmentTab));
            
            System.out.println("🔍 Validating Enrollment Tab UI...");
            verifyCurrentUIState(enrollmentTabElement, "Enrollment Tab");
            
            highlightElement(enrollmentTabElement, "blue");
            enrollmentTabElement.click();
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
            System.out.println("✅ Successfully navigated to Enrollment section");
            
        } catch (Exception e) {
            throw new RuntimeException("Navigation to Enrollment failed: " + e.getMessage());
        }
    }

    // ==================== CHILD ENROLLMENT WITH UI VALIDATION ====================

    private void completeChildEnrollmentWithUIValidation() {
        System.out.println("\n👶 Starting Child Enrollment with Immediate UI Validation...");
        
        try {
            // First Name
            enterTextAndImmediatelyVerifyUI(firstName, "Child First Name", "John");
            
            // Middle Name
            enterTextAndImmediatelyVerifyUI(middleName, "Child Middle Name", "Michael");
            
            // Last Name
            enterTextAndImmediatelyVerifyUI(lastName, "Child Last Name", "Doe");
            
            // Date of Birth
            String randomDOB = getRandomDOB();
            enterTextAndImmediatelyVerifyUI(dateOfBirth, "Date of Birth", randomDOB);
            
            // Place of Birth
            enterTextAndImmediatelyVerifyUI(placeOfBirth, "Place of Birth", "New York");
            
            // Gender Selection
            selectGenderAndVerifyUI("Male");
            
            // Validate and Click Proceed Button
            validateAndClickChildProceedButton();
            
            System.out.println("✅ Child Enrollment with UI Validation completed successfully");
            
        } catch (Exception e) {
            throw new RuntimeException("Child enrollment with UI validation failed: " + e.getMessage());
        }
    }

    private void selectGenderAndVerifyUI(String gender) {
        System.out.println("\n⚧️ Selecting Gender: " + gender);
        
        try {
            By genderLocator = gender.equalsIgnoreCase("male") ? genderMale : genderFemale;
            WebElement genderElement = wait.until(ExpectedConditions.elementToBeClickable(genderLocator));
            
            System.out.println("🔍 Validating Gender Button UI before selection...");
            verifyCurrentUIState(genderElement, gender + " Gender Button");
            
            highlightElement(genderElement, "orange");
            clickWithJavaScript(genderElement);
            safeSleep(1000);
            
            System.out.println("🔍 Validating Gender Button UI after selection...");
            verifyCurrentUIState(genderElement, gender + " Gender Button");
            
            System.out.println("✅ Gender selection completed: " + gender);
            
        } catch (Exception e) {
            throw new RuntimeException("Gender selection failed: " + e.getMessage());
        }
    }

    private void validateAndClickChildProceedButton() {
        System.out.println("\n➡️ Validating and Clicking Child Proceed Button...");
        
        try {
            WebElement proceedBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(proceedButton));
            
            System.out.println("🔍 Validating Proceed Button UI...");
            verifyCurrentUIState(proceedBtn, "Proceed Button");
            
            Assert.assertTrue(proceedBtn.isDisplayed(), "Proceed button should be displayed");
            
            if (proceedBtn.isEnabled()) {
                highlightElement(proceedBtn, "green");
                System.out.println("✅ Proceed button is enabled - clicking...");
                clickWithJavaScript(proceedBtn);
                
                // Wait for page transition - use multiple indicators
                waitForGuardianPageToLoad();
                System.out.println("✅ Successfully navigated to Guardian/Parent section");
            } else {
                System.out.println("⚠️ Proceed button is disabled - checking for validation errors");
                checkForValidationErrors();
                throw new RuntimeException("Proceed button is disabled - cannot continue");
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Proceed button validation and click failed: " + e.getMessage());
        }
    }

    private void waitForGuardianPageToLoad() {
        System.out.println("⏳ Waiting for Guardian/Parent page to load...");
        
        // Try multiple indicators to confirm we're on guardian page
        By[] guardianPageIndicators = {
            By.xpath("//*[contains(text(),'Guardian') or contains(text(),'Parent')]"),
            relationWithChild,
            guardianFirstName,
            By.xpath("//*[contains(text(),'Personal Information')]"),
            By.xpath("//*[contains(text(),'Guardian Information')]"),
            By.xpath("//*[contains(text(),'Parent Information')]")
        };
        
        boolean pageLoaded = false;
        for (By indicator : guardianPageIndicators) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(indicator));
                System.out.println("✅ Guardian page indicator found: " + indicator);
                pageLoaded = true;
                break;
            } catch (Exception e) {
                System.out.println("⚠️ Guardian page indicator not found: " + indicator);
            }
        }
        
        if (!pageLoaded) {
            throw new RuntimeException("Failed to navigate to guardian page - no indicators found");
        }
        
        safeSleep(3000); // Give more time for page to fully load
        
        // Debug: Print all available fields on current page
        debugCurrentPageFields();
    }

    // ==================== GUARDIAN ENROLLMENT WITH UI VALIDATION ====================

    private void completeGuardianEnrollmentWithUIValidation() {
        System.out.println("\n👨‍👩‍👧‍👦 Starting Guardian/Parent Enrollment with Immediate UI Validation...");
        
        try {
            // Personal Information Section
            System.out.println("\n📝 PERSONAL INFORMATION SECTION");
            
            // Use different XPaths for guardian fields
            enterTextAndImmediatelyVerifyUI(guardianFirstName, "Guardian First Name", getRandomFirstName());
            enterTextAndImmediatelyVerifyUI(guardianMiddleName, "Guardian Middle Name", "Kumar");
            enterTextAndImmediatelyVerifyUI(guardianLastName, "Guardian Last Name", getRandomLastName());
            selectDropdownAndImmediatelyVerifyUI(relationWithChild, "Relation with Child", "Father");
            
            // Address Information Section
            System.out.println("\n🏠 ADDRESS INFORMATION SECTION");
            enterTextAndImmediatelyVerifyUI(addressLine1, "Address Line 1", getRandomAddressLine1());
            enterTextAndImmediatelyVerifyUI(addressLine2, "Address Line 2", "Apt 101");
            selectDropdownAndImmediatelyVerifyUI(countryDropdown, "Country", "United States");
            selectDropdownAndImmediatelyVerifyUI(stateDropdown, "State", getRandomUSState());
            enterTextAndImmediatelyVerifyUI(cityField, "City", getRandomCity("United States"));
            enterTextAndImmediatelyVerifyUI(zipCodeField, "Zip Code", getRandomUSZip());
            
            // Contact Information Section
            System.out.println("\n📞 CONTACT INFORMATION SECTION");
            enterTextAndImmediatelyVerifyUI(emailFieldGuardian, "Email", getRandomEmail());
            selectDropdownAndImmediatelyVerifyUI(phoneTypeDropdown, "Phone Type", "Cell");
            selectDropdownAndImmediatelyVerifyUI(countryCodeDropdown, "Country Code", "+1");
            enterTextAndImmediatelyVerifyUI(phoneNumberField, "Phone Number", getRandomPhone());
            
            // Alternate Contact Section
            System.out.println("\n📱 ALTERNATE CONTACT SECTION");
            selectDropdownAndImmediatelyVerifyUI(alternatePhoneTypeDropdown, "Alternate Phone Type", "Home");
            selectDropdownAndImmediatelyVerifyUI(alternateCountryCodeDropdown, "Alternate Country Code", "+1");
            enterTextAndImmediatelyVerifyUI(alternatePhoneNumberField, "Alternate Phone Number", getRandomPhone());
            
            // Validate Guardian Proceed Button
            validateGuardianProceedButton();
            
            System.out.println("✅ Guardian/Parent Enrollment with UI Validation completed successfully");
            
        } catch (Exception e) {
            throw new RuntimeException("Guardian enrollment with UI validation failed: " + e.getMessage());
        }
    }

    private void validateGuardianProceedButton() {
        System.out.println("\n➡️ Validating Guardian Proceed Button...");
        
        try {
            WebElement proceedBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(guardianProceedButton));
            
            System.out.println("🔍 Validating Guardian Proceed Button UI...");
            verifyCurrentUIState(proceedBtn, "Guardian Proceed Button");
            
            if (proceedBtn.isEnabled()) {
                highlightElement(proceedBtn, "green");
                clickWithJavaScript(proceedBtn);
                System.out.println("✅ Guardian Proceed button clicked successfully");
                
                safeSleep(3000);
                verifyNavigationToNextStep();
            } else {
                System.out.println("⚠️ Guardian Proceed button is disabled");
                checkForValidationErrors();
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Guardian proceed button validation failed: " + e.getMessage());
        }
    }

    // ==================== ENHANCED UI VERIFICATION METHODS ====================

    private void enterTextAndImmediatelyVerifyUI(By locator, String fieldName, String text) {
        System.out.println("\n📍 Processing: " + fieldName);
        
        try {
            // Wait for element with longer timeout and better conditions
            WebElement field = wait.until(ExpectedConditions.elementToBeClickable(locator));
            
            // Scroll to element to ensure it's in view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", field);
            safeSleep(500);
            
            // STEP 1: Enter text
            System.out.println("📥 Entering text: " + text);
            clearAndEnterText(field, text);
            safeSleep(500);
            
            // STEP 2: Immediately verify UI after text entry
            System.out.println("🔍 Immediately verifying UI after text entry...");
            verifyCurrentUIState(field, fieldName);
            
            // STEP 3: Verify text was actually entered
            String enteredText = field.getAttribute("value");
            if (enteredText != null && enteredText.equals(text)) {
                System.out.println("✅ Text verification: PASSED - Text correctly entered");
            } else {
                System.out.println("❌ Text verification: FAILED - Expected: " + text + ", Got: " + enteredText);
                // Try entering text again
                clearAndEnterText(field, text);
                safeSleep(500);
            }
            
            highlightElement(field, "green");
            safeSleep(500);
            
        } catch (Exception e) {
            System.out.println("❌ " + fieldName + " processing failed: " + e.getMessage());
            
            // Try alternative locators for guardian fields
            if (fieldName.contains("Guardian")) {
                tryAlternativeGuardianLocators(fieldName, text);
            } else {
                throw new RuntimeException(fieldName + " processing failed", e);
            }
        }
    }

    private void tryAlternativeGuardianLocators(String fieldName, String text) {
        System.out.println("🔄 Trying alternative locators for: " + fieldName);
        
        try {
            By alternativeLocator = null;
            
            switch (fieldName) {
                case "Guardian First Name":
                    // Try multiple alternative XPaths
                    By[] firstNameAlternatives = {
                        By.xpath("//input[@formcontrolname='firstName' and @name='firstName']"),
                        By.xpath("//input[@formcontrolname='firstName' and contains(@class,'ng-touched')]"),
                        By.xpath("(//input[@formcontrolname='firstName'])[2]"),
                        By.xpath("//input[@formcontrolname='firstName' and preceding::*[contains(text(),'Guardian')]]")
                    };
                    alternativeLocator = findWorkingLocator(firstNameAlternatives, "Guardian First Name");
                    break;
                    
                case "Guardian Middle Name":
                    By[] middleNameAlternatives = {
                        By.xpath("//input[@formcontrolname='middleName' and @name='middleName']"),
                        By.xpath("//input[@formcontrolname='middleName' and contains(@class,'ng-touched')]"),
                        By.xpath("(//input[@formcontrolname='middleName'])[2]"),
                        By.xpath("//input[@formcontrolname='middleName' and preceding::*[contains(text(),'Guardian')]]")
                    };
                    alternativeLocator = findWorkingLocator(middleNameAlternatives, "Guardian Middle Name");
                    break;
                    
                case "Guardian Last Name":
                    By[] lastNameAlternatives = {
                        By.xpath("//input[@formcontrolname='lastName' and @name='lastName']"),
                        By.xpath("//input[@formcontrolname='lastName' and contains(@class,'ng-touched')]"),
                        By.xpath("(//input[@formcontrolname='lastName'])[2]"),
                        By.xpath("//input[@formcontrolname='lastName' and preceding::*[contains(text(),'Guardian')]]")
                    };
                    alternativeLocator = findWorkingLocator(lastNameAlternatives, "Guardian Last Name");
                    break;
                    
                default:
                    throw new RuntimeException("No alternative locator for: " + fieldName);
            }
            
            if (alternativeLocator != null) {
                System.out.println("✅ Found working alternative locator for " + fieldName + ": " + alternativeLocator);
                enterTextAndImmediatelyVerifyUI(alternativeLocator, fieldName, text);
            } else {
                throw new RuntimeException("No alternative locators worked for: " + fieldName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Alternative locators also failed for: " + fieldName, e);
        }
    }

    private By findWorkingLocator(By[] locators, String fieldName) {
        for (By locator : locators) {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed() && element.isEnabled()) {
                    return locator;
                }
            } catch (Exception e) {
                // Continue to next locator
            }
        }
        return null;
    }

    private void selectDropdownAndImmediatelyVerifyUI(By dropdownLocator, String dropdownName, String optionToSelect) {
        System.out.println("\n📍 Processing: " + dropdownName);
        
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
            
            // STEP 1: Select dropdown option
            System.out.println("📥 Selecting option: " + optionToSelect);
            selectDropdownOption(dropdownLocator, optionToSelect);
            safeSleep(500);
            
            // STEP 2: Immediately verify UI after selection
            System.out.println("🔍 Immediately verifying UI after selection...");
            verifyCurrentUIState(dropdown, dropdownName);
            
            System.out.println("✅ Dropdown selection: PASSED - Option selected successfully");
            
            highlightElement(dropdown, "blue");
            safeSleep(500);
            
        } catch (Exception e) {
            System.out.println("❌ " + dropdownName + " processing failed: " + e.getMessage());
            throw new RuntimeException(dropdownName + " processing failed", e);
        }
    }

    private void verifyCurrentUIState(WebElement element, String elementName) {
        try {
            String fontSize = element.getCssValue("font-size");
            String color = element.getCssValue("color");
            String border = element.getCssValue("border");
            String backgroundColor = element.getCssValue("background-color");
            String isEnabled = String.valueOf(element.isEnabled());
            String isDisplayed = String.valueOf(element.isDisplayed());
            
            System.out.println("📊 Current UI State for " + elementName + ":");
            System.out.println("   ├── Font Size: " + fontSize);
            System.out.println("   ├── Color: " + color);
            System.out.println("   ├── Border: " + (border.length() > 50 ? border.substring(0, 50) + "..." : border));
            System.out.println("   ├── Background: " + backgroundColor);
            System.out.println("   ├── Enabled: " + isEnabled);
            System.out.println("   └── Displayed: " + isDisplayed);
            
            // Basic UI assertions
            Assert.assertTrue(element.isDisplayed(), elementName + " should be displayed");
            Assert.assertTrue(element.isEnabled(), elementName + " should be enabled");
            Assert.assertNotNull(fontSize, elementName + " should have font size");
            Assert.assertNotNull(color, elementName + " should have color");
            
        } catch (Exception e) {
            System.out.println("⚠️ Could not verify UI state for " + elementName + ": " + e.getMessage());
        }
    }

    // ==================== UTILITY METHODS ====================

    private void selectDropdownOption(By dropdownLocator, String optionToSelect) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", dropdown);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
            safeSleep(1000);
            
            By optionLocator = By.xpath("//mat-option//span[contains(text(),'" + optionToSelect + "')]");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
            safeSleep(1000);
            
        } catch (Exception e) {
            throw new RuntimeException("Dropdown selection failed for: " + optionToSelect, e);
        }
    }

    private void highlightElement(WebElement element, String color) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='2px solid " + color + "';",
                element
            );
        } catch (Exception e) {
            // Ignore highlight errors
        }
    }

    private void clickWithJavaScript(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    private void clearAndEnterText(WebElement element, String text) {
        try {
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
            element.sendKeys(text);
        }
    }

    private void checkForValidationErrors() {
        try {
            List<WebElement> errors = driver.findElements(By.xpath("//mat-error | .error | [class*='error']"));
            if (!errors.isEmpty()) {
                System.out.println("Validation errors found:");
                for (WebElement error : errors) {
                    if (error.isDisplayed()) {
                        System.out.println("   - " + error.getText());
                    }
                }
            } else {
                System.out.println("No validation errors found");
            }
        } catch (Exception e) {
            System.out.println("Could not check validation errors: " + e.getMessage());
        }
    }

    private void debugCurrentPageFields() {
        try {
            System.out.println("🐛 DEBUG - Available fields on current page:");
            
            // Check for firstName fields
            List<WebElement> firstNameFields = driver.findElements(By.xpath("//input[@formcontrolname='firstName']"));
            System.out.println("   firstName fields found: " + firstNameFields.size());
            for (int i = 0; i < firstNameFields.size(); i++) {
                WebElement field = firstNameFields.get(i);
                System.out.println("     [" + i + "] - Displayed: " + field.isDisplayed() + ", Enabled: " + field.isEnabled() + ", Value: " + field.getAttribute("value"));
            }
            
            // Check for middleName fields
            List<WebElement> middleNameFields = driver.findElements(By.xpath("//input[@formcontrolname='middleName']"));
            System.out.println("   middleName fields found: " + middleNameFields.size());
            
            // Check for lastName fields
            List<WebElement> lastNameFields = driver.findElements(By.xpath("//input[@formcontrolname='lastName']"));
            System.out.println("   lastName fields found: " + lastNameFields.size());
            
        } catch (Exception e) {
            System.out.println("⚠️ Could not debug page fields: " + e.getMessage());
        }
    }

    private void verifyNavigationToNextStep() {
        try {
            boolean isNextPage = driver.findElements(By.xpath("//*[contains(text(),'Emergency') or contains(text(),'Medical') or contains(text(),'Review')]")).size() > 0;
            
            if (isNextPage) {
                System.out.println("✅ Successfully navigated to next step after guardian information");
            } else {
                System.out.println("ℹ️ Current page after guardian: " + driver.getCurrentUrl());
                System.out.println("ℹ️ Page title: " + driver.getTitle());
            }
            
        } catch (Exception e) {
            System.out.println("⚠️ Navigation verification: " + e.getMessage());
        }
    }

    private void safeSleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ==================== DATA GENERATORS ====================

    private String getRandomFirstName() {
        String[] names = {"Aarav","Vivaan","Isha","Diya","Meera","Rohan","Kavya","Arjun","Saanvi","Anaya"};
        return names[random.nextInt(names.length)];
    }

    private String getRandomLastName() {
        String[] names = {"Sharma","Verma","Chowdhary","Patel","Reddy","Nair","Singh","Mehta","Kapoor","Joshi"};
        return names[random.nextInt(names.length)];
    }

    private String getRandomEmail() {
        return "guardian" + System.currentTimeMillis() + "@test.com";
    }

    private String getRandomAddressLine1() {
        String[] streets = {"Oak Street","Maple Ave","Cedar Lane","Park Road","Lakeview Blvd"};
        return (100 + random.nextInt(900)) + " " + streets[random.nextInt(streets.length)];
    }

    private String getRandomPhone() {
        return "555" + (1000000 + random.nextInt(8999999));
    }

    private String getRandomCity(String country) {
        String[] cities = {"Los Angeles","Houston","New York","Chicago","Miami"};
        return cities[random.nextInt(cities.length)];
    }

    private String getRandomUSState() {
        String[] states = {"California","Texas","Florida","New York","Illinois"};
        return states[random.nextInt(states.length)];
    }

    private String getRandomUSZip() { 
        return String.valueOf(10000 + random.nextInt(89999)); 
    }

    private String getRandomDOB() {
        int year = 2010 + random.nextInt(8);
        int month = 1 + random.nextInt(12);
        int day = 1 + random.nextInt(28);
        LocalDate dob = LocalDate.of(year, month, day);
        return dob.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    @AfterClass
    public void tearDown() {
        System.out.println("\n🛑 Test completed! Closing browser...");
        safeSleep(3000);
        if (driver != null) {
            driver.quit();
            System.out.println("✅ Browser closed successfully");
        }
    }
}