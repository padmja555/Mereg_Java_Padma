
package AdminTestcase;

import Base.BaseDriver;
import AdminPages.LoginPage;
import AdminPages.SecondChildnotworking;
//import AdminPages.TwoChildPage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class SecondChidTestcasenotworking extends BaseDriver {

    @Test
    public void addTwoChildrenAndProceed() throws InterruptedException {
        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("srasysife@gmail.com");
        loginPage.enterPassword("Admin@123");
        loginPage.clickSignIn();
        System.out.println("Successfully logged in");

        // Step 2: Navigate to Enrollment page
        SecondChildPage twoChildPage = new SecondChildnotworking(driver);
        twoChildPage.clickEnrollmentLink();

        // Step 3: Add two children
        twoChildPage.addTwoChildren();

        // Step 4: Proceed to Guardian Info
        twoChildPage.clickProceedToGuardian();

        System.out.println("Successfully proceeded to Parent/Guardian page with two children.");
    }
}

