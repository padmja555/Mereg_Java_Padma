package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Paymentpage {
	   private WebDriver driver;
	    private WebDriverWait wait;


    By paymentTitleLocator = By.xpath("//h1[normalize-space()='Payment']");
    By paymentLink = By.xpath("//mat-sidenav/div/div/div/div/mat-nav-list[2]/a[5]/div/div[2]/h4");
    //By paymentLink = By.xpath("//h4[normalize-space()='Payment']");
    ////mat-nav-list[2]/a[5]/div/div[2]/h4
    //By paymentLink = By.xpath("//(//h4[@class='mat-line'])[6]");
    //(//h4[@class="mat-line"])[6]

    public Paymentpage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // A 15-second timeout is a good starting point

    }
    /*
    public void clickOnPaymentPage() {
        By paymentLink = By.xpath("//h4[contains(text(),'Payment')]");

        WebElement elem = new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.visibilityOfElementLocated(paymentLink));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(elem));

        elem.click();
    }
    */
    public void clickOnPaymentPage() {
        By paymentLink = By.xpath("//h4[contains(text(),'Payment')]");

        WebElement elem = new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.visibilityOfElementLocated(paymentLink));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(elem));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem);  // FIX: JS click safer
    }



    public String getpaymentpageTitleText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(paymentTitleLocator));
        return driver.findElement(paymentTitleLocator).getText();
    }
}

