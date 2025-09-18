package ReceipeTests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import Hooks.Hooks;
import ReceipePages.Receipescrapingpages;
	
public class Receipescrapingtests extends Hooks{
	private Receipescrapingpages Recipepage;
	private WebDriver driver;
	 
	
		@Test()
		public void Setup() {
			Recipepage.getURL();
		}
	
//	 @BeforeClass
//	    public void Setup() {
//		 Recipepage = new Receipescrapingpages(driver);
//		 Recipepage.getURL();
//		   }

//	    @Test
//	    public void verifyRecipePageURL() {
//	        String currentUrl = Recipepage.getCurrentURL();
//	        log.info("Current URL: " + currentUrl);
//	        Assert.assertTrue(currentUrl.contains("tarladalal"), "URL does not contain 'tarladalal'");
//	    }

		
	}
