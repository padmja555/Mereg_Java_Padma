package AdminTestcase;

import Base.BaseDriver;
import AdminPages.LoginPage;
import AdminPages.SideNavbarpage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class SideNavbarTest extends BaseDriver {

    @Test
    public void ClickSideNavbar() throws InterruptedException {

	    LoginPage loginPage = new LoginPage(driver);
	    loginPage.enterEmail("srasysife@gmail.com");
	    loginPage.enterPassword("Admin@123");
	    loginPage.clickSignIn();

	    SideNavbarpage navbar = new SideNavbarpage(driver);
        String title = navbar.getWorkspaceTitle();
        System.out.println("Dashboard Title: " + title);

        Assert.assertEquals(title, "MeReg Workspace", "Dashboard title mismatch");
        Thread.sleep(3000);
        navbar.clickOnAdmin();
    }
}



