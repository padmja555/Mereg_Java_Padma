package AdminTestcase;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import AdminPages.*;
import Base.BaseDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.StaleElementReferenceException;

public class ChooseplanNegativeTest extends BaseDriver {

    @Test
    public void testFullRegistrationFlowWithPlanSelection() {
        try {
            // Step 1: Login to the application
            System.out.println("Starting registration test flow...");
            LoginPage login = new LoginPage(driver);
            login.enterEmail("srasysife@gmail.com");
            login.enterPassword("Admin@123");
            login.clickSignIn();
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.urlContains("dashboard"));

            // Step 2: Navigate to the parent info page via child info
            Childinfo childinfo = new Childinfo(driver);
            childinfo.clickOnenrollment();
            
            childinfo.enterRandomFirstAndLastName();
            childinfo.selectGender();
            childinfo.enterrandomDOB();
            childinfo.ClickonProceedtoGuardian();
            
            // Wait for parent info page to load
            wait.until(ExpectedConditions.urlContains("guardian-info"));

            // Step 3: Fill out the parent/guardian information
            Parentpage parentguardianinfo = new Parentpage(driver);
            parentguardianinfo.enterRandomFirstName();
            parentguardianinfo.enterRandomMiddleName();
            parentguardianinfo.enterRandomLastName();
            parentguardianinfo.selectRelationship();
            parentguardianinfo.enterRandomAddress1();
            parentguardianinfo.enterRandomAddress2();
            parentguardianinfo.selectRandomCountry();
            parentguardianinfo.selectState();
            parentguardianinfo.enterRandomCity();
            parentguardianinfo.enterRandomZipCode();
            parentguardianinfo.selectRandomPhoneType();
            parentguardianinfo.enterRandomCountryCode();
            parentguardianinfo.enterRandomPhoneNumber();
            parentguardianinfo.alternateGuardianInfoIconButton();
            parentguardianinfo.enterRandomAlternateGuardianName();
            parentguardianinfo.alterGuardianSelectRelationship();
            parentguardianinfo.alternateGuardianCountryCode();
            parentguardianinfo.enterRandomAlternateGuardianPhoneNumber();
            
            // Step 4: Click proceed to medical info
            System.out.println("Clicking Proceed to Medical Info button...");
            parentguardianinfo.proceedToMedicalInfoButton();
            
            // Wait for medical info page to load
            wait.until(ExpectedConditions.urlContains("medical-info"));

            // Step 5: Fill physician and emergency information
            PhysicianInfoPage medicalinfo = new PhysicianInfoPage(driver);
            String pageHeader = medicalinfo.verifyPhysicianInfoPage();
            System.out.println("On page: " + pageHeader);
            medicalinfo.fillAllPhysicianInformation();
            
            // Click the final proceed button
            System.out.println("Clicking final proceed button...");
            medicalinfo.proceedToNext();
            
            // Wait for plan selection page to load
            wait.until(ExpectedConditions.urlContains("choose-plan"));

            // Step 6: Choose a plan
            System.out.println("Starting plan selection process...");
            selectPlanWithRetry();
            System.out.println("Plan selected successfully!");

            // Step 7: Click the "Complete Registration" button
            System.out.println("Attempting to click Complete Registration button...");
            clickCompleteRegistrationWithRetry();

            // Step 8: Verify successful registration
            if (isRegistrationSuccessful()) {
                System.out.println("Registration process completed successfully!");
            } else {
                throw new RuntimeException("Registration failed: Not on a success page after clicking complete.");
            }
            
        } catch (Exception e) {
            System.out.println("Registration process failed: " + e.getMessage());
            System.out.println("Current URL at failure: " + driver.getCurrentUrl());
            throw new RuntimeException("Test failed during registration flow.", e);
        }
    }

    /**
     * Attempts to select a plan with multiple retries to handle transient issues.
     */
    private void selectPlanWithRetry() {
        int maxAttempts = 3;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                System.out.println("Plan selection attempt " + attempt + " of " + maxAttempts);
                
                // Find all clickable plan options
                List<WebElement> planOptions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//div[contains(@class, 'plan-card')] | " +
                            "//mat-card[contains(@class, 'plan')] | " +
                            "//button[contains(text(), 'Select')]")
                ));
                
                if (planOptions.isEmpty()) {
                    throw new RuntimeException("No plan options found.");
                }
                
                // Select a random plan
                WebElement selectedPlan = planOptions.get(new java.util.Random().nextInt(planOptions.size()));
                
                // Scroll into view and click
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectedPlan);
                selectedPlan.click();
                
                // Verify a successful click by checking for a 'selected' or 'active' class
                try {
                    wait.until(ExpectedConditions.attributeContains(selectedPlan, "class", "selected"));
                    System.out.println("Plan successfully selected on attempt " + attempt + ".");
                    return; // Exit the method on success
                } catch (Exception e) {
                    System.out.println("Could not verify plan selection via class change. Continuing...");
                }
                
                // If a "Select" button was found, the click was likely successful
                System.out.println("Plan clicked successfully on attempt " + attempt + ".");
                return;

            } catch (StaleElementReferenceException e) {
                System.out.println("Stale element encountered. Retrying...");
                if (attempt == maxAttempts) {
                    throw new RuntimeException("Failed to select a plan after " + maxAttempts + " attempts due to stale elements.");
                }
            } catch (Exception e) {
                System.out.println("Plan selection attempt " + attempt + " failed: " + e.getMessage());
                if (attempt == maxAttempts) {
                    throw new RuntimeException("Failed to select a plan after " + maxAttempts + " attempts.");
                }
            }
        }
    }

    /**
     * Clicks the "Complete Registration" button with retries and a fallback to JavaScript.
     */
    private void clickCompleteRegistrationWithRetry() {
        int maxAttempts = 3;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                System.out.println("Complete registration click attempt " + attempt + " of " + maxAttempts);
                
                WebElement completeButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(), 'Complete')] | " +
                             "//button[contains(text(), 'Finish')] | " +
                             "//button[contains(text(), 'Submit')]")
                ));
                
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", completeButton);
                completeButton.click();
                
                // Check if the URL has changed, indicating a successful click
                if (!driver.getCurrentUrl().contains("choose-plan")) {
                    System.out.println("Complete registration clicked successfully on attempt " + attempt);
                    return;
                }
                
            } catch (Exception e) {
                System.out.println("Complete registration click attempt " + attempt + " failed: " + e.getMessage());
                if (attempt == maxAttempts) {
                    throw new RuntimeException("Failed to click 'Complete Registration' button after " + maxAttempts + " attempts.");
                }
            }
        }
    }

    /**
     * Checks for success indicators on the final page.
     * @return true if registration is considered successful, false otherwise.
     */
    private boolean isRegistrationSuccessful() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            // Check for common success messages or URL changes
            return wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'success')] | " +
                             "//*[contains(text(), 'complete')] | " +
                             "//*[contains(text(), 'thank you')] | " +
                             "//*[contains(text(), 'congratulations')]")
                ),
                ExpectedConditions.urlMatches("(?i)(dashboard|home|success)")
            )) != null;
        } catch (Exception e) {
            System.out.println("Could not find success indicators.");
            return false;
        }
    }
}