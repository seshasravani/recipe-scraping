package ReceipeTests;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Hooks.Hooks;
import ReceipePages.Receipescrapingpages;

public class Receipescrapingtests extends Hooks {

    private Receipescrapingpages recipePage;

    // Initialize page AFTER browser launches
    @BeforeMethod(alwaysRun = true)
    public void setupPage() {
        recipePage = new Receipescrapingpages();
    }

    @Test
    public void openURLTest() throws InterruptedException {
        recipePage.openURL(); // navigate to URL

        // wait for 3 seconds so you can see browser
        Thread.sleep(3000);

        String currentUrl = recipePage.getCurrentURL();
        System.out.println("Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("tarladalal"), "URL does not contain 'tarladalal'");
    }
    
    @Test
    public void clickCategoriesDropdownTest() throws InterruptedException {
        recipePage.openURL(); // navigate to the site
        log.info("Clicking on Categories menu dropdown1");
        // Optional: wait a little to ensure page loads
        Thread.sleep(10000);

        // Click on the Categories dropdown
       // recipePage.clickCategoriesDropdown();
        
        
        
        log.info("Clicking on Categories menu dropdown");

        
        recipePage.clickCategoriesMenuDropdown();
        log.info("Clicked on Categories menu dropdown");
    }
    
 // ✅ Click method for Categories Menu Dropdown
//    public void clickCategoriesMenuDropdown() {
//        log.info("Clicking on Categories menu dropdown");
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.elementToBeClickable(categoriesMenuDropdown)).click();
//    }

}
