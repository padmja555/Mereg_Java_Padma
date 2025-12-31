package AdminTestcase;

import AdminPages.*;
import Base.BaseDriver;
import org.testng.annotations.*;

public class IndividualComposeMessageTest extends BaseDriver {

    IndividualComposeMessagePage compose;

    @BeforeMethod
    public void setup() throws InterruptedException {
        // Login
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        Thread.sleep(3000); // Wait for login to complete
        
        // Navigate to Messages
        AMessagePage message = new AMessagePage(driver);
        message.clickOnMessage();
        
        Thread.sleep(2000); // Wait for messages page to load
        
        // Initialize page object
        compose = new IndividualComposeMessagePage(driver);
    }

    @Test
    public void sendIndividualMessageRandomly() throws InterruptedException {
        System.out.println("Starting Individual Message Test...");
        
        
        // Open compose message modal
        compose.openComposeMessage();
        Thread.sleep(1000);
        // Select Individual tab
        compose.selectIndividualTab();
        Thread.sleep(1000);
        // Select 2 random parents (this will select category first)
        compose.selectRandomParents(2);
        Thread.sleep(3000);
        compose.selectMessageType("Alert");
        // Enter subject
        compose.enterRandomSubject();
        Thread.sleep(3000);
        // Enter formatted message
        try {
			compose.enterRandomFormattedMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Send the message
        compose.clickSendMessage();
        
        System.out.println("Test completed successfully!");
    }
}