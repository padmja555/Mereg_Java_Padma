
package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class SecondChildnotworking {

    private WebDriver driver;
    private WebDriverWait wait;

    public SecondChildnotworking(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ******** Dynamic Locators ********
    private By enrollmentLink = By.xpath("//span[contains(text(),'Enrollment')]");
    private By addChildButton = By.xpath("//button[contains(text(),'Add Child')]");

    private By firstNameField = By.xpath("//input[@formcontrolname='firstName']");
    private By lastNameField = By.xpath("//input[@formcontrolname='lastName']");
    private By dobField = By.xpath("//input[@formcontrolname='dateOfBirth']");

    private By genderDropdown = By.xpath("//mat-select[@formcontrolname='gender']");
    private By genderMaleOption = By.xpath("//mat-option//span[normalize-space()='Male']");
    private By genderFemaleOption = By.xpath("//mat-option//span[normalize-space()='Female']");

    private By proceedToGuardianButton = By.xpath("//button[contains(text(),'Proceed to Guardian')]");

    // ******** Utility Methods ********
    private String generateRandomName(String prefix) {
        return prefix + new Random().nextInt(1000);
    }

    private String generateRandomDOB() {
        LocalDate randomDate = LocalDate.now().minusYears(3).minusMonths(new Random().nextInt(12));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return randomDate.format(formatter);
    }

    // ******** Actions ********
    public void clickEnrollmentLink() {
        wait.until(ExpectedConditions.elementToBeClickable(enrollmentLink)).click();
        System.out.println("Clicked on Enrollment link");
    }

    public void clickAddChildButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addChildButton)).click();
        System.out.println("Clicked on Add Child button");
    }

    public void enterRandomFirstAndLastName() {
        String firstName = generateRandomName("ChildFN");
        String lastName = generateRandomName("ChildLN");

        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).clear();
        driver.findElement(firstNameField).sendKeys(firstName);

        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField)).clear();
        driver.findElement(lastNameField).sendKeys(lastName);

        System.out.println("Entered child name: " + firstName + " " + lastName);
    }

    public void selectGender(String gender) {
        wait.until(ExpectedConditions.elementToBeClickable(genderDropdown)).click();
        if (gender.equalsIgnoreCase("Male")) {
            wait.until(ExpectedConditions.elementToBeClickable(genderMaleOption)).click();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(genderFemaleOption)).click();
        }
        System.out.println("Selected gender: " + gender);
    }

    public void enterDOB() {
        WebElement dobInput = wait.until(ExpectedConditions.visibilityOfElementLocated(dobField));
        dobInput.sendKeys(Keys.CONTROL + "a", Keys.DELETE); // Clear field dynamically
        String randomDOB = generateRandomDOB();
        dobInput.sendKeys(randomDOB);
        System.out.println("Entered DOB: " + randomDOB);
    }

    public void clickProceedToGuardian() {
        wait.until(ExpectedConditions.elementToBeClickable(proceedToGuardianButton)).click();
        System.out.println("Proceeded to Guardian page");
    }

    // ******** Business Flow: Add Two Children ********
    public void addTwoChildren() {
        System.out.println("Adding first child...");
        clickAddChildButton();
        enterRandomFirstAndLastName();
        selectGender("Male");
        enterDOB();

        System.out.println("Adding second child...");
        clickAddChildButton();
        enterRandomFirstAndLastName();
        selectGender("Female");
        enterDOB();
    }
}
