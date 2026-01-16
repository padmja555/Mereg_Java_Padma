package AdminTestcase;

import AdminPages.LoginPage;
import AdminPages.StaffMessagepaginationPage;
import Base.BaseDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StaffMessagepaginationTest extends BaseDriver {

	StaffMessagepaginationPage paginamessagesPage;

    @BeforeMethod
    public void setup() {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        paginamessagesPage = new StaffMessagepaginationPage(driver);
        paginamessagesPage.clickOnMessageTab();
 
    }
    
    
    @Test
    public void testPaginationRandomNavigation() {

        paginamessagesPage.clickStaffTab();
        paginamessagesPage.searchStaffMessage("Automation Subject");

        paginamessagesPage.clickNextPage();
        paginamessagesPage.clickPreviousPage();
        paginamessagesPage.clickRandomPageNumber();

        System.out.println("✅ Staff message pagination test successful");
    }
}