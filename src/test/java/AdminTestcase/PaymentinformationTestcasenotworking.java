package AdminTestcase;

import Base.BaseDriver;
import AdminPages.LoginPage;
import AdminPages.Paymentinformationpagenotworking;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class PaymentinformationTestcasenotworking extends BaseDriver {

    @Test
    public void paymentTest() throws InterruptedException {
        // Login
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        // Payment Page object
        Paymentinformationpagenotworking payment = new Paymentinformationpagenotworking(driver);

        // Navigate to payment page
        payment.clickOnPaymentPage();

        // Verify page title
        String title = payment.getPaymentPageTitleText();
        System.out.println("Payment Title: " + title);
        Assert.assertEquals(title, "Payment", "Payment page title mismatch");

        // Select first guardian dynamically
        List<String> guardians = payment.getGuardianOptions();
        Assert.assertFalse(guardians.isEmpty(), "Guardian list should not be empty");
        String guardianToSelect = guardians.get(0);
        System.out.println("Selecting guardian: " + guardianToSelect);
        payment.selectGuardian(guardianToSelect);

        // Select first child dynamically
        List<String> children = payment.getChildOptions();
        Assert.assertFalse(children.isEmpty(), "Child list should not be empty");
        String childToSelect = children.get(0);
        System.out.println("Selecting child: " + childToSelect);
        payment.Selecttochild(childToSelect);

        // Proceed and process payment
        payment.clickProceedToPayment();
        payment.processPayment("4111111111111111", "12/25", "123");

        // TODO: Add asserts for payment success
    }
}
