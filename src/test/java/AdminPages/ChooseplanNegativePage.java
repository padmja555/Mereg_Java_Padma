package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;
import java.util.Random;

public class ChooseplanNegativePage {
    WebDriver driver;
    private Random random = new Random();

    // Locators
    public By choosePlanHeader = By.xpath("//h2[contains(text(), 'Choose Your Plan') or contains(text(), 'Select Plan')]");
    public By planOptions = By.xpath("//div[contains(@class, 'plan-card')] | //mat-card[contains(@class, 'plan')]");
    public By selectPlanButton = By.xpath("//button[contains(text(), 'Select') or contains(text(), 'Choose')]");
    public By proceedButton = By.xpath("//button[contains(text(), 'Proceed') or contains(text(), 'Continue')]");

    public ChooseplanNegativePage(WebDriver driver) {
        this.driver = driver;
    }

    public String verifyChoosePlanPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(choosePlanHeader));
        return header.getText();
    }

    public void selectRandomPlan() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            List<WebElement> plans = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(planOptions));
            
            if (plans.isEmpty()) {
                throw new RuntimeException("No plan options found");
            }
            
            // Select a random plan
            int randomIndex = random.nextInt(plans.size());
            WebElement selectedPlan = plans.get(randomIndex);
            selectedPlan.click();
            
            System.out.println("Selected plan: " + selectedPlan.getText());
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to select a plan: " + e.getMessage());
        }
    }

    public void proceedWithSelectedPlan() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // First try to click select button if it exists
            List<WebElement> selectButtons = driver.findElements(selectPlanButton);
            if (!selectButtons.isEmpty()) {
                selectButtons.get(0).click();
                Thread.sleep(1000);
            }
            
            // Then click proceed button
            WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(proceedButton));
            proceedBtn.click();
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to proceed with selected plan: " + e.getMessage());
        }
    }
}