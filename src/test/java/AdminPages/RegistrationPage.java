package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model for the Registration Page and its associated confirmation modal.
 */
public class RegistrationPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators for the registration page elements
    @FindBy(xpath = "//button[@class='mat-focus-indicator buttonproceed mt-3 mat-raised-button mat-button-base']")
    private WebElement completeRegistrationButton;
    
    // Locators for the first confirmation modal
    @FindBy(xpath = "//div[contains(@class, 'modal')]//h2[contains(text(), 'Complete registration')]")
    private WebElement confirmationModalHeader;

    //@FindBy(xpath = "//div[contains(@class, 'modal')]//button[normalize-space()='Yes']")
    @FindBy(xpath = "//app-registration-confirmation/div[2]/div[2]/button[1]")
    private WebElement yesButton;
    
    // Locators for the second confirmation modal after submitting
    @FindBy(xpath = "//div[contains(@class, 'modal')]//h2[contains(text(), 'Complete registration')]")
    private WebElement finalConfirmationModalHeader;
    
    //@FindBy(xpath = "//button[normalize-space()='Return to Dashboard']")
    @FindBy(xpath = "//button[@class='mat-focus-indicator buttonproceed mat-raised-button mat-button-base']")
    private WebElement returnToDashboardButton;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Clicks the main registration button and handles the confirmation modal flow.
     */
    public void clickRegisterAndConfirm() {
        System.out.println("Clicking on Complete Registration button...");
        try {
            // Click the complete registration button
            wait.until(ExpectedConditions.elementToBeClickable(completeRegistrationButton)).click();
            System.out.println("Register button clicked. Waiting for the first modal.");
            
            // Wait for the "Are you sure..." modal and click "Yes"
            wait.until(ExpectedConditions.visibilityOf(yesButton)).click();
            System.out.println("First confirmation modal is displayed. Clicking 'Yes'.");
            
            // Wait for the second "Registration Complete" modal and the "Return to Dashboard" button
            wait.until(ExpectedConditions.visibilityOf(returnToDashboardButton));
            System.out.println("Second confirmation modal is displayed. You can now click 'Return to Dashboard'.");
            
        } catch (Exception e) {
            System.err.println("Failed to click the Register button or handle the modals: " + e.getMessage());
            // Print page source for debugging purposes
            System.err.println("Current page source: \n" + driver.getPageSource());
            throw new RuntimeException("Failed to complete the final registration step.", e);
        }
    }

    /**
     * Verifies that the final confirmation page is loaded by checking for the "Return to Dashboard" button.
     * @return true if the button is visible, false otherwise.
     */
    public boolean verifyFinalConfirmationModalIsLoaded() {
        System.out.println("Waiting for final confirmation modal to load...");
        try {
            return wait.until(ExpectedConditions.visibilityOf(returnToDashboardButton)).isDisplayed();
        } catch (Exception e) {
            System.err.println("Final confirmation modal did not load: " + e.getMessage());
            throw new RuntimeException("Could not find the final confirmation modal.", e);
        }
    }
    
    /**
     * Clicks the "Return to Dashboard" button to complete the registration flow.
     */
    public void clickReturnToDashboardButton() {
        System.out.println("Clicking on 'Return to Dashboard' button...");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(returnToDashboardButton)).click();
            System.out.println("'Return to Dashboard' button clicked successfully.");
        } catch (Exception e) {
            System.err.println("Failed to click the 'Return to Dashboard' button: " + e.getMessage());
            throw new RuntimeException("Failed to click 'Return to Dashboard' button.", e);
        }
    }
};

