
package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

public class studentspage {
    WebDriver driver;

    By studentsTitleLocator = By.xpath("//h3[normalize-space()='MeReg Workspace']");
    // adminLink = By.xpath("//div[contains(text(),'Admin')]");
    By studentsLink = By.xpath("//h4[normalize-space()='Students']");

    public studentspage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnStudents() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(studentsLink)).click();
    }
    /*
    public void clickOnStudents() {
        By studentsMenu = By.xpath("//a[.//span[normalize-space()='Students']]");
        
        // Wait until Students menu is visible and clickable
        wait.until(ExpectedConditions.visibilityOfElementLocated(studentsMenu));
        wait.until(ExpectedConditions.elementToBeClickable(studentsMenu)).click();
        
        System.out.println("✅ Clicked on Students menu successfully");
    }
  */

    public String getStudentsTitleText() throws TimeoutException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            // Wait for the title element to be visible and return it
            WebElement studentsTitleElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(studentsTitleLocator)
            );

            // Return the text safely
            String titleText = studentsTitleElement.getText().trim();
            System.out.println("Students title text found: " + titleText);
            return titleText;

        } catch (StaleElementReferenceException e) {
            // Handle stale element by re-fetching once
            WebElement refreshedElement = driver.findElement(studentsTitleLocator);
            return refreshedElement.getText().trim();
        }
    }



}

