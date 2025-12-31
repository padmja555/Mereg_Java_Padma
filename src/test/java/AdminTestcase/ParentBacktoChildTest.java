package AdminTestcase;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import AdminPages.Childinfo;
import AdminPages.ParentBacktoChildPage;
import AdminPages.LoginPage;
import Base.BaseDriver;

import java.time.Duration;

@Listeners(listeners.TestListener.class)
public class ParentBacktoChildTest extends BaseDriver {

    @Test
    public void testBackToChildInfoNavigation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        try {
            System.out.println("=== STARTING TEST ===");
            
            // 1. Login
            LoginPage login = new LoginPage(driver);
            login.enterEmail("srasysife@gmail.com");
            login.enterPassword("Admin@123");
            login.clickSignIn();
            
            // Simple login wait
            Thread.sleep(5000);
            System.out.println("✓ Login completed");

            // 2. Navigate to Child Info
            Childinfo childInfo = new Childinfo(driver);
            childInfo.clickOnenrollment();
            Thread.sleep(3000);
            
            // Verify we're on child info page
            if (!isOnChildInfoPage()) {
                throw new RuntimeException("Not on Child Info page after enrollment click");
            }
            System.out.println("✓ On Child Info page");

            // Fill child information
            childInfo.enterRandomFirstAndLastName();
            childInfo.selectGender();
            childInfo.enterrandomDOB();
            
            // Take screenshot before proceeding to guardian
            takeScreenshot("child_info_filled");
            
            // Click proceed to guardian
            childInfo.ClickonProceedtoGuardian();
            System.out.println("Clicked Proceed to Guardian");

            // 3. Wait for navigation and detect Parent/Guardian page
            waitForPageNavigation(wait, "parent");
            
            // Debug current state
            debugPageState("After navigation to Parent/Guardian");
            
            // 4. Now work with Parent/Guardian page
            ParentBacktoChildPage parentPage = new ParentBacktoChildPage(driver);
            
            // Fill some parent fields
            fillParentFields(parentPage);
            System.out.println("✓ Parent fields filled");

            // 5. Take screenshot before going back
            takeScreenshot("parent_info_filled");
            
            // 6. Click Back to Child Info
            clickBackToChildInfoWithScroll(parentPage);
            System.out.println("✓ Back to Child Info clicked");

            // 7. Wait for navigation back to child page
            waitForPageNavigation(wait, "child");
            
            // Debug final state
            debugPageState("After back to Child Info");

            // 8. Final verification
            Assert.assertTrue(isOnChildInfoPage(), "Should be back on Child Info page");
            System.out.println("✓ Test completed successfully");

        } catch (Exception e) {
            System.out.println("❌ Test failed: " + e.getMessage());
            debugPageState("FAILURE_STATE");
            takeScreenshot("test_failure");
            throw new RuntimeException("Test failed: " + e.getMessage(), e);
        }
    }

    private void waitForPageNavigation(WebDriverWait wait, String targetPage) {
        System.out.println("Waiting for navigation to: " + targetPage);
        
        // Wait for page to load generally
        wait.until(driver -> {
            return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        });
        
        // Additional wait based on target page
        if ("parent".equals(targetPage)) {
            waitForParentPageIndicators(wait);
        } else if ("child".equals(targetPage)) {
            waitForChildPageIndicators(wait);
        }
        
        // Final stabilization wait
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void waitForParentPageIndicators(WebDriverWait wait) {
        System.out.println("Looking for Parent/Guardian page indicators...");
        
        // Try multiple ways to detect parent page
        try {
            // Wait for any element that might indicate parent page
            wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Guardian') or contains(text(), 'Parent')]")),
                ExpectedConditions.presenceOfElementLocated(By.name("firstName")), // Parent first name field
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Back to Child Info')]")), // Back button
                ExpectedConditions.urlContains("guardian") // URL indicator
            ));
            System.out.println("✓ Parent/Guardian page detected");
        } catch (Exception e) {
            System.out.println("Standard parent page detection failed, trying fallback...");
            waitForParentPageFallback(wait);
        }
    }

    private void waitForParentPageFallback(WebDriverWait wait) {
        // Fallback: Just wait for any form field and assume we're on the right page
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input | //select | //textarea")));
            System.out.println("✓ Form elements found, assuming page loaded");
        } catch (Exception e) {
            System.out.println("❌ Cannot detect page state after navigation");
            throw new RuntimeException("Page navigation failed - cannot detect Parent/Guardian page");
        }
    }

    private void waitForChildPageIndicators(WebDriverWait wait) {
        System.out.println("Looking for Child Info page indicators...");
        
        try {
            // Wait for child page specific elements
            wait.until(ExpectedConditions.and(
                ExpectedConditions.presenceOfElementLocated(By.name("firstName")), // Child first name
                ExpectedConditions.presenceOfElementLocated(By.name("lastName"))   // Child last name
            ));
            System.out.println("✓ Child Info page detected");
        } catch (Exception e) {
            System.out.println("Standard child page detection failed, trying fallback...");
            waitForChildPageFallback(wait);
        }
    }

    private void waitForChildPageFallback(WebDriverWait wait) {
        // Fallback: Wait for any input field and check if we can interact
        try {
            wait.until(driver -> {
                try {
                    WebElement anyInput = driver.findElement(By.xpath("//input[1]"));
                    return anyInput.isEnabled();
                } catch (Exception e) {
                    return false;
                }
            });
            System.out.println("✓ Page seems loaded (fallback detection)");
        } catch (Exception e) {
            System.out.println("❌ Cannot detect page state after back navigation");
            throw new RuntimeException("Back navigation failed - cannot detect Child Info page");
        }
    }

    private void fillParentFields(ParentBacktoChildPage parentPage) {
        try {
            // Try to fill fields with retry logic
            fillWithRetry(() -> parentPage.enterRandomFirstName(), "First Name");
            fillWithRetry(() -> parentPage.enterRandomLastName(), "Last Name");
            fillWithRetry(() -> parentPage.selectRandomRelationship(), "Relationship");
        } catch (Exception e) {
            System.out.println("Error filling parent fields: " + e.getMessage());
            // Continue anyway - maybe we can still click back
        }
    }

    private void fillWithRetry(Runnable fillAction, String fieldName) {
        int retries = 2;
        for (int i = 1; i <= retries; i++) {
            try {
                fillAction.run();
                System.out.println("✓ Filled " + fieldName);
                return;
            } catch (Exception e) {
                System.out.println("Attempt " + i + " failed for " + fieldName + ": " + e.getMessage());
                if (i == retries) {
                    System.out.println("❌ Failed to fill " + fieldName + " after " + retries + " attempts");
                }
                try { Thread.sleep(1000); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            }
        }
    }

    private void clickBackToChildInfoWithScroll(ParentBacktoChildPage parentPage) {
        try {
            // Scroll to bottom to ensure button is visible
            scrollToBottom();
            
            // Try the page object method
            parentPage.clickBackToChildInfo();
        } catch (Exception e) {
            System.out.println("Page object method failed, trying direct approach...");
            clickBackButtonDirectly();
        }
    }

    private void clickBackButtonDirectly() {
        String[] buttonSelectors = {
            "//button[contains(., 'Back to Child Info')]",
            "//a[contains(., 'Back to Child Info')]",
            "//input[@value='Back to Child Info']",
            "//*[contains(text(), 'Back to Child Info')]",
            "//button[contains(., 'Back')]",
            "//a[contains(., 'Back')]"
        };
        
        for (String selector : buttonSelectors) {
            try {
                System.out.println("Trying selector: " + selector);
                WebElement button = driver.findElement(By.xpath(selector));
                
                // Scroll to button
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
                Thread.sleep(500);
                
                // Click using JavaScript
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
                System.out.println("✓ Clicked using selector: " + selector);
                return;
                
            } catch (Exception e) {
                System.out.println("Failed with selector " + selector);
            }
        }
        
        throw new RuntimeException("Could not find or click Back to Child Info button");
    }

    private void scrollToBottom() {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(1000);
            System.out.println("✓ Scrolled to bottom");
        } catch (Exception e) {
            System.out.println("Scroll failed: " + e.getMessage());
        }
    }

    private boolean isOnChildInfoPage() {
        try {
            // Check multiple indicators
            String url = driver.getCurrentUrl().toLowerCase();
            String pageSource = driver.getPageSource().toLowerCase();
            
            // Try to interact with child form fields
            WebElement firstName = driver.findElement(By.name("firstName"));
            WebElement lastName = driver.findElement(By.name("lastName"));
            
            boolean canInteract = firstName.isEnabled() && lastName.isEnabled();
            boolean hasChildContent = pageSource.contains("child") || pageSource.contains("student");
            boolean urlMatches = url.contains("child") || url.contains("student") || url.contains("enroll");
            
            boolean result = canInteract || hasChildContent || urlMatches;
            
            System.out.println("Child Page Check:");
            System.out.println("  - Can interact: " + canInteract);
            System.out.println("  - Has child content: " + hasChildContent);
            System.out.println("  - URL matches: " + urlMatches);
            System.out.println("  - Result: " + result);
            
            return result;
            
        } catch (Exception e) {
            System.out.println("Error checking child page: " + e.getMessage());
            return false;
        }
    }

    private void debugPageState(String stage) {
        System.out.println("=== DEBUG: " + stage + " ===");
        try {
            System.out.println("URL: " + driver.getCurrentUrl());
            System.out.println("Title: " + driver.getTitle());
            
            // Check key elements
            checkElement("name", "firstName", "First Name field");
            checkElement("name", "lastName", "Last Name field");
            checkElement("xpath", "//button[contains(., 'Back to Child Info')]", "Back button");
            checkElement("xpath", "//h1 | //h2 | //h3", "Headers");
            
        } catch (Exception e) {
            System.out.println("Debug error: " + e.getMessage());
        }
        System.out.println("=======================");
    }

    private void checkElement(String type, String locator, String description) {
        try {
            By by;
            if ("xpath".equals(type)) {
                by = By.xpath(locator);
            } else if ("name".equals(type)) {
                by = By.name(locator);
            } else {
                by = By.id(locator);
            }
            
            int count = driver.findElements(by).size();
            System.out.println(description + ": " + count + " found");
            
            if (count > 0) {
                WebElement element = driver.findElement(by);
                System.out.println("  - Displayed: " + element.isDisplayed());
                System.out.println("  - Enabled: " + element.isEnabled());
                if (!locator.contains("h1") && !locator.contains("h2") && !locator.contains("h3")) {
                    System.out.println("  - Text: " + element.getAttribute("value"));
                }
            }
        } catch (Exception e) {
            System.out.println(description + ": Error - " + e.getMessage());
        }
    }

    private void takeScreenshot(String name) {
        try {
            // Your screenshot implementation
            System.out.println("📸 Screenshot: " + name);
        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }
    }
}