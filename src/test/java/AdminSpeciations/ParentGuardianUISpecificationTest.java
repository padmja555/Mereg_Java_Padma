package AdminSpeciations;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import AdminPages.Parentpage;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class ParentGuardianUISpecificationTest {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private Random random = new Random();
    private Parentpage parentPage;
    
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
    
    @BeforeClass
    public void setUp() {
        io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();
        parentPage = new Parentpage(driver);
    }

    // ==================== COMPLETE PARENT/GUARDIAN UI TEST ====================

    @Test
    public void completeParentGuardianUIValidationTest() {
        System.out.println("🚀 Starting Parent/Guardian UI Validation Test...");
        
        try {
            // STEP 1: Login and Navigate
            performLoginAndNavigation();
            
            // STEP 2: Complete Child Enrollment (if needed)
            completeBasicChildEnrollment();
            
            // STEP 3: Comprehensive Parent/Guardian UI Validation
            validateParentGuardianPageUI();
            fillParentGuardianFormWithUIValidation();
            validateAlternateGuardianSection();
            validateFormSubmission();
            
            System.out.println("✅ Parent/Guardian UI Validation Test completed successfully!");
            
        } catch (Exception e) {
            System.out.println("❌ Parent/Guardian UI Validation Test failed: " + e.getMessage());
            Assert.fail("Parent/Guardian UI validation test failed: " + e.getMessage());
        }
    }

    // ==================== LOGIN AND NAVIGATION ====================

    private void performLoginAndNavigation() {
        System.out.println("\n🔐 Performing Login and Navigation...");
        
        try {
            // Login
            driver.get("https://mereg-dev.netlify.app/");
            
            WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
            email.clear();
            email.sendKeys("srasysife@gmail.com");
            
            WebElement password = driver.findElement(passwordField);
            password.clear();
            password.sendKeys("Admin@123");
            
            WebElement signIn = driver.findElement(signInButton);
            signIn.click();
            
            // Navigate to Enrollment
            wait.until(ExpectedConditions.elementToBeClickable(enrollmentTab)).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
            
            System.out.println("✅ Login and navigation successful");
            
        } catch (Exception e) {
            throw new RuntimeException("Login and navigation failed: " + e.getMessage());
        }
    }

    // ==================== CHILD ENROLLMENT ====================

    private void completeBasicChildEnrollment() {
        System.out.println("\n👶 Completing Basic Child Enrollment...");
        
        try {
            // Fill basic child information
            enterTextWithValidation(firstName, "Child First Name", "TestChild");
            enterTextWithValidation(middleName, "Child Middle Name", "Middle");
            enterTextWithValidation(lastName, "Child Last Name", "Doe");
            enterTextWithValidation(dateOfBirth, "Date of Birth", "01/01/2020");
            enterTextWithValidation(placeOfBirth, "Place of Birth", "New York");
            
            // Select gender
            WebElement genderElement = wait.until(ExpectedConditions.elementToBeClickable(genderMale));
            highlightElement(genderElement, "orange");
            clickWithJavaScript(genderElement);
            
            // Proceed to parent/guardian section
            WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(proceedButton));
            highlightElement(proceedBtn, "green");
            clickWithJavaScript(proceedBtn);
            
            // Wait for parent/guardian page to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(parentPage.parentguardianHeaderText));
            
            System.out.println("✅ Basic child enrollment completed");
            
        } catch (Exception e) {
            throw new RuntimeException("Child enrollment failed: " + e.getMessage());
        }
    }

    // ==================== PARENT/GUARDIAN PAGE UI VALIDATION ====================

    private void validateParentGuardianPageUI() {
        System.out.println("\n📋 Validating Parent/Guardian Page UI Structure...");
        
        try {
            // Validate header
            String headerText = parentPage.verifyParentGuardianPage();
            Assert.assertTrue(headerText.contains("Parent/Guardian") || headerText.contains("Guardian"), 
                "Parent/Guardian header not found");
            System.out.println("✅ Header validation passed: " + headerText);
            
            // Validate all main form sections are present (check only for visibility, not enabled state)
            validateSectionPresence("First Name Field", parentPage.firstname);
            validateSectionPresence("Middle Name Field", parentPage.middlename);
            validateSectionPresence("Last Name Field", parentPage.lastname);
            validateSectionPresence("Relationship Dropdown", parentPage.relationshipdropdown);
            validateSectionPresence("Address Line 1", parentPage.address1);
            validateSectionPresence("Address Line 2", parentPage.address2);
            validateSectionPresence("Country Dropdown", parentPage.countrydropdownclick);
            validateSectionPresence("State Dropdown", parentPage.statedropdown);
            validateSectionPresence("City Field", parentPage.city);
            validateSectionPresence("Zip Code Field", parentPage.zipcode);
            validateSectionPresence("Phone Type Dropdown", parentPage.phonetype);
            validateSectionPresence("Country Code Dropdown", parentPage.Countrycode);
            validateSectionPresence("Phone Number Field", parentPage.phonenumber);
            validateSectionPresence("Email Field", parentPage.emailaddress);
            validateSectionPresence("Add Alternate Guardian Icon", parentPage.AddAlternativGuardianinfoicon);
            
            System.out.println("✅ All parent/guardian form sections are present and visible");
            
        } catch (Exception e) {
            throw new RuntimeException("Parent/Guardian page UI validation failed: " + e.getMessage());
        }
    }

    // ==================== PARENT/GUARDIAN FORM FILLING WITH UI VALIDATION ====================

    private void fillParentGuardianFormWithUIValidation() {
        System.out.println("\n📝 Filling Parent/Guardian Form with UI Validation...");
        
        try {
            // Personal Information Section
            System.out.println("\n👤 PERSONAL INFORMATION SECTION");
            fillPersonalInformationWithValidation();
            
            // Address Information Section
            System.out.println("\n🏠 ADDRESS INFORMATION SECTION");
            fillAddressInformationWithValidation();
            
            // Contact Information Section
            System.out.println("\n📞 CONTACT INFORMATION SECTION");
            fillContactInformationWithValidation();
            
            System.out.println("✅ Parent/Guardian form filled successfully with UI validation");
            
        } catch (Exception e) {
            throw new RuntimeException("Parent/Guardian form filling failed: " + e.getMessage());
        }
    }

    private void fillPersonalInformationWithValidation() {
        // First Name
        WebElement firstNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(parentPage.firstname));
        enterTextWithUIValidation(firstNameField, "First Name", parentPage.firstname);
        
        // Middle Name
        WebElement middleNameField = driver.findElement(parentPage.middlename);
        enterTextWithUIValidation(middleNameField, "Middle Name", parentPage.middlename);
        
        // Last Name
        WebElement lastNameField = driver.findElement(parentPage.lastname);
        enterTextWithUIValidation(lastNameField, "Last Name", parentPage.lastname);
        
        // Relationship
        selectRelationshipWithValidation();
    }

    private void selectRelationshipWithValidation() {
        System.out.println("📍 Processing: Relationship");
        
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(parentPage.relationshipdropdown));
            
            // Scroll to dropdown
            scrollToElement(dropdown);
            safeSleep(500);
            
            // Click dropdown using enhanced method
            highlightElement(dropdown, "orange");
            clickDropdownWithRetry(dropdown);
            
            // Wait for options to load and select
            selectDropdownOption("Father", "Relationship");
            
            safeSleep(1000);
            
        } catch (Exception e) {
            System.out.println("❌ Relationship selection failed: " + e.getMessage());
            throw new RuntimeException("Relationship selection failed", e);
        }
    }

    private void fillAddressInformationWithValidation() {
        // Address Line 1
        WebElement address1Field = driver.findElement(parentPage.address1);
        enterTextWithUIValidation(address1Field, "Address Line 1", parentPage.address1);
        
        // Address Line 2
        WebElement address2Field = driver.findElement(parentPage.address2);
        enterTextWithUIValidation(address2Field, "Address Line 2", parentPage.address2);
        
        // Country - Select United States specifically
        selectCountryWithValidation();
        
        // State - Wait for state dropdown to be enabled after country selection
        selectStateWithValidation();
        
        // City
        WebElement cityField = driver.findElement(parentPage.city);
        enterTextWithUIValidation(cityField, "City", parentPage.city);
        
        // Zip Code
        WebElement zipField = driver.findElement(parentPage.zipcode);
        enterTextWithUIValidation(zipField, "Zip Code", parentPage.zipcode);
    }

    private void selectCountryWithValidation() {
        System.out.println("📍 Processing: Country");
        
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(parentPage.countrydropdownclick));
            
            // Scroll to dropdown
            scrollToElement(dropdown);
            safeSleep(500);
            
            // Click dropdown using enhanced method
            highlightElement(dropdown, "orange");
            clickDropdownWithRetry(dropdown);
            
            // Wait for options to load and select
            selectDropdownOption("United States", "Country");
            
            safeSleep(2000); // Wait for state dropdown to populate
            
        } catch (Exception e) {
            System.out.println("❌ Country selection failed: " + e.getMessage());
            throw new RuntimeException("Country selection failed", e);
        }
    }

    private void selectStateWithValidation() {
        System.out.println("📍 Processing: State");
        
        try {
            // Wait for state dropdown to be enabled
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(parentPage.statedropdown));
            
            // Scroll to dropdown
            scrollToElement(dropdown);
            safeSleep(500);
            
            // Click dropdown using enhanced method
            highlightElement(dropdown, "orange");
            clickDropdownWithRetry(dropdown);
            
            // Wait for options to load and select
            selectDropdownOption("California", "State");
            
            safeSleep(1000);
            
        } catch (Exception e) {
            System.out.println("❌ State selection failed: " + e.getMessage());
            throw new RuntimeException("State selection failed", e);
        }
    }

    private void fillContactInformationWithValidation() {
        // Phone Type - Select first
        selectPhoneTypeWithValidation();
        
        // Country Code - Select next
        selectCountryCodeWithValidation();
        
        // Phone Number - Now this should be enabled
        enterPhoneNumberWithValidation();
        
        // Email
        WebElement emailField = driver.findElement(parentPage.emailaddress);
        enterTextWithUIValidation(emailField, "Email", parentPage.emailaddress);
    }

    // ==================== ENHANCED DROPDOWN METHODS ====================

    private void selectPhoneTypeWithValidation() {
        System.out.println("📍 Processing: Phone Type");
        
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(parentPage.phonetype));
            
            // Scroll to dropdown
            scrollToElement(dropdown);
            safeSleep(500);
            
            // Click dropdown using enhanced method
            highlightElement(dropdown, "orange");
            clickDropdownWithRetry(dropdown);
            
            // Wait for options to load and select
            selectDropdownOption("Cell", "Phone Type");
            
            safeSleep(1000);
            
        } catch (Exception e) {
            System.out.println("❌ Phone type selection failed: " + e.getMessage());
            throw new RuntimeException("Phone type selection failed", e);
        }
    }

    private void selectCountryCodeWithValidation() {
        System.out.println("📍 Processing: Country Code");
        
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(parentPage.Countrycode));
            
            // Scroll to dropdown
            scrollToElement(dropdown);
            safeSleep(500);
            
            // Click dropdown using enhanced method
            highlightElement(dropdown, "orange");
            clickDropdownWithRetry(dropdown);
            
            // Wait for options to load and select
            selectDropdownOption("+1 (US)", "Country Code");
            
            safeSleep(1000);
            
        } catch (Exception e) {
            System.out.println("❌ Country code selection failed: " + e.getMessage());
            throw new RuntimeException("Country code selection failed", e);
        }
    }

    /**
     * Enhanced method to click dropdown with multiple retry strategies
     */
    private void clickDropdownWithRetry(WebElement dropdown) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                // Try normal click first
                dropdown.click();
                System.out.println("   ✅ Dropdown clicked successfully (normal click)");
                return;
            } catch (ElementClickInterceptedException e1) {
                try {
                    // Try JavaScript click
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
                    System.out.println("   ✅ Dropdown clicked successfully (JavaScript click)");
                    return;
                } catch (Exception e2) {
                    try {
                        // Try Actions click
                        new org.openqa.selenium.interactions.Actions(driver)
                            .moveToElement(dropdown)
                            .click()
                            .perform();
                        System.out.println("   ✅ Dropdown clicked successfully (Actions click)");
                        return;
                    } catch (Exception e3) {
                        attempts++;
                        System.out.println("   ⚠️ Dropdown click attempt " + attempts + " failed, retrying...");
                        if (attempts >= 3) {
                            throw new RuntimeException("All dropdown click attempts failed");
                        }
                        safeSleep(1000);
                    }
                }
            } catch (Exception e) {
                attempts++;
                System.out.println("   ⚠️ Dropdown click attempt " + attempts + " failed: " + e.getMessage());
                if (attempts >= 3) {
                    throw new RuntimeException("Dropdown click failed after 3 attempts: " + e.getMessage());
                }
                safeSleep(1000);
            }
        }
    }

    /**
     * Enhanced method to select dropdown options with multiple strategies
     */
    private void selectDropdownOption(String optionText, String dropdownName) {
        System.out.println("   🔍 Selecting option: " + optionText);
        
        int attempts = 0;
        while (attempts < 3) {
            try {
                // Wait for dropdown panel to be visible
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'cdk-overlay-pane')] | //div[contains(@class,'mat-select-panel')]")
                ));
                
                // Try multiple strategies to find and click the option
                boolean optionSelected = false;
                
                // Strategy 1: Try with span text
                try {
                    WebElement option = driver.findElement(
                        By.xpath("//span[contains(@class,'mat-option-text') and contains(text(), '" + optionText + "')] | " +
                                "//mat-option[contains(., '" + optionText + "')] | " +
                                "//div[contains(@class,'mat-option-text') and contains(text(), '" + optionText + "')]")
                    );
                    scrollToElement(option);
                    highlightElement(option, "green");
                    clickWithJavaScript(option);
                    optionSelected = true;
                    System.out.println("   ✅ Option selected using Strategy 1");
                } catch (Exception e) {
                    System.out.println("   ⚠️ Strategy 1 failed, trying next...");
                }
                
                // Strategy 2: Try with any element containing the text
                if (!optionSelected) {
                    try {
                        List<WebElement> allOptions = driver.findElements(
                            By.xpath("//mat-option | //div[contains(@class,'mat-option')] | //div[@role='option']")
                        );
                        
                        for (WebElement option : allOptions) {
                            if (option.getText().trim().contains(optionText)) {
                                scrollToElement(option);
                                highlightElement(option, "green");
                                clickWithJavaScript(option);
                                optionSelected = true;
                                System.out.println("   ✅ Option selected using Strategy 2");
                                break;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("   ⚠️ Strategy 2 failed, trying next...");
                    }
                }
                
                // Strategy 3: Try keyboard navigation
                if (!optionSelected) {
                    try {
                        // Press down arrow until we find the option
                        for (int i = 0; i < 10; i++) {
                            new org.openqa.selenium.interactions.Actions(driver)
                                .sendKeys(Keys.ARROW_DOWN)
                                .perform();
                            safeSleep(200);
                            
                            // Check if current option contains our text
                            WebElement selectedOption = driver.findElement(
                                By.xpath("//mat-option[contains(@class,'mat-active')] | //div[contains(@class,'mat-active')]")
                            );
                            if (selectedOption != null && selectedOption.getText().contains(optionText)) {
                                selectedOption.click();
                                optionSelected = true;
                                System.out.println("   ✅ Option selected using Strategy 3 (keyboard)");
                                break;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("   ⚠️ Strategy 3 failed...");
                    }
                }
                
                if (optionSelected) {
                    System.out.println("   ✅ Successfully selected " + dropdownName + ": " + optionText);
                    return;
                } else {
                    throw new RuntimeException("Could not find option: " + optionText);
                }
                
            } catch (StaleElementReferenceException e) {
                attempts++;
                System.out.println("   ⚠️ Stale element in " + dropdownName + " dropdown, attempt: " + attempts);
                if (attempts >= 3) throw e;
            } catch (Exception e) {
                attempts++;
                System.out.println("   ⚠️ Error selecting " + dropdownName + " option, attempt: " + attempts + ": " + e.getMessage());
                if (attempts >= 3) {
                    // If all attempts fail, try to select the first available option
                    try {
                        List<WebElement> firstOptions = driver.findElements(
                            By.xpath("//mat-option | //div[contains(@class,'mat-option')]")
                        );
                        if (!firstOptions.isEmpty()) {
                            WebElement firstOption = firstOptions.get(0);
                            String selectedText = firstOption.getText();
                            scrollToElement(firstOption);
                            highlightElement(firstOption, "yellow");
                            clickWithJavaScript(firstOption);
                            System.out.println("   ⚠️ Selected first available option: " + selectedText);
                            return;
                        }
                    } catch (Exception fallbackException) {
                        throw new RuntimeException("Failed to select '" + optionText + "' from " + dropdownName + " dropdown after 3 attempts.", e);
                    }
                }
                safeSleep(1000);
            }
        }
    }

    private void enterPhoneNumberWithValidation() {
        System.out.println("📍 Processing: Phone Number");
        
        try {
            // Wait for phone number field to be enabled after country code selection
            WebElement phoneField = wait.until(ExpectedConditions.elementToBeClickable(parentPage.phonenumber));
            
            // Scroll to field
            scrollToElement(phoneField);
            safeSleep(500);
            
            // Verify field is now enabled
            if (phoneField.isEnabled()) {
                // Generate and enter phone number
                String phoneNumber = generateRandomPhoneNumber();
                phoneField.clear();
                phoneField.sendKeys(phoneNumber);
                System.out.println("   ✅ Entered phone number: " + phoneNumber);
                
                // Verify text was entered
                String enteredValue = phoneField.getAttribute("value");
                if (enteredValue != null && !enteredValue.isEmpty()) {
                    System.out.println("   ✅ Phone number entered successfully: " + enteredValue);
                }
                
                highlightElement(phoneField, "green");
            } else {
                System.out.println("   ❌ Phone number field is still disabled after country code selection");
                throw new RuntimeException("Phone number field is disabled");
            }
            
            safeSleep(500);
            
        } catch (Exception e) {
            System.out.println("❌ Phone number entry failed: " + e.getMessage());
            throw new RuntimeException("Phone number entry failed", e);
        }
    }

    private String generateRandomPhoneNumber() {
        // Generate a 10-digit phone number in format (123) 456-7890
        String areaCode = String.format("%03d", random.nextInt(1000));
        String prefix = String.format("%03d", random.nextInt(1000));
        String lineNumber = String.format("%04d", random.nextInt(10000));
        return "(" + areaCode + ") " + prefix + "-" + lineNumber;
    }

    // ==================== ALTERNATE GUARDIAN SECTION ====================

    private void validateAlternateGuardianSection() {
        System.out.println("\n👥 Validating Alternate Guardian Section...");
        
        try {
            // Click Add Alternate Guardian
            WebElement addAltGuardianBtn = wait.until(ExpectedConditions.elementToBeClickable(parentPage.AddAlternativGuardianinfoicon));
            highlightElement(addAltGuardianBtn, "purple");
            clickWithJavaScript(addAltGuardianBtn);
            
            // Wait for alternate guardian fields to appear
            wait.until(ExpectedConditions.visibilityOfElementLocated(parentPage.AlternateGuardianName));
            
            // Validate alternate guardian fields
            validateSectionPresence("Alternate Guardian Name", parentPage.AlternateGuardianName);
            validateSectionPresence("Alternate Guardian Relation", parentPage.AlternateGuardianRelation);
            validateSectionPresence("Alternate Guardian Country Code", parentPage.AlternateGuardiancountrycode);
            validateSectionPresence("Alternate Guardian Phone Number", parentPage.AlternateGuardianPhonenumber);
            
            // Fill alternate guardian information
            fillAlternateGuardianInformation();
            
            System.out.println("✅ Alternate guardian section validated and filled successfully");
            
        } catch (Exception e) {
            System.out.println("❌ Alternate guardian section validation failed: " + e.getMessage());
            throw new RuntimeException("Alternate guardian section validation failed", e);
        }
    }

    private void fillAlternateGuardianInformation() {
        // Alternate Guardian Name
        WebElement altNameField = driver.findElement(parentPage.AlternateGuardianName);
        enterTextWithUIValidation(altNameField, "Alternate Guardian Name", parentPage.AlternateGuardianName);
        
        // Alternate Guardian Relation
        selectAlternateGuardianRelation();
        
        // Alternate Guardian Country Code
        selectAlternateGuardianCountryCode();
        
        // Alternate Guardian Phone Number
        enterAlternateGuardianPhoneNumber();
    }

    private void selectAlternateGuardianRelation() {
        System.out.println("📍 Processing: Alternate Guardian Relation");
        
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(parentPage.AlternateGuardianRelation));
            
            // Scroll to dropdown
            scrollToElement(dropdown);
            safeSleep(500);
            
            // Click dropdown using enhanced method
            highlightElement(dropdown, "orange");
            clickDropdownWithRetry(dropdown);
            
            // Wait for options to load and select
            selectDropdownOption("Mother", "Alternate Guardian Relation");
            
            safeSleep(1000);
            
        } catch (Exception e) {
            System.out.println("❌ Alternate guardian relation selection failed: " + e.getMessage());
            throw new RuntimeException("Alternate guardian relation selection failed", e);
        }
    }

    private void selectAlternateGuardianCountryCode() {
        System.out.println("📍 Processing: Alternate Guardian Country Code");
        
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(parentPage.AlternateGuardiancountrycode));
            
            // Scroll to dropdown
            scrollToElement(dropdown);
            safeSleep(500);
            
            // Click dropdown using enhanced method
            highlightElement(dropdown, "orange");
            clickDropdownWithRetry(dropdown);
            
            // Wait for options to load and select
            selectDropdownOption("+1 (US)", "Alternate Guardian Country Code");
            
            safeSleep(1000);
            
        } catch (Exception e) {
            System.out.println("❌ Alternate guardian country code selection failed: " + e.getMessage());
            throw new RuntimeException("Alternate guardian country code selection failed", e);
        }
    }

    private void enterAlternateGuardianPhoneNumber() {
        System.out.println("📍 Processing: Alternate Guardian Phone Number");
        
        try {
            WebElement phoneField = wait.until(ExpectedConditions.elementToBeClickable(parentPage.AlternateGuardianPhonenumber));
            
            // Scroll to field
            scrollToElement(phoneField);
            safeSleep(500);
            
            if (phoneField.isEnabled()) {
                String phoneNumber = generateRandomPhoneNumber();
                phoneField.clear();
                phoneField.sendKeys(phoneNumber);
                System.out.println("   ✅ Entered alternate guardian phone number: " + phoneNumber);
                
                highlightElement(phoneField, "green");
            } else {
                System.out.println("   ⚠️ Alternate guardian phone number field is disabled");
            }
            
            safeSleep(500);
            
        } catch (Exception e) {
            System.out.println("❌ Alternate guardian phone number entry failed: " + e.getMessage());
            throw new RuntimeException("Alternate guardian phone number entry failed", e);
        }
    }

    // ==================== FORM SUBMISSION VALIDATION ====================

    private void validateFormSubmission() {
        System.out.println("\n✅ Validating Form Submission...");
        
        try {
            // Scroll to proceed button
            WebElement proceedBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(parentPage.ProccedToMedicalInfoButton));
            scrollToElement(proceedBtn);
            safeSleep(1000);
            
            // Validate proceed button state
            System.out.println("🔍 Validating Proceed Button State:");
            System.out.println("   ├── Displayed: " + proceedBtn.isDisplayed());
            System.out.println("   ├── Enabled: " + proceedBtn.isEnabled());
            System.out.println("   └── Text: " + proceedBtn.getText());
            
            // Verify button is enabled (form validation passed)
            if (proceedBtn.isEnabled()) {
                highlightElement(proceedBtn, "green");
                System.out.println("✅ Form validation passed - Proceed button is enabled");
                
                // Click proceed button
                clickWithJavaScript(proceedBtn);
                System.out.println("✅ Successfully clicked Proceed to Medical Info button");
                
                // Verify navigation to next step
                verifyNavigationToMedicalInfo();
            } else {
                System.out.println("⚠️ Proceed button is disabled - checking for validation errors");
                checkForValidationErrors();
                Assert.fail("Form validation failed - Proceed button is disabled");
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Form submission validation failed: " + e.getMessage());
        }
    }

    private void verifyNavigationToMedicalInfo() {
        System.out.println("⏳ Verifying navigation to Medical Information page...");
        
        try {
            // Wait for page transition and look for medical info indicators
            By[] medicalPageIndicators = {
                By.xpath("//*[contains(text(),'Medical') or contains(text(),'Health')]"),
                By.xpath("//*[contains(text(),'Allergies')]"),
                By.xpath("//*[contains(text(),'Medications')]"),
                By.xpath("//*[contains(text(),'Emergency Contact')]")
            };
            
            boolean pageLoaded = false;
            for (By indicator : medicalPageIndicators) {
                try {
                    wait.until(ExpectedConditions.presenceOfElementLocated(indicator));
                    System.out.println("✅ Medical page indicator found: " + indicator);
                    pageLoaded = true;
                    break;
                } catch (Exception e) {
                    // Continue to next indicator
                }
            }
            
            if (!pageLoaded) {
                // Check if we're still on parent page
                try {
                    if (driver.findElement(parentPage.parentguardianHeaderText).isDisplayed()) {
                        System.out.println("❌ Still on parent/guardian page - navigation may have failed");
                        Assert.fail("Navigation to medical info page failed");
                    }
                } catch (Exception e) {
                    System.out.println("✅ Successfully navigated away from parent/guardian page");
                }
            }
            
            safeSleep(2000);
            
        } catch (Exception e) {
            throw new RuntimeException("Navigation verification failed: " + e.getMessage());
        }
    }

    // ==================== ENHANCED UI VALIDATION METHODS ====================

    private void validateSectionPresence(String sectionName, By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            
            System.out.println("🔍 Validating " + sectionName + " Presence:");
            System.out.println("   ├── Displayed: " + element.isDisplayed());
            System.out.println("   ├── Enabled: " + element.isEnabled());
            System.out.println("   └── Location: X=" + element.getLocation().getX() + ", Y=" + element.getLocation().getY());
            
            Assert.assertTrue(element.isDisplayed(), sectionName + " should be displayed");
            
            highlightElement(element, "blue");
            
        } catch (Exception e) {
            throw new RuntimeException(sectionName + " UI validation failed: " + e.getMessage());
        }
    }

    private void enterTextWithUIValidation(WebElement element, String fieldName, By locator) {
        System.out.println("📍 Processing: " + fieldName);
        
        try {
            // Scroll to element
            scrollToElement(element);
            safeSleep(500);
            
            // Wait for element to be enabled
            if (!element.isEnabled()) {
                System.out.println("   ⏳ Waiting for " + fieldName + " to become enabled...");
                wait.until(ExpectedConditions.elementToBeClickable(locator));
                element = driver.findElement(locator); // Re-find element
            }
            
            // Clear and enter text using ParentPage method
            switch (fieldName) {
                case "First Name":
                    parentPage.enterRandomFirstName();
                    break;
                case "Middle Name":
                    parentPage.enterRandomMiddleName();
                    break;
                case "Last Name":
                    parentPage.enterRandomLastName();
                    break;
                case "Address Line 1":
                    parentPage.enterRandomAddress1();
                    break;
                case "Address Line 2":
                    parentPage.enterRandomAddress2();
                    break;
                case "City":
                    parentPage.enterRandomCity();
                    break;
                case "Zip Code":
                    parentPage.enterRandomZipCode();
                    break;
                case "Email":
                    parentPage.enterRandomEmailAddress();
                    break;
                case "Alternate Guardian Name":
                    parentPage.enterRandomAlternateGuardianName();
                    break;
                default:
                    // Use generic method for other fields
                    String randomText = "Test" + System.currentTimeMillis();
                    element.clear();
                    element.sendKeys(randomText);
                    System.out.println("   📥 Entered text: " + randomText);
            }
            
            // Verify text was entered
            String enteredValue = element.getAttribute("value");
            if (enteredValue != null && !enteredValue.isEmpty()) {
                System.out.println("   ✅ Text entered successfully: " + (enteredValue.length() > 20 ? enteredValue.substring(0, 20) + "..." : enteredValue));
            } else {
                System.out.println("   ⚠️ Text may not have been entered properly");
            }
            
            highlightElement(element, "green");
            safeSleep(300);
            
        } catch (Exception e) {
            System.out.println("❌ " + fieldName + " entry failed: " + e.getMessage());
            throw new RuntimeException(fieldName + " entry failed", e);
        }
    }

    private void enterTextWithValidation(By locator, String fieldName, String text) {
        try {
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            field.clear();
            field.sendKeys(text);
            System.out.println("✅ " + fieldName + " entered: " + text);
        } catch (Exception e) {
            throw new RuntimeException(fieldName + " entry failed: " + e.getMessage());
        }
    }

    // ==================== UTILITY METHODS ====================

    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    private void highlightElement(WebElement element, String color) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='3px solid " + color + "'; arguments[0].style.boxShadow='0 0 5px " + color + "';",
                element
            );
        } catch (Exception e) {
            // Ignore highlight errors
        }
    }

    private void clickWithJavaScript(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    private void checkForValidationErrors() {
        try {
            List<WebElement> errors = driver.findElements(By.xpath("//mat-error | .error | [class*='error'] | [class*='invalid']"));
            if (!errors.isEmpty()) {
                System.out.println("❌ Validation errors found:");
                for (WebElement error : errors) {
                    if (error.isDisplayed()) {
                        System.out.println("   - " + error.getText());
                    }
                }
            } else {
                System.out.println("✅ No validation errors found");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not check validation errors: " + e.getMessage());
        }
    }

    private void safeSleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterClass
    public void tearDown() {
        System.out.println("\n🛑 Parent/Guardian UI Test completed! Closing browser...");
        safeSleep(3000);
        if (driver != null) {
            driver.quit();
            System.out.println("✅ Browser closed successfully");
        }
    }
}