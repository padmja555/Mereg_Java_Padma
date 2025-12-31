
package SuperAdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SuperAdminLoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By emailInput = By.xpath("//input[@id='login-username']");
    private By passwordInput = By.xpath("//input[@id='login-password']");
    private By signInButton = By.xpath("//a[@class='mat-focus-indicator mat-raised-button mat-button-base']");
    private By errorMessage = By.xpath("//simple-snack-bar//span | //div[contains(@class,'mat-snack-bar-container')]");

    // Constructor
    public SuperAdminLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Enter email
    public void enterEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        emailField.clear();
        emailField.sendKeys(email);
    }

    // Enter password
    public void enterPassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    // Click sign-in
    public void clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
    }

    // Perform full login action
    public boolean login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
        return isLoginSuccessful();
    }

    // Check if login is successful
    public boolean isLoginSuccessful() {
        try {
            WebElement dashboard = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h1[contains(text(),'Dashboard')] | //span[contains(text(),'Welcome to MeReg')]")
            ));
            return dashboard.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Check if error message is displayed
    public boolean isErrorMessageDisplayed() {
        try {
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            System.out.println("Error Message Text: " + errorMsg.getText());
            return errorMsg.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Get error message text
    public String getErrorMessageText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
        } catch (Exception e) {
            return "No error message found";
        }
    }
}

