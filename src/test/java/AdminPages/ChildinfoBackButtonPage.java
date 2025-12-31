package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.List;

public class ChildinfoBackButtonPage {
    WebDriver driver;
    WebDriverWait wait;

    // ------------------ Locators ------------------ //
    By enrollLink = By.xpath("//h4[normalize-space()='Enrollment']");
    By firstEle = By.xpath("//input[@formcontrolname='firstName']");
    By lastEle = By.xpath("//input[@formcontrolname='lastName']");
    By dobEle = By.xpath("//input[@formcontrolname='dob']");
    
    // Multiple possible selectors for the back button
    By backToHomeButton1 = By.xpath("//button[span[normalize-space()='Back to Home']]");
    By backToHomeButton2 = By.xpath("//button[contains(text(), 'Back to Home')]");
    By backToHomeButton3 = By.xpath("//button[contains(., 'Back to Home')]");
    By backToHomeButton4 = By.xpath("//span[contains(text(), 'Back to Home')]/ancestor::button");
    By backToHomeButton5 = By.xpath("//*[contains(text(), 'Back to Home') and self::button or parent::button]");
    By backToHomeButton6 = By.xpath("(//button[@class='mat-focus-indicator mat-stepper-previous btn btn-primary back-btn mat-button mat-button-base'])[1]");
    
    By guardiansInfo = By.xpath("//button//span[contains(text(),'Proceed')]");
    By childinfoTitleLocator = By.xpath("//h1[contains(@class,'page-heading') or contains(text(),'Child')]");
    By dashboardTitle = By.xpath("//h1[normalize-space()='Dashboard']");

    // ------------------ Constructor ------------------ //
    public ChildinfoBackButtonPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ------------------ Actions ------------------ //

    public void clickOnenrollment() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(enrollLink));
            element.click();
            System.out.println("Clicked on Enrollment");
        } catch (TimeoutException e) {
            System.out.println("Enrollment element not clickable in time.");
            throw e;
        }
    }

    public void clickBackToHome() {
        try {
            // Try multiple selectors for the back button
            WebElement backBtn = findElementWithMultipleSelectors(
                backToHomeButton1, 
                backToHomeButton2, 
                backToHomeButton3, 
                backToHomeButton4,
                backToHomeButton5,
                backToHomeButton6
            );
            
            if (backBtn == null) {
                throw new RuntimeException("Could not find 'Back to Home' button with any selector");
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", backBtn);
            Thread.sleep(500); // Small delay for scroll to complete
            
            // Try JavaScript click if regular click fails
            try {
                backBtn.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backBtn);
            }
            
            System.out.println("Clicked on 'Back to Home' button.");
            
        } catch (Exception e) {
            System.out.println("Error clicking back button: " + e.getMessage());
            // Take screenshot for debugging
            takeScreenshot("back_button_error");
            throw new RuntimeException("Failed to click back button", e);
        }
    }

    /**
     * Helper method to try multiple selectors for an element
     */
    private WebElement findElementWithMultipleSelectors(By... selectors) {
        for (By selector : selectors) {
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(selector));
                if (element.isDisplayed()) {
                    System.out.println("Found element with selector: " + selector);
                    return element;
                }
            } catch (Exception e) {
                // Continue to next selector
                System.out.println("Selector not found: " + selector);
            }
        }
        return null;
    }

    public boolean isOnDashboard() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardTitle)).isDisplayed();
        } catch (TimeoutException e) {
            // Also check for other indicators of dashboard
            try {
                return driver.getCurrentUrl().contains("dashboard") || 
                       driver.getTitle().toLowerCase().contains("dashboard");
            } catch (Exception ex) {
                return false;
            }
        }
    }

    // Screenshot utility for debugging
    private void takeScreenshot(String testName) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            // You can save this to a file if needed
            System.out.println("Screenshot taken for: " + testName);
        } catch (Exception e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }
    }

    // The other methods for entering child info...
    public void enterRandomFirstAndLastName() {
        try {
            String fn = getRandomName(), ln = getRandomLastName();
            WebElement firstNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(firstEle));
            WebElement lastNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(lastEle));

            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", firstNameField);
            firstNameField.sendKeys(fn);

            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", lastNameField);
            lastNameField.sendKeys(ln);

            System.out.println("Random First Name: " + fn);
            System.out.println("Random Last Name: " + ln);
        } catch (Exception e) {
            System.out.println("Error entering name: " + e.getMessage());
            throw e;
        }
    }

    public void selectGender() {
        try {
            String[] genders = {"Male", "Female"};
            String selectedGender = genders[new Random().nextInt(genders.length)];
            By genderEle = By.xpath("//span[normalize-space()='" + selectedGender + "']");
            wait.until(ExpectedConditions.elementToBeClickable(genderEle)).click();
            System.out.println("Selected Gender: " + selectedGender);
        } catch (Exception e) {
            System.out.println("Error selecting gender: " + e.getMessage());
            throw e;
        }
    }

    public void enterrandomDOB() {
        try {
            String formattedDOB = getRandomDOB();
            WebElement dobField = wait.until(ExpectedConditions.visibilityOfElementLocated(dobEle));
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", dobField);
            dobField.sendKeys(formattedDOB);
            System.out.println("Random DOB entered: " + formattedDOB);
        } catch (Exception e) {
            System.out.println("Error entering DOB: " + e.getMessage());
            throw e;
        }
    }

    public String getchildinfopageTitleText() {
        try {
            WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(childinfoTitleLocator));
            return titleElement.getText();
        } catch (Exception e) {
            return "Title not found";
        }
    }

    public String getRandomName() {
        String[] names = {"Aarav", "Vivaan", "Reyansh", "Aarya", "Ishita", "Diya", "Kavya", "Anaya", "Saanvi", "Meera"};
        return names[new Random().nextInt(names.length)];
    }

    public String getRandomLastName() {
        String[] lastnames = {"Sharma", "Verma", "Reddy", "Gupta", "Patel", "Kapoor", "Nair", "Joshi", "Singh", "Mehta"};
        return lastnames[new Random().nextInt(lastnames.length)];
    }

    public String getRandomDOB() {
        Random random = new Random();
        int year = random.nextInt(2018 - 2010 + 1) + 2010;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1;
        LocalDate dob = LocalDate.of(year, month, day);
        return dob.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    /**
     * Debug method to print all buttons on the page (for troubleshooting)
     */
    public void debugPrintAllButtons() {
        try {
            List<WebElement> buttons = driver.findElements(By.tagName("button"));
            System.out.println("=== ALL BUTTONS ON PAGE ===");
            for (int i = 0; i < buttons.size(); i++) {
                WebElement button = buttons.get(i);
                System.out.println(i + ": " + button.getText() + " | " + button.getAttribute("class"));
            }
            System.out.println("===========================");
        } catch (Exception e) {
            System.out.println("Error printing buttons: " + e.getMessage());
        }
    }
}