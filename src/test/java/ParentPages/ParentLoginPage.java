package ParentPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ParentLoginPage {
    WebDriver driver;

    // Locators
    By emailInput = By.xpath("//input[@id='mat-input-0']");
    By passwordInput = By.xpath("//input[@id='mat-input-1']");
     By loginButton = By.xpath("//mat-card/div/div[2]/a/span[1]");
    By errorMessage = By.xpath("//simple-snack-bar | //div[contains(@class,'mat-snack-bar-container')]");

    // Constructor
    public ParentLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String email) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).clear();
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).clear();
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public boolean isErrorDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
