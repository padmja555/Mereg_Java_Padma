package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class BankpaymentValidationPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // Locators for the payment form on the main page
    private final By currencyButton = By.xpath("(//span[@class='CurrencyAmount'])[2]");
    private final By emailInput = By.xpath("//input[@autocomplete='email']");
    private final By usBankAccountRadio = By.xpath("//button[@data-testid='us_bank_account-accordion-item-button']");
    private final By fullNameInput = By.xpath("//input[@id=\"billingName\"]");
    ////button[1]//div[1]//div[1]//div[1]//img[1]
    private final By testInstitutionButton = By.xpath("//div[contains(text(), 'Test Institution')]");
    //private final By testInstitutionButton = By.xpath("//img[contains(@src, 'testmodeGreenBank')]");
    ////img[contains(@src, 'testmodeGreenBank')]

    private final By payButton = By.xpath("//button[contains(., 'Pay')]");
    private final By stripeConnectAccountButton = By.xpath("//button[contains(., 'Connect account')]");
    private final By stripeBackToSandboxButton = By.xpath("//button[contains(., 'Back to MeReg-main sandbox')]");
    private final By stripeSuccessMessage = By.xpath("//*[contains(text(), 'Success')]");

    public BankpaymentValidationPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.js = (JavascriptExecutor) driver;
    }

    public void clickCurrencyButton() {
        wait.until(ExpectedConditions.elementToBeClickable(currencyButton)).click();
        System.out.println("Clicked on the currency button ($1029).");
    }

    public void enterEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        emailField.clear();
        emailField.sendKeys(email);
        System.out.println("Entered email: " + email);
    }
    
    public void selectUsBankAccount() throws Exception {
        try {
            WebElement usBankAccount = wait.until(ExpectedConditions.presenceOfElementLocated(usBankAccountRadio));
            js.executeScript("arguments[0].click();", usBankAccount);
            System.out.println("Selected US Bank Account using JavaScript click.");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Failed to select US Bank Account: " + e.getMessage());
            throw e;
        }
    }

    public void enterFullName(String fullName) {
        try {
            WebElement fullNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(fullNameInput));
            fullNameField.clear();
            fullNameField.sendKeys(fullName);
            System.out.println("Entered full name: " + fullName);
        } catch (Exception e) {
            System.out.println("Failed to enter full name: " + e.getMessage());
            throw e;
        }
    }
    
    
    public void selectTestInstitution() throws Exception {
        try {
            WebElement testInstitution = wait.until(ExpectedConditions.elementToBeClickable(testInstitutionButton));
            js.executeScript("arguments[0].click();", testInstitution);
            System.out.println("Selected 'Test Institution' using JavaScript click.");
            
            // Wait for modal to load
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("Failed to select Test Institution: " + e.getMessage());
            throw e;
        }
    }

    // Add the debugIframes method
    public void debugIframes() {
        try {
            List<WebElement> allIframes = driver.findElements(By.tagName("iframe"));
            System.out.println("Total iframes found: " + allIframes.size());
            
            for (int i = 0; i < allIframes.size(); i++) {
                try {
                    WebElement iframe = allIframes.get(i);
                    String name = iframe.getAttribute("name");
                    String id = iframe.getAttribute("id");
                    String title = iframe.getAttribute("title");
                    String src = iframe.getAttribute("src");
                    String dataTestid = iframe.getAttribute("data-testid");
                    String className = iframe.getAttribute("class");
                    
                    System.out.println("Iframe " + i + ":");
                    System.out.println("  name: '" + name + "'");
                    System.out.println("  id: '" + id + "'");
                    System.out.println("  title: '" + title + "'");
                    System.out.println("  class: '" + className + "'");
                    System.out.println("  data-testid: '" + dataTestid + "'");
                    if (src != null) {
                        System.out.println("  src: '" + src.substring(0, Math.min(100, src.length())) + "'");
                    } else {
                        System.out.println("  src: null");
                    }
                    System.out.println("  displayed: " + iframe.isDisplayed());
                    System.out.println("  enabled: " + iframe.isEnabled());
                    System.out.println("-----------------------------------");
                } catch (Exception e) {
                    System.out.println("Could not get attributes for iframe " + i + ": " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Error in debugIframes: " + e.getMessage());
        }
    }

    // Add the switchToStripeIframe method
    public void switchToStripeIframe() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // First, let's see what iframes are available
            debugIframes();
            
            // Wait a bit for the iframe content to load
            Thread.sleep(3000);
            
            // Try the specific iframe that's displayed and looks like the main Stripe iframe
            // Iframe 0: name='__privateStripeFrame3556' - this one is displayed: true
            try {
                WebElement stripeFrame = driver.findElement(By.cssSelector("iframe[name^='__privateStripeFrame']"));
                driver.switchTo().frame(stripeFrame);
                System.out.println("Switched to main Stripe iframe: " + stripeFrame.getAttribute("name"));
                
                // Wait for elements to load inside the iframe
                Thread.sleep(2000);
                
                // Check if we can find any Stripe elements
                List<WebElement> stripeElements = driver.findElements(
                    By.cssSelector("input, button, [data-*], [data-elements-stable-field-name]"));
                
                System.out.println("Found " + stripeElements.size() + " elements in the Stripe iframe");
                
                if (!stripeElements.isEmpty()) {
                    // Log what elements we found for debugging
                    for (int i = 0; i < Math.min(stripeElements.size(), 5); i++) {
                        WebElement element = stripeElements.get(i);
                        System.out.println("Element " + i + ": tag=" + element.getTagName() + 
                                         ", id=" + element.getAttribute("id") +
                                         ", name=" + element.getAttribute("name"));
                    }
                    return;
                }
                
            } catch (Exception e) {
                System.out.println("Error with main Stripe iframe: " + e.getMessage());
                driver.switchTo().defaultContent();
            }
            
            // If that didn't work, try other approaches
            String[] iframePatterns = {
                "iframe[name^='__privateStripeFrame']",
                "iframe[title*='Secure']",
                "iframe[src*='linked-accounts']",
                "iframe[src*='elements-inner']"
            };
            
            for (String pattern : iframePatterns) {
                try {
                    System.out.println("Trying pattern: " + pattern);
                    List<WebElement> iframes = driver.findElements(By.cssSelector(pattern));
                    System.out.println("Found " + iframes.size() + " iframes with pattern: " + pattern);
                    
                    for (WebElement iframe : iframes) {
                        try {
                            // Try to switch to this iframe
                            driver.switchTo().frame(iframe);
                            System.out.println("Successfully switched to iframe: " + iframe.getAttribute("name"));
                            
                            // Wait for content to load
                            Thread.sleep(2000);
                            
                            // Check if we can find bank account elements
                            List<WebElement> accountFields = driver.findElements(
                                By.cssSelector("input[type='text'], input[type='number'], [autocomplete*='account']"));
                            
                            System.out.println("Found " + accountFields.size() + " input fields in this iframe");
                            
                            if (!accountFields.isEmpty()) {
                                // Log what we found
                                for (WebElement field : accountFields) {
                                    System.out.println("Field: " + field.getAttribute("autocomplete") + 
                                                     ", placeholder: " + field.getAttribute("placeholder"));
                                }
                                return;
                            } else {
                                System.out.println("No account fields found in this iframe, switching back");
                                driver.switchTo().defaultContent();
                            }
                        } catch (Exception e) {
                            System.out.println("Error switching to iframe: " + e.getMessage());
                            driver.switchTo().defaultContent();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error with pattern " + pattern + ": " + e.getMessage());
                }
            }
            
            throw new NoSuchElementException("No Stripe iframes with bank account fields found");
            
        } catch (Exception e) {
            System.out.println("Error in switchToStripeIframe: " + e.getMessage());
            driver.switchTo().defaultContent();
            throw e;
        }
    }
    
    

    // Updated handleStripeModals method

    
    public void handleStripeModals() {
        try {
            System.out.println("=== Starting Stripe modal handling ===");
            
            // Wait a bit for the modal to load completely
            Thread.sleep(5000);
            
            // Switch to Stripe iframe
            switchToStripeIframe();
            
            // Now try to interact with the elements with more flexible selectors
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Try to find account number field with various selectors
            String[] accountNumberSelectors = {
                "input[data-elements-stable-field-name='accountNumber']",
                "input[autocomplete='account-number']",
                "input[placeholder*='account']",
                "input[type='text']",
                "input[type='number']"
            };
            
            boolean accountNumberEntered = false;
            for (String selector : accountNumberSelectors) {
                try {
                    List<WebElement> fields = driver.findElements(By.cssSelector(selector));
                    if (!fields.isEmpty()) {
                        WebElement accountField = fields.get(0);
                        accountField.clear();
                        accountField.sendKeys("000123456789");
                        System.out.println("Entered account number using selector: " + selector);
                        accountNumberEntered = true;
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Failed with selector " + selector + ": " + e.getMessage());
                }
            }
            
            if (!accountNumberEntered) {
                throw new RuntimeException("Could not find account number field");
            }
            
            // Try to find routing number field
            String[] routingNumberSelectors = {
                "input[data-elements-stable-field-name='routingNumber']",
                "input[autocomplete='routing-number']",
                "input[placeholder*='routing']",
                "input[type='text']",
                "input[type='number']"
            };
            
            boolean routingNumberEntered = false;
            for (String selector : routingNumberSelectors) {
                try {
                    List<WebElement> fields = driver.findElements(By.cssSelector(selector));
                    for (WebElement field : fields) {
                        // Skip if this is the account number field we already filled
                        if (!field.getAttribute("value").equals("000123456789")) {
                            field.clear();
                            field.sendKeys("110000000");
                            System.out.println("Entered routing number using selector: " + selector);
                            routingNumberEntered = true;
                            break;
                        }
                    }
                    if (routingNumberEntered) break;
                } catch (Exception e) {
                    System.out.println("Failed with selector " + selector + ": " + e.getMessage());
                }
            }
            
            if (!routingNumberEntered) {
                throw new RuntimeException("Could not find routing number field");
            }
            
            // Try to find continue button
            String[] buttonSelectors = {
                "button[data-testid='bank-account-continue-button']",
                "button:contains('Continue')",
                "button:contains('continue')",
                "button[type='submit']",
                "button"
            };
            
            boolean buttonClicked = false;
            for (String selector : buttonSelectors) {
                try {
                    List<WebElement> buttons = driver.findElements(By.cssSelector(selector));
                    for (WebElement button : buttons) {
                        if (button.isDisplayed() && button.isEnabled()) {
                            button.click();
                            System.out.println("Clicked button using selector: " + selector);
                            buttonClicked = true;
                            break;
                        }
                    }
                    if (buttonClicked) break;
                } catch (Exception e) {
                    System.out.println("Failed with selector " + selector + ": " + e.getMessage());
                }
            }
            
            if (!buttonClicked) {
                throw new RuntimeException("Could not find continue button");
            }
            
        } catch (Exception e) {
            System.out.println("Failed to handle Stripe modals: " + e.getMessage());
            throw new RuntimeException("Stripe modal handling failed", e);
        } finally {
            // Always switch back to main content
            try {
                driver.switchTo().defaultContent();
                System.out.println("Switched back to main content");
            } catch (Exception e) {
                System.out.println("Error switching back to main content: " + e.getMessage());
            }
        }
    }
 // Method to handle Stripe authentication
    private void handleStripeAuthentication() {
        try {
            System.out.println("=== Handling Stripe Authentication ===");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Wait for Stripe iframe to load
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("iframe[name^='__privateStripeFrame']")
            ));
            
            // Switch to Stripe iframe
            WebElement stripeIframe = driver.findElement(
                By.cssSelector("iframe[name^='__privateStripeFrame']")
            );
            driver.switchTo().frame(stripeIframe);
            System.out.println("Switched to Stripe iframe: " + stripeIframe.getAttribute("name"));
            
            // Handle the authentication steps inside the iframe
            handleStripeIframeContent();
            
            // Switch back to main content
            driver.switchTo().defaultContent();
            System.out.println("Switched back to main content");
            
        } catch (Exception e) {
            System.out.println("Error in Stripe authentication: " + e.getMessage());
            driver.switchTo().defaultContent();
            // Don't throw exception, continue with payment
        }
    }

    // Method to handle content inside Stripe iframe
    private void handleStripeIframeContent() {
        try {
            System.out.println("=== Handling Stripe Iframe Content ===");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Based on your screenshot, look for "Test OAuth Institution" and click it
            try {
                WebElement testInstitution = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[contains(text(), 'Test OAuth Institution') or contains(text(), 'Test Institution')]")
                ));
                
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", testInstitution);
                System.out.println("Clicked on Test Institution");
                Thread.sleep(2000);
                
            } catch (Exception e) {
                System.out.println("Test Institution not found: " + e.getMessage());
            }
            
            // Look for and click "Agree and continue" if present
            try {
                WebElement agreeButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[.//span[normalize-space()='Agree and continue']]")
                ));
                
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", agreeButton);
                System.out.println("Clicked 'Agree and continue'");
                Thread.sleep(2000);
                
            } catch (Exception e) {
                System.out.println("'Agree and continue' not found: " + e.getMessage());
            }
            
            // Look for and click "Connect account" if present
            try {
                WebElement connectButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[.//span[contains(text(), 'Connect account')]]")
                ));
                
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", connectButton);
                System.out.println("Clicked 'Connect account'");
                Thread.sleep(2000);
                
            } catch (Exception e) {
                System.out.println("'Connect account' not found: " + e.getMessage());
            }
            
            // Wait for the connection to complete and iframe to close
            Thread.sleep(3000);
            
        } catch (Exception e) {
            System.out.println("Error handling iframe content: " + e.getMessage());
        }
    }

    // Method to ensure all Stripe iframes are closed
    private void ensureStripeIframesClosed() {
        try {
            System.out.println("=== Ensuring Stripe Iframes Are Closed ===");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Wait for Stripe iframes to be removed or hidden
            try {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("iframe[name^='__privateStripeFrame']")
                ));
                System.out.println("Stripe iframes are now closed/invisible");
            } catch (Exception e) {
                System.out.println("Stripe iframes may still be present: " + e.getMessage());
                
                // If iframes are still present, try to handle them
                handleRemainingStripeIframes();
            }
            
            // Additional wait to ensure page is stable
            Thread.sleep(2000);
            
        } catch (Exception e) {
            System.out.println("Error ensuring iframes closed: " + e.getMessage());
        }
    }

    // Method to handle any remaining Stripe iframes
    private void handleRemainingStripeIframes() {
        try {
            System.out.println("=== Handling Remaining Stripe Iframes ===");
            
            List<WebElement> stripeIframes = driver.findElements(
                By.cssSelector("iframe[name^='__privateStripeFrame']")
            );
            
            if (!stripeIframes.isEmpty()) {
                System.out.println("Found " + stripeIframes.size() + " remaining Stripe iframes");
                
                for (WebElement iframe : stripeIframes) {
                    try {
                        // Switch to iframe and check for close buttons
                        driver.switchTo().frame(iframe);
                        
                        // Look for close buttons or back buttons
                        String[] closeButtonSelectors = {
                            "button[aria-label*='close']",
                            "button[data-testid*='close']",
                            "button:contains('Close')",
                            "button:contains('Back')",
                            "button:contains('Cancel')"
                        };
                        
                        for (String selector : closeButtonSelectors) {
                            try {
                                List<WebElement> closeButtons = driver.findElements(By.cssSelector(selector));
                                if (!closeButtons.isEmpty()) {
                                    WebElement closeButton = closeButtons.get(0);
                                    if (closeButton.isDisplayed()) {
                                        JavascriptExecutor js = (JavascriptExecutor) driver;
                                        js.executeScript("arguments[0].click();", closeButton);
                                        System.out.println("Clicked close button in remaining iframe");
                                        Thread.sleep(1000);
                                        break;
                                    }
                                }
                            } catch (Exception e) {
                                // Continue to next selector
                            }
                        }
                        
                    } catch (Exception e) {
                        System.out.println("Error handling remaining iframe: " + e.getMessage());
                    } finally {
                        driver.switchTo().defaultContent();
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println("Error handling remaining iframes: " + e.getMessage());
        }
    }

    /*
    public void clickPayButton() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            driver.switchTo().defaultContent();

            WebElement payBtn = wait.until(ExpectedConditions.elementToBeClickable(payButton));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", payBtn);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", payBtn);

            System.out.println("✅ Clicked Pay button");
        } catch (Exception e) {
            System.out.println("❌ Failed to click Pay button: " + e.getMessage());
            throw e;
        }
    }
*/


    // Helper method to handle overlapping elements
    private void handleOverlappingElements() {
        try {
            // Check for any visible Stripe iframes that might be overlapping
            List<WebElement> visibleIframes = driver.findElements(
                By.cssSelector("iframe[name^='__privateStripeFrame']")
            );
            
            for (WebElement iframe : visibleIframes) {
                try {
                    // Try to make iframes invisible using JavaScript
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].style.visibility = 'hidden';", iframe);
                    System.out.println("Hidden overlapping iframe");
                } catch (Exception e) {
                    System.out.println("Could not hide iframe: " + e.getMessage());
                }
            }
            
            Thread.sleep(1000);
            
        } catch (Exception e) {
            System.out.println("Error handling overlapping elements: " + e.getMessage());
        }
    }
    
/*
    public boolean verifyPaymentSuccess() {
        try {
            By successMessage = By.xpath("//*[contains(text(), 'success') or contains(text(), 'Success') or contains(text(), 'confirmed') or contains(text(), 'Thank you')]");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    */
    private void handleStripeBankAccountAuthentication() {
        try {
            System.out.println("=== Handling Stripe Bank Account Authentication ===");

            WebDriverWait stripeWait = new WebDriverWait(driver, Duration.ofSeconds(30));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // 1. Wait for iframe, switch into it
            stripeWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                By.cssSelector("iframe[name^='__privateStripeFrame']")
            ));
            System.out.println("✅ Switched to Stripe iframe");

            // 2. Click "Agree and continue" (if present)
            try {
                WebElement agreeBtn = stripeWait.until(
                    ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[.//span[normalize-space()='Agree and continue']]")
                    )
                );
                js.executeScript("arguments[0].click();", agreeBtn);
                System.out.println("✅ Clicked 'Agree and continue'");
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("⚠️ 'Agree and continue' missing: " + e.getMessage());
            }

            // 3. Click "Connect account"
            try {
                WebElement connectBtn = stripeWait.until(
                    ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[.//span[contains(text(), 'Connect account')]]")
                    )
                );
                js.executeScript("arguments[0].click();", connectBtn);
                System.out.println("✅ Clicked 'Connect account'");
                Thread.sleep(3000);
            } catch (Exception e) {
                System.out.println("⚠️ 'Connect account' missing: " + e.getMessage());
            }

            // 4. Wait for success and click Back button - WITH IMPROVED LOCATOR
            try {
                // Wait for success message
                WebElement successText = stripeWait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(), 'Your account was connected')]")
                    )
                );
                System.out.println("✅ Success screen displayed");
                
                // Wait longer for the back button to be fully interactive
                Thread.sleep(3000);
                
                // Try multiple locator strategies for the back button
                WebElement backButton = null;
                try {
                    // Strategy 1: Look for span containing the text
                    backButton = stripeWait.until(
                        ExpectedConditions.elementToBeClickable(
                            By.xpath("//span[contains(text(), 'Back to Mereg-main sandbox') or contains(text(), 'Back to Merge Main')]")
                        )
                    );
                } catch (Exception e) {
                    // Strategy 2: Look for button containing the text
                    backButton = stripeWait.until(
                        ExpectedConditions.elementToBeClickable(
                            
                        		By.xpath("//button[contains(., 'Back to Mereg-main sandbox') or contains(., 'Back to Merge Main')]")
                        )
                    );
                }
                
                // Scroll and click with multiple attempts if needed
                int attempts = 0;
                while (attempts < 3) {
                    try {
                        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", backButton);
                        Thread.sleep(1000);
                        js.executeScript("arguments[0].click();", backButton);
                        System.out.println("✅ Clicked 'Back to Mereg-main sandbox'");
                        break;
                    } catch (Exception e) {
                        attempts++;
                        if (attempts == 3) throw e;
                        Thread.sleep(1000);
                    }
                }
                
                Thread.sleep(3000);
            } catch (Exception e) {
                System.out.println("❌ Failed to click back button: " + e.getMessage());
                // Try alternative approach - switch to default content and proceed
                driver.switchTo().defaultContent();
                System.out.println("⚠️ Switched to default content as fallback");
                return;
            }

            // 5. Switch back to main content
            driver.switchTo().defaultContent();
            System.out.println("✅ Switched back to main content");

        } catch (Exception e) {
            System.out.println("❌ Error in Stripe authentication flow: " + e.getMessage());
            driver.switchTo().defaultContent();
            throw new RuntimeException("Stripe bank account flow failed", e);
        }
    }
    private void clickIfVisible(WebDriverWait wait, JavascriptExecutor js, By locator, String elementName) throws TimeoutException {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", element);
            System.out.println("✅ Clicked '" + elementName + "'");
            Thread.sleep(1500);
        } catch (Exception e) {
            System.out.println("⚠️ Error clicking '" + elementName + "': " + e.getMessage());
        }
    }

    // Method to handle the bank account selection in main content
    public void selectBankAccount() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            // Ensure we're in main content
            driver.switchTo().defaultContent();
            
            // Wait for and click on the bank account section
            WebElement bankAccountSection = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class, 'UsBankAccountRow')]")
                )
            );
            
            js.executeScript("arguments[0].scrollIntoView(true);", bankAccountSection);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", bankAccountSection);
            
            System.out.println("✅ Clicked on bank account section");
            Thread.sleep(2000);
            
        } catch (Exception e) {
            System.out.println("❌ Failed to select bank account: " + e.getMessage());
            throw e;
        }
    }
 // Method to click Pay button
    public void clickPayButton() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            driver.switchTo().defaultContent();

            // More specific locator for Pay button
            WebElement payBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(., 'Pay') and not(@disabled)]")
            ));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", payBtn);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", payBtn);

            System.out.println("✅ Clicked Pay button");
        } catch (Exception e) {
            System.out.println("❌ Failed to click Pay button: " + e.getMessage());
            throw e;
        }
    }
    
   
    public boolean verifyPaymentSuccess() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement successMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(), 'Payment Successful') or contains(text(), 'Payment was successful') or contains(text(), 'Thank you') or contains(text(), 'Success')]")
                )
            );
            System.out.println("✅ Payment success message found: " + successMsg.getText());
            return successMsg.isDisplayed();
        } catch (Exception e) {
            System.out.println("❌ Payment success message not found: " + e.getMessage());
            return false;
        }
    }


 
    

}