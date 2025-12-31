package dataproviders;

import Base.BaseDriver;
import AdminPages.ChildinfoNegativepage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ChildinfoNegativetestcase extends BaseDriver {

    @Test(dataProvider = "NegativeChildData")
    public void testNegativeChildInfo(String firstName, String lastName, String dob) {
        ChildinfoNegativepage childPage = new ChildinfoNegativepage(driver);

        // Step 1: Navigate to Enrollment
        childPage.clickOnEnrollment();

        // Step 2: Fill child details from DataProvider
        childPage.enterChildDetails(firstName, lastName, dob);
        childPage.selectGenderMale();

        // Step 3: Try to proceed
        childPage.clickProceedToGuardian();

        // Step 4: Assertion — Example: Page should still be "Child Information"
        String title = childPage.getPageTitle();
        Assert.assertTrue(title.contains("Child Information"),
                "Validation failed for: " + firstName + " " + lastName + " " + dob);
    }

    @DataProvider(name = "NegativeChildData")
    public Object[][] getNegativeChildData() {
        return new Object[][] {
            {"", "Lastname1", "01/01/2015"},     
            {"Firstname2", "", "01/01/2015"},     
            {"Firstname3", "Lastname3", ""},     
            {"", "", ""},                        
            {"1234", "Lastname4", "01/01/2015"},  
            {"Firstname5", "5678", "01/01/2015"}  
        };
    }
}
