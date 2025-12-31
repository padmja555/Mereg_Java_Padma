package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Paymentinformationpagenotworking {
    WebDriver driver;
    WebDriverWait wait;
    Random random = new Random();

    // Locators
    By paymentTitleLocator = By.xpath("//h2[@class='child-name']");
    By paymentLink = By.xpath("//h4[normalize-space()='Payment']");
    By guardianDropdown = By.xpath("//mat-card/mat-card-content/mat-form-field[1]//input");
    By guardianOptions = By.xpath("//mat-option//span");
    By childDropdown = By.xpath("//mat-select[@id='mat-select-2']");
    By childOptions = By.xpath("//mat-option//span");
    By proceedButton = By.xpath("//mat-sidenav-content/app-subscriptioninfo/div[3]/button");
    By cardNumberInput = By.xpath("//input[@placeholder='Card Number']");
    By expiryDateInput = By.xpath("//input[@placeholder='MM/YY']");
    By cvvInput = By.xpath("//input[@placeholder='CVV']");
    By payButton = By.xpath("//button[normalize-space()='Pay']");

    public Paymentinformationpagenotworking(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickOnPaymentPage() {
        WebElement elem = wait.until(ExpectedConditions.elementToBeClickable(paymentLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);
        elem.click();
    }

    public String getPaymentPageTitleText() {
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(paymentTitleLocator));
        return title.getText();
    }

    public void selectGuardian(String guardianName) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(guardianDropdown));
        dropdown.click();

        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(guardianOptions));
        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(guardianName.trim())) {
                option.click();
                break;
            }
        }
    }

    public List<String> Selecttochild(String childName) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(childDropdown));

        // Scroll into view and click
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
        dropdown.click();

        // Wait until mat-options are visible
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        List<WebElement> options = longWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(childOptions));

        return options.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void clickProceedToPayment() {
        WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(proceedButton));
        proceedBtn.click();
    }

    public void processPayment(String cardNumber, String expiryDate, String cvv) {
        WebElement cardInput = wait.until(ExpectedConditions.visibilityOfElementLocated(cardNumberInput));
        cardInput.clear();
        cardInput.sendKeys(cardNumber);

        WebElement expiryInput = wait.until(ExpectedConditions.visibilityOfElementLocated(expiryDateInput));
        expiryInput.clear();
        expiryInput.sendKeys(expiryDate);

        WebElement cvvInputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(cvvInput));
        cvvInputElement.clear();
        cvvInputElement.sendKeys(cvv);

        WebElement payBtn = wait.until(ExpectedConditions.elementToBeClickable(payButton));
        payBtn.click();
    }

    public List<String> getGuardianOptions() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(guardianDropdown));
        dropdown.click();
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(guardianOptions));
        return options.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<String> getChildOptions() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(childDropdown));
        dropdown.click();
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(childOptions));
        return options.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}
