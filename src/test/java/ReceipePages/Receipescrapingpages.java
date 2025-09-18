package ReceipePages;

import org.openqa.selenium.WebDriver;

import Hooks.Hooks;
import Utilities.BaseLogger;
import Utilities.ConfigReader;
import Utilities.ElementsUtil;

public class Receipescrapingpages extends BaseLogger {

	private WebDriver driver;
	ElementsUtil elementsUtil;
		
		
	public Receipescrapingpages(WebDriver driver) {
        this.driver = driver;
        this.elementsUtil = new ElementsUtil(driver);
    } 
		
	String url = ConfigReader.getProperty("url");
	
   public void getURL()
	    {
		   
	       log.info("Navigating to the URL of Tarladalal");
	        driver.get(url);
	    }
	 public String getCurrentURL() {
	        return driver.getCurrentUrl();
	    }
}
