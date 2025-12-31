
package AdminTestcase;

import Base.BaseDriver;
import AdminPages.LoginPage;
import AdminPages.EnrollmentChildPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class EnrollmentStudentTest extends BaseDriver {

    @Test
    public void EnrollmentStudentTest() {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        EnrollmentChildPage enrollmentPage = new EnrollmentChildPage(driver);
        enrollmentPage.clickOnAdmin(); 
        enrollmentPage.clickIndiduallink();

        String title = enrollmentPage.getEnrollmentTitleText(); 
        System.out.println("Adminpage Title: " + title);

        Assert.assertEquals(title, "MeReg Workspace", "Admin page title mismatch");
    }
}
