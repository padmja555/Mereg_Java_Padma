package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ChildEditDetailsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public ChildEditDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.js = (JavascriptExecutor) driver;
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//*[contains(text(), 'Student Details') or contains(text(), 'Enrollment Information')]")));
    }

    public void clickEdit() {
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(., 'Edit')]")));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", editButton);
        js.executeScript("arguments[0].click();", editButton);
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//mat-form-field | //button[contains(., 'Save')]")));
    }

    public void fillAngularMaterialForm(String studentFirstName, String studentLastName,
                                        String parentFirstName, String parentLastName) throws InterruptedException {
        // Only filling minimal required fields to trigger validation

        fillFieldByExactLabel("First Name", studentFirstName, true);
        fillFieldByExactLabel("Last Name", studentLastName, true);
        fillFieldByExactLabel("Date of Birth", "02/06/2002", true);
        fillFieldByExactLabel("City", "TestCity", true);
        fillFieldByExactLabel("Zip Code", "12345", true);
        setDropdownByExactLabel("State", "Alabama", true);
        setDropdownByExactLabel("Phone Type", "Cell", true);
        setDropdownByExactLabel("Country Code", "+1 (US)", true);
        fillFieldByExactLabel("Phone Number", "3012565287", true);

        // 🔧 Fill Alternate Phone Number (NEW)
        setDropdownByExactLabel("Alternate Phone Type", "Cell", false);
        setDropdownByExactLabel("Alternate Country Code", "+44 (UK)", false);
        fillFieldByExactLabel("Alternate Phone Number", "441234567890", true); // Avoid validation error
    }

    public void fillFieldByExactLabel(String exactLabel, String value, boolean required) {
        try {
            WebElement field = findFieldByExactLabel(exactLabel);
            if (field != null) {
                WebElement input = findInputInField(field);
                if (input != null) {
                    input.clear();
                    input.sendKeys(value);
                }
            } else if (required) {
                System.out.println("❌ Required field not found: " + exactLabel);
            }
        } catch (Exception e) {
            System.out.println("Error filling field '" + exactLabel + "': " + e.getMessage());
        }
    }

    public void setDropdownByExactLabel(String exactLabel, String optionValue, boolean required) {
        try {
            WebElement field = findFieldByExactLabel(exactLabel);
            if (field != null) {
                WebElement dropdown = field.findElement(By.xpath(".//mat-select"));
                js.executeScript("arguments[0].click();", dropdown);
                Thread.sleep(1000);
                List<WebElement> options = driver.findElements(By.xpath("//mat-option//span[contains(text(), '" + optionValue + "')]"));
                if (!options.isEmpty()) {
                    js.executeScript("arguments[0].click();", options.get(0));
                }
            } else if (required) {
                System.out.println("❌ Required dropdown not found: " + exactLabel);
            }
        } catch (Exception e) {
            System.out.println("Error setting dropdown: " + e.getMessage());
        }
    }

    private WebElement findFieldByExactLabel(String exactLabel) {
        String[] xpaths = {
            "//mat-form-field[.//mat-label[text()='" + exactLabel + "']]",
            "//mat-form-field[.//label[text()='" + exactLabel + "']]",
            "//mat-form-field[.//span[text()='" + exactLabel + "']]"
        };

        for (String xpath : xpaths) {
            List<WebElement> elements = driver.findElements(By.xpath(xpath));
            if (!elements.isEmpty()) return elements.get(0);
        }
        return null;
    }

    private WebElement findInputInField(WebElement field) {
        List<WebElement> inputs = field.findElements(By.tagName("input"));
        return inputs.isEmpty() ? null : inputs.get(0);
    }

    public void clickSave() {
        try {
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(., 'Save')]")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", saveButton);
            js.executeScript("arguments[0].click();", saveButton);
        } catch (Exception e) {
            System.out.println("Error clicking Save: " + e.getMessage());
        }
    }

    public boolean hasValidationErrors() {
        try {
            Thread.sleep(1000);

            // 1. Check for mat-error messages
            List<WebElement> errorMessages = driver.findElements(By.xpath("//mat-error[normalize-space()!='']"));
            for (WebElement error : errorMessages) {
                if (error.isDisplayed()) {
                    System.out.println("❌ Validation error: " + error.getText().trim());
                    return true;
                }
            }

            // 2. Check for mat-form-fields with validation errors
            List<WebElement> invalidFields = driver.findElements(By.xpath(
                "//mat-form-field[contains(@class, 'mat-form-field-invalid') or contains(@class, 'ng-invalid')]"
            ));

            for (WebElement field : invalidFields) {
                if (field.isDisplayed()) {
                    String label = field.getText();
                    System.out.println("❌ Invalid field detected: " + label);
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            System.out.println("Error checking validation errors: " + e.getMessage());
            return false;
        }
    }

    public void navigateToStudentsPage() {
        driver.navigate().to("https://mereg.netlify.app/navigation-home/students");
    }
}
