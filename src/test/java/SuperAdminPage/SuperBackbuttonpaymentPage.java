package SuperAdminPage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SuperBackbuttonpaymentPage {
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
    By paybackbuttontitle = By.xpath("//div[contains(@class,'Text') and contains(text(),'Pay with card')]");

    By paybackButton = By.xpath("//a[@title=\"Mereg-hotfix sandbox\"]");

    public SuperBackbuttonpaymentPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Get page title text
    public String getPageTitleText() {
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
        return title.getText().trim();
    }

    // Fill card details
    public void enterCardDetails(String email, String number, String expiry, String cvc, String holderName, String country, String string) {
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
    public void clickbackButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(paybackButton));
        button.click();
        System.out.println("Clicked on Pay Securely button");
    }
    public String getPagebackbuttonTitleText() {
  	    // Switch to the top-level iframe first
  	    WebElement outerFrame = driver.findElement(By.tagName("iframe"));
  	    driver.switchTo().frame(outerFrame);

  	    // Then switch to inner iframe (if nested)
  	    WebElement innerFrame = driver.findElement(By.tagName("iframe"));
  	    driver.switchTo().frame(innerFrame);

  	    // Wait for presence first, then visibility
  	    By payWithCardLocator = By.xpath("//div[contains(text(),'Pay with card')]");
  	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

  	    WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(payWithCardLocator));
  	    wait.until(ExpectedConditions.visibilityOf(titleElement));

  	    String titleText = titleElement.getText().trim();

  	    // Switch back to main content
  	    driver.switchTo().defaultContent();

  	    return titleText;
  	}

}
