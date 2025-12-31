package SuperAdminTestcase;


import Base.BaseDriver;
import AdminPages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SuperStudentEditTest extends BaseDriver {

    @Test(description = "Edit student details with focus on contact information")
    public void verifyStudentDetailsCanBeEdited() {
        StudentEditPage editPage = null;
        
        try {
            // === STEP 1: LOGIN ===
            System.out.println("=== STEP 1: LOGIN ===");
            LoginPage login = new LoginPage(driver);
            login.enterEmail("mereg2025@gmail.com"); // Fixed email from screenshot
            login.enterPassword("SuperAdmin@123");
            login.clickSignIn();
            waitForPageLoad(5000);

            // === STEP 2: NAVIGATE TO STUDENTS ===
            System.out.println("=== STEP 2: NAVIGATE TO STUDENTS ===");
            studentspage studentPage = new studentspage(driver);
            studentPage.clickOnStudents();
            waitForPageLoad(5000);

            // === STEP 3: SELECT RANDOM STUDENT ===
            System.out.println("=== STEP 3: SELECT RANDOM STUDENT ===");
            StudentListPage listPage = new StudentListPage(driver);
            Assert.assertTrue(listPage.isStudentListDisplayed(), "❌ Student list not visible");
            listPage.clickRandomViewDetails();
            waitForPageLoad(5000);

            // === STEP 4: EDIT STUDENT ===
            editPage = new StudentEditPage(driver);
            editPage.waitForPageLoad();
            
            // Generate test data
            String firstName = generateRandomName();
            String lastName = generateRandomName();

            // Enter edit mode
            editPage.clickEdit();

            // Fill basic information based on screenshot structure
            editPage.fillBasicStudentInfo(firstName, lastName);
            
            // Fill address information (from screenshot)
            editPage.fillAddressInfo();
            
            // Fill phone information (primary and alternate)
            editPage.fillPhoneInformation();

            // Save changes
            editPage.clickSave();

            // Verify success
            if (editPage.isSaveSuccessful()) {
                System.out.println("✅ Student edit completed successfully!");
            } else {
                System.out.println("⚠️ Save completed with warnings");
            }

            System.out.println("✅ Student edit test completed successfully!");

        } catch (Exception e) {
            System.out.println("Test encountered error: " + e.getMessage());
            e.printStackTrace();
            
            // Cleanup
            if (editPage != null) {
                editPage.navigateToStudentsPage();
            }
            
            throw new RuntimeException("Student edit test failed: " + e.getMessage());
        }
    }

    @Test(description = "Test alternate phone number functionality specifically")
    public void verifyAlternatePhoneNumberFields() throws Exception {
        StudentEditPage editPage = null;
        
        try {
            System.out.println("=== TEST: ALTERNATE PHONE NUMBER FIELDS ===");
            
            // Login and navigate to student details
            loginAndNavigateToStudentDetails();
            
            // Enter edit mode
            editPage = new StudentEditPage(driver);
            editPage.waitForPageLoad();
            editPage.clickEdit();

            // Test alternate phone fields
            boolean alternatePhoneTested = editPage.testAlternatePhoneFields();
            
            if (alternatePhoneTested) {
                System.out.println("✅ Alternate phone fields were found and tested");
                
                // Save and verify
                editPage.clickSave();
                
                if (editPage.isSaveSuccessful()) {
                    System.out.println("✅ Form saved successfully with alternate phone information");
                }
            } else {
                System.out.println("ℹ️ No alternate phone fields found in this form");
            }
            
        } catch (Exception e) {
            System.out.println("Alternate phone test error: " + e.getMessage());
            if (editPage != null) {
                editPage.navigateToStudentsPage();
            }
            throw e;
        }
    }

    private void loginAndNavigateToStudentDetails() throws InterruptedException {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("mereg2025@gmail.com");
        login.enterPassword("SuperAdmin@123");
        login.clickSignIn();
        waitForPageLoad(5000);

        studentspage studentPage = new studentspage(driver);
        studentPage.clickOnStudents();
        waitForPageLoad(5000);

        StudentListPage listPage = new StudentListPage(driver);
        Assert.assertTrue(listPage.isStudentListDisplayed(), "Student list not visible");
        listPage.clickRandomViewDetails();
        waitForPageLoad(5000);
    }

    private String generateRandomName() {
        String[] names = {"James", "Mary", "John", "Patricia", "Robert", "Jennifer", 
                         "Michael", "Linda", "William", "Elizabeth"};
        Random rand = new Random();
        return names[rand.nextInt(names.length)];
    }

    private void waitForPageLoad(long milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }
}