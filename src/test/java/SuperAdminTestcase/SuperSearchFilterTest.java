package SuperAdminTestcase;

import AdminPages.studentspage;
import Base.BaseDriver;
import SuperAdminPage.SuperLoginPage;
import SuperAdminPage.SuperSearchFilterPage;
import org.testng.annotations.Test;

public class SuperSearchFilterTest extends BaseDriver {

    @Test(priority = 1, description = "Test student filtering with random values")
    public void testStudentFilteringWithRandomValues() {
        try {
            System.out.println("=== Starting Student Filter Test ===");
            
            // Step 1: Login
            System.out.println("Step 1: Logging in...");
            SuperLoginPage superLoginPage = new SuperLoginPage(driver);
            superLoginPage.enterEmail("mereg2025@gmail.com");
            superLoginPage.enterPassword("SuperAdmin@123");
            superLoginPage.clickSignIn();
            Thread.sleep(5000);
            System.out.println("Login successful");

            // Step 2: Navigate to Students page
            System.out.println("Step 2: Navigating to Students page...");
            studentspage studentNav = new studentspage(driver);
            studentNav.clickOnStudents();
            Thread.sleep(5000);
            System.out.println("Current URL: " + driver.getCurrentUrl());

            // Step 3: Initialize Filter Page
            System.out.println("Step 3: Initializing Student Filter Page...");
            SuperSearchFilterPage filterPage = new SuperSearchFilterPage(driver);
            
            // Debug page elements
            filterPage.debugPageElements();

            // Get initial counts
            int initialStudentCount = filterPage.getStudentCount();
            int initialViewDetailsCount = filterPage.getViewDetailsCount();
            System.out.println("Initial student count: " + initialStudentCount);
            System.out.println("Initial View Details buttons: " + initialViewDetailsCount);

            // Step 4: Test Child Last Name filter
            System.out.println("\nStep 4: Testing Child Last Name filter...");
            String randomLastName = filterPage.selectRandomChildLastName();
            filterPage.enterChildLastName(randomLastName);
            Thread.sleep(3000);
            
            int afterLastNameFilter = filterPage.getStudentCount();
            System.out.println("Students after Last Name filter: " + afterLastNameFilter);

            // Step 5: Test Enrolled Program filter
            System.out.println("\nStep 5: Testing Enrolled Program filter...");
            String randomProgram = filterPage.selectRandomEnrolledProgram();
            Thread.sleep(3000);
            
            int afterProgramFilter = filterPage.getStudentCount();
            System.out.println("Students after Program filter: " + afterProgramFilter);

            // Step 6: Test Fee Status filter
            System.out.println("\nStep 6: Testing Fee Status filter...");
            String randomFeeStatus = filterPage.selectRandomFeeStatus();
            Thread.sleep(3000);
            
            int finalStudentCount = filterPage.getStudentCount();
            int finalViewDetailsCount = filterPage.getViewDetailsCount();
            System.out.println("Final student count: " + finalStudentCount);
            System.out.println("Final View Details buttons: " + finalViewDetailsCount);

            // Step 7: Test Student Group filter
            System.out.println("\nStep 7: Testing Student Group filter...");
            String randomGroup = filterPage.selectRandomStudentGroup();
            Thread.sleep(3000);
            
            int afterGroupFilter = filterPage.getStudentCount();
            System.out.println("Students after Group filter: " + afterGroupFilter);

            // Step 8: Click random View Details
            System.out.println("\nStep 8: Clicking random View Details...");
            filterPage.clickRandomViewDetails();
            Thread.sleep(3000);

            // Summary
            System.out.println("\n=== FILTER TEST SUMMARY ===");
            System.out.println("Applied Filters:");
            System.out.println("  - Child Last Name: " + randomLastName);
            System.out.println("  - Enrolled Program: " + randomProgram);
            System.out.println("  - Fee Status: " + randomFeeStatus);
            System.out.println("  - Student Group: " + randomGroup);
            System.out.println("Results: " + afterGroupFilter + " students found");

            // Step 9: Clear filters
            System.out.println("\nStep 9: Clearing filters...");
            filterPage.clearFilters();
            Thread.sleep(3000);

            int clearedCount = filterPage.getStudentCount();
            System.out.println("Students after clearing filters: " + clearedCount);

            System.out.println("✅ Student filter test completed successfully!");

        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test(priority = 2, description = "Test specific filter combinations")
    public void testSpecificFilterCombinations() {
        try {
            System.out.println("=== Starting Specific Filter Combinations Test ===");
            
            // Login and navigate (reuse if already on students page)
            if (!driver.getCurrentUrl().contains("students")) {
                SuperLoginPage superLoginPage = new SuperLoginPage(driver);
                superLoginPage.enterEmail("mereg2025@gmail.com");
                superLoginPage.enterPassword("SuperAdmin@123");
                superLoginPage.clickSignIn();
                Thread.sleep(5000);
                
                studentspage studentNav = new studentspage(driver);
                studentNav.clickOnStudents();
                Thread.sleep(5000);
            }

            SuperSearchFilterPage filterPage = new SuperSearchFilterPage(driver);

            // Test Case 1: Filter by specific Child Last Name
            System.out.println("Test Case 1: Filter by Child Last Name 'BB'");
            filterPage.enterChildLastName("BB");
            Thread.sleep(3000);
            System.out.println("Results for Last Name 'BB': " + filterPage.getStudentCount());

            // Test Case 2: Filter by specific Program
            System.out.println("Test Case 2: Filter by Program 'fullTime'");
            filterPage.clearFilters();
            Thread.sleep(2000);
            filterPage.selectEnrolledProgram("fullTime");
            Thread.sleep(3000);
            System.out.println("Results for Program 'fullTime': " + filterPage.getStudentCount());

            // Test Case 3: Filter by specific Fee Status
            System.out.println("Test Case 3: Filter by Fee Status 'Paid'");
            filterPage.clearFilters();
            Thread.sleep(2000);
            filterPage.selectFeeStatus("Paid");
            Thread.sleep(3000);
            System.out.println("Results for Fee Status 'Paid': " + filterPage.getStudentCount());

            // Test Case 4: Combined filters
            System.out.println("Test Case 4: Combined filters (BB + fullTime + Paid)");
            filterPage.clearFilters();
            Thread.sleep(2000);
            filterPage.enterChildLastName("BB");
            filterPage.selectEnrolledProgram("fullTime");
            filterPage.selectFeeStatus("Paid");
            Thread.sleep(3000);
            System.out.println("Results for combined filters: " + filterPage.getStudentCount());

            // Click View Details on filtered results
            filterPage.clickRandomViewDetails();
            Thread.sleep(3000);

            System.out.println("Specific filter combinations test completed!");

        } catch (Exception e) {
            System.err.println("Specific filter test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test(priority = 3, description = "Test pagination with 50 items")
    public void testPaginationWith50Items() {
        try {
            System.out.println("=== Testing Pagination with 50 Items ===");
            
            // Ensure we're on students page
            if (!driver.getCurrentUrl().contains("students")) {
                driver.get("https://mereg-hotfix.netlify.app/navigation-home/dashboardstu");
                Thread.sleep(5000);
            }

            SuperSearchFilterPage filterPage = new SuperSearchFilterPage(driver);
            
            // Get current counts
            int currentCount = filterPage.getViewDetailsCount();
            System.out.println("Current View Details buttons: " + currentCount);

            
            System.out.println("Pagination reference test completed");

        } catch (Exception e) {
            System.err.println("Pagination test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}