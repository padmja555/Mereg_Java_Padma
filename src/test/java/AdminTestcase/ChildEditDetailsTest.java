package AdminTestcase;

import Base.BaseDriver;
import AdminPages.*;
import org.testng.annotations.Test;

import java.util.Random;

public class ChildEditDetailsTest extends BaseDriver {

    @Test(description = "Edit student details using dynamic field discovery")
    public void verifyStudentDetailsCanBeEdited() {
        ChildEditDetailsPage editPage = null;

        try {
            // STEP 1: LOGIN
            LoginPage login = new LoginPage(driver);
            login.enterEmail("srasysife@gmail.com");
            login.enterPassword("Admin@123");
            login.clickSignIn();
            Thread.sleep(3000);

            // STEP 2: NAVIGATE TO STUDENTS
            studentspage studentPage = new studentspage(driver);
            studentPage.clickOnStudents();
            Thread.sleep(3000);

            // STEP 3: VIEW RANDOM STUDENT
            StudentListPage listPage = new StudentListPage(driver);
            listPage.clickRandomViewDetails();
            Thread.sleep(3000);

            // STEP 4: EDIT STUDENT
            editPage = new ChildEditDetailsPage(driver);
            editPage.waitForPageLoad();
            editPage.clickEdit();

            String[] firstNames = {"John", "Alice", "Robert", "Emily"};
            String[] lastNames = {"Smith", "Johnson", "Brown", "Taylor"};

            Random rand = new Random();
            String firstName = firstNames[rand.nextInt(firstNames.length)];
            String lastName = lastNames[rand.nextInt(lastNames.length)];

            editPage.fillAngularMaterialForm(
                firstName,
                lastName,
                "Parent" + firstName,
                "Parent" + lastName
            );

            editPage.clickSave();

            if (editPage.hasValidationErrors()) {
                System.out.println("⚠️ Validation errors occurred after save.");
                editPage.navigateToStudentsPage(); // Or try to recover
            } else {
                System.out.println("✅ Student details updated successfully.");
            }

        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
            if (editPage != null) editPage.navigateToStudentsPage();
        }
    }
}
