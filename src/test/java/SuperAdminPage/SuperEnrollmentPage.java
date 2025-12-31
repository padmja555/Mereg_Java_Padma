package SuperAdminPage;


import org.openqa.selenium.By;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SuperEnrollmentPage {
    WebDriver driver;

    By enrollmentTitleLocator = By.xpath("//h3[@class='title']");
    // adminLink = By.xpath("//div[contains(text(),'Admin')]");
    //By enrollmentLink = By.xpath("//h4[normalize-space()='Enrollment']");
    ////mat-nav-list//a[.//h4[normalize-space()='Enrollment']]
    By enrollmentLink = By.xpath("//mat-nav-list//a[.//h4[normalize-space()='Enrollment']]");

    public SuperEnrollmentPage(WebDriver driver) {
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

