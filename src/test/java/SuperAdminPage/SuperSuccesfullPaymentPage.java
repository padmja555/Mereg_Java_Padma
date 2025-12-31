package SuperAdminPage;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.util.List;
import java.util.Random;

public class SuperSuccesfullPaymentPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Random random = new Random();

    // ---------- Locators ----------
    private By pageTitle = By.xpath("//h2[contains(text(),'Payment Summary') or contains(text(),'Pay with card')]");
    private By paymentModeDropdown = By.xpath("//mat-label[text()='Select Payment Mode']/ancestor::div[contains(@class,'mat-form-field-infix')]//mat-select");
    private By paymentModeOptions = By.xpath("//mat-option");
    private By attachFileIcon = By.xpath("//mat-icon[normalize-space()='attach_file']");
    private By chequeUploadInput = By.xpath("//input[@type='file']");
    private By paySecurelyButton = By.xpath("//button[contains(text(),'Pay') and contains(text(),'Securely')]");

    // Online (Stripe-like) payment fields
    private By paymentEmail = By.xpath("//input[contains(@autocomplete,'email')]");
    private By cardNumber = By.xpath("//input[@type='text' and contains(@autocomplete,'cc-number')]");
    private By cardExpiry = By.xpath("//input[@type='text' and contains(@autocomplete,'cc-exp')]");
    private By cardCVC = By.xpath("//input[@type='text' and contains(@autocomplete,'cc-csc')]");
    private By cardHolderName = By.xpath("//input[@type='text' and contains(@autocomplete,'cc-name')]");
    private By countryDropdown = By.xpath("//select[@id='billingCountry']");
    private By zipcodeField = By.xpath("//input[@id='billingPostalCode']");
    private By payButton = By.xpath("//div[@class='SubmitButton-IconContainer']");

    // Manual payment fields
    private By cashReceiptInput = By.xpath("//input[contains(@id,'receipt') or contains(@placeholder,'receipt')]");
    private By chequeNumberInput = By.xpath("//input[contains(@id,'cheque') or contains(@placeholder,'cheque') or contains(@formcontrolname,'chequeNumber')]");

    // ---------- Constructor ----------
    public SuperSuccesfullPaymentPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // ---------- Page Title ----------
    //public String getPageTitleText() {
    public String getPageTitleText() {
        try {
            WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
            return titleElement.getText().trim();
        } catch (TimeoutException e) {
            // Stripe page fallback
            try {
                WebElement stripeTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h1[contains(text(),'Pay with card')] | //label[contains(text(),'Email')]")
                ));
                return stripeTitle.getText().trim();
            } catch (Exception ex) {
                return driver.getTitle(); // fallback to browser title
            }
        }
    }


    // ---------- Country / ZIP ----------
    public void selectCountry(String countryName) {
        WebElement countryDropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(countryDropdown));
        Select countrySelect = new Select(countryDropdownElement);
        countrySelect.selectByVisibleText(countryName.trim());
        System.out.println("Selected Country: " + countryName);
    }

    public boolean isZipcodeFieldDisplayed() {
        try {
            WebElement zipcodeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(zipcodeField));
            return zipcodeElement.isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    // ---------- Random Payment Mode ----------
    public String selectRandomPaymentModeDynamically(String chequeFilePath) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(paymentModeDropdown));
        dropdown.click();

        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(paymentModeOptions));
        if (options.isEmpty()) throw new RuntimeException("No payment mode options available!");

        int randomIndex = random.nextInt(options.size());
        WebElement selectedOption = options.get(randomIndex);
        String selectedText = selectedOption.getText().trim();
        System.out.println("Selecting Payment Mode: " + selectedText);
        selectedOption.click();

        switch (selectedText.toLowerCase()) {
            case "online":
                System.out.println("Online selected → continue to card payment page.");
                break;

            case "cash":
                System.out.println("Cash selected → staying on summary page.");
                processCashPayment();
                break;

            case "cheque":
                processChequePayment(chequeFilePath);
                break;

            default:
                System.out.println("Selected mode: " + selectedText);
        }
        return selectedText;
    }

    // ---------- Cash Payment ----------
    public String processCashPayment() {
        System.out.println("Processing cash payment...");

        String receiptNumber = String.valueOf(10000 + new Random().nextInt(90000)); // always 5 digits

        try {
            By receiptLocator = By.xpath(
                "//input[contains(@formcontrolname,'receipt') or " +
                "contains(@placeholder,'Receipt') or " +
                "contains(@id,'receipt') or " +
                "contains(@name,'receipt')]"
            );

            WebElement receiptField = wait.until(ExpectedConditions.elementToBeClickable(receiptLocator));
            receiptField.clear();
            receiptField.sendKeys(receiptNumber);

            System.out.println("✅ Entered Cash Receipt Number: " + receiptNumber);
        } catch (Exception e) {
            System.out.println("⚠️ Cash receipt input not found: " + e.getMessage());
        }

        return receiptNumber;
    }

    // ---------- Cheque Payment ----------
 // ---------- Cheque Payment ----------
 // ---------- Cheque Payment ----------
    public String processChequePayment(String chequeFilePath) {
        // Must be 3–6 digits only
        String chequeNumber = String.valueOf(100000 + random.nextInt(900000)); // generates 6-digit numeric string
        System.out.println("Processing cheque payment...");

        try {
            WebElement attachIcon = wait.until(ExpectedConditions.elementToBeClickable(attachFileIcon));
            attachIcon.click();
            System.out.println("📎 Clicked attach file icon.");
        } catch (Exception e) {
            System.out.println("⚠️ Attach icon not clickable or not found — continuing anyway.");
        }

        By chequeFieldLocator = By.xpath(
            "//input[contains(@formcontrolname,'chequeNumber') or " +
            "contains(@placeholder,'Cheque') or " +
            "contains(@id,'cheque') or " +
            "contains(@name,'cheque')]"
        );

        WebElement chequeInput = wait.until(ExpectedConditions.elementToBeClickable(chequeFieldLocator));
        chequeInput.clear();
        chequeInput.sendKeys(chequeNumber);
        System.out.println("🧾 Entered cheque number: " + chequeNumber);

        try {
            WebElement uploadField = wait.until(ExpectedConditions.presenceOfElementLocated(chequeUploadInput));
            uploadField.sendKeys("C:\\Upload\\Padma.txt");
            System.out.println("✅ Cheque file uploaded successfully.");
        } catch (Exception e) {
            System.out.println("⚠️ Could not upload cheque file: " + e.getMessage());
        }

        return chequeNumber;
    }



    // ---------- Online Card Entry ----------
    public void enterCardDetails(String email, String number, String expiry, String cvc, String holderName,
                                 String country, String zipcodeOrPostal) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(paymentEmail)).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardNumber)).sendKeys(number);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardExpiry)).sendKeys(expiry);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardCVC)).sendKeys(cvc);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardHolderName)).sendKeys(holderName);

        selectCountry(country);
        if (isZipcodeFieldDisplayed()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(zipcodeField)).sendKeys(zipcodeOrPostal);
        }

        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    // ---------- Buttons ----------
    public void clickPaySecurelyButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(paySecurelyButton));
        button.click();
        System.out.println("Clicked on 'Pay Securely' button.");
    }

    public void clickPayButton() {
        WebElement pay = wait.until(ExpectedConditions.elementToBeClickable(payButton));
        pay.click();
        System.out.println("Clicked Stripe 'Pay' button.");
    }
}
