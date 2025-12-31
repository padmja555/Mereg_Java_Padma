package AdminPages;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PhysicininfoNegativePage {
    WebDriver driver;
    private String selectedCountry;
    private String selectedState;
    private Random random = new Random();

    // Locators
    // Physician Information Section - UPDATED LOCATORS
    public By physicianInfoHeaderText = By.xpath("//app-student-registration-home-page//app-emergency-info/h2");
    public By physicianFirstName = By.xpath("(//input[@name='firstName'])[3]");
    public By physicianMiddleName = By.xpath("(//input[@name='middleName'])[3]");
    public By physicianLastName = By.xpath("(//input[@name='lastName'])[3]");
    public By physicianAddress1 = By.xpath("(//input[@name='addressLine1'])[2]");
    public By physicianAddress2 = By.xpath("(//input[@formcontrolname='addressLine2'])[2]");
    public By physicianCountryDropdown = By.xpath("(//mat-select[@name='country'])[2]");
    public By physicianStateDropdown = By.xpath("(//mat-select[@formcontrolname='state'])[2]");
    public By physicianCity = By.xpath("(//input[@name='city'])[2]");
    public By physicianZipCode = By.xpath("(//input[@name='zipCode'])[2]");
    
    // UPDATED PHONE FIELD LOCATORS
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
    public By physicianCountryOptions = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
    public By physicianStateOptions = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
    public By physicianPhoneTypeOptions = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
    public By physicianCountryCodeOptions = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
    public By careFacilityCountryOptions = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
    public By careFacilityStateOptions = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
    public By careFacilityPhoneTypeOptions = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
    public By careFacilityCountryCodeOptions = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
    
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
    
    private String[] phoneTypes = {"Mobile", "Home", "Work", "Other"};
    private String[] careFacilityNames = {"City Hospital", "Community Medical Center", "Regional Health", "General Hospital", 
                                        "Children's Clinic", "Family Practice", "Urgent Care", "Specialty Center"};

    public PhysicininfoNegativePage(WebDriver driver) {
        this.driver = driver;
    }

    public String verifyPhysicianInfoPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(physicianInfoHeaderText));
        return driver.findElement(physicianInfoHeaderText).getText();
    }

    public void enterRandomPhysicianFirstName() {
        String randomFirstName = firstNames[random.nextInt(firstNames.length)];
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(physicianFirstName));
        element.clear();
        element.sendKeys(randomFirstName);
        System.out.println("Entered physician first name: " + randomFirstName);
    }

    public void enterRandomPhysicianMiddleName() {
        String randomMiddleName = middleNames[random.nextInt(middleNames.length)];
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(physicianMiddleName));
        element.clear();
        element.sendKeys(randomMiddleName);
        System.out.println("Entered physician middle name: " + randomMiddleName);
    }

    public void enterRandomPhysicianLastName() {
        String randomLastName = lastNames[random.nextInt(lastNames.length)];
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(physicianLastName));
        element.clear();
        element.sendKeys(randomLastName);
        System.out.println("Entered physician last name: " + randomLastName);
    }

    public void enterRandomPhysicianAddress1() {
        String randomAddress1 = address1Options[random.nextInt(address1Options.length)];
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(physicianAddress1));
        element.clear();
        element.sendKeys(randomAddress1);
        System.out.println("Entered physician address 1: " + randomAddress1);
    }

    public void enterRandomPhysicianAddress2() {
        String randomAddress2 = address2Options[random.nextInt(address2Options.length)];
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(physicianAddress2));
        element.clear();
        element.sendKeys(randomAddress2);
        System.out.println("Entered physician address 2: " + randomAddress2);
    }
    
    public void selectRandomPhysicianCountry() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(physicianCountryDropdown)).click();

        List<WebElement> countryOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(physicianCountryOptions));

        int randomIndex = random.nextInt(countryOptions.size());
        WebElement selectedOption = countryOptions.get(randomIndex);
        this.selectedCountry = selectedOption.getText();
        selectedOption.click();
        System.out.println("Selected physician country: " + selectedCountry);
    }

    public void selectPhysicianState() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        int attempts = 0;
        
        while (attempts < 3) {
            try {
                WebElement stateDropdown = wait.until(ExpectedConditions.elementToBeClickable(physicianStateDropdown));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", stateDropdown);
                
                wait.until(ExpectedConditions.visibilityOfElementLocated(physicianStateOptions));
                List<WebElement> options = driver.findElements(physicianStateOptions);
                
                if (options.isEmpty()) {
                    attempts++;
                    continue;
                }
                
                String stateToSelect = "";
                if ("United States".equals(selectedCountry)) {
                    stateToSelect = "New York";
                    this.selectedState = stateToSelect;
                } else if ("United Kingdom".equals(selectedCountry)) {
                    stateToSelect = "Scotland";
                    this.selectedState = stateToSelect;
                } else if ("Canada".equals(selectedCountry)) {
                    stateToSelect = "Ontario";
                    this.selectedState = stateToSelect;
                } else if ("Australia".equals(selectedCountry)) {
                    stateToSelect = "New South Wales";
                    this.selectedState = stateToSelect;
                }
                
                if (!stateToSelect.isEmpty()) {
                    for (WebElement option : options) {
                        if (option.getText().trim().equals(stateToSelect)) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                            System.out.println("Selected physician state: " + stateToSelect);
                            return;
                        }
                    }
                }
                
                int randomIndex = randomRange(1, options.size() - 1);
                WebElement randomOption = options.get(randomIndex);
                this.selectedState = randomOption.getText();
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", randomOption);
                System.out.println("Selected physician state: " + selectedState);
                return;
                
            } catch (StaleElementReferenceException e) {
                attempts++;
                System.out.println("Stale element in selectPhysicianState(), attempt: " + attempts);
                if (attempts >= 3) throw e;
            }
        }
    }
    
    public void enterRandomPhysicianCity() {
        String randomCity = cityNames[random.nextInt(cityNames.length)];
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(physicianCity));
        element.clear();
        element.sendKeys(randomCity);
        System.out.println("Entered physician city: " + randomCity);
    }
    
    public void enterRandomPhysicianZipCode() {
        String zipCodeToEnter = generateRandomZipCode();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement zipField = wait.until(ExpectedConditions.elementToBeClickable(physicianZipCode));
            zipField.clear();
            zipField.sendKeys(zipCodeToEnter);
            ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", zipField);
            System.out.println("Entered physician Zip Code: " + zipCodeToEnter + " for State: " + selectedState);
        } catch (Exception e) {
            System.out.println("Error entering physician zip code: " + e.getMessage());
            throw new RuntimeException("Failed to enter physician zip code into the field.", e);
        }
    }
    
    private String generateRandomZipCode() {
        if ("United States".equals(selectedCountry)) {
            return String.format("%05d", random.nextInt(100000));
        } else if ("United Kingdom".equals(selectedCountry)) {
            String[] formats = {"AA9A 9AA", "A9A 9AA", "A9 9AA", "A99 9AA", "AA9 9AA", "AA99 9AA"};
            String format = formats[random.nextInt(formats.length)];
            StringBuilder postcode = new StringBuilder();
            
            for (char c : format.toCharArray()) {
                if (c == 'A') {
                    postcode.append((char) ('A' + random.nextInt(26)));
                } else if (c == '9') {
                    postcode.append(random.nextInt(10));
                } else {
                    postcode.append(c);
                }
            }
            
            return postcode.toString();
        } else if ("Canada".equals(selectedCountry)) {
            return String.format("%c%d%c %d%c%d", 
                (char) ('A' + random.nextInt(26)),
                random.nextInt(10),
                (char) ('A' + random.nextInt(26)),
                random.nextInt(10),
                (char) ('A' + random.nextInt(26)),
                random.nextInt(10));
        } else {
            return String.format("%05d", random.nextInt(100000));
        }
    }

    public void selectRandomPhysicianPhoneType() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(physicianPhoneTypeDropdown)).click();
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(physicianPhoneTypeOptions));
        if (!options.isEmpty()) {
            int randomIndex = randomRange(1, options.size() - 1);
            String selectedType = options.get(randomIndex).getText();
            options.get(randomIndex).click();
            System.out.println("Selected physician phone type: " + selectedType);
        }
    }
    
    public void selectRandomPhysicianCountryCode() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(physicianCountryCodeDropdown)).click();
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(physicianCountryCodeOptions));
        int randomIndex = randomRange(1, options.size() - 1);
        String selectedCode = options.get(randomIndex).getText();
        options.get(randomIndex).click();
        System.out.println("Selected physician country code: " + selectedCode);
    }
    
    public void enterRandomPhysicianPhoneNumber() {
        String phoneNumber = generateRandomPhoneNumber();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(physicianPhoneNumber));
        element.clear();
        element.sendKeys(phoneNumber);
        System.out.println("Entered physician phone number: " + phoneNumber);
    }
    
    private String generateRandomPhoneNumber() {
        return String.format("%03d%03d%04d", 
            random.nextInt(1000), 
            random.nextInt(1000), 
            random.nextInt(10000));
    }
    
    public void enterRandomCareFacilityName() {
        String randomFacilityName = careFacilityNames[random.nextInt(careFacilityNames.length)];
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(careFacilityName));
        element.clear();
        element.sendKeys(randomFacilityName);
        System.out.println("Entered care facility name: " + randomFacilityName);
    }

    public void selectRandomCareFacilityPhoneType() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(careFacilityPhoneTypeDropdown)).click();
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(careFacilityPhoneTypeOptions));
        if (!options.isEmpty()) {
            int randomIndex = randomRange(1, options.size() - 1);
            String selectedType = options.get(randomIndex).getText();
            options.get(randomIndex).click();
            System.out.println("Selected care facility phone type: " + selectedType);
        }
    }
    
    public void selectRandomCareFacilityCountryCode() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(careFacilityCountryCodeDropdown)).click();
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(careFacilityCountryCodeOptions));
        int randomIndex = randomRange(1, options.size() - 1);
        String selectedCode = options.get(randomIndex).getText();
        options.get(randomIndex).click();
        System.out.println("Selected care facility country code: " + selectedCode);
    }

    public void enterRandomCareFacilityPhoneNumber() {
        String phoneNumber = generateRandomPhoneNumber();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(careFacilityPhoneNumber));
        element.clear();
        element.sendKeys(phoneNumber);
        System.out.println("Entered care facility phone number: " + phoneNumber);
    }

    public void enterRandomCareFacilityAddress1() {
        String randomAddress1 = address1Options[random.nextInt(address1Options.length)];
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(careFacilityAddress1));
        element.clear();
        element.sendKeys(randomAddress1);
        System.out.println("Entered care facility address 1: " + randomAddress1);
    }

    public void enterRandomCareFacilityAddress2() {
        String randomAddress2 = address2Options[random.nextInt(address2Options.length)];
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(careFacilityAddress2));
        element.clear();
        element.sendKeys(randomAddress2);
        System.out.println("Entered care facility address 2: " + randomAddress2);
    }
    
    public void selectRandomCareFacilityCountry() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(careFacilityCountryDropdown)).click();

        List<WebElement> countryOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(careFacilityCountryOptions));

        int randomIndex = random.nextInt(countryOptions.size());
        WebElement selectedOption = countryOptions.get(randomIndex);
        String selectedCountry = selectedOption.getText();
        selectedOption.click();
        System.out.println("Selected care facility country: " + selectedCountry);
    }

    public void selectCareFacilityState() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        int attempts = 0;
        
        while (attempts < 3) {
            try {
                WebElement stateDropdown = wait.until(ExpectedConditions.elementToBeClickable(careFacilityStateDropdown));
                
                // Scroll into view first
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", stateDropdown);
                Thread.sleep(500);
                
                // Click using JavaScript to avoid interception issues
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", stateDropdown);
                
                // Wait for options to appear
                wait.until(ExpectedConditions.visibilityOfElementLocated(careFacilityStateOptions));
                List<WebElement> options = driver.findElements(careFacilityStateOptions);
                
                if (options.isEmpty()) {
                    attempts++;
                    Thread.sleep(1000);
                    continue;
                }
                
                // Select a random state option
                int randomIndex = randomRange(1, options.size() - 1);
                WebElement randomOption = options.get(randomIndex);
                String selectedState = randomOption.getText();
                
                // Click using JavaScript
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", randomOption);
                System.out.println("Selected care facility state: " + selectedState);
                return;
                
            } catch (StaleElementReferenceException e) {
                attempts++;
                System.out.println("Stale element in selectCareFacilityState(), attempt: " + attempts);
                if (attempts >= 3) throw e;
            } catch (Exception e) {
                attempts++;
                System.out.println("Error in selectCareFacilityState(), attempt: " + attempts + ", error: " + e.getMessage());
                if (attempts >= 3) throw new RuntimeException("Failed to select care facility state after 3 attempts", e);
            }
        }
    }

    public void enterRandomCareFacilityCity() {
        try {
            String randomCity = cityNames[random.nextInt(cityNames.length)];
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Wait for element to be visible and enabled
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(careFacilityCity));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            Thread.sleep(300);
            
            // Clear and enter text
            element.clear();
            element.sendKeys(randomCity);
            System.out.println("Entered care facility city: " + randomCity);
            
            // Trigger blur event to ensure validation fires
            ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", element);
            
        } catch (Exception e) {
            System.out.println("Error entering care facility city: " + e.getMessage());
            throw new RuntimeException("Failed to enter care facility city", e);
        }
    }

    public void enterRandomCareFacilityZipCode() {
        try {
            // Generate appropriate zip code based on selected country
            String zipCodeToEnter = generateRandomZipCodeForCareFacility();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Wait for element to be interactable
            WebElement zipField = wait.until(ExpectedConditions.elementToBeClickable(careFacilityZipCode));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", zipField);
            Thread.sleep(300);
            
            // Clear and enter text
            zipField.clear();
            zipField.sendKeys(zipCodeToEnter);
            
            // Trigger blur event
            ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", zipField);
            System.out.println("Entered care facility Zip Code: " + zipCodeToEnter);
            
        } catch (Exception e) {
            System.out.println("Error entering care facility zip code: " + e.getMessage());
            throw new RuntimeException("Failed to enter care facility zip code", e);
        }
    }

    private String generateRandomZipCodeForCareFacility() {
        // This method should use the selected country for care facility
        // You might need to track the selected care facility country separately
        if ("United States".equals(selectedCountry)) {
            return String.format("%05d", random.nextInt(100000));
        } else if ("United Kingdom".equals(selectedCountry)) {
            String[] formats = {"AA9A 9AA", "A9A 9AA", "A9 9AA", "A99 9AA", "AA9 9AA", "AA99 9AA"};
            String format = formats[random.nextInt(formats.length)];
            StringBuilder postcode = new StringBuilder();
            
            for (char c : format.toCharArray()) {
                if (c == 'A') {
                    postcode.append((char) ('A' + random.nextInt(26)));
                } else if (c == '9') {
                    postcode.append(random.nextInt(10));
                } else {
                    postcode.append(c);
                }
            }
            
            return postcode.toString();
        } else if ("Canada".equals(selectedCountry)) {
            return String.format("%c%d%c %d%c%d", 
                (char) ('A' + random.nextInt(26)),
                random.nextInt(10),
                (char) ('A' + random.nextInt(26)),
                random.nextInt(10),
                (char) ('A' + random.nextInt(26)),
                random.nextInt(10));
        } else {
            return String.format("%05d", random.nextInt(100000));
        }
    }

    // Enhanced method to fill all physician information
    public void fillAllPhysicianInformation() {
        try {
            // Physician information
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
            
            // Add a small delay before filling care facility info
            Thread.sleep(1000);
            
            // Care facility information
            enterRandomCareFacilityName();
            selectRandomCareFacilityPhoneType();
            selectRandomCareFacilityCountryCode();
            enterRandomCareFacilityPhoneNumber();
            enterRandomCareFacilityAddress1();
            enterRandomCareFacilityAddress2();
            selectRandomCareFacilityCountry();
            
            // Add delay to ensure state dropdown is populated
            Thread.sleep(1500);
            
            selectCareFacilityState();
            
            // Add delay to ensure city field is enabled
            Thread.sleep(1000);
            
            enterRandomCareFacilityCity();
            
            // Add delay to ensure zip code field is enabled
            Thread.sleep(1000);
            
            enterRandomCareFacilityZipCode();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while filling physician information", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fill physician information", e);
        }
    }

    public void proceedToNext() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        try {
            WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(proceedToNextButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", proceedButton);
            Thread.sleep(1000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", proceedButton);
            System.out.println("Clicked on Proceed button");
            
        } catch (Exception e) {
            System.out.println("Error clicking proceed button: " + e.getMessage());
            throw new RuntimeException("Failed to click on Proceed button", e);
        }
    }

    public int randomRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}