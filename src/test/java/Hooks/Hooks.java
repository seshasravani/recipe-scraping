package Hooks;

import java.lang.reflect.Method;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import DriverFactory.DriverFactory;

import Utilities.BaseLogger;
import Utilities.ConfigReader;


public class Hooks extends BaseLogger {
	private DriverFactory driverFactory;
    private WebDriver driver;
    private ConfigReader configReader;
    Properties prop;
    private static final String LOG_FILE_PATH = "target/logs/execution.log";
   
    @BeforeClass(alwaysRun = true)
    public void getProperty() {
        configReader = new ConfigReader();
        prop = configReader.init_prop();
    }

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void setBrowser(@Optional("chrome") String browser) {
        DriverFactory.setBrowser(browser);
        //log.info("Running tests on: " + browser + " | Thread ID: " + Thread.currentThread().getId());
    }

    @BeforeMethod(alwaysRun = true)
    public void launchBrowser() {
        String browserName = DriverFactory.getBrowser();
        if (browserName == null || browserName.isEmpty()) {
            browserName = prop.getProperty("browser");
        }
        driverFactory = new DriverFactory();
        driver = driverFactory.init_driver(browserName);
       //log.info("Browser launched: " + browserName + " | Thread: " + Thread.currentThread().getId());
    }
    
    @BeforeMethod(alwaysRun = true)
    public void logTestStart(Method method) {
       log.info("Starting test: " + method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
      log.info("After all tests - quitting driver");
        DriverFactory.quitDriver();
    }

}
