package SuperAdminPage;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SuperOnlinePaymentPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators for the Payment Summary Page
    private final By paymentSummaryTitle = By.xpath("//div[contains(@class, 'payment-summary-card')]//h2");
    private final By paySecurelyButton = By.xpath("//button[contains(text(),'Pay')]");

    // Locators for the Stripe Card Payment Form (inside an iframe)
    private final By stripeCardIframe = By.xpath("//iframe[contains(@title, 'secure card payment input')]");
    private final By cardNumberInput = By.xpath("//input[@name='cardnumber']");
    private final By cardExpiryInput = By.xpath("//input[@name='exp-date']");
    private final By cardCvcInput = By.xpath("//input[@name='cvc']");
    // This locator might be incorrect. It's often on the main page.
    private final By finalPayButton = By.xpath("//button[@type='submit' and contains(., 'Pay')]"); 
    By paymentbackButton= By.xpath("//button[@class=\"mat-focus-indicator back-to-dashboard-btn mat-stroked-button mat-button-base\"]");

    // Final Success Message Locator
    private final By successMessage = By.xpath("//h1[normalize-space()='Payment successful']");

    public SuperOnlinePaymentPage(WebDriver driver, WebDriverWait wait) {
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
    public void clickbackButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(paymentbackButton));
        button.click();
        System.out.println("Clicked on Pay Securely button");
    }
    
}