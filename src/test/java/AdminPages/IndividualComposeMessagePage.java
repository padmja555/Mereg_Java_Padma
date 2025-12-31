package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;

public class IndividualComposeMessagePage {

    WebDriver driver;
    WebDriverWait wait;
    Random random = new Random();

    public IndividualComposeMessagePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    /* ================= LOCATORS ================= */

    private By composeBtn =
            By.xpath("//button[contains(.,'New Message')]");

    private By individualTab =
            By.xpath("//span[normalize-space()='Individual']");

    private By categoryDropdown =
            By.xpath("//label[contains(text(),'Select Category')]/following::mat-select[1]");

    private By parentCategoryOption =
            By.xpath("//mat-option//span[normalize-space()='Parent']");

    private By recipientsDropdown =
            By.xpath("//label[contains(text(),'Recipients')]/following::mat-select[1]");

    private By recipientOptions =
            By.xpath("//mat-option//span[contains(text(),'Parent')]");

    private By subjectField =
            By.xpath("//input[contains(@placeholder,'subject')]");
    //private By messageTypeDropdown =
            //By.xpath("//label[contains(text(),'Message Type')]/following::mat-select[1]");

    private By messageTypeOption =
        By.xpath("//label[contains(text(),'Message Type')]/following::mat-select[1]");

    /* ===== MESSAGE EDITOR ===== */
    private By messageBody =
            By.xpath("//div[@contenteditable='true']");

    /* ===== TOOLBAR BUTTONS (CORRECT) ===== */
    private By boldBtn =
            By.xpath("//button[.//mat-icon[normalize-space()='format_bold']]");

    private By italicBtn =
            By.xpath("//button[.//mat-icon[normalize-space()='format_italic']]");

    private By underlineBtn =
            By.xpath("//button[.//mat-icon[normalize-space()='format_underlined']]");

    private By sendBtn =
            By.xpath("//button//span[normalize-space()='Send Message']");
    private By overlayBackdrop =
            By.cssSelector(".cdk-overlay-backdrop");
 // Message Type dropdown
    private By messageTypeDropdown =
            By.xpath("//label[contains(text(),'Message Type')]/following::mat-select[1]");

    // All message type options
    private By messageTypeOptions =
            By.xpath("//mat-option//span[contains(@class,'mat-option-text')]");

    // Specific options
    private By regularOption =
            By.xpath("//mat-option//span[normalize-space()='Regular Message']");

    private By reminderOption =
            By.xpath("//mat-option//span[normalize-space()='Reminder']");

    private By alertOption =
            By.xpath("//mat-option//span[normalize-space()='Alert']");


    /* ================= UTILITIES ================= */

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }

    private WebElement waitFor(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private void waitAndClick(By locator) {
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(locator));
        jsClick(element);
    }

    /* ================= PAGE METHODS ================= */

    public void openComposeMessage() {
        waitAndClick(composeBtn);
    }

    public void selectIndividualTab() {
        waitAndClick(individualTab);
    }
    private void waitForOverlayToDisappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayBackdrop));
    }


    public void selectRandomParents(int count) throws InterruptedException {

        waitAndClick(categoryDropdown);
        jsClick(waitFor(parentCategoryOption));
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();

        waitAndClick(recipientsDropdown);

        List<WebElement> recipients =
                wait.until(ExpectedConditions
                        .visibilityOfAllElementsLocatedBy(recipientOptions));

        Collections.shuffle(recipients);

        for (int i = 0; i < Math.min(count, recipients.size()); i++) {
            jsClick(recipients.get(i));
            Thread.sleep(300);
        }

        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }

    public void enterRandomSubject() {
        WebElement subject = wait.until(
                ExpectedConditions.elementToBeClickable(subjectField));
        subject.clear();
        subject.sendKeys("Automation Test " + System.currentTimeMillis());
    }
    public void selectMessageType(String messageType) {
        try {
            System.out.println("Selecting message type: " + messageType);

            // Open Message Type dropdown
            WebElement dropdown =
                    wait.until(ExpectedConditions.elementToBeClickable(messageTypeDropdown));
            jsClick(dropdown);

            // Select based on input
            switch (messageType.toLowerCase()) {
                case "regular":
                case "regular message":
                    jsClick(wait.until(ExpectedConditions.elementToBeClickable(regularOption)));
                    break;

                case "reminder":
                    jsClick(wait.until(ExpectedConditions.elementToBeClickable(reminderOption)));
                    break;

                case "alert":
                    jsClick(wait.until(ExpectedConditions.elementToBeClickable(alertOption)));
                    break;

                default:
                    throw new IllegalArgumentException("Invalid Message Type: " + messageType);
            }

            System.out.println("Message type selected successfully");

            waitForOverlayToClose();

        } catch (Exception e) {
            throw new RuntimeException("Failed to select message type: " + messageType, e);
        }
    }


    /* ================= MAIN FIXED METHOD ================= */

    private void waitForOverlayToClose() {
		// TODO Auto-generated method stub
		
	}

	public void enterRandomFormattedMessage() {

        // 🔥 Ensure no overlay is present
        waitForOverlayToDisappear();

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

    public void clickSendMessage() throws InterruptedException {

        WebElement sendButton = wait.until(
                ExpectedConditions.elementToBeClickable(sendBtn));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", sendButton);

        Thread.sleep(500);
        jsClick(sendButton);
    }
}
