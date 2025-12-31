package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.List;

public class TwoChildrenspage {
    WebDriver driver;
    public WebDriverWait wait;

    // ------------------ Locators ------------------ //
    By enrollLink = By.xpath("//h4[normalize-space()='Enrollment']");
    By addChildIcon = By.xpath("(//input[@formcontrolname='firstName'])[2]"); // "+" icon
    By guardiansInfo = By.xpath("//button//span[contains(text(),'Proceed')]");

    // ------------------ Constructor ------------------ //
    public TwoChildrenspage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ------------------ Actions ------------------ //

    public void clickOnenrollment() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(enrollLink));
        element.click();
        System.out.println("Clicked on Enrollment");
    }

    /**
     * Add another child form by clicking the + icon
     */
    public void addAnotherChild() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // Try multiple possible selectors for the add child button
            By[] addButtonSelectors = {
                By.xpath("//mat-icon[contains(@mattooltip, 'Add another child')]"),
                By.xpath("//mat-icon[contains(text(), 'add')]"),
                By.xpath("//button[contains(@class, 'add-child')]"),
                By.xpath("//*[contains(@class, 'add-button')]"),
                By.xpath("//button//mat-icon[contains(text(), 'add_circle')]")
            };
            
            WebElement addChildIcon = null;
            for (By selector : addButtonSelectors) {
                try {
                    addChildIcon = wait.until(ExpectedConditions.elementToBeClickable(selector));
                    break;
                } catch (Exception e) {
                    // Continue to next selector
                }
            }
            
            if (addChildIcon == null) {
                throw new RuntimeException("Could not find the 'Add another child' button with any selector");
            }

            // Scroll into view and click using JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addChildIcon);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addChildIcon);

            System.out.println("Clicked on + icon (Add another child)");

            // Wait for the SECOND child's form to appear - try multiple indicators
            try {
                // Option 1: Wait for the second first name field
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("(//input[@formcontrolname='firstName'])[2]")
                ));
            } catch (TimeoutException e) {
                // Option 2: Wait for any indicator that a second form exists
                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                    By.xpath("//input[@formcontrolname='firstName']"), 1
                ));
            }

            System.out.println("Second child section is now visible");

        } catch (TimeoutException e) {
            // Take screenshot for debugging
            //takeFailureScreenshot("AddAnotherChildTimeout");
            throw new RuntimeException("Timeout: + icon clicked but second child form did not appear within 15 seconds", e);
        }
    }

    /**
     * Enter child information for a given index (0 for first child, 1 for second child, etc.)
     */
    public void enterRandomChildDetails(int index) {
        try {
            String firstNameXpath = "(//input[@formcontrolname='firstName'])[" + (index + 1) + "]";
            String lastNameXpath = "(//input[@formcontrolname='lastName'])[" + (index + 1) + "]";
            String dobXpath = "(//input[@formcontrolname='dob'])[" + (index + 1) + "]";
            String genderMaleXpath = "(//span[normalize-space()='Male'])[" + (index + 1) + "]";
            String genderFemaleXpath = "(//span[normalize-space()='Female'])[" + (index + 1) + "]";

            // Generate random data
            String fn = getRandomName();
            String ln = getRandomLastName();
            String dob = getRandomDOB();

            // First Name
            WebElement firstNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(firstNameXpath)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", firstNameField);
            firstNameField.sendKeys(fn);

            // Last Name
            WebElement lastNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(lastNameXpath)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", lastNameField);
            lastNameField.sendKeys(ln);

            // Gender
            String[] genders = {"Male", "Female"};
            String selectedGender = genders[new Random().nextInt(genders.length)];
            if (selectedGender.equals("Male")) {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(genderMaleXpath))).click();
            } else {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(genderFemaleXpath))).click();
            }

            // DOB
            WebElement dobField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dobXpath)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].value='';", dobField);
            dobField.sendKeys(dob);

            System.out.println("Child " + (index + 1) + " Details -> Name: " + fn + " " + ln + ", Gender: " + selectedGender + ", DOB: " + dob);

        } catch (Exception e) {
            System.out.println("Error entering details for child index " + index + ": " + e.getMessage());
            throw e;
        }
    }

    /**
     * Proceed to Guardian Info page
     */
    public void ClickonProceedtoGuardian() {
        try {
            WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(guardiansInfo));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", proceedButton);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", proceedButton);
            System.out.println("Clicked Proceed to Guardian button");
        } catch (Exception e) {
            throw new RuntimeException("Failed to click proceed button", e);
        }
    }

    // ------------------ Random Data Generators ------------------ //

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

    // ------------------ Optional: Method to check if on next page ------------------ //
    public boolean isOnParentGuardianPage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(.,'Parent') or contains(.,'Guardian')]"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

