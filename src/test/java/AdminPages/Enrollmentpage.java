package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Enrollmentpage {
    WebDriver driver;

    By enrollmentTitleLocator = By.xpath("//h3[@class='title']");
    // adminLink = By.xpath("//div[contains(text(),'Admin')]");
    By enrollmentLink = By.xpath("//h4[normalize-space()='Enrollment']");

    public Enrollmentpage(WebDriver driver) {
        this.driver = driver;
    }

    
    public void clickOnEnrollment() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(enrollmentLink)).click();
    }

    public String getEnrollmentTitleText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(enrollmentTitleLocator));
        return driver.findElement(enrollmentTitleLocator).getText();
    }

	public void clickonadmin1() {

		
	}
}
