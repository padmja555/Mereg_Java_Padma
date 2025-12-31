package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OnlinePaymentPage {
    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By pageTitle = By.xpath("//div[contains(@class,'Text') and contains(text(),'Pay with card')]");
    By paymentEmail = By.xpath("//input[contains(@class,'CheckoutInput') and @type='text' and @autocomplete='email']");
    By cardNumber = By.xpath("//input[@type='text' and @autocomplete='cc-number' and contains(@id,'cardNumber')]");
    By cardExpiry = By.xpath("//input[@type='text' and @autocomplete='cc-exp' and contains(@id,'cardExpiry')]");
    By cardCVC = By.xpath("//input[@type='text' and @autocomplete='cc-csc' and contains(@id,'cardCvc')]");
    By cardHolderName = By.xpath("//input[@type='text' and @autocomplete='cc-name' and contains(@id,'billingName')]");
    By countryDropdown = By.xpath("//select[@id='billingCountry']");
    By payButton = By.xpath("//button[contains(text(),'Pay')]");

    public OnlinePaymentPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Get page title text
    public String getPageTitleText() {
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
        return title.getText().trim();
    }

    // Fill card details
    public void enterCardDetails(String email, String number, String expiry, String cvc, String holderName, String country) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(paymentEmail)).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardNumber)).sendKeys(number);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardExpiry)).sendKeys(expiry);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardCVC)).sendKeys(cvc);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardHolderName)).sendKeys(holderName);

        WebElement countryElement = wait.until(ExpectedConditions.elementToBeClickable(countryDropdown));
        Select countrySelect = new Select(countryElement);
        countrySelect.selectByVisibleText(country.trim());
    }

    // Click Pay button
    public void clickPayButton() {
    	WebElement pay = wait.until(ExpectedConditions.elementToBeClickable(payButton));
    	((JavascriptExecutor) driver).executeScript("arguments[0].click();", pay);

}
}