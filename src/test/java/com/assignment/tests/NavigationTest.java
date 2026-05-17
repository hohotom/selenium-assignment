package com.assignment.tests;

import com.assignment.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.assignment.utils.TestListener;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class NavigationTest extends BaseTest {

    @Test(priority = 1)
    public void testStaticPage() {

        driver.get(ConfigReader.getProperty("baseUrl") + "static/elerhetosegeink.html");
        
        WebElement title = driver.findElement(By.tagName("h1"));
        Assert.assertTrue(title.getText().contains("Elérhetőség"), "Static site should contain 'Elérhetőség'.");
    }

    @Test(priority = 2)
    public void testMultiplePagesAndHistory() {
        String[] categories = {
            "mobiltelefon-c3277/", 
            "notebook-c3100/",
            "mosogep-c3167/",
            "led-tv-lcd-tv-plazma-tv-c3164",
            "fulhallgato-fejhallgato-c3109/"
        };

        for (String category : categories) {
            String fullUrl = ConfigReader.getProperty("baseUrl") + category;
            driver.get(fullUrl);
            
            // Basic: page_title (1 pt)
            String pageTitle = driver.getTitle();
            Assert.assertFalse(pageTitle.isEmpty(), "The Page title shouldn't be empty: " + category);
        }

        driver.navigate().back(); 
        Assert.assertTrue(driver.getCurrentUrl().contains("led-tv"), "The browser should have gone back to TVs.");
        

        driver.navigate().forward(); 
        Assert.assertTrue(driver.getCurrentUrl().contains("fulhallgato"), "The browsher should have gone to headphones.");
    }
}