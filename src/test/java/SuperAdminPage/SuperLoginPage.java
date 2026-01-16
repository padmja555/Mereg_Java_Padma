package SuperAdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SuperLoginPage {
    WebDriver driver;

    // Locators
    private final By emailField = By.xpath("//input[@name='UserID']");
    private final By passwordField = By.xpath("//input[@name='password']");
    //private final By signInButton = By.xpath("//a[@class='mat-focus-indicator mat-raised-button mat-button-base']");
   // private final By errorMessage = By.xpath("//div[contains(@class,'error') or contains(text(),'Invalid')]");
    private By signInButton = By.xpath("//button//span[text()=' Sign In ']/parent::button");

    public SuperLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String email) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickSignIn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
    }


    private final By errorMessage = By.xpath("//*[contains(text(),'Invalid') or contains(text(),'incorrect')]");

    public boolean isErrorMessageDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
