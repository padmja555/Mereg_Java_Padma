package SuperAdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

public class SuperStudentPage {
    WebDriver driver;

   By studentsTitleLocator = By.xpath("//h3[normalize-space()='MeReg Workspace']");
    //By studentsTitleLocator = By.xpath("//mat-sidenav-content[@class='mat-drawer-content mat-sidenav-content content-area']");

    // adminLink = By.xpath("//div[contains(text(),'Admin')]");
    By studentsLink = By.xpath("//h4[normalize-space()='Students']");

    public SuperStudentPage(WebDriver driver) {
        this.driver = driver;
    }
    /*
    public void clickOnStudents() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(studentsLink)).click();
    }*/
    
   
    public void clickOnStudents() throws TimeoutException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    try {
        // Optional: wait for any loader or overlay to disappear first
        By loader = By.cssSelector(".loading-spinner, .mat-progress-bar, .mat-overlay-backdrop");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));

        // Now wait until the "Students" button is clickable
        By studentsTab = By.xpath("//span[normalize-space()='Students'] | //h4[normalize-space()='Students']");
        WebElement studentsElement = wait.until(ExpectedConditions.elementToBeClickable(studentsTab));

        // Scroll into view to ensure clickability
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", studentsElement);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", studentsElement);

        System.out.println("Clicked on 'Students' tab successfully.");
    } catch (Exception e) {
        System.err.println("Failed to click on 'Students' tab: " + e.getMessage());
        throw e;
    }
}

  

    public String getStudentsTitleText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(studentsTitleLocator));
        return driver.findElement(studentsTitleLocator).getText();
    }


}

