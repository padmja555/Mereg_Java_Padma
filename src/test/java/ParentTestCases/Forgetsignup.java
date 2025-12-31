package ParentTestCases;

import javax.mail.*;
import javax.mail.search.SubjectTerm;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.Duration;
import java.io.File;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Forgetsignup {

    private WebDriver driver;
    private WebDriverWait wait;

    // Config
    private static final String EMAIL = "padmavathi555divyakolu@gmail.com";
    private static final String EMAIL_PASSWORD = "qizk fsjc dvam ajha"; // Gmail app password
    private static final String IMAP_HOST = "imap.gmail.com";
    private static final String APP_URL = "https://mereg.netlify.app/createaccount";
    private static final String EMAIL_SUBJECT = "MeReg - Email Verification OTP";

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications", "--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void testSignUpWithOTP() throws Exception {
        try {
            driver.get(APP_URL);
            System.out.println("Opened signup page");

            // Fill all required fields
            driver.findElement(By.name("firstName")).sendKeys("Padma");
            driver.findElement(By.name("lastName")).sendKeys("Pamidi");
            driver.findElement(By.xpath("//input[@placeholder='sample@xyz.com']")).sendKeys(EMAIL);

            Select countryCodeSelect = new Select(driver.findElement(By.name("countryCode")));
            countryCodeSelect.selectByValue("+1");

            driver.findElement(By.xpath("//input[@placeholder='1234567890']")).sendKeys("9492744717");
            driver.findElement(By.xpath("//input[@placeholder='Enter Address line 1']")).sendKeys("123 Main Street");
            driver.findElement(By.xpath("//input[@placeholder='Enter city']")).sendKeys("Vij");

            Select countrySelect = new Select(driver.findElement(By.name("country")));
            countrySelect.selectByVisibleText("United States");

            Select stateSelect = new Select(driver.findElement(By.name("state")));
            stateSelect.selectByVisibleText("Florida");

            driver.findElement(By.xpath("//input[@placeholder='Enter zip code']")).sendKeys("123456");
            driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("Chelshitha$123");
            driver.findElement(By.xpath("//input[@placeholder='Confirm Password']")).sendKeys("Chelshitha$123");

            WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
            submitBtn.click();
            System.out.println("Submitted signup form");

            // Wait for OTP input page to load
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@type='email' and @placeholder='Enter your email']")));
            emailField.clear();
            emailField.sendKeys(EMAIL);
            System.out.println("Entered email: " + EMAIL);

            WebElement sendOtpButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Send OTP']")));
            sendOtpButton.click();
            System.out.println("Clicked Send OTP button");

            // Wait and fetch OTP
            String otp = fetchMeRegOTPWithRetry(3, 15000);
            System.out.println("Extracted OTP: " + otp);

            // Enter OTP in the input field
            WebElement otpField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input.email-input[placeholder='Enter OTP']")));
            otpField.clear();
            for (char c : otp.toCharArray()) {
                otpField.sendKeys(Character.toString(c));
                Thread.sleep(100); // Small delay between characters
            }
            System.out.println("OTP entered in input field");

            // Click Verify OTP button
            WebElement verifyButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'next-btn')]//span[contains(text(), 'Verify OTP')]")));
            
            // Scroll into view and highlight for debugging
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", verifyButton);
            ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", verifyButton);
            Thread.sleep(500);
            
            verifyButton.click();
            System.out.println("Clicked Verify OTP button");

            // Verify successful submission
            wait.until(ExpectedConditions.invisibilityOf(verifyButton));
            System.out.println("OTP verification successful!");

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            takeScreenshot("failure_" + System.currentTimeMillis());
            throw e;
        }
    }

    private void takeScreenshot(String filename) {
        try {
            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(filename + ".png"));
            System.out.println("Screenshot saved as: " + filename + ".png");
        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
        }
    }

    private String fetchMeRegOTPWithRetry(int maxRetries, long retryIntervalMs) throws Exception {
        for (int i = 0; i < maxRetries; i++) {
            try {
                return fetchMeRegOTPFromEmail();
            } catch (AuthenticationFailedException authEx) {
                throw new RuntimeException(getGmailAuthInstructions(), authEx);
            } catch (Exception e) {
                if (i == maxRetries - 1) throw e;
                System.out.println("Attempt " + (i+1) + " failed. Retrying in " + (retryIntervalMs/1000) + " seconds...");
                Thread.sleep(retryIntervalMs);
            }
        }
        throw new RuntimeException("Failed to fetch OTP after " + maxRetries + " attempts");
    }

    private String getGmailAuthInstructions() {
        return "\n\nGMAIL AUTHENTICATION SETUP REQUIRED:\n" +
               "1. Go to: https://myaccount.google.com/security\n" +
               "2. If you DON'T have 2FA enabled:\n" +
               "   - Turn ON 'Less secure app access'\n" +
               "3. If you HAVE 2FA enabled:\n" +
               "   - Create an 'App Password':\n" +
               "     a) Go to: https://myaccount.google.com/apppasswords\n" +
               "     b) Select 'Mail' and device\n" +
               "     c) Generate 16-character password\n" +
               "     d) Use this password in the EMAIL_PASSWORD field\n" +
               "4. Ensure IMAP is enabled in Gmail settings\n" +
               "5. Try running the test again";
    }

    private String fetchMeRegOTPFromEmail() throws Exception {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", IMAP_HOST);
        props.put("mail.imaps.port", "993");
        props.put("mail.imaps.ssl.enable", "true");
        props.put("mail.imaps.timeout", "30000");
        props.put("mail.imaps.ssl.protocols", "TLSv1.2");
        
        Session session = Session.getInstance(props);
        Store store = session.getStore("imaps");
        
        try {
            System.out.println("Attempting to connect to email server...");
            store.connect(IMAP_HOST, EMAIL, EMAIL_PASSWORD);
            System.out.println("Successfully connected to email server");

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.search(new SubjectTerm(EMAIL_SUBJECT), inbox.getMessages());
            if (messages.length == 0) {
                throw new RuntimeException("No OTP email found with subject: " + EMAIL_SUBJECT);
            }

            // Sort messages by date to get the latest one
            Arrays.sort(messages, (m1, m2) -> {
                try {
                    return m2.getSentDate().compareTo(m1.getSentDate());
                } catch (MessagingException e) {
                    return 0;
                }
            });

            Message latestMessage = messages[0];
            String content = extractEmailContent(latestMessage);
            System.out.println("Email content: " + content);
            return extractOTPFromContent(content);

        } finally {
            if (store != null && store.isConnected()) {
                store.close();
            }
        }
    }

    private String extractEmailContent(Message message) throws Exception {
        try {
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
                        content.append(org.jsoup.Jsoup.parse(html).text());
                    }
                }
                
                return content.toString();
            }

            return message.getContent().toString();
        } catch (Exception e) {
            throw new Exception("Failed to extract email content: " + e.getMessage(), e);
        }
    }

    private String extractOTPFromContent(String content) {
        // Try multiple patterns to extract OTP
        Pattern[] patterns = {
            Pattern.compile("Your OTP for account verification is:\\s*[\\n\\r]+\\s*(\\d{6})"),
            Pattern.compile("OTP is:\\s*(\\d{6})"),
            Pattern.compile("(\\d{6})\\s*is your verification code"),
            Pattern.compile("\\b\\d{6}\\b")
        };

        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(content);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        throw new RuntimeException("Failed to extract OTP from email content. Content was: " + content);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser session ended");
        }
    }
}