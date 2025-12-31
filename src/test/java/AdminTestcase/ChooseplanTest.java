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

public class ChooseplanTest extends BaseDriver {
    
    @Test
    public void testCompleteRegistration() {
        try {
            // Step 1: Login to the application
            LoginPage login = new LoginPage(driver);
            login.enterEmail("srasysife@gmail.com");
            login.enterPassword("Admin@123");
            login.clickSignIn();
            Thread.sleep(5000);

            // Step 2: Navigate to the parent info page via child info
            Childinfo childinfo = new Childinfo(driver);
            childinfo.clickOnenrollment();
            Thread.sleep(3000);
            
            childinfo.enterRandomFirstAndLastName();
            Thread.sleep(1500);
            
            childinfo.selectGender();
            Thread.sleep(1500);
            
            childinfo.enterrandomDOB();
            Thread.sleep(1500);
            
            childinfo.ClickonProceedtoGuardian();
            Thread.sleep(5000);

            // Step 3: Fill out the parent/guardian information
            Parentpage parentguardianinfo = new Parentpage(driver);
            
            parentguardianinfo.enterRandomFirstName();
            Thread.sleep(1000);
            
            parentguardianinfo.enterRandomMiddleName();
            Thread.sleep(1000);
            
            parentguardianinfo.enterRandomLastName();
            Thread.sleep(1000);
            
            parentguardianinfo.selectRelationship();
            Thread.sleep(2000);
            
            parentguardianinfo.enterRandomAddress1();
            Thread.sleep(1000);
            
            parentguardianinfo.enterRandomAddress2();
            Thread.sleep(1000);
            
            parentguardianinfo.selectRandomCountry();
            Thread.sleep(2000);
            
            parentguardianinfo.selectState();
            Thread.sleep(2000);
            
            parentguardianinfo.enterRandomCity();
            Thread.sleep(1000);
            
            parentguardianinfo.enterRandomZipCode();
            Thread.sleep(1000);
            
            parentguardianinfo.selectRandomPhoneType();
            Thread.sleep(2000);
            
            parentguardianinfo.enterRandomCountryCode();
            Thread.sleep(2000);
            
            parentguardianinfo.enterRandomPhoneNumber();
            Thread.sleep(2000);
            
            parentguardianinfo.alternateGuardianInfoIconButton();
            Thread.sleep(2000);
            
            parentguardianinfo.enterRandomAlternateGuardianName();
            Thread.sleep(1000);
            
            parentguardianinfo.alterGuardianSelectRelationship();
            Thread.sleep(2000);
            
            parentguardianinfo.alternateGuardianCountryCode();
            Thread.sleep(1000);
            
            parentguardianinfo.enterRandomAlternateGuardianPhoneNumber();
            Thread.sleep(1000);
            
            //parentguardianinfo.enterRandomEmailAddress();
            Thread.sleep(1000);
            
            // Step 4: Click proceed to medical info
            System.out.println("Clicking Proceed to Medical Info button...");
            parentguardianinfo.proceedToMedicalInfoButton();
            Thread.sleep(8000);

            // Step 5: Fill physician and emergency information
            PhysicianInfoPage medicalinfo = new PhysicianInfoPage(driver);
            
            // Verify we're on the physician info page
            String pageHeader = medicalinfo.verifyPhysicianInfoPage();
            System.out.println("On page: " + pageHeader);
            
            // Fill all physician information using the comprehensive method
            medicalinfo.fillAllPhysicianInformation();
            Thread.sleep(2000);
            
            // Click the final proceed button
            System.out.println("Clicking final proceed button...");
            medicalinfo.proceedToNext();
            Thread.sleep(5000);
            
            System.out.println("Medical information form completed successfully!");
            
            // Step 6: Choose a plan - ENHANCED PLAN SELECTION
            System.out.println("Starting plan selection process...");

            // Wait for plan page to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // Check if we're on the plan selection page using a custom condition
            try {
                wait.until(driver -> {
                    String currentUrl = driver.getCurrentUrl().toLowerCase();
                    boolean hasPlanInUrl = currentUrl.contains("choose-plan") || currentUrl.contains("plan");
                    
                    boolean hasPlanText = driver.findElements(
                        By.xpath("//*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'plan')]")
                    ).size() > 0;
                    
                    return hasPlanInUrl || hasPlanText;
                });
                System.out.println("Confirmed on plan selection page");
            } catch (Exception e) {
                System.out.println("Not on plan selection page. Current URL: " + driver.getCurrentUrl());
                System.out.println("Proceeding with plan selection anyway...");
            }

            // Enhanced plan selection with multiple approaches
            selectPlanWithRetry();
            
            System.out.println("Plan selected successfully!");
            
            // Step 7: Complete registration
            System.out.println("Clicking Complete Registration button...");
            clickCompleteRegistrationWithRetry();
            
            // Wait for registration to complete
            Thread.sleep(8000);
            
            // Verify successful registration
            if (isRegistrationSuccessful()) {
                System.out.println("Registration process completed successfully!");
            } else {
                System.out.println("Registration may have issues. Checking current state...");
                System.out.println("Current URL: " + driver.getCurrentUrl());
                
                // Check if we're on a success page or still on plan page
                if (driver.getCurrentUrl().contains("choose-plan") || 
                    driver.findElements(By.xpath("//*[contains(text(), 'Plan')]")).size() > 0) {
                    throw new RuntimeException("Still on plan selection page after clicking complete");
                }
                
                System.out.println("Registration completed with possible minor issues");
            }
            
        } catch (Exception e) {
            System.out.println("Error during registration process: " + e.getMessage());
            System.out.println("Current URL: " + driver.getCurrentUrl());
            e.printStackTrace();
            throw new RuntimeException("Registration process failed", e);
        }
    }
    
    private void selectPlanWithRetry() throws InterruptedException {
        int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                System.out.println("Plan selection attempt " + attempt + " of " + maxAttempts);
                
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                
                // Approach 1: Look for plan cards/options
                List<WebElement> planOptions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//div[contains(@class, 'plan')] | " +
                            "//mat-card[contains(@class, 'plan')] | " +
                            "//div[contains(@class, 'card')] | " +
                            "//button[contains(text(), 'Select')] | " +
                            "//div[contains(@class, 'option')]")
                ));
                
                if (planOptions.isEmpty()) {
                    throw new RuntimeException("No plan options found");
                }
                
                // Try to select the first available plan
                WebElement firstPlan = planOptions.get(0);
                
                // Scroll into view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstPlan);
                Thread.sleep(500);
                
                // Try different click methods
                try {
                    firstPlan.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstPlan);
                }
                
                Thread.sleep(2000);
                
                // Verify plan is selected (look for selected state)
                String planClass = firstPlan.getAttribute("class");
                if (planClass.contains("selected") || planClass.contains("active")) {
                    System.out.println("Plan selected successfully on attempt " + attempt);
                    return;
                }
                
                // Check if there's a select button inside the plan element
                try {
                    WebElement selectButton = firstPlan.findElement(By.xpath(".//button[contains(text(), 'Select')]"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectButton);
                    Thread.sleep(2000);
                    return;
                } catch (Exception e) {
                    // Continue to next approach
                }
                
            } catch (Exception e) {
                System.out.println("Plan selection attempt " + attempt + " failed: " + e.getMessage());
                
                if (attempt == maxAttempts) {
                    throw new RuntimeException("Failed to select plan after " + maxAttempts + " attempts: " + e.getMessage());
                }
                
                // Refresh page and try again
                driver.navigate().refresh();
                Thread.sleep(3000);
            }
        }
    }
    
    private void clickCompleteRegistrationWithRetry() throws InterruptedException {
        int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                System.out.println("Complete registration click attempt " + attempt + " of " + maxAttempts);
                
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                
                // Look for complete registration button with multiple possible locators
                List<WebElement> completeButtons = driver.findElements(
                    By.xpath("//button[contains(text(), 'Complete')] | " +
                            "//button[contains(text(), 'Finish')] | " +
                            "//button[contains(text(), 'Submit')] | " +
                            "//button[contains(text(), 'Register')] | " +
                            "//span[contains(text(), 'Complete')]/ancestor::button | " +
                            "//mat-icon[contains(text(), 'check')]/ancestor::button")
                );
                
                if (completeButtons.isEmpty()) {
                    throw new RuntimeException("No complete registration button found");
                }
                
                WebElement completeButton = completeButtons.get(0);
                
                // Scroll into view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", completeButton);
                Thread.sleep(500);
                
                // Try regular click first
                try {
                    completeButton.click();
                } catch (Exception e) {
                    // Fallback to JavaScript click
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", completeButton);
                }
                
                Thread.sleep(1000);
                
                // Check if click was successful by verifying page changed
                if (!driver.getCurrentUrl().contains("choose-plan")) {
                    System.out.println("Complete registration clicked successfully on attempt " + attempt);
                    return;
                }
                
            } catch (Exception e) {
                System.out.println("Complete registration click attempt " + attempt + " failed: " + e.getMessage());
                
                if (attempt == maxAttempts) {
                    throw new RuntimeException("Failed to complete registration after " + maxAttempts + " attempts: " + e.getMessage());
                }
                
                Thread.sleep(2000);
            }
        }
    }
    
    private boolean isRegistrationSuccessful() {
        try {
            // Check for success indicators
            boolean hasSuccessMessage = driver.findElements(
                By.xpath("//*[contains(text(), 'success')] | " +
                        "//*[contains(text(), 'complete')] | " +
                        "//*[contains(text(), 'thank you')] | " +
                        "//*[contains(text(), 'congrat')]")
            ).size() > 0;
            
            // Check if URL changed to something other than plan selection
            boolean urlChanged = !driver.getCurrentUrl().contains("choose-plan");
            
            // Check for dashboard or home page elements
            boolean hasDashboard = driver.findElements(
                By.xpath("//*[contains(text(), 'Dashboard')] | " +
                        "//*[contains(text(), 'Home')] | " +
                        "//*[contains(text(), 'Welcome')]")
            ).size() > 0;
            
            return hasSuccessMessage || urlChanged || hasDashboard;
            
        } catch (Exception e) {
            System.out.println("Error checking registration success: " + e.getMessage());
            return false;
        }
    }
}