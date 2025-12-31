package SuperAdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SuperLogoutPage {
    WebDriver driver;
    
    By emailInput = By.xpath("//input[@id='mat-input-0']");
    By passwordInput = By.xpath("//input[@id='mat-input-1']");
     By loginButton = By.xpath("//mat-card/div/div[2]/a/span[1]");

    By logoutTitleLocator = By.xpath("//h1[normalize-space()='Welcome to MeReg']");
    By logoutLink = By.xpath("//h4[normalize-space()='Logout']");

    public SuperLogoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnlogoutpage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

    public String getreportpageTitleText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoutTitleLocator));
        return driver.findElement(logoutTitleLocator).getText();
    }
}

