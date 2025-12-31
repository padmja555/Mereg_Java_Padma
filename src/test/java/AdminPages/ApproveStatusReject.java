package AdminPages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ApproveStatusReject {

    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor
    public ApproveStatusReject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Locators
    private By filterInput = By.xpath("//mat-label[normalize-space()='Filter']/ancestor::mat-form-field//input");
    private By registrationStatusText = By.xpath("//*[contains(text(), 'Pending Approval')]");
    private By rejectReasonTextarea = By.xpath("//textarea[@placeholder='Enter reason for rejecting the application' or contains(@data-placeholder, 'reason')]");
    private By rejectButton = By.xpath("(//span[@class='mat-ripple mat-button-ripple'])[3]");
    private By approveButton = By.xpath("(//span[@class='mat-ripple mat-button-ripple'])[2]");
    private By successMessage = By.xpath("//*[contains(text(), 'Success') or contains(text(), 'Rejected')]");
    private By modalRejectButton = By.xpath("//button[contains(., 'Reject') or contains(., 'Reject Registration')]");
    private By confirmRejectButton = By.xpath("//button[contains(., 'Yes') or contains(., 'Confirm Reject')]");


    // Actions

    public void searchChildByName(String name) {
        try {
            WebElement filter = wait.until(ExpectedConditions.visibilityOfElementLocated(filterInput));
            filter.clear();
            filter.sendKeys(name);
            System.out.println("Searched for child: " + name);
            Thread.sleep(2000); // Wait for search results
        } catch (Exception e) {
            throw new RuntimeException("Failed to search for child: " + name, e);
        }
    }

    public void clickPendingButtonForChild(String childName) {
        By pendingButtonXpath = By.xpath("//tr[td[contains(., '" + childName + "')]]//button[contains(., 'Pending')]");
        
        try {
            // Wait for the child row to be visible
            By childRow = By.xpath("//tr[td[contains(., '" + childName + "')]]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(childRow));
            
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(pendingButtonXpath));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
            System.out.println("Clicked Pending button for child: " + childName);
            
            Thread.sleep(3000); // Wait for details page to load
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Pending button for " + childName, e);
        }
    }

    public boolean isPendingApprovalDisplayed() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(registrationStatusText));
            return element.isDisplayed();
        } catch (Exception e) {
            System.out.println("Pending Approval text not found: " + e.getMessage());
            return false;
        }
    }

    public void enterRejectReason(String reason) {
        try {
            WebElement reasonTextarea = wait.until(ExpectedConditions.visibilityOfElementLocated(rejectReasonTextarea));
            reasonTextarea.clear();
            reasonTextarea.sendKeys(reason);
            System.out.println("Entered reject reason: " + reason);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter reject reason", e);
        }
    }

    public void clickRejectRegistration() {
        try {
            WebElement rejectBtn = wait.until(ExpectedConditions.elementToBeClickable(rejectButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", rejectBtn);
            
            // Use JavascriptExecutor to click to avoid ElementClickInterceptedException
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", rejectBtn);
            System.out.println("Clicked Reject Registration button");
            
            // Handle confirmation if it appears
            handleRejectConfirmation();
            
            Thread.sleep(3000); // Wait for reject process to complete
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Reject Registration button", e);
        }
    }

    private void handleRejectConfirmation() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement confirmBtn = shortWait.until(ExpectedConditions.elementToBeClickable(confirmRejectButton));
            confirmBtn.click();
            System.out.println("Clicked confirmation button for rejection");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("No confirmation dialog appeared for rejection");
        }
    }

    public void clickApproveRegistration() {
        try {
            WebElement approveBtn = wait.until(ExpectedConditions.elementToBeClickable(approveButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", approveBtn);
            
            try {
                approveBtn.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", approveBtn);
            }
            System.out.println("Clicked Approve Registration button");
            
            Thread.sleep(3000); // Wait for approval process to complete
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Approve Registration button", e);
        }
    }

    public boolean isSuccessfullyRejected() {
        try {
            return wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(successMessage),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Rejected"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Success")
            ));
        } catch (Exception e) {
            System.out.println("Success/Rejected message not found: " + e.getMessage());
            return false;
        }
    }
}