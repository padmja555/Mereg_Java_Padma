package SuperAdminPage;


//PaymentModePage.java
//package com.mereg.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.time.Duration;
import java.util.Random;

public class PaymentModePage {
 private WebDriver driver;
 private WebDriverWait wait;
 private Random random;
 
 // Locators
 private By paymentModeSelect = By.xpath("//select[contains(@id, 'payment-mode') or contains(@name, 'payment-mode')]");
 private By cashReceiptInput = By.xpath("//input[contains(@id, 'receipt') or contains(@placeholder, 'receipt')]");
 private By chequeNumberInput = By.xpath("//input[contains(@id, 'cheque') or contains(@placeholder, 'cheque number')]");
 private By uploadChequeButton = By.xpath("//input[@type='file' and contains(@id, 'cheque')]");
 private By paySecurelyButton = By.xpath("//button[contains(text(), 'Pay Securely') or contains(text(), 'Pay')]");
 private By processingFee = By.xpath("//div[contains(text(), 'Processing Fee')]/following-sibling::div");
 private By totalAmount = By.xpath("//div[contains(text(), 'Total Amount')]/following-sibling::div");
 
 public PaymentModePage(WebDriver driver) {
     this.driver = driver;
     this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
     this.random = new Random();
 }
 
 public String PaymentModePage(String mode) {
     try {
         // Try dropdown first
         WebElement paymentDropdown = wait.until(ExpectedConditions.elementToBeClickable(paymentModeSelect));
         Select paymentSelect = new Select(paymentDropdown);
         
         switch (mode.toLowerCase()) {
             case "online":
                 paymentSelect.selectByVisibleText("Online");
                 break;
             case "cash":
                 paymentSelect.selectByVisibleText("Cash");
                 break;
             case "cheque":
                 paymentSelect.selectByVisibleText("Cheque");
                 break;
         }
     } catch (Exception e) {
         // Try radio buttons or direct elements
         By modeLocator = switch (mode.toLowerCase()) {
             case "online" -> By.xpath("//input[@value='online'] | //div[contains(text(), 'Online')]");
             case "cash" -> By.xpath("//input[@value='cash'] | //div[contains(text(), 'Cash')]");
             case "cheque" -> By.xpath("//input[@value='cheque'] | //div[contains(text(), 'Cheque')]");
             default -> throw new IllegalArgumentException("Invalid payment mode: " + mode);
         };
         
         WebElement modeElement = wait.until(ExpectedConditions.elementToBeClickable(modeLocator));
         modeElement.click();
     }
     
     System.out.println("Selected payment mode: " + mode);
     return mode;
 }
 
 public String processCashPayment() {
     String receiptNumber = "RC" + (100000 + random.nextInt(900000));
     WebElement receiptInput = wait.until(ExpectedConditions.elementToBeClickable(cashReceiptInput));
     receiptInput.clear();
     receiptInput.sendKeys(receiptNumber);
     System.out.println("Entered cash receipt number: " + receiptNumber);
     return receiptNumber;
 }
 
 public String processChequePayment() {
     String chequeNumber = "CHQ" + (100000 + random.nextInt(900000));
     
     // Enter cheque number
     WebElement chequeInput = wait.until(ExpectedConditions.elementToBeClickable(chequeNumberInput));
     chequeInput.clear();
     chequeInput.sendKeys(chequeNumber);
     System.out.println("Entered cheque number: " + chequeNumber);
     
     // Upload cheque file
     WebElement uploadButton = wait.until(ExpectedConditions.presenceOfElementLocated(uploadChequeButton));
     
     // Create dummy file for testing
     File dummyFile = new File("dummy_cheque.pdf");
     uploadButton.sendKeys(dummyFile.getAbsolutePath());
     System.out.println("Uploaded cheque file");
     
     return chequeNumber;
 }
 
 public void processOnlinePayment() {
     WebElement payButton = wait.until(ExpectedConditions.elementToBeClickable(paySecurelyButton));
     payButton.click();
     System.out.println("Proceeding to online payment...");
 }
 
 public double getProcessingFee() {
     try {
         WebElement feeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(processingFee));
         double fee = Double.parseDouble(feeElement.getText().replace("$", "").replace(",", ""));
         System.out.println("Processing fee: $" + fee);
         return fee;
     } catch (Exception e) {
         return 0.0;
     }
 }
 
 public double getTotalAmount() {
     WebElement totalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(totalAmount));
     double total = Double.parseDouble(totalElement.getText().replace("$", "").replace(",", ""));
     System.out.println("Total amount with fees: $" + total);
     return total;
 }
 
 public boolean verifyCardDetailsDisplayed() {
     try {
         WebElement cardElement = wait.until(
                 ExpectedConditions.visibilityOfElementLocated(
                         By.xpath("//h2[contains(text(), 'Pay with card') or contains(text(), 'Card information')]"))
         );
         return cardElement.isDisplayed();
     } catch (Exception e) {
         return false;
     }
 }
}