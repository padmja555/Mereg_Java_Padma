package SuperAdminTestcase;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import AdminPages.*;
import Base.BaseDriver;
import SuperAdminPage.SuperChildChoosePlanPage;
import SuperAdminPage.SuperChildInfoPage;
import SuperAdminPage.SuperChildParentPage;
import SuperAdminPage.SuperChildPhyscianPage;
import SuperAdminPage.SuperEnrollmentPage;
import SuperAdminPage.SuperLoginPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class SuperChildChoosePlanTest extends BaseDriver {
    
    @Test
    public void SuperChildChooseTest() throws InterruptedException {
        //try {
        // Step 1: Login to the application
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
    
    	SuperChildPhyscianPage superchildphyscianpage = new SuperChildPhyscianPage(driver);
    	
        String pageHeader = superchildphyscianpage.verifyPhysicianInfoPage();
        System.out.println("On page: " + pageHeader);
    
         
        // Fill physician information
    	superchildphyscianpage.enterRandomPhysicianFirstName();
        Thread.sleep(1000);
        
        superchildphyscianpage.enterRandomPhysicianMiddleName();
        Thread.sleep(1000);
        
        superchildphyscianpage.enterRandomPhysicianLastName();
        Thread.sleep(1000);
        
        superchildphyscianpage.enterRandomPhysicianAddress1();
        Thread.sleep(1000);
        
        superchildphyscianpage.enterRandomPhysicianAddress2();
        Thread.sleep(1000);
        
        superchildphyscianpage.selectRandomPhysicianCountry();
        Thread.sleep(2000);
        
        superchildphyscianpage.selectPhysicianState();
        Thread.sleep(2000);
        
        superchildphyscianpage.enterRandomPhysicianCity();
        Thread.sleep(1000);
        
        superchildphyscianpage.enterRandomPhysicianZipCode();
        Thread.sleep(1000);
        
        superchildphyscianpage.selectRandomPhysicianPhoneType();
        Thread.sleep(3000);
        
        superchildphyscianpage.selectRandomPhysicianCountryCode();
        Thread.sleep(2000);
        
        superchildphyscianpage.enterRandomPhysicianPhoneNumber();
        Thread.sleep(2000);
        
        // Fill emergency care information
        superchildphyscianpage.enterRandomCareFacilityName();
        Thread.sleep(1000);
        
        superchildphyscianpage.selectRandomCareFacilityPhoneType();
        Thread.sleep(2000);
        
        superchildphyscianpage.selectRandomCareFacilityCountryCode();
        Thread.sleep(2000);
        
        superchildphyscianpage.enterRandomCareFacilityPhoneNumber();
        Thread.sleep(1000);
        
        superchildphyscianpage.enterRandomCareFacilityAddress1();
        Thread.sleep(1000);
        
        superchildphyscianpage.enterRandomCareFacilityAddress2();
        Thread.sleep(1000);
        
        superchildphyscianpage.selectRandomCareFacilityCountry();
        Thread.sleep(2000);
        
        superchildphyscianpage.selectCareFacilityState();
        Thread.sleep(2000);
        
        superchildphyscianpage.enterRandomCareFacilityCity();
        Thread.sleep(1000);
        
        superchildphyscianpage.enterRandomCareFacilityZipCode();
        Thread.sleep(1000);
        
        // Toggle the checkbox if needed
        //medicalinfo.toggleUsePhysicianForAll(true);
        Thread.sleep(2000);

        
        // Click the final proceed button
        System.out.println("Clicking final proceed button...");
        superchildphyscianpage.proceedToNext();
        Thread.sleep(5000);
        
        System.out.println("Medical information form completed successfully!");
            
    //} catch (Exception e) {
        //System.out.println("Error during medical info test: " + e.getMessage());
        //System.out.println("Current URL: " + driver.getCurrentUrl());
        //e.printStackTrace();
        //throw new RuntimeException("Medical information test failed", e);
    
            Thread.sleep(5000);
            
            //String pageHeader1 = medicalinfo.verifyPhysicianInfoPage();
            //System.out.println("On page: " + pageHeader1);
            SuperChildChoosePlanPage superchildchooseplanPage = new SuperChildChoosePlanPage(driver);
            
            superchildchooseplanPage.VerifyChoosePlanPage();
            Thread.sleep(2000);
            superchildchooseplanPage.SelectAPlan();
            Thread.sleep(2000);
            superchildchooseplanPage.selectRandomMonthPlan();
            Thread.sleep(2000);
            superchildchooseplanPage.ProceedToCompleteRegestration();
            Thread.sleep(2000);
           
}}
