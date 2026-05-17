package com.assignment.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    @FindBy(name = "uname")
    private WebElement emailInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//form//button[@type='submit']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {

        wait.until(ExpectedConditions.elementToBeClickable(emailInput)).clear();
        

        sendKeysWithDelay(emailInput, email);
        
        passwordInput.clear();
        sendKeysWithDelay(passwordInput, password);
        
        try { Thread.sleep(800); } catch (InterruptedException e) {}
        
        passwordInput.sendKeys(Keys.ENTER);
    }


    private void sendKeysWithDelay(WebElement element, String text) {
        for (char ch : text.toCharArray()) {
            element.sendKeys(String.valueOf(ch));
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}