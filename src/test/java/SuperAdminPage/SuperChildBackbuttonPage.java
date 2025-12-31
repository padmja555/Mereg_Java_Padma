package SuperAdminPage;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.*;

public class SuperChildBackbuttonPage {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    Random random = new Random();

    // ======= FIXED LOCATORS =======
    private By guardianDropdown = By.xpath("//mat-form-field[.//*[contains(text(),'Select Guardian')]]//input");
    private By guardianOptions = By.xpath("//mat-option//span");
    private By childrenDropdown = By.xpath("//mat-form-field[.//*[contains(text(),'Select Children')]]//mat-select");
    private By childrenOptions = By.xpath("//div[@class='cdk-overlay-pane']//mat-option");
    
    // Radio button locators
    private By customAmountRadio = By.xpath("//mat-radio-button[contains(.,'Custom Amount')]");
    private By fullAmountRadio = By.xpath("//mat-radio-button[contains(.,'Full Amount')]");
    private By radioInput = By.xpath("//mat-radio-button[contains(.,'Custom Amount')]//input[@type='radio']");
    private By radioChecked = By.xpath("//mat-radio-button[contains(@class,'mat-radio-checked')]");
    private By ProccedpaymentbackButton= By.xpath("//button[@class=\"mat-focus-indicator back-to-dashboard-btn mat-stroked-button mat-button-base\"]");
  
    // Amount field
    private By amountField = By.xpath("//input[@formcontrolname='amount' or contains(@placeholder,'Amount')]");
    private By proceedButton = By.xpath("//button[contains(.,'Proceed') or contains(.,'Pay')]");

    public SuperChildBackbuttonPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    // ======= FIXED OVERLAY HANDLING =======

    /**
     * Wait for and remove any blocking overlays
     */
    private void handleOverlays() {
        try {
            System.out.println("🛡️ Checking for blocking overlays...");
            
            // Wait for any backdrop overlays to disappear
            By backdropLocator = By.xpath("//div[contains(@class,'cdk-overlay-backdrop')]");
            List<WebElement> backdrops = driver.findElements(backdropLocator);
            
            for (WebElement backdrop : backdrops) {
                try {
                    // If backdrop is visible and blocking, try to remove it
                    if (backdrop.isDisplayed()) {
                        System.out.println("⚠️ Found blocking overlay, attempting to close...");
                        
                        // Method 1: Click the backdrop to close it
                        js.executeScript("arguments[0].click();", backdrop);
                        Thread.sleep(1000);
                        
                        // Method 2: Press ESC key
                        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
                        Thread.sleep(1000);
                        
                        // Method 3: Hide with JavaScript
                        js.executeScript("arguments[0].style.display = 'none';", backdrop);
                        Thread.sleep(500);
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Could not handle backdrop: " + e.getMessage());
                }
            }
            
            // Wait for overlays to be gone
            wait.until(ExpectedConditions.invisibilityOfElementLocated(backdropLocator));
            System.out.println("✅ Overlays handled successfully");
            
        } catch (Exception e) {
            System.out.println("ℹ️ No overlays found or couldn't handle them");
        }
    }

    // ======= FIXED GUARDIAN SELECTION =======

    public void selectRandomGuardian() {
        try {
            System.out.println("👤 Selecting random guardian...");
            
            // Handle any overlays first
            handleOverlays();
            
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(guardianDropdown));
            
            // Use JavaScript click to avoid overlay issues
            js.executeScript("arguments[0].click();", dropdown);
            System.out.println("✅ Guardian dropdown clicked");
            Thread.sleep(2000);

            List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(guardianOptions));
            
            if (options.isEmpty()) {
                throw new RuntimeException("No guardians available in dropdown!");
            }

            WebElement randomGuardian = options.get(random.nextInt(options.size()));
            String guardianName = randomGuardian.getText();
            
            // Use JavaScript click for selection too
            js.executeScript("arguments[0].click();", randomGuardian);
            System.out.println("✅ Selected Guardian: " + guardianName);
            Thread.sleep(3000);

        } catch (Exception e) {
            throw new RuntimeException("Failed to select guardian: " + e.getMessage());
        }
    }

    public void selectGuardianByName(String guardianName) {
        try {
            System.out.println("👤 Selecting guardian: " + guardianName);
            
            // Handle overlays
            handleOverlays();
            
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(guardianDropdown));
            js.executeScript("arguments[0].click();", dropdown);
            Thread.sleep(2000);

            List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(guardianOptions));
            
            boolean found = false;
            for (WebElement option : options) {
                if (option.getText().trim().contains(guardianName.trim())) {
                    js.executeScript("arguments[0].click();", option);
                    System.out.println("✅ Selected Guardian: " + guardianName);
                    found = true;
                    Thread.sleep(3000);
                    break;
                }
            }
            
            if (!found) {
                throw new RuntimeException("Guardian not found: " + guardianName);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to select guardian by name: " + e.getMessage());
        }
    }

    // ======= FIXED CHILD SELECTION =======


    
    public void selectRandomChild() {
        try {
            System.out.println("👶 Selecting random child...");
            
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(childrenDropdown));
            js.executeScript("arguments[0].click();", dropdown);
            System.out.println("✅ Child dropdown clicked");
            Thread.sleep(2000);

            List<WebElement> childOptions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(childrenOptions));
            
            if (childOptions.isEmpty()) {
                throw new RuntimeException("No children available for selected guardian!");
            }

            WebElement randomChild = childOptions.get(random.nextInt(childOptions.size()));
            String childName = randomChild.getText();
            
            js.executeScript("arguments[0].click();", randomChild);
            Thread.sleep(1000);
            randomChild.sendKeys(Keys.ESCAPE);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            Thread.sleep(1000);
            //driver.findElement(By.xpath("//body")).click();
           // Webelement=childEle=By.xpath("(//input[@type='number'])[1]").click(;)
            //WebElement body = driver.findElement(By.xpath("//body"));
            //body.click();
            System.out.println("✅ Selected Child: " + childName);
            Thread.sleep(2000);

        } catch (Exception e) {
            throw new RuntimeException("Failed to select child: " + e.getMessage());
        }
    }
//selectFirstChild
    public void selectGuardian(String guardianName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("? Selecting guardian: " + guardianName);

        WebElement guardianDropdown = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//mat-card//mat-form-field[.//mat-label[contains(text(),'Guardian')]]//input")
        ));

        js.executeScript("arguments[0].click();", guardianDropdown);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.xpath("//div[@class='cdk-overlay-pane']//mat-option")
        ));

        List<WebElement> guardians = driver.findElements(By.xpath("//div[@class='cdk-overlay-pane']//mat-option"));
        for (WebElement g : guardians) {
            if (g.getText().trim().equalsIgnoreCase(guardianName.trim())) {
                js.executeScript("arguments[0].click();", g);
                break;
            }
        }

        // Wait for overlays to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.xpath("//div[contains(@class,'cdk-overlay-backdrop') and contains(@class,'cdk-overlay-backdrop-showing')]")
        ));

        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Let UI refresh child list
        System.out.println("? Selected Guardian: " + guardianName);
    }








    // ======= FIXED CUSTOM AMOUNT SELECTION =======

    /**
     * CORRECT PUBLIC METHOD NAME: selectCustomAmount (not chooseCustomAmount)
     */
    public void selectCustomAmount() {
        try {
            System.out.println("💰 Selecting Custom Amount...");
            
            // Wait for radio button to be present
            WebElement customRadio = wait.until(ExpectedConditions.presenceOfElementLocated(customAmountRadio));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", customRadio);
            Thread.sleep(1000);
            
            // Check if already selected
            if (isCustomAmountSelected()) {
                System.out.println("✅ Custom Amount is already selected");
                return;
            }
            
            // Try multiple selection strategies
            boolean selected = false;
            
            // Strategy 1: JavaScript click on radio button
            if (!selected) {
                try {
                    js.executeScript("arguments[0].click();", customRadio);
                    System.out.println("✅ Clicked Custom Amount using JavaScript");
                    Thread.sleep(1000);
                    selected = isCustomAmountSelected();
                } catch (Exception e) {
                    System.out.println("⚠️ JavaScript click failed: " + e.getMessage());
                }
            }
            
            // Strategy 2: Click input element directly
            if (!selected) {
                try {
                    WebElement radioInputElement = driver.findElement(radioInput);
                    js.executeScript("arguments[0].click();", radioInputElement);
                    System.out.println("✅ Clicked radio input directly");
                    Thread.sleep(1000);
                    selected = isCustomAmountSelected();
                } catch (Exception e) {
                    System.out.println("⚠️ Radio input click failed: " + e.getMessage());
                }
            }
            
            // Strategy 3: Actions class
            if (!selected) {
                try {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(customRadio).click().perform();
                    System.out.println("✅ Clicked using Actions class");
                    Thread.sleep(1000);
                    selected = isCustomAmountSelected();
                } catch (Exception e) {
                    System.out.println("⚠️ Actions click failed: " + e.getMessage());
                }
            }
            
            // Strategy 4: Click by finding the exact element
            if (!selected) {
                try {
                    // Find all custom amount radio buttons and click the first one
                    List<WebElement> customRadios = driver.findElements(customAmountRadio);
                    if (!customRadios.isEmpty()) {
                        js.executeScript("arguments[0].click();", customRadios.get(0));
                        System.out.println("✅ Clicked first custom amount radio");
                        Thread.sleep(1000);
                        selected = isCustomAmountSelected();
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Multiple radios click failed: " + e.getMessage());
                }
            }
            
            // Final verification
            if (selected) {
                System.out.println("✅ Custom Amount selection verified successfully");
            } else {
                // Last resort: force select using advanced method
                forceSelectCustomAmount();
            }
            
            Thread.sleep(1000);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to select custom amount: " + e.getMessage());
        }
    }

    /**
     * EMERGENCY METHOD: Force select custom amount when normal methods fail
     */
    private void forceSelectCustomAmount() {
        try {
            System.out.println("🆘 FORCE Selecting Custom Amount...");
            
            // Try all possible approaches
            String[] scripts = {
                // Click by exact text
                "var elements = document.evaluate(\"//mat-radio-button[contains(.,'Custom Amount')]\", document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null); for (var i = 0; i < elements.snapshotLength; i++) { elements.snapshotItem(i).click(); }",
                
                // Click by input value
                "var inputs = document.querySelectorAll('input[type=\"radio\"][value*=\"custom\"], input[type=\"radio\"][value*=\"Custom\"]'); for (var i = 0; i < inputs.length; i++) { inputs[i].click(); }",
                
                // Click by label text
                "var spans = document.querySelectorAll('span'); for (var i = 0; i < spans.length; i++) { if (spans[i].textContent.includes('Custom Amount')) { spans[i].click(); break; } }",
                
                // Set checked property directly
                "var radios = document.querySelectorAll('mat-radio-button'); for (var i = 0; i < radios.length; i++) { if (radios[i].textContent.includes('Custom Amount')) { var input = radios[i].querySelector('input'); if (input) { input.checked = true; input.dispatchEvent(new Event('change', { bubbles: true })); } } }"
            };
            
            for (int i = 0; i < scripts.length; i++) {
                try {
                    js.executeScript(scripts[i]);
                    System.out.println("✅ Attempted force selection method " + (i + 1));
                    Thread.sleep(1000);
                    
                    if (isCustomAmountSelected()) {
                        System.out.println("✅ Custom Amount force-selected successfully!");
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("❌ Force method " + (i + 1) + " failed: " + e.getMessage());
                }
            }
            
            throw new RuntimeException("Could not force select Custom Amount");
            
        } catch (Exception e) {
            throw new RuntimeException("Force selection failed: " + e.getMessage());
        }
    }

    /**
     * Check if custom amount is selected - PRIVATE HELPER
     */
    //isCustomAmountSelected
    
    private boolean isCustomAmountSelected() {
        try {
            // Method 1: Check visually selected radios
            List<WebElement> checkedRadios = driver.findElements(radioChecked);
            if (!checkedRadios.isEmpty()) {
                for (WebElement radio : checkedRadios) {
                    if (radio.getText().contains("Custom Amount")) {
                        System.out.println("🔍 Custom Amount is visually selected");
                        return true;
                    }
                }
            }
            
            
            // Method 2: Check input checked property
            try {
                WebElement radioInputElement = driver.findElement(radioInput);
                boolean isChecked = (Boolean) js.executeScript("return arguments[0].checked;", radioInputElement);
                if (isChecked) {
                    System.out.println("🔍 Custom Amount input is checked");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("⚠️ Could not check input element");
            }
            
            // Method 3: Check aria-checked attribute
            try {
                WebElement customRadio = driver.findElement(customAmountRadio);
                String ariaChecked = customRadio.getAttribute("aria-checked");
                if ("true".equals(ariaChecked)) {
                    System.out.println("🔍 Custom Amount has aria-checked=true");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("⚠️ Could not check aria-checked");
            }
            
            return false;
            
        } catch (Exception e) {
            System.out.println("❌ Error checking radio state: " + e.getMessage());
            return false;
        }
    }

    // ======= AMOUNT ENTRY METHODS =======

    public void enterRandomAmount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            System.out.println("? Entering random amount...");

            WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                //By.xpath("//input[@formcontrolname='amount' or contains(@placeholder,'Amount')]")
                By.xpath("(//input[@type='number'])[1]")

            ));

            int amount = new Random().nextInt(500) + 100; // 100–600 range
            amountField.clear();
            amountField.sendKeys(String.valueOf(amount));
            js.executeScript("arguments[0].blur();", amountField);

            System.out.println("? Entered amount: " + amount);

        } catch (TimeoutException e) {
            throw new AssertionError("? Failed to enter random amount: " + e.getMessage());
        }
    }

    // ======= PROCEED BUTTON =======
    //clickProceedButton
    public void clickProceedButton() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            System.out.println("?? Clicking proceed button...");

            // Wait until overlay is gone
            List<WebElement> overlays = driver.findElements(By.cssSelector(".cdk-overlay-backdrop.cdk-overlay-backdrop-showing"));
            if (!overlays.isEmpty()) {
                System.out.println("⚠️ Waiting for overlay to disappear before clicking proceed...");
                wait.until(ExpectedConditions.invisibilityOfAllElements(overlays));
            }

            WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(.,'Proceed') or contains(.,'Pay')]")));

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", proceedButton);
            js.executeScript("arguments[0].click();", proceedButton);
            System.out.println("? Proceed button clicked successfully.");

        } catch (Exception e) {
            System.out.println("❌ Failed to click proceed button: " + e.getMessage());
            throw new AssertionError("? Specific guardian test failed: " + e.getMessage());
        }
    }


    // ======= UTILITY METHODS =======

    public void clearGuardianSelection() {
        try {
            WebElement guardianInput = driver.findElement(guardianDropdown);
            guardianInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            guardianInput.sendKeys(Keys.DELETE);
            System.out.println("✅ Guardian selection cleared");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("⚠️ Could not clear guardian: " + e.getMessage());
        }
    }

    /**
     * Debug method to check radio button states
     */
    public void debugRadioButtons() {
        try {
            System.out.println("=== RADIO BUTTON DEBUG ===");
            
            // Check Custom Amount
            try {
                WebElement customRadio = driver.findElement(customAmountRadio);
                System.out.println("📻 Custom Amount Radio:");
                System.out.println("   - Displayed: " + customRadio.isDisplayed());
                System.out.println("   - Enabled: " + customRadio.isEnabled());
                System.out.println("   - Text: " + customRadio.getText());
                System.out.println("   - Class: " + customRadio.getAttribute("class"));
                System.out.println("   - aria-checked: " + customRadio.getAttribute("aria-checked"));
            } catch (Exception e) {
                System.out.println("❌ Custom Amount radio not found");
            }
            
            // Check Full Amount
            try {
                WebElement fullRadio = driver.findElement(fullAmountRadio);
                System.out.println("📻 Full Amount Radio:");
                System.out.println("   - Displayed: " + fullRadio.isDisplayed());
                System.out.println("   - Enabled: " + fullRadio.isEnabled());
                System.out.println("   - Text: " + fullRadio.getText());
                System.out.println("   - Class: " + fullRadio.getAttribute("class"));
                System.out.println("   - aria-checked: " + fullRadio.getAttribute("aria-checked"));
            } catch (Exception e) {
                System.out.println("❌ Full Amount radio not found");
            }
            
            // Check all checked radios
            List<WebElement> checked = driver.findElements(radioChecked);
            System.out.println("✅ Currently checked radios: " + checked.size());
            for (WebElement radio : checked) {
                System.out.println("   - " + radio.getText());
            }
            
        } catch (Exception e) {
            System.out.println("❌ Radio debug failed: " + e.getMessage());
        }
    }
    public void clickbackButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(ProccedpaymentbackButton));
        button.click();
        System.out.println("Clicked on Pay Securely button");
    }
}