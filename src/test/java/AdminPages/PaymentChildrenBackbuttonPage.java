
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



public class PaymentChildrenBackbuttonPage {
    WebDriver driver;
    WebDriverWait wait;

    By paymentTitleLocator = By.xpath("//h2[normalize-space()='Payment Summary']");
    By paymentLink = By.xpath("//h4[normalize-space()='Payment']");
    By selectGurdianEle = By.xpath("//mat-form-field[.//*[contains(text(),'Select Guardian')]]//input");
    By SelectiondromGurlist = By.xpath("//mat-option//span");
    By selectChildrenDropdown = By.xpath("//mat-select[@id='mat-select-2']");
    //By selectChildrenDropdown = By.xpath("//mat-form-field[.//*[contains(text(),'Select Children')]]");
    ////mat-form-field[.//*[contains(text(),'Select Children')]]//mat-select
    By pageEle          =       By.xpath("//body");
    By fullPaymentRadio = By.xpath("//mat-radio-button[.//label[contains(.,'Full Payment')]]");
    By partialPaymentRadio = By.xpath("//mat-radio-button[contains(.,'Custom Amount')]");
    By feeForChildField = By.xpath("//input[@placeholder='Fee for Roshan Setty']");
    By amountToBePaidField = By.xpath("//input[@id='mat-input-2' and @name='totalAmountBeingPaid']");
    By ProccedpaymentButton= By.xpath("//mat-sidenav-content/app-subscriptioninfo/div[3]/button");
    By ProccedpaymentbackButton= By.xpath("//button[@class=\"mat-focus-indicator back-to-dashboard-btn mat-stroked-button mat-button-base\"]");
    
    private By countryDropdown = By.xpath("//select[@id='billingCountry']");
    private By zipcodeField = By.xpath("//input[@id='billingPostalCode']");

    
     //By selectChildrenDropdown = By.xpath("//mat-select[@id='mat-select-2']");
    //By secondChildOption = By.xpath("//mat-option");
    //By childrenOptions = By.cssSelector(".cdk-overlay-pane mat-option");
    By childrenOptions = By.cssSelector(".cdk-overlay-pane mat-option");
    
    public PaymentChildrenBackbuttonPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void clickOnPaymentPage() {
        WebElement elem = wait.until(ExpectedConditions.visibilityOfElementLocated(paymentLink));
        elem.click();
    }

    public String getpaymentpageTitleText() {
        WebElement titleElement = wait.until(ExpectedConditions.presenceOfElementLocated(paymentTitleLocator));
        wait.until(ExpectedConditions.visibilityOf(titleElement));
        return titleElement.getText().trim();
    }
    public void clickOnGurdian() {
        // Wait for guardian dropdown to be clickable
        WebElement guardianBox = wait.until(ExpectedConditions.elementToBeClickable(selectGurdianEle));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", guardianBox);

        // Use JS click to avoid overlay interception
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", guardianBox);

        // Wait a short time for Angular Material overlay animation
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(SelectiondromGurlist));

        // Collect all visible options
        List<WebElement> options = driver.findElements(SelectiondromGurlist);

        System.out.println("Available guardians:");
        for (WebElement option : options) {
            System.out.println("- " + option.getText().trim());
        }

        // Select a guardian if available
        if (!options.isEmpty()) {
            // Pick randomly or choose specific index safely
            WebElement guardianToSelect = options.size() > 30 ? options.get(5) : options.get(0);
            System.out.println("Selecting guardian: " + guardianToSelect.getText().trim());

            // Use JS click again (Angular overlays often intercept native clicks)
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", guardianToSelect);
        } else {
            System.out.println("No guardians available to select!");
        }
    }


    /*
    public void clickOnGurdian() {
        // Open the guardian dropdown
        WebElement guardianBox = wait.until(ExpectedConditions.elementToBeClickable(selectGurdianEle));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", guardianBox);
        guardianBox.click();

        // Wait for dropdown options to appear
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(SelectiondromGurlist));

        // Print available guardian names
        System.out.println("Available guardians:");
        for (WebElement option : options) {
            System.out.println("- " + option.getText().trim());
        }

        // Select second guardian if available
        if (options.size() >= 10) {
            WebElement secondGuardian = options.get(4); // index 1 = second option
            System.out.println("Selecting second guardian: " + secondGuardian.getText().trim());
            secondGuardian.click();
        } else if (options.size() == 4) {
            System.out.println("Only one guardian available. Selecting: " + options.get(0).getText().trim());
            options.get(0).click();
        } else {
            System.out.println("No guardians available to select!");
        }
    }
    */
    /*
    public void selectRandomChild() {
    	// Wait for trigger to be clickable and click
        WebElement trigger = wait.until(ExpectedConditions.elementToBeClickable(selectChildrenDropdown));
        trigger.click();
    }
    
    /*
    public void selectRandomChild() {
        // Wait for trigger to be clickable and click
public void selectRandomChild() {
        // Wait for overlay to appear before searching for options
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".cdk-overlay-pane")));

        // Now get all options
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(childrenOptions));

        if (options.isEmpty()) {
            System.out.println("No children options found!");
            return;
        }

        Random random = new Random();
        int index = random.nextInt(options.size());
        WebElement chosen = options.get(index);

        System.out.println("Selecting child: " + chosen.getText());
        chosen.click();
    }
    */
    public void selectRandomChild() {
        // Open the child dropdown
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(selectChildrenDropdown));
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        dropdown.click();

        // Get all children inside the dropdown
        List<WebElement> childrenList = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//mat-option"))
        );

        if (childrenList.isEmpty()) {
            throw new RuntimeException("No children found in dropdown.");
        }

        // Select a random child
        Random random = new Random();
        WebElement randomChild = childrenList.get(random.nextInt(childrenList.size()));

        System.out.println("Selected child: " + randomChild.getText());
        randomChild.click();
    }


    public void selectRandomPaymentOptionAndEnterAmount(String childName) throws InterruptedException {
        Random rand = new Random();

        // 1️⃣ Get all radio buttons for this child
        List<WebElement> radioButtons = driver.findElements(
            //By.xpath("//input[@type='radio' and contains(@ng-reflect-name, '" + childName + "')]")
    By.xpath("//mat-radio-button[contains(.,'Custom Amount') and contains(.,'" + childName + "')]")

        );

        if (radioButtons.isEmpty()) {
            throw new RuntimeException("No radio buttons found for child: " + childName);
        }

        // 2️⃣ Pick one random radio button
        WebElement selectedRadio = radioButtons.get(rand.nextInt(radioButtons.size()));
        Thread.sleep(2000);
        selectedRadio.click();

        // 3️⃣ Find the amount input for this child
        WebElement amountInput = driver.findElement(
            By.xpath("//input[@type='number' and contains(@ng-reflect-name, 'amountBeingPaid_" + childName + "')]")
        );

        // 4️⃣ Clear previous value
        amountInput.clear();

        // 5️⃣ Decide amount based on radio choice
        String selectedValue = selectedRadio.getAttribute("value"); // Assuming radio has value attribute
        String maxValueAttr = amountInput.getAttribute("max");
        double maxValue = (maxValueAttr != null && !maxValueAttr.isEmpty()) ? Double.parseDouble(maxValueAttr) : 0;

        if (selectedValue != null && selectedValue.toLowerCase().contains("full")) {
            // Full amount
            amountInput.sendKeys(String.valueOf((int) maxValue));
            System.out.println("Full amount entered for " + childName + ": " + (int) maxValue);
        } else {
            // Partial amount (random between 1 and max-1)
            int partialValue = rand.nextInt((int) maxValue - 1) + 1;
            amountInput.sendKeys(String.valueOf(partialValue));
            System.out.println("Partial amount entered for " + childName + ": " + partialValue);
        }
    }
  
    public void selectRandomPaymentOptionAndEnterAmount(String fullAmount, String partialAmount) {
        // TODO: Implement selecting a random payment option and entering the amount
    }
    

    public void Procedbuttonpayment() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until overlay/backdrop disappears
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.cssSelector("div.cdk-overlay-backdrop.cdk-overlay-backdrop-showing")
        ));

        // Wait until the actual button (not span) is clickable
        WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button.button-proceed")
        ));

        // Scroll into view before clicking
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", proceedBtn);

        // Click with JS to avoid overlay intercept issues
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", proceedBtn);
    }
    public void clickbackButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(ProccedpaymentbackButton));
        button.click();
        System.out.println("Clicked on Pay Securely button");
    }
}





