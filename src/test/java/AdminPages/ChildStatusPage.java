package AdminPages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ChildStatusPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor
    public ChildStatusPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    // Locators
    private By filterInput = By.xpath("//mat-label[normalize-space()='Filter']/ancestor::mat-form-field//input");
    private By searchButton = By.xpath("//app-dashboardhome//table/tbody/tr[3]/td[position()=2]");
    //private By searchButton = By.xpath("(//tr[td[contains(normalize-space(.), 'Saanvi')]])[1]");
    ////tbody/tr[3]/td[2]

    //(//tr[td[contains(normalize-space(.), 'Reyansh')]])[1]
    private By registrationStatusText = By.xpath("//h2[contains(text(), 'Pending Approval')]");
    //private By approveRegistrationButton = By.xpath("//app-user-registration-details/div/div/div/div[2]/div/button[1]/span[1]");
    private By approveRegistrationButton = By.xpath("//app-user-registration-details/div/div/div/div[2]/div/button[1]/span[2]");

    // Actions

    public void searchChildByName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterInput)).clear();
        driver.findElement(filterInput).sendKeys(name);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }
    /*
    public void clickPendingButtonForChild(String childName) {
        By pendingButton = By.xpath("//tr[td[normalize-space()='" + childName + "']]//button[contains(text(), 'Pending')]");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(pendingButton));
        button.click();
    }
*/
    public void clickPendingButtonForChild(String childName) {
        // Build dynamic xpath
        By pendingButton = By.xpath("//tr[td[normalize-space()='" + childName + "']]//button[contains(normalize-space(), 'Pending')]");

        // Wait for the row with the child to appear first
        By childRow = By.xpath("//tr[td[normalize-space()='" + childName + "']]");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(childRow));
        } catch (Exception e) {
            System.out.println("Child name '" + childName + "' row not visible in table");
            throw e;
        }

        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(pendingButton));
            System.out.println("Clicking Pending button for " + childName);
            btn.click();
        } catch (Exception e) {
            System.out.println("Unable to click Pending button for " + childName + ". Trying fallback...");
            // Fallback: maybe use JS click
            WebElement btnFallback = driver.findElement(pendingButton);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btnFallback);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", btnFallback);
        }
    }

    public boolean isPendingApprovalDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(registrationStatusText)).isDisplayed();
    }
    /*
    public void clickApproveRegistration() {
        wait.until(ExpectedConditions.elementToBeClickable(approveRegistrationButton)).click();
    }
    */
    public void clickApproveRegistration() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        By approveButtonLocator = By.xpath("(//span[@class='mat-ripple mat-button-ripple'])[2]");

        WebElement approveButton = wait.until(ExpectedConditions.elementToBeClickable(approveButtonLocator));

        // Scroll to make sure it's visible
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", approveButton);

        // Use JavaScript click as fallback
        try {
            approveButton.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Normal click failed, trying JavaScript click...");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", approveButton);
        }
    }

}
