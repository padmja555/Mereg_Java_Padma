package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;

public class AllParentComposeMeesagePage {

    WebDriver driver;
    WebDriverWait wait;
    Random random = new Random();

    private By newMessageBtn =
            By.xpath("//button[contains(@class,'new-message-btn')]");
    ////button[contains(@class,'new-message-btn')]

    private By allParentsTab =
            By.xpath("//span[normalize-space()='All Parents']");

    private By primaryRecipientDropdown =
            By.xpath("//label[contains(text(),'Primary Recipient')]/following::mat-select[1]");

    private By primaryAllParentsOption =
            By.xpath("//mat-option//span[normalize-space()='All Parents']");

    private By additionalRecipientsDropdown =
            By.xpath("(//mat-select)[2]");

    private By staffOptions =
            By.xpath("//mat-optgroup[@label='Staff']//mat-option");

    private By messageTypeDropdown =
            By.xpath("//mat-select[@formcontrolname='messageType']");

    private By messageTypeOptions =
            By.xpath("//mat-option//span");

    private By subjectField =
            By.xpath("//input[@placeholder='Type message subject']");

    private By messageBody =
            By.xpath("//div[@contenteditable='true']");

    private By sendBtn =
            By.xpath("//span[contains(text(),'Send Message')]");

    public AllParentComposeMeesagePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    private void closeDropdown() {
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }

    public void openComposeMessage() {
        wait.until(ExpectedConditions.elementToBeClickable(newMessageBtn)).click();
    }

    public void selectAllParentsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(allParentsTab)).click();
    }

    public void selectPrimaryAllParents() {
        jsClick(wait.until(ExpectedConditions.elementToBeClickable(primaryRecipientDropdown)));
        wait.until(ExpectedConditions.elementToBeClickable(primaryAllParentsOption)).click();
        closeDropdown();
    }

    public void selectRandomStaffCC() {
        jsClick(wait.until(ExpectedConditions.elementToBeClickable(additionalRecipientsDropdown)));

        List<WebElement> staffList =
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(staffOptions));

        Collections.shuffle(staffList);
        int count = Math.min(2, staffList.size());

        for (int i = 0; i < count; i++) {
            jsClick(staffList.get(i));
        }
        closeDropdown();
    }

    public void selectRandomMessageType() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        By messageTypeDropdown = By.xpath("//mat-select[@formcontrolname='messageType']");

        // Open dropdown
        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(messageTypeDropdown)
        );
        dropdown.click();

        By optionsLocator = By.xpath(
                "//div[contains(@class,'cdk-overlay-pane')]//mat-option//span"
        );

        // Wait until all options are visible
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(optionsLocator));

        int optionCount = driver.findElements(optionsLocator).size();

        if (optionCount == 0) {
            throw new RuntimeException("No message type options found");
        }

        int randomIndex = new Random().nextInt(optionCount);

        // Re-locate and click immediately
        driver.findElements(optionsLocator).get(randomIndex).click();
    }

    public void enterRandomSubjectAndMessage() {
        wait.until(ExpectedConditions.elementToBeClickable(subjectField))
                .sendKeys("Automation Test Message");

        wait.until(ExpectedConditions.elementToBeClickable(messageBody))
                .sendKeys("This message is sent by Selenium automation.");
    }
    /*
    public void clickSendButton() {
        try {
            WebElement sendBtn1 = wait.until(ExpectedConditions.elementToBeClickable(sendBtn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendBtn1);
            System.out.println("✅ Clicked Send button");
            Thread.sleep(4000);
        } catch (Exception e) {
            System.out.println("❌ Failed to click Send: " + e.getMessage());
        }
    }
*/
    public void clickSendMessage() {

        By sendBtn = By.xpath("//button//span[normalize-space()='Send Message']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // wait for button to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(sendBtn));

        // wait until enabled
        wait.until(driver -> driver.findElement(sendBtn).isEnabled());

        WebElement button = driver.findElement(sendBtn);

        // scroll into view
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", button);

        // click using JS
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", button);
    }


}
