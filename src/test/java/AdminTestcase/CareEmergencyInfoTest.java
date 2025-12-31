package AdminTestcase;

import org.testng.annotations.Test;
import AdminPages.Childinfo;
import AdminPages.Parentpage;
import AdminPages.LoginPage;
import AdminPages.PhysicianInfoPage;
import Base.BaseDriver;
import org.testng.Assert;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CareEmergencyInfoTest extends BaseDriver {
    
    @Test
    public void medicalInfoTest() {
        try {
            // Step 1: Login to the application
            System.out.println("Starting the medical info test flow.");
            LoginPage login = new LoginPage(driver);
            login.enterEmail("srasysife@gmail.com");
            login.enterPassword("Admin@123");
            login.clickSignIn();

            // Step 2: Navigate to the parent info page via child info
            System.out.println("Navigating to Child and Parent/Guardian Info pages...");
            Childinfo childinfo = new Childinfo(driver);
            childinfo.clickOnenrollment();
            childinfo.enterRandomFirstAndLastName();
            childinfo.selectGender();
            childinfo.enterrandomDOB();
            childinfo.ClickonProceedtoGuardian();

            // Step 3: Fill out the parent/guardian information
            Parentpage parentguardianinfo = new Parentpage(driver);
            parentguardianinfo.enterRandomFirstName();
            parentguardianinfo.enterRandomMiddleName();
            parentguardianinfo.enterRandomLastName();
            parentguardianinfo.selectRelationship();
            parentguardianinfo.enterRandomAddress1();
            parentguardianinfo.enterRandomAddress2();
            parentguardianinfo.selectRandomCountry();
            parentguardianinfo.selectState();
            parentguardianinfo.enterRandomCity();
            parentguardianinfo.enterRandomZipCode();
            parentguardianinfo.selectRandomPhoneType();
            parentguardianinfo.enterRandomCountryCode();
            parentguardianinfo.enterRandomPhoneNumber();
            parentguardianinfo.alternateGuardianInfoIconButton();
            parentguardianinfo.enterRandomAlternateGuardianName();
            parentguardianinfo.alterGuardianSelectRelationship();
            parentguardianinfo.alternateGuardianCountryCode();
            parentguardianinfo.enterRandomAlternateGuardianPhoneNumber();
            parentguardianinfo.proceedToMedicalInfoButton();

            // Step 4: Fill physician information only
            System.out.println("Filling Physician Information only...");
            PhysicianInfoPage medicalinfo = new PhysicianInfoPage(driver);
            String pageHeader = medicalinfo.verifyPhysicianInfoPage();
            System.out.println("On page: " + pageHeader);
            
            medicalinfo.enterRandomPhysicianFirstName();
            medicalinfo.enterRandomPhysicianMiddleName();
            medicalinfo.enterRandomPhysicianLastName();
            medicalinfo.enterRandomPhysicianAddress1();
            medicalinfo.enterRandomPhysicianAddress2();
            medicalinfo.selectRandomPhysicianCountry();
            medicalinfo.selectPhysicianState();
            medicalinfo.enterRandomPhysicianCity();
            medicalinfo.enterRandomPhysicianZipCode();
            medicalinfo.selectRandomPhysicianPhoneType();
            medicalinfo.selectRandomPhysicianCountryCode();
            medicalinfo.enterRandomPhysicianPhoneNumber();

            System.out.println("Clicking the next button to proceed.");
            medicalinfo.proceedToNext();
            
            System.out.println("Physician information form completed successfully!");
            
        } catch (Exception e) {
            System.out.println("Error during medical info test: " + e.getMessage());
            System.out.println("Current URL: " + driver.getCurrentUrl());
            e.printStackTrace();
            Assert.fail("Medical information test failed", e);
        }
    }
}