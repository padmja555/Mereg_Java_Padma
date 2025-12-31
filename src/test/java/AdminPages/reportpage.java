package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class reportpage {
    WebDriver driver;

    By reportTitleLocator = By.xpath("//mat-card-title[normalize-space()='Total Payments']");
    By reportLink = By.xpath("//h4[normalize-space()='Reports']");

    public reportpage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnreportpage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(reportLink)).click();
    }

    public String getreportpageTitleText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(reportTitleLocator));
        return driver.findElement(reportTitleLocator).getText();
    }
}
