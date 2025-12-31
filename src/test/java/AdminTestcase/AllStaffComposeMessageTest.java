package AdminTestcase;

import AdminPages.*;
import Base.BaseDriver;
import org.testng.annotations.Test;

public class AllStaffComposeMessageTest extends BaseDriver {

    @Test
    public void sendIndividualMessageToMultipleStaff() throws InterruptedException {

        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        Thread.sleep(3000);

        AMessagePage msg = new AMessagePage(driver);
        msg.clickOnMessage();

        AllStaffComposeMessagePage compose =
                new AllStaffComposeMessagePage(driver);

        compose.openCompose();
 
        compose.selectIndividual();

        compose.selectStaffCategory();
        Thread.sleep(2000);
        compose.selectMultipleStaff(3);   // 🔥 MUST happen
        Thread.sleep(2000);
        compose.enableSendIndividually(); // optional
        Thread.sleep(2000);
        compose.selectReminder();
        Thread.sleep(2000);
        compose.enterRandomSubject();  
        //Thread.sleep(4000);// now enabled
        try {
   			compose.enterRandomFormattedMessage();
   		} catch (Exception e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
           

        //compose.enterRandomFormattedMessage();
        Thread.sleep(2000);
        compose.sendMessage();


    }}
