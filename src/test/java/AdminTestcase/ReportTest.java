package AdminTestcase;

import Base.BaseDriver;
import AdminPages.LoginPage;
import AdminPages.reportpage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class ReportTest extends BaseDriver {

    @Test
    public void ReportTest() {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        reportpage reportPage = new reportpage(driver);
        reportPage.clickOnreportpage();

        String title = reportPage.getreportpageTitleText();
        System.out.println("Report Title: " + title);
        Assert.assertEquals(title, "Total Payments", "Report page title mismatch");
    }
}

