package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Onlinecardpayment {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators for the Payment Summary Page
    private final By paymentSummaryTitle = By.xpath("//div[contains(@class, 'payment-summary-card')]//h2");
    private final By paySecurelyButton = By.xpath("//button[contains(text(),'Pay')]");
    By paymentbackButton= By.xpath("//button[@class=\"mat-focus-indicator back-to-dashboard-btn mat-stroked-button mat-button-base\"]");

    // Locators for the Stripe Card Payment Form (inside an iframe)
    private final By stripeCardIframe = By.xpath("//iframe[contains(@title, 'secure card payment input')]");
    private final By cardNumberInput = By.xpath("//input[@name='cardnumber']");
    private final By cardExpiryInput = By.xpath("//input[@name='exp-date']");
    private final By cardCvcInput = By.xpath("//input[@name='cvc']");
    // This locator might be incorrect. It's often on the main page.
    private final By finalPayButton = By.xpath("//button[@type='submit' and contains(., 'Pay')]"); 

    // Final Success Message Locator
    private final By successMessage = By.xpath("//h1[normalize-space()='Payment successful']");

    public Onlinecardpayment(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void verifyPaymentSummaryPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(paymentSummaryTitle));
        System.out.println("Payment Summary page is displayed.");
    }

    public void clickPaySecurelyButton() {
        wait.until(ExpectedConditions.elementToBeClickable(paySecurelyButton)).click();
        System.out.println("Clicked on 'Pay Securely' button.");
    }
    
    /*
    public void enterCardDetails(String cardNumber, String expiryDate, String cvc) {
        // Switch to the Stripe iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(stripeCardIframe));
        
        // Enter card number
        WebElement cardNumberField = wait.until(ExpectedConditions.visibilityOfElementLocated(cardNumberInput));
        cardNumberField.sendKeys(cardNumber);

        // Enter expiry date
        WebElement expiryField = driver.findElement(cardExpiryInput);
        expiryField.sendKeys(expiryDate);

        // Enter CVC
        WebElement cvcField = driver.findElement(cardCvcInput);
        cvcField.sendKeys(cvc);
        
        System.out.println("Entered card details.");
        
        // Switch back to the main page
        driver.switchTo().defaultContent();
    }
    
    // This method is likely incorrect, as the 'Pay' button is often outside the iframe.
    // The previous enterCardDetails method already switched back to the main page.
    public void clickFinalPayButton() {
        wait.until(ExpectedConditions.elementToBeClickable(finalPayButton)).click();
        System.out.println("Clicked final 'Pay' button.");
    }
    
    public void verifyPaymentSuccess() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        System.out.println("Payment successful message is displayed.");
    }
}
    */
    public void clickbackButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(paymentbackButton));
        button.click();
        System.out.println("Clicked on Pay Securely button");
    }
}