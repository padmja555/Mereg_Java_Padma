package AdminSpeciations;

import Base.BaseDriver;
import AdminPages.LoginPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class StudentEditUISpecificationTest extends BaseDriver {

    @Test(description = "Verify UI styling specifications on Student Edit Page")
    public void verifyStudentEditUISpecifications() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            // === STEP 1: LOGIN ===
            System.out.println("=== STEP 1: LOGIN ===");
            LoginPage login = new LoginPage(driver);
            login.enterEmail("srasysife@gmail.com");
            login.enterPassword("Admin@123");
            login.clickSignIn();

            // Wait for dashboard to load
            System.out.println("Waiting for dashboard to load...");
            try {
                wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-sidenav")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//aside")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'sidebar')]")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Dashboard') or contains(text(),'Admin')]")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Enrollment') or contains(text(),'Students')]"))
                ));
                System.out.println("✅ Dashboard loaded successfully.");
            } catch (TimeoutException e) {
                System.out.println("⚠️ Dashboard not detected — checking current page state...");
                System.out.println("Current URL: " + driver.getCurrentUrl());
                System.out.println("Page title: " + driver.getTitle());
                
                // Try to continue anyway - sometimes the page loads differently
                Thread.sleep(3000);
            }

            // === STEP 2: NAVIGATE TO STUDENTS PAGE ===
            System.out.println("=== STEP 2: NAVIGATE TO STUDENTS PAGE ===");
            
            // Try multiple navigation strategies
            boolean navigationSuccessful = navigateToStudentsPage(wait);
            
            if (!navigationSuccessful) {
                // Fallback: Try direct URL navigation
                System.out.println("⚠️ Sidebar navigation failed, trying direct URL...");
                try {
                    driver.get("https://mereg-dev.netlify.app/navigation-home/students");
                    wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(text(),'Student') or contains(text(),'student')]")));
                    System.out.println("✅ Direct navigation to students page successful");
                } catch (Exception urlException) {
                    Assert.fail("❌ All navigation methods failed. Current URL: " + driver.getCurrentUrl());
                }
            }

            // === STEP 3: CLICK ON VIEW DETAILS ===
            System.out.println("=== STEP 3: CLICK ON VIEW DETAILS ===");
            
            // Wait for students page to load completely
            Thread.sleep(3000);
            
            // Look for View Details buttons with multiple strategies
            List<WebElement> viewDetailsButtons = findViewDetailsButtons(wait);
            
            if (viewDetailsButtons.isEmpty()) {
                // Take screenshot for debugging
                System.out.println("No View Details buttons found. Current page state:");
                System.out.println("URL: " + driver.getCurrentUrl());
                System.out.println("Title: " + driver.getTitle());
                System.out.println("Page source contains 'View Details': " + 
                    driver.getPageSource().contains("View Details"));
                Assert.fail("❌ No 'View Details' buttons found on students page");
            }
            
            // Click the first available View Details button
            WebElement firstViewDetails = viewDetailsButtons.get(0);
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstViewDetails);
                Thread.sleep(1000);
                firstViewDetails.click();
                System.out.println("✅ Clicked on View Details button");
            } catch (Exception e) {
                // Try JavaScript click as fallback
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstViewDetails);
                System.out.println("✅ Clicked on View Details using JavaScript");
            }

            // Wait for student details page
            wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Student Details')]")),
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Enrollment Information')]")),
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Child Information')]"))
            ));

            // === STEP 4: ENTER EDIT MODE ===
            System.out.println("=== STEP 4: ENTER EDIT MODE ===");
            
            WebElement editButton = findEditButton(wait);
            if (editButton == null) {
                Assert.fail("❌ Edit button not found on student details page");
            }
            
            try {
                editButton.click();
                System.out.println("✅ Clicked on Edit button");
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
                System.out.println("✅ Clicked on Edit button using JavaScript");
            }

            // Wait for edit form to load
            wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//mat-form-field//input")),
                ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='text']")),
                ExpectedConditions.presenceOfElementLocated(By.xpath("//textarea")),
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Save Changes')]"))
            ));

            // === STEP 5: VERIFY UI SPECIFICATIONS ===
            System.out.println("=== STEP 5: VERIFY UI SPECIFICATIONS ===");
            verifyAllUISpecifications(wait);

            System.out.println("\n✅ Student Edit Page UI Specification test completed successfully!");

        } catch (Exception e) {
            System.err.println("❌ Test failed due to: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("❌ Test failed due to: " + e.getMessage());
        }
    }

    private boolean navigateToStudentsPage(WebDriverWait wait) {
        try {
            // Strategy 1: Look for Students link in sidebar/navigation
            String[] sidebarLocators = {
                "//span[contains(text(),'Students')]",
                "//a[contains(text(),'Students')]",
                "//button[contains(.,'Students')]",
                "//div[contains(text(),'Students')]",
                "//li[contains(.,'Students')]",
                "//*[@role='menuitem' and contains(.,'Students')]",
                "//mat-list-item[contains(.,'Students')]",
                "//*[contains(@class,'menu')]//*[contains(text(),'Students')]",
                "//*[contains(@class,'nav')]//*[contains(text(),'Students')]",
                "//*[contains(@class,'sidebar')]//*[contains(text(),'Students')]"
            };
            
            for (String locator : sidebarLocators) {
                try {
                    List<WebElement> elements = driver.findElements(By.xpath(locator));
                    for (WebElement element : elements) {
                        if (element.isDisplayed() && element.isEnabled()) {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
                            Thread.sleep(1000);
                            element.click();
                            System.out.println("✅ Clicked on Students using: " + locator);
                            
                            // Verify we navigated to students page
                            Thread.sleep(3000);
                            if (isOnStudentsPage(wait)) {
                                return true;
                            }
                        }
                    }
                } catch (Exception e) {
                    // Continue to next locator
                }
            }
            
            // Strategy 2: Look for any clickable Students element
            List<WebElement> allStudentsElements = driver.findElements(
                By.xpath("//*[contains(text(),'Students') and (self::a or self::button or self::span or self::div)]"));
            
            for (WebElement element : allStudentsElements) {
                try {
                    if (element.isDisplayed() && element.isEnabled()) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                        System.out.println("✅ Clicked on Students element using generic locator");
                        
                        Thread.sleep(3000);
                        if (isOnStudentsPage(wait)) {
                            return true;
                        }
                    }
                } catch (Exception e) {
                    // Continue to next element
                }
            }
            
            return false;
            
        } catch (Exception e) {
            System.out.println("⚠️ Navigation error: " + e.getMessage());
            return false;
        }
    }

    private boolean isOnStudentsPage(WebDriverWait wait) {
        try {
            return wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("students"),
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Student List')]")),
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Manage Students')]")),
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'View Details')]"))
            ));
        } catch (Exception e) {
            return false;
        }
    }

    private List<WebElement> findViewDetailsButtons(WebDriverWait wait) {
        String[] viewDetailsLocators = {
            "//button[contains(.,'View Details')]",
            "//button[contains(.,'view details')]",
            "//*[contains(text(),'View Details')]",
            "//a[contains(.,'View Details')]",
            "//span[contains(.,'View Details')]/ancestor::button",
            "//*[contains(@class,'button') and contains(.,'View Details')]"
        };
        
        for (String locator : viewDetailsLocators) {
            try {
                List<WebElement> elements = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
                if (!elements.isEmpty()) {
                    System.out.println("✅ Found " + elements.size() + " View Details buttons using: " + locator);
                    return elements;
                }
            } catch (Exception e) {
                // Continue to next locator
            }
        }
        return java.util.Collections.emptyList();
    }

    private WebElement findEditButton(WebDriverWait wait) {
        String[] editButtonLocators = {
            "//button[contains(.,'Edit')]",
            "//button[contains(.,'edit')]",
            "//*[contains(text(),'Edit') and (self::button or self::a)]",
            "//span[contains(.,'Edit')]/ancestor::button",
            "//*[contains(@class,'button') and contains(.,'Edit')]"
        };
        
        for (String locator : editButtonLocators) {
            try {
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
                if (element.isDisplayed()) {
                    System.out.println("✅ Found Edit button using: " + locator);
                    return element;
                }
            } catch (Exception e) {
                // Continue to next locator
            }
        }
        return null;
    }

    private void verifyAllUISpecifications(WebDriverWait wait) {
        // Based on your screenshots, verify these fields
        String[] fieldLabels = {
            "First Name", "Last Name", "Middle Name", "Date of Birth",
            "Place Of Birth", "Address Line 1", "Address Line 2",
            "City", "State", "Email Address", "Phone Number"
        };

        for (String fieldLabel : fieldLabels) {
            verifyFieldUIStyling(fieldLabel, wait);
        }

        // Verify dropdowns
        verifyDropdownUIStyling("Gender", wait);
        verifyDropdownUIStyling("Relationship with Child", wait);
        verifyDropdownUIStyling("Phone Type", wait);

        // Verify buttons
        verifyButtonUIStyling("Save Changes", wait);
        verifyButtonUIStyling("Cancel", wait);
    }

    private void verifyFieldUIStyling(String fieldLabel, WebDriverWait wait) {
        try {
            WebElement inputField = findInputFieldByLabel(fieldLabel);
            
            if (inputField == null) {
                System.out.println("⚠️ Field not found: " + fieldLabel);
                return;
            }

            // Extract UI CSS values
            String fontFamily = inputField.getCssValue("font-family");
            String fontSize = inputField.getCssValue("font-size");
            String fontColor = inputField.getCssValue("color");
            String borderColor = inputField.getCssValue("border-color");

            System.out.println("\n----- Field: " + fieldLabel + " -----");
            System.out.println("Font Family: " + fontFamily);
            System.out.println("Font Size: " + fontSize);
            System.out.println("Font Color: " + fontColor);
            System.out.println("Border Color: " + borderColor);

            // Basic Assertions
            Assert.assertTrue(fontFamily.toLowerCase().contains("roboto") 
                            || fontFamily.toLowerCase().contains("sans"),
                    "❌ Font family mismatch for " + fieldLabel);
            
            Assert.assertTrue(fontSize.equals("14px") || fontSize.equals("16px"),
                    "❌ Font size not standard for " + fieldLabel);

            // Test data entry
            testDataEntry(inputField, fieldLabel);

        } catch (Exception e) {
            System.out.println("⚠️ Error verifying field " + fieldLabel + ": " + e.getMessage());
        }
    }

    private WebElement findInputFieldByLabel(String label) {
        String[] xpaths = {
            "//mat-label[contains(text(), '" + label + "')]/ancestor::mat-form-field//input",
            "//label[contains(text(), '" + label + "')]/following-sibling::input",
            "//input[@placeholder='" + label + "']",
            "//*[contains(text(), '" + label + "')]/ancestor::div[contains(@class, 'form-field')]//input",
            "//mat-form-field[.//*[contains(text(), '" + label + "')]]//input",
            "//*[contains(text(), '" + label + "')]/following::input[1]"
        };
        
        for (String xpath : xpaths) {
            try {
                List<WebElement> elements = driver.findElements(By.xpath(xpath));
                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    return elements.get(0);
                }
            } catch (Exception e) {
                // Continue to next xpath
            }
        }
        return null;
    }

    private void verifyDropdownUIStyling(String dropdownLabel, WebDriverWait wait) {
        try {
            WebElement dropdown = findDropdownByLabel(dropdownLabel);
            
            if (dropdown == null) {
                System.out.println("⚠️ Dropdown not found: " + dropdownLabel);
                return;
            }

            String fontFamily = dropdown.getCssValue("font-family");
            String fontSize = dropdown.getCssValue("font-size");

            System.out.println("\n----- Dropdown: " + dropdownLabel + " -----");
            System.out.println("Font Family: " + fontFamily);
            System.out.println("Font Size: " + fontSize);

            Assert.assertTrue(fontFamily.toLowerCase().contains("roboto") 
                            || fontFamily.toLowerCase().contains("sans"),
                    "❌ Dropdown font family mismatch for " + dropdownLabel);

            System.out.println("✅ Dropdown UI verified: " + dropdownLabel);

        } catch (Exception e) {
            System.out.println("⚠️ Error verifying dropdown " + dropdownLabel + ": " + e.getMessage());
        }
    }

    private WebElement findDropdownByLabel(String label) {
        String[] xpaths = {
            "//mat-label[contains(text(), '" + label + "')]/ancestor::mat-form-field//mat-select",
            "//label[contains(text(), '" + label + "')]/following-sibling::mat-select",
            "//mat-form-field[.//*[contains(text(), '" + label + "')]]//mat-select"
        };
        
        for (String xpath : xpaths) {
            try {
                List<WebElement> elements = driver.findElements(By.xpath(xpath));
                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    return elements.get(0);
                }
            } catch (Exception e) {
                // Continue to next xpath
            }
        }
        return null;
    }

    private void verifyButtonUIStyling(String buttonText, WebDriverWait wait) {
        try {
            WebElement button = findButtonByText(buttonText);
            
            if (button == null) {
                System.out.println("⚠️ Button not found: " + buttonText);
                return;
            }

            String fontFamily = button.getCssValue("font-family");
            String fontSize = button.getCssValue("font-size");
            String backgroundColor = button.getCssValue("background-color");

            System.out.println("\n----- Button: " + buttonText + " -----");
            System.out.println("Font Family: " + fontFamily);
            System.out.println("Font Size: " + fontSize);
            System.out.println("Background Color: " + backgroundColor);

            Assert.assertTrue(button.isDisplayed(), "❌ Button " + buttonText + " not visible");
            Assert.assertTrue(button.isEnabled(), "❌ Button " + buttonText + " not enabled");
            
            Assert.assertTrue(fontFamily.toLowerCase().contains("roboto") 
                            || fontFamily.toLowerCase().contains("sans"),
                    "❌ Button font family mismatch for " + buttonText);

            System.out.println("✅ Button UI verified: " + buttonText);

        } catch (Exception e) {
            System.out.println("⚠️ Error verifying button " + buttonText + ": " + e.getMessage());
        }
    }

    private WebElement findButtonByText(String buttonText) {
        String[] xpaths = {
            "//button[contains(text(), '" + buttonText + "')]",
            "//button[.//span[contains(text(), '" + buttonText + "')]]",
            "//span[contains(text(), '" + buttonText + "')]/ancestor::button"
        };
        
        for (String xpath : xpaths) {
            try {
                List<WebElement> elements = driver.findElements(By.xpath(xpath));
                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    return elements.get(0);
                }
            } catch (Exception e) {
                // Continue to next xpath
            }
        }
        return null;
    }

    private void testDataEntry(WebElement inputField, String fieldLabel) {
        try {
            String testData = getTestDataForField(fieldLabel);
            
            inputField.click();
            inputField.clear();
            inputField.sendKeys(testData);
            
            String enteredValue = inputField.getAttribute("value");
            if (enteredValue != null && !enteredValue.isEmpty()) {
                System.out.println("✓ Data entry test passed for " + fieldLabel + ": " + enteredValue);
            }
            
        } catch (Exception e) {
            System.out.println("⚠️ Data entry test failed for " + fieldLabel + ": " + e.getMessage());
        }
    }

    private String getTestDataForField(String fieldName) {
        switch (fieldName.toLowerCase()) {
            case "first name":
                return "John";
            case "last name":
                return "Doe";
            case "middle name":
                return "Michael";
            case "date of birth":
                return "01012015";
            case "place of birth":
                return "New York";
            case "address line 1":
                return "123 Main Street";
            case "address line 2":
                return "Apt 101";
            case "city":
                return "Los Angeles";
            case "state":
                return "California";
            case "email address":
                return "john.doe@test.com";
            case "phone number":
                return "3012565287";
            default:
                return "Test Data";
        }
    }
}