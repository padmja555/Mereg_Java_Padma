package SuperAdminPage;

//CardDetailsPage.java
//package com.mereg.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class CardDetailsPage {
 private WebDriver driver;
 private WebDriverWait wait;
 private Random random;
 
 // Locators
 private By currencyOptions = By.xpath("//div[contains(text(), '¥') or contains(text(), '$')]");
 private By emailInput = By.xpath("//input[@type='email' or contains(@placeholder, 'email')]");
 private By cardNumberInput = By.xpath("//input[contains(@placeholder, '1234') or contains(@id, 'card-number')]");
 private By expiryDateInput = By.xpath("//input[contains(@placeholder, 'MM') or contains(@id, 'expiry')]");
 private By cvcInput = By.xpath("//input[contains(@placeholder, 'CVC') or contains(@id, 'cvc')]");
 private By cardholderNameInput = By.xpath("//input[contains(@placeholder, 'Full name') or contains(@id, 'cardholder')]");
 private By countrySelect = By.xpath("//select[contains(@id, 'country')]");
 private By payButton = By.xpath("//button[contains(text(), 'Pay')]");
 
 public CardDetailsPage(WebDriver driver) {
     this.driver = driver;
     this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
     this.random = new Random();
 }
 
 public String selectRandomCurrency() {
     List<WebElement> currencyElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(currencyOptions));
     
     if (!currencyElements.isEmpty()) {
         WebElement randomCurrency = currencyElements.get(random.nextInt(currencyElements.size()));
         String currencyAmount = randomCurrency.getText();
         randomCurrency.click();
         System.out.println("Selected currency: " + currencyAmount);
         return currencyAmount;
     }
     return null;
 }
 
 public CardDetails fillCardDetails() {
     // Generate random test data
     String testEmail = "testuser" + (1000 + random.nextInt(9000)) + "@example.com";
     String cardNumber = generateRandomCardNumber();
     String expiryDate = String.format("%02d", (1 + random.nextInt(12))) + "/" + (23 + random.nextInt(8));
     String cvc = String.valueOf(100 + random.nextInt(900));
     String cardholderName = "Test User " + (1000 + random.nextInt(9000));
     
     // Fill email
     WebElement emailElement = wait.until(ExpectedConditions.elementToBeClickable(emailInput));
     emailElement.clear();
     emailElement.sendKeys(testEmail);
     
     // Fill card number
     WebElement cardNumberElement = wait.until(ExpectedConditions.elementToBeClickable(cardNumberInput));
     cardNumberElement.clear();
     cardNumberElement.sendKeys(cardNumber);
     
     // Fill expiry date
     WebElement expiryElement = wait.until(ExpectedConditions.elementToBeClickable(expiryDateInput));
     expiryElement.clear();
     expiryElement.sendKeys(expiryDate);
     
     // Fill CVC
     WebElement cvcElement = wait.until(ExpectedConditions.elementToBeClickable(cvcInput));
     cvcElement.clear();
     cvcElement.sendKeys(cvc);
     
     // Fill cardholder name
     WebElement nameElement = wait.until(ExpectedConditions.elementToBeClickable(cardholderNameInput));
     nameElement.clear();
     nameElement.sendKeys(cardholderName);
     
     // Select country
     try {
         WebElement countryElement = wait.until(ExpectedConditions.elementToBeClickable(countrySelect));
         Select countrySelect = new Select(countryElement);
         List<WebElement> countries = countrySelect.getOptions();
         if (countries.size() > 1) {
             countrySelect.selectByIndex(1 + random.nextInt(countries.size() - 1));
         }
     } catch (Exception e) {
         // Country selection not available
     }
     
     System.out.println("Filled card details for: " + cardholderName);
     
     return new CardDetails(testEmail, cardNumber, expiryDate, cvc, cardholderName);
 }
 
 private String generateRandomCardNumber() {
     String[] testPrefixes = {"4242", "4000", "5555", "2222"};
     String prefix = testPrefixes[random.nextInt(testPrefixes.length)];
     StringBuilder rest = new StringBuilder();
     for (int i = 0; i < 12; i++) {
         rest.append(random.nextInt(10));
     }
     return prefix + rest;
 }
 
 public void submitPayment() {
     WebElement payBtn = wait.until(ExpectedConditions.elementToBeClickable(payButton));
     payBtn.click();
     System.out.println("Payment submitted...");
 }
 
 public boolean verifyPaymentSuccess() {
     try {
         WebElement successElement = wait.until(
                 ExpectedConditions.visibilityOfElementLocated(
                         By.xpath("//h2[contains(text(), 'Success') or contains(text(), 'Thank you')]"))
         );
         return successElement.isDisplayed();
     } catch (Exception e) {
         return false;
     }
 }
 
 // Data class for card details
 public static class CardDetails {
     public final String email;
     public final String cardNumber;
     public final String expiryDate;
     public final String cvc;
     public final String cardholderName;
     
     public CardDetails(String email, String cardNumber, String expiryDate, String cvc, String cardholderName) {
         this.email = email;
         this.cardNumber = cardNumber;
         this.expiryDate = expiryDate;
         this.cvc = cvc;
         this.cardholderName = cardholderName;
     }
 }
}