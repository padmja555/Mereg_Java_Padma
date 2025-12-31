package SuperAdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ManagementPage {
	   private WebDriver driver;
	    private WebDriverWait wait;


    By managementTitleLocator = By.xpath("//h2[normalize-space()='Filter Payment Reports']");
    By managementLink = By.xpath("//div[@class='mat-list-item-content']//h4[normalize-space()='Management']\r\n"
    		+ "");
    //By paymentLink = By.xpath("//h4[normalize-space()='Payment']");
    ////mat-nav-list[2]/a[5]/div/div[2]/h4
    //By paymentLink = By.xpath("//(//h4[@class='mat-line'])[6]");
    //(//h4[@class="mat-line"])[6]

    public ManagementPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // A 15-second timeout is a good starting point

    }
    public void clickOnmanagementPage() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='mat-list-item-content']//h4[normalize-space()='Management']")));

        WebElement elem = wait.until(ExpectedConditions.visibilityOfElementLocated(managementLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);
        wait.until(ExpectedConditions.elementToBeClickable(managementLink));
        elem.click();
    }


    public String getmanagementpageTitleText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(managementTitleLocator));
        return driver.findElement(managementTitleLocator).getText();
    }
}


