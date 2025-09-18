package ReceipePages;

import org.openqa.selenium.WebDriver;
import Utilities.BaseLogger;
import Utilities.ConfigReader;
import Utilities.ElementsUtil;

public class Receipescrapingpages extends BaseLogger {

    private WebDriver driver;
    private ElementsUtil elementsUtil;
    private String url;

    public Receipescrapingpages(WebDriver driver) {
        this.driver = driver;
        this.elementsUtil = new ElementsUtil(driver);
        this.url = ConfigReader.getProperty("url"); // read URL from config
    }

    public void openURL() {
        log.info("Navigating to the URL of Tarladalal");
        driver.get(url);
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }
}
