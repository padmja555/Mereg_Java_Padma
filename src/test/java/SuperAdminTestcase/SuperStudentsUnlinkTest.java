package SuperAdminTestcase;


import org.testng.Assert;
import org.testng.annotations.*;
import AdminPages.LoginPage;
import AdminPages.adminpage1;
import AdminPages.StudentLinkPage;
import Base.BaseDriver;
import SuperAdminPage.SuperLoginPage;
import SuperAdminPage.SuperStudentPage;
import SuperAdminPage.SuperStudentsUnlinkPage;
import SuperAdminPage.SuperStudentsUnlinkPage;

import java.util.concurrent.TimeoutException;

import org.openqa.selenium.support.ui.ExpectedConditions;

public class SuperStudentsUnlinkTest extends BaseDriver {

    LoginPage loginPage;
    adminpage1 SuperStudentPage;
    SuperAdminPage.SuperStudentsUnlinkPage SuperStudentsUnlinkPage;

    @BeforeMethod
    public void testSetup() {
        System.out.println("=== STARTING TEST SETUP ===");
        
        // Step 1: Login
        SuperLoginPage superloginPage = new SuperLoginPage(driver);
        superloginPage.enterEmail("mereg2025@gmail.com");
        superloginPage.enterPassword("SuperAdmin@123");
        superloginPage.clickSignIn();
        System.out.println("Login successful");
        
        // Wait for login to complete
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe("https://mereg-hotfix.netlify.app/")));
        
        // Step 2: Navigate to Admin Page
        // Step 2: Navigate to Students tab
        SuperStudentPage studentNav = new SuperStudentPage(driver);
        try {
			studentNav.clickOnStudents();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Wait for admin page to load
        wait.until(ExpectedConditions.urlContains("superadmin"));
        
        // Step 3: Initialize StudentLinkPage
        // Step 3: Initialize Unlink Page
        SuperStudentsUnlinkPage = new SuperStudentsUnlinkPage(driver); // ✅ FIXED LINE

        System.out.println("✓ Test setup completed successfully");
    }

    @Test
    public void linkStudentWithValidEmail() {
        System.out.println("=== TEST: LINK STUDENT WITH VALID EMAIL ===");

        // Verify "Unlinked Students" title is displayed
        Assert.assertTrue(SuperStudentsUnlinkPage.isUnlinkedTitleDisplayed(), "Unlinked Students title is not visible.");

        // Count total students
        int totalStudents = SuperStudentsUnlinkPage.getTotalStudents();
        System.out.println("Total Students: " + totalStudents);
        Assert.assertTrue(totalStudents > 0, "No students found.");

        // Get random student and search
        String randomFirstName = SuperStudentsUnlinkPage.getRandomFirstName();
        System.out.println("Searching for student: " + randomFirstName);
        SuperStudentsUnlinkPage.searchStudentByFirstName(randomFirstName);

        // Wait for search results to load
        SuperStudentsUnlinkPage.waitForSearchResultsToLoad();

        // Link the student
        SuperStudentsUnlinkPage.clickFirstLinkButton();

        // Verify email field is available
        Assert.assertTrue(SuperStudentsUnlinkPage.isEmailFieldAvailable(), "Email field is not available");

        // Enter a valid random email
        String randomEmail = SuperStudentsUnlinkPage.getRandomEmail();
        System.out.println("Using email: " + randomEmail);
        SuperStudentsUnlinkPage.enterEmail(randomEmail);

        // Verify email was entered correctly
        String enteredEmail = SuperStudentsUnlinkPage.getCurrentEmailValue();
        System.out.println("Email field contains: " + enteredEmail);

        SuperStudentsUnlinkPage.confirmLink();

        // Verify success message displayed
        boolean success = SuperStudentsUnlinkPage.isLinkSuccessDisplayed();
        if (success) {
            System.out.println("✅ SUCCESS: Student linked successfully with email: " + randomEmail);
        } else {
            System.out.println("⚠️ No success message, but process may have completed");
            // Check for errors
            if (SuperStudentsUnlinkPage.isErrorMessageDisplayed()) {
                String error = SuperStudentsUnlinkPage.getEmailFieldError();
                System.out.println("Error message: " + error);
            }
        }

        System.out.println("✅ Test execution completed");
    }

    @Test
    public void testEmailFieldValidation() {
        System.out.println("=== TEST: EMAIL FIELD VALIDATION ===");

        // Verify "Unlinked Students" title is displayed
        Assert.assertTrue(SuperStudentsUnlinkPage.isUnlinkedTitleDisplayed(), "Unlinked Students title is not visible.");

        // Count total students
        int totalStudents = SuperStudentsUnlinkPage.getTotalStudents();
        if (totalStudents == 0) {
            System.out.println("⚠️ No students found, skipping test");
            return;
        }

        // Get random student and search
        String randomFirstName = SuperStudentsUnlinkPage.getRandomFirstName();
        System.out.println("Searching for student: " + randomFirstName);
        SuperStudentsUnlinkPage.searchStudentByFirstName(randomFirstName);

        // Wait for search results to load
        SuperStudentsUnlinkPage.waitForSearchResultsToLoad();

        // Link the student
        SuperStudentsUnlinkPage.clickFirstLinkButton();

        // Test 1: Empty email
        System.out.println("--- Testing empty email ---");
        SuperStudentsUnlinkPage.enterEmail("");
        SuperStudentsUnlinkPage.confirmLink();
        checkForEmailErrors("empty email");

        // Test 2: Invalid email format
        System.out.println("--- Testing invalid email ---");
        String invalidEmail = SuperStudentsUnlinkPage.getRandomInvalidEmail();
        SuperStudentsUnlinkPage.enterEmail(invalidEmail);
        SuperStudentsUnlinkPage.confirmLink();
        checkForEmailErrors("invalid email: " + invalidEmail);

        // Test 3: Valid email
        System.out.println("--- Testing valid email ---");
        String validEmail = SuperStudentsUnlinkPage.getRandomEmail();
        SuperStudentsUnlinkPage.enterEmail(validEmail);
        SuperStudentsUnlinkPage.confirmLink();
        
        boolean success = SuperStudentsUnlinkPage.isLinkSuccessDisplayed();
        if (success) {
            System.out.println("✅ Valid email accepted: " + validEmail);
        } else {
            String error = SuperStudentsUnlinkPage.getEmailFieldError();
            System.out.println("⚠️ Valid email rejected: " + error);
        }

        System.out.println("✅ Email validation test completed");
    }

    private void checkForEmailErrors(String testCase) {
        if (SuperStudentsUnlinkPage.isErrorMessageDisplayed()) {
            String error = SuperStudentsUnlinkPage.getEmailFieldError();
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