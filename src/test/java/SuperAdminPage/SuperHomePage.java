package SuperAdminPage;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SuperHomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    //private By adminDashboard = By.xpath("//h3[@class='title']");
    private By adminDashboard = By.xpath("//h3[contains(text(),'Administrator Screen') or contains(@class,'title')]");


    public SuperHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    
    public boolean isAdminHomeDisplayed() {
        try {
            System.out.println("Current URL after login: " + driver.getCurrentUrl());
            return wait.until(ExpectedConditions.visibilityOfElementLocated(adminDashboard)).isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    /*public boolean isAdminHomeDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(adminDashboard)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    */
}

