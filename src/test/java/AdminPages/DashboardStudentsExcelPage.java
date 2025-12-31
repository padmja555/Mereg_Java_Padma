package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class DashboardStudentsExcelPage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    private By childLastNameFilter = By.xpath("//mat-label[normalize-space()='Filter']/ancestor::mat-form-field//input");
    private By excelDownloadBtn = By.xpath("//button[contains(.,'Excel') or contains(.,'excel')]");

    public DashboardStudentsExcelPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public void enterChildLastName(String lastName) throws Exception {
        try {
            WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(childLastNameFilter));
            searchInput.clear();
            searchInput.sendKeys(lastName);
            System.out.println("✅ Entered child last name: " + lastName);
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("❌ Failed to enter child last name: " + e.getMessage());
            throw e;
        }
    }

    public void clickExcelDownload() throws Exception {
        try {
            WebElement excelBtn = wait.until(ExpectedConditions.elementToBeClickable(excelDownloadBtn));
            js.executeScript("arguments[0].scrollIntoView(true);", excelBtn);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", excelBtn);
            System.out.println("✅ Excel download initiated successfully");
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("❌ Failed to click Excel button: " + e.getMessage());
            throw e;
        }
    }
}
