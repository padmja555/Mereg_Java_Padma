
package SuperAdminPage;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class PaymementFilterExcelPage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    // ===== Locators =====
    private By childLastNameFilter = By.xpath("//input[@data-placeholder=\"Search by Student Name\"]");
    private By excelDownloadBtn = By.xpath("//button[contains(.,'Excel') or contains(.,'excel')]");
    		
    //private By applyFilterBtn = By.xpath("//button[normalize-space()='Apply Filters']");
    //private By paymentHistoryTable = By.xpath("//table[contains(@class,'mat-table')]");

    public PaymementFilterExcelPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    // ===== Utility: Generate random date =====
    public void enterRandomChildLastName() throws Exception {
        try {
            // 🔹 List of possible child last names — add or update as needed
            String[] childLastNames = {"Srirama", "Ramya","sanjay","Ramu", "Harsha", "Poojitha", "poojitha"};
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

