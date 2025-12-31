package AdminPages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PendindapprovalbackbuttonPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor
    public PendindapprovalbackbuttonPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    // Locators
    private By filterInput = By.xpath("//mat-label[normalize-space()='Filter']/ancestor::mat-form-field//input");
    private By pendingButton = By.xpath("//tr[td[normalize-space()='Johnson']]//button[contains(normalize-space(), 'Pending')]");
    private By registrationStatusText = By.xpath("//h2[contains(text(), 'Pending Approval')]");
    private By backToDashboardButton = By.xpath("//button[contains(@class,'back-to-dashboard-btn')]");
    private By childLastNameHeader = By.xpath("//div[@class='mat-sort-header-content' and normalize-space()='Child Last Name']");

    // Actions
    public void searchChildByName(String name) {
        // Clear and enter text into the filter field
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterInput)).clear();
        driver.findElement(filterInput).sendKeys(name);
        
        // Wait for the search results to load. A reliable way is to wait for a specific child's name to appear in the table.
        // For example, you can wait for the first name "Johnson" to be visible after the filter is applied.
        By childNameInTable = By.xpath("//td[normalize-space()='" + name + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(childNameInTable));
    }

    public void clickPendingButtonForChild(String childName) {
        By pendingButtonForChild = By.xpath("//tr[td[normalize-space()='" + childName + "']]//button[contains(normalize-space(), 'Pending')]");
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(pendingButtonForChild));
            btn.click();
        } catch (Exception e) {
            WebElement btnFallback = driver.findElement(pendingButtonForChild);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btnFallback);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", btnFallback);
        }
    }

    public boolean isPendingApprovalDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(registrationStatusText)).isDisplayed();
    }

    public void clickBackToDashboard() {
        try {
            WebElement backButton = wait.until(ExpectedConditions.elementToBeClickable(backToDashboardButton));
            backButton.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Normal click failed, trying JavaScript click...");
            WebElement backButtonFallback = driver.findElement(backToDashboardButton);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backButtonFallback);
        }
    }
}