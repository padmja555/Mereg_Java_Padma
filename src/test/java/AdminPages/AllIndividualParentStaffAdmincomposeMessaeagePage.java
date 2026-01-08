
package AdminPages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.time.Duration;
import java.util.*;

public class AllIndividualParentStaffAdmincomposeMessaeagePage {

    WebDriver driver;
    WebDriverWait wait;
    Random random = new Random();

    private By newMessageBtn =
            By.xpath("//button[contains(@class,'new-message-btn')]");
    ////button[contains(@class,'new-message-btn')]

    private By allIndidualTab =
            By.xpath("//span[normalize-space()='Individual']/parent::*");

    private By primaryRecipientDropdown =
            By.xpath("//div[contains(@class,'mat-select-value')]");

    //private By primaryAllRecipientDropdownOption =
           // By.xpath("//mat-select[@formcontrolname='primaryRecipient']");
    private By primaryAllRecipientDropdownOption =
            By.xpath("(//mat-select)[2]");
    private By additionalRecipientsDropdown =
            By.xpath("(//mat-select)[6]");

    private By staffOptions =
            By.xpath("//mat-optgroup[@label='Staff']//mat-option");
    
    private By adminOptions =
            By.xpath("//mat-optgroup[@label='Admin']//mat-option");
    
    private By parentOptions =
            By.xpath("//mat-optgroup[@label='Parent']//mat-option");

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

    public AllIndividualParentStaffAdmincomposeMessaeagePage(WebDriver driver) {
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

    public void selectAllIndidualTab() {
        wait.until(ExpectedConditions.elementToBeClickable(allIndidualTab)).click();
    }

    public void selectThreeCategories() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1️⃣ Open Select Category dropdown
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-select[@formcontrolname='individualCategory']")
        ));
        js.executeScript("arguments[0].click();", dropdown);

        // 2️⃣ Select required options
        selectCategoryOption("Staff");
        selectCategoryOption("Parent");
        selectCategoryOption("Admin");

        // 3️⃣ Close dropdown
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }
//**********************************************
  /*
    public void selectRandomRecipients(int totalCount) {

        // Open Recipients dropdown
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-select[@formcontrolname=\"individualCategory\"]")
        )).click();

        // Get ALL recipients
        List<WebElement> allRecipients = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//mat-option//span[@class='mat-option-text']")
                )
        );

        Collections.shuffle(allRecipients);

        int count = Math.min(totalCount, allRecipients.size());

        for (int i = 0; i < count; i++) {
            allRecipients.get(i).click();
        }

        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }
    */
    public void selectRandomRecipients(int count) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        
        try {
            // 1. Open dropdown
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-select[@placeholder='Select your recipients']")
            ));
            dropdown.click();
            
            // 2. Wait for options with shorter timeout
            List<WebElement> options = Collections.emptyList();
            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                options = shortWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//mat-option")
                ));
            } catch (TimeoutException e) {
                System.out.println("No recipients found. Creating placeholder selection...");
                
                // 3. If no options, select whatever is available or use default
                // Try to find ANY selectable option
                options = driver.findElements(By.xpath("//*[@role='option']"));
                
                if (options.isEmpty()) {
                    System.out.println("WARNING: No recipients available. Test may fail or need manual setup.");
                    // Close dropdown and return
                    dropdown.sendKeys(Keys.ESCAPE);
                    return;
                }
            }
            
            // 4. Select available options
            System.out.println("Available recipients: " + options.size());
            
            if (options.size() > 0) {
                Random rand = new Random();
                int selectCount = Math.min(count, options.size());
                
                for (int i = 0; i < selectCount; i++) {
                    int index = rand.nextInt(options.size());
                    options.get(index).click();
                    Thread.sleep(300);
                    
                    // Remove selected option to avoid duplicates if multi-select
                    if (options.size() > 1) {
                        options.remove(index);
                    }
                }
            }
            
            // 5. Close dropdown
            dropdown.sendKeys(Keys.ESCAPE);
            
        } catch (Exception e) {
            System.err.println("Error selecting recipients: " + e.getMessage());
            // Take screenshot for debugging
            //takeScreenshot("recipient_selection_error");
            throw e;
        }
    } 
    
    //******************************** Recipantoption secondmehtod2:::::::::::::

    //:::::::::::::::::::::::::::::::::::::::2method:::::::::
	public void selectRandomMessageType() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 1️⃣ Click Message Type dropdown
        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//mat-select[@formcontrolname='messageType']")
                )
        );
        dropdown.click();

        // 2️⃣ Wait for overlay options
        By optionsLocator = By.xpath(
                "//div[contains(@class,'cdk-overlay-pane')]//mat-option//span"
        );
        wait.until(ExpectedConditions.visibilityOfElementLocated(optionsLocator));

        // 3️⃣ Get fresh options
        List<WebElement> options = driver.findElements(optionsLocator);

        // 4️⃣ Select random option
        int randomIndex = new Random().nextInt(options.size());

        // ⚠️ Re-locate AGAIN before click (critical)
        WebElement optionToClick = driver.findElements(optionsLocator).get(randomIndex);

        // 5️⃣ Click immediately (no reuse)
        optionToClick.click();
    }

    public void enterRandomSubjectAndMessage() {
        wait.until(ExpectedConditions.elementToBeClickable(subjectField))
                .sendKeys("Automation Test Message");

        wait.until(ExpectedConditions.elementToBeClickable(messageBody))
                .sendKeys("This message is sent by Selenium automation.");
    }
    private void selectCategoryOption(String optionText) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        By optionLocator = By.xpath(
            "//div[contains(@class,'cdk-overlay-pane')]//mat-option//span[normalize-space()='" 
            + optionText + "']/preceding-sibling::mat-pseudo-checkbox"
        );

        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));

        // click only if not already selected
        if (!checkbox.getAttribute("class").contains("mat-pseudo-checkbox-checked")) {
            js.executeScript("arguments[0].click();", checkbox);
        }
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
