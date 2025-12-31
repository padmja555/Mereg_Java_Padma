package SuperAdminTestcase;

import Base.BaseDriver;
import SuperAdminPage.SuperLoginPage;
import SuperAdminPage.SuperStudentPage;
import AdminPages.Enrollmentpage;
import AdminPages.LoginPage;
import AdminPages.adminpage1;
import AdminPages.studentspage;

import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class SuperStudentTest extends BaseDriver {

    @Test
    public void SuperStudentTest() {
        SuperLoginPage superloginPage = new SuperLoginPage(driver);
        superloginPage.enterEmail("mereg2025@gmail.com");
        superloginPage.enterPassword("SuperAdmin@123");
        superloginPage.clickSignIn();
        System.out.println("Login successful");

        SuperStudentPage superstudent = new SuperStudentPage(driver);
        try {
			superstudent.clickOnStudents();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

        String title = superstudent.getStudentsTitleText(); 
        System.out.println("Students Title: " + title);

        Assert.assertEquals(title, "MeReg Workspace", "students title mismatch");
    }
}
