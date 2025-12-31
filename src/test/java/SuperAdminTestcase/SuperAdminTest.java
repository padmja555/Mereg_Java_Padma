package SuperAdminTestcase;


import Base.BaseDriver;
import SuperAdminPage.SuperAdminpage;
import SuperAdminPage.SuperLoginPage;
import AdminPages.LoginPage;
import AdminPages.adminpage1;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class SuperAdminTest extends BaseDriver {

    @Test
    public void AdminTest() {
        SuperLoginPage superloginPage = new SuperLoginPage(driver);
        superloginPage.enterEmail("mereg2025@gmail.com");
        superloginPage.enterPassword("SuperAdmin@123");
        superloginPage.clickSignIn();
        System.out.println("Login successful");

        SuperAdminpage adminPage = new SuperAdminpage(driver);
        adminPage.clickOnpageAdmin(); 

        String title = adminPage.getpageTitleText(); 
        System.out.println("Adminpage Title: " + title);

        Assert.assertEquals(title, "MeReg Workspace", "Admin page title mismatch");
    }
}
