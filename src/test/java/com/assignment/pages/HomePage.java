package com.assignment.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait; 
import java.time.Duration;

public class HomePage extends BasePage {

    @FindBy(xpath = "//a[contains(@href, '/belepes/') or contains(@class, 'login')]")
    private WebElement loginLink;

    @FindBy(name = "st")
    private WebElement searchInput;

    @FindBy(xpath = "//button[contains(@class, 'btn-search') or contains(@class, 'search-button')]")
    private WebElement searchButton;

    @FindBy(xpath = "(//a[contains(@href, '-c')])[1]")
    private WebElement firstCategoryMenuLink;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public LoginPage clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
        return new LoginPage(driver);
    }

    public void searchFor(String query) {
        wait.until(ExpectedConditions.visibilityOf(searchInput)).clear();
        searchInput.sendKeys(query);       
        searchInput.submit();

        try {
  
            Thread.sleep(2000); 
            
 
            WebDriverWait searchWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            searchWait.until(ExpectedConditions.urlContains("st="));

            searchWait.until(ExpectedConditions.not(
                ExpectedConditions.titleContains("Just a moment")
            ));
            
            Thread.sleep(500);


            
        } catch (Exception e) {
            System.out.println("Time out occurred.");
        }
    }

    public void hoverOverCategory() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", firstCategoryMenuLink);
        
        wait.until(ExpectedConditions.visibilityOf(firstCategoryMenuLink));
        

        Actions actions = new Actions(driver);
        actions.moveToElement(firstCategoryMenuLink).perform();
        
        try { Thread.sleep(5000); } catch (InterruptedException e) {}

        String textDecoration = firstCategoryMenuLink.getCssValue("text-decoration");
        
        // A TestNG ellenőrzi, hogy a kapott CSS értékben szerepel-e az 'underline' (aláhúzás) szó
        org.testng.Assert.assertTrue(textDecoration.contains("underline"), 
            "Error: The hover action failed, the category text was not underlined!");
    }

    public void scrollToFooter() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
}