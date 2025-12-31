package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class StudentListPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public StudentListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Locator for the table container (could be table tag or some div wrapper)
    private By studentsTableLocator = By.xpath("//table[.//button[contains(., 'View Details')]]");

    // Fallback locator – maybe buttons first
    //private By viewDetailsButtons = By.xpath("//button[normalize-space()='View Details']");
    private By viewDetailsButtons = By.xpath("//button[contains(., 'View Details')]");

////button[contains(., 'View Details')]
    public boolean isStudentListDisplayed() {
        try {
            // Wait for either the table with buttons or at least one button
            System.out.println("Waiting for student table to be visible...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(studentsTableLocator));

            System.out.println("Table located. Now checking for 'View Details' buttons...");
            List<WebElement> buttons = driver.findElements(viewDetailsButtons);
            System.out.println("Number of 'View Details' buttons found: " + buttons.size());

            return !buttons.isEmpty();
        } catch (Exception e) {
            System.err.println("Student list not displayed: " + e.getMessage());
            return false;
        }
    }

    public void clickRandomViewDetails() {
        List<WebElement> buttons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(viewDetailsButtons));
        if (buttons.isEmpty()) {
            throw new RuntimeException("No 'View Details' buttons found.");
        }
        Random rand = new Random();
        WebElement btn = buttons.get(rand.nextInt(buttons.size()));
        wait.until(ExpectedConditions.elementToBeClickable(btn)).click();
    }
    //By viewDetailsButtons = By.xpath("//button[contains(., 'View Details')]");
    //wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(viewDetailsButtons));
    public By getStudentListTableLocator() {
        return By.id("studentListTable"); // Or whatever locator identifies the student list table
    }
}
