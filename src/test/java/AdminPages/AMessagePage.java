
package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

public class AMessagePage {
    WebDriver driver;

    By MessageTitleLocator = By.xpath("//h1[normalize-space()='Messages']");
    // adminLink = By.xpath("//div[contains(text(),'Admin')]");
    By MessageLink  = By.xpath("//a[.//h4[normalize-space(text())='Messages']]\r\n"
    		+ "");

    public AMessagePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        By messageTab = By.xpath("//a[.//h4[normalize-space()='Messages']]");

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(messageTab));

        element.click();
    }

    /*
    public void clickOnMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement messageLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[.//h4[normalize-space()='Messages']]")
        ));
        messageLink.click();
        wait.until(ExpectedConditions.urlContains("/messages-admin"));
        System.out.println("✅ Navigated to Messages Admin Page");
    }
    */

   /*
    public void clickOnMessage() {
        By studentsMenu = By.xpath("//a[.//span[normalize-space()='Students']]");
        
        // Wait until Students menu is visible and clickable
        wait.until(ExpectedConditions.visibilityOfElementLocated(studentsMenu));
        wait.until(ExpectedConditions.elementToBeClickable(studentsMenu)).click();
        
        System.out.println("✅ Clicked on Students menu successfully");
    }
	*/

    public String getMessageTitleText() throws TimeoutException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            // Wait for the title element to be visible and return it
            WebElement studentsTitleElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(MessageTitleLocator)
            );

            // Return the text safely
            String titleText = studentsTitleElement.getText().trim();
            System.out.println("Students title text found: " + titleText);
            return titleText;

        } catch (StaleElementReferenceException e) {
            // Handle stale element by re-fetching once
            WebElement refreshedElement = driver.findElement(MessageTitleLocator);
            return refreshedElement.getText().trim();
        }
    }



}

