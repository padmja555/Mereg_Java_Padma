package Base;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseDriver {

    public static WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setup(ITestContext context) {
    	try {
    	ConfigReader.loadConfig();
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Add driver to TestNG context so listener can access it
        context.setAttribute("driver", driver);

        // Launch application
        //driver.get("https://mereg-hotfix.netlify.app/");
        //https://mereg-dev.netlify.app/
        //Working HardCoded URL
        //driver.get("https://mereg.netlify.app/");
        String url=ConfigReader.get("baseURL");
        driver.get(url);
        // Wait for page to fully load
        wait.until(d -> ((JavascriptExecutor) d)
            .executeScript("return document.readyState").equals("complete"));
    	}catch(Exception e) {
    		//Padma: TODO Add Logger
    		System.out.println("Error reading property File or creating Base Driver");
    		e.printStackTrace();   // or use a logger
    		throw new RuntimeException("Failed to load config or create WebDriver", e);
    	}
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            //driver.quit();
        }
    
    }
}   



//Present working codde//


//Presentworkingcodebaseclass
