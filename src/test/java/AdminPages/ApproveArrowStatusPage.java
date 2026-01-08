package AdminPages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ApproveArrowStatusPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Corrected Locators
    private By filterInput = By.xpath("//mat-label[normalize-space()='Filter']/ancestor::mat-form-field//input");
    private By paginationArrow = By.xpath("//button[@aria-label='Next page']");
    private By approveButton = By.xpath("//button[contains(@class, 'btn-success') and contains(., 'Approve')]");
    private By modalApproveButton = By.xpath("//div[contains(@class, 'modal')]//button[contains(., 'Approve')]");
    private By confirmApproveButton = By.xpath("//button[contains(., 'Yes') or contains(., 'Confirm')]");
    private By pendingApprovalText = By.xpath("//*[contains(text(), 'Pending Approval')]");
    private By successMessage = By.xpath("//*[contains(text(), 'Success') or contains(text(), 'Approved')]");
    private By registrationStatus = By.xpath("//*[contains(text(), 'Registration Status')]");

    public ApproveArrowStatusPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void navigateToDashboard() {
        driver.get("https://mereg-dev.netlify.app/navigation-home/dashboard");
        System.out.println("Navigated to Dashboard.");
    }

    public void clickPaginationArrow() {
        try {
            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(paginationArrow));
            nextButton.click();
            System.out.println("Clicked on the next pagination arrow.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to click pagination arrow.", e);
        }
    }

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
        By pendingButton = By.xpath("//tr[td[contains(., '" + childName + "')]]//button[contains(., 'Pending')]");
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(pendingButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
            System.out.println("Clicked Pending button for child: " + childName);
            
            // Wait for page to load after clicking
            Thread.sleep(4000);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Pending button for " + childName, e);
        }
    }
    
    public boolean isPendingApprovalDisplayed() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(pendingApprovalText));
            return element.isDisplayed();
        } catch (Exception e) {
            System.out.println("Pending Approval text not found: " + e.getMessage());
            return false;
        }
    }
    
    public void clickApproveRegistration() {
        try {
            // Wait for page to load completely
            Thread.sleep(3000);
            
            // Try multiple approve button selectors
            try {
                WebElement approveBtn = wait.until(ExpectedConditions.elementToBeClickable(approveButton));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", approveBtn);
                approveBtn.click();
                System.out.println("Clicked 'Approve Registration' button.");
            } catch (Exception e) {
                // Try modal approve button if first one fails
                WebElement modalApproveBtn = wait.until(ExpectedConditions.elementToBeClickable(modalApproveButton));
                modalApproveBtn.click();
                System.out.println("Clicked modal 'Approve Registration' button.");
            }
            
            // Handle confirmation if it appears
            handleConfirmation();
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Approve Registration button.", e);
        }
    }

    private void handleConfirmation() {
        try {
            // Wait briefly for confirmation dialog
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement confirmBtn = shortWait.until(ExpectedConditions.elementToBeClickable(confirmApproveButton));
            confirmBtn.click();
            System.out.println("Clicked confirmation button.");
            Thread.sleep(2000);
        } catch (Exception e) {
            // No confirmation dialog, continue
            System.out.println("No confirmation dialog appeared.");
        }
    }

    public boolean isSuccessfullyApproved() {
        try {
            // Wait for success message or status change
            Thread.sleep(3000);
            return wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(successMessage),
                ExpectedConditions.textToBePresentInElementLocated(registrationStatus, "Approved"),
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Approved")
            ));
        } catch (Exception e) {
            System.out.println("Success message not found: " + e.getMessage());
            return false;
        }
    }
}