package AdminPages;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.search.SubjectTerm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage {

    WebDriver driver;

    // Gmail credentials (App Password recommended)
    String gmailUsername = "tohiw33350@namestal.com";
    String gmailPassword = "qvzf ndts exbn gsip";  // <-- must be App Password

    // Locators
    By forgotPasswordLink = By.linkText("Forgot Password?");
    By emailInput = By.cssSelector("input.email-input");
    By sendOtpButton = By.xpath("//button[contains(@class,'next-btn')]//span[text()='Send OTP']");
    By otpInput = By.cssSelector("input[placeholder='Enter OTP']");
    By verifyOtpButton = By.xpath("//span[text()='Verify OTP']");
    By newPasswordInput = By.cssSelector("input[placeholder='New Password']");
    By confirmPasswordInput = By.cssSelector("input[placeholder='Confirm Password']");
    By resetPasswordButton = By.xpath("//span[text()='Reset Password']");

    // Constructor
    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void clickForgotPassword() {
        driver.findElement(forgotPasswordLink).click();
    }

    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void clickSendOtp() {
        driver.findElement(sendOtpButton).click();
    }

    public void enterOtp(String otp) {
        driver.findElement(otpInput).sendKeys(otp);
    }

    public void clickVerifyOtp() {
        driver.findElement(verifyOtpButton).click();
    }

    public void enterNewPassword(String password) {
        driver.findElement(newPasswordInput).sendKeys(password);
    }

    public void enterConfirmPassword(String password) {
        driver.findElement(confirmPasswordInput).sendKeys(password);
    }

    public void clickResetPassword() {
        driver.findElement(resetPasswordButton).click();
    }

    // Fetch OTP from Gmail
    public String getOTPFromGmail() throws Exception {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");

        Session emailSession = Session.getInstance(properties);
        Store store = emailSession.getStore("imaps");
        store.connect("imap.gmail.com", gmailUsername, gmailPassword);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        // Search for the latest email with subject containing "OTP"
        Message[] messages = inbox.search(new SubjectTerm("OTP"));

        if (messages.length == 0) {
            throw new Exception("No OTP email found");
        }

        Message latestMessage = messages[messages.length - 1];
        String content = latestMessage.getContent().toString();

        // Extract 6-digit OTP using regex
        Pattern pattern = Pattern.compile("\\b\\d{6}\\b");
        Matcher matcher = pattern.matcher(content);

        String otp = null;
        if (matcher.find()) {
            otp = matcher.group();
        }

        inbox.close(false);
        store.close();

        if (otp == null) {
            throw new Exception("OTP not found in email content");
        }
        return otp;
    }
}
