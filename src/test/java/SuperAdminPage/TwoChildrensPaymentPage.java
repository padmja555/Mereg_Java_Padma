
package SuperAdminPage;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;

public class TwoChildrensPaymentPage {
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
    
    // Amount field
    private By amountField = By.xpath("//input[@formcontrolname='amount' or contains(@placeholder,'Amount')]");
    private By proceedButton = By.xpath("//button[contains(.,'Proceed') or contains(.,'Pay')]");

    public TwoChildrensPaymentPage(WebDriver driver) {
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
    public void clickOnGurdian() {
        // Open the guardian dropdown
        WebElement guardianBox = wait.until(ExpectedConditions.elementToBeClickable(guardianDropdown));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", guardianBox);
        guardianBox.click();

        // Wait for dropdown options to appear
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(guardianOptions));

        // Print available guardian names
        System.out.println("Available guardians:");
        for (WebElement option : options) {
            System.out.println("- " + option.getText().trim());
        }

        // Select second guardian if available
        if (options.size() >= 40) {
            WebElement secondGuardian = options.get(39); // index 1 = second option
            System.out.println("Selecting second guardian: " + secondGuardian.getText().trim());
            secondGuardian.click();
        } else if (options.size() == 40) {
            System.out.println("Only one guardian available. Selecting: " + options.get(0).getText().trim());
            options.get(38).click();
        } else {
            System.out.println("No guardians available to select!");
        }
    }
    public void clickOnGuardianByIndex(int index) {
        try {
            // 1️⃣ Open the guardian dropdown
            WebElement guardianBox = wait.until(ExpectedConditions.elementToBeClickable(guardianDropdown));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", guardianBox);
            guardianBox.click();

            // 2️⃣ Wait until options are visible
            List<WebElement> options = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(guardianOptions)
            );

            // 3️⃣ Print available guardians for debugging
            System.out.println("Total guardians found: " + options.size());
            for (int i = 0; i < options.size(); i++) {
                System.out.println(i + " → " + options.get(i).getText().trim());
            }

            // 4️⃣ Validate index
            if (options.isEmpty()) {
                System.out.println("❌ No guardians available to select!");
                return;
            }
            if (index < 0 || index >= options.size()) {
                System.out.println("⚠️ Invalid index: " + index + ". Selecting first guardian instead.");
                index = 0;
            }

            // 5️⃣ Select guardian by index
            WebElement guardianToSelect = options.get(index);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", guardianToSelect);
            String guardianName = guardianToSelect.getText().trim();

            guardianToSelect.click();
            System.out.println("✅ Selected guardian by index [" + index + "]: " + guardianName);

        } catch (Exception e) {
            System.out.println("⚠️ Exception while selecting guardian by index: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void selectGuardian1(String guardianName) {
        try {
            // Open guardian dropdown
            WebElement guardianBox = wait.until(ExpectedConditions.elementToBeClickable(guardianDropdown));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", guardianBox);
            guardianBox.click();

            // Wait for dropdown options to appear
            List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(guardianOptions));

            System.out.println("Available guardians:");
            for (WebElement option : options) {
                System.out.println("- " + option.getText().trim());
            }

            if (options.isEmpty()) {
                throw new RuntimeException("No guardians available to select!");
            }

            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement selectedGuardian = null;

            // Try to find guardian by name
            for (WebElement option : options) {
                if (option.getText().trim().equalsIgnoreCase(guardianName)) {
                    selectedGuardian = option;
                    break;
                }
            }

            if (selectedGuardian != null) {
                System.out.println("✅ Selecting guardian: " + guardianName);
                js.executeScript("arguments[0].click();", selectedGuardian);
            } else {
                // Fallback logic if guardian name not found
                WebElement fallbackGuardian = options.get(options.size() - 1); // select last available
                System.out.println("⚠️ Guardian '" + guardianName + "' not found. Selecting fallback guardian: "
                                    + fallbackGuardian.getText().trim());
                js.executeScript("arguments[0].click();", fallbackGuardian);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to select guardian '" + guardianName + "': " + e.getMessage(), e);
        }
    }


    /*
    public void selectGuardianAndChildren() {
        try {
            // --- Select Guardian ---
            WebElement guardianBox = wait.until(ExpectedConditions.elementToBeClickable(guardianDropdown));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", guardianBox);
            guardianBox.click();

            // Wait for guardian options to appear
            List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(guardianOptions));
            System.out.println("Available guardians:");
            for (WebElement option : options) {
                System.out.println("- " + option.getText().trim());
            }

            // Select second guardian if exists, otherwise first
            WebElement selectedGuardian;
            if (options.size() >= 2) {
                selectedGuardian = options.get(1);
                System.out.println("Selecting second guardian: " + selectedGuardian.getText().trim());
            } else if (!options.isEmpty()) {
                selectedGuardian = options.get(0);
                System.out.println("Only one guardian available. Selecting: " + selectedGuardian.getText().trim());
            } else {
                throw new RuntimeException("No guardians available!");
            }
            selectedGuardian.click();

            // --- Select Children for the selected guardian ---
            WebElement childDropdown = wait.until(ExpectedConditions.elementToBeClickable(childrenDropdown));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", childDropdown);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", childDropdown);

            // Wait for child options in overlay using FluentWait
            FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(15))
                    .pollingEvery(Duration.ofMillis(200))
                    .ignoring(NoSuchElementException.class);

            List<WebElement> childOptions = fluentWait.until(driver -> {
                List<WebElement> optionsList = driver.findElements(By.cssSelector("div.cdk-overlay-pane mat-option"));
                return optionsList.size() > 0 ? optionsList : null;
            });

            if (childOptions.isEmpty()) {
                throw new RuntimeException("Selected guardian has no children!");
            }

            // Select up to 2 random children
            int numToSelect = Math.min(2, childOptions.size());
            Set<Integer> indices = new HashSet<>();
            Random random = new Random();
            while (indices.size() < numToSelect) {
                indices.add(random.nextInt(childOptions.size()));
            }

            List<String> selectedChildren = new ArrayList<>();
            for (int index : indices) {
                WebElement child = childOptions.get(index);
                String name = child.getText().trim();
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", child);
                selectedChildren.add(name);
            }

            // Close the child dropdown
            childDropdown.sendKeys(Keys.ESCAPE);

            System.out.println("✅ Selected Children for " + selectedGuardian.getText().trim() + ": " +
                    String.join(", ", selectedChildren));

        } catch (Exception e) {
            throw new RuntimeException("Failed to select guardian and children: " + e.getMessage(), e);
        }
    }
    */


    // ======= FIXED GUARDIAN SELECTION =======
/*
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


  
    //selectRandomChild
     * 
     */
    
     /*
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
    /*
     * 
     */
//selectFirstChild
    public void selectTwoChildren() {
        try {
            //log.info("?? Selecting two children for payment...");

            // Find and click the child dropdown
            WebElement childDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//mat-select[contains(@class,'mat-select')]")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", childDropdown);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", childDropdown);

            // Wait for at least 2 options in the overlay
            FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(15))
                    .pollingEvery(Duration.ofMillis(250))
                    .ignoring(NoSuchElementException.class);

            List<WebElement> childOptions = fluentWait.until(driver -> {
                List<WebElement> options = driver.findElements(By.cssSelector("div.cdk-overlay-pane mat-option"));
                return options.size() >= 2 ? options : null;
            });

            //log.info("? Found " + childOptions.size() + " children, selecting first two...");

            // Select first two children
            for (int i = 0; i < 2; i++) {
                WebElement child = childOptions.get(i);
                String name = child.getText().trim();
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", child);
                //log.info("? Selected child " + (i + 1) + ": " + name);
            }

            // Close dropdown
            childDropdown.sendKeys(Keys.ESCAPE);
            //log.info("? Two children selection completed successfully");

        } catch (Exception e) {
            //log.error("? Failed to select two children: " + e.getMessage());
            throw new RuntimeException("Two children selection failed", e);
        }
    }

    public void selectFirstChildrens() {
        try {
            System.out.println("Selecting up to two random children...");

            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(childrenDropdown));
            js.executeScript("arguments[0].click();", dropdown);

            List<WebElement> childOptions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(childrenOptions));

            if (childOptions.isEmpty()) {
                throw new RuntimeException("No children available for selected guardian!");
            }

            int numToSelect = Math.min(2, childOptions.size()); // select 1 or 2 children
            Set<Integer> indices = new HashSet<>();
            Random random = new Random();
            while (indices.size() < numToSelect) {
                indices.add(random.nextInt(childOptions.size()));
            }

            List<String> selectedChildren = new ArrayList<>();
            for (int index : indices) {
                WebElement child = childOptions.get(index);
                String name = child.getText();
                js.executeScript("arguments[0].click();", child);
                selectedChildren.add(name);
            }

            // Close dropdown using ESC
            dropdown.sendKeys(Keys.ESCAPE);

            System.out.println("Selected Children: " + String.join(", ", selectedChildren));

        } catch (Exception e) {
            throw new RuntimeException("Failed to select children: " + e.getMessage(), e);
        }
    }

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
    //changecustomamount::::::::
   
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
    /*
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
/*
    public void handleCustomAmountAfterRefresh() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Random random = new Random();

        try {
            System.out.println("🔄 Page refreshed — verifying selections...");

            // ✅ Wait until guardian and children selections are visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//mat-card//mat-form-field//input[contains(@placeholder,'Guardian') or contains(@aria-label,'Guardian')]")
            ));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//mat-card//mat-form-field//input[contains(@placeholder,'Child') or contains(@aria-label,'Child')]")
            ));

            System.out.println("✅ Guardian and child selections loaded.");

            // ✅ Wait for the Custom Amount radio button or label
            WebElement customAmountOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(),'Custom Amount') or contains(.,'Custom')]")
            ));

            // ✅ Select Custom Amount if not already selected
            boolean isSelected = (boolean) js.executeScript(
                "return arguments[0].closest('mat-radio-button')?.classList.contains('mat-radio-checked');",
                customAmountOption
            );

            if (!isSelected) {
                customAmountOption.click();
                System.out.println("🎯 Selected Custom Amount option.");
                Thread.sleep(1000);
            }

            // ✅ Locate the amount input box
            WebElement amountBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//input[@type='number'])[1]")
            ));

            // ✅ Check if field is empty or zero
            String currentValue = (String) js.executeScript("return arguments[0].value;", amountBox);
            if (currentValue == null || currentValue.trim().isEmpty() || currentValue.equals("0")) {
                System.out.println("⚠️ Amount field is zero or blank after refresh — re-entering...");

                // Clear existing value
                js.executeScript("arguments[0].value = '';", amountBox);
                Thread.sleep(500);

                // Generate random amount (100–600)
                int randomAmount = random.nextInt(500) + 100;

                // Enter random amount with all Angular event triggers
                String script =
                    "var element = arguments[0];" +
                    "var value = arguments[1];" +
                    "element.value = value;" +
                    "element.dispatchEvent(new Event('input', { bubbles: true }));" +
                    "element.dispatchEvent(new Event('change', { bubbles: true }));" +
                    "element.dispatchEvent(new Event('blur', { bubbles: true }));" +
                    "if (element.ngModel) element.ngModel.$setViewValue(value);" +
                    "if (element.ngChange) element.ngChange();";

                js.executeScript(script, amountBox, String.valueOf(randomAmount));
                Thread.sleep(1000);

                // Verify again
                String verifiedValue = (String) js.executeScript("return arguments[0].value;", amountBox);
                if (verifiedValue == null || verifiedValue.trim().isEmpty() || verifiedValue.equals("0")) {
                    throw new AssertionError("❌ Amount still zero after retry — check page state or selection logic.");
                }

                System.out.println("✅ Random amount re-entered successfully: $" + verifiedValue);
            } else {
                System.out.println("✅ Amount already set correctly: $" + currentValue);
            }

        } catch (TimeoutException e) {
            throw new AssertionError("❌ Timeout waiting for page elements: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("⚠️ Error handling custom amount after refresh: " + e.getMessage());
            e.printStackTrace();
        }
    }
*/
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
}
