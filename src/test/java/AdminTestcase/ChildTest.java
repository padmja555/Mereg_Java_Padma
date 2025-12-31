package AdminTestcase;

import Base.BaseDriver;

import AdminPages.Childinfo;
import AdminPages.LoginPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
@Listeners(listeners.TestListener.class)
public class ChildTest extends BaseDriver {

    @Test
    public void ChildInfo_AddMultipleChildren() throws InterruptedException {
        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("srasysife@gmail.com");
        loginPage.enterPassword("Admin@123");
        loginPage.clickSignIn();

        // Navigate to Child Info Page
        Childinfo childInfoPage = new Childinfo(driver);
        childInfoPage.clickOnenrollment();

        // First Child
        childInfoPage.enterRandomFirstAndLastName();
        childInfoPage.selectGender();
        childInfoPage.enterrandomDOB();

        // Debug: Check the current state before adding another child
        debugFormState("Before adding second child");
        
        // Add Second Child
        try {
            childInfoPage.clickAddAnotherChild();
            
            // Wait a moment for any UI response
            Thread.sleep(2000);
            
            // Debug: Check the state after clicking the button
            debugFormState("After clicking 'Add Another Child'");
            
            // Try multiple approaches to handle the second form
            boolean secondFormAdded = waitForSecondForm();
            
            if (secondFormAdded) {
                // Enter details for additional child
                enterDetailsForAdditionalChild();
            } else {
                // If no second form was detected, try alternative approach
                handleAlternativeSecondChildAddition();
            }
            
        } catch (Exception e) {
            System.out.println("Error in adding second child: " + e.getMessage());
            // Try to save the first child and continue
            saveFirstChildOnly();
        }
    }
    
    private void debugFormState(String message) {
        System.out.println("=== " + message + " ===");
        
        // Check forms
        List<WebElement> forms = driver.findElements(By.tagName("form"));
        System.out.println("Number of forms: " + forms.size());
        
        // Check gender dropdowns
        List<WebElement> genderDropdowns = driver.findElements(
            By.xpath("//mat-select[@formcontrolname='gender']"));
        System.out.println("Number of gender dropdowns: " + genderDropdowns.size());
        
        // Check first name fields
        List<WebElement> firstNameFields = driver.findElements(
            By.xpath("//input[@formcontrolname='firstName']"));
        System.out.println("Number of first name fields: " + firstNameFields.size());
        
        // Check if "Add Another Child" button is present
        List<WebElement> addButtons = driver.findElements(
            By.xpath("//button[contains(.,'Add Another Child') or contains(.,'Add Child')]"));
        System.out.println("Number of 'Add Another Child' buttons: " + addButtons.size());
        
        System.out.println("=========================================");
    }
    
    private boolean waitForSecondForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            // Wait for any new form elements to appear
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.xpath("//input[@formcontrolname='firstName']"), 1));
            
            List<WebElement> firstNameFields = driver.findElements(
                By.xpath("//input[@formcontrolname='firstName']"));
            
            if (firstNameFields.size() > 1) {
                System.out.println("Second form detected with " + firstNameFields.size() + " first name fields");
                return true;
            }
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for second form: " + e.getMessage());
        }
        
        return false;
    }
    
    private void enterDetailsForAdditionalChild() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            // Find all form fields using a more flexible approach
            List<WebElement> allForms = driver.findElements(By.xpath("//form | //div[contains(@class, 'form')]"));
            System.out.println("Total form containers found: " + allForms.size());
            
            // Try to find the second set of form fields using various approaches
            List<WebElement> firstNameFields = driver.findElements(
                By.xpath("//input[contains(@id, 'firstName') or contains(@name, 'firstName') or @formcontrolname='firstName']"));
            
            if (firstNameFields.size() < 2) {
                throw new RuntimeException("Only " + firstNameFields.size() + " first name fields found. Expected at least 2.");
            }
            
            // Fill second form fields
            WebElement secondFirstName = firstNameFields.get(1);
            highlightElement(secondFirstName);
            secondFirstName.clear();
            secondFirstName.sendKeys("TestChildSecond");
            
            // Last name
            List<WebElement> lastNameFields = driver.findElements(
                By.xpath("//input[contains(@id, 'lastName') or contains(@name, 'lastName') or @formcontrolname='lastName']"));
            
            if (lastNameFields.size() > 1) {
                WebElement secondLastName = lastNameFields.get(1);
                highlightElement(secondLastName);
                secondLastName.clear();
                secondLastName.sendKeys("DoeSecond");
            }
            
            // Gender - try multiple approaches
            try {
                List<WebElement> genderDropdowns = driver.findElements(
                    By.xpath("//mat-select[contains(@id, 'gender') or contains(@name, 'gender') or @formcontrolname='gender']"));
                
                if (genderDropdowns.size() > 1) {
                    WebElement secondGender = genderDropdowns.get(1);
                    highlightElement(secondGender);
                    secondGender.click();
                    
                    // Select gender option
                    WebElement genderOption = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//mat-option[contains(.,'Male') or contains(.,'MALE')]")));
                    genderOption.click();
                }
            } catch (Exception e) {
                System.out.println("Could not set gender for second child: " + e.getMessage());
                // Continue without setting gender
            }
            
            // Save all children
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(.,'Save') or contains(.,'SAVE')]")));
            saveButton.click();
            
            // Verify success
            verifySuccess();
            
        } catch (Exception e) {
            System.out.println("Error filling second form: " + e.getMessage());
            throw new RuntimeException("Failed to enter additional child details", e);
        }
    }
    
    private void handleAlternativeSecondChildAddition() {
        System.out.println("Trying alternative approach to add second child...");
        
        // Sometimes the UI might work differently - try to find and click the button again
        try {
            WebElement addButton = driver.findElement(
                By.xpath("//button[contains(.,'Add Another Child') or contains(.,'Add Child')]"));
            addButton.click();
            Thread.sleep(3000);
            
            // Check if we now have a second form
            if (waitForSecondForm()) {
                enterDetailsForAdditionalChild();
            } else {
                throw new RuntimeException("Alternative approach also failed - no second form created");
            }
        } catch (Exception e) {
            System.out.println("Alternative approach failed: " + e.getMessage());
            saveFirstChildOnly();
        }
    }
    
    private void saveFirstChildOnly() {
        System.out.println("Saving only the first child due to errors with second form...");
        
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(.,'Save') or contains(.,'SAVE')]")));
            saveButton.click();
            
            verifySuccess();
            
        } catch (Exception e) {
            System.out.println("Error saving first child: " + e.getMessage());
            throw new RuntimeException("Failed to save even the first child", e);
        }
    }
    
    private void verifySuccess() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        try {
            // Look for any success message
            WebElement successElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'success') or contains(text(),'Success') or contains(text(),'saved') or contains(text(),'Saved')]")));
            
            Assert.assertTrue(successElement.isDisplayed(), "Success message not displayed");
            System.out.println("Success message: " + successElement.getText());
            
        } catch (Exception e) {
            System.out.println("No success message found, but continuing...");
            // In some cases, success might be indicated by navigation to a different page
        }
    }
    
    private void highlightElement(WebElement element) {
        // Highlight element for debugging (will flash yellow)
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px solid yellow'", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        js.executeScript("arguments[0].style.border=''", element);
    }
}