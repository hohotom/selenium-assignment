package com.assignment.tests;

import com.assignment.pages.HomePage;
import com.assignment.pages.SearchResultsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.assignment.utils.TestListener;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class SearchAndFilterTest extends BaseTest {

    @Test
    public void testSearchAndAdvancedInteractions() {
        HomePage homePage = new HomePage(driver);

        homePage.scrollToFooter();
        
        homePage.hoverOverCategory();

        String searchQuery = "iPhone 15";
        homePage.searchFor(searchQuery);

        SearchResultsPage resultsPage = new SearchResultsPage(driver);
        System.out.println(driver.getTitle());
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("iphone"), "The title should contain the searched word.");

        resultsPage.sortByCheapest();
        
    }
}