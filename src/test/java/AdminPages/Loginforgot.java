package AdminPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Loginforgot {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//a[contains(text(), 'Forgot Password?')]")
    private WebElement forgotPasswordLink;

    public Loginforgot(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    public ForgotPasswordPage clickForgotPassword() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink)).click();
        return new ForgotPasswordPage(driver);
    }
}