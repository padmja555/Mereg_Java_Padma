package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaymentSummaryonliepage {
    WebDriver driver;
    WebDriverWait wait;

    // Locators
    private By pageTitle = By.xpath("//div[contains(@class,'Text') and contains(text(),'Pay with card')]");
    private By paymentEmail = By.xpath("//input[contains(@class,'CheckoutInput') and @type='text' and @autocomplete='email']");
    private By cardNumber = By.xpath("//input[@type='text' and @autocomplete='cc-number' and contains(@id,'cardNumber')]");
    private By cardExpiry = By.xpath("//input[@type='text' and @autocomplete='cc-exp' and contains(@id,'cardExpiry')]");
    private By cardCVC = By.xpath("//input[@type='text' and @autocomplete='cc-csc' and contains(@id,'cardCvc')]");
    private By cardHolderName = By.xpath("//input[@type='text' and @autocomplete='cc-name' and contains(@id,'billingName')]");
    private By countryDropdown = By.xpath("//select[@id='billingCountry']");
    private By zipcodeField = By.xpath("//input[@id='billingPostalCode']");
    //private By payButton = By.xpath("//button[contains(text(),'Pay')]");
    private By payButton = By.xpath("//div[@class='SubmitButton-IconContainer']");
    ////div[@class='SubmitButton-IconContainer']

    // Cash/Cheque-specific fields
    private By cashrecepitnum = By.xpath("//input[@formcontrolname='receiptNumber']");
    private By chequenum = By.xpath("//input[@formcontrolname='chequeNumber']");

    public PaymentSummaryonliepage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // ✅ Get online payment page title
    public String getPageTitleText() {
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
        return title.getText().trim();
    }

    // ✅ Select country from dropdown
    public void selectCountry(String countryName) {
        WebElement countryDropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(countryDropdown));
        Select countrySelect = new Select(countryDropdownElement);
        countrySelect.selectByVisibleText(countryName.trim());
        System.out.println("Selected Country: " + countryName);
    }

    // ✅ Check if ZIP/Postal field is displayed (for US/UK)
    public boolean isZipcodeFieldDisplayed() {
        try {
            WebElement zipcodeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(zipcodeField));
            return zipcodeElement.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ✅ Fill credit card & billing info (ZIP if visible)
    public void enterCardDetails(String email, String number, String expiry, String cvc, String holderName, String country, String zipcodeOrPostal) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(paymentEmail)).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardNumber)).sendKeys(number);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardExpiry)).sendKeys(expiry);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardCVC)).sendKeys(cvc);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardHolderName)).sendKeys(holderName);

        // Select country
        selectCountry(country);

        // If ZIP field visible → enter
        if (isZipcodeFieldDisplayed()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(zipcodeField)).sendKeys(zipcodeOrPostal);
        }

        // Optional delay to ensure Pay button is enabled
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // ✅ Click on Pay button
    public void clickPayButton() {
        WebElement pay = wait.until(ExpectedConditions.elementToBeClickable(payButton));
        pay.click();
    }
    //WebElement pay = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[type='submit']")));
    //System.out.println("Pay button enabled: " + pay.isEnabled());
    //pay.click();


    // ✅ Enter receipt number for Cash
    public void enterCashReceiptNumber(String receiptNumber) {
        WebElement receipt = wait.until(ExpectedConditions.visibilityOfElementLocated(cashrecepitnum));
        receipt.clear();
        receipt.sendKeys(receiptNumber);
        System.out.println("Entered cash receipt number: " + receiptNumber);
    }

    // ✅ Enter cheque number for Cheque
    public void enterChequeNumber(String chequeNumber) {
        WebElement cheque = wait.until(ExpectedConditions.visibilityOfElementLocated(chequenum));
        cheque.clear();
        cheque.sendKeys(chequeNumber);
        System.out.println("Entered cheque number: " + chequeNumber);
    }
}
