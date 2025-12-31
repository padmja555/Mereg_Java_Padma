package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

public class ChildpaymentPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By paymentMenu = By.xpath("//h4[normalize-space()='Payment']");
    // Updated locator for the guardian dropdown based on the provided image
    private By guardianDropdown = By.xpath("//input[@data-placeholder='Select Guardian']");
    private By guardianOptions = By.xpath("//mat-option//span");
    // Updated locator for the child dropdown
    ////mat-select/div/div[1]/span
    //private By childDropdown = By.xpath("//mat-select[@id='mat-select-2']");
    private By childDropdown = By.xpath("//mat-select/div/div[1]/span");

    private By childOptions = By.xpath("//div[@role='listbox']//mat-option//span");
    private By amountToBePaidField = By.xpath("(//input[@type='number'])[1]");
    private By amountToBePaidField1 = By.xpath("(//input[@type='number'])[2]");
    By pageEle          =       By.xpath("//body");
    private By proceedPaymentButton = By.xpath("//button[normalize-space()='Proceed to payment']");
    ////mat-sidenav-content/app-subscriptioninfo/div[3]/button
    
    
    		
    // Locators for country selection and zipcode
    private By countryDropdown = By.xpath("//select[@id='billingCountry']");
    private By zipcodeField = By.xpath("//input[@id='billingPostalCode']");

    // Constructor
    public ChildpaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    /**
     * Navigate to Payment menu
     */
    public void goToPaymentPage() {
        WebElement paymentMenu = wait.until(ExpectedConditions.elementToBeClickable(this.paymentMenu));
        paymentMenu.click();
    }
    
    /**
     * Select Guardian dynamically
     */
    public void selectGuardian(String guardianName) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(guardianDropdown));
        dropdown.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(guardianOptions));
        List<WebElement> guardians = driver.findElements(guardianOptions);

        boolean found = false;
        for (WebElement guardian : guardians) {
            if (guardian.getText().equalsIgnoreCase(guardianName)) {
                guardian.click();
                System.out.println("Selected Guardian: " + guardianName);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new RuntimeException("Guardian not found: " + guardianName);
        }
    }

    /**
     * Select Child dynamically
     */
    public void selectChild(String childName) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(childDropdown));
        dropdown.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(childOptions));
        List<WebElement> children = driver.findElements(childOptions);

        boolean found = false;
        for (WebElement child : children) {
            if (child.getText().equalsIgnoreCase(childName)) {
                child.click();
                System.out.println("Selected Child: " + childName);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new RuntimeException("Child not found: " + childName);
        }
    }

    /**
     * Get Amount to be paid
     */
    public double getAmountToBePaid() {
        WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(amountToBePaidField));
        String amountText = amountField.getAttribute("value").trim();
        return amountText.isEmpty() ? 0.0 : Double.parseDouble(amountText);
    }
    public double getAmountToBePaid1() {
        WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(amountToBePaidField1));
        String amountText = amountField.getAttribute("value").trim();
        return amountText.isEmpty() ? 0.0 : Double.parseDouble(amountText);
    }
    /**
     * Click Proceed Payment
     */
    public void clickProceedPayment() {
        WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(proceedPaymentButton));
        proceedButton.click();
        System.out.println("Clicked 'Proceed to payment'.");
    }

    /**
     * Select country from dropdown
     */
    public void selectCountry(String countryName) {
        WebElement countryDropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(countryDropdown));
        Select countrySelect = new Select(countryDropdownElement);
        countrySelect.selectByVisibleText(countryName);
        System.out.println("Selected Country: " + countryName);
    }

    /**
     * Check if zipcode field is displayed
     */
    public boolean isZipcodeFieldDisplayed() {
        try {
            WebElement zipcodeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(zipcodeField));
            return zipcodeElement.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
