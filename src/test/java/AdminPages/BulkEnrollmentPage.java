package AdminPages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BulkEnrollmentPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    @FindBy(xpath = "//h1[contains(text(),'Bulk Student Enrollment')]")
    private WebElement pageTitle;
    
    @FindBy(linkText = "Download Sample Template")
    private WebElement downloadTemplateLink;
    
    @FindBy(xpath = "//*[contains(text(),'Drag and drop your file here')]")
    private WebElement fileUploadArea;
    
    @FindBy(xpath = "//*[contains(text(),'Browse Files')]")
    private WebElement browseFilesButton;
    
    public BulkEnrollmentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }
    
    public boolean isPageLoaded() {
        wait.until(ExpectedConditions.visibilityOf(pageTitle));
        return pageTitle.isDisplayed();
    }
    
    public void clickDownloadTemplate() {
        wait.until(ExpectedConditions.elementToBeClickable(downloadTemplateLink)).click();
    }
    
    public boolean isFileUploadAreaDisplayed() {
        return fileUploadArea.isDisplayed();
    }
    
    public boolean isBrowseFilesButtonDisplayed() {
        return browseFilesButton.isDisplayed();
    }
}