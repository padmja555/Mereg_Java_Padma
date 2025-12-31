package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By adminDashboard = By.xpath("//h1[contains(text(),'Dashboard')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isAdminHomeDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(adminDashboard)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
