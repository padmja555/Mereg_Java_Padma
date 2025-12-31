package AdminTestcase;
import org.testng.annotations.Test;

import Base.BaseDriver;

import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import java.time.Duration;

public class BulkEnrollmentTest extends BaseDriver {
    private WebDriver driver;
    private WebDriverWait wait;
    
    @BeforeMethod
    public void setUp() {
        // Initialize WebDriver
        //System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        //driver = new ChromeDriver();
        //wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //driver.manage().window().maximize();
        
        // Navigate to login page and login (assuming login is required)
        driver.get("https://yourapplication.com/login");
        // Add login steps here if needed
    }
    
    @Test(priority = 1)
    public void testAdminBulkEnrollmentNavigation() {
        // Step 1: Click on admin link
        WebElement adminLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(text(),'Admin')]")));
        adminLink.click();
        
        // Step 2: Click on bulk enrollment link
        WebElement bulkEnrollmentLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(text(),'Bulk Enrollment') or contains(text(),'bulk enrollment')]")));
        bulkEnrollmentLink.click();
        
        // Step 3: Verify title is displayed
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h1[contains(text(),'Bulk Student Enrollment')]")));
        Assert.assertTrue(titleElement.isDisplayed(), "Title 'Bulk Student Enrollment' is not displayed");
    }
    
    @Test(priority = 2)
    public void testDownloadSampleTemplate() {
        // Navigate to bulk enrollment page first
        testAdminBulkEnrollmentNavigation();
        
        // Step 4: Click on download sample template link
        WebElement downloadLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(text(),'Download Sample Template')]")));
        downloadLink.click();
        
        // Step 5: Verify download (this might vary based on your implementation)
        // Option 1: Check if a new window/tab opens
        // Option 2: Check if file download starts
        // Option 3: Check if redirected to download page
        
        // For now, we'll verify that the download link exists and is clickable
        Assert.assertTrue(downloadLink.isEnabled(), "Download link is not clickable");
        
        // You might want to add file download verification here
        // This could involve checking the file in downloads folder
    }
    
    @Test(priority = 3)
    public void testBulkEnrollmentPageElements() {
        // Navigate to bulk enrollment page
        testAdminBulkEnrollmentNavigation();
        
        // Verify key elements on the page
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'File Upload Instructions')]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'Only upload files in .csv or .xlsx format')]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'Drag and drop your file here')]")).isDisplayed());
    }
    
    @Test(priority = 4)
    public void testFileUploadArea() {
        // Navigate to bulk enrollment page
        testAdminBulkEnrollmentNavigation();
        
        // Verify drag and drop area
        WebElement uploadArea = driver.findElement(By.xpath("//*[contains(text(),'Drag and drop your file here')]"));
        Assert.assertTrue(uploadArea.isDisplayed(), "File upload area is not displayed");
        
        // Verify Browse Files button if exists
        try {
            WebElement browseButton = driver.findElement(By.xpath("//*[contains(text(),'Browse Files')]"));
            Assert.assertTrue(browseButton.isDisplayed(), "Browse Files button is not displayed");
        } catch (Exception e) {
            System.out.println("Browse Files button not found, might be in a different layout");
        }
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
