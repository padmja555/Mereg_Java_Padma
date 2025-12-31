package SuperAdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

public class SuperPaymentPage {
	   private WebDriver driver;
	    private WebDriverWait wait;


    By paymentTitleLocator = By.xpath("//h1[normalize-space()='Payment']");
    By paymentLink = By.xpath("//h4[contains(text(),'Payment')]");
    //By paymentLink = By.xpath("//h4[normalize-space()='Payment']");
    ////mat-nav-list[2]/a[5]/div/div[2]/h4
    //By paymentLink = By.xpath("//(//h4[@class='mat-line'])[6]");
    //(//h4[@class="mat-line"])[6]

    public SuperPaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40)); // A 15-second timeout is a good starting point

    }
    /*
    public void clickOnPaymentPage() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='mat-ripple mat-list-item-ripple'])[5]")));

        WebElement elem = wait.until(ExpectedConditions.visibilityOfElementLocated(paymentLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);
        wait.until(ExpectedConditions.elementToBeClickable(paymentLink));
        elem.click();
    }*/
    public void clickOnPaymentPage() throws TimeoutException {
        WebElement paymentMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(paymentLink));
		wait.until(ExpectedConditions.elementToBeClickable(paymentMenu)).click();
		System.out.println("✅ Payment page menu clicked successfully");
    }
    


    public String getpaymentpageTitleText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(paymentTitleLocator));
        return driver.findElement(paymentTitleLocator).getText();
    }
	public String selectRandomPaymentModeDynamically(String chequeFile) {
		// TODO Auto-generated method stub
		return null;
	}
}


