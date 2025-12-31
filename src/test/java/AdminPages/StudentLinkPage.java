package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;
import java.util.Random;

public class StudentLinkPage {

    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By adminLink = By.linkText("Admin");
    By unlinkedStudentsTitle = By.xpath("//h2[normalize-space()='Unlinked Students']");
    By studentRows = By.cssSelector("table tbody tr");
    By searchField = By.xpath("//div[contains(@class,'link-students-container')]//input[@matinput]");
    By linkButtons = By.xpath("(//button[@class='mat-focus-indicator button-proceed mat-raised-button mat-button-base'])[3]");
    By emailField = By.xpath("//input[@name='email' and @type='email']");
    By confirmLinkButton = By.xpath("//button[@class='mat-focus-indicator mat-raised-button mat-button-base mat-primary']");
    By successToast = By.cssSelector(".toast-success");
    By errorMessage = By.xpath("//*[contains(@class,'error') or contains(@class,'mat-error')]");

    public StudentLinkPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void clickAdminLink() {
        driver.findElement(adminLink).click();
    }

    public boolean isUnlinkedTitleDisplayed() {
        return driver.findElement(unlinkedStudentsTitle).isDisplayed();
    }

    public int getTotalStudents() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(studentRows));
        List<WebElement> rows = driver.findElements(studentRows);
        System.out.println("Students on first page: " + rows.size());
        return rows.size();
    }

    public String getRandomFirstName() {
        List<WebElement> rows = driver.findElements(studentRows);
        if (rows.isEmpty()) return "";

        Random rand = new Random();
        int index = rand.nextInt(rows.size());

        WebElement firstNameCell = rows.get(index).findElements(By.tagName("td")).get(0);
        return firstNameCell.getText().trim();
    }

    public void searchStudentByFirstName(String firstName) {
        WebElement search = driver.findElement(searchField);
        search.clear();
        search.sendKeys(firstName);
    }

    public void waitForSearchResultsToLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(studentRows));
    }

    public void clickFirstLinkButton() {
        List<WebElement> buttons = driver.findElements(linkButtons);
        if (!buttons.isEmpty()) {
            buttons.get(0).click();
        }
    }

    public void enterEmail(String email) {
        try {
            System.out.println("Attempting to enter email: " + email);
            
            // Wait for email field to be visible and enabled
            WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(emailField));
            
            // Clear the field first
            emailInput.clear();
            
            // Enter email
            emailInput.sendKeys(email);
            
            System.out.println("Successfully entered email: " + email);
            
            // Verify the email was entered correctly
            String enteredValue = emailInput.getAttribute("value");
            System.out.println("Verified entered email: " + enteredValue);
            
        } catch (Exception e) {
            System.out.println("Error entering email: " + e.getMessage());
            throw new RuntimeException("Failed to enter email", e);
        }
    }

    public void confirmLink() {
        try {
            WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(confirmLinkButton));
            confirmBtn.click();
            System.out.println("Clicked confirm link button");
        } catch (Exception e) {
            System.out.println("Error clicking confirm button: " + e.getMessage());
            throw new RuntimeException("Failed to confirm link", e);
        }
    }

    public boolean isLinkSuccessDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successToast));
            System.out.println("Success toast displayed");
            return true;
        } catch (TimeoutException e) {
            System.out.println("Success toast not displayed within timeout");
            return false;
        }
    }

    public boolean isErrorMessageDisplayed() {
        try {
            List<WebElement> errors = driver.findElements(errorMessage);
            if (!errors.isEmpty()) {
                System.out.println("Error message found: " + errors.get(0).getText());
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailFieldError() {
        try {
            // Check for various error message locations
            By[] errorLocators = {
                By.xpath("//mat-error[contains(.,'email')]"),
                By.xpath("//div[contains(@class,'error') and contains(.,'email')]"),
                By.xpath("//*[contains(text(),'valid email')]"),
                By.xpath("//*[contains(text(),'required')]")
            };
            
            for (By locator : errorLocators) {
                List<WebElement> errors = driver.findElements(locator);
                if (!errors.isEmpty() && errors.get(0).isDisplayed()) {
                    return errors.get(0).getText();
                }
            }
            return "No error message found";
        } catch (Exception e) {
            return "Error checking for messages: " + e.getMessage();
        }
    }

    public String getRandomEmail() {
        String[] emails = {
            "padmavathi555divyakolu@gmail.com",
            "pamidi2021@yahoo.com",
            "teststudent" + System.currentTimeMillis() + "@gmail.com",
            "valid.email@test.com"
        };
        Random rand = new Random();
        String selectedEmail = emails[rand.nextInt(emails.length)];
        System.out.println("Selected random email: " + selectedEmail);
        return selectedEmail;
    }

    // Method to test invalid emails
    public String getRandomInvalidEmail() {
        String[] invalidEmails = {
            "invalid-email",
            "missing@domain",
            "@nodomain.com",
            "spaces in@email.com",
            ""
        };
        Random rand = new Random();
        return invalidEmails[rand.nextInt(invalidEmails.length)];
    }

    // Check if email field is present and enabled
    public boolean isEmailFieldAvailable() {
        try {
            List<WebElement> emailFields = driver.findElements(emailField);
            return !emailFields.isEmpty() && emailFields.get(0).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    // Get current value in email field
    public String getCurrentEmailValue() {
        try {
            WebElement emailInput = driver.findElement(emailField);
            return emailInput.getAttribute("value");
        } catch (Exception e) {
            return "Cannot read email field value";
        }
    }
}