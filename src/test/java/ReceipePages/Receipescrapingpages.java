package ReceipePages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.BaseLogger;
import Utilities.ConfigReader;
import Utilities.ElementsUtil;
import DriverFactory.DriverFactory;   // ✅ Correct import

public class Receipescrapingpages extends BaseLogger {

    private WebDriver driver;
    private ElementsUtil elementsUtil;
    private String url;

    // ✅ Locator for Categories dropdown
  //  private By categoriesDropdown = By.xpath("//a[text()='Categories']");
    
    
 // private By categoriesDropdown = By.xpatha[contains(@class,'dropdown-toggle') and contains(normalize-space(text()), 'Categories')]
    
   // private By categoriesDropdown = By.xpath("//a[normalize-space(text())='Categories']");
    
    private By categoriesDropdown = By.xpath("//a[contains(@class,'dropdown-toggle') and contains(normalize-space(text()), 'Categories')]");
    
   // private By categoriesMenuDropdown = By.xpath("//a[contains(@class,'dropdown-toggle') and @role='button' and normalize-space(text())='Categories']");

    
 // ✅ Menu Dropdown locator for "Categories"
    private By categoriesMenuDropdown = By.xpath(
        "//a[contains(@class,'dropdown-toggle') and @role='button' and normalize-space(text())='Categories']"
    );



    public Receipescrapingpages() {
        this.driver = DriverFactory.getDriver();   // get driver from factory
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

    // Action method for Categories dropdown
//    public void clickCategoriesDropdown() {
//        log.info("Clicking on Categories dropdown link");
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.elementToBeClickable(categoriesDropdown)).click();
//    }
    
 // ✅ Click method for Categories Menu Dropdown
    public void clickCategoriesMenuDropdown() {
        log.info("Clicking on Categories menu dropdown");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(categoriesMenuDropdown)).click();
    }

}
