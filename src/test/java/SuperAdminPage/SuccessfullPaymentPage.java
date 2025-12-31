package SuperAdminPage;

import java.time.Duration;

//SuccessfulPaymentPage.java
//package com.mereg.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SuccessfullPaymentPage {
 private WebDriver driver;
 private WebDriverWait wait;
 
 // Locators
 private By successMessage = By.xpath("//h2[contains(text(), 'Success') or contains(text(), 'Thank you')]");
 private By transactionId = By.xpath("//div[contains(text(), 'Transaction') or contains(text(), 'Reference')]");
 private By paymentAmount = By.xpath("//div[contains(text(), '$') or contains(text(), '¥')]");
 private By continueButton = By.xpath("//button[contains(text(), 'Continue') or contains(text(), 'Done')]");
 
 public SuccessfullPaymentPage(WebDriver driver) {
     this.driver = driver;
     this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
 }
 
 public PaymentResult verifySuccessfulPayment() {
     try {
         // Wait for success message
         WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
         
         // Get transaction ID
         WebElement transactionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(transactionId));
         String transactionIdText = transactionElement.getText();
         
         // Get payment amount
         WebElement amountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(paymentAmount));
         String paymentAmountText = amountElement.getText();
         
         System.out.println("Payment successful! Transaction: " + transactionIdText + ", Amount: " + paymentAmountText);
         
         return new PaymentResult(true, transactionIdText, paymentAmountText);
     } catch (Exception e) {
         System.out.println("Payment verification failed: " + e.getMessage());
         return new PaymentResult(false, null, null);
     }
 }
 
 public boolean continueToDashboard() {
     try {
         WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
         continueBtn.click();
         System.out.println("Returning to dashboard...");
         return true;
     } catch (Exception e) {
         return false;
     }
 }
 
 // Data class for payment result
 public static class PaymentResult {
     public final boolean success;
     public final String transactionId;
     public final String paymentAmount;
     
     public PaymentResult(boolean success, String transactionId, String paymentAmount) {
         this.success = success;
         this.transactionId = transactionId;
         this.paymentAmount = paymentAmount;
     }
 }
}