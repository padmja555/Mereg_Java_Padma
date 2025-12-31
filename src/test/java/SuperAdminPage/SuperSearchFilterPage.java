package SuperAdminPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SuperSearchFilterPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Random random;

    public SuperSearchFilterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.random = new Random();
    }

    // Filter Section Locators
    private By filterSection = By.xpath("//div[contains(@class,'filter') or contains(@class,'filters')]");
    
    // Input Field Locators
    private By childLastNameInput = By.xpath("//input[@placeholder='Child Last Name' or " +
            "contains(@placeholder,'Last Name') or " +
            "contains(@aria-label,'Child Last Name')]");
    
    // Dropdown Locators
    private By enrolledProgramDropdown = By.xpath("//mat-select[contains(@aria-label,'Enrolled Program') or " +
            "contains(@placeholder,'Enrolled Program') or " +
            "contains(.,'Enrolled Program')]");
    
    private By feeStatusDropdown = By.xpath("//mat-select[contains(@aria-label,'Fee Status') or " +
            "contains(@placeholder,'Fee Status') or " +
            "contains(.,'Fee Status')]");
    
    private By studentGroupDropdown = By.xpath("//mat-select[contains(@aria-label,'Student Group') or " +
            "contains(@placeholder,'Student Group') or " +
            "contains(.,'Student Group')]");

    // Table Locators
    private By studentTableRows = By.xpath("//table//tbody//tr");
    private By childLastNameCells = By.xpath("//table//tbody//tr/td[1]"); // Child Last Name column
    private By enrolledProgramCells = By.xpath("//table//tbody//tr/td[6]"); // Enrolled Program column
    private By feeStatusCells = By.xpath("//table//tbody//tr/td[7]"); // Fee Status column
    private By viewDetailsButtons = By.xpath("//button[contains(.,'View Details')]");

    // Dropdown Options
    private By dropdownOptions = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option");
    private By dropdownOptionSpans = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option//span");

    // Method to enter Child Last Name
    public void enterChildLastName(String lastName) {
        try {
            System.out.println("Entering Child Last Name: " + lastName);
            
            WebElement inputField = findInputField();
            if (inputField != null) {
                inputField.clear();
                inputField.sendKeys(lastName);
                System.out.println("✅ Successfully entered Child Last Name: " + lastName);
                
                // Wait for filter to apply
                waitForFilterResults();
            } else {
                System.out.println("⚠️ Child Last Name input field not found");
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to enter Child Last Name: " + e.getMessage());
        }
    }

    // Find input field using multiple strategies
    private WebElement findInputField() {
        List<By> locators = List.of(
            childLastNameInput,
            By.xpath("//input[contains(@class,'filter')]"),
            By.xpath("//input[@type='text']"),
            By.xpath("//mat-form-field//input"),
            By.xpath("//input")
        );

        for (By locator : locators) {
            try {
                List<WebElement> elements = driver.findElements(locator);
                for (WebElement element : elements) {
                    if (element.isDisplayed() && element.isEnabled()) {
                        String placeholder = element.getAttribute("placeholder");
                        if (placeholder != null && 
                            (placeholder.contains("Last Name") || placeholder.contains("Filter") || 
                             placeholder.contains("Search"))) {
                            return element;
                        }
                        // If it's the only input or first input in filter section, use it
                        if (elements.size() == 1 || isInFilterSection(element)) {
                            return element;
                        }
                    }
                }
            } catch (Exception e) {
                // Continue to next locator
            }
        }
        return null;
    }

    private boolean isInFilterSection(WebElement element) {
        try {
            WebElement parent = element.findElement(By.xpath("./ancestor::div[contains(@class,'filter') or contains(@class,'filters')]"));
            return parent != null;
        } catch (Exception e) {
            return false;
        }
    }

    // Select random Child Last Name from existing data
    public String selectRandomChildLastName() {
        try {
            List<WebElement> cells = driver.findElements(childLastNameCells);
            if (cells.isEmpty()) {
                return "TestLastName"; // Fallback value
            }

            List<String> lastNames = new ArrayList<>();
            for (WebElement cell : cells) {
                String name = cell.getText().trim();
                if (!name.isEmpty() && !lastNames.contains(name)) {
                    lastNames.add(name);
                }
            }

            if (lastNames.isEmpty()) {
                return "TestLastName";
            }

            String randomName = lastNames.get(random.nextInt(lastNames.size()));
            System.out.println("Selected random Child Last Name: " + randomName);
            return randomName;
        } catch (Exception e) {
            System.err.println("Failed to select random Child Last Name: " + e.getMessage());
            return "TestLastName";
        }
    }

    // Select Enrolled Program
    public void selectEnrolledProgram(String program) {
        selectDropdown(enrolledProgramDropdown, program, "Enrolled Program");
    }

    // Select random Enrolled Program
    public String selectRandomEnrolledProgram() {
        return selectRandomDropdown(enrolledProgramDropdown, "Enrolled Program");
    }

    // Select Fee Status
    public void selectFeeStatus(String feeStatus) {
        selectDropdown(feeStatusDropdown, feeStatus, "Fee Status");
    }

    // Select random Fee Status
    public String selectRandomFeeStatus() {
        return selectRandomDropdown(feeStatusDropdown, "Fee Status");
    }

    // Select Student Group
    public void selectStudentGroup(String studentGroup) {
        selectDropdown(studentGroupDropdown, studentGroup, "Student Group");
    }

    // Select random Student Group
    public String selectRandomStudentGroup() {
        return selectRandomDropdown(studentGroupDropdown, "Student Group");
    }

    // Generic dropdown selection method
    private void selectDropdown(By dropdownLocator, String value, String dropdownName) {
        try {
            System.out.println("Selecting " + dropdownName + ": " + value);
            
            WebElement dropdown = findDropdownElement(dropdownLocator, dropdownName);
            if (dropdown != null) {
                clickElement(dropdown);
                selectDropdownOption(value);
                System.out.println("✅ Successfully selected " + dropdownName + ": " + value);
                waitForFilterResults();
            } else {
                System.out.println("⚠️ " + dropdownName + " dropdown not found");
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to select " + dropdownName + ": " + e.getMessage());
        }
    }

    // Generic random dropdown selection
    private String selectRandomDropdown(By dropdownLocator, String dropdownName) {
        try {
            System.out.println("Selecting random " + dropdownName + "...");
            
            WebElement dropdown = findDropdownElement(dropdownLocator, dropdownName);
            if (dropdown != null) {
                clickElement(dropdown);
                String selectedValue = selectRandomDropdownOption();
                System.out.println("✅ Successfully selected random " + dropdownName + ": " + selectedValue);
                waitForFilterResults();
                return selectedValue;
            } else {
                System.out.println("⚠️ " + dropdownName + " dropdown not found");
                return null;
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to select random " + dropdownName + ": " + e.getMessage());
            return null;
        }
    }

    // Find dropdown element
    private WebElement findDropdownElement(By primaryLocator, String dropdownName) {
        List<By> locators = List.of(
            primaryLocator,
            By.xpath("//mat-select[contains(.,'" + dropdownName + "')]"),
            By.xpath("//mat-form-field[contains(.,'" + dropdownName + "')]//mat-select"),
            By.xpath("//div[contains(@class,'filter')]//mat-select")
        );

        for (By locator : locators) {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed() && element.isEnabled()) {
                    return element;
                }
            } catch (Exception e) {
                // Continue to next locator
            }
        }
        return null;
    }

    // Click element with multiple strategies
    private void clickElement(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            Thread.sleep(500);
            
            // Try JavaScript click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            Thread.sleep(1000);
        } catch (Exception e) {
            try {
                element.click();
            } catch (Exception e2) {
                new Actions(driver).moveToElement(element).click().perform();
            }
        }
    }

    // Select specific dropdown option
    private void selectDropdownOption(String value) {
        List<By> optionLocators = List.of(
            By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option//span[normalize-space()='" + value + "']"),
            By.xpath("//mat-option//span[contains(text(),'" + value + "')]"),
            By.xpath("//mat-option[contains(.,'" + value + "')]")
        );

        for (By locator : optionLocators) {
            try {
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(locator));
                clickElement(option);
                return;
            } catch (Exception e) {
                // Continue to next locator
            }
        }
    }

    // Select random dropdown option
    private String selectRandomDropdownOption() {
        try {
            // Wait for dropdown options to appear
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dropdownOptions));
            
            List<WebElement> options = driver.findElements(dropdownOptionSpans);
            if (options.isEmpty()) {
                options = driver.findElements(dropdownOptions);
            }

            if (!options.isEmpty()) {
                List<WebElement> visibleOptions = new ArrayList<>();
                for (WebElement option : options) {
                    if (option.isDisplayed() && !option.getText().trim().isEmpty()) {
                        visibleOptions.add(option);
                    }
                }

                if (!visibleOptions.isEmpty()) {
                    WebElement randomOption = visibleOptions.get(random.nextInt(visibleOptions.size()));
                    String optionText = randomOption.getText().trim();
                    clickElement(randomOption);
                    return optionText;
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to select random dropdown option: " + e.getMessage());
        }
        return "Unknown";
    }

    // Wait for filter results to load
    private void waitForFilterResults() {
        try {
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(studentTableRows));
        } catch (Exception e) {
            System.out.println("Filter wait completed with exception: " + e.getMessage());
        }
    }

    // Get current student count
    public int getStudentCount() {
        try {
            List<WebElement> rows = driver.findElements(studentTableRows);
            return rows.size();
        } catch (Exception e) {
            return 0;
        }
    }

    // Get count of View Details buttons
    public int getViewDetailsCount() {
        try {
            List<WebElement> buttons = driver.findElements(viewDetailsButtons);
            return buttons.size();
        } catch (Exception e) {
            return 0;
        }
    }

    // Click random View Details button
    public void clickRandomViewDetails() {
        try {
            List<WebElement> buttons = driver.findElements(viewDetailsButtons);
            if (!buttons.isEmpty()) {
                WebElement randomButton = buttons.get(random.nextInt(buttons.size()));
                clickElement(randomButton);
                System.out.println("✅ Clicked random View Details button");
            } else {
                System.out.println("⚠️ No View Details buttons found");
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to click View Details: " + e.getMessage());
        }
    }

    // Clear all filters
    public void clearFilters() {
        try {
            // Clear input field
            WebElement inputField = findInputField();
            if (inputField != null) {
                inputField.clear();
            }
            
            // Refresh page to reset dropdowns
            driver.navigate().refresh();
            wait.until(ExpectedConditions.visibilityOfElementLocated(studentTableRows));
            System.out.println("✅ Filters cleared");
        } catch (Exception e) {
            System.err.println("❌ Failed to clear filters: " + e.getMessage());
        }
    }

    // Debug method to show available elements
    public void debugPageElements() {
        try {
            System.out.println("=== DEBUG: Page Elements ===");
            
            // Input fields
            List<WebElement> inputs = driver.findElements(By.xpath("//input"));
            System.out.println("Input fields found: " + inputs.size());
            for (WebElement input : inputs) {
                if (input.isDisplayed()) {
                    System.out.println("Input - Placeholder: " + input.getAttribute("placeholder") + 
                                     ", Type: " + input.getAttribute("type"));
                }
            }
            
            // Dropdowns
            List<WebElement> dropdowns = driver.findElements(By.xpath("//mat-select"));
            System.out.println("Dropdowns found: " + dropdowns.size());
            for (WebElement dropdown : dropdowns) {
                if (dropdown.isDisplayed()) {
                    System.out.println("Dropdown - Text: " + dropdown.getText());
                }
            }
            
            // Table info
            List<WebElement> rows = driver.findElements(studentTableRows);
            System.out.println("Table rows: " + rows.size());
            
            List<WebElement> buttons = driver.findElements(viewDetailsButtons);
            System.out.println("View Details buttons: " + buttons.size());
            
        } catch (Exception e) {
            System.out.println("Debug failed: " + e.getMessage());
        }
    }
}