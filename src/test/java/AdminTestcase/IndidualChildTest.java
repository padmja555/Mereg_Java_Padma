
package AdminTestcase;
import Base.BaseDriver;

import AdminPages.Childinfo;
import AdminPages.EnrollmentChildPage;
import AdminPages.IndidualChildPage;
import AdminPages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class IndidualChildTest extends BaseDriver {

    @Test
    public void ChildInfo_AddSingleChild() throws InterruptedException {
        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("srasysife@gmail.com");
        loginPage.enterPassword("Admin@123");
        loginPage.clickSignIn();
        
        // Navigate to Child Info Page
        EnrollmentChildPage enrollmentPage = new EnrollmentChildPage(driver);
        enrollmentPage.clickOnAdmin();   
        enrollmentPage.clickIndiduallink();
        enrollmentPage.getEnrollmentTitleText();
;
        // Navigate to Child Info Page
        IndidualChildPage indidualchildPage = new IndidualChildPage(driver);
        indidualchildPage.enterRandomFirstAndLastName();
        //enrollmentPage.enterDetailsForAdditionChild()
        //enrollmentPage.enterRandomFirstAndLastName();
        //enrollmentPage.enterRandomFirstAndLastName();
        indidualchildPage.selectGender();
        indidualchildPage.enterrandomDOB();
        indidualchildPage.ClickonProceedtoGuardian();
        // Optional: Add verification that we moved to next page
        System.out.println("Successfully proceeded to Enrollment  Parent/Guardian page with one child");




}
}
