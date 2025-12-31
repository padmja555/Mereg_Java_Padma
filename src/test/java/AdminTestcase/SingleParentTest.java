package AdminTestcase;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import AdminPages.Childinfo;
import AdminPages.Singlechildparentpage;
import Base.BaseDriver;
import AdminPages.LoginPage;

public class SingleParentTest extends BaseDriver {

    @Test
    public void parentguardianTest() throws Exception {
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        Childinfo childinfo = new Childinfo(driver);
        childinfo.clickOnenrollment();
        childinfo.enterRandomFirstAndLastName();
        childinfo.selectGender();
        Thread.sleep(3000);
        childinfo.enterrandomDOB();
        Thread.sleep(2000);
        childinfo.ClickonProceedtoGuardian();
        Thread.sleep(5000); // Increased wait time
        
        String title = childinfo.getchildinfopageTitleText();
        System.out.println("childinfo Title: " + title);
        Assert.assertEquals(title, "Child Enrollment", "childinfo page title mismatch");

        Singlechildparentpage parentguardianinfo = new Singlechildparentpage(driver);
        String parentguardiantitle = parentguardianinfo.VerifyParentGuardianPage();
        System.out.println("parentguardian Title: " + parentguardiantitle);
        Assert.assertEquals(parentguardiantitle, "Parent/Guardian info");

        parentguardianinfo.enterFirstName("Padma");
        parentguardianinfo.enterMiddleName("K");
        parentguardianinfo.enterLastName("divyakolu");
        parentguardianinfo.selectRelationship();
        parentguardianinfo.EnterAddress1("Newyork");
        parentguardianinfo.EnterAddress2("Missippi");
        
        // Select country and store the selected country
        String selectedCountry = parentguardianinfo.selectRandomCountry();
        Thread.sleep(2000); // Wait after country selection
        
        parentguardianinfo.selectStateBasedOnCountry();
        Thread.sleep(1000);
        
        parentguardianinfo.enterCityBasedOnCountry();
        Thread.sleep(1000);
        
        parentguardianinfo.enterZipCodeBasedOnCountry();
        Thread.sleep(1000);
        
        parentguardianinfo.selectPhoneType();
        Thread.sleep(1000);
        
        parentguardianinfo.selectCountryCode();
        Thread.sleep(1000);
        
        parentguardianinfo.enterPhoneNumber("3304857261");
        Thread.sleep(1000);
        
        parentguardianinfo.enterEmailAddress("test@example.com");
        Thread.sleep(1000);

        parentguardianinfo.AlternateGuardianinfoIconButton();
        Thread.sleep(1000);
        
        parentguardianinfo.enterAlternateGuardianName("Padma");
        parentguardianinfo.AlterGuardianselectRelationship();
        parentguardianinfo.selectCountryCode();
        parentguardianinfo.AlterGuardiansPhoneNumber("7654123908");
        parentguardianinfo.ProceedToMedicalInfoButton();  
    }
}