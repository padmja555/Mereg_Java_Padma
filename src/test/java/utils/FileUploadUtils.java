package utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FileUploadUtils {

    private final WebDriver driver;
    private final String FILE_INPUT_XPATH = "//app-file-upload/input";

    public FileUploadUtils(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Uploads a file by removing the hidden class using JavaScript before sending keys.
     * This method directly translates the logic from your Python code.
     * @param absoluteFilePath The absolute path to the file on the local machine (e.g., "C:\\Downloadfile\\newfile.txt").
     */
    public void uploadFileByRemovingHiddenClass(String absoluteFilePath) {
        System.out.println("Attempting file upload using JavaScript to remove hidden class...");

        // 1. Get the JavascriptExecutor instance
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 2. XPath to find the element and remove the class
        // Note: Java string literal handling requires extra escaping for backslashes and quotes
        String removeHiddenClassScript = 
            "document.evaluate('" + FILE_INPUT_XPATH + "', document, null, " +
            "XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.classList.remove('input-fileupload-hidden')";

        try {
            // Execute the script to make the input field visible/interactable
            js.executeScript(removeHiddenClassScript);
            System.out.println("✅ JavaScript executed: 'input-fileupload-hidden' class removed.");

            // 3. Find the element now that it's visible/interactable
            WebElement fileInput = driver.findElement(By.xpath(FILE_INPUT_XPATH));

            // 4. Send the file path
            fileInput.sendKeys(absoluteFilePath);
            System.out.println("✅ File path sent: " + absoluteFilePath);

        } catch (Exception e) {
            System.err.println("❌ File upload failed via JS removal: " + e.getMessage());
            // Optionally re-throw or handle as needed
        }
    }
}
