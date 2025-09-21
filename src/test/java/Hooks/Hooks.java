package Hooks;

import java.lang.reflect.Method;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import DriverFactory.DriverFactory;
import Utilities.BaseLogger;
import Utilities.ConfigReader;

public class Hooks extends BaseLogger {
    protected WebDriver driver;
    protected Properties prop;

    @BeforeClass(alwaysRun = true)
    public void loadConfig() {
        prop = new ConfigReader().init_prop();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void startDriver(@Optional("") String browser, Method method) {
        // log test name
        log.info("Starting test: " + method.getName());

        // decide browser: TestNG param > config > default "chrome"
        String browserName = (browser != null && !browser.isEmpty())
                ? browser
                : prop.getProperty("browser", "chrome");

        // create driver
        DriverFactory.setBrowser(browserName);
        driver = new DriverFactory().init_driver(browserName);
        log.info("Launched browser: " + browserName);
    }

    @AfterMethod(alwaysRun = true)
    public void quitDriver() {
        log.info("Closing browser");
        DriverFactory.quitDriver();
    }
}



