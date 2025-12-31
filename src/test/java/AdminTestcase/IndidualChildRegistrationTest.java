
package AdminTestcase;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import AdminPages.Childinfo;
import AdminPages.Parentpage;
import AdminPages.LoginPage;
import AdminPages.PhysicianInfoPage;
import AdminPages.ChooseplanPage;
import AdminPages.IndidualChildChoosePlanPage;
import AdminPages.IndidualChildPage;
import AdminPages.IndidualChildPhyscianPage;
import AdminPages.RegistrationPage;
import Base.BaseDriver;

@Listeners(listeners.TestListener.class)
public class IndidualChildRegistrationTest extends BaseDriver {
	
	@Test
    public void validateCompleteRegistrationFlow() {
        System.out.println("Starting the complete registration test flow.");
        try {
            // Step 1: Login to the application and navigate to the enrollment page
            System.out.println("-----> Logging in and navigating to Enrollment...");
            LoginPage login = new LoginPage(driver);
            login.enterEmail("srasysife@gmail.com");
            login.enterPassword("Admin@123");
            login.clickSignIn();
            
            Childinfo childinfo = new Childinfo(driver);
            childinfo.clickOnenrollment();

            // Navigate to Child Info Page
            IndidualChildPage indidualchildPage = new IndidualChildPage(driver);
            indidualchildPage.enterRandomFirstAndLastName();
            //enrollmentPage.enterDetailsForAdditionChild()
            //enrollmentPage.enterRandomFirstAndLastName();
            //enrollmentPage.enterRandomFirstAndLastName();
            indidualchildPage.selectGender();
            indidualchildPage.enterrandomDOB();
            indidualchildPage.ClickonProceedtoGuardian();
              // Step 3: Verify page titles
            String title = indidualchildPage.getchildinfopageTitleText();
            System.out.println("Child Enrollment Title: " + title);
            Assert.assertEquals(title, "Child Enrollment", "Child enrollment page title mismatch");


            // Step 3: Fill out the parent/guardian information with random data
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
            Thread.sleep(3000);
            
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
            Thread.sleep(8000); // Wait for page navigation

            // Step 5: Verify navigation to medical page
            IndidualChildPhyscianPage medicalinfo = new IndidualChildPhyscianPage(driver);
            
            // Step 5: Fill physician and emergency information
            //PhysicianInfoPage medicalinfo = new PhysicianInfoPage(driver);
            
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
            Thread.sleep(1000);
            
            medicalinfo.selectRandomPhysicianPhoneType();
            Thread.sleep(3000);
            
            medicalinfo.selectRandomPhysicianCountryCode();
            Thread.sleep(2000);
            
            medicalinfo.enterRandomPhysicianPhoneNumber();
            Thread.sleep(2000);
            
            // Fill emergency care information
            medicalinfo.enterRandomCareFacilityName();
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
            
            // Toggle the checkbox if needed
            //medicalinfo.toggleUsePhysicianForAll(true);
            Thread.sleep(2000);
            
            // Click the final proceed button
            System.out.println("Clicking final proceed button...");
            medicalinfo.proceedToNext();
            Thread.sleep(5000);
            
            //String pageHeader1 = medicalinfo.verifyPhysicianInfoPage();
            //System.out.println("On page: " + pageHeader1);
            IndidualChildChoosePlanPage indiduachooseplanPage = new IndidualChildChoosePlanPage(driver);
            
            indiduachooseplanPage.VerifyChoosePlanPage();
            Thread.sleep(2000);
            indiduachooseplanPage.SelectAPlan();
            Thread.sleep(2000);
            indiduachooseplanPage.selectRandomMonthPlan();
            Thread.sleep(2000);
            indiduachooseplanPage.ProceedToCompleteRegestration();
            Thread.sleep(2000);
           
///}}
            // Step 6: Complete registration and verify confirmation
            System.out.println("-----> Completing registration and verifying confirmation page...");
            RegistrationPage registrationPage = new RegistrationPage(driver);
            registrationPage.clickRegisterAndConfirm(); // Clicks button and confirms modal

            // Verify that the final confirmation modal is displayed
            boolean modalDisplayed = registrationPage.verifyFinalConfirmationModalIsLoaded();
            Assert.assertTrue(modalDisplayed, "Final confirmation modal was not displayed.");
            
            // Click the "Return to Dashboard" button to complete the flow
            registrationPage.clickReturnToDashboardButton();
            
            System.out.println("Complete registration test flow finished successfully.");
            
        } catch (Exception e) {
            System.out.println("Test failed during registration flow: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed during registration flow.", e);
        }
    }
}
            
            
            
 /*           
            // Step 2: Fill out Child Information
            System.out.println("-----> Filling out Child Information page...");
            childinfo.enterRandomFirstAndLastName();
            childinfo.selectGender();
            childinfo.enterrandomDOB();
            childinfo.ClickonProceedtoGuardian();

            // Step 3: Fill out Parent/Guardian Information
            System.out.println("-----> Filling out Parent/Guardian Information page...");
            Parentpage parentPage = new Parentpage(driver);
            parentPage.enterRandomFirstName();
            parentPage.enterRandomMiddleName();
            parentPage.enterRandomLastName();
            parentPage.selectRelationship();
            parentPage.enterRandomAddress1();
            parentPage.enterRandomAddress2();
            parentPage.selectRandomCountry();
            parentPage.selectState();
            parentPage.enterRandomCity();
            parentPage.enterRandomZipCode();
            parentPage.selectRandomPhoneType();
            parentPage.enterRandomCountryCode();
            parentPage.enterRandomPhoneNumber();
            parentPage.enterRandomEmailAddress();
            parentPage.proceedToMedicalInfoButton();

            // Step 4: Fill out Physician and Emergency Care Facility Information
            System.out.println("-----> Filling out Physician and Emergency Info page...");
            PhysicianInfoPage physicianInfoPage = new PhysicianInfoPage(driver);
            physicianInfoPage.fillAllPhysicianInformation();
            physicianInfoPage.proceedToNext();
            
            // Step 5: Choose a plan
            System.out.println("-----> Choosing a plan...");
            ChooseplanPage chooseplanPage = new ChooseplanPage(driver);
            chooseplanPage.proceedWithSelectedPlan();
            
           // Step 6: Complete registration and verify confirmation
            System.out.println("-----> Completing registration and verifying confirmation page...");
            RegistrationPage registrationPage = new RegistrationPage(driver);
            registrationPage.clickRegisterAndConfirm(); // Clicks button and confirms modal

            // Verify that the final confirmation modal is displayed
            boolean modalDisplayed = registrationPage.verifyFinalConfirmationModalIsLoaded();
            Assert.assertTrue(modalDisplayed, "Final confirmation modal was not displayed.");
            
            // Click the "Return to Dashboard" button to complete the flow
            registrationPage.clickReturnToDashboardButton();
            
            System.out.println("Complete registration test flow finished successfully.");
            
        } catch (Exception e) {
            System.out.println("Test failed during registration flow: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed during registration flow.", e);
        }
    }
}
*/