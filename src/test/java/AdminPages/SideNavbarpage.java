package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SideNavbarpage {
    WebDriver driver;

    // Locator for the title that says "MeReg Workspace"
    By workspaceTitle = By.xpath("//h3[normalize-space()='MeReg Workspace']");
    By adminField = By.xpath("//h4[normalize-space()='Admin']");

    public SideNavbarpage(WebDriver driver) {
        this.driver = driver;
    }

    public String getWorkspaceTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(workspaceTitle));
        return driver.findElement(workspaceTitle).getText();
    }

    public void clickOnAdmin() {
        driver.findElement(adminField).click();
    }
}

