package AdminTestcase;

import AdminPages.ChildinfoNegativepage;
import AdminPages.LoginPage;
import Base.BaseDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(listeners.TestListener.class)
public class ChildsNegativeScenarioTest extends BaseDriver {

    @Test(dataProvider = "childData", dataProviderClass = ChildInfoDataProvider.class)
    public void verifyChildInfoForm(String firstName, String lastName, String dob, boolean isPositive, String description) throws InterruptedException {
        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("srasysife@gmail.com");
        loginPage.enterPassword("Admin@123");
        loginPage.clickSignIn();

        ChildinfoNegativepage childPage = new ChildinfoNegativepage(driver);
        childPage.openEnrollmentSection();

        // Enter child details
        childPage.enterFirstName(firstName);
        childPage.enterLastName(lastName);
        childPage.enterDOB(dob);

        // Submit the form
        childPage.submitForm();

        if (isPositive) {
            // Positive scenario - no errors expected
            Assert.assertFalse(childPage.isFirstNameErrorDisplayed(), "First name error should NOT be displayed: " + description);
            Assert.assertFalse(childPage.isLastNameErrorDisplayed(), "Last name error should NOT be displayed: " + description);
            Assert.assertFalse(childPage.isDOBErrorDisplayed(), "DOB error should NOT be displayed: " + description);
            // TODO: Add assertions for success page or confirmation message if applicable
        } else {
            // Negative scenario - errors expected as per blank/invalid fields
            if (firstName.isEmpty()) {
                Assert.assertTrue(childPage.isFirstNameErrorDisplayed(), "Expected first name error for: " + description);
            }
            if (lastName.isEmpty()) {
                Assert.assertTrue(childPage.isLastNameErrorDisplayed(), "Expected last name error for: " + description);
            }
            if (dob.isEmpty() || dob.equals("invalid-date")) {
                Assert.assertTrue(childPage.isDOBErrorDisplayed(), "Expected DOB error for: " + description);
            }
        }
    }
}
