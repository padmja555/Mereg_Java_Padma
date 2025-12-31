package AdminPages;

//import org.bouncycastle.oer.its.ieee1609dot2.basetypes.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class BackbuttonPaymentPage {
  WebDriver driver;
  WebDriverWait wait;
  Random random = new Random();

  // Locators
  By pageTitle = By.xpath("//h2[contains(text(),'Payment Summary')]");
  By paymentModeDropdown = By.xpath("//mat-label[text()='Select Payment Mode']/ancestor::div[contains(@class,'mat-form-field-infix')]//mat-select");
  By paymentModeOptions = By.xpath("//mat-option");
  By payemetattachfile = By.xpath("//mat-icon[normalize-space()='attach_file']");
  By chequeUploadInput  = By.xpath("//input[@type='file']");
  //By chequeUploadInput = By.xpath("//input[@type='file' and @formcontrolname='chequeFile']");
  By paySecurelyButton = By.xpath("//button[contains(text(),'Pay') and contains(text(),'Securely')]");
  By paybackbuttontitle = By.xpath("//div[contains(@class,'Text') and contains(text(),'Pay with card')]");

  By paybackButton = By.xpath("//a[@title=\"Mereg-hotfix sandbox\"]");

  
  public BackbuttonPaymentPage(WebDriver driver, WebDriverWait wait) {
      this.driver = driver;
      this.wait = wait;
  }

  // Get Payment Summary Page Title
  public String getPageSummaryPageTitleText() {
      WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
      return titleElement.getText().trim();
  }

  public String selectOnlinePaymentMode() {
	    // 1️⃣ Click the payment mode dropdown
	    WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(paymentModeDropdown));
	    dropdown.click();

	    // 2️⃣ Wait and get all available options
	    List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(paymentModeOptions));
	    if (options.isEmpty()) {
	        throw new RuntimeException("No payment mode options available!");
	    }

	    // 3️⃣ Look for 'Online' option explicitly
	    WebElement onlineOption = options.stream()
	            .filter(opt -> opt.getText().trim().equalsIgnoreCase("Online"))
	            .findFirst()
	            .orElseThrow(() -> new RuntimeException("'Online' payment option not found!"));

	    // 4️⃣ Click the 'Online' option
	    System.out.println("Selecting Payment Mode: Online");
	    onlineOption.click();

	    // 5️⃣ Proceed message or navigation
	    System.out.println("Online selected → proceed to Online Payment Page.");

	    return "Online";
	}


  // Click Pay Securely Button
  public void clickPaySecurelyButton() {
      WebElement button = wait.until(ExpectedConditions.elementToBeClickable(paySecurelyButton));
      button.click();
      System.out.println("Clicked on Pay Securely button");
  }
  public void clickbackButton() {
      WebElement button = wait.until(ExpectedConditions.elementToBeClickable(paybackButton));
      button.click();
      System.out.println("Clicked on Pay Securely button");
  }
  public String getPagebackbuttonTitleText() {
	    // Switch to the top-level iframe first
	    WebElement outerFrame = driver.findElement(By.tagName("iframe"));
	    driver.switchTo().frame(outerFrame);

	    // Then switch to inner iframe (if nested)
	    WebElement innerFrame = driver.findElement(By.tagName("iframe"));
	    driver.switchTo().frame(innerFrame);

	    // Wait for presence first, then visibility
	    By payWithCardLocator = By.xpath("//div[contains(text(),'Pay with card')]");
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

	    WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(payWithCardLocator));
	    wait.until(ExpectedConditions.visibilityOf(titleElement));

	    String titleText = titleElement.getText().trim();

	    // Switch back to main content
	    driver.switchTo().defaultContent();

	    return titleText;
	}

  
}
