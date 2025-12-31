package ParentTestCases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Base.BaseDriver;
import ParentPages.ParentHomePage;
import ParentPages.ParentLoginPage;

public class ParentLoginTest extends BaseDriver {

    @Test(dataProvider = "loginCredentials")
    public void testParentLogin(String email, String password, boolean expectedSuccess) {
        ParentLoginPage loginPage = new ParentLoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        if (expectedSuccess) {
            ParentHomePage homePage = new ParentHomePage(driver);
            Assert.assertTrue(homePage.isParentHomeDisplayed(), "Login should be successful but was not.");
        } else {
            Assert.assertTrue(loginPage.isErrorDisplayed(), "Expected error message not displayed for invalid login.");
        }
    }

    @DataProvider(name = "loginCredentials")
    public Object[][] getLoginData() {
        return new Object[][] {
            // Positive scenario
            {"srasysacademy@gmail.com", "Parent@123", true},

            // Negative scenarios
            {"wrongemail@gmail.com", "Parent@123", false},         // Invalid email
            {"srasysacademy@gmail.com", "WrongPassword", false},   // Invalid password
            {"", "Parent@123", false},                             // Empty email
            {"srasysacademy@gmail.com", "", false},                // Empty password
            {"", "", false}                                        // Both empty
        };
    }
}
