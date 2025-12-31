package AdminTestcase;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import AdminPages.Childinfo;
import AdminPages.Parentpage;
import AdminPages.LoginPage;
import AdminPages.PhysicianInfoPage;
import AdminPages.ChooseplanPage;
import AdminPages.RegistrationPage;
import Base.BaseDriver;

@Listeners(listeners.TestListener.class)
public class CompleteRegistrationTest extends BaseDriver {
	
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