/*
package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class ChooseplanPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Random random;

    private final By planCards = By.xpath("//div[contains(@class,'card-body')]");
    private final By proceedButton = By.xpath("//app-payment/div[3]/button");

    public ChooseplanPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.random = new Random();
    }
    /*

    public void selectRandomPlan() {
        System.out.println("Attempting to select a random plan...");
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(planCards));
            List<WebElement> plans = driver.findElements(planCards);
            if (!plans.isEmpty()) {
                WebElement randomPlan = plans.get(random.nextInt(plans.size()));
                randomPlan.click();
                System.out.println("Random plan selected successfully.");
            } else {
                throw new RuntimeException("No plan cards found to select.");
            }
        } catch (Exception e) {
            System.err.println("Failed to select a plan: " + e.getMessage());
            throw new RuntimeException("Failed to select a plan.", e);
        }
    }

    public void proceedWithSelectedPlan() {
        System.out.println("Clicking 'Proceed' with the selected plan...");
        try {
            // Wait for the button to become clickable after a plan is selected
            WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(proceedButton));
            proceedBtn.click();
            System.out.println("Clicked on Proceed button to continue to registration.");
        } catch (Exception e) {
            System.err.println("Failed to proceed with selected plan: " + e.getMessage());
            throw new RuntimeException("Failed to proceed with selected plan: " + e.getMessage(), e);
        }
    }
*/

package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class ChooseplanPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Random random;

    private final By planCards = By.xpath("//div[contains(@class,'card-body')]");
    private final By proceedButton = By.xpath("//app-payment/div[3]/button");

    public ChooseplanPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.random = new Random();
    }
    /*

    public void selectRandomPlan() {
        System.out.println("Attempting to select a random plan...");
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(planCards));
            List<WebElement> plans = driver.findElements(planCards);
            if (!plans.isEmpty()) {
                WebElement randomPlan = plans.get(random.nextInt(plans.size()));
                randomPlan.click();
                System.out.println("Random plan selected successfully.");
            } else {
                throw new RuntimeException("No plan cards found to select.");
            }
        } catch (Exception e) {
            System.err.println("Failed to select a plan: " + e.getMessage());
            throw new RuntimeException("Failed to select a plan.", e);
        }
    }
*/
    public void proceedWithSelectedPlan() {
        System.out.println("Clicking 'Proceed' with the selected plan...");
        try {
            // Wait for the button to become clickable after a plan is selected
            WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(proceedButton));
            proceedBtn.click();
            System.out.println("Clicked on Proceed button to continue to registration.");
        } catch (Exception e) {
            System.err.println("Failed to proceed with selected plan: " + e.getMessage());
            throw new RuntimeException("Failed to proceed with selected plan: " + e.getMessage(), e);
        }
    }
}