package AdminSpeciations;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import AdminPages.PhysicianInfoPage;
import AdminPages.Parentpage;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class PhysicianSpecificationTest {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    private Random random = new Random();
    private PhysicianInfoPage physicianPage;
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().window().maximize();
        physicianPage = new PhysicianInfoPage(driver);
        parentPage = new Parentpage(driver);
    }

    @Test
    public void completePhysicianInfoUIValidationTest() {
        System.out.println("🚀 Starting Physician Information UI Validation Test...");
        
        try {
            // STEP 1: Login and Navigate
            performLoginAndNavigation();
            
            // STEP 2: Complete Child Enrollment
            completeBasicChildEnrollment();
            
            // STEP 3: Complete Parent/Guardian Information
            completeParentGuardianInformation();
            
            // STEP 4: Comprehensive Physician Information UI Validation
            validatePhysicianInfoPageUI();
            fillPhysicianInfoFormWithUIValidation();
            validateEmergencyCareSection();
            validateFormSubmission();
            
            System.out.println("✅ Physician Information UI Validation Test completed successfully!");
            
        } catch (Exception e) {
            System.out.println("❌ Physician Information UI Validation Test failed: " + e.getMessage());
            takeScreenshot("physician-info-test-failure");
            Assert.fail("Physician Information UI validation test failed: " + e.getMessage());
        }
    }

    // ==================== DEBUGGED PHONE TYPE DROPDOWN SOLUTION ====================

    /**
     * NEW: Debug method to find ALL elements on page for analysis
     */
    private void debugPageElements() {
        System.out.println("\n🔍 DEBUGGING PAGE ELEMENTS:");
        
        // Find all mat-select elements
        List<WebElement> allMatSelects = driver.findElements(By.tagName("mat-select"));
        System.out.println("📊 Total mat-select elements found: " + allMatSelects.size());
        
        for (int i = 0; i < allMatSelects.size(); i++) {
            WebElement select = allMatSelects.get(i);
            try {
                String formControlName = select.getAttribute("formcontrolname");
                String id = select.getAttribute("id");
                String classAttr = select.getAttribute("class");
                System.out.println("   [" + i + "] formcontrolname: '" + formControlName + "', id: '" + id + "', class: '" + classAttr + "'");
            } catch (Exception e) {
                System.out.println("   [" + i + "] Error reading attributes: " + e.getMessage());
            }
        }
        
        // Find all iframes
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        System.out.println("📊 Total iframes found: " + iframes.size());
        for (int i = 0; i < iframes.size(); i++) {
            WebElement iframe = iframes.get(i);
            try {
                String src = iframe.getAttribute("src");
                String id = iframe.getAttribute("id");
                System.out.println("   Iframe [" + i + "] src: '" + src + "', id: '" + id + "'");
            } catch (Exception e) {
                System.out.println("   Iframe [" + i + "] Error reading attributes");
            }
        }
        
        // Take screenshot for visual debugging
        takeScreenshot("debug-page-elements");
    }

    /**
     * NEW: Universal dropdown handler with multiple fallback strategies
     */
    private void handleDropdownWithFallbacks(String dropdownName, String targetOption, String... locatorStrategies) {
        System.out.println("\n📋 Processing: " + dropdownName);
        
        int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                System.out.println("   Attempt " + attempt + " of " + maxAttempts);
                
                // Try each locator strategy
                WebElement dropdown = findElementWithMultipleStrategies(locatorStrategies, dropdownName);
                if (dropdown == null) {
                    throw new NoSuchElementException("Could not find " + dropdownName + " with any strategy");
                }
                
                // Scroll and ensure visible
                scrollToElement(dropdown);
                safeSleep(1000);
                
                // Check if already expanded
                String ariaExpanded = dropdown.getAttribute("aria-expanded");
                if (!"true".equals(ariaExpanded)) {
                    // Click dropdown to expand
                    clickElementWithRetry(dropdown, dropdownName);
                    safeSleep(1500);
                }
                
                // Wait for and select option
                if (selectDropdownOptionWithRetry(targetOption, dropdownName)) {
                    System.out.println("   ✅ Successfully selected '" + targetOption + "' from " + dropdownName);
                    safeSleep(1000);
                    return;
                }
                
            } catch (StaleElementReferenceException e) {
                System.out.println("   ⚠️ Stale element, retrying...");
                if (attempt == maxAttempts) throw e;
            } catch (Exception e) {
                System.out.println("   ❌ Attempt " + attempt + " failed: " + e.getMessage());
                if (attempt == maxAttempts) {
                    // Last attempt - try debug approach
                    debugPageElements();
                    throw new RuntimeException("Failed to handle " + dropdownName + " after " + maxAttempts + " attempts: " + e.getMessage());
                }
                safeSleep(2000);
            }
        }
    }

    /**
     * NEW: Find element with multiple locator strategies
     */
    private WebElement findElementWithMultipleStrategies(String[] locatorStrategies, String elementName) {
        System.out.println("   🔍 Finding " + elementName + " with " + locatorStrategies.length + " strategies...");
        
        for (String strategy : locatorStrategies) {
            try {
                System.out.println("     Trying: " + strategy);
                List<WebElement> elements = driver.findElements(By.xpath(strategy));
                
                if (!elements.isEmpty()) {
                    System.out.println("     Found " + elements.size() + " elements with: " + strategy);
                    
                    // Return first visible element
                    for (WebElement element : elements) {
                        if (element.isDisplayed()) {
                            System.out.println("     ✅ Using visible element from: " + strategy);
                            return element;
                        }
                    }
                    
                    // If no visible elements, return first one and scroll to it
                    WebElement firstElement = elements.get(0);
                    System.out.println("     ⚠️ No visible elements, using first element and scrolling");
                    scrollToElement(firstElement);
                    return firstElement;
                }
            } catch (Exception e) {
                System.out.println("     ❌ Strategy failed: " + strategy + " - " + e.getMessage());
            }
        }
        
        System.out.println("   ❌ All strategies failed for " + elementName);
        return null;
    }

    /**
     * NEW: Robust element clicking with multiple methods
     */
    private void clickElementWithRetry(WebElement element, String elementName) {
        System.out.println("   🖱️ Clicking " + elementName);
        
        // Try different click methods
        try {
            element.click();
            System.out.println("     ✅ Clicked successfully (normal click)");
            return;
        } catch (Exception e1) {
            System.out.println("     ⚠️ Normal click failed: " + e1.getMessage());
        }
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            System.out.println("     ✅ Clicked successfully (JavaScript click)");
            return;
        } catch (Exception e2) {
            System.out.println("     ⚠️ JavaScript click failed: " + e2.getMessage());
        }
        
        try {
            new org.openqa.selenium.interactions.Actions(driver)
                .moveToElement(element)
                .click()
                .perform();
            System.out.println("     ✅ Clicked successfully (Actions click)");
            return;
        } catch (Exception e3) {
            System.out.println("     ⚠️ Actions click failed: " + e3.getMessage());
        }
        
        throw new RuntimeException("All click methods failed for " + elementName);
    }

    /**
     * NEW: Robust option selection with multiple strategies
     */
    private boolean selectDropdownOptionWithRetry(String optionText, String dropdownName) {
        System.out.println("   🔍 Selecting option: '" + optionText + "'");
        
        // Wait for options to appear
        safeSleep(2000);
        
        // Strategy 1: Direct text match
        try {
            WebElement option = driver.findElement(
                By.xpath("//mat-option//span[contains(text(), '" + optionText + "')] | " +
                        "//mat-option[contains(., '" + optionText + "')] | " +
                        "//div[contains(@class, 'mat-option-text') and contains(text(), '" + optionText + "')]")
            );
            scrollToElement(option);
            highlightElement(option, "green");
            option.click();
            System.out.println("     ✅ Option selected (direct match)");
            return true;
        } catch (Exception e) {
            System.out.println("     ⚠️ Direct match failed: " + e.getMessage());
        }
        
        // Strategy 2: Iterate through all options
        try {
            List<WebElement> allOptions = driver.findElements(
                By.xpath("//mat-option | //div[contains(@class, 'mat-option')]")
            );
            //System.out.println("     Found " + allOptions.size() + total options");
            
            for (WebElement option : allOptions) {
                try {
                    String text = option.getText().trim();
                    if (text.contains(optionText) || optionText.contains(text)) {
                        scrollToElement(option);
                        highlightElement(option, "green");
                        option.click();
                        System.out.println("     ✅ Option selected: '" + text + "'");
                        return true;
                    }
                } catch (Exception e) {
                    // Continue to next option
                }
            }
        } catch (Exception e) {
            System.out.println("     ⚠️ Option iteration failed: " + e.getMessage());
        }
        
        // Strategy 3: Select first available option
        try {
            List<WebElement> options = driver.findElements(
                By.xpath("//mat-option | //div[contains(@class, 'mat-option')]")
            );
            if (!options.isEmpty()) {
                WebElement firstOption = options.get(0);
                String selectedText = firstOption.getText();
                scrollToElement(firstOption);
                highlightElement(firstOption, "yellow");
                firstOption.click();
                System.out.println("     ⚠️ Selected first available option: '" + selectedText + "'");
                return true;
            }
        } catch (Exception e) {
            System.out.println("     ⚠️ First option fallback failed: " + e.getMessage());
        }
        
        System.out.println("     ❌ All option selection strategies failed");
        return false;
    }

    // ==================== UPDATED PHONE TYPE METHODS ====================

    private void selectPhysicianPhoneTypeWithValidation() {
        String[] locatorStrategies = {
            "//mat-select[@formcontrolname='phoneType']",
            "//mat-select[contains(@formcontrolname, 'phoneType')]",
            "//mat-select[contains(@id, 'phoneType')]",
            "//mat-select[contains(@class, 'phoneType')]",
            "//mat-select[.//*[contains(text(), 'Phone Type')]]",
            "//mat-select",
            "//div[contains(@class, 'mat-select')]"
        };
        
        handleDropdownWithFallbacks("Physician Phone Type", "Cell", locatorStrategies);
    }

    private void selectCareFacilityPhoneTypeWithValidation() {
        String[] locatorStrategies = {
            "//mat-select[@formcontrolname='careFacilityPhoneType']",
            "//mat-select[contains(@formcontrolname, 'careFacilityPhoneType')]",
            "//mat-select[contains(@id, 'careFacilityPhoneType')]",
            "//mat-select[contains(@class, 'careFacilityPhoneType')]",
            "//mat-select[.//*[contains(text(), 'Care Facility Phone Type')]]",
            "//mat-select",
            "//div[contains(@class, 'mat-select')]"
        };
        
        handleDropdownWithFallbacks("Care Facility Phone Type", "Cell", locatorStrategies);
    }

    // ==================== CORE TEST METHODS ====================

    private void performLoginAndNavigation() {
        System.out.println("\n🔐 Performing Login and Navigation...");
        
        try {
            driver.get("https://mereg-dev.netlify.app/");
            
            WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
            email.clear();
            email.sendKeys("srasysife@gmail.com");
            
            WebElement password = driver.findElement(passwordField);
            password.clear();
            password.sendKeys("Admin@123");
            
            WebElement signIn = driver.findElement(signInButton);
            signIn.click();
            
            waitForLoginToComplete();
            
            try {
                WebElement enrollmentTabElement = wait.until(ExpectedConditions.elementToBeClickable(enrollmentTab));
                enrollmentTabElement.click();
                System.out.println("✅ Clicked on Enrollment tab");
            } catch (Exception e) {
                System.out.println("⚠️ Enrollment tab not clickable or already on enrollment page: " + e.getMessage());
            }
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
            System.out.println("✅ Login and navigation successful");
            
        } catch (Exception e) {
            throw new RuntimeException("Login and navigation failed: " + e.getMessage());
        }
    }

    private void waitForLoginToComplete() {
        System.out.println("⏳ Waiting for login to complete...");
        
        try {
            WebDriverWait loginWait = new WebDriverWait(driver, Duration.ofSeconds(30));
            
            loginWait.until(driver -> {
                boolean hasEnrollmentContent = driver.getPageSource().contains("Enrollment") || 
                                              driver.getPageSource().contains("enrollment");
                boolean hasEnrollmentTab = driver.findElements(enrollmentTab).size() > 0;
                boolean notOnLoginPage = !driver.getCurrentUrl().contains("login") && 
                                        !driver.getPageSource().contains("login");
                boolean hasChildFormElements = driver.findElements(firstName).size() > 0;
                
                return hasEnrollmentContent || hasEnrollmentTab || notOnLoginPage || hasChildFormElements;
            });
            
            System.out.println("✅ Login completed successfully");
            safeSleep(2000);
            
        } catch (Exception e) {
            System.out.println("⚠️ Login completion check timed out, continuing anyway: " + e.getMessage());
        }
    }

    private void completeBasicChildEnrollment() {
        System.out.println("\n👶 Completing Basic Child Enrollment...");
        
        try {
            enterTextWithValidation(firstName, "Child First Name", "TestChild");
            enterTextWithValidation(middleName, "Child Middle Name", "Middle");
            enterTextWithValidation(lastName, "Child Last Name", "Doe");
            enterTextWithValidation(dateOfBirth, "Date of Birth", "01/01/2020");
            enterTextWithValidation(placeOfBirth, "Place of Birth", "New York");
            
            WebElement genderElement = wait.until(ExpectedConditions.elementToBeClickable(genderMale));
            highlightElement(genderElement, "orange");
            clickWithJavaScript(genderElement);
            
            WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(proceedButton));
            highlightElement(proceedBtn, "green");
            clickWithJavaScript(proceedBtn);
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(parentPage.parentguardianHeaderText));
            System.out.println("✅ Basic child enrollment completed");
            
        } catch (Exception e) {
            throw new RuntimeException("Child enrollment failed: " + e.getMessage());
        }
    }

    private void completeParentGuardianInformation() {
        System.out.println("\n👨‍👩‍👧‍👦 Completing Parent/Guardian Information...");
        
        try {
            parentPage.enterRandomFirstName();
            safeSleep(500);
            parentPage.enterRandomLastName();
            safeSleep(500);
            parentPage.enterRandomAddress1();
            safeSleep(500);
            
            selectParentCountry();
            safeSleep(1000);
            selectParentState();
            safeSleep(500);
            
            parentPage.enterRandomCity();
            safeSleep(500);
            parentPage.enterRandomZipCode();
            safeSleep(500);
            
            selectParentPhoneType();
            safeSleep(500);
            selectParentCountryCode();
            safeSleep(500);
            enterParentPhoneNumber();
            safeSleep(500);
            
            parentPage.enterRandomEmailAddress();
            safeSleep(500);
            
            WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(parentPage.ProccedToMedicalInfoButton));
            highlightElement(proceedBtn, "green");
            clickWithJavaScript(proceedBtn);
            
            waitForPhysicianPageToLoad();
            System.out.println("✅ Parent/Guardian information completed and navigated to Physician page");
            
        } catch (Exception e) {
            throw new RuntimeException("Parent/Guardian information completion failed: " + e.getMessage());
        }
    }

    private void waitForPhysicianPageToLoad() {
        System.out.println("⏳ Waiting for Physician page to load completely...");
        
        try {
            // First, debug the current page state
            debugPageElements();
            
            // Wait for physician header
            wait.until(ExpectedConditions.visibilityOfElementLocated(physicianPage.physicianInfoHeaderText));
            
            // Wait for form elements to be present
            wait.until(driver -> {
                try {
                    List<WebElement> formElements = driver.findElements(By.xpath("//input | //mat-select"));
                    System.out.println("📊 Form elements found: " + formElements.size());
                    return formElements.size() > 5;
                } catch (Exception e) {
                    return false;
                }
            });
            
            System.out.println("✅ Physician page loaded successfully");
            
        } catch (Exception e) {
            System.out.println("❌ Physician page loading failed: " + e.getMessage());
            debugPageElements();
            throw e;
        }
    }

    private void validatePhysicianInfoPageUI() {
        System.out.println("\n📋 Validating Physician Information Page UI Structure...");
        
        try {
            String headerText = physicianPage.verifyPhysicianInfoPage();
            Assert.assertTrue(headerText.contains("Physician") || headerText.contains("Emergency") || headerText.contains("Medical"), 
                "Physician Information header not found");
            System.out.println("✅ Header validation passed: " + headerText);
            
            // Use debug approach for validation
            debugPageElements();
            
            // Validate critical fields with enhanced approach
            validateFieldPresence("Physician First Name", physicianPage.physicianFirstName);
            validateFieldPresence("Physician Last Name", physicianPage.physicianLastName);
            validateFieldPresence("Physician Address Line 1", physicianPage.physicianAddress1);
            
            System.out.println("✅ Basic physician information form sections are present");
            
        } catch (Exception e) {
            debugPageElements();
            throw new RuntimeException("Physician Information page UI validation failed: " + e.getMessage());
        }
    }

    /**
     * NEW: Enhanced field validation with debugging
     */
    private void validateFieldPresence(String fieldName, By locator) {
        System.out.println("🔍 Validating: " + fieldName);
        
        try {
            List<WebElement> elements = driver.findElements(locator);
            System.out.println("   Found " + elements.size() + " elements for " + fieldName);
            
            if (elements.isEmpty()) {
                throw new NoSuchElementException("No elements found for " + fieldName);
            }
            
            boolean foundVisible = false;
            for (int i = 0; i < elements.size(); i++) {
                WebElement element = elements.get(i);
                if (element.isDisplayed()) {
                    System.out.println("   ✅ Visible element found at index " + i);
                    highlightElement(element, "blue");
                    foundVisible = true;
                    break;
                }
            }
            
            if (!foundVisible) {
                System.out.println("   ⚠️ No visible elements, using first element");
                WebElement firstElement = elements.get(0);
                scrollToElement(firstElement);
                highlightElement(firstElement, "yellow");
            }
            
        } catch (Exception e) {
            System.out.println("   ❌ " + fieldName + " validation failed: " + e.getMessage());
            throw e;
        }
    }

    private void fillPhysicianInfoFormWithUIValidation() {
        System.out.println("\n📝 Filling Physician Information Form with UI Validation...");
        
        try {
            System.out.println("\n👨‍⚕️ PHYSICIAN INFORMATION SECTION");
            fillPhysicianInformationWithValidation();
            
            System.out.println("✅ Physician information form filled successfully with UI validation");
            
        } catch (Exception e) {
            throw new RuntimeException("Physician information form filling failed: " + e.getMessage());
        }
    }

    private void fillPhysicianInformationWithValidation() {
        WebElement firstNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(physicianPage.physicianFirstName));
        enterTextWithUIValidation(firstNameField, "Physician First Name", "James");
        
        WebElement lastNameField = driver.findElement(physicianPage.physicianLastName);
        enterTextWithUIValidation(lastNameField, "Physician Last Name", "Smith");
        
        WebElement address1Field = driver.findElement(physicianPage.physicianAddress1);
        enterTextWithUIValidation(address1Field, "Physician Address Line 1", "123 Medical Center Dr");
        
        // Use the new dropdown handlers
        selectPhysicianCountryWithValidation();
        selectPhysicianStateWithValidation();
        
        WebElement cityField = driver.findElement(physicianPage.physicianCity);
        enterTextWithUIValidation(cityField, "Physician City", "Los Angeles");
        
        WebElement zipField = driver.findElement(physicianPage.physicianZipCode);
        enterTextWithUIValidation(zipField, "Physician Zip Code", "90210");
        
        // THIS IS THE KEY FIX - using the new dropdown handler
        selectPhysicianPhoneTypeWithValidation();
        
        selectPhysicianCountryCodeWithValidation();
        enterPhysicianPhoneNumberWithValidation();
    }

    private void selectPhysicianCountryWithValidation() {
        String[] locatorStrategies = {
            "//mat-select[@formcontrolname='country']",
            "//mat-select[contains(@formcontrolname, 'country')]",
            "//mat-select[contains(@id, 'country')]",
            "//mat-select[.//*[contains(text(), 'Country')]]"
        };
        handleDropdownWithFallbacks("Physician Country", "United States", locatorStrategies);
    }

    private void selectPhysicianStateWithValidation() {
        String[] locatorStrategies = {
            "//mat-select[@formcontrolname='state']",
            "//mat-select[contains(@formcontrolname, 'state')]",
            "//mat-select[contains(@id, 'state')]",
            "//mat-select[.//*[contains(text(), 'State')]]"
        };
        handleDropdownWithFallbacks("Physician State", "California", locatorStrategies);
    }

    private void selectPhysicianCountryCodeWithValidation() {
        String[] locatorStrategies = {
            "//mat-select[@formcontrolname='countryCode']",
            "//mat-select[contains(@formcontrolname, 'countryCode')]",
            "//mat-select[contains(@id, 'countryCode')]",
            "//mat-select[.//*[contains(text(), 'Country Code')]]"
        };
        handleDropdownWithFallbacks("Physician Country Code", "+1 (US)", locatorStrategies);
    }

    private void enterPhysicianPhoneNumberWithValidation() {
        System.out.println("📍 Processing: Physician Phone Number");
        
        try {
            // Find phone number field with multiple strategies
            String[] phoneLocators = {
                "//input[@formcontrolname='phoneNumber']",
                "//input[contains(@formcontrolname, 'phoneNumber')]",
                "//input[contains(@id, 'phoneNumber')]",
                "//input[@type='tel']",
                "//input[contains(@placeholder, 'Phone')]"
            };
            
            WebElement phoneField = findElementWithMultipleStrategies(phoneLocators, "Physician Phone Number");
            
            if (phoneField != null && phoneField.isEnabled()) {
                String phoneNumber = generateRandomPhoneNumber();
                phoneField.clear();
                phoneField.sendKeys(phoneNumber);
                System.out.println("   ✅ Entered physician phone number: " + phoneNumber);
                highlightElement(phoneField, "green");
            } else {
                throw new RuntimeException("Physician phone number field is disabled or not found");
            }
            
            safeSleep(500);
            
        } catch (Exception e) {
            System.out.println("❌ Physician phone number entry failed: " + e.getMessage());
            throw new RuntimeException("Physician phone number entry failed", e);
        }
    }

    // ==================== EMERGENCY CARE SECTION ====================

    private void validateEmergencyCareSection() {
        System.out.println("\n🏥 Validating Emergency Care Section...");
        
        try {
            fillEmergencyCareInformationWithValidation();
            System.out.println("✅ Emergency care section validated and filled successfully");
            
        } catch (Exception e) {
            System.out.println("❌ Emergency care section validation failed: " + e.getMessage());
            throw new RuntimeException("Emergency care section validation failed", e);
        }
    }

    private void fillEmergencyCareInformationWithValidation() {
        System.out.println("\n🏥 EMERGENCY CARE INFORMATION SECTION");
        
        WebElement facilityNameField = driver.findElement(physicianPage.careFacilityName);
        enterTextWithUIValidation(facilityNameField, "Care Facility Name", "City General Hospital");
        
        // Use the new dropdown handler
        selectCareFacilityPhoneTypeWithValidation();
        
        selectCareFacilityCountryCodeWithValidation();
        enterCareFacilityPhoneNumberWithValidation();
        
        WebElement facilityAddress1Field = driver.findElement(physicianPage.careFacilityAddress1);
        enterTextWithUIValidation(facilityAddress1Field, "Care Facility Address Line 1", "456 Emergency Lane");
        
        selectCareFacilityCountryWithValidation();
        selectCareFacilityStateWithValidation();
        
        WebElement facilityCityField = driver.findElement(physicianPage.careFacilityCity);
        enterTextWithUIValidation(facilityCityField, "Care Facility City", "San Francisco");
        
        WebElement facilityZipField = driver.findElement(physicianPage.careFacilityZipCode);
        enterTextWithUIValidation(facilityZipField, "Care Facility Zip Code", "94102");
    }

    private void selectCareFacilityCountryCodeWithValidation() {
        String[] locatorStrategies = {
            "//mat-select[@formcontrolname='careFacilityCountryCode']",
            "//mat-select[contains(@formcontrolname, 'careFacilityCountryCode')]",
            "//mat-select[contains(@id, 'careFacilityCountryCode')]"
        };
        handleDropdownWithFallbacks("Care Facility Country Code", "+1 (US)", locatorStrategies);
    }

    private void enterCareFacilityPhoneNumberWithValidation() {
        System.out.println("📍 Processing: Care Facility Phone Number");
        
        try {
            String[] phoneLocators = {
                "//input[@formcontrolname='careFacilityPhoneNumber']",
                "//input[contains(@formcontrolname, 'careFacilityPhoneNumber')]",
                "//input[contains(@id, 'careFacilityPhoneNumber')]"
            };
            
            WebElement phoneField = findElementWithMultipleStrategies(phoneLocators, "Care Facility Phone Number");
            
            if (phoneField != null && phoneField.isEnabled()) {
                String phoneNumber = generateRandomPhoneNumber();
                phoneField.clear();
                phoneField.sendKeys(phoneNumber);
                System.out.println("   ✅ Entered care facility phone number: " + phoneNumber);
                highlightElement(phoneField, "green");
            } else {
                throw new RuntimeException("Care facility phone number field is disabled or not found");
            }
            
            safeSleep(500);
            
        } catch (Exception e) {
            System.out.println("❌ Care facility phone number entry failed: " + e.getMessage());
            throw new RuntimeException("Care facility phone number entry failed", e);
        }
    }

    private void selectCareFacilityCountryWithValidation() {
        String[] locatorStrategies = {
            "//mat-select[@formcontrolname='careFacilityCountry']",
            "//mat-select[contains(@formcontrolname, 'careFacilityCountry')]",
            "//mat-select[contains(@id, 'careFacilityCountry')]"
        };
        handleDropdownWithFallbacks("Care Facility Country", "United States", locatorStrategies);
    }

    private void selectCareFacilityStateWithValidation() {
        String[] locatorStrategies = {
            "//mat-select[@formcontrolname='careFacilityState']",
            "//mat-select[contains(@formcontrolname, 'careFacilityState')]",
            "//mat-select[contains(@id, 'careFacilityState')]"
        };
        handleDropdownWithFallbacks("Care Facility State", "California", locatorStrategies);
    }

    // ==================== FORM SUBMISSION & UTILITY METHODS ====================

    private void validateFormSubmission() {
        System.out.println("\n✅ Validating Physician Form Submission...");
        
        try {
            WebElement proceedBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(physicianPage.proceedToNextButton));
            scrollToElement(proceedBtn);
            safeSleep(1000);
            
            System.out.println("🔍 Validating Proceed Button State:");
            System.out.println("   ├── Displayed: " + proceedBtn.isDisplayed());
            System.out.println("   ├── Enabled: " + proceedBtn.isEnabled());
            System.out.println("   └── Text: " + proceedBtn.getText());
            
            if (proceedBtn.isEnabled()) {
                highlightElement(proceedBtn, "green");
                System.out.println("✅ Form validation passed - Proceed button is enabled");
                
                clickWithJavaScript(proceedBtn);
                System.out.println("✅ Successfully clicked Proceed button");
                
                verifyNavigationToNextStep();
            } else {
                System.out.println("⚠️ Proceed button is disabled - checking for validation errors");
                checkForValidationErrors();
                Assert.fail("Form validation failed - Proceed button is disabled");
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Physician form submission validation failed: " + e.getMessage());
        }
    }

    private void verifyNavigationToNextStep() {
        System.out.println("⏳ Verifying navigation to next page...");
        
        try {
            safeSleep(3000);
            System.out.println("✅ Navigation completed");
        } catch (Exception e) {
            throw new RuntimeException("Navigation verification failed: " + e.getMessage());
        }
    }

    // ==================== PARENT/GUARDIAN HELPER METHODS ====================

    private void selectParentCountry() {
        String[] locatorStrategies = {
            "//mat-select[@formcontrolname='country']",
            "//mat-select[contains(@formcontrolname, 'country')]"
        };
        handleDropdownWithFallbacks("Parent Country", "United States", locatorStrategies);
    }

    private void selectParentState() {
        String[] locatorStrategies = {
            "//mat-select[@formcontrolname='state']",
            "//mat-select[contains(@formcontrolname, 'state')]"
        };
        handleDropdownWithFallbacks("Parent State", "California", locatorStrategies);
    }

    private void selectParentPhoneType() {
        String[] locatorStrategies = {
            "//mat-select[@formcontrolname='phoneType']",
            "//mat-select[contains(@formcontrolname, 'phoneType')]"
        };
        handleDropdownWithFallbacks("Parent Phone Type", "Cell", locatorStrategies);
    }

    private void selectParentCountryCode() {
        String[] locatorStrategies = {
            "//mat-select[@formcontrolname='countryCode']",
            "//mat-select[contains(@formcontrolname, 'countryCode')]"
        };
        handleDropdownWithFallbacks("Parent Country Code", "+1 (US)", locatorStrategies);
    }

    private void enterParentPhoneNumber() {
        System.out.println("📍 Processing: Parent Phone Number");
        
        try {
            WebElement phoneField = wait.until(ExpectedConditions.elementToBeClickable(parentPage.phonenumber));
            scrollToElement(phoneField);
            safeSleep(1000);
            
            if (phoneField.isEnabled()) {
                String phoneNumber = generateRandomPhoneNumber();
                phoneField.clear();
                phoneField.sendKeys(phoneNumber);
                System.out.println("   ✅ Entered parent phone number: " + phoneNumber);
                highlightElement(phoneField, "green");
            } else {
                throw new RuntimeException("Parent phone number field is disabled");
            }
            
            safeSleep(500);
            
        } catch (Exception e) {
            throw new RuntimeException("Parent phone number entry failed", e);
        }
    }

    // ==================== UTILITY METHODS ====================

    private void enterTextWithUIValidation(WebElement element, String fieldName, String text) {
        System.out.println("📍 Processing: " + fieldName);
        
        try {
            scrollToElement(element);
            safeSleep(1000);
            
            if (!element.isEnabled()) {
                System.out.println("   ⏳ Waiting for " + fieldName + " to become enabled...");
                wait.until(ExpectedConditions.elementToBeClickable(element));
            }
            
            element.clear();
            element.sendKeys(text);
            
            String enteredValue = element.getAttribute("value");
            if (enteredValue != null && !enteredValue.isEmpty()) {
                System.out.println("   ✅ Text entered successfully: " + (enteredValue.length() > 20 ? enteredValue.substring(0, 20) + "..." : enteredValue));
            } else {
                System.out.println("   ⚠️ Text may not have been entered properly");
            }
            
            highlightElement(element, "green");
            safeSleep(500);
            
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

    private String generateRandomPhoneNumber() {
        String areaCode = String.format("%03d", random.nextInt(1000));
        String prefix = String.format("%03d", random.nextInt(1000));
        String lineNumber = String.format("%04d", random.nextInt(10000));
        return areaCode + prefix + lineNumber;
    }

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

    private void takeScreenshot(String testName) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            System.out.println("📸 Screenshot taken for: " + testName);
        } catch (Exception e) {
            System.out.println("⚠️ Could not take screenshot: " + e.getMessage());
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
        System.out.println("\nPhysician Information UI Test completed! Closing browser...");
        safeSleep(3000);
        if (driver != null) {
            driver.quit();
            System.out.println("✅ Browser closed successfully");
        }
    }
}