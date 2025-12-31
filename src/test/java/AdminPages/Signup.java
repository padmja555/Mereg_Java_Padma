package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Signup {
    private WebDriver driver;
    private WebDriverWait wait;

    public Signup(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // --- Signup Form Fields ---
    @FindBy(xpath = "//a[@routerlink='/createaccount' and text()='Create an Account']")
    private WebElement createAccountLink;

    @FindBy(xpath = "//*[@name='firstName']")
    private WebElement firstName;

    @FindBy(xpath = "//*[@name='lastName']")
    private WebElement lastName;

    @FindBy(xpath = "//*[@name='email']")
    private WebElement email;

    @FindBy(xpath = "//*[@name='countryCode']")
    private WebElement countryCode;

    @FindBy(xpath = "//*[@name='phoneNumber']")
    private WebElement phoneNumber;

    @FindBy(xpath = "//*[@name='address']")
    private WebElement address;

    @FindBy(xpath = "//*[@name='city']")
    private WebElement city;

    @FindBy(xpath = "//*[@name='country']")
    private WebElement country;

    @FindBy(xpath = "//*[@name='state']")
    private WebElement state;

    @FindBy(xpath = "//*[@name='zipcode']")
    private WebElement zipCode;

    @FindBy(xpath = "//*[@name='password']")
    private WebElement password;

    @FindBy(xpath = "//*[@name='confirmPassword']")
    private WebElement confirmPassword;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement createAccountButton;

    // --- OTP Field ---
    @FindBy(xpath = "//*[@id='otp']")
    private WebElement otpField;

    @FindBy(xpath = "//a[@routerlink='/createaccount']")
    private WebElement finalCreateAccountButton;
    ////a[@routerlink='/createaccount']
    // --- Actions ---

    public void clickCreateAccountLink() {
        createAccountLink.click();
    }

    public void fillSignupForm(String fName, String lName, String emailVal, String phoneVal) {
        firstName.sendKeys(fName);
        lastName.sendKeys(lName);
        email.sendKeys(emailVal);
        countryCode.sendKeys("1");
        phoneNumber.sendKeys(phoneVal);
        address.sendKeys("123 Main Street");
        city.sendKeys("New York");
        country.sendKeys("United States");
        state.sendKeys("New York");
        zipCode.sendKeys("10001");
        password.sendKeys("Test@12345");
        confirmPassword.sendKeys("Test@12345");
    }

    public void clickCreateAccount() {
        createAccountButton.click();
    }

    public void enterOTP(String otp) throws InterruptedException {
        otpField.clear();
        for (char c : otp.toCharArray()) {
            otpField.sendKeys(Character.toString(c));
            Thread.sleep(100); // Simulate typing
        }
    }

    public void clickFinalSubmit() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", finalCreateAccountButton);
        finalCreateAccountButton.click();
    }
}
