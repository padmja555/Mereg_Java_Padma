package AdminPages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PhysicianInfoPage {
    WebDriver driver;
    private String selectedPhysicianCountry;
    private String selectedCareFacilityCountry;
    private String selectedPhysicianState;
    private String selectedCareFacilityState;
    private String physicianPhoneNumberValue;
    private Random random = new Random();

    // Locators for Physician Information section
    public By physicianInfoHeaderText = By.xpath("//app-student-registration-home-page//app-emergency-info/h2");
    public By physicianFirstName = By.xpath("(//input[@formcontrolname='firstName'])[3]");
    public By physicianMiddleName = By.xpath("(//input[@name='middleName'])[3]");
    public By physicianLastName = By.xpath("(//input[@name='lastName'])[3]");
    public By physicianAddress1 = By.xpath("(//input[@name='addressLine1'])[2]");
    public By physicianAddress2 = By.xpath("(//input[@formcontrolname='addressLine2'])[2]");
    public By physicianCountryDropdown = By.xpath("(//mat-select[./ancestor::mat-form-field[.//mat-label[text()='Country']]])[2]");
    public By physicianStateDropdown = By.xpath("(//mat-select[./ancestor::mat-form-field[.//mat-label[text()='State']]])[2]");
    public By physicianCity = By.xpath("(//input[@name='city'])[2]");
    public By physicianZipCode = By.xpath("(//input[@name='zipCode'])[2]");
    public By physicianPhoneTypeDropdown = By.xpath("(//mat-select[@name='phoneType'])[3]");
    public By physicianCountryCodeDropdown = By.xpath("(//mat-select[@formcontrolname='countryCode'])[3]");
    public By physicianPhoneNumber = By.xpath("((//input[@formcontrolname=\"phoneNumber\"])[3]");
    
    // Locators for Emergency Care Information Section
    public By alternatePhoneTypeDropdown = By.xpath("(//mat-select[@formcontrolname='alternatePhoneType'])[2]");
    public By alternateCountryCodeDropdown = By.xpath("(//mat-select[@formcontrolname='countryCode'])[3]");
    public By alternatePhoneNumber = By.xpath("(//input[@formcontrolname='phoneNumber'])[3]");
    public By careFacilityName = By.xpath("//input[@formcontrolname='careFacilityName']");
    public By careFacilityPhoneTypeDropdown = By.xpath("(//mat-select[@name='phoneType'])[4]");
    public By careFacilityCountryCodeDropdown = By.xpath("//mat-select[@formcontrolname='careFacilityCountryCode']");
    public By careFacilityPhoneNumber = By.xpath("//input[@formcontrolname='careFacilityPhoneNumber']");
    public By careFacilityAddress1 = By.xpath("//input[@name='careFacilityAddressLine1']");
    public By careFacilityAddress2 = By.xpath("//input[@name='careFacilityAddressLine2']");
    public By careFacilityCountryDropdown = By.xpath("//mat-select[@formcontrolname='careFacilityCountry']");
    public By careFacilityStateDropdown = By.xpath("//mat-select[@formcontrolname='careFacilityState']");
    public By careFacilityCity = By.xpath("//input[@name='careFacilityCity']");
    public By careFacilityZipCode = By.xpath("//input[@name='careFacilityZipCode']");

    // Common locators
    public By dropdownOptions = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
    
    // Button locators
    public By proceedToNextButton = By.xpath("(//mat-icon[text()='arrow_forward'])[3]");
    public By usePhysicianForAllCheckbox = By.xpath("//mat-checkbox[contains(., 'Use the selected physician')]");
    public By backButton = By.xpath("//button[contains(., 'Back to Parent/Guardian Info') or contains(., 'Back')]");
    public By choosePlanButton = By.xpath("//span[contains(text(), 'Choose Plan')]/ancestor::button");


    // Test data arrays
    private String[] firstNames = {"James", "Martinez", "John", "Patricia", "Robert", "Jennifer", "Michael", "Linda",
                                   "William", "Elizabeth", "David", "Susan", "Richard", "Jessica", "Joseph", "Sarah"};
    private String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis",
                                 "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson"};
    private String[] address1Options = {"123 Main St", "456 Oak Ave", "789 Pine Rd", "321 Elm St", "654 Maple Ave",
                                       "987 Cedar Ln", "111 Birch Dr", "222 Spruce Way", "333 Willow Rd", "444 Aspen Ct"};
    private String[] cityNames = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia",
                                 "San Antonio", "San Diego", "Dallas", "San Jose", "Austin", "Jacksonville",
                                 "Fort Worth", "Columbus", "Charlotte", "Indianapolis", "Seattle", "Denver",
                                 "Washington", "Boston", "El Paso", "Nashville", "Detroit", "Oklahoma City"};
    private String[] phoneTypes = {"Cell", "Work", "Other"};
    private String[] careFacilityNames = {"City Hospital", "Community Medical Center", "Regional Health", "General Hospital",
                                         "Children's Clinic", "Family Practice", "Urgent Care", "Specialty Center"};
    private String[] countryCodes = {"+1", "+44", "+91", "+61"};


    public PhysicianInfoPage(WebDriver driver) {
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
            Thread.sleep(1000);
            selectPhysicianState();
            Thread.sleep(2000);
            enterRandomPhysicianCity();
            Thread.sleep(2000);
            enterRandomPhysicianZipCode();
            Thread.sleep(2000);
            selectRandomPhysicianPhoneType();
            Thread.sleep(2000);
            selectRandomPhysicianCountryCode();
            Thread.sleep(2000);
            enterRandomPhysicianPhoneNumber();
            
            //toggleUsePhysicianForAll(false);
            Thread.sleep(2000);
            enterRandomCareFacilityName();
            //selectRandomCareFacilityPhoneType();
            Thread.sleep(2000);
            selectRandomCareFacilityCountryCode();
            Thread.sleep(2000);
            enterRandomCareFacilityPhoneNumber();
            Thread.sleep(2000);
           enterRandomCareFacilityAddress1();
            enterRandomCareFacilityAddress2();
           Thread.sleep(2000);
            selectRandomCareFacilityCountry();
            Thread.sleep(2000);
            selectCareFacilityState();
            Thread.sleep(2000);
            enterRandomCareFacilityCity();
            Thread.sleep(2000);
            enterRandomCareFacilityZipCode();

        } catch (Exception e) {
            throw new RuntimeException("Failed to fill physician and emergency information.", e);
        }
    }
    
    public void fillOnlyMandatoryPhysicianFields() {
        System.out.println("Filling only mandatory physician information fields...");
        try {
            enterRandomPhysicianFirstName();
            enterRandomPhysicianLastName();
            enterRandomPhysicianAddress1();
            selectRandomPhysicianCountry();
            selectPhysicianState();
            enterRandomPhysicianCity();
            enterRandomPhysicianZipCode();
            selectRandomPhysicianPhoneType();
            selectRandomPhysicianCountryCode();
            enterRandomPhysicianPhoneNumber();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fill mandatory physician information.", e);
        }
    }


    public void proceedToNext() {
        clickButton(proceedToNextButton, "Proceed");
    }
    
    public void clickChoosePlanButton() {
        clickButton(choosePlanButton, "Choose Plan");
    }


    private void enterText(By locator, String text, String fieldName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            element.clear();
            element.sendKeys(text);
            System.out.println("Entered " + fieldName + ": " + text);
        } catch (TimeoutException e) {
            throw new RuntimeException("Timeout: Failed to enter text into " + fieldName + " with locator: " + locator.toString(), e);
        }
    }
    
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

    private void selectDropdownOptionByText(By dropdownLocator, String optionText, String dropdownName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        int attempts = 0;

        while (attempts < 3) {
            try {
                WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
                Thread.sleep(500);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
                By optionsLocator = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(optionsLocator));
                List<WebElement> options = driver.findElements(optionsLocator);
                if (options.isEmpty()) {
                    throw new RuntimeException("No options found in " + dropdownName + " dropdown");
                }
                for (WebElement option : options) {
                    if (option.getText().trim().equalsIgnoreCase(optionText)) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
                        Thread.sleep(300);
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                        System.out.println("Selected " + dropdownName + ": " + optionText);
                        return;
                    }
                }
                throw new RuntimeException("Option '" + optionText + "' not found in " + dropdownName + " dropdown.");
            } catch (Exception e) {
                attempts++;
                System.out.println("Error in " + dropdownName + " dropdown, attempt: " + attempts + ": " + e.getMessage());
                if (attempts >= 3) {
                    throw new RuntimeException("Failed to select '" + optionText + "' from " + dropdownName + " dropdown after " + attempts + " attempts.", e);
                }
            }
        }
    }

    public void enterRandomPhysicianFirstName() {
        enterText(physicianFirstName, firstNames[random.nextInt(firstNames.length)], "physician first name");
    }

    public void enterRandomPhysicianMiddleName() {
        enterText(physicianMiddleName, "MiddleName", "physician middle name");
    }

    public void enterRandomPhysicianLastName() {
        enterText(physicianLastName, lastNames[random.nextInt(lastNames.length)], "physician last name");
    }
    
    public void enterRandomPhysicianAddress1() {
        enterText(physicianAddress1, address1Options[random.nextInt(address1Options.length)], "physician address 1");
    }

    public void enterRandomPhysicianAddress2() {
        enterText(physicianAddress2, "Apt 1", "physician address 2");
    }
    
    public void selectRandomPhysicianCountry() {
        try {
            WebElement dropdown = driver.findElement(physicianCountryDropdown);
            dropdown.click();
            By optionsLocator = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(optionsLocator));
            if (options.isEmpty()) {
                throw new RuntimeException("No countries found in the Physician Country dropdown.");
            }
            WebElement randomOption = options.get(random.nextInt(options.size()));
            String countryToSelect = randomOption.getText();
            By specificOptionLocator = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option[normalize-space(.)='" + countryToSelect + "']");
            WebElement optionToClick = wait.until(ExpectedConditions.elementToBeClickable(specificOptionLocator));
            optionToClick.click();
            System.out.println("Selected Physician Country: " + countryToSelect);
            this.selectedPhysicianCountry = countryToSelect;
        } catch (Exception e) {
            throw new RuntimeException("Failed to select a random country from the Physician Country dropdown.", e);
        }
    }
    
    public void selectPhysicianState() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        int attempts = 0;
        
        while (attempts < 3) {
            try {
                WebElement stateDropdown = wait.until(ExpectedConditions.elementToBeClickable(physicianStateDropdown));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", stateDropdown);
                List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option")));
                if (options.isEmpty()) {
                    attempts++;
                    continue;
                }
                String stateToSelect = options.get(random.nextInt(options.size())).getText().trim();
                WebElement optionToClick = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option[normalize-space(.)='" + stateToSelect + "']")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", optionToClick);
                this.selectedPhysicianState = stateToSelect;
                System.out.println("Selected Physician State: " + selectedPhysicianState);
                return;
            } catch (StaleElementReferenceException | TimeoutException e) {
                attempts++;
                System.out.println("Encountered stale element or timeout, retrying. Attempt: " + attempts);
                if (attempts >= 3) {
                    throw new RuntimeException("Failed to select a state after " + attempts + " attempts.", e);
                }
            }
        }
    }

    public void enterRandomPhysicianCity() {
        enterText(physicianCity, cityNames[random.nextInt(cityNames.length)], "physician city");
    }
    
    public void enterRandomPhysicianZipCode() {
        String zipCodeToEnter = generateRandomZipCode(this.selectedPhysicianCountry);
        enterText(physicianZipCode, zipCodeToEnter, "Physician Zip Code");
        System.out.println(" for Country: " + selectedPhysicianCountry);
    }
    
    public void selectRandomPhysicianPhoneType() {
        System.out.println("Attempting to select a random phone type for Physician...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); 
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(physicianPhoneTypeDropdown));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
            By optionsLocator = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
            List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(optionsLocator));
            if (options.isEmpty()) {
                throw new RuntimeException("No phone types found in the dropdown.");
            }
            WebElement randomOption = options.get(random.nextInt(options.size()));
            String phoneTypeToSelect = randomOption.getText();
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", randomOption);
            System.out.println("Selected Physician Phone Type: " + phoneTypeToSelect);
        } catch (TimeoutException e) {
            throw new RuntimeException("Timeout: Failed to find or click on the phone type dropdown or its options.", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select a random phone type from the Physician Phone Type dropdown.", e);
        }
    }
    
    public void selectRandomPhysicianCountryCode() {
        String countryCodeToSelect;
        if ("United States".equals(selectedPhysicianCountry)) {
            countryCodeToSelect = "+1 (US)";
        } else if ("United Kingdom".equals(selectedPhysicianCountry)) {
            countryCodeToSelect = "+44 (UK)";
        } else {
            countryCodeToSelect = countryCodes[random.nextInt(countryCodes.length)];
        }
        try {
            System.out.println("Attempting to select Physician Country Code: " + countryCodeToSelect);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Wait for the dropdown to be clickable and click it
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(physicianCountryCodeDropdown));
            dropdown.click();

            // Wait for the specific option to be visible and clickable
            By optionLocator = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option//span[normalize-space(.)='" + countryCodeToSelect + "']");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            option.click();

            System.out.println("Selected Physician Country Code: " + countryCodeToSelect);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select '" + countryCodeToSelect + "' from Physician Country Code dropdown.", e);
        }
    }
    /*
   
    public void enterRandomPhysicianPhoneNumber() {
        String randomPhoneNumber = generateRandomPhoneNumber(); 
        enterText(physicianPhoneNumber, randomPhoneNumber, "Physician Phone Number");
        this.physicianPhoneNumberValue = randomPhoneNumber;
    }
    */
    public void enterRandomPhysicianPhoneNumber() {
        try {
            String randomPhoneNumber = generateRandomPhoneNumber(); 
            
            // CORRECTED: Use proper XPath syntax without extra parenthesis
            By physicianPhoneLocator = By.xpath("(//input[@formcontrolname='phoneNumber'])[3]");
            
            enterText(physicianPhoneLocator, randomPhoneNumber, "Physician Phone Number");
            this.physicianPhoneNumberValue = randomPhoneNumber;
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter physician phone number", e);
        }
    }
    /*
    private String enterRandomPhysicianPhoneNumber() {
        Random random = new Random();
        
        // Generate formatted phone number: (XXX) XXX-XXXX
        return String.format("(%03d) %03d-%04d",
            random.nextInt(1000),  // Area code (000-999)
            random.nextInt(1000),  // Prefix (000-999)
            random.nextInt(10000)  // Line number (0000-9999)
        );
    }
      */
    
    public void enterRandomCareFacilityName() {
        enterText(careFacilityName, careFacilityNames[random.nextInt(careFacilityNames.length)], "Care Facility Name");
    }

    public void selectRandomCareFacilityPhoneType() {
        System.out.println("Attempting to select a random phone type for the Care Facility...");
        By careFacilityPhoneTypeDropdown = By.xpath("//mat-select[@formcontrolname='careFacilityPhoneType']");
        String[] phoneTypes = {"Cell", "Work", "Other"};
        String phoneTypeToSelect = phoneTypes[random.nextInt(phoneTypes.length)];
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(careFacilityPhoneTypeDropdown));
            dropdown.click();
            By optionsLocator = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
            List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(optionsLocator));
            boolean optionClicked = false;
            for (WebElement option : options) {
                if (option.getText().trim().equals(phoneTypeToSelect)) {
                    option.click();
                    optionClicked = true;
                    System.out.println("Selected Care Facility Phone Type: " + phoneTypeToSelect);
                    break;
                }
            }
            if (!optionClicked) {
                 throw new RuntimeException("Option '" + phoneTypeToSelect + "' was not found in the dropdown list.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to select '" + phoneTypeToSelect + "' from Care Facility Phone Type dropdown.", e);
        }
    }
    
    public void selectRandomCareFacilityCountryCode() {
        System.out.println("Attempting to select a random phone type for the Care Facility...");
        
        // Use a FluentWait for a more robust click on the dropdown itself.
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        
        try {
            // Wait for the dropdown to be clickable and click it to open the options.
            wait.until(ExpectedConditions.elementToBeClickable(careFacilityCountryCodeDropdown)).click();
            System.out.println("Opened Care Facility Country Code dropdown.");
            
            // Re-use the FluentWait for a robust selection.
            wait.withTimeout(Duration.ofSeconds(15)); // Extend wait time for the options to appear.
            
            // We use a loop to handle the StaleElementReferenceException
            for (int i = 0; i < 5; i++) { // Retry up to 5 times
                try {
                    // Find all the options in the dropdown.
                    List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option")));
                    
                    if (options.isEmpty()) {
                        throw new RuntimeException("No country code options found in dropdown.");
                    }

                    // Select a random option.
                    Random rand = new Random();
                    WebElement selectedOption = options.get(rand.nextInt(options.size()));
                    
                    // Click the selected option.
                    selectedOption.click();
                    System.out.println("Selected Care Facility Country Code: " + selectedOption.getText());
                    return; // Exit the method on successful click.
                    
                } catch (StaleElementReferenceException e) {
                    System.out.println("Caught StaleElementReferenceException. Retrying...");
                    // The loop will continue and re-attempt to find the element.
                }
            }
        } catch (TimeoutException e) {
            throw new RuntimeException("An error occurred during Care Facility Country/Country Code selection: The dropdown did not open or options did not appear.", e);
        }
        
        // If the loop finishes without a successful click
        throw new RuntimeException("An error occurred during Care Facility Country/Country Code selection after multiple retries.");
    }



    public void enterRandomCareFacilityPhoneNumber() {
        String randomPhoneNumber = generateRandomPhoneNumber();
        enterText(careFacilityPhoneNumber, randomPhoneNumber, "Care Facility Phone Number");
    }

    public void enterRandomCareFacilityAddress1() {
        enterText(careFacilityAddress1, address1Options[random.nextInt(address1Options.length)], "Care Facility Address 1");
    }

    public void enterRandomCareFacilityAddress2() {
        enterText(careFacilityAddress2, "Apt 1", "Care Facility Address 2");
    }

    public void selectRandomCareFacilityCountry() {
        try {
            WebElement dropdown = driver.findElement(careFacilityCountryDropdown);
            dropdown.click();
            By optionsLocator = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(optionsLocator));
            if (options.isEmpty()) {
                throw new RuntimeException("No countries found in the Care Facility Country dropdown.");
            }
            WebElement randomOption = options.get(random.nextInt(options.size()));
            String countryToSelect = randomOption.getText();
            By specificOptionLocator = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option[normalize-space(.)='" + countryToSelect + "']");
            WebElement optionToClick = wait.until(ExpectedConditions.elementToBeClickable(specificOptionLocator));
            optionToClick.click();
            System.out.println("Selected Care Facility Country: " + countryToSelect);
            this.selectedCareFacilityCountry = countryToSelect;
        } catch (Exception e) {
            throw new RuntimeException("Failed to select a random country from the Care Facility Country dropdown.", e);
        }
    }

    public void selectCareFacilityState() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        int attempts = 0;
        
        while (attempts < 3) {
            try {
                WebElement stateDropdown = wait.until(ExpectedConditions.elementToBeClickable(careFacilityStateDropdown));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", stateDropdown);
                List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option")));
                if (options.isEmpty()) {
                    attempts++;
                    continue;
                }
                String stateToSelect = options.get(random.nextInt(options.size())).getText().trim();
                WebElement optionToClick = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option[normalize-space(.)='" + stateToSelect + "']")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", optionToClick);
                this.selectedCareFacilityState = stateToSelect;
                System.out.println("Selected Care Facility State: " + selectedCareFacilityState);
                return;
            } catch (StaleElementReferenceException | TimeoutException e) {
                attempts++;
                System.out.println("Encountered stale element or timeout, retrying. Attempt: " + attempts);
                if (attempts >= 3) {
                    throw new RuntimeException("Failed to select a state after " + attempts + " attempts.", e);
                }
            }
        }
    }

    public void enterRandomCareFacilityCity() {
        enterText(careFacilityCity, cityNames[random.nextInt(cityNames.length)], "Care Facility City");
    }

    public void enterRandomCareFacilityZipCode() {
        String zipCodeToEnter = generateRandomZipCode(this.selectedCareFacilityCountry);
        enterText(careFacilityZipCode, zipCodeToEnter, "Care Facility Zip Code");
        System.out.println(" for Country: " + selectedCareFacilityCountry);
    }
    
    public void toggleUsePhysicianForAll(boolean checked) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(usePhysicianForAllCheckbox));
        boolean isChecked = checkbox.getAttribute("class").contains("mat-checkbox-checked");
        if (checked != isChecked) {
            checkbox.click();
            System.out.println("Toggled 'Use Physician for all' checkbox to " + checked);
        }
    }
    
    private String generateRandomPhoneNumber() {
        StringBuilder phoneNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            phoneNumber.append(random.nextInt(10));
        }
        return phoneNumber.toString();
    }
    
    private String generateRandomZipCode(String country) {
        switch (country) {
            case "United States":
                return String.format("%05d", random.nextInt(100000));
            case "United Kingdom":
                String[] ukFormats = {"AA9A 9AA", "A9A 9AA", "A9 9AA", "A99 9AA", "AA9 9AA", "AA99 9AA"};
                String ukFormat = ukFormats[random.nextInt(ukFormats.length)];
                StringBuilder ukPostcode = new StringBuilder();
                for (char c : ukFormat.toCharArray()) {
                    if (c == 'A') {
                        ukPostcode.append((char) ('A' + random.nextInt(26)));
                    } else if (c == '9') {
                        ukPostcode.append(random.nextInt(10));
                    } else {
                        ukPostcode.append(c);
                    }
                }
                return ukPostcode.toString();
            case "Canada":
                return String.format("%c%d%c %d%c%d",
                        (char) ('A' + random.nextInt(26)),
                        random.nextInt(10),
                        (char) ('A' + random.nextInt(26)),
                        random.nextInt(10),
                        (char) ('A' + random.nextInt(26)),
                        random.nextInt(10));
            default:
                return String.format("%05d", random.nextInt(100000));
        }
    }
    /*
    public void selectRandomPhysicianCountryCode(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            // Wait for the dropdown to be visible and clickable
            WebElement countryCodeDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-select[@formcontrolname='countryCode']")));
            Actions actions = new Actions(driver);
            actions.moveToElement(countryCodeDropdown).click().perform();
            
            // Optionally add more wait to ensure the options load
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-option[contains(text(), '+1 (US)')]"))).click();
        } catch (TimeoutException e) {
            // Handle timeout, log the error, or rethrow as a RuntimeException
            throw new RuntimeException("Failed to select country code", e);
        }
    }
    */

}