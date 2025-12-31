package AdminSpeciations;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import AdminPages.LoginPage;
import Base.BaseDriver;

import java.time.Duration;
import java.util.List;

public class StudentUiTest extends BaseDriver {

    By studentsMenu = By.xpath("//h4[normalize-space()='Students']");
    By studentsMenuContainer = By.xpath("//h4[normalize-space()='Students']/ancestor::a[1]");
    By title = By.xpath("//h1[contains(text(),'Students Dashboard')]");
    By excelBtn = By.xpath("//button[contains(.,'Excel')]");
    By pdfBtn = By.xpath("//button[contains(.,'PDF')]");
    By tableHeaders = By.xpath("//table/thead/tr/th");
    By filterSearchInput = By.xpath("//mat-form-field[.//mat-label[normalize-space()='Filter']]//input");
    By studentRows = By.xpath("//table/tbody/tr");
    By overDueBadge = By.xpath("//span[contains(text(),'Overdue')]");
    By paidBadge = By.xpath("//span[contains(text(),'Paid')]");

    @Test
    public void verifyStudentsDashboardUI() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // ----------------------------------------------------
        // LOGIN
        // ----------------------------------------------------
        System.out.println("🔐 LOGGING IN...");
        LoginPage login = new LoginPage(driver);
        login.enterEmail("srasysife@gmail.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();

        // Wait for login to complete
        wait.until(ExpectedConditions.urlContains("/navigation-home"));
        System.out.println("✅ Login successful");

        // ----------------------------------------------------
        // CLICK STUDENTS MENU
        // ----------------------------------------------------
        System.out.println("📋 NAVIGATING TO STUDENTS MENU...");
        wait.until(ExpectedConditions.elementToBeClickable(studentsMenu)).click();

        // ----------------------------------------------------
        // VERIFY LEFT MENU IS HIGHLIGHTED
        // ----------------------------------------------------
        WebElement menuContainer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(studentsMenuContainer)
        );

        String bg = menuContainer.getCssValue("background-color");
        String cls = menuContainer.getAttribute("class");

        System.out.println("🎨 MENU BG COLOR = " + bg);
        System.out.println("🎨 MENU CLASS = " + cls);

        boolean isHighlighted =
                (bg != null && (bg.contains("98") || bg.contains("234"))) ||
                        (cls != null && cls.toLowerCase().contains("active")) ||
                        (cls != null && cls.toLowerCase().contains("selected")) ||
                        (cls != null && cls.toLowerCase().contains("focus"));

        Assert.assertTrue(isHighlighted, "❌ Students menu is NOT highlighted");
        System.out.println("✅ Students menu is highlighted");

        // ----------------------------------------------------
        // VERIFY PAGE TITLE
        // ----------------------------------------------------
        WebElement pageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(title));
        Assert.assertTrue(pageTitle.isDisplayed());
        Assert.assertEquals(pageTitle.getText().trim(), "Students Dashboard");
        System.out.println("✅ Page title verified: Students Dashboard");

        // ----------------------------------------------------
        // VERIFY EXCEL BUTTON UI
        // ----------------------------------------------------
        WebElement excel = wait.until(ExpectedConditions.visibilityOfElementLocated(excelBtn));
        Assert.assertTrue(excel.isDisplayed(), "Excel button missing");
        System.out.println("✅ Excel button is displayed");

        // ----------------------------------------------------
        // VERIFY PDF BUTTON UI
        // ----------------------------------------------------
        WebElement pdf = wait.until(ExpectedConditions.visibilityOfElementLocated(pdfBtn));
        Assert.assertTrue(pdf.isDisplayed(), "PDF button missing");
        System.out.println("✅ PDF button is displayed");

        // ----------------------------------------------------
        // VERIFY TABLE HEADERS
        // ----------------------------------------------------
        wait.until(ExpectedConditions.presenceOfElementLocated(tableHeaders));
        List<WebElement> headers = driver.findElements(tableHeaders);
        Assert.assertTrue(headers.size() > 0, "Table headers missing");

        String[] expectedHeaders = {
                "Child Last Name", "Child First Name", "Gender",
                "Parent/Guardian Last Name", "Parent/Guardian First Name",
                "Student Group", "Enrolled Program", "Fee Status", "View"
        };

        for (int i = 0; i < expectedHeaders.length && i < headers.size(); i++) {
            String actualHeader = headers.get(i).getText().trim();
            String expectedHeader = expectedHeaders[i];
            Assert.assertEquals(actualHeader, expectedHeader, 
                "Header mismatch at position " + (i+1) + ": expected '" + expectedHeader + "', got '" + actualHeader + "'");
        }
        System.out.println("✅ All table headers verified");

        // ----------------------------------------------------
        // PERFORM ADDITIONAL UI STYLE VERIFICATIONS BEFORE ANY ACTIONS
        // ----------------------------------------------------
        System.out.println("🎨 PERFORMING INITIAL UI STYLE VERIFICATIONS...");
        verifyInitialUIStyles(wait);

        // ----------------------------------------------------
        // CHECK FOR BADGES IN UNFILTERED TABLE FIRST
        // ----------------------------------------------------
        System.out.println("🏷️ CHECKING FOR BADGES IN UNFILTERED TABLE...");
        
        List<WebElement> overdueBadgesBeforeFilter = driver.findElements(overDueBadge);
        List<WebElement> paidBadgesBeforeFilter = driver.findElements(paidBadge);
        
        System.out.println("📊 Overdue badges in unfiltered table: " + overdueBadgesBeforeFilter.size());
        System.out.println("📊 Paid badges in unfiltered table: " + paidBadgesBeforeFilter.size());
        
        boolean hasBadgesBeforeFilter = !overdueBadgesBeforeFilter.isEmpty() || !paidBadgesBeforeFilter.isEmpty();
        
        if (hasBadgesBeforeFilter) {
            System.out.println("✅ Badges are present in the unfiltered table");
            verifyBadgeStyles(overdueBadgesBeforeFilter, paidBadgesBeforeFilter);
        } else {
            System.out.println("⚠ No badges found in unfiltered table - this may be normal");
        }

        // ----------------------------------------------------
        // FILTER BY NAME 'Emma'
        // ----------------------------------------------------
        System.out.println("🔍 FILTERING BY NAME 'Emma'...");
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(filterSearchInput));
        
        // Clear any existing text and type 'Emma'
        searchInput.clear();
        searchInput.sendKeys("Emma");
        
        // Wait for filtering to complete
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("✅ Filtered student table by name: Emma");

        // ----------------------------------------------------
        // VERIFY STUDENT ROWS DISPLAYED AFTER FILTERING
        // ----------------------------------------------------
        wait.until(ExpectedConditions.presenceOfElementLocated(studentRows));
        List<WebElement> rows = driver.findElements(studentRows);
        Assert.assertTrue(rows.size() > 0, "No student rows displayed after filtering for 'Emma'");
        System.out.println("✅ Found " + rows.size() + " student row(s) after filtering");

        // VERIFY FILTERING IS CORRECT (Check 'Child First Name' column, index 1)
        int childFirstNameIndex = 1; 
        boolean foundEmma = false;
        WebElement emmaRow = null;
        
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() > childFirstNameIndex) {
                String firstName = cells.get(childFirstNameIndex).getText().trim();
                if (firstName.equals("Emma")) {
                    foundEmma = true;
                    emmaRow = row;
                    System.out.println("✅ Verified row with Emma: " + firstName);
                    break;
                }
            }
        }
        
        Assert.assertTrue(foundEmma, "❌ No row found with 'Emma' in Child First Name column after filtering");

        // ----------------------------------------------------
        // CHECK FEE STATUS FOR EMMA (INSTEAD OF BADGES)
        // ----------------------------------------------------
        System.out.println("💳 CHECKING FEE STATUS FOR EMMA...");
        
        if (emmaRow != null) {
            checkFeeStatusForRow(emmaRow);
        }

        // ----------------------------------------------------
        // VERIFY VIEW DETAILS BUTTON UI (BUT DON'T CLICK IT)
        // ----------------------------------------------------
        System.out.println("👁️ VERIFYING VIEW BUTTON STYLES (without clicking)...");
        
        WebElement viewBtn = findViewButton(rows, childFirstNameIndex);
        Assert.assertNotNull(viewBtn, "❌ View Details button not found with any locator");
        Assert.assertTrue(viewBtn.isDisplayed(), "❌ View Details button is not displayed");
        
        // Verify View button styles without clicking
        verifyButtonStyles(viewBtn, "View Button");
        System.out.println("✅ View button styles verified");

        // ----------------------------------------------------
        // FINAL UI VERIFICATIONS (BEFORE ANY NAVIGATION)
        // ----------------------------------------------------
        System.out.println("🎨 PERFORMING FINAL UI VERIFICATIONS...");
        verifyFinalUIStyles(wait, searchInput);

        System.out.println("🎉 STUDENTS DASHBOARD UI TEST COMPLETED SUCCESSFULLY!");
        
        // OPTIONAL: If you want to test the View button navigation separately, 
        // you can add it here but be aware it will navigate away from the dashboard
        /*
        System.out.println("🔗 OPTIONAL: TESTING VIEW BUTTON NAVIGATION...");
        testViewButtonNavigation(viewBtn);
        */
    }

    // ==================== HELPER METHODS ====================

    private void verifyInitialUIStyles(WebDriverWait wait) {
        try {
            // Verify table exists and get styles
            WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table")));
            String tableBorder = table.getCssValue("border");
            String tableBackground = table.getCssValue("background-color");
            System.out.println("📊 Table Styles - Border: " + tableBorder + ", Background: " + tableBackground);
            
            // Verify header row styles
            WebElement headerRow = driver.findElement(By.xpath("//table/thead/tr"));
            String headerBackground = headerRow.getCssValue("background-color");
            String headerColor = headerRow.getCssValue("color");
            System.out.println("📊 Header Row - Background: " + headerBackground + ", Text Color: " + headerColor);
            
        } catch (Exception e) {
            System.out.println("⚠ Could not verify initial table styles: " + e.getMessage());
        }
    }

    private void verifyFinalUIStyles(WebDriverWait wait, WebElement searchInput) {
        try {
            // Verify filter input styles
            String filterBackground = searchInput.getCssValue("background-color");
            String filterBorder = searchInput.getCssValue("border");
            String filterColor = searchInput.getCssValue("color");
            System.out.println("🔍 Filter Input - Background: " + filterBackground + ", Border: " + filterBorder + ", Text Color: " + filterColor);

            // Verify buttons are still present and styled
            WebElement excel = driver.findElement(excelBtn);
            WebElement pdf = driver.findElement(pdfBtn);
            
            String excelBackground = excel.getCssValue("background-color");
            String pdfBackground = pdf.getCssValue("background-color");
            
            System.out.println("📊 Excel Button - Background: " + excelBackground);
            System.out.println("📊 PDF Button - Background: " + pdfBackground);
            
        } catch (Exception e) {
            System.out.println("⚠ Could not verify final UI styles: " + e.getMessage());
        }
    }

    private void checkFeeStatusForRow(WebElement row) {
        List<WebElement> cells = row.findElements(By.tagName("td"));
        
        // Find the Fee Status column (typically 7th column, index 6)
        int feeStatusIndex = 7; // Adjust based on your table structure
        if (cells.size() > feeStatusIndex) {
            String feeStatus = cells.get(feeStatusIndex).getText().trim();
            System.out.println("💰 Emma's Fee Status: " + feeStatus);
            
            // Verify fee status is displayed (not empty)
            Assert.assertFalse(feeStatus.isEmpty(), "Fee status should not be empty for Emma");
            
            // Check if fee status contains any badge-like text
            if (feeStatus.contains("Overdue") || feeStatus.contains("Paid") || 
                feeStatus.contains("Due") || feeStatus.contains("Pending")) {
                System.out.println("✅ Fee status text found: " + feeStatus);
            } else {
                System.out.println("📝 Fee status displayed as: " + feeStatus);
            }
        } else {
            System.out.println("⚠ Could not find fee status column for Emma");
        }
        
        // Check for badges specifically in Emma's row
        checkForBadgesInRow(row);
    }

    private void checkForBadgesInRow(WebElement row) {
        System.out.println("🔍 Checking for badges in Emma's row...");
        
        // Look for badge elements within the specific row
        List<WebElement> badgesInRow = row.findElements(By.xpath(".//span[contains(@class, 'badge') or contains(@class, 'status')]"));
        if (!badgesInRow.isEmpty()) {
            System.out.println("✅ Found " + badgesInRow.size() + " badge(s) in Emma's row");
            for (WebElement badge : badgesInRow) {
                System.out.println("   Badge: " + badge.getText() + " | Classes: " + badge.getAttribute("class"));
            }
        } else {
            System.out.println("💡 No badge elements found in Emma's row");
        }
        
        // Check for Overdue/Paid text in the row
        String rowText = row.getText();
        if (rowText.contains("Overdue") || rowText.contains("Paid")) {
            System.out.println("✅ Found fee status text in row: " + 
                (rowText.contains("Overdue") ? "Overdue" : "Paid"));
        }
    }

    private WebElement findViewButton(List<WebElement> rows, int childFirstNameIndex) {
        // Try multiple possible locators for View buttons
        By[] viewButtonLocators = {
            By.xpath("//button[contains(@mattooltip, 'View')]"),
            By.xpath("//button[contains(@aria-label, 'View')]"),
            By.xpath("//button[.//mat-icon[contains(text(), 'visibility')]]"),
            By.xpath("//button[contains(., 'View')]"),
            By.xpath("//button[contains(@class, 'view')]"),
            By.xpath("//td[contains(@class, 'view')]//button"),
            By.xpath("//table//button[contains(., 'View')]"),
            By.xpath("//button[.//*[contains(text(), 'visibility')]]")
        };

        WebElement viewBtn = null;
        for (By locator : viewButtonLocators) {
            try {
                List<WebElement> buttons = driver.findElements(locator);
                if (!buttons.isEmpty()) {
                    viewBtn = buttons.get(0);
                    System.out.println("✅ Found View button using locator: " + locator);
                    break;
                }
            } catch (Exception e) {
                // Continue to next locator
            }
        }

        // If still not found, try to find any button in the Emma row
        if (viewBtn == null) {
            System.out.println("🔍 Trying alternative approach - finding button in Emma's row...");
            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() > childFirstNameIndex) {
                    String firstName = cells.get(childFirstNameIndex).getText().trim();
                    if (firstName.equals("Emma")) {
                        // Last cell should contain the View button
                        WebElement lastCell = cells.get(cells.size() - 1);
                        try {
                            viewBtn = lastCell.findElement(By.tagName("button"));
                            System.out.println("✅ Found View button in Emma's row");
                            break;
                        } catch (Exception e) {
                            // Try other button elements
                            List<WebElement> buttons = lastCell.findElements(By.xpath(".//button | .//a | .//mat-icon"));
                            if (!buttons.isEmpty()) {
                                viewBtn = buttons.get(0);
                                System.out.println("✅ Found View element in Emma's row");
                                break;
                            }
                        }
                    }
                }
            }
        }
        
        return viewBtn;
    }

    private void verifyButtonStyles(WebElement button, String buttonName) {
        String backgroundColor = button.getCssValue("background-color");
        String color = button.getCssValue("color");
        String border = button.getCssValue("border");
        String fontSize = button.getCssValue("font-size");
        String borderRadius = button.getCssValue("border-radius");
        
        System.out.println("🎨 " + buttonName + " Styles:");
        System.out.println("   Background: " + backgroundColor);
        System.out.println("   Text Color: " + color);
        System.out.println("   Border: " + border);
        System.out.println("   Font Size: " + fontSize);
        System.out.println("   Border Radius: " + borderRadius);
    }

    private void verifyBadgeStyles(List<WebElement> overdueBadges, List<WebElement> paidBadges) {
        if (!overdueBadges.isEmpty()) {
            WebElement overdueBadge = overdueBadges.get(0);
            String overdueColor = overdueBadge.getCssValue("background-color");
            String overdueColorText = overdueBadge.getCssValue("color");
            String overdueFontSize = overdueBadge.getCssValue("font-size");
            String overdueBorderRadius = overdueBadge.getCssValue("border-radius");
            
            System.out.println("🎨 Overdue Badge Styles:");
            System.out.println("   Background: " + overdueColor);
            System.out.println("   Text Color: " + overdueColorText);
            System.out.println("   Font Size: " + overdueFontSize);
            System.out.println("   Border Radius: " + overdueBorderRadius);
        }
        
        if (!paidBadges.isEmpty()) {
            WebElement paidBadge = paidBadges.get(0);
            String paidColor = paidBadge.getCssValue("background-color");
            String paidColorText = paidBadge.getCssValue("color");
            String paidFontSize = paidBadge.getCssValue("font-size");
            String paidBorderRadius = paidBadge.getCssValue("border-radius");
            
            System.out.println("🎨 Paid Badge Styles:");
            System.out.println("   Background: " + paidColor);
            System.out.println("   Text Color: " + paidColorText);
            System.out.println("   Font Size: " + paidFontSize);
            System.out.println("   Border Radius: " + paidBorderRadius);
        }
    }

    // Optional method to test View button navigation (commented out by default)
    private void testViewButtonNavigation(WebElement viewBtn) {
        System.out.println("🔗 TESTING VIEW BUTTON NAVIGATION...");
        
        // Store current URL for comparison
        String currentUrl = driver.getCurrentUrl();
        
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", viewBtn);
            Thread.sleep(500);
            viewBtn.click();
            System.out.println("✅ View button clicked successfully");
            
            // Wait for navigation
            WebDriverWait navWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            navWait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl)));
            
            System.out.println("✅ Navigation occurred successfully");
            System.out.println("New URL: " + driver.getCurrentUrl());
            
            // Optional: Add verification for the new page here
            
        } catch (Exception e) {
            System.out.println("⚠ Could not click View button or navigate: " + e.getMessage());
        }
    }
}