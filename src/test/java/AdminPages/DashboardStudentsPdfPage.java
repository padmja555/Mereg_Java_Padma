
package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class DashboardStudentsPdfPage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    // ======= Locators =======
    private By childLastNameFilter = By.xpath("//mat-label[normalize-space()='Filter']/ancestor::mat-form-field//input");
    private By pdfDownloadBtn = By.xpath("//button[contains(.,'PDF') or contains(.,'pdf')]");

    public DashboardStudentsPdfPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    // ✅ Enter child last name in filter
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

    // ✅ Click PDF download button
    public void clickPDFDownload() throws Exception {
        try {
            WebElement pdfBtn = wait.until(ExpectedConditions.elementToBeClickable(pdfDownloadBtn));
            js.executeScript("arguments[0].scrollIntoView(true);", pdfBtn);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", pdfBtn);
            System.out.println("✅ PDF download initiated successfully");
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("❌ Failed to click PDF button: " + e.getMessage());
            throw e;
        }
    }
}

