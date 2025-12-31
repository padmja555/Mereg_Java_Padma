package SuperAdminPage;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.*;

public class TempGurdianlistPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Random random = new Random();

    // --- Locators ---
    private By userId = By.id("login-username");
    private By password = By.id("login-password");
    private By signInBtn = By.xpath("//span[text()='Sign In']");
    private By paymentTab = By.xpath("//h4[normalize-space()='Payment']");
    private By guardianDropdown = By.xpath("//input[@aria-label='Select Guardian']");
    private By childDropdown = By.xpath("//mat-select[contains(@class,'mat-select')]");
    private By customAmountRadio = By.xpath("//mat-radio-button[contains(.,'Custom Amount')]");
    private By radioInput = By.xpath("//mat-radio-button[contains(.,'Custom Amount')]//input[@type='radio']");
    private By amountField = By.xpath("//input[@type='number' and contains(@id,'mat-input-')]");
    private By proceedButton = By.xpath("//span[contains(@class, 'mat-button-wrapper') and contains(text(), 'Proceed to payment')]");

    public TempGurdianlistPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    /*
    // --- Step 1: Login ---
    /public void login(String username, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(userId)).sendKeys(username);
        //driver.findElement(password).sendKeys(pass);
        //driver.findElement(signInBtn).click();
        System.out.println("Logged in successfully as " + username);
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    */

    // --- Step 2: Click Payment tab ---
    public void openPaymentTab() {
        try {
            System.out.println("Waiting for login to complete...");
            Thread.sleep(2000);
            
            WebElement paymentTabElement = driver.findElement(paymentTab);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", paymentTabElement);
            System.out.println("Opened Payment tab");
            
            Thread.sleep(3000); // Wait for 3 seconds as requested
            
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Error opening Payment tab: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // --- Step 3: Select Random Guardian and Child ---
    public boolean selectGuardianAndChildWithValidAmount() {
        int maxAttempts = 15;
        int attempt = 0;
        
        while (attempt < maxAttempts) {
            attempt++;
            System.out.println("\n Attempt " + attempt + " to find child with valid amount");
            
            try {
                // Select random guardian
                String selectedGuardian = selectRandomGuardian();
                if (selectedGuardian == null) {
                    System.out.println("Failed to select guardian");
                    refreshPageAndRetry();
                    continue;
                }
                
                Thread.sleep(2000);
                
                // Select random child
                String selectedChild = selectRandomChild();
                if (selectedChild == null) {
                    System.out.println("Failed to select child");
                    refreshPageAndRetry();
                    continue;
                }
                
                System.out.println("Selected guardian: " + selectedGuardian + " and child: " + selectedChild);
                
                // Shift focus away from dropdown
                shiftFocusAway();
                Thread.sleep(2000);
                
                // Get amount from field
                Double amount = getAmountFromField();
                System.out.println("Current amount in field: " + amount);
                
                if (amount == null) {
                    System.out.println("Could not read amount from field");
                    refreshPageAndRetry();
                    continue;
                }
                
                if (amount > 0) {
                    System.out.println("Valid amount found: $" + amount);
                    return true;
                } else if (amount == 0) {
                    System.out.println("Amount is $0 for child: " + selectedChild + ", refreshing and selecting different guardian/child");
                    refreshPageAndRetry();
                    continue;
                }
                
            } catch (Exception e) {
                System.out.println("Error in attempt " + attempt + ": " + e.getMessage());
                refreshPageAndRetry();
            }
        }
        
        System.out.println("Could not find child with valid amount after " + maxAttempts + " attempts");
        return false;
    }

    // --- Helper method to select random guardian ---
    public String selectRandomGuardian() {
        try {
            System.out.println("Looking for guardian dropdown...");
            
            WebElement guardianInput = wait.until(ExpectedConditions.elementToBeClickable(guardianDropdown));
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", guardianInput);
            Thread.sleep(1000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", guardianInput);
            System.out.println("Opened guardian dropdown");

            Thread.sleep(2000);
            
            // Wait for guardian options to appear
            By guardianOptionsLocator = By.xpath("//mat-option[contains(@class,'mat-option')]");
            List<WebElement> guardianOptions = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(guardianOptionsLocator));
            
            if (guardianOptions.isEmpty()) {
                System.out.println("No guardian options found");
                return null;
            }
            
            System.out.println("Found " + guardianOptions.size() + " guardian options");
            
            // Select random guardian
            int randomIndex = random.nextInt(guardianOptions.size());
            WebElement randomGuardian = guardianOptions.get(randomIndex);
            String guardianName = randomGuardian.getText().trim();
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", randomGuardian);
            System.out.println("Selected guardian: " + guardianName);
            
            Thread.sleep(1000);
            return guardianName;

        } catch (Exception e) {
            System.out.println("Error in selectRandomGuardian: " + e.getMessage());
            return null;
        }
    }

    // --- Helper method to select random child ---
    public String selectRandomChild() {
        try {
            System.out.println("Looking for children dropdown...");
            
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(childDropdown));
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", dropdown);
            Thread.sleep(1000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdown);
            System.out.println("Opened children dropdown");

            Thread.sleep(2000);
            
            By optionsLocator = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
            List<WebElement> options = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(optionsLocator));
            
            if (options.isEmpty()) {
                System.out.println("No child options found");
                return null;
            }
            
            System.out.println("Found " + options.size() + " child options");
            
            // Select random child
            int randomIndex = random.nextInt(options.size());
            WebElement randomChild = options.get(randomIndex);
            String childName = randomChild.getText().trim();
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", randomChild);
            System.out.println("Selected child: " + childName);
            
            Thread.sleep(1000);
            return childName;

        } catch (Exception e) {
            System.out.println("Error in selectRandomChild: " + e.getMessage());
            return null;
        }
    }

    // --- Helper method to shift focus away ---
    private void shiftFocusAway() {
        System.out.println("Shifting focus away from dropdown...");
        
        try {
            Actions actions = new Actions(driver);
            actions.sendKeys(Keys.ESCAPE).perform();
            System.out.println("Pressed ESC key to close dropdown");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Could not press ESC key");
        }
        
        try {
            WebElement body = driver.findElement(By.tagName("body"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", body);
            System.out.println("Clicked body to shift focus");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Could not click body");
        }
        
        try {
            Actions actions = new Actions(driver);
            actions.sendKeys(Keys.TAB).perform();
            System.out.println("Pressed TAB to move focus");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Could not press TAB");
        }
    }

    // --- Refresh Page and Retry ---
    private void refreshPageAndRetry() {
        try {
            System.out.println("Refreshing page...");
            driver.navigate().refresh();
            Thread.sleep(3000);
            
            wait.until(ExpectedConditions.elementToBeClickable(paymentTab)).click();
            System.out.println("Page refreshed and Payment tab reopened");
            Thread.sleep(2000);
            
        } catch (Exception e) {
            System.out.println("Error refreshing page: " + e.getMessage());
        }
    }

    // --- Helper method to get amount from field ---
    public Double getAmountFromField() {
        try {
            WebElement amountBox = wait.until(ExpectedConditions.presenceOfElementLocated(amountField));
            
            String amountValue = amountBox.getAttribute("value");
            if (amountValue == null || amountValue.trim().isEmpty()) {
                amountValue = amountBox.getAttribute("ng-reflect-model");
            }
            if (amountValue == null || amountValue.trim().isEmpty()) {
                amountValue = amountBox.getAttribute("data-value");
            }
            
            if (amountValue == null || amountValue.trim().isEmpty()) {
                System.out.println("All attribute strategies returned null or empty");
                return null;
            }
            
            amountValue = amountValue.replace("$", "").replace(",", "").replace(" ", "").trim();
            
            try {
                return Double.parseDouble(amountValue);
            } catch (NumberFormatException e) {
                System.out.println("Number format exception for value: '" + amountValue + "'");
                return null;
            }
            
        } catch (Exception e) {
            System.out.println("Error getting amount from field: " + e.getMessage());
            return null;
        }
    }

    // --- Step 4: Choose Custom Amount ---
    public void chooseCustomAmount() {
        try {
            System.out.println("Attempting to select Custom Amount radio button...");
            
            WebElement customRadio = wait.until(ExpectedConditions.presenceOfElementLocated(customAmountRadio));
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", customRadio);
            Thread.sleep(1000);
            
            if (isCustomAmountSelected()) {
                System.out.println("Custom Amount is already selected");
                return;
            }
            
            boolean clicked = false;
            
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", customRadio);
                System.out.println("Clicked Custom Amount using JavaScript");
                clicked = true;
            } catch (Exception e) {
                System.out.println("JavaScript click failed: " + e.getMessage());
            }
            
            Thread.sleep(1000);
            
            if (!isCustomAmountSelected()) {
                try {
                    WebElement radioInputElement = driver.findElement(radioInput);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radioInputElement);
                    System.out.println("Clicked radio input directly using JavaScript");
                    clicked = true;
                } catch (Exception e) {
                    System.out.println("Radio input click failed: " + e.getMessage());
                }
            }
            
            Thread.sleep(1000);
            
            if (isCustomAmountSelected()) {
                System.out.println("Custom Amount selection verified successfully");
            } else {
                System.out.println("Custom Amount still not selected after all attempts");
            }
            
            Thread.sleep(1000);
            
        } catch (Exception e) {
            System.out.println("Error selecting custom amount: " + e.getMessage());
        }
    }

    // --- Helper method to check if Custom Amount is selected ---
    private boolean isCustomAmountSelected() {
        try {
            List<WebElement> checkedRadios = driver.findElements(By.xpath("//mat-radio-button[contains(@class,'mat-radio-checked')]"));
            if (!checkedRadios.isEmpty()) {
                for (WebElement radio : checkedRadios) {
                    if (radio.getText().contains("Custom Amount")) {
                        System.out.println(" Custom Amount radio button is visually selected");
                        return true;
                    }
                }
            }
            
            WebElement radioInputElement = driver.findElement(radioInput);
            boolean isChecked = (Boolean) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].checked;", radioInputElement);
            
            if (isChecked) {
                System.out.println(" Custom Amount input is checked programmatically");
            }
            
            return isChecked;
            
        } catch (Exception e) {
            System.out.println(" Error checking radio button state: " + e.getMessage());
            return false;
        }
    }

    // --- Step 5: Enter Random Amount ---
    public void enterRandomAmount() {
        try {
            WebElement amountBox = wait.until(ExpectedConditions.visibilityOfElementLocated(amountField));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", amountBox);
            
            // Get the current amount to ensure we don't exceed the maximum
            Double currentAmount = getAmountFromField();
            int maxAllowed = (currentAmount != null && currentAmount > 0) ? currentAmount.intValue() : 100;
            
            int randomAmount = random.nextInt(Math.min(100, maxAllowed)) + 1;
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", amountBox);
            Thread.sleep(500);
            
            String script = 
                "var element = arguments[0]; " +
                "var value = arguments[1]; " +
                "element.value = value; " +
                "element.dispatchEvent(new Event('input', { bubbles: true })); " +
                "element.dispatchEvent(new Event('change', { bubbles: true })); " +
                "element.dispatchEvent(new Event('blur', { bubbles: true })); " +
                "element.dispatchEvent(new Event('keyup', { bubbles: true })); " +
                "element.dispatchEvent(new Event('keydown', { bubbles: true })); " +
                "if (element.ngModel) element.ngModel.$setViewValue(value); " +
                "if (element.ngChange) element.ngChange();";
            
            ((JavascriptExecutor) driver).executeScript(script, amountBox, String.valueOf(randomAmount));
            
            System.out.println("Entered random amount: $" + randomAmount);
            
            Thread.sleep(2000);
            
            String actualValue = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;", amountBox);
            System.out.println("Amount field value verified: $" + actualValue);
            
        } catch (Exception e) {
            System.out.println("Error entering random amount: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- Step 6: Click Proceed to Payment ---
    public void clickProceedToPayment() {
        try {
            System.out.println("Clicking Proceed to Payment button...");
            
            WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(proceedButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", proceedBtn);
            Thread.sleep(1000);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", proceedBtn);
            
            System.out.println("Clicked Proceed to Payment button successfully");
            Thread.sleep(3000);
            
        } catch (Exception e) {
            System.out.println("Error clicking Proceed to Payment: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // --- Full Flow Combined ---
    public void completeAdminPaymentFlow(String username, String pass) {
       // login(username, pass);
        openPaymentTab();
        
        if (selectGuardianAndChildWithValidAmount()) {
            try {
                System.out.println("Waiting for dropdown to close...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            chooseCustomAmount();
            enterRandomAmount();
            clickProceedToPayment();
            System.out.println("Admin Payment flow completed successfully until Proceed to Payment!");
        } else {
            System.out.println("Admin Payment flow failed - no child with valid amount found");
        }
    }
}