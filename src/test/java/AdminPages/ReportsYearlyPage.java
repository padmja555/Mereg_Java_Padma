
package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class ReportsYearlyPage {
    WebDriver driver;

    By reportTitleLocator = By.xpath("//mat-card-title[normalize-space()='Total Payments']");
    By reportLink = By.xpath("//h4[normalize-space()='Reports']");
    //By reportyearlyLink = By.xpath("(//span[@class='mat-button-toggle-label-content'])[2]");
    //By reportToggleOptions = By.xpath("//mat-button-toggle-group[@name='range']//mat-button-toggle");
    By reportToggleOptions = By.xpath("//span[@class='mat-button-toggle-label-content']");

    public ReportsYearlyPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnreportpage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(reportLink)).click();
    }
    public void selectRandomReportRange() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(reportToggleOptions));

        List<WebElement> toggleOptions = driver.findElements(reportToggleOptions);

        if (toggleOptions.isEmpty()) {
            throw new RuntimeException("No report range toggle options found.");
        }

        int randomIndex = new Random().nextInt(toggleOptions.size());
        WebElement selectedToggle = toggleOptions.get(randomIndex);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectedToggle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectedToggle);

        String selectedText = selectedToggle.getText().trim();
        System.out.println("✅ Randomly selected report range: " + selectedText);
    }



    public String getreportpageTitleText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(reportTitleLocator));
        return driver.findElement(reportTitleLocator).getText();
    }
}

