package com.assignment.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResultsPage extends BasePage {


    @FindBy(xpath = "//select[@name='OrderBy' or contains(@id, 'orderBy') or contains(@class, 'order')]")
    private WebElement sortDropdown;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public void sortByCheapest() {
        WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        WebElement dropdownToggle = localWait.until(ExpectedConditions.elementToBeClickable(
            By.id("order-dropdown")
        ));
        dropdownToggle.click();
        
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        
        // 2. LÉPÉS: Megkeressük a lenyílt listában az 'Olcsók előre' opciót és rákattintunk
        WebElement cheapestOption = localWait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(text(), 'Olcsók')]")
        ));
        cheapestOption.click();

        try { Thread.sleep(1500); } catch (InterruptedException e) {} 
    }

}