package ParentPages;

import org.openqa.selenium.WebDriver;

public class ParentHomePage {
    WebDriver driver;

    public ParentHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isParentHomeDisplayed() {
        return driver.getTitle().contains("Dashboard") || driver.getCurrentUrl().contains("dashboard");
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
