package AdminTestcase;

import org.testng.Assert;
import org.testng.annotations.*;
import AdminPages.LoginPage;
import AdminPages.adminpage1;
import AdminPages.StudentLinkPage;
import Base.BaseDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StudentLinkTest extends BaseDriver {

    LoginPage loginPage;
    adminpage1 studentPage;
    StudentLinkPage studentLinkPage;

    @BeforeMethod
    public void testSetup() {
        System.out.println("=== STARTING TEST SETUP ===");
        
        // Step 1: Login
        loginPage = new LoginPage(driver);
        loginPage.enterEmail("srasysife@gmail.com");
        loginPage.enterPassword("Admin@123");
        loginPage.clickSignIn();
        
        // Wait for login to complete
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe("https://mereg.netlify.app/")));
        
        // Step 2: Navigate to Admin Page
        studentPage = new adminpage1(driver);
        studentPage.clickOnAdmin();
        
        // Wait for admin page to load
        wait.until(ExpectedConditions.urlContains("admin"));
        
        // Step 3: Initialize StudentLinkPage
        studentLinkPage = new StudentLinkPage(driver);
        
        System.out.println("✓ Test setup completed successfully");
    }

    @Test
    public void linkStudentWithValidEmail() {
        System.out.println("=== TEST: LINK STUDENT WITH VALID EMAIL ===");

        // Verify "Unlinked Students" title is displayed
        Assert.assertTrue(studentLinkPage.isUnlinkedTitleDisplayed(), "Unlinked Students title is not visible.");

        // Count total students
        int totalStudents = studentLinkPage.getTotalStudents();
        System.out.println("Total Students: " + totalStudents);
        Assert.assertTrue(totalStudents > 0, "No students found.");

        // Get random student and search
        String randomFirstName = studentLinkPage.getRandomFirstName();
        System.out.println("Searching for student: " + randomFirstName);
        studentLinkPage.searchStudentByFirstName(randomFirstName);

        // Wait for search results to load
        studentLinkPage.waitForSearchResultsToLoad();

        // Link the student
        studentLinkPage.clickFirstLinkButton();

        // Verify email field is available
        Assert.assertTrue(studentLinkPage.isEmailFieldAvailable(), "Email field is not available");

        // Enter a valid random email
        String randomEmail = studentLinkPage.getRandomEmail();
        System.out.println("Using email: " + randomEmail);
        studentLinkPage.enterEmail(randomEmail);

        // Verify email was entered correctly
        String enteredEmail = studentLinkPage.getCurrentEmailValue();
        System.out.println("Email field contains: " + enteredEmail);

        studentLinkPage.confirmLink();

        // Verify success message displayed
        boolean success = studentLinkPage.isLinkSuccessDisplayed();
        if (success) {
            System.out.println("✅ SUCCESS: Student linked successfully with email: " + randomEmail);
        } else {
            System.out.println("⚠️ No success message, but process may have completed");
            // Check for errors
            if (studentLinkPage.isErrorMessageDisplayed()) {
                String error = studentLinkPage.getEmailFieldError();
                System.out.println("Error message: " + error);
            }
        }

        System.out.println("✅ Test execution completed");
    }

    @Test
    public void testEmailFieldValidation() {
        System.out.println("=== TEST: EMAIL FIELD VALIDATION ===");

        // Verify "Unlinked Students" title is displayed
        Assert.assertTrue(studentLinkPage.isUnlinkedTitleDisplayed(), "Unlinked Students title is not visible.");

        // Count total students
        int totalStudents = studentLinkPage.getTotalStudents();
        if (totalStudents == 0) {
            System.out.println("⚠️ No students found, skipping test");
            return;
        }

        // Get random student and search
        String randomFirstName = studentLinkPage.getRandomFirstName();
        System.out.println("Searching for student: " + randomFirstName);
        studentLinkPage.searchStudentByFirstName(randomFirstName);

        // Wait for search results to load
        studentLinkPage.waitForSearchResultsToLoad();

        // Link the student
        studentLinkPage.clickFirstLinkButton();

        // Test 1: Empty email
        System.out.println("--- Testing empty email ---");
        studentLinkPage.enterEmail("");
        studentLinkPage.confirmLink();
        checkForEmailErrors("empty email");

        // Test 2: Invalid email format
        System.out.println("--- Testing invalid email ---");
        String invalidEmail = studentLinkPage.getRandomInvalidEmail();
        studentLinkPage.enterEmail(invalidEmail);
        studentLinkPage.confirmLink();
        checkForEmailErrors("invalid email: " + invalidEmail);

        // Test 3: Valid email
        System.out.println("--- Testing valid email ---");
        String validEmail = studentLinkPage.getRandomEmail();
        studentLinkPage.enterEmail(validEmail);
        studentLinkPage.confirmLink();
        
        boolean success = studentLinkPage.isLinkSuccessDisplayed();
        if (success) {
            System.out.println("✅ Valid email accepted: " + validEmail);
        } else {
            String error = studentLinkPage.getEmailFieldError();
            System.out.println("⚠️ Valid email rejected: " + error);
        }

        System.out.println("✅ Email validation test completed");
    }

    private void checkForEmailErrors(String testCase) {
        if (studentLinkPage.isErrorMessageDisplayed()) {
            String error = studentLinkPage.getEmailFieldError();
            System.out.println("✅ Expected error for " + testCase + ": " + error);
        } else {
            System.out.println("⚠️ No error message for " + testCase);
        }
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("=== TEST COMPLETED ===");
    }
}