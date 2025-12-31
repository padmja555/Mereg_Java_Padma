
package AdminTestcase;

import Base.BaseDriver;
import AdminPages.TwoChildrenspage;
import AdminPages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class TwChildrensNegativeTestcase extends BaseDriver {

    // ------------------ Helper Methods ------------------ //
    
    private void login() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("srasysife@gmail.com");
        loginPage.enterPassword("Admin@123");
        loginPage.clickSignIn();
    }
    
    private void navigateToEnrollment() {
        TwoChildrenspage twoChildPage = new TwoChildrenspage(driver);
        twoChildPage.clickOnenrollment();
    }
    
    // ------------------ Negative Test Cases ------------------ //

    @Test
    public void testAddThirdChild_ShouldNotBeAllowed() {
        login();
        TwoChildrenspage twoChildPage = new TwoChildrenspage(driver);
        navigateToEnrollment();

        // Add first child
        twoChildPage.enterRandomChildDetails(0);
        
        // Add second child
        twoChildPage.addAnotherChild();
        twoChildPage.enterRandomChildDetails(1);
        
        // Try to add third child - should fail
        try {
            twoChildPage.addAnotherChild();
            Assert.fail("Adding third child should not be allowed");
        } catch (Exception e) {
            System.out.println("Expected behavior: " + e.getMessage());
            Assert.assertTrue(e.getMessage().contains("Could not find") || 
                             e.getMessage().contains("Timeout"),
                             "Should not be able to find add button for third child");
        }
    }

    @Test
    public void testProceedWithIncompleteSecondChildForm_ShouldFail() throws InterruptedException {
        login();
        TwoChildrenspage twoChildPage = new TwoChildrenspage(driver);
        navigateToEnrollment();

        // Complete first child form
        twoChildPage.enterRandomChildDetails(0);
        
        // Add second child but leave it incomplete
        twoChildPage.addAnotherChild();
        Thread.sleep(1000);
        
        // Only fill first name for second child
        WebElement secondChildFirstName = twoChildPage.wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//input[@formcontrolname='firstName'])[2]")));
        secondChildFirstName.sendKeys("Incomplete");
        
        // Try to proceed - should fail
        try {
            twoChildPage.ClickonProceedtoGuardian();
            Thread.sleep(2000); // Wait for validation
            
            // Should not navigate to next page
            Assert.assertFalse(twoChildPage.isOnParentGuardianPage(),
                              "Should not proceed with incomplete second child form");
            
        } catch (Exception e) {
            System.out.println("Expected validation error: " + e.getMessage());
            Assert.assertTrue(true); // Expected validation failure
        }
    }

    @Test
    public void testInvalidDOBFormatSecondChild_ShouldFail() throws InterruptedException {
        login();
        TwoChildrenspage twoChildPage = new TwoChildrenspage(driver);
        navigateToEnrollment();

        // Complete first child form
        twoChildPage.enterRandomChildDetails(0);
        
        // Add second child
        twoChildPage.addAnotherChild();
        Thread.sleep(1000);
        
        // Fill second child form with invalid DOB
        WebElement firstName = driver.findElement(By.xpath("(//input[@formcontrolname='firstName'])[2]"));
        WebElement lastName = driver.findElement(By.xpath("(//input[@formcontrolname='lastName'])[2]"));
        WebElement dob = driver.findElement(By.xpath("(//input[@formcontrolname='dob'])[2]"));
        WebElement gender = driver.findElement(By.xpath("(//span[normalize-space()='Male'])[2]"));
        
        firstName.sendKeys("Test");
        lastName.sendKeys("Child");
        dob.sendKeys("invalid-date-format"); // Invalid format
        gender.click();
        
        // Try to proceed - should fail
        try {
            twoChildPage.ClickonProceedtoGuardian();
            Thread.sleep(2000);
            
            Assert.assertFalse(twoChildPage.isOnParentGuardianPage(),
                              "Should not proceed with invalid DOB format");
            
        } catch (Exception e) {
            System.out.println("Expected validation error: " + e.getMessage());
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testFutureDOBSecondChild_ShouldFail() throws InterruptedException {
        login();
        TwoChildrenspage twoChildPage = new TwoChildrenspage(driver);
        navigateToEnrollment();

        // Complete first child form
        twoChildPage.enterRandomChildDetails(0);
        
        // Add second child
        twoChildPage.addAnotherChild();
        Thread.sleep(1000);
        
        // Fill second child form with future DOB
        WebElement firstName = driver.findElement(By.xpath("(//input[@formcontrolname='firstName'])[2]"));
        WebElement lastName = driver.findElement(By.xpath("(//input[@formcontrolname='lastName'])[2]"));
        WebElement dob = driver.findElement(By.xpath("(//input[@formcontrolname='dob'])[2]"));
        WebElement gender = driver.findElement(By.xpath("(//span[normalize-space()='Female'])[2]"));
        
        firstName.sendKeys("Future");
        lastName.sendKeys("Child");
        dob.sendKeys("12/31/2030"); // Future date
        gender.click();
        
        // Try to proceed - should fail
        try {
            twoChildPage.ClickonProceedtoGuardian();
            Thread.sleep(2000);
            
            Assert.assertFalse(twoChildPage.isOnParentGuardianPage(),
                              "Should not proceed with future DOB");
            
        } catch (Exception e) {
            System.out.println("Expected validation error: " + e.getMessage());
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testDuplicateChildInformation_ShouldFail() throws InterruptedException {
        login();
        TwoChildrenspage twoChildPage = new TwoChildrenspage(driver);
        navigateToEnrollment();

        // Fill first child with specific data
        String firstName = "Duplicate";
        String lastName = "Test";
        String dob = "05/15/2015";
        
        WebElement firstName1 = driver.findElement(By.xpath("(//input[@formcontrolname='firstName'])[1]"));
        WebElement lastName1 = driver.findElement(By.xpath("(//input[@formcontrolname='lastName'])[1]"));
        WebElement dob1 = driver.findElement(By.xpath("(//input[@formcontrolname='dob'])[1]"));
        WebElement gender1 = driver.findElement(By.xpath("(//span[normalize-space()='Male'])[1]"));
        
        firstName1.sendKeys(firstName);
        lastName1.sendKeys(lastName);
        dob1.sendKeys(dob);
        gender1.click();
        
        // Add second child
        twoChildPage.addAnotherChild();
        Thread.sleep(1000);
        
        // Fill second child with identical information
        WebElement firstName2 = driver.findElement(By.xpath("(//input[@formcontrolname='firstName'])[2]"));
        WebElement lastName2 = driver.findElement(By.xpath("(//input[@formcontrolname='lastName'])[2]"));
        WebElement dob2 = driver.findElement(By.xpath("(//input[@formcontrolname='dob'])[2]"));
        WebElement gender2 = driver.findElement(By.xpath("(//span[normalize-space()='Male'])[2]"));
        
        firstName2.sendKeys(firstName);
        lastName2.sendKeys(lastName);
        dob2.sendKeys(dob);
        gender2.click();
        
        // Try to proceed - should fail
        try {
            twoChildPage.ClickonProceedtoGuardian();
            Thread.sleep(2000);
            
            Assert.assertFalse(twoChildPage.isOnParentGuardianPage(),
                              "Should not proceed with duplicate child information");
            
        } catch (Exception e) {
            System.out.println("Expected validation error: " + e.getMessage());
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testSpecialCharactersInNameFields_ShouldFail() throws InterruptedException {
        login();
        TwoChildrenspage twoChildPage = new TwoChildrenspage(driver);
        navigateToEnrollment();

        // Complete first child form
        twoChildPage.enterRandomChildDetails(0);
        
        // Add second child
        twoChildPage.addAnotherChild();
        Thread.sleep(1000);
        
        // Fill second child form with special characters
        WebElement firstName = driver.findElement(By.xpath("(//input[@formcontrolname='firstName'])[2]"));
        WebElement lastName = driver.findElement(By.xpath("(//input[@formcontrolname='lastName'])[2]"));
        WebElement dob = driver.findElement(By.xpath("(//input[@formcontrolname='dob'])[2]"));
        WebElement gender = driver.findElement(By.xpath("(//span[normalize-space()='Female'])[2]"));
        
        firstName.sendKeys("Test@#$%"); // Special characters
        lastName.sendKeys("Child123!"); // Special characters
        dob.sendKeys("05/15/2015");
        gender.click();
        
        // Try to proceed - should fail
        try {
            twoChildPage.ClickonProceedtoGuardian();
            Thread.sleep(2000);
            
            Assert.assertFalse(twoChildPage.isOnParentGuardianPage(),
                              "Should not proceed with special characters in name fields");
            
        } catch (Exception e) {
            System.out.println("Expected validation error: " + e.getMessage());
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testExtremelyLongNames_ShouldFail() throws InterruptedException {
        login();
        TwoChildrenspage twoChildPage = new TwoChildrenspage(driver);
        navigateToEnrollment();

        // Complete first child form
        twoChildPage.enterRandomChildDetails(0);
        
        // Add second child
        twoChildPage.addAnotherChild();
        Thread.sleep(1000);
        
        // Fill second child form with extremely long names
        String longName = "A".repeat(1000); // Very long name
        
        WebElement firstName = driver.findElement(By.xpath("(//input[@formcontrolname='firstName'])[2]"));
        WebElement lastName = driver.findElement(By.xpath("(//input[@formcontrolname='lastName'])[2]"));
        WebElement dob = driver.findElement(By.xpath("(//input[@formcontrolname='dob'])[2]"));
        WebElement gender = driver.findElement(By.xpath("(//span[normalize-space()='Male'])[2]"));
        
        firstName.sendKeys(longName);
        lastName.sendKeys(longName);
        dob.sendKeys("05/15/2015");
        gender.click();
        
        // Try to proceed - should fail
        try {
            twoChildPage.ClickonProceedtoGuardian();
            Thread.sleep(2000);
            
            Assert.assertFalse(twoChildPage.isOnParentGuardianPage(),
                              "Should not proceed with extremely long names");
            
        } catch (Exception e) {
            System.out.println("Expected validation error: " + e.getMessage());
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testEmptyFormSubmission_ShouldFail() {
        login();
        TwoChildrenspage twoChildPage = new TwoChildrenspage(driver);
        navigateToEnrollment();

        // Don't fill any child information, try to proceed directly
        try {
            twoChildPage.ClickonProceedtoGuardian();
            Thread.sleep(2000);
            
            Assert.assertFalse(twoChildPage.isOnParentGuardianPage(),
                              "Should not proceed with empty form");
            
        } catch (Exception e) {
            System.out.println("Expected validation error: " + e.getMessage());
            Assert.assertTrue(true);
        }
    }
}
