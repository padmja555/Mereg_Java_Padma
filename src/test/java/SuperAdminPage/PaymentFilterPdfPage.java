

package SuperAdminPage;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class PaymentFilterPdfPage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    // ===== Locators =====
    private By childLastNameFilter = By.xpath("//input[@data-placeholder=\"Search by Student Name\"]");
    private By pdfDownloadBtn = By.xpath("//button[contains(.,'PDF') or contains(.,'pdf')]");
    		
    //private By applyFilterBtn = By.xpath("//button[normalize-space()='Apply Filters']");
    //private By paymentHistoryTable = By.xpath("//table[contains(@class,'mat-table')]");

    public PaymentFilterPdfPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    // ===== Utility: Generate random date =====
    public void enterRandomChildLastName() throws Exception {
        try {
            // 🔹 List of possible child last names — add or update as needed
            String[] childLastNames = {"Chelshi", "sanjay", "PATAN", "Padma", "poojitha"};
            //String[] childLastNames = {"Chelshi", "sanjay", "PATAN", "Padma", "poojitha", "Nair", "Rao", "Gupta", "Das", "Yadav"};

            // 🔹 Pick a random one
            Random random = new Random();
            String randomLastName = childLastNames[random.nextInt(childLastNames.length)];

            // 🔹 Locate the search box
            WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(childLastNameFilter));

            // 🔹 Clear and enter random last name
            searchInput.clear();
            searchInput.sendKeys(randomLastName);
            System.out.println("✅ Entered random child last name: " + randomLastName);

            // 🔹 Give UI time to update (for Angular filter refresh)
            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("❌ Failed to enter child last name: " + e.getMessage());
            throw e;
        }
    }


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

