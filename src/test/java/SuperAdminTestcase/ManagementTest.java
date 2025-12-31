
package SuperAdminTestcase;

import Base.BaseDriver;
import SuperAdminPage.ManagementPage;
import SuperAdminPage.SuperLoginPage;
import SuperAdminPage.SuperPaymentPage;
import AdminPages.LoginPage;
import AdminPages.Paymentpage;
import AdminPages.reportpage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class ManagementTest extends BaseDriver {

    @Test
    public void managementTest() throws InterruptedException {
        SuperLoginPage superloginPage = new SuperLoginPage(driver);
        superloginPage.enterEmail("mereg2025@gmail.com");
        superloginPage.enterPassword("SuperAdmin@123");
        superloginPage.clickSignIn();
        System.out.println("Login successful");


        ManagementPage payment = new ManagementPage(driver);
        Thread.sleep(2000);
        payment.clickOnmanagementPage();

        String title = payment.getmanagementpageTitleText();
        System.out.println("Filter Payment Reports Title: " + title);
        Assert.assertEquals(title, "Filter Payment Reports", "Filter Payment Reports page title mismatch");
    }
}
