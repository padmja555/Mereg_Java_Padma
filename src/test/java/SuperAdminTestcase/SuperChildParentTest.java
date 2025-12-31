package SuperAdminTestcase;


import java.util.concurrent.TimeoutException;


import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import AdminPages.Childinfo;
import AdminPages.EnrollmentChildPage;
import AdminPages.IndidualChildPage;
import AdminPages.Parentpage;
import Base.BaseDriver;
import SuperAdminPage.SuperChildInfoPage;
import SuperAdminPage.SuperChildParentPage;
import SuperAdminPage.SuperEnrollmentPage;
import SuperAdminPage.SuperLoginPage;
import AdminPages.LoginPage;

@Listeners(listeners.TestListener.class)
public class SuperChildParentTest extends BaseDriver {

    @Test
    public void SuperChildParenttest() throws InterruptedException, TimeoutException {
        // Step 1: Login to the application

        Thread.sleep(5000);

        // Step 2: Navigate to the parent info page via child info
        EnrollmentChildPage enrollmentPage = new EnrollmentChildPage(driver);
        SuperLoginPage superloginPage = new SuperLoginPage(driver);
        superloginPage.enterEmail("mereg2025@gmail.com");
        superloginPage.enterPassword("SuperAdmin@123");
        superloginPage.clickSignIn();
        Thread.sleep(5000);
        System.out.println("Login successful");

        // Navigate to Enrollment Page
        SuperEnrollmentPage superenrollmentPage = new SuperEnrollmentPage(driver);
        superenrollmentPage.clickOnEnrollment();
        System.out.println("Clicked on Enrollment link");

;
        // Navigate to Child Info Page
	      SuperChildInfoPage superchildPage = new SuperChildInfoPage(driver);
	      superchildPage.enterRandomFirstAndLastName();
	      superchildPage.selectGender();
	      superchildPage.enterrandomDOB();
	      superchildPage.ClickonProceedtoGuardian();
          // Step 3: Verify page titles
        String title = superchildPage.getchildinfopageTitleText();
        System.out.println("Child Enrollment Title: " + title);
        Assert.assertEquals(title, "Child Enrollment", "Child enrollment page title mismatch");

        SuperChildParentPage childparentpage = new SuperChildParentPage(driver);
        String parentguardiantitle = childparentpage.verifyParentGuardianPage();
        System.out.println("Parent/Guardian Title: " + parentguardiantitle);
        Assert.assertEquals(parentguardiantitle, "Parent/Guardian info", "Parent/Guardian page title mismatch");

        // Step 4: Fill parent/guardian information with random data
        childparentpage.enterRandomFirstName();
        Thread.sleep(1000);
        
        childparentpage.enterRandomMiddleName();
        Thread.sleep(1000);
        
        childparentpage.enterRandomLastName();
        Thread.sleep(1000);
        
        childparentpage.selectRelationship();
        Thread.sleep(2000);
        
        childparentpage.enterRandomAddress1();
        Thread.sleep(1000);
        
        childparentpage.enterRandomAddress2();
        Thread.sleep(1000);
        
        // Step 5: Select location details
        childparentpage.selectRandomCountry();
        Thread.sleep(3000);
        
        childparentpage.selectState();
        Thread.sleep(3000);
        
        childparentpage.enterRandomCity();
        Thread.sleep(1000);
        
        // Step 6: Enter random zip code based on selected country
        childparentpage.enterRandomZipCode();
        Thread.sleep(1000);
        
        // Step 7: Enter phone details with random data
        childparentpage.selectRandomPhoneType();
        Thread.sleep(2000);
        
        childparentpage.enterRandomCountryCode();
        Thread.sleep(2000);
        
        childparentpage.enterRandomPhoneNumber();
        Thread.sleep(2000);
        
        // Step 8: Add alternate guardian with random data
        childparentpage.alternateGuardianInfoIconButton();
        Thread.sleep(4000);
        
        childparentpage.enterRandomAlternateGuardianName();
        Thread.sleep(1500);
        
        childparentpage.alterGuardianSelectRelationship();
        Thread.sleep(2500);
        
        childparentpage.alternateGuardianCountryCode();
        Thread.sleep(2500);
        
        // Enter alternate phone number using the second phone number field
        childparentpage.enterRandomAlternateGuardianPhoneNumber();
        Thread.sleep(2000);
        
        // Step 9: Enter random email
        childparentpage.enterRandomEmailAddress();
        Thread.sleep(1500);
        
        // Step 10: Proceed to next page
        childparentpage.proceedToMedicalInfoButton();
        Thread.sleep(5000);
        
        System.out.println("Parent/Guardian information submitted successfully with random data!");
    }
}

