
package AdminTestcase;

import Base.BaseDriver;
import AdminPages.LoginPage;
import AdminPages.ReportsYearlyPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class ReportsYearlyTest extends BaseDriver {

    @Test
    public void ReportTest() {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        ReportsYearlyPage reportoptionPage = new ReportsYearlyPage(driver);
        reportoptionPage.clickOnreportpage();
        reportoptionPage.selectRandomReportRange();

        String title = reportoptionPage.getreportpageTitleText();
        System.out.println("Report Title: " + title);
        Assert.assertEquals(title, "Total Payments", "Report page title mismatch");
    }
}


