package AdminPages;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IndidualChildPhyscianPage {
    WebDriver driver;
    private String selectedCountry;
    private String selectedState;
    private Random random = new Random();

    // Locators
    public By physicianInfoHeaderText = By.xpath("//app-student-registration-home-page//app-emergency-info/h2");
    public By physicianFirstName = By.xpath("(//input[@name='firstName'])[3]");
    public By physicianMiddleName = By.xpath("(//input[@name='middleName'])[3]");
    public By physicianLastName = By.xpath("(//input[@name='lastName'])[3]");
    public By physicianAddress1 = By.xpath("(//input[@name='addressLine1'])[2]");
    public By physicianAddress2 = By.xpath("(//input[@formcontrolname='addressLine2'])[2]");
    //public By physicianCountryDropdown = By.xpath("(//mat-select[@name='country'])[2]");
    ////div[@class="mat-form-field-wrapper ng-tns-c142-53"]
    public By physicianCountryDropdown = By.xpath("(//mat-select[@formcontrolname='country'])[2]");
    public By physicianStateDropdown = By.xpath("(//mat-select[@formcontrolname='state'])[2]");
    public By physicianCity = By.xpath("(//input[@name='city'])[2]");
    public By physicianZipCode = By.xpath("(//input[@name='zipCode'])[2]");

    // This locator is correct for the Physician Phone Type dropdown itself
    public By physicianPhoneTypeDropdown = By.xpath("(//mat-select[@name='phoneType'])[3]");
    public By physicianCountryCodeDropdown = By.xpath("(//mat-select[@formcontrolname='countryCode'])[3]");
    public By physicianPhoneNumber = By.xpath("(//input[@name='phoneNumber'])[3]");

    public By alternatePhoneTypeDropdown = By.xpath("(//mat-select[@formcontrolname='alternatePhoneType'])[2]");
    public By alternateCountryCodeDropdown = By.xpath("(//mat-select[@formcontrolname='alternateCountryCode'])[2]");
    public By alternatePhoneNumber = By.xpath("(//input[@formcontrolname='alternatePhoneNumber'])[2]");

    // Emergency Care Information Section
    public By careFacilityName = By.xpath("//input[@formcontrolname='careFacilityName']");
    public By careFacilityPhoneTypeDropdown = By.xpath("(//mat-select[@name='phoneType'])[4]");
    public By careFacilityCountryCodeDropdown = By.xpath("//mat-select[@formcontrolname='careFacilityCountryCode']");
    public By careFacilityPhoneNumber = By.xpath("//input[@formcontrolname='careFacilityPhoneNumber']");
    public By careFacilityAddress1 = By.xpath("//input[@name='careFacilityAddressLine1']");
    public By careFacilityAddress2 = By.xpath("//input[@name='careFacilityAddressLine2']");
    public By careFacilityCountryDropdown = By.xpath("//mat-select[@name='careFacilityCountry']");
    public By careFacilityStateDropdown = By.xpath("//mat-select[@name='careFacilityState']");
    public By careFacilityCity = By.xpath("//input[@name='careFacilityCity']");
    public By careFacilityZipCode = By.xpath("//input[@name='careFacilityZipCode']");

    // Common locators for dropdown options
    // This locator is crucial for finding the list of options
    public By dropdownOptions = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
    
    // Button locators
    public By proceedToNextButton = By.xpath("(//mat-icon[text()='arrow_forward'])[3]");
    public By usePhysicianForAllCheckbox = By.xpath("//mat-checkbox[contains(., 'Use the selected physician')]");
    public By backButton = By.xpath("//button[contains(., 'Back to Parent/Guardian Info') or contains(., 'Back')]");

    // Test data arrays
    private String[] firstNames = {"James", "Martinez", "John", "Patricia", "Robert", "Jennifer", "Michael", "Linda",
                                   "William", "Elizabeth", "David", "Susan", "Richard", "Jessica", "Joseph", "Sarah"};
    
    private String[] middleNames = {"James", "BMKmm", "David", "Susan", "Martinez", "josph", "Jones", "Joseph", "Joseph", "krishna", "venu", "renu", "Meena",
            "james", "Martinez", "Susan", "Richard", "Jones", "Garcia", "ravi", "uma", "vena", "nani", "rani", "raja", "taha"};
    
    private String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
                                 "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson"};
    
    private String[] address1Options = {"123 Main St", "456 Oak Ave", "789 Pine Rd", "321 Elm St", "654 Maple Ave",
                                       "987 Cedar Ln", "111 Birch Dr", "222 Spruce Way", "333 Willow Rd", "444 Aspen Ct"};
    
    private String[] address2Options = {"Apt 1", "Apt 2B", "Unit 3", "Suite 100", "Floor 2", "Building A",
                                       "Apartment 5C", "Unit 7D", "Suite 200", "Floor 3"};
    
    private String[] cityNames = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia",
                                 "San Antonio", "San Diego", "Dallas", "San Jose", "Austin", "Jacksonville",
                                 "Fort Worth", "Columbus", "Charlotte", "Indianapolis", "Seattle", "Denver",
                                 "Washington", "Boston", "El Paso", "Nashville", "Detroit", "Oklahoma City"};
    
    private String[] phoneTypes = {"Cell", "Work", "Other"};
    private String[] careFacilityNames = {"City Hospital", "Community Medical Center", "Regional Health", "General Hospital",
                                         "Children's Clinic", "Family Practice", "Urgent Care", "Specialty Center"};
    
    private String[] countryCodes = {"+1", "+44", "+91", "+61"};

    public IndidualChildPhyscianPage(WebDriver driver) {
        this.driver = driver;
    }

    public String verifyPhysicianInfoPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(physicianInfoHeaderText));
        return driver.findElement(physicianInfoHeaderText).getText();
    }
    
    public void fillAllPhysicianInformation() {
        System.out.println("Filling all physician information fields...");
        try {
            enterRandomPhysicianFirstName();
            enterRandomPhysicianMiddleName();
            enterRandomPhysicianLastName();
            enterRandomPhysicianAddress1();
            enterRandomPhysicianAddress2();
            selectRandomPhysicianCountry();
            selectPhysicianState();
            enterRandomPhysicianCity();
            enterRandomPhysicianZipCode();
            selectRandomPhysicianPhoneType();
            selectRandomPhysicianCountryCode();
            enterRandomPhysicianPhoneNumber();
            
            toggleUsePhysicianForAll(false);
            
            enterRandomCareFacilityName();
            selectRandomCareFacilityPhoneType();
            selectRandomCareFacilityCountryCode();
            enterRandomCareFacilityPhoneNumber();
            enterRandomCareFacilityAddress1();
            enterRandomCareFacilityAddress2();
            selectRandomCareFacilityCountry();
            selectCareFacilityState();
            enterRandomCareFacilityCity();
            enterRandomCareFacilityZipCode();

        } catch (Exception e) {
            throw new RuntimeException("Failed to fill physician and emergency information.", e);
        }
    }

    public void proceedToNext() {
        clickButton(proceedToNextButton, "Proceed");
    }

    private void enterText(By locator, String text, String fieldName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        element.clear();
        element.sendKeys(text);
        System.out.println("Entered " + fieldName + ": " + text);
    }
    /*
    private void clickButton(By locator, String buttonName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
            System.out.println("Clicked on the '" + buttonName + "' button.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to click on the '" + buttonName + "' button.", e);
        }
    }
    */
    private void clickButton(By locator, String buttonName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("mat-progress-spinner")
            ));

            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            js.executeScript("arguments[0].click();", element);

            System.out.println("Clicked on: " + buttonName);

        } catch (Exception e) {
            System.out.println("Proceed button click failed. Element disabled? " + e.getMessage());
            throw new RuntimeException("Failed to click on '" + buttonName + "' button.", e);
        }
    }

    private void selectDropdownOptionByText(By dropdownLocator, String optionText, String dropdownName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        int attempts = 0;
        
        while (attempts < 3) {
            try {
                // Wait for the dropdown to be clickable and then click it
                WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
                Thread.sleep(500);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
                
                // Wait for the dropdown panel to be visible
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'cdk-overlay-pane')]")
                ));
                
                // Get all dropdown options - CORRECTED LOCATOR
                List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option//span")
                ));
                
                if (options.isEmpty()) {
                    throw new RuntimeException("No options found in " + dropdownName + " dropdown");
                }
                
                // Try to find exact match first
                for (WebElement option : options) {
                    if (option.getText().trim().equalsIgnoreCase(optionText)) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
                        Thread.sleep(300);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                        System.out.println("Selected " + dropdownName + ": " + optionText);
                        return;
                    }
                }
                
                // If exact match not found, try partial match
                for (WebElement option : options) {
                    if (option.getText().trim().contains(optionText)) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
                        Thread.sleep(300);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                        System.out.println("Selected " + dropdownName + " (partial match): " + option.getText());
                        return;
                    }
                }
                
                // If still not found, select first available option
                WebElement firstOption = options.get(0);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstOption);
                Thread.sleep(300);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstOption);
                System.out.println("Selected first option for " + dropdownName + ": " + firstOption.getText());
                return;
                
            } catch (StaleElementReferenceException e) {
                attempts++;
                System.out.println("Stale element in " + dropdownName + " dropdown, attempt: " + attempts);
                if (attempts >= 3) throw e;
            } catch (TimeoutException e) {
                attempts++;
                System.out.println("Timeout in " + dropdownName + " dropdown, attempt: " + attempts);
                if (attempts >= 3) throw new RuntimeException("Failed to select '" + optionText + "' from " + dropdownName + " dropdown.", e);
            } catch (Exception e) {
                attempts++;
                System.out.println("Error in " + dropdownName + " dropdown, attempt: " + attempts + ": " + e.getMessage());
                if (attempts >= 3) throw new RuntimeException("Failed to select '" + optionText + "' from " + dropdownName + " dropdown.", e);
            }
        }
    }

    public void enterRandomPhysicianFirstName() {
        enterText(physicianFirstName, firstNames[random.nextInt(firstNames.length)], "physician first name");
    }

    public void enterRandomPhysicianMiddleName() {
        enterText(physicianMiddleName, middleNames[random.nextInt(middleNames.length)], "physician middle name");
    }

    public void enterRandomPhysicianLastName() {
        enterText(physicianLastName, lastNames[random.nextInt(lastNames.length)], "physician last name");
    }
    
    public void enterRandomPhysicianAddress1() {
        enterText(physicianAddress1, address1Options[random.nextInt(address1Options.length)], "physician address 1");
    }

    public void enterRandomPhysicianAddress2() {
        enterText(physicianAddress2, address2Options[random.nextInt(address2Options.length)], "physician address 2");
    }

    public void selectRandomPhysicianCountry() {
        String[] countries = {"United States", "United Kingdom"};
        String randomCountry = countries[random.nextInt(countries.length)];
        this.selectedCountry = randomCountry;
        selectDropdownOptionByText(physicianCountryDropdown, randomCountry, "Physician Country");
    }

    public void selectPhysicianState() {
        String stateToSelect;
        if ("United States".equals(selectedCountry)) {
            String[] usStates = {"New York", "California", "Texas", "Florida", "Illinois"};
            stateToSelect = usStates[random.nextInt(usStates.length)];
        } else if ("United Kingdom".equals(selectedCountry)) {
            String[] ukRegions = {"England", "Scotland", "Wales", "Northern Ireland"};
            stateToSelect = ukRegions[random.nextInt(ukRegions.length)];
        } else {
            stateToSelect = "Not Applicable";
        }
        this.selectedState = stateToSelect;
        selectDropdownOptionByText(physicianStateDropdown, stateToSelect, "Physician State");
    }

    public void enterRandomPhysicianCity() {
        enterText(physicianCity, cityNames[random.nextInt(cityNames.length)], "physician city");
    }
    
    public void enterRandomPhysicianZipCode() {
        String zipCodeToEnter = generateRandomZipCode();
        enterText(physicianZipCode, zipCodeToEnter, "Physician Zip Code");
        System.out.println(" for State: " + selectedState);
    }
    
    public void selectRandomPhysicianPhoneType() {
        // Corrected data to match UI options: "Cell", "Work", "Other"
        selectDropdownOptionByText(physicianPhoneTypeDropdown, phoneTypes[random.nextInt(phoneTypes.length)], "Physician Phone Type");
    }
    
    public void selectRandomPhysicianCountryCode() {
        String countryCodeToSelect;
        if ("United States".equals(selectedCountry)) {
            countryCodeToSelect = "+1 (US)";
        } else if ("United Kingdom".equals(selectedCountry)) {
            countryCodeToSelect = "+44 (UK)";
        } else {
            countryCodeToSelect = countryCodes[random.nextInt(countryCodes.length)];
        }
        selectDropdownOptionByText(physicianCountryCodeDropdown, countryCodeToSelect, "Physician Country Code");
    }
    
    public void enterRandomPhysicianPhoneNumber() {
        enterText(physicianPhoneNumber, generateRandomPhoneNumber(), "Physician Phone Number");
    }

    public void enterRandomCareFacilityName() {
        enterText(careFacilityName, careFacilityNames[random.nextInt(careFacilityNames.length)], "Care Facility Name");
    }

    public void selectRandomCareFacilityPhoneType() {
        selectDropdownOptionByText(careFacilityPhoneTypeDropdown, phoneTypes[random.nextInt(phoneTypes.length)], "Care Facility Phone Type");
    }

    public void selectRandomCareFacilityCountryCode() {
        String countryCodeToSelect;
        if ("United States".equals(selectedCountry)) {
            countryCodeToSelect = "+1 (US)";
        } else if ("United Kingdom".equals(selectedCountry)) {
            countryCodeToSelect = "+44 (UK)";
        } else {
            countryCodeToSelect = countryCodes[random.nextInt(countryCodes.length)];
        }
        selectDropdownOptionByText(careFacilityCountryCodeDropdown, countryCodeToSelect, "Care Facility Country Code");
    }
    
 
    public void enterRandomCareFacilityPhoneNumber() {
        enterText(careFacilityPhoneNumber, generateRandomPhoneNumber(), "Care Facility Phone Number");
    }

    public void enterRandomCareFacilityAddress1() {
        enterText(careFacilityAddress1, address1Options[random.nextInt(address1Options.length)], "Care Facility Address 1");
    }

    public void enterRandomCareFacilityAddress2() {
        enterText(careFacilityAddress2, address2Options[random.nextInt(address2Options.length)], "Care Facility Address 2");
    }

    public void selectRandomCareFacilityCountry() {
        selectDropdownOptionByText(careFacilityCountryDropdown, selectedCountry, "Care Facility Country");
    }

    public void selectCareFacilityState() {
        selectDropdownOptionByText(careFacilityStateDropdown, selectedState, "Care Facility State");
    }

    public void enterRandomCareFacilityCity() {
        enterText(careFacilityCity, cityNames[random.nextInt(cityNames.length)], "Care Facility City");
    }

    public void enterRandomCareFacilityZipCode() {
        String zipCodeToEnter = generateRandomZipCode();
        enterText(careFacilityZipCode, zipCodeToEnter, "Care Facility Zip Code");
    }
    
    public void toggleUsePhysicianForAll(boolean shouldCheck) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(usePhysicianForAllCheckbox));
        boolean isCurrentlyChecked = checkbox.getAttribute("aria-checked") != null &&
                                     checkbox.getAttribute("aria-checked").equals("true");

        if (shouldCheck != isCurrentlyChecked) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
            System.out.println((shouldCheck ? "Checked" : "Unchecked") + " 'Use physician for all' checkbox");
        }
    }

    private String generateRandomPhoneNumber() {
        return String.format("%03d%03d%04d",
            random.nextInt(1000),
            random.nextInt(1000),
            random.nextInt(10000));
    }

    private String generateRandomZipCode() {
        if ("United States".equals(selectedCountry)) {
            return String.format("%05d", random.nextInt(100000));
        } else if ("United Kingdom".equals(selectedCountry)) {
            String[] areas = {"BD", "BH", "BL", "BN"};
            String area = areas[random.nextInt(areas.length)];
            String district = String.format("%d", random.nextInt(99) + 1);
            return area + district + " " + random.nextInt(10) + "AA";
        }
        return "90210";
    }
}
