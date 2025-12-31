package AdminTestcase;

import org.testng.annotations.Test;
import AdminPages.Childinfo;
import AdminPages.Parentpage;
import AdminPages.LoginPage;
import AdminPages.PhysicianInfoPage;
import Base.BaseDriver;

public class PhysicialinfoTest extends BaseDriver {
    
    @Test
    public void medicalInfoTest() {
        try {
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

            // Step 3: Fill out the parent/guardian information
            Parentpage parentguardianinfo = new Parentpage(driver);
            
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
            
            parentguardianinfo.selectRandomCountry();
            Thread.sleep(2000);
            
            parentguardianinfo.selectState();
            Thread.sleep(2000);
            
            parentguardianinfo.enterRandomCity();
            Thread.sleep(1000);
            
            parentguardianinfo.enterRandomZipCode();
            Thread.sleep(1000);
            
            parentguardianinfo.selectRandomPhoneType();
            Thread.sleep(2000);
            
            parentguardianinfo.enterRandomCountryCode();
            Thread.sleep(2000);
            
            parentguardianinfo.enterRandomPhoneNumber();
            Thread.sleep(2000);
            
            parentguardianinfo.alternateGuardianInfoIconButton();
            Thread.sleep(2000);
            
            parentguardianinfo.enterRandomAlternateGuardianName();
            Thread.sleep(1000);
            
            parentguardianinfo.alterGuardianSelectRelationship();
            Thread.sleep(2000);
            
            parentguardianinfo.alternateGuardianCountryCode();
            Thread.sleep(1000);
            
            parentguardianinfo.enterRandomAlternateGuardianPhoneNumber();
            Thread.sleep(1000);
            
            parentguardianinfo.enterRandomEmailAddress();
            Thread.sleep(1000);
            
            // Step 4: Click proceed to medical info
            System.out.println("Clicking Proceed to Medical Info button...");
            parentguardianinfo.proceedToMedicalInfoButton();
            Thread.sleep(8000);

            // Step 5: Fill physician and emergency information
            PhysicianInfoPage medicalinfo = new PhysicianInfoPage(driver);
            
            // Verify we're on the physician info page
            String pageHeader = medicalinfo.verifyPhysicianInfoPage();
            System.out.println("On page: " + pageHeader);
            
            // Fill physician information
            medicalinfo.enterRandomPhysicianFirstName();
            Thread.sleep(1000);
            
            medicalinfo.enterRandomPhysicianMiddleName();
            Thread.sleep(1000);
            
            medicalinfo.enterRandomPhysicianLastName();
            Thread.sleep(1000);
            
            medicalinfo.enterRandomPhysicianAddress1();
            Thread.sleep(1000);
            
            medicalinfo.enterRandomPhysicianAddress2();
            Thread.sleep(1000);
            
            medicalinfo.selectRandomPhysicianCountry();
            Thread.sleep(2000);
            
            medicalinfo.selectPhysicianState();
            Thread.sleep(2000);
            
            medicalinfo.enterRandomPhysicianCity();
            Thread.sleep(1000);
            
            medicalinfo.enterRandomPhysicianZipCode();
            Thread.sleep(2000);
            
            medicalinfo.selectRandomPhysicianPhoneType();
            Thread.sleep(3000);
            
            medicalinfo.selectRandomPhysicianCountryCode();
            Thread.sleep(2000);
            
            medicalinfo.enterRandomPhysicianPhoneNumber();
            Thread.sleep(2000);
            
            // Fill emergency care information
            //medicalinfo.enterRandomCareFacilityName();
            Thread.sleep(1000);
            
            medicalinfo.selectRandomCareFacilityPhoneType();
            Thread.sleep(2000);
            
            medicalinfo.selectRandomCareFacilityCountryCode();
            Thread.sleep(2000);
            
            medicalinfo.enterRandomCareFacilityPhoneNumber();
            Thread.sleep(1000);
            
            medicalinfo.enterRandomCareFacilityAddress1();
            Thread.sleep(1000);
            
            medicalinfo.enterRandomCareFacilityAddress2();
            Thread.sleep(1000);
            
            medicalinfo.selectRandomCareFacilityCountry();
            Thread.sleep(2000);
            
            medicalinfo.selectCareFacilityState();
            Thread.sleep(2000);
            
            medicalinfo.enterRandomCareFacilityCity();
            Thread.sleep(1000);
            
            medicalinfo.enterRandomCareFacilityZipCode();
            Thread.sleep(1000);
            
            //Toggle the checkbox if needed
            medicalinfo.toggleUsePhysicianForAll(true);
            Thread.sleep(2000);
            
            // Click the final proceed button
            System.out.println("Clicking final proceed button...");
            medicalinfo.proceedToNext();
            Thread.sleep(5000);
            
            System.out.println("Medical information form completed successfully!");
            
        } catch (Exception e) {
            System.out.println("Error during medical info test: " + e.getMessage());
            System.out.println("Current URL: " + driver.getCurrentUrl());
            e.printStackTrace();
            throw new RuntimeException("Medical information test failed", e);
        }
    }
}	
	