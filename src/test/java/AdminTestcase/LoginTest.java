package AdminTestcase;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import Base.BaseDriver;
import AdminPages.LoginPage;
import AdminPages.HomePage;
@Listeners(listeners.TestListener.class)
public class LoginTest extends BaseDriver {

    @Test(dataProvider = "loginCredentials")
    public void testAdminLoginPage(String email, String password, boolean expectedSuccess) {
        LoginPage loginPage = new LoginPage(driver);

        // Perform login
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickSignIn();

        if (expectedSuccess) {
            HomePage homePage = new HomePage(driver);
            Assert.assertTrue(homePage.isAdminHomeDisplayed(), 
                "Login should be successful but was not.");
            System.out.println("Login successful for Email: " + email + " | Password: " + password);
        } else {
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), 
                "Expected error message not displayed for invalid login.");
            System.out.println("Login failed as expected. Current URL: " + driver.getCurrentUrl());
        }
    }

    // Data provider for multiple login scenarios
    @DataProvider(name = "loginCredentials")
    public Object[][] getLoginData() {
        return new Object[][]{
            {"srasysife@gmail.com", "Admin@123", true},      
            {"wrongemail@gmail.com", "Admin@123", false},    
            {"srasysife@gmail.com", "WrongPassword", false}, 
            {"", "Admin@123", false},                         
            {"srasysife@gmail.com", "", false},              
            {"", "", false}                                   
        };
    }
}
