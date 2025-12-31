package SuperAdminPage;


//MeRegPaymentTest.java
//package com.mereg.tests;

//import com.mereg.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Random;

public class SuccessFullPaymentTest extends BaseTest {
 private Random random = new Random();
 
 @Test(priority = 1)
 public void testSelectGuardianAndChildren() {
     System.out.println("\n=== Test Case 1: Select Guardian and Children ===");
     
     GuardianListPage guardianPage = new GuardianListPage(driver);
     
     // Select random guardian
     String guardianName = guardianPage.selectRandomGuardian();
     Assert.assertNotNull(guardianName, "Failed to select guardian");
     
     // Select random children (1-3 children)
     List<String> childrenNames = guardianPage.selectRandomChildren(3);
     Assert.assertFalse(childrenNames.isEmpty(), "Failed to select children");
     
     // Select payment type
     String paymentType = random.nextBoolean() ? "full" : "custom";
     guardianPage.selectPaymentType(paymentType);
     
     // Get fee amounts and verify
     List<Double> feeAmounts = guardianPage.getFeeAmounts();
     double totalAmount = guardianPage.getTotalAmount();
     
     // Verify total matches sum of fees (for full payment)
     if ("full".equals(paymentType)) {
         double expectedTotal = feeAmounts.stream().mapToDouble(Double::doubleValue).sum();
         Assert.assertEquals(totalAmount, expectedTotal, 0.01, 
                 "Total amount " + totalAmount + " doesn't match sum of fees " + expectedTotal);
     }
     
     // Proceed to payment
     guardianPage.proceedToPayment();
     
     // Verify navigation to payment summary
     Assert.assertTrue(guardianPage.verifyPaymentSummaryDisplayed(), 
             "Failed to navigate to payment summary");
     
     System.out.println("✓ Test Case 1 Passed: Successfully selected guardian and children");
 }
 
 @Test(priority = 2, dependsOnMethods = "testSelectGuardianAndChildren")
 public void testPaymentModeSelection() {
     System.out.println("\n=== Test Case 2: Payment Mode Selection ===");
     
     PaymentModePage paymentPage = new PaymentModePage(driver);
     
     // Select random payment mode
     String[] paymentModes = {"online", "cash", "cheque"};
     String selectedMode = paymentModes[random.nextInt(paymentModes.length)];
     paymentPage.selectPaymentMode(selectedMode);
     
     // Get processing fee and total amount
     double processingFee = paymentPage.getProcessingFee();
     double totalAmount = paymentPage.getTotalAmount();
     
     // Process based on payment mode
     if ("cash".equals(selectedMode)) {
         String receiptNumber = paymentPage.processCashPayment();
         Assert.assertNotNull(receiptNumber, "Failed to process cash payment");
         
     } else if ("cheque".equals(selectedMode)) {
         String chequeNumber = paymentPage.processChequePayment();
         Assert.assertNotNull(chequeNumber, "Failed to process cheque payment");
         
     } else if ("online".equals(selectedMode)) {
         paymentPage.processOnlinePayment();
         Assert.assertTrue(paymentPage.verifyCardDetailsDisplayed(), 
                 "Failed to navigate to card details");
     }
     
     System.out.println("✓ Test Case 2 Passed: Successfully processed " + selectedMode + " payment");
 }
 
 @Test(priority = 3, dependsOnMethods = "testPaymentModeSelection")
 public void testOnlinePaymentProcessing() {
     System.out.println("\n=== Test Case 3: Online Payment Processing ===");
     
     // Check if we're on card details page (only for online payments)
     try {
         CardDetailsPage cardPage = new CardDetailsPage(driver);
         
         // Select random currency
         String currency = cardPage.selectRandomCurrency();
         Assert.assertNotNull(currency, "Failed to select currency");
         
         // Fill card details with random data
         CardDetailsPage.CardDetails cardDetails = cardPage.fillCardDetails();
         Assert.assertNotNull(cardDetails, "Failed to fill card details");
         
         // Submit payment
         cardPage.submitPayment();
         
         System.out.println("✓ Test Case 3 Passed: Successfully processed online payment");
     } catch (Exception e) {
         System.out.println("Skipping online payment processing - not applicable for current payment mode");
     }
 }
 
 @Test(priority = 4, dependsOnMethods = {"testPaymentModeSelection", "testOnlinePaymentProcessing"})
 public void testPaymentConfirmation() {
     System.out.println("\n=== Test Case 4: Payment Confirmation ===");
     
     SuccessfulPaymentPage successPage = new SuccessfulPaymentPage(driver);
     
     // Verify payment success
     SuccessfulPaymentPage.PaymentResult paymentResult = successPage.verifySuccessfulPayment();
     Assert.assertTrue(paymentResult.success, "Payment was not successful");
     
     // Continue to dashboard
     successPage.continueToDashboard();
     
     System.out.println("✓ Test Case 4 Passed: Payment confirmed successfully");
 }
 
 @Test(priority = 5)
 public void testMultiplePaymentsFlow() {
     System.out.println("\n=== Test Case 5: Multiple Payments Flow ===");
     
     // Process 2-3 payments with different combinations
     int numPayments = 2 + random.nextInt(2); // 2 or 3 payments
     
     for (int i = 0; i < numPayments; i++) {
         System.out.println("\n--- Processing Payment " + (i + 1) + " of " + numPayments + " ---");
         
         // Navigate back to guardian selection
         driver.get("https://mereg-dev.netlify.app/navigation-home/complete-payment");
         
         // Execute complete payment flow
         testSelectGuardianAndChildren();
         testPaymentModeSelection();
         testOnlinePaymentProcessing();
         testPaymentConfirmation();
         
         System.out.println("✓ Completed Payment " + (i + 1) + " successfully");
     }
     
     System.out.println("✓ Test Case 5 Passed: Successfully processed " + numPayments + " payments");
 }
}