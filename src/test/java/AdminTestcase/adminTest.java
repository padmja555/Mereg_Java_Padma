package AdminTestcase;

import Base.BaseDriver;
import AdminPages.LoginPage;
import AdminPages.adminpage1;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class adminTest extends BaseDriver {

    @Test
    public void AdminTest() {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        adminpage1 adminPage = new adminpage1(driver);
        adminPage.clickOnAdmin(); 

        String title = adminPage.getAdminTitleText(); 
        System.out.println("Adminpage Title: " + title);

        Assert.assertEquals(title, "MeReg Workspace", "Admin page title mismatch");
    }
}
