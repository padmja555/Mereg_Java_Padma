package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ChildinfoNegativepage {
    WebDriver driver;
    WebDriverWait wait;

    // Constructor
    public ChildinfoNegativepage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    By enrollLink = By.xpath("//h4[text()='Enrollment']");
    By submitButton = By.xpath("//button[.//span[contains(text(),'Proceed to Parent/Guardian Info')]]\r\n"
    		+ "");
    By addAnotherChildButton = By.xpath("//mat-icon[@mattooltip='Add another child']");

    By firstNameInput = By.xpath("//input[@formcontrolname='firstName']");  
    By lastNameInput = By.xpath("//input[@formcontrolname='lastName']");   
    By dobInput = By.xpath("//input[@formcontrolname='dob']");             

    By firstNameError = By.xpath("//input[@formcontrolname='firstName']/following::mat-error[1]\r\n"
    		+ "");
    By lastNameError = By.xpath("//input[@formcontrolname='lastName']/following::mat-error[1]\r\n"
    		+ "");
    By dobError = By.xpath("//input[@formcontrolname='dob']/following::mat-error[1]\r\n"
    		+ "");

    // Actions
    public void openEnrollmentSection() {
        wait.until(ExpectedConditions.elementToBeClickable(enrollLink)).click();
    }

    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).clear();
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput)).clear();
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public void enterDOB(String dob) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(dobInput)).clear();
        driver.findElement(dobInput).sendKeys(dob);
    }

    public void submitForm() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public void clickAddAnotherChild() {
        wait.until(ExpectedConditions.elementToBeClickable(addAnotherChildButton)).click();
    }

    // Error checks
    public boolean isFirstNameErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameError)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLastNameErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameError)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDOBErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(dobError)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
