package ReceipeTests;

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
        recipePage = new Receipescrapingpages(driver);
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
}
