package AdminPages;

//import org.bouncycastle.oer.its.ieee1609dot2.basetypes.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

public class PaymentSummaryPage {
    WebDriver driver;
    WebDriverWait wait;
    Random random = new Random();

    // Locators
    By pageTitle = By.xpath("//h2[contains(text(),'Payment Summary')]");
    By paymentModeDropdown = By.xpath("//mat-label[text()='Select Payment Mode']/ancestor::div[contains(@class,'mat-form-field-infix')]//mat-select");

    ////mat-label[text()='Select Payment Mode']/ancestor::div[contains(@class,'mat-form-field-infix')]//mat-select
    //By paymentModeDropdown = By.xpath("//mat-select[@formcontrolname='paymentMode']");
    By paymentModeOptions = By.xpath("//mat-option");
    By payemetattachfile = By.xpath("//mat-icon[normalize-space()='attach_file']");
    By chequeUploadInput  = By.xpath("//input[@type='file']");
  
    //By chequeUploadInput = By.xpath("//input[@type='file' and @formcontrolname='chequeFile']");
    By paySecurelyButton = By.xpath("//button[contains(text(),'Pay') and contains(text(),'Securely')]");

    public PaymentSummaryPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Get Payment Summary Page Title
    public String getPageSummaryPageTitleText() {
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
        return titleElement.getText().trim();
    }

    public String selectRandomPaymentModeDynamically(String chequeFilePath) {
        // 1️⃣ Click the payment mode dropdown
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(paymentModeDropdown));
        dropdown.click();

        // 2️⃣ Wait and get all options
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(paymentModeOptions));
        if (options.isEmpty()) throw new RuntimeException("No payment mode options available!");

        // 3️⃣ Select a random option
        int randomIndex = new Random().nextInt(options.size());
        WebElement selectedOption = options.get(randomIndex);
        String selectedText = selectedOption.getText().trim();
        System.out.println("Selecting Payment Mode: " + selectedText);
        selectedOption.click();

        // 4️⃣ Handle special cases
        switch (selectedText.toLowerCase()) {
            case "online":
                // Online mode → just return, your test can continue to OnlinePaymentPage
                System.out.println("Online selected → proceed to Online Payment Page.");
                break;

            case "cash":
                // Cash mode → nothing extra needed
                System.out.println("Cash selected → complete in summary page.");
                break;

            case "cheque":
                // Cheque → click attach file and upload
                try {
                    WebElement attachIcon = wait.until(ExpectedConditions.elementToBeClickable(payemetattachfile));
                    attachIcon.click();

                    WebElement uploadField = wait.until(ExpectedConditions.presenceOfElementLocated(chequeUploadInput));
                    uploadField.sendKeys(chequeFilePath);
                    System.out.println("Cheque file uploaded: " + chequeFilePath);

                } catch (Exception e) {
                    throw new RuntimeException("Cheque input/upload not found! Check if Cheque mode is available.");
                }
                break;

            default:
                System.out.println("Selected mode: " + selectedText + " → no extra actions defined.");
        }

        return selectedText;
    }

    // Click Pay Securely Button
    public void clickPaySecurelyButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(paySecurelyButton));
        button.click();
        System.out.println("Clicked on Pay Securely button");
    }
}
