package AdminTestcase;

import Base.BaseDriver;
import AdminPages.AMessagePage;
import AdminPages.Enrollmentpage;
import AdminPages.LoginPage;
import AdminPages.adminpage1;
import AdminPages.studentspage;

import java.util.concurrent.TimeoutException;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class AMessageTest extends BaseDriver {

    @Test
    public void enrollmentTestTest() {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        AMessagePage student = new AMessagePage(driver);
        student.clickOnMessage(); 

        String title = null;
		try {
			title = student.getMessageTitleText();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        System.out.println("Messages Title: " + title);

        Assert.assertEquals(title, "Messages", "message title mismatch");
    }
}
