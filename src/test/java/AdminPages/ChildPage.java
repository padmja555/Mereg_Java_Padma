package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

public class ChildPage {
    WebDriver driver;
    WebDriverWait wait;

    // ------------------ Locators ------------------ //
 public   By childName = By.xpath("//input[@formcontrolname='childName']");
 public   By childAge = By.xpath("//input[@formcontrolname='childAge']");
 public  By dateOfBirth = By.xpath("//input[@formcontrolname='dateOfBirth']");
 //public  By dateOfBirth = By.xpath("//input[@formcontrolname='dob']");
 ////input[@formcontrolname="dob"]
 public  By genderMale = By.xpath("//mat-radio-button[.//span[contains(text(),'Male')]]");
 public  By genderFemale = By.xpath("//mat-radio-button[.//span[contains(text(),'Female')]]");
 public   By saveButton = By.xpath("//button[contains(text(),'Save')]");
 public    By addAnotherChild = By.xpath("//button[contains(text(),'Add Another Child')]");
    
    // Multiple locators for the proceed button (more robust)
    By proceedButton = By.xpath("//button[contains(., 'Proceed to Guardian')]");
    
    By successMessage = By.xpath("//div[contains(@class,'success')]");

    // ------------------ Constructor ------------------ //
    public ChildPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ------------------ Main Methods ------------------ //
    
    public void enterDetailsForAdditionalChild(int childIndex, String name, String age, String dob, String gender) {
        System.out.println("=== Starting child details entry for child #" + (childIndex + 1) + " ===");
        
        try {
            waitForFormToLoad(childIndex);
            
            // Use index to target specific child form fields
            safeEnterTextWithIndex(childName, name, "Child Name", childIndex);
            safeEnterTextWithIndex(childAge, age, "Child Age", childIndex);
            safeEnterDateWithIndex(dob, childIndex);
            safeSelectGenderWithIndex(gender, childIndex);
            
            System.out.println("=== Successfully entered details for child #" + (childIndex + 1) + " ===");
            
        } catch (Exception e) {
            System.out.println("CRITICAL ERROR: " + e.getMessage());
            throw e;
        }
    }

    // ------------------ Form Loading ------------------ //
    private void waitForFormToLoad(int childIndex) {
        int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                System.out.println("Waiting for form load - attempt " + attempt);
                
                // Wait for specific input fields with index
                String nameFieldXpath = "(//input[@formcontrolname='childName'])[" + (childIndex + 1) + "]";
                WebElement nameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(nameFieldXpath)));
                wait.until(ExpectedConditions.visibilityOf(nameField));
                
                // Wait for JavaScript readiness
                wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));
                    
                System.out.println("Form for child #" + (childIndex + 1) + " loaded successfully");
                return;
                
            } catch (Exception e) {
                if (attempt == maxAttempts) {
                    System.out.println("Final form load attempt failed: " + e.getMessage());
                    throw new RuntimeException("Form failed to load after " + maxAttempts + " attempts", e);
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    // ------------------ Index-based Field Handling ------------------ //
    private void safeEnterTextWithIndex(By locator, String text, String fieldName, int index) {
        try {
            String indexedXpath = getIndexedXpath(locator, index);
            WebElement field = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(indexedXpath)));
            scrollIntoView(field);
            
            // Clear using JavaScript to avoid InvalidElementStateException
            clearFieldWithJS(field);
            
            // Enter text using JavaScript
            setFieldValueWithJS(field, text);
            
            // Also try sending keys normally as backup
            try {
                field.sendKeys(Keys.TAB); // Trigger blur event if needed
            } catch (Exception e) {
                // Ignore if normal sendKeys fails
            }
            
            System.out.println("Set " + fieldName + " for child #" + (index + 1) + ": " + text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set " + fieldName + " for child #" + (index + 1), e);
        }
    }

    private void safeEnterDateWithIndex(String dob, int index) {
        try {
            String indexedXpath = getIndexedXpath(dateOfBirth, index);
            WebElement dobField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(indexedXpath)));
            scrollIntoView(dobField);
            
            // Use JavaScript to set the date value directly
            setFieldValueWithJS(dobField, dob);
            
            System.out.println("Set Date of Birth for child #" + (index + 1) + ": " + dob);
            
            // Try to trigger change events if needed
            triggerChangeEvent(dobField);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to set date of birth for child #" + (index + 1), e);
        }
    }

    private void safeSelectGenderWithIndex(String gender, int index) {
        try {
            By genderLocator = gender.equalsIgnoreCase("male") ? genderMale : genderFemale;
            String indexedXpath = getIndexedXpath(genderLocator, index);
            
            // Wait for the element to be present
            WebElement genderElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(indexedXpath)));
            scrollIntoView(genderElement);
            
            // Use JavaScript click to avoid interaction issues
            clickWithJavaScript(genderElement);
            
            System.out.println("Selected gender for child #" + (index + 1) + ": " + gender);
            
            // Verify selection if possible
            try {
                Thread.sleep(300); // Small delay for UI update
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to select gender for child #" + (index + 1), e);
        }
    }

    private String getIndexedXpath(By locator, int index) {
        // Convert By to xpath and add index
        String baseXpath = locator.toString().replace("By.xpath: ", "");
        return "(" + baseXpath + ")[" + (index + 1) + "]";
    }

    // ------------------ Utility Methods ------------------ //
    private void scrollIntoView(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', behavior: 'instant'});", element);
        } catch (Exception e) {
            System.out.println("Scroll into view failed: " + e.getMessage());
        }
    }

    private void setFieldValueWithJS(WebElement element, String value) {
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].value = arguments[1];", element, value);
    }

    private void clearFieldWithJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
    }

    private void clickWithJavaScript(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
    
    private void triggerChangeEvent(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
            "var event = new Event('change', { bubbles: true });" +
            "arguments[0].dispatchEvent(event);", element);
    }
    
    // ------------------ Button Click Methods ------------------ //
    public void clickSaveButton() {
        try {
            WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
            scrollIntoView(saveBtn);
            clickWithJavaScript(saveBtn);
            System.out.println("Clicked Save button");
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Save button", e);
        }
    }
    
    public void clickAddAnotherChild() {
        try {
            WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(addAnotherChild));
            scrollIntoView(addBtn);
            clickWithJavaScript(addBtn);
            System.out.println("Clicked Add Another Child button");
            
            // Wait for new form to be ready
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Add Another Child button", e);
        }
    }
    
    public void clickProceedButton() {
        try {
            WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(proceedButton));
            scrollIntoView(proceedBtn);
            clickWithJavaScript(proceedBtn);
            System.out.println("Clicked Proceed button");
        } catch (Exception e) {
            throw new RuntimeException("Failed to click Proceed button", e);
        }
    }
    
    // ------------------ Validation Methods ------------------ //
    public boolean isSuccessMessageDisplayed() {
        try {
            WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            return successMsg.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}