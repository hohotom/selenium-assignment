package com.assignment.tests;

import com.assignment.pages.HomePage;
import com.assignment.pages.LoginPage;
import com.assignment.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;
import com.assignment.utils.TestListener;
import org.testng.annotations.Listeners;
import org.openqa.selenium.JavascriptExecutor;

@Listeners(TestListener.class)
public class LoginTest extends BaseTest {

    @Test(priority = 1)
    public void testSuccessfulLogin() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickLogin();
        
        loginPage.login(
            ConfigReader.getProperty("testEmail"), 
            ConfigReader.getProperty("testPassword")
        );
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isLoggedIn = wait.until(ExpectedConditions.urlContains("users"));
        Assert.assertTrue(isLoggedIn, "User should be logged in and redirected to the profile page");
    }

    @Test(priority = 2, dependsOnMethods = "testSuccessfulLogin") 
    public void testLogout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        
        WebElement logoutLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(@href, 'logout') or contains(text(), 'Kijelentkezés')]")
            ));
            
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", logoutLink);
        
        boolean isLogoutGone = wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.xpath("//a[contains(@href, 'logout') or contains(text(), 'Kijelentkezés')]")
        ));

        
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[contains(@href, 'belepes') or contains(text(), 'Belépés') or contains(@class, 'login')]")
        ));
        Assert.assertTrue(isLogoutGone, "Error: The logout button is still visible!");
        Assert.assertTrue(loginButton.isDisplayed(), "Success: The login button is visible again on the homepage!");
    }
}