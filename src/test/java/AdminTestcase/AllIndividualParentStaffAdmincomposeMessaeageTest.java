
package AdminTestcase;

import AdminPages.AMessagePage;
import AdminPages.AllIndividualParentStaffAdmincomposeMessaeagePage;
import AdminPages.ComposeMessagePage;
import AdminPages.LoginPage;
import Base.BaseDriver;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



    public class AllIndividualParentStaffAdmincomposeMessaeageTest extends BaseDriver {

        AMessagePage message;
        AllIndividualParentStaffAdmincomposeMessaeagePage composePage;

        @BeforeMethod
        public void setupTest() {

            LoginPage login = new LoginPage(driver);
            login.enterEmail("srasysife@gmail.com");
            login.enterPassword("Admin@123");
            login.clickSignIn();

            message = new AMessagePage(driver);
            message.clickOnMessage();

            composePage = new AllIndividualParentStaffAdmincomposeMessaeagePage(driver);
        }

        @Test
        public void sendAllIndidualMessageWithRandomStaffCC() {

            composePage.openComposeMessage();
            composePage.selectAllIndidualTab();
            composePage.selectThreeCategories(); 
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            List<String> recipients = Arrays.asList(
            	    "Diya Reddy (Staff)",
            	    "Suraj Konangi (Staff)",
            	    "John Doe (Staff)",
            	    "Maheswar Gorantla (Admin)",
            	    "Thomas K Dawns (Admin)",
            	    "Super Admin MeReg (Super Admin)",
            	    "Anuradha M (Parent)",
            	    "Padma D pamidi (Parent)"
            	);

            //composePage. selectRecipientsByDropdownSearch(recipients );
            //*****************secondmethod1::::::::::::
        
            try {
				composePage.selectRandomRecipients(8);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            //**************************
            composePage.selectRandomMessageType();
            composePage.enterRandomSubjectAndMessage();
            composePage.clickSendMessage();
        }
    }
