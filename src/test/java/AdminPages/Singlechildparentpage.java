package AdminPages;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Singlechildparentpage {
    WebDriver driver;

    // Locators
    By parentguardianHeaderText = By.xpath("//h2[text()='Parent/Guardian info']");
    By firstname = By.xpath("(//*[@name='firstName'])[2]");
    By middlename = By.xpath("(//*[@name='middleName'])[2]");
    By lastname = By.xpath("(//*[@name='lastName'])[2]");
    By relationshipdropdown = By.xpath("//mat-select[@name='relationshipWithChild']");
    By address1 = By.xpath("(//input[@name='addressLine1'])[1]");
    By address2 = By.xpath("(//input[@formcontrolname='addressLine2'])[1]");
    By countrydropdownclick = By.xpath("(//mat-select[@name='country'])[1]");
    By statedropdown = By.xpath("(//mat-select[@name='state'])[1]");
    By city = By.xpath("(//input[@name='city'])[1]");
    By zipcode = By.xpath("(//input[@name='zipCode'])[1]");
    By phonetype = By.xpath("(//mat-select[@name='phoneType'])[1]");
    By countryCodeDropdown = By.xpath("(//mat-select[@name='countryCode'])[1]");
    public By phonenumber = By.xpath("(//input[@name='phoneNumber'])[1]");
    By emailAddress = By.xpath("(//input[@name='email'])[1]");
    By AddAlternativGuardianinfoicon = By.xpath("(//mat-icon[text()='add_circle_outline'])[2]");
    By AlternateGuardianName = By.xpath("//input[@name='name']");
    By AlternateGuardianRelation = By.xpath("(//mat-select[@name='relationshipWithChild'])[2]");
    By AlternateGuardianCountryCode = By.xpath("(//mat-select[@name='countryCode'])[2]");
    By AlternateGuardianPhonenumber = By.xpath("(//input[@name='phoneNumber'])[2]");
    By ProccedToMedicalInfoButton = By.xpath("(//button[@name='started'])[4]");

    // Generic option locator for all dropdowns
    By dropdownOptions = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");

    private String selectedCountry;

    public Singlechildparentpage(WebDriver driver) {
        this.driver = driver;
    }

    public String VerifyParentGuardianPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(parentguardianHeaderText));
        return driver.findElement(parentguardianHeaderText).getText();
    }

    public void enterFirstName(String firstName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstname)).sendKeys(firstName);
    }

    public void enterMiddleName(String middleName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(middlename)).sendKeys(middleName);
    }

    public void enterLastName(String lastName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastname)).sendKeys(lastName);
    }

    public void selectRelationship() throws InterruptedException {
        clickAndSelectRandomOption(relationshipdropdown);
    }

    public void EnterAddress1(String address) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(address1)).sendKeys(address);
    }

    public void EnterAddress2(String secondAddress) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(address2)).sendKeys(secondAddress);
    }

    public String selectRandomCountry() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(countrydropdownclick)).click();
        
        // Wait for dropdown to appear
        Thread.sleep(1000);
        
        // Select random option
        selectRandomDropdownOption();
        
        // Get the selected country text
        WebElement countrySelect = wait.until(ExpectedConditions.visibilityOfElementLocated(countrydropdownclick));
        selectedCountry = countrySelect.getText().trim();
        System.out.println("Selected country: " + selectedCountry);
        
        return selectedCountry;
    }

    public void selectStateBasedOnCountry() throws InterruptedException {
        clickAndSelectRandomOption(statedropdown);
    }

    public void enterCityBasedOnCountry() {
        String cityName;
        if (isUKCountry()) {
            String[] ukCities = {"London", "Manchester", "Birmingham", "Liverpool", "Glasgow", "Edinburgh"};
            cityName = ukCities[new Random().nextInt(ukCities.length)];
        } else {
            String[] usCities = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "Dallas"};
            cityName = usCities[new Random().nextInt(usCities.length)];
        }
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cityField = wait.until(ExpectedConditions.elementToBeClickable(city));
        safeClearAndSendKeys(cityField, cityName);
    }

    public void enterZipCodeBasedOnCountry() {
        String zip;
        if (isUKCountry()) {
            // UK postcode format
            String[] areas = {"SW", "NW", "SE", "EC", "WC", "W", "E", "N", "S"};
            String area = areas[new Random().nextInt(areas.length)];
            String numbers = String.valueOf(100 + new Random().nextInt(900));
            String letters = String.valueOf((char)(65 + new Random().nextInt(26))) + 
                            String.valueOf((char)(65 + new Random().nextInt(26)));
            zip = area + numbers + " " + letters;
        } else {
            // US zipcode format
            zip = String.format("%05d", new Random().nextInt(100000));
        }
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement zipField = wait.until(ExpectedConditions.elementToBeClickable(zipcode));
        safeClearAndSendKeys(zipField, zip);
    }

    public void selectPhoneType() throws InterruptedException {
        clickAndSelectRandomOption(phonetype);
    }

    public void selectCountryCode() throws InterruptedException {
        clickAndSelectRandomOption(countryCodeDropdown);
    }

    public void enterPhoneNumber(String phone) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement phoneField = wait.until(ExpectedConditions.visibilityOfElementLocated(phonenumber));
        safeClearAndSendKeys(phoneField, phone);
    }

    public void enterEmailAddress(String email) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailAddress));
        safeClearAndSendKeys(emailField, email);
    }

    public void AlternateGuardianinfoIconButton() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Try to find and click the add button
            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(AddAlternativGuardianinfoicon));
            
            // Scroll to the button first
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addButton);
            Thread.sleep(500);
            
            // Click using JavaScript for reliability
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);
            
            System.out.println("Successfully clicked alternate guardian add button");
            
            // Wait for the section to expand
            Thread.sleep(2000);
            
        } catch (Exception e) {
            System.out.println("Error clicking alternate guardian button: " + e.getMessage());
            throw new RuntimeException("Failed to click alternate guardian add button", e);
        }
    }

    public void enterAlternateGuardianName(String GuardianName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(AlternateGuardianName)).sendKeys(GuardianName);
    }

    public void AlterGuardianselectRelationship() throws InterruptedException {
        clickAndSelectRandomOption(AlternateGuardianRelation);
    }

    public void selectAlternateGuardianCountryCode() throws InterruptedException {
        clickAndSelectRandomOption(AlternateGuardianCountryCode);
    }

    public void AlterGuardiansPhoneNumber(String phone) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement phoneField = wait.until(ExpectedConditions.visibilityOfElementLocated(AlternateGuardianPhonenumber));
        safeClearAndSendKeys(phoneField, phone);
    }

    public void ProceedToMedicalInfoButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(ProccedToMedicalInfoButton)).click();
    }

    // Helper method to click dropdown and select random option
    private void clickAndSelectRandomOption(By dropdownLocator) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator)).click();
        
        // Wait for dropdown to appear
        Thread.sleep(1000);
        
        selectRandomDropdownOption();
    }

    // Helper method to select random option from visible dropdown
    private void selectRandomDropdownOption() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Wait for options to be present
        List<WebElement> options = wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(dropdownOptions)
        );
        
        if (!options.isEmpty()) {
            int randomIndex = new Random().nextInt(options.size());
            try {
                options.get(randomIndex).click();
            } catch (Exception e) {
                System.out.println("Error clicking option, retrying...");
                // If failed, wait and try again with fresh elements
                options = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(dropdownOptions)
                );
                if (!options.isEmpty()) {
                    randomIndex = new Random().nextInt(options.size());
                    options.get(randomIndex).click();
                }
            }
        }
    }

    private boolean isUKCountry() {
        return selectedCountry != null && 
               (selectedCountry.equalsIgnoreCase("United Kingdom") || 
                selectedCountry.equalsIgnoreCase("UK") ||
                selectedCountry.equalsIgnoreCase("Great Britain"));
    }

    private void safeClearAndSendKeys(WebElement element, String text) {
        try {
            // Use JavaScript to clear and set value
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value = '';", element);
            js.executeScript("arguments[0].value = arguments[1];", element, text);
            js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", element);
            
        } catch (Exception e) {
            System.out.println("JavaScript clear/send failed, trying manual approach: " + e.getMessage());
            
            // Fallback to manual approach
            try {
                element.click();
                element.clear();
                element.sendKeys(text);
            } catch (Exception ex) {
                System.out.println("Manual approach also failed: " + ex.getMessage());
                throw ex;
            }
        }
    }
}