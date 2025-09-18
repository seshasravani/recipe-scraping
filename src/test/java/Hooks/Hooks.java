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
    protected WebDriver driver; // made protected so tests can access
    private DriverFactory driverFactory;
    private ConfigReader configReader;
    protected Properties prop;

    @BeforeClass(alwaysRun = true)
    public void getProperty() {
        configReader = new ConfigReader();
        prop = configReader.init_prop();
    }

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public void setBrowser(@Optional("chrome") String browser) {
        DriverFactory.setBrowser(browser);
    }

    @BeforeMethod(alwaysRun = true)
    public void launchBrowser() {
        String browserName = DriverFactory.getBrowser();
        if (browserName == null || browserName.isEmpty()) {
            browserName = prop.getProperty("browser");
        }
        driverFactory = new DriverFactory();
        driver = driverFactory.init_driver(browserName);
    }

    @BeforeMethod(alwaysRun = true)
    public void logTestStart(Method method) {
        log.info("Starting test: " + method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        log.info("After test - quitting driver");
        DriverFactory.quitDriver();
    }
}
