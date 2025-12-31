package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class adminpage1 {
    WebDriver driver;

    By adminTitleLocator = By.xpath("//h3[normalize-space()='MeReg Workspace']");
    //By adminLink = By.xpath("//h4[normalize-space()='Admin']");
    By adminlink = By.xpath("//mat-nav-list[2]/a[1]/div/div[2]/h4");

    public adminpage1(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnAdmin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(adminlink)).click();
    }

    public String getAdminTitleText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminTitleLocator));
        return driver.findElement(adminTitleLocator).getText();
    }
}
