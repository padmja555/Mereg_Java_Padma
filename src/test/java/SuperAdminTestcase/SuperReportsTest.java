package SuperAdminTestcase;
import Base.BaseDriver;
import SuperAdminPage.SuperLoginPage;
import SuperAdminPage.SuperReportsPage;
import AdminPages.LoginPage;
import AdminPages.reportpage;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class SuperReportsTest extends BaseDriver {

    @Test
    public void SuperReportsTest() {
        SuperLoginPage superloginPage = new SuperLoginPage(driver);
        superloginPage.enterEmail("mereg2025@gmail.com");
        superloginPage.enterPassword("SuperAdmin@123");
        superloginPage.clickSignIn();
        System.out.println("Login successful");

        SuperReportsPage superreportPage = new SuperReportsPage(driver);
        superreportPage.clickOnreportpage();
        String actualTitle = driver.findElement(By.xpath("//h1")).getText();
        Assert.assertTrue(actualTitle.equals("Reports") || actualTitle.equals("Total Reports"),
                "Reports page title mismatch. Found: " + actualTitle);

        //String title = superreportPage.getreportpageTitleText();
        //System.out.println("Reports Title: " + title);
        //Assert.assertEquals(title, "Total Reports", "Reports page title mismatch");
    }
}

