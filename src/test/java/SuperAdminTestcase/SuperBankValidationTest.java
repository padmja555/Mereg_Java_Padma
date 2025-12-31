package SuperAdminTestcase;
import AdminPages.LoginPage;
import AdminPages.Onlinecardpayment;
import AdminPages.Paymentpage;
import AdminPages.ChildPayment;
import AdminPages.BankpaymentValidationPage;
import Base.BaseDriver;
import SuperAdminPage.SuperLoginPage;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SuperBankValidationTest extends BaseDriver {
    Paymentpage paymentPage;
    ChildPayment childpayment;
    Onlinecardpayment onlineCardPaymentPage;
    BankpaymentValidationPage bankPaymentPage;

    @Test(priority = 1)
    public void usBankAccountPaymentFlowTest() {
        try {
            // 1. Login
            SuperLoginPage superloginPage = new SuperLoginPage(driver);
            superloginPage.enterEmail("mereg2025@gmail.com");
            superloginPage.enterPassword("SuperAdmin@123");
            superloginPage.clickSignIn();
            System.out.println("✅ Login successful");
            
            wait.until(ExpectedConditions.urlContains("dashboard"));
            System.out.println("Login successful.");

            // 2. Navigate to Payment and select child information      
            paymentPage = new Paymentpage(driver);
            paymentPage.clickOnPaymentPage();
            paymentPage.getpaymentpageTitleText();

            childpayment = new ChildPayment(driver, wait);
            childpayment.clickOnGurdian();
            Thread.sleep(2000);
            childpayment.selectRandomChild();
            Thread.sleep(3000);
            childpayment.selectRandomPaymentOptionAndEnterAmount("500", "500");
            Thread.sleep(3000);
            childpayment.Procedbuttonpayment();
            
            // 3. Verify Payment Summary and click Pay Securely button          
            onlineCardPaymentPage = new Onlinecardpayment(driver, wait);
            onlineCardPaymentPage.verifyPaymentSummaryPage(); 
            Thread.sleep(2000);
            onlineCardPaymentPage.clickPaySecurelyButton();
            Thread.sleep(2000);

            // 4. Perform US Bank Account payment flow
            bankPaymentPage = new BankpaymentValidationPage(driver, wait);

            // Click on the currency button ($1029)
            bankPaymentPage.clickCurrencyButton();
            Thread.sleep(3000);

            // Enter email
            bankPaymentPage.enterEmail("testuser@example.com");
            Thread.sleep(3000);
            
            // Select US bank account (this expands the section)
            bankPaymentPage.selectUsBankAccount();
            Thread.sleep(3000);

            // Enter full name (AFTER the section is expanded)
            bankPaymentPage.enterFullName("John Doe");
            Thread.sleep(3000);

            // Select the test institution
            bankPaymentPage.selectTestInstitution();
            Thread.sleep(5000);

            // 5. Add explicit wait before handling Stripe
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(20));
            longWait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector(".loading-spinner, [aria-busy='true']")));
            
            // 6. Handle the Stripe bank account authentication
            handleStripeBankAccountAuthentication();
            
            Thread.sleep(3000);

            // 7. Click on the final Pay button after account connection
            bankPaymentPage.clickPayButton();
            Thread.sleep(5000);

            System.out.println("US Bank Account payment flow completed successfully.");
            
            // 8. Verify payment success
            Assert.assertTrue(bankPaymentPage.verifyPaymentSuccess(), "Payment was not successful");
            
        } catch (Exception e) {
            System.out.println("Test failed with exception: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    // Method to handle Stripe bank account authentication
 // Method to handle Stripe bank account authentication
    /*
    private void handleStripeBankAccountAuthentication() {
        try {
            System.out.println("=== Handling Stripe Bank Account Authentication ===");
            
            // Wait for and click on Test Institution within Stripe modal
            WebDriverWait stripeWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Wait for Stripe iframe to load
            stripeWait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("iframe[name^='__privateStripeFrame']")
            ));
            
            // Switch to Stripe iframe
            WebElement stripeIframe = driver.findElement(
                By.cssSelector("iframe[name^='__privateStripeFrame']")
            );
            driver.switchTo().frame(stripeIframe);
            System.out.println("Switched to Stripe iframe");
            
            // FIRST: Handle "Agree and continue" button
            try {
                WebElement agreeButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[.//span[normalize-space()='Agree and continue']]")
                ));
                
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", agreeButton);
                System.out.println("Successfully clicked 'Agree and continue' button");
                
                // Wait for the next screen to load
                Thread.sleep(3000);
                
            } catch (Exception e) {
                System.out.println("'Agree and continue' button not found or not clickable: " + e.getMessage());
            }
            
            // SECOND: Handle "Connect account" button (after Agree and continue)
            try {
                // Wait for the Connect account button to be present
                WebElement connectAccountButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[.//span[contains(text(), 'Connect account')]]")
                ));
                
                // Use JavaScript click for better reliability
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true);", connectAccountButton);
                Thread.sleep(500);
                js.executeScript("arguments[0].click();", connectAccountButton);
                System.out.println("Successfully clicked 'Connect account' button");
                
            } catch (Exception e) {
                System.out.println("'Connect account' button not found, trying alternative selectors: " + e.getMessage());
                
                // Try alternative selectors for Connect account button
                String[] connectAccountSelectors = {
                    "button[data-testid*='connect']",
                    "button:contains('Connect account')",
                    "span:contains('Connect account')",
                    "[data-track*='connect']",
                    "button[type='submit']"
                };
                
                boolean connectClicked = false;
                for (String selector : connectAccountSelectors) {
                    try {
                        List<WebElement> buttons = driver.findElements(By.cssSelector(selector));
                        for (WebElement button : buttons) {
                            if (button.isDisplayed() && button.isEnabled()) {
                                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
                                Thread.sleep(500);
                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
                                System.out.println("Clicked 'Connect account' using selector: " + selector);
                                connectClicked = true;
                                break;
                            }
                        }
                        if (connectClicked) break;
                    } catch (Exception ex) {
                        System.out.println("Failed with selector " + selector + ": " + ex.getMessage());
                    }
                }
                
                if (!connectClicked) {
                    throw new RuntimeException("Could not find and click 'Connect account' button");
                }
            }
            
            Thread.sleep(2000);
            
            // Switch back to main content
            driver.switchTo().defaultContent();
            System.out.println("Switched back to main content");
            
        } catch (Exception e) {
            System.out.println("Error in Stripe bank account authentication: " + e.getMessage());
            // Ensure we switch back to main content on error
            try {
                driver.switchTo().defaultContent();
            } catch (Exception ex) {
                // Ignore if already on default content
            }
            throw new RuntimeException("Failed to handle Stripe authentication", e);
        }
    }
}
    */
    
    /*
    private void handleStripeBankAccountAuthentication() {
        try {
            System.out.println("=== Handling Stripe Bank Account Authentication ===");

            WebDriverWait stripeWait = new WebDriverWait(driver, Duration.ofSeconds(30));  // increased timeout

            // 1. Wait for iframe, switch into it
            stripeWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                By.cssSelector("iframe[name^='__privateStripeFrame']")
            ));
            System.out.println("✅ Switched to Stripe iframe");

            JavascriptExecutor js = (JavascriptExecutor) driver;

            // 2. Click "Agree and continue"
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

            // 4. Wait for success text ("Your account was connected") and then click Back button
            try {
                WebElement successText = stripeWait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(), 'Your account was connected')]")
                    )
                );
                System.out.println("✅ Success screen displayed");

                // Maybe give small delay to ensure the Back button is rendered
                Thread.sleep(2000);

                WebElement backButton = stripeWait.until(
                    ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[contains(text(), 'Back to Merge Main‑Sandbox') or contains(text(), 'Back to Mereg main sandbox') or contains(text(), 'Back to Merge Main Sandbox')]")
                    )
                );
                js.executeScript("arguments[0].scrollIntoView(true);", backButton);
                Thread.sleep(500);
                js.executeScript("arguments[0].click();", backButton);
                System.out.println("✅ Clicked 'Back to Merge Main‑Sandbox'");
                Thread.sleep(3000);
            } catch (Exception e) {
                System.out.println("⚠️ Back to Merge Main‑Sandbox not found or not clickable: " + e.getMessage());
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

            // 2. Click "Agree and continue"
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
}