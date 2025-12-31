package SuperAdminTestcase;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import Base.BaseDriver;
import SuperAdminPage.SuperLoginPage;
import AdminPages.HomePage;

@Listeners(listeners.TestListener.class)
public class SuperLoginTest extends BaseDriver {

    @Test(dataProvider = "loginCredentials")
    public void testSuperLoginPage(String email, String password, boolean expectedSuccess) {
        SuperLoginPage superloginPage = new SuperLoginPage(driver);

        // Perform login
        superloginPage.enterEmail(email);
        superloginPage.enterPassword(password);
        superloginPage.clickSignIn();

        if (expectedSuccess) {
            HomePage homePage = new HomePage(driver);
            Assert.assertTrue(homePage.isAdminHomeDisplayed(),
                    "Login should be successful but was not.");
            System.out.println("✅ Login successful for Email: " + email + " | Password: " + password);
        } else {
            Assert.assertTrue(superloginPage.isErrorMessageDisplayed(),
                    "❌ Expected error message not displayed for invalid login.");
            System.out.println("⚠️ Login failed as expected. Current URL: " + driver.getCurrentUrl());
        }
    }

    // Data provider for multiple login scenarios
    @DataProvider(name = "loginCredentials")
    public Object[][] getLoginData() {
        return new Object[][]{
                {"mereg2025@gmail.com", "SuperAdmin@123", true},   
                {"mereg2025@gmail.com", "SuperAdmin@12", false},     
                {"mereg2025@gmail.com", "WrongPassword", false},  
                {"", "SuperAdmin@123", false},                         
                {"mereg2025@gmail.com", "", false},               
                {"", "", false}                                   
        };
    }
}
