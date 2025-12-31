package SuperAdminPage;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SuperSelectGuardianPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Random random;

    private By selectGuardianEle = By.xpath("//mat-card/mat-card-content/mat-form-field[1]//input");
    private By childrenEle = By.xpath("//mat-card/mat-card-content/mat-form-field[2]");
    private By listOptions = By.xpath("//mat-option//span");
    private By guardianOptions = By.xpath("//div[@role='listbox']//mat-option");
    private By SelectiondromGurlist = By.xpath("//mat-option//span"); // Fixed typo

    private By fullPaymentRadio = By.xpath("//mat-radio-button[.//label[contains(.,'Full Amount')]]");
    private By customPaymentRadio = By.xpath("//mat-radio-button[.//label[contains(.,'Custom Amount')]]");
    private By amountToBePaidField = By.xpath("//input[@formcontrolname='amount']");
    private By proceedPaymentButton = By.xpath("//span[normalize-space()='Proceed to payment']/parent::button");
    private By overlay = By.cssSelector("div.cdk-overlay-backdrop.cdk-overlay-backdrop-showing");
    
    // Locators based on your screenshot
    private By childFeeInputs = By.xpath("//label[contains(text(),'Fee for')]/following-sibling::input");
    private By amountUpArrow = By.xpath("//input[contains(@id,'fee')]/following-sibling::button[contains(@class,'increase') or contains(text(),'↑')]");
    private By amountDownArrow = By.xpath("//input[contains(@id,'fee')]/following-sibling::button[contains(@class,'decrease') or contains(text(),'↓')]");
    private By childFeeLabels = By.xpath("//label[contains(text(),'Fee for')]");

    public SuperSelectGuardianPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.random = new Random();
    }

    private void waitForOverlayToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));
            Thread.sleep(300);
        } catch (Exception ignored) {}
    }

    public void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
   
    /** Clear guardian selection */
    private void clearGuardianSelection() {
        try {
            WebElement guardianInput = driver.findElement(selectGuardianEle);
            guardianInput.clear();
            // Sometimes need to send keys to trigger clear
            guardianInput.sendKeys(Keys.CONTROL + "a");
            guardianInput.sendKeys(Keys.DELETE);
            Thread.sleep(500);
        } catch (Exception e) {
            System.out.println("⚠️ Could not clear guardian selection: " + e.getMessage());
        }
    }

    /** Click on guardian dropdown and select */
    /** Click on guardian dropdown and select by index */
    public void clickOnGuardian(int indexToSelect) {
        By guardianOptions = By.xpath("//div[@role='listbox']//mat-option//span");

        // Open the dropdown safely
        WebElement guardianBox = wait.until(ExpectedConditions.elementToBeClickable(selectGuardianEle));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", guardianBox);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", guardianBox);

        // Wait for overlay to appear and stabilize
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(guardianOptions));
        try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // short pause for Angular animations

        int attempts = 0;
        List<WebElement> options = null;
        while (attempts < 3) {
            try {
                options = driver.findElements(guardianOptions);
                System.out.println("Available guardians:");
                for (int i = 0; i < options.size(); i++) {
                    System.out.println(i + ": " + options.get(i).getText().trim());
                }
                break; // success, exit retry loop
            } catch (StaleElementReferenceException e) {
                System.out.println("⚠️ Options became stale, retrying...");
                try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                attempts++;
            }
        }

        if (options == null || options.isEmpty()) {
            throw new RuntimeException("No guardian options found.");
        }

        // Select guardian safely
        if (indexToSelect >= 0 && indexToSelect < options.size()) {
            WebElement guardianToSelect = driver.findElements(guardianOptions).get(indexToSelect);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", guardianToSelect);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", guardianToSelect);
            System.out.println("✅ Selected guardian: " + guardianToSelect.getText().trim());
        } else {
            System.out.println("❌ Invalid index: " + indexToSelect + ", available: " + options.size());
        }
    }

    public void selectRandomChild() {
        // Wait for and click the child input dropdown
        WebElement childInput = wait.until(ExpectedConditions.elementToBeClickable(childrenEle));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", childInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", childInput);

        // Wait for dropdown options to appear
        By childOptions = By.xpath("//div[@role='listbox']//mat-option//span");
        List<WebElement> childrenList = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(childOptions)
        );

        if (childrenList.isEmpty()) {
            throw new RuntimeException("❌ No children found in dropdown.");
        }

        // Select a random child
        Random random = new Random();
        int randomIndex = random.nextInt(childrenList.size());
        WebElement randomChild = childrenList.get(randomIndex);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", randomChild);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", randomChild);

        System.out.println("✅ Selected child: " + randomChild.getText().trim());
    }


    /** SIMPLIFIED VERSION - Directly find guardian with children */
    public boolean selectAnyGuardianWithChildren() {
        try {
            System.out.println("🎯 Directly selecting any guardian with children...");

            // Clear current selection
            clearGuardianSelection();
            Thread.sleep(1000);

            // Open guardian dropdown
            WebElement guardianInput = wait.until(ExpectedConditions.elementToBeClickable(selectGuardianEle));
            guardianInput.click();
            Thread.sleep(1500);

            List<WebElement> guardians = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(guardianOptions));
            
            System.out.println("📋 Total guardians available: " + guardians.size());

            // Try each guardian sequentially
            for (WebElement guardian : guardians) {
                try {
                    String guardianName = guardian.getText().trim();
                    if (guardianName.isEmpty()) continue;

                    System.out.println("🔄 Testing guardian: " + guardianName);
                    
                    // Select this guardian
                    jsClick(guardian);
                    Thread.sleep(2000);

                    // Check children
                    WebElement childrenDropdown = wait.until(ExpectedConditions.elementToBeClickable(childrenEle));
                    childrenDropdown.click();
                    Thread.sleep(1500);
                    
                    List<WebElement> children = driver.findElements(listOptions);
                    
                    if (!children.isEmpty()) {
                        System.out.println("✅ Guardian '" + guardianName + "' has " + children.size() + " children");
                        
                        // Select all children
                        for (WebElement child : children) {
                            String childName = child.getText().trim();
                            child.click();
                            System.out.println("   👶 Selected: " + childName);
                            Thread.sleep(500);
                            new Actions(driver).sendKeys(Keys.ESCAPE).perform();
                            Thread.sleep(1000);
                        }
                        
                        // Close children dropdown by clicking guardian input
                        guardianInput.click();
                        Thread.sleep(500);
                        
                        return true;
                    } else {
                        System.out.println("❌ No children for: " + guardianName);
                        // Clear and try next
                        clearGuardianSelection();
                        Thread.sleep(1000);
                        guardianInput.click();
                        Thread.sleep(1500);
                        guardians = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(guardianOptions));
                    }
                    
                } catch (Exception e) {
                    System.out.println("⚠️ Error testing guardian: " + e.getMessage());
                    continue;
                }
            }
            
            System.out.println("❌ No guardian with children found!");
            return false;
            
        } catch (Exception e) {
            System.out.println("❌ Error in direct selection: " + e.getMessage());
            return false;
        }
    }

    /** Select payment option */
    public void selectPaymentOption(String paymentType) {
        try {
            waitForOverlayToDisappear();
            Thread.sleep(1000);

            if ("custom".equalsIgnoreCase(paymentType) || "partial".equalsIgnoreCase(paymentType)) {
                WebElement customRadio = wait.until(ExpectedConditions.elementToBeClickable(customPaymentRadio));
                jsClick(customRadio);
                System.out.println("✅ Custom Amount option selected");
                Thread.sleep(2000);
            } else {
                WebElement fullRadio = wait.until(ExpectedConditions.elementToBeClickable(fullPaymentRadio));
                jsClick(fullRadio);
                System.out.println("✅ Full Amount selected");
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("❌ Error selecting payment option: " + e.getMessage());
        }
    }

    /** Test up/down arrows for custom amount */
    public void testCustomAmountArrows() {
        try {
            System.out.println("🎯 Testing custom amount arrows...");
            
            List<WebElement> feeInputs = driver.findElements(childFeeInputs);
            List<WebElement> feeLabels = driver.findElements(childFeeLabels);
            
            System.out.println("💰 Found " + feeInputs.size() + " fee inputs");
            
            for (int i = 0; i < feeInputs.size(); i++) {
                WebElement feeInput = feeInputs.get(i);
                String childName = feeLabels.size() > i ? 
                    feeLabels.get(i).getText().replace("Fee for", "").replace("*", "").trim() : "Child " + (i+1);
                
                String currentValue = feeInput.getAttribute("value");
                System.out.println("   Current fee for " + childName + ": " + currentValue);
                
                // Try to find and use arrows, if not found, set manually
                try {
                    List<WebElement> upArrows = driver.findElements(amountUpArrow);
                    List<WebElement> downArrows = driver.findElements(amountDownArrow);
                    
                    if (!upArrows.isEmpty() && upArrows.size() > i) {
                        // Test up arrow
                        System.out.println("   ⬆️ Testing UP arrow...");
                        for (int j = 0; j < 3; j++) {
                            jsClick(upArrows.get(i));
                            Thread.sleep(500);
                            System.out.println("      After up click " + (j+1) + ": " + feeInput.getAttribute("value"));
                        }
                        
                        // Test down arrow  
                        System.out.println("   ⬇️ Testing DOWN arrow...");
                        for (int j = 0; j < 3; j++) {
                            jsClick(downArrows.get(i));
                            Thread.sleep(500);
                            System.out.println("      After down click " + (j+1) + ": " + feeInput.getAttribute("value"));
                        }
                    } else {
                        System.out.println("   🔄 No arrows found, setting values manually");
                        setManualAmounts(feeInput, currentValue);
                    }
                } catch (Exception e) {
                    System.out.println("   ⚠️ Arrow test failed, using manual method");
                    setManualAmounts(feeInput, currentValue);
                }
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error testing custom amount arrows: " + e.getMessage());
        }
    }

    private void setManualAmounts(WebElement feeInput, String originalValue) {
        try {
            // Set to high value
            feeInput.clear();
            feeInput.sendKeys("5000");
            System.out.println("      Set to: 5000");
            Thread.sleep(1000);
            
            // Set to low value
            feeInput.clear();
            feeInput.sendKeys("1000");
            System.out.println("      Set to: 1000");
            Thread.sleep(1000);
            
            // Restore original
            feeInput.clear();
            feeInput.sendKeys(originalValue);
            System.out.println("      Restored to: " + originalValue);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("      Error in manual amount setting: " + e.getMessage());
        }
    }

    /** Proceed to payment */
    public boolean proceedToPayment() {
        try {
            waitForOverlayToDisappear();
            Thread.sleep(1000);
            
            WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(proceedPaymentButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", proceedBtn);
            Thread.sleep(500);
            
            jsClick(proceedBtn);
            System.out.println("✅ Clicked 'Proceed to payment'");
            Thread.sleep(3000);
            
            return true;
        } catch (Exception e) {
            System.out.println("❌ Error proceeding to payment: " + e.getMessage());
            return false;
        }
    }

    /** Verify payment summary displayed */
    public boolean verifyPaymentSummary() {
        try {
            WebElement summary = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Payment Summary') or contains(text(),'payment summary')]")));
            boolean isDisplayed = summary.isDisplayed();
            System.out.println("✅ Payment Summary displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("❌ Payment Summary not displayed");
            return false;
        }
    }
}