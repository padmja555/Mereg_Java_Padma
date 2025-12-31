/*
package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

public class IndidualChildPage {
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
    public IndidualChildPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ------------------ Main Methods ------------------ //
    
    public void enterDetailsForAdditionChild(int childIndex, String name, String age, String dob, String gender) {
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
*/


//****************
package AdminPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.List;

public class IndidualChildPage {
    WebDriver driver;
    WebDriverWait wait;

    // ------------------ Locators ------------------ //
    By enrollLink = By.xpath("//h4[normalize-space()='Enrollment']");
    By firstEle = By.xpath("//input[@formcontrolname='firstName']");
    By lastEle = By.xpath("//input[@formcontrolname='lastName']");
    By dobEle = By.xpath("//input[@formcontrolname='dob']");
    By guardiansInfo = By.xpath("//button//span[contains(text(),'Proceed')]");
    By childinfoTitleLocator = By.xpath("//h1[contains(@class,'page-heading') or contains(text(),'Child')]");

    // ------------------ Constructor ------------------ //
    public IndidualChildPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ------------------ Actions ------------------ //
    /*
    public void clickOnenrollment() {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(enrollLink));
            element.click();
        } catch (TimeoutException e) {
            System.out.println("Enrollment element not clickable in time.");
            throw e;
        }
    }
    */

    public void enterRandomFirstAndLastName() {
        try {
            String fn = getRandomName(), ln = getRandomLastName();
            WebElement firstNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(firstEle));
            WebElement lastNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(lastEle));

            // Clear using JavaScript for reliability
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", firstNameField);
            firstNameField.sendKeys(fn);

            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", lastNameField);
            lastNameField.sendKeys(ln);

            System.out.println("Random First Name: " + fn);
            System.out.println("Random Last Name: " + ln);
        } catch (Exception e) {
            System.out.println("Error entering name: " + e.getMessage());
            throw e;
        }
    }

    public void selectGender() {
        try {
            String[] genders = {"Male", "Female"};
            String selectedGender = genders[new Random().nextInt(genders.length)];
            By genderEle = By.xpath("//span[normalize-space()='" + selectedGender + "']");
            wait.until(ExpectedConditions.elementToBeClickable(genderEle)).click();
            System.out.println("Selected Gender: " + selectedGender);
        } catch (Exception e) {
            System.out.println("Error selecting gender: " + e.getMessage());
            throw e;
        }
    }

    public void enterrandomDOB() {
        try {
            String formattedDOB = getRandomDOB();
            WebElement dobField = wait.until(ExpectedConditions.visibilityOfElementLocated(dobEle));
            
            // Clear using JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", dobField);
            dobField.sendKeys(formattedDOB);
            System.out.println("Random DOB entered: " + formattedDOB);
        } catch (Exception e) {
            System.out.println("Error entering DOB: " + e.getMessage());
            throw e;
        }
    }

    public void ClickonProceedtoGuardian() {
        try {
            WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(guardiansInfo));
            
            // Scroll to button and click using JavaScript for reliability
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", proceedButton);
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", proceedButton);
            
            System.out.println("Clicked Proceed to Guardian button");
        } catch (Exception e) {
            System.out.println("Error clicking proceed button: " + e.getMessage());
            throw new RuntimeException("Failed to click proceed button", e);
        }
    }
    
    public String getchildinfopageTitleText() {
        try {
            WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(childinfoTitleLocator));
            return titleElement.getText();
        } catch (Exception e) {
            return "Title not found";
        }
    }

    // ------------------ Random Data Generators ------------------ //

    public String getRandomName() {
        String[] names = {"Aarav", "Vivaan", "Reyansh", "Aarya", "Ishita", "Diya", "Kavya", "Anaya", "Saanvi", "Meera"};
        return names[new Random().nextInt(names.length)];
    }

    public String getRandomLastName() {
        String[] lastnames = {"Sharma", "Verma", "Reddy", "Gupta", "Patel", "Kapoor", "Nair", "Joshi", "Singh", "Mehta"};
        return lastnames[new Random().nextInt(lastnames.length)];
    }

    public String getRandomDOB() {
        Random random = new Random();
        int year = random.nextInt(2018 - 2010 + 1) + 2010;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1;
        LocalDate dob = LocalDate.of(year, month, day);
        return dob.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    // ------------------ Optional: Method to check if on next page ------------------ //
    public boolean isOnParentGuardianPage() {
        try {
            // Wait for elements that indicate we're on the parent/guardian page
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(.,'Parent') or contains(.,'Guardian')]"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

	public By getFirstNameLocator() {
		// TODO Auto-generated method stub
		return null;
	}
}