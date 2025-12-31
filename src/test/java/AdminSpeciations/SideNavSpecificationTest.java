package AdminSpeciations;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import Base.BaseDriver;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

public class SideNavSpecificationTest extends BaseDriver {

    By emailInput = By.xpath("//input[@id='login-username']");
    By passwordInput = By.xpath("//input[@id='login-password']");
    By signInButton = By.xpath("//a[@class='mat-focus-indicator mat-raised-button mat-button-base']");

    // Map link names to their XPaths
    Map<String, String> sidebarLinks = new LinkedHashMap<>() {{
        put("Admin", "//h4[normalize-space()='Admin']");
        put("Enrollment", "//h4[normalize-space()='Enrollment']");
        put("Students", "//h4[normalize-space()='Students']");
        put("Payment", "//h4[normalize-space()='Payment']");
        put("Reports", "//h4[normalize-space()='Reports']");
        put("Messages", "//h4[normalize-space()='Messages']");
        put("Logout", "//a[@ng-reflect-router-link='../']//div[@class='mat-list-item-content']");
    }};

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(password);
    }

    public void clickSignIn() {
        wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
    }

    public boolean login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
        try {
            WebElement dashboard = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h1[contains(text(),'Dashboard')] | //span[contains(text(),'Welcome to MeReg')]")
            ));
            return dashboard.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    public void testSidebarLinksAndCssSpecs() throws InterruptedException {
        // Login first
        Assert.assertTrue(login("srasysife@gmail.com", "Admin@123"), "Login failed!");

        for (Map.Entry<String, String> entry : sidebarLinks.entrySet()) {
            String linkName = entry.getKey();
            By linkXPath = By.xpath(entry.getValue());

            // Skip logout link for now
            if (linkName.equalsIgnoreCase("Logout")) {
                continue;
            }

            try {
                // Re-locate the element fresh every time to avoid stale reference
                WebElement link = wait.until(ExpectedConditions.elementToBeClickable(linkXPath));
                System.out.println("\nClicking sidebar link: " + linkName);
                link.click();

                // Wait for corresponding header/page to appear
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//h1[contains(text(),'" + linkName + "')] | //span[contains(text(),'" + linkName + "')]")
                    ));
                } catch (Exception e) {
                    System.out.println("⚠ Warning: Header for '" + linkName + "' not found.");
                }

                // Re-locate the element again before getting CSS (page reload may have changed DOM)
                link = wait.until(ExpectedConditions.visibilityOfElementLocated(linkXPath));

                // Print CSS specifications
                System.out.println("CSS specifications for link '" + linkName + "':");
                System.out.println("Font Size: " + link.getCssValue("font-size"));
                System.out.println("Font Family: " + link.getCssValue("font-family"));
                System.out.println("Color: " + link.getCssValue("color"));
                System.out.println("Padding: " + link.getCssValue("padding"));
                System.out.println("Background Color: " + link.getCssValue("background-color"));
                System.out.println("Border Radius: " + link.getCssValue("border-radius"));

                Thread.sleep(500); // optional small pause
            } catch (StaleElementReferenceException e) {
                System.out.println("⚠ Warning: Stale element reference for link '" + linkName + "'. Skipping...");
            }
        }

     // Inside your test method, after all sidebar link checks
        if (sidebarLinks.containsKey("Logout")) {
            // Use a robust logout locator
            By logoutXPath = By.xpath("//h4[normalize-space()='Logout']");
            
            WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(logoutXPath));
            logoutLink.click();

            // Wait until login page header appears
            By loginPageHeaderLocator = By.xpath("//h1[contains(text(),'Welcome to MeReg')] | //span[contains(text(),'Welcome to MeReg')]");
            WebElement loginPageHeader = new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.presenceOfElementLocated(loginPageHeaderLocator));
            loginPageHeader = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOf(loginPageHeader));
    }
}
}
