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

public class Parentpage {
    WebDriver driver;
    private String selectedCountry;
    private String selectedState;
    private Random random = new Random();

    // Locators
    public By parentguardianHeaderText = By.xpath("//h2[text()='Parent/Guardian info']");
    public By firstname = By.xpath("(//*[@name='firstName'])[2]");
    public By middlename = By.xpath("(//*[@name='middleName'])[2]");
    public By lastname = By.xpath("(//*[@name='lastName'])[2]");
    public By relationshipdropdown = By.xpath("//mat-select[@name='relationshipWithChild']");
    public  By relationshipoptions = By.xpath("//div[@class='cdk-overlay-pane']//mat-option");
    public By address1 = By.xpath("(//input[@name='addressLine1'])[1]");
    public By address2 = By.xpath("(//input[@formcontrolname='addressLine2'])[1]");
    public By countrydropdownclick = By.xpath("(//mat-select[@name='country'])[1]");
    public By countrydropdown = By.xpath("//div[@class='cdk-overlay-pane']//mat-option");
    public By statedropdown = By.xpath("(//mat-select[@name='state'])[1]");
    public By statedropdownoptions = By.xpath("//div[@class='cdk-overlay-pane']//mat-option");
    public  By city = By.xpath("(//input[@name='city'])[1]");
    public  By zipcode = By.xpath("(//input[@name='zipCode'])[1]");
    public By phonetype = By.xpath("(//mat-select[@name='phoneType'])[1]");
    public By phonetypeoptions = By.xpath("//div[@class='cdk-overlay-pane']//mat-option");
    public By Countrycode = By.xpath("(//mat-select[@formcontrolname='countryCode'])[1]");
    public By Counrtycodeselect = By.xpath("//div[@class='cdk-overlay-pane']//mat-option");
    public By phonenumber = By.xpath("(//input[@name='phoneNumber'])[1]");
    public By Alternativephonenumbercode = By.xpath("(//mat-select[@name='alternateCountryCode'])[1]");
    public By Alternativephonenumbercodeselect = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
    public  By Alternativephonenumber = By.xpath("(//input[@name='phoneNumber'])[2]");
    public  By emailaddress = By.xpath("//input[@type='email' or @name='email' or contains(@id,'email') or @formcontrolname='email']");
    //By AddAlternativGuardianinfoicon = By.xpath("(//mat-icon[text()='add_circle_outline'])[2]");
    ////mat-icon[@mattooltip='Add alternate guardian']
    public By AddAlternativGuardianinfoicon = By.xpath("//mat-icon[@mattooltip='Add alternate guardian']");
    public By AlternateGuardianName = By.xpath("//input[@name='name']");
    public By AlternateGuardianRelation = By.xpath("(//mat-select[@name='relationshipWithChild'])[2]");
    public By AlternateGuardianRelationtypes = By.xpath("//div[@role='listbox']//mat-option");
    public By AlternateGuardianPhoneType = By.xpath("//div[@id='mat-select-value-59']");
    public By AlternateGuardiancountrycode = By.xpath("(//mat-select[@formcontrolname='countryCode'])[2]");
    public By AlternateGuardiancountryCodeSelect = By.xpath("//div[@class='cdk-overlay-pane']//mat-option");
    public By AlternateGuardianPhonenumber = By.xpath("(//input[@name='phoneNumber'])[2]");
    public By ProccedToMedicalInfoButton = By.xpath("(//button[@name='started'])[4]");
    //public By ProccedToMedicalInfoButton = By.xpath("(//button[@name='started'])[5]");

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

    public Parentpage(WebDriver driver) {
        this.driver = driver;
    }

    public String verifyParentGuardianPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(parentguardianHeaderText));
        return driver.findElement(parentguardianHeaderText).getText();
    }

    public void enterRandomFirstName() {
        String randomFirstName = firstNames[random.nextInt(firstNames.length)];
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstname));
        element.clear();
        element.sendKeys(randomFirstName);
        System.out.println("Entered first name: " + randomFirstName);
    }

    public void enterRandomMiddleName() {
        String randomMiddleName = middleNames[random.nextInt(middleNames.length)];
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(middlename));
        element.clear();
        element.sendKeys(randomMiddleName);
        System.out.println("Entered middle name: " + randomMiddleName);
    }

    public void enterRandomLastName() {
        String randomLastName = lastNames[random.nextInt(lastNames.length)];
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(lastname));
        element.clear();
        element.sendKeys(randomLastName);
        System.out.println("Entered last name: " + randomLastName);
    }

    public void selectRelationship() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(relationshipdropdown)).click();

        List<WebElement> relationoptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(relationshipoptions));
        
        int randomIndex = randomRange(1, relationoptions.size() - 1);
        relationoptions.get(randomIndex).click();
    }
    
    
    public void enterRandomAddress1() {
        String randomAddress1 = address1Options[random.nextInt(address1Options.length)];
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(address1));
        element.clear();
        element.sendKeys(randomAddress1);
        System.out.println("Entered address 1: " + randomAddress1);
    }

    public void enterRandomAddress2() {
        String randomAddress2 = address2Options[random.nextInt(address2Options.length)];
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(address2));
        element.clear();
        element.sendKeys(randomAddress2);
        System.out.println("Entered address 2: " + randomAddress2);
    }
    
    public void selectRandomCountry() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(countrydropdownclick)).click();

        List<WebElement> countryOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(countrydropdown));

        int randomIndex = random.nextInt(countryOptions.size());
        WebElement selectedOption = countryOptions.get(randomIndex);
        this.selectedCountry = selectedOption.getText();
        selectedOption.click();
        System.out.println("Selected country: " + selectedCountry);
    }

    public void selectState() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        int attempts = 0;
        
        while (attempts < 3) {
            try {
                WebElement stateDropdown = wait.until(ExpectedConditions.elementToBeClickable(statedropdown));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", stateDropdown);
                
                wait.until(ExpectedConditions.visibilityOfElementLocated(statedropdownoptions));
                List<WebElement> options = driver.findElements(statedropdownoptions);
                
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
                            System.out.println("Selected state: " + stateToSelect);
                            return;
                        }
                    }
                }
                
                int randomIndex = randomRange(1, options.size() - 1);
                WebElement randomOption = options.get(randomIndex);
                this.selectedState = randomOption.getText();
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", randomOption);
                System.out.println("Selected state: " + selectedState);
                return;
                
            } catch (StaleElementReferenceException e) {
                attempts++;
                System.out.println("Stale element in selectState(), attempt: " + attempts);
                if (attempts >= 3) throw e;
            }
        }
    }
    
    public void enterRandomCity() {
        String randomCity = cityNames[random.nextInt(cityNames.length)];
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(city));
        element.clear();
        element.sendKeys(randomCity);
        System.out.println("Entered city: " + randomCity);
    }
    
    public void enterRandomZipCode() {
        String zipCodeToEnter = generateRandomZipCode();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement zipField = wait.until(ExpectedConditions.elementToBeClickable(zipcode));
            zipField.clear();
            zipField.sendKeys(zipCodeToEnter);
            ((JavascriptExecutor) driver).executeScript("arguments[0].blur();", zipField);
            System.out.println("Entered Zip Code: " + zipCodeToEnter + " for State: " + selectedState);
        } catch (Exception e) {
            System.out.println("Error entering zip code: " + e.getMessage());
            throw new RuntimeException("Failed to enter zip code into the field.", e);
        }
    }
    
    private String generateRandomZipCode() {
        if ("United States".equals(selectedCountry)) {
            // US zip code format: 5 digits
            return String.format("%05d", random.nextInt(100000));
        } else if ("United Kingdom".equals(selectedCountry)) {
            // UK postcode format: AA9A 9AA or A9A 9AA or A9 9AA or A99 9AA or AA9 9AA or AA99 9AA
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
            // Canadian postal code format: A9A 9A9
            return String.format("%c%d%c %d%c%d", 
                (char) ('A' + random.nextInt(26)),
                random.nextInt(10),
                (char) ('A' + random.nextInt(26)),
                random.nextInt(10),
                (char) ('A' + random.nextInt(26)),
                random.nextInt(10));
        } else {
            // Default: 5-digit format
            return String.format("%05d", random.nextInt(100000));
        }
    }

    public void selectRandomPhoneType() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(phonetype)).click();
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(phonetypeoptions));
        if (!options.isEmpty()) {
            int randomIndex = randomRange(1, options.size() - 1);
            String selectedType = options.get(randomIndex).getText();
            options.get(randomIndex).click();
            System.out.println("Selected phone type: " + selectedType);
        }
    }
    /*
    public void enterRandomCountryCode() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(Countrycode)).click();
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Counrtycodeselect));
        int randomIndex = randomRange(1, options.size() - 1);
        String selectedCode = options.get(randomIndex).getText();
        options.get(randomIndex).click();
        System.out.println("Selected country code: " + selectedCode);
    }
    */
    public void enterRandomCountryCode() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        int attempts = 0;
        
        while (attempts < 3) {
            try {
                // Wait for and click the country code dropdown
                WebElement countryCodeDropdown = wait.until(ExpectedConditions.elementToBeClickable(Countrycode));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", countryCodeDropdown);
                Thread.sleep(500);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", countryCodeDropdown);
                
                // Wait for options to load
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='cdk-overlay-pane']")));
                
                // Get all country code options
                List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Counrtycodeselect));
                
                if (options.isEmpty()) {
                    attempts++;
                    continue;
                }
                
                // Select based on selected country
                String targetCode = "+1 (US)";
                if ("United Kingdom".equals(selectedCountry)) {
                    targetCode = "+44 (UK)";
                } else if ("Canada".equals(selectedCountry)) {
                    targetCode = "+1 (CA)";
                } else if ("Australia".equals(selectedCountry)) {
                    targetCode = "+61 (AU)";
                } else if ("India".equals(selectedCountry)) {
                    targetCode = "+91 (IN)";
                }
                
                // Try to find matching country code
                for (WebElement option : options) {
                    if (option.getText().contains(targetCode)) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
                        Thread.sleep(300);
                        option.click();
                        System.out.println("Selected country code: " + targetCode);
                        return;
                    }
                }
                
                // Fallback: select random option (avoid first if it's placeholder)
                int randomIndex = Math.max(1, random.nextInt(options.size()));
                String selectedCode = options.get(randomIndex).getText();
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", options.get(randomIndex));
                Thread.sleep(300);
                options.get(randomIndex).click();
                System.out.println("Selected random country code: " + selectedCode);
                return;
                
            } catch (StaleElementReferenceException e) {
                attempts++;
                System.out.println("Stale element in enterRandomCountryCode(), attempt: " + attempts);
                if (attempts >= 3) throw e;
            } catch (Exception e) {
                attempts++;
                System.out.println("Error in enterRandomCountryCode(), attempt: " + attempts + ": " + e.getMessage());
                if (attempts >= 3) throw new RuntimeException("Failed to select country code after 3 attempts", e);
            }
        }
    }

    
    public void enterRandomPhoneNumber() {
        String phoneNumber = generateRandomPhoneNumber();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(phonenumber));
        element.clear();
        element.sendKeys(phoneNumber);
        System.out.println("Entered phone number: " + phoneNumber);
    }
    
    private String generateRandomPhoneNumber() {
        // Generate a 10-digit phone number
        return String.format("%03d%03d%04d", 
            random.nextInt(1000), 
            random.nextInt(1000), 
            random.nextInt(10000));
    }
    
    public void alternateGuardianInfoIconButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(AddAlternativGuardianinfoicon));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void enterRandomAlternateGuardianName() {
        String randomFirstName = firstNames[random.nextInt(firstNames.length)];
        String randomLastName = lastNames[random.nextInt(lastNames.length)];
        String guardianName = randomFirstName + " " + randomLastName;
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(AlternateGuardianName));
        element.clear();
        element.sendKeys(guardianName);
        System.out.println("Entered alternate guardian name: " + guardianName);
    }

    public void alterGuardianSelectRelationship() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(AlternateGuardianRelation)).click();
        List<WebElement> relationoptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AlternateGuardianRelationtypes));
        int randomIndex = randomRange(1, relationoptions.size() - 1);
        String selectedRelation = relationoptions.get(randomIndex).getText();
        relationoptions.get(randomIndex).click();
        System.out.println("Selected alternate guardian relation: " + selectedRelation);
    }
    
    /*
    public void alternateGuardianCountryCode() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(AlternateGuardiancountrycode)).click();
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AlternateGuardiancountryCodeSelect));
        int randomIndex = randomRange(1, options.size() - 1);
        String selectedCode = options.get(randomIndex).getText();
        options.get(randomIndex).click();
        System.out.println("Selected alternate guardian country code: " + selectedCode);
    }*/
    
    public void alternateGuardianCountryCode() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        int attempts = 0;
        
        while (attempts < 3) {
            try {
                // Wait for and click the alternate country code dropdown
                WebElement countryCodeDropdown = wait.until(ExpectedConditions.elementToBeClickable(AlternateGuardiancountrycode));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", countryCodeDropdown);
                Thread.sleep(500);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", countryCodeDropdown);
                
                // Wait for options to load
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='cdk-overlay-pane']")));
                
                // Get all country code options
                List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(AlternateGuardiancountryCodeSelect));
                
                if (options.isEmpty()) {
                    attempts++;
                    continue;
                }
                
                // Select based on selected country
                String targetCode = "+1 (US)";
                if ("United Kingdom".equals(selectedCountry)) {
                    targetCode = "+44 (UK)";
                } else if ("Canada".equals(selectedCountry)) {
                    targetCode = "+1 (CA)";
                } else if ("Australia".equals(selectedCountry)) {
                    targetCode = "+61 (AU)";
                } else if ("India".equals(selectedCountry)) {
                    targetCode = "+91 (IN)";
                }
                
                // Try to find matching country code
                for (WebElement option : options) {
                    if (option.getText().contains(targetCode)) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
                        Thread.sleep(300);
                        option.click();
                        System.out.println("Selected alternate guardian country code: " + targetCode);
                        return;
                    }
                }
                
                // Fallback: select random option
                int randomIndex = Math.max(1, random.nextInt(options.size()));
                String selectedCode = options.get(randomIndex).getText();
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", options.get(randomIndex));
                Thread.sleep(300);
                options.get(randomIndex).click();
                System.out.println("Selected random alternate guardian country code: " + selectedCode);
                return;
                
            } catch (StaleElementReferenceException e) {
                attempts++;
                System.out.println("Stale element in alternateGuardianCountryCode(), attempt: " + attempts);
                if (attempts >= 3) throw e;
            } catch (Exception e) {
                attempts++;
                System.out.println("Error in alternateGuardianCountryCode(), attempt: " + attempts + ": " + e.getMessage());
                if (attempts >= 3) throw new RuntimeException("Failed to select alternate country code after 3 attempts", e);
            }
        }
    }

    // CORRECTED PROCEED BUTTON METHOD
    public void enterRandomEmailAddress() {
        String randomEmail = generateRandomEmail();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        try {
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailaddress));
            emailField.clear();
            emailField.sendKeys(randomEmail);
            System.out.println("Entered email: " + randomEmail);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter email address: " + e.getMessage());
        }
    }
      
    public void enterRandomAlternateGuardianPhoneNumber() {
        String phoneNumber = generateRandomPhoneNumber();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        try {
            System.out.println("Attempting to enter alternate guardian phone number...");
            
            // Wait for the alternate guardian section to be fully loaded
            wait.until(ExpectedConditions.visibilityOfElementLocated(AlternateGuardianName));
            
            // Use JavaScript to find and interact with the phone number field
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            // First try to find the second phone number field using XPath
            WebElement phoneField = wait.until(ExpectedConditions.presenceOfElementLocated(AlternateGuardianPhonenumber));
            
            // Scroll into view
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", phoneField);
            Thread.sleep(1000);
            
            // Use JavaScript to set the value directly
            js.executeScript(
                "arguments[0].value = arguments[1]; " +
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); " +
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true })); " +
                "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));",
                phoneField, phoneNumber
            );
            
            System.out.println("Successfully entered alternate phone number: " + phoneNumber);
            
        } catch (Exception e) {
            System.out.println("Standard method failed, trying JavaScript fallback...");
            
            // JavaScript fallback - directly find and set the second phone number field
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript(
                    "var phoneInputs = document.querySelectorAll('input[name=\"phoneNumber\"]'); " +
                    "if (phoneInputs.length >= 2) { " +
                    "   phoneInputs[1].value = '" + phoneNumber + "'; " +
                    "   phoneInputs[1].dispatchEvent(new Event('input', { bubbles: true })); " +
                    "   phoneInputs[1].dispatchEvent(new Event('change', { bubbles: true })); " +
                    "   console.log('Set alternate phone number via JavaScript: " + phoneNumber + "'); " +
                    "}"
                );
                System.out.println("Used JavaScript fallback to set phone number: " + phoneNumber);
            } catch (Exception jsError) {
                System.out.println("JavaScript fallback also failed: " + jsError.getMessage());
                throw new RuntimeException("Failed to enter alternate guardian phone number after all attempts", jsError);
            }
        }
    }
    /*
    public void enterRandomEmailAddress() {
        String randomEmail = generateRandomEmail();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        try {
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailaddress));
            emailField.clear();
            emailField.sendKeys(randomEmail);
            System.out.println("Entered email: " + randomEmail);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter email address: " + e.getMessage());
        }
    }
    */
    
    private String generateRandomEmail() {
        String[] domains = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "example.com"};
        String name = firstNames[random.nextInt(firstNames.length)].toLowerCase();
        String domain = domains[random.nextInt(domains.length)];
        return name + random.nextInt(1000) + "@" + domain;
    }
    
    public void proceedToMedicalInfoButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        try {
            // Wait for the button to be clickable
            WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(ProccedToMedicalInfoButton));
            
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", proceedButton);
            Thread.sleep(1000);
            
            // Click using JavaScript to avoid any overlay issues
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", proceedButton);
            System.out.println("Clicked on Proceed to Medical Info button");
            
        } catch (Exception e) {
            System.out.println("Error clicking proceed button: " + e.getMessage());
            throw new RuntimeException("Failed to click on Proceed to Medical Info button", e);
        }
    }
    

    public int randomRange(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public String getSelectedCountry() {
        return selectedCountry;
    }

    public String getSelectedState() {
        return selectedState;
    }
}