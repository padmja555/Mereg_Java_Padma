package AdminTestcase;

import AdminPages.LoginPage;
import AdminPages.StudentListPage;
import AdminPages.StudentListPage;
import AdminPages.studentspage;
import Base.BaseDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StudentListTest extends BaseDriver {

    @Test(description = "Verify that the student list is displayed and a random student's details can be viewed.")
    public void verifyStudentListAndClickRandomStudent() {
        // Step 1: Login
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        // Step 2: Navigate to Students tab
        studentspage studentNav = new studentspage(driver);
        studentNav.clickOnStudents();

        // Log current URL and page source (or some part) for debugging
        System.out.println("After clicking Students, current URL: " + driver.getCurrentUrl());

        // Step 3: Check student list
        StudentListPage studentListPage = new StudentListPage(driver);
        boolean listVisible = studentListPage.isStudentListDisplayed();
        Assert.assertTrue(listVisible, "Student list table is not displayed.");
        System.out.println("Student list is successfully displayed.");

        // Step 4: Click random View Details
        studentListPage.clickRandomViewDetails();
        System.out.println("Clicked on a random 'View Details' button.");
    }
}
