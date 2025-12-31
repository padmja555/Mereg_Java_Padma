package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;

public class AllStaffComposeMessagePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private Random random = new Random();

    public AllStaffComposeMessagePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        this.actions = new Actions(driver);
    }

    /* ================= LOCATORS ================= */

    private By newMessageBtn = By.xpath("//button[contains(.,'New Message')]");
    private By individualTab = By.xpath("//span[normalize-space()='Individual']");

    private By categoryDropdown = By.xpath("//label[contains(text(),'Select Category')]/following::mat-select[1]");
    private By staffCategory = By.xpath("//mat-option//span[normalize-space()='Staff']");

    private By recipientsDropdown = By.xpath("//label[contains(text(),'Recipients')]/following::mat-select[1]");
    private By staffRecipients = By.xpath("//mat-option//span[contains(text(),'Staff')]");

    private By messageTypeDropdown = By.xpath("//label[contains(text(),'Message Type')]/following::mat-select[1]");
    private By reminderOption = By.xpath("//mat-option//span[normalize-space()='Reminder']");

    private By subjectField = By.xpath("//input[@formcontrolname='subject']");
    private By editor = By.xpath("//div[@contenteditable='true']");
    private By boldBtn =
            By.xpath("//button[.//mat-icon[normalize-space()='format_bold']]");

    private By italicBtn =
            By.xpath("//button[.//mat-icon[normalize-space()='format_italic']]");

    private By underlineBtn =
            By.xpath("//button[.//mat-icon[normalize-space()='format_underlined']]");



   // private By boldBtn = By.xpath("//button[@aria-label='Bold']");
    //private By italicBtn = By.xpath("//button[@aria-label='Italic']");
    //private By underlineBtn = By.xpath("//button[@aria-label='Underline']");
    private By bulletBtn = By.xpath("//button[@aria-label='Bulleted list']");
    private By numberBtn = By.xpath("//button[@aria-label='Numbered list']");

    private By sendIndividually = By.xpath("//input[@type='checkbox']/following-sibling::span");
    private By sendBtn = By.xpath("//span[normalize-space()='Send Message']");
    private By overlay = By.cssSelector(".cdk-overlay-backdrop");
    private By messageBody =
            By.xpath("//div[@contenteditable='true']");

    /* ================= COMMON ================= */

    private void jsClick(WebElement e) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
    }

    private void waitOverlayGone() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
    }
    private void waitForOverlayToClose() {
		// TODO Auto-generated method stub
		
	}

    private WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /* ================= ACTIONS ================= */

    public void openCompose() {
        jsClick(waitClickable(newMessageBtn));
    }

    public void selectIndividual() {
        jsClick(waitClickable(individualTab));
    }

    public void selectStaffCategory() {
        jsClick(waitClickable(categoryDropdown));
        jsClick(waitClickable(staffCategory));
        actions.sendKeys(Keys.ESCAPE).perform();
    }

    public void selectMultipleStaff(int count) {
        jsClick(waitClickable(recipientsDropdown));
        waitOverlayGone();

        List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//mat-option//mat-pseudo-checkbox")));
        if (options.isEmpty()) throw new RuntimeException("No staff recipients loaded");

        Collections.shuffle(options);
        for (int i = 0; i < Math.min(count, options.size()); i++) {
            jsClick(options.get(i));
            waitOverlayGone();
        }

        actions.sendKeys(Keys.ESCAPE).perform();
    }

    public void selectReminder() {
        jsClick(waitClickable(messageTypeDropdown));
        jsClick(waitClickable(reminderOption));
        waitOverlayGone();
    }

    public void enterRandomSubject() {
        WebElement subject = waitClickable(subjectField);
        subject.clear();
        subject.sendKeys("Automation Subject " + System.currentTimeMillis());
    }

    /**
     * Enter message with random formatting. Ensures buttons are visible and clickable.
     */
   // private void waitForOverlayToClose() {
		// TODO Auto-generated method stub
		
	//}

	public void enterRandomFormattedMessage() {

        // 🔥 Ensure no overlay is present
        waitForOverlayToClose();

        WebElement editor = wait.until(
                ExpectedConditions.visibilityOfElementLocated(messageBody));

        // Use JS click to bypass interception
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].focus();", editor);

        editor.sendKeys("This is an automated message from Selenium test. ");

        Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();

        jsClick(wait.until(ExpectedConditions.presenceOfElementLocated(italicBtn)));
        editor.sendKeys("ITALIC TEXT ");
        jsClick(wait.until(ExpectedConditions.presenceOfElementLocated(italicBtn)));

        editor.sendKeys(" Thank you!");
    }


    public void enableSendIndividually() {
        WebElement checkbox = waitClickable(sendIndividually);
        if (!checkbox.isSelected()) jsClick(checkbox);
    }

    public void sendMessage() {
        WebElement send = waitClickable(sendBtn);
        jsClick(send);
    }
}
