package AdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Signpage {
    private WebDriver driver;

    public Signpage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickCreateAccount() {
        driver.findElement(By.xpath("//a[@routerlink='/createaccount' and text()='Create an Account']")).click();
    }

    public void fillForm(String firstName, String lastName, String email, String countryCode,
                         String phone, String address, String city, String country,
                         String state, String zip, String password) throws InterruptedException {

        driver.findElement(By.name("firstName")).sendKeys(firstName);
        driver.findElement(By.name("lastName")).sendKeys(lastName);
        driver.findElement(By.xpath("//input[@placeholder='sample@xyz.com']")).sendKeys(email);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@placeholder='+1']")).sendKeys(countryCode);
        driver.findElement(By.xpath("//input[@placeholder='1234567890']")).sendKeys(phone);
        driver.findElement(By.xpath("//input[@placeholder='Enter street address']")).sendKeys(address);
        driver.findElement(By.xpath("//input[@placeholder='Enter city']")).sendKeys(city);
        driver.findElement(By.name("country")).sendKeys(country);
        driver.findElement(By.name("state")).sendKeys(state);
        driver.findElement(By.xpath("//input[@placeholder='Enter zip code']")).sendKeys(zip);
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(password);
        driver.findElement(By.xpath("//input[@placeholder='Confirm Password']")).sendKeys(password);
    }

    public void submitForm() {
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    public void enterOTP(String otp) throws InterruptedException {
        WebElement otpField = driver.findElement(By.xpath("//input[@id='otp' and @formcontrolname='otp' and @placeholder='000000']"));
        otpField.clear();
        for (char c : otp.toCharArray()) {
            otpField.sendKeys(Character.toString(c));
            Thread.sleep(100);
        }
    }

    public void clickVerifyButton() {
        WebElement verifyButton = driver.findElement(By.xpath("//button[@type='submit' and contains(@class, 'btn-primary') and normalize-space()='Create Account']"));
        verifyButton.click();
    }
}
