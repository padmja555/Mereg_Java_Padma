
package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class EnrollmentChildPage {
    WebDriver driver;

    By enrollmentTitleLocator = By.xpath("//h3[normalize-space()='MeReg Workspace']");
    //By adminLink = By.xpath("//h4[normalize-space()='Admin']");
    By adminlink = By.xpath("//mat-nav-list[2]/a[1]/div/div[2]/h4");
    By indiduallink = By.xpath("(//button[@class='mat-focus-indicator button-proceed mat-raised-button mat-button-base'])[1]");

    public EnrollmentChildPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnAdmin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(adminlink)).click();
    }

    public void clickIndiduallink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(indiduallink)).click();
    }
    public String getEnrollmentTitleText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(enrollmentTitleLocator));
        return driver.findElement(enrollmentTitleLocator).getText();
    }
}

