package AdminPages;


import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import javax.mail;
//import javax.mail.*;
//import javax.mail.internet.*;
//import javax.mail.search.SubjectTerm;

import org.jsoup.Jsoup;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jakarta.mail.BodyPart;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.search.SubjectTerm;

import java.time.Duration;

public class CreateAccountPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Config
    private static final String EMAIL = "padmavathi555divyakolu@gmail.com";
    private static final String EMAIL_PASSWORD = "qvzf ndts exbn gsip"; // app password recommended
    private static final String IMAP_HOST = "imap.gmail.com";
    private static final String EMAIL_SUBJECT = "MeReg - Email Verification OTP";

    // Locators
    By createAccountLink = By.xpath("//a[@routerlink='/createaccount' and text()='Create an Account']");
    By firstName = By.name("firstName");
    By lastName = By.name("lastName");
    By addressLine1 = By.id("mat-input-5");
    By countryDropdown = By.xpath("//mat-select[@name='country']");
    By countryOptionUS = By.xpath("//mat-option//span[contains(text(),'United States')]");
    By stateDropdown = By.xpath("//mat-select[@name='state']");
    By stateOptionCA = By.xpath("//mat-option//span[contains(text(),'California')]");
    By city = By.name("city");
    By zipCode = By.name("zipCode");
    By phoneTypeDropdown = By.xpath("//mat-select[@name='phoneType']");
    By phoneTypeCell = By.xpath("//mat-option//span[contains(text(),'Cell')]");
    By countryCodeDropdown = By.name("countryCode");
    By countryCodeUS = By.xpath("//mat-option/span[text()='+1 (US)']");
    By phoneNumber = By.id("mat-input-10");
    By emailField = By.id("mat-input-12");
    By password = By.id("mat-input-13");
    By confirmPassword = By.id("mat-input-14");
    By createButton = By.xpath("//button[@type='submit']");
    By otpField = By.xpath("//input[@id='otp' and @formcontrolname='otp' and @placeholder='000000']");
    By verifyButton = By.xpath("//button[@type='submit' and contains(@class, 'btn-primary') and normalize-space()='Create Account']");

    // Constructor
    public CreateAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Actions
    public void clickCreateAccountLink() {
        wait.until(ExpectedConditions.elementToBeClickable(createAccountLink)).click();
    }

    public void fillRegistrationForm() {
        driver.findElement(firstName).sendKeys("Padma");
        driver.findElement(lastName).sendKeys("divyakolu");
        driver.findElement(addressLine1).sendKeys("123 Main Street");

        // Country
        wait.until(ExpectedConditions.elementToBeClickable(countryDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(countryOptionUS)).click();

        // State
        wait.until(ExpectedConditions.elementToBeClickable(stateDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(stateOptionCA)).click();

        driver.findElement(city).sendKeys("Vij");
        driver.findElement(zipCode).sendKeys("123456");

        // Phone type
        wait.until(ExpectedConditions.elementToBeClickable(phoneTypeDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(phoneTypeCell)).click();

        // Country code
        driver.findElement(countryCodeDropdown).click();
        wait.until(ExpectedConditions.elementToBeClickable(countryCodeUS)).click();

        driver.findElement(phoneNumber).sendKeys("1234567890");
        driver.findElement(emailField).sendKeys(EMAIL);
        driver.findElement(password).sendKeys("Saketh@123");
        driver.findElement(confirmPassword).sendKeys("Saketh@123");
    }

    public void submitRegistrationForm() {
        wait.until(ExpectedConditions.elementToBeClickable(createButton)).click();
    }

    public void enterOTP(String otp) throws InterruptedException {
        WebElement otpInput = wait.until(ExpectedConditions.visibilityOfElementLocated(otpField));
        otpInput.clear();
        for (char c : otp.toCharArray()) {
            otpInput.sendKeys(Character.toString(c));
            Thread.sleep(100);
        }
    }

    public void clickVerifyButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(verifyButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", btn);
        btn.click();
    }

    // Gmail OTP Fetching
    public String fetchMeRegOTPWithRetry(int maxRetries, long retryIntervalMs) throws Exception {
        for (int i = 0; i < maxRetries; i++) {
            try {
                return fetchMeRegOTPFromEmail();
            } catch (Exception e) {
                if (i == maxRetries - 1) throw e;
                Thread.sleep(retryIntervalMs);
            }
        }
        throw new RuntimeException("Failed to fetch OTP after " + maxRetries + " attempts");
    }

    private String fetchMeRegOTPFromEmail() throws Exception {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", IMAP_HOST);
        props.put("mail.imaps.port", "993");
        props.put("mail.imaps.ssl.enable", "true");
        props.put("mail.imaps.timeout", "30000");

        Session session = Session.getInstance(props);
        Store store = session.getStore("imaps");

        store.connect(IMAP_HOST, EMAIL, EMAIL_PASSWORD);
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message[] messages = inbox.search(new SubjectTerm(EMAIL_SUBJECT), inbox.getMessages());
        if (messages.length == 0) {
            throw new RuntimeException("No OTP email found with subject: " + EMAIL_SUBJECT);
        }

        Message latestMessage = messages[messages.length - 1];
        String content = extractEmailContent(latestMessage);
        return extractOTPFromContent(content);
    }

    private String extractEmailContent(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        }

        if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();
            StringBuilder content = new StringBuilder();
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    content.append(bodyPart.getContent().toString());
                } else if (bodyPart.isMimeType("text/html")) {
                    String html = bodyPart.getContent().toString();
                    content.append(Jsoup.parse(html).text());
                }
            }
            if (content.length() > 0) return content.toString();
        }
        return message.getContent().toString();
    }

    private String extractOTPFromContent(String content) {
        Pattern pattern = Pattern.compile("\\b\\d{6}\\b");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new RuntimeException("Failed to extract OTP from email content. Content: " + content);
    }
}