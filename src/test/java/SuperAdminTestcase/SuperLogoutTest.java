package SuperAdminTestcase;


import Base.BaseDriver;
import SuperAdminPage.SuperLoginPage;
import Base.BaseDriver;
import AdminPages.LoginPage;
import AdminPages.LogoutPage;
import AdminPages.reportpage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class SuperLogoutTest extends BaseDriver {

    @Test
    public void SuperLogoutTest() {
        SuperLoginPage superloginPage = new SuperLoginPage(driver);
        superloginPage.enterEmail("mereg2025@gmail.com");
        superloginPage.enterPassword("SuperAdmin@123");
        superloginPage.clickSignIn();
        System.out.println("Login successful");

        LogoutPage logoutpage = new LogoutPage(driver);
        logoutpage.clickOnlogoutpage();

        String title = logoutpage.getreportpageTitleText();
        System.out.println("Logout Title: " + title);
        Assert.assertEquals(title, "Welcome to MeReg");
    }
}


