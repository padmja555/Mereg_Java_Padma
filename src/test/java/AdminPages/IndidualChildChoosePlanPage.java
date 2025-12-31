package AdminPages;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IndidualChildChoosePlanPage {

	WebDriver driver;

	By chooseplanHeaderText = By.xpath("//h1[text()='Choose a Plan']");
	By selectaplan = By.xpath("//mat-radio-group//input[@type='radio']");
	By completeregistration = By.xpath("//span[text()='Complete registration']");
    //By planToggles = By.xpath("//mat-button-toggle-group[@name='paymentPlan']//mat-button-toggle");
    public By planToggles = By.xpath("//mat-button-toggle-group[@name='paymentPlan']//button");

 ////mat-button-toggle-group[@name='paymentPlan']//button
   ////mat-button-toggle-group[@name='paymentPlan']//mat-button-toggle
    
	public IndidualChildChoosePlanPage(WebDriver driver) {
		this.driver = driver;
	}

	public String VerifyChoosePlanPage() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(chooseplanHeaderText));
		return driver.findElement(chooseplanHeaderText).getText();
	}

	public void SelectAPlan() throws InterruptedException {
		// Get all radio button containers (not the hidden input or span)
		List<WebElement> radioButtons = driver.findElements(selectaplan);

		System.out.println("Total radio options: " + radioButtons.size());

		if (radioButtons.size() > 1) {
			// pick random option excluding the first (default selected)
			int randomIndex = 1 + new Random().nextInt(radioButtons.size() - 1);
			WebElement randomOption = radioButtons.get(randomIndex);

			// scroll into view
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", randomOption);

			// click via JS to bypass Angular hidden layers
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", randomOption);

			// extract text label inside that option
			String selectedText = randomOption.getText().trim();
			System.out.println("✅ Randomly selected option (excluding default): " + selectedText);
		} else {
			System.out.println("Only default option exists.");
		}
	}
	

	public void ProceedToCompleteRegestration() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(completeregistration)).click();
	}
	public void selectRandomMonthPlan() {
	    // Wait until the toggle buttons are visible
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOfElementLocated(planToggles));

	    // Find all month plan toggle buttons
	    List<WebElement> toggles = driver.findElements(planToggles);

	    System.out.println("Total month plan options: " + toggles.size());

	    if (toggles.isEmpty()) {
	        throw new RuntimeException("No month plan toggles found.");
	    }

	    // Pick a random toggle
	    int idx = new Random().nextInt(toggles.size());
	    WebElement chosen = toggles.get(idx);

	    // Scroll into view and click using JavaScript (helps with Angular overlays)
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", chosen);
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", chosen);

	    String selectedText = chosen.getText().trim();
	    System.out.println("✅ Selected Month Plan: " + selectedText);
	    
	}

	public boolean isPlanPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}


	
}
