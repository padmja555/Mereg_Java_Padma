package AdminTestcase;

import Base.BaseDriver;
import AdminPages.Enrollmentpage;
import AdminPages.LoginPage;
import AdminPages.adminpage1;
import AdminPages.studentspage;

import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class StudentTest extends BaseDriver {

    @Test
    public void enrollmentTestTest() {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        studentspage student = new studentspage(driver);
        student.clickOnStudents(); 

        String title = null;
		try {
			title = student.getStudentsTitleText();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        System.out.println("Students Title: " + title);

        Assert.assertEquals(title, "MeReg Workspace", "students title mismatch");
    }
}
