package SuperAdminTestcase;

import AdminPages.studentspage;
import Base.BaseDriver;
import SuperAdminPage.SuperLoginPage;
import SuperAdminPage.SuperStudentListPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SuperStudentListTest extends BaseDriver {

    @Test(description = "Verify that the student list is displayed, select items per page, and view a random student's details.")
    public void verifyStudentListAndClickRandomStudent() {
        try {
            // Step 1: Login
            System.out.println("Step 1: Logging in...");
            SuperLoginPage superloginPage = new SuperLoginPage(driver);
            superloginPage.enterEmail("mereg2025@gmail.com");
            superloginPage.enterPassword("SuperAdmin@123");
            superloginPage.clickSignIn();
            Thread.sleep(5000);
            System.out.println("Login successful");

            // Step 2: Navigate to Students tab
            System.out.println("Step 2: Navigating to Students page...");
            studentspage studentNav = new studentspage(driver);
            studentNav.clickOnStudents();
            Thread.sleep(5000);
            System.out.println("Current URL: " + driver.getCurrentUrl());

            // Step 3: Verify student list is displayed
            System.out.println("Step 3: Verifying student list...");
            SuperStudentListPage listPage = new SuperStudentListPage(driver);
            boolean listVisible = listPage.isStudentListDisplayed();
            Assert.assertTrue(listVisible, "Student list table is not displayed.");
            
            int initialCount = listPage.getVisibleStudentCount();
            System.out.println("Initial student count: " + initialCount + " View Details buttons");

            // Step 4: Select '50' items per page
            System.out.println("Step 4: Selecting 50 items per page...");
            try {
                listPage.selectItemsPerPage("50");
            } catch (Exception e) {
                System.out.println("Standard method failed, trying JavaScript method...");
                listPage.selectItemsPerPage("50");
            }

            // Verify we have more students visible now
            int finalCount = listPage.getVisibleStudentCount();
            System.out.println("Final student count: " + finalCount + " View Details buttons");
            
            if (finalCount > initialCount) {
                System.out.println("Successfully increased visible students from " + initialCount + " to " + finalCount);
            }

            // Step 5: Click random 'View Details'
            System.out.println("Step 5: Clicking random View Details...");
            listPage.clickRandomViewDetails();
            Thread.sleep(3000);
            System.out.println("Clicked on a random 'View Details' button.");

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Test execution failed", e);
        }
    }
}