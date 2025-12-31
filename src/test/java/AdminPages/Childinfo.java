package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.List;

public class Childinfo {
    WebDriver driver;
    WebDriverWait wait;

    // ------------------ Locators ------------------ //
    By enrollLink = By.xpath("//h4[normalize-space()='Enrollment']");
    By firstEle = By.xpath("//input[@formcontrolname='firstName']");
    By lastEle = By.xpath("//input[@formcontrolname='lastName']");
    By dobEle = By.xpath("//input[@formcontrolname='dob']");
    By guardiansInfo = By.xpath("//button//span[contains(text(),'Proceed')]");
    By childinfoTitleLocator = By.xpath("//h1[contains(@class,'page-heading') or contains(text(),'Child')]");

    // ------------------ Constructor ------------------ //
    public Childinfo(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ------------------ Actions ------------------ //

    public void clickOnenrollment() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(enrollLink));
            element.click();
        } catch (TimeoutException e) {
            System.out.println("Enrollment element not clickable in time.");
            throw e;
        }
    }

    public void enterRandomFirstAndLastName() {
        try {
            String fn = getRandomName(), ln = getRandomLastName();
            WebElement firstNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(firstEle));
            WebElement lastNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(lastEle));

            // Clear using JavaScript for reliability
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
            
            // Clear using JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", dobField);
            dobField.sendKeys(formattedDOB);
            System.out.println("Random DOB entered: " + formattedDOB);
        } catch (Exception e) {
            System.out.println("Error entering DOB: " + e.getMessage());
            throw e;
        }
    }

    public void ClickonProceedtoGuardian() {
        try {
            WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(guardiansInfo));
            
            // Scroll to button and click using JavaScript for reliability
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", proceedButton);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", proceedButton);
            
            System.out.println("Clicked Proceed to Guardian button");
        } catch (Exception e) {
            System.out.println("Error clicking proceed button: " + e.getMessage());
            throw new RuntimeException("Failed to click proceed button", e);
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
            // Wait for elements that indicate we're on the parent/guardian page
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(.,'Parent') or contains(.,'Guardian')]"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

	public By getFirstNameLocator() {
		// TODO Auto-generated method stub
		return null;
	}
}