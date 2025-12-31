package AdminTestcase;

import Base.BaseDriver;
import Base.BaseDriver;
import AdminPages.LoginPage;
import AdminPages.LogoutPage;
import AdminPages.reportpage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class LogoutTest extends BaseDriver {

    @Test
    public void LogoutTest() {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        LogoutPage logoutpage = new LogoutPage(driver);
        logoutpage.clickOnlogoutpage();

        String title = logoutpage.getreportpageTitleText();
        System.out.println("Logout Title: " + title);
        Assert.assertEquals(title, "Welcome to MeReg");
    }
}


