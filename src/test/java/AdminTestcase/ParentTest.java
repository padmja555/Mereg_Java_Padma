package AdminTestcase;

import java.util.concurrent.TimeoutException;


import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import AdminPages.Childinfo;
import AdminPages.Parentpage;
import Base.BaseDriver;
import AdminPages.LoginPage;

@Listeners(listeners.TestListener.class)
public class ParentTest extends BaseDriver {

    @Test
    public void parentguardianTest() throws InterruptedException, TimeoutException {
        // Step 1: Login to the application
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();
        Thread.sleep(5000);

        // Step 2: Navigate to the parent info page via child info
        Childinfo childinfo = new Childinfo(driver);
        childinfo.clickOnenrollment();
        Thread.sleep(3000);
        
        childinfo.enterRandomFirstAndLastName();
        Thread.sleep(1500);
        
        childinfo.selectGender();
        Thread.sleep(1500);
        
        childinfo.enterrandomDOB();
        Thread.sleep(1500);
        
        childinfo.ClickonProceedtoGuardian();
        Thread.sleep(5000);

        // Step 3: Verify page titles
        String title = childinfo.getchildinfopageTitleText();
        System.out.println("Child Enrollment Title: " + title);
        Assert.assertEquals(title, "Child Enrollment", "Child enrollment page title mismatch");

        Parentpage parentguardianinfo = new Parentpage(driver);
        String parentguardiantitle = parentguardianinfo.verifyParentGuardianPage();
        System.out.println("Parent/Guardian Title: " + parentguardiantitle);
        Assert.assertEquals(parentguardiantitle, "Parent/Guardian info", "Parent/Guardian page title mismatch");

        // Step 4: Fill parent/guardian information with random data
        parentguardianinfo.enterRandomFirstName();
        Thread.sleep(1000);
        
        parentguardianinfo.enterRandomMiddleName();
        Thread.sleep(1000);
        
        parentguardianinfo.enterRandomLastName();
        Thread.sleep(1000);
        
        parentguardianinfo.selectRelationship();
        Thread.sleep(2000);
        
        parentguardianinfo.enterRandomAddress1();
        Thread.sleep(1000);
        
        parentguardianinfo.enterRandomAddress2();
        Thread.sleep(1000);
        
        // Step 5: Select location details
        parentguardianinfo.selectRandomCountry();
        Thread.sleep(3000);
        
        parentguardianinfo.selectState();
        Thread.sleep(3000);
        
        parentguardianinfo.enterRandomCity();
        Thread.sleep(1000);
        
        // Step 6: Enter random zip code based on selected country
        parentguardianinfo.enterRandomZipCode();
        Thread.sleep(1000);
        
        // Step 7: Enter phone details with random data
        parentguardianinfo.selectRandomPhoneType();
        Thread.sleep(2000);
        
        parentguardianinfo.enterRandomCountryCode();
        Thread.sleep(2000);
        
        parentguardianinfo.enterRandomPhoneNumber();
        Thread.sleep(2000);
        
        // Step 8: Add alternate guardian with random data
        parentguardianinfo.alternateGuardianInfoIconButton();
        Thread.sleep(4000);
        
        parentguardianinfo.enterRandomAlternateGuardianName();
        Thread.sleep(1500);
        
        parentguardianinfo.alterGuardianSelectRelationship();
        Thread.sleep(2500);
        
        parentguardianinfo.alternateGuardianCountryCode();
        Thread.sleep(2500);
        
        // Enter alternate phone number using the second phone number field
        parentguardianinfo.enterRandomAlternateGuardianPhoneNumber();
        Thread.sleep(2000);
        
        // Step 9: Enter random email
        parentguardianinfo.enterRandomEmailAddress();
        Thread.sleep(1500);
        
        // Step 10: Proceed to next page
        parentguardianinfo.proceedToMedicalInfoButton();
        Thread.sleep(5000);
        
        System.out.println("Parent/Guardian information submitted successfully with random data!");
    }
}