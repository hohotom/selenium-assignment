package com.assignment.tests;

import com.assignment.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.Cookie;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {
    protected static WebDriver driver;

    public WebDriver getDriver() {
        return this.driver;
    }

    @BeforeSuite 
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless=new"); needs to be commented out for Cloudflare to work properly, otherwise can't manually bypass Cloudflare's anti-bot page, which causes tests to fail.
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        String env = System.getProperty("env", "local");

        if (env.equalsIgnoreCase("docker")) {
            try {
                driver = new RemoteWebDriver(new URL("http://host.docker.internal:4444/wd/hub"), options);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("Invalid Docker Selenium URL!");
            }
        } else {
            driver = new ChromeDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(ConfigReader.getProperty("baseUrl"));

        try { 
            System.out.println("Várakozás a Cloudflare csendes lefutására...");
            Thread.sleep(5000); 
        } catch (InterruptedException e) { e.printStackTrace(); }


        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(text(), 'Elfogadás és bezárás') or contains(text(), 'Elfogadom')]")
            ));
            acceptButton.click();
            System.out.println("Cookie consent accepted.");
        } catch (Exception e) {
            System.out.println("No cookie consent popup found or error while accepting cookies.");
        }

        System.out.println("Number of cookies after accept: " + driver.manage().getCookies().size());
            
        Cookie myTestCookie = new Cookie("AssignmentCookie", "6PointsValue");
        driver.manage().addCookie(myTestCookie);
        
        System.out.println("Value of added cookie: " + driver.manage().getCookieNamed("AssignmentCookie").getValue());
        
        driver.manage().deleteCookieNamed("AssignmentCookie");
        System.out.println("Value of deleted cookie: " + driver.manage().getCookieNamed("AssignmentCookie"));
    }

    @BeforeMethod 
    public void SlowDown() {
        try {
            System.out.println("waiting for 3 seconds...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterSuite 
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}