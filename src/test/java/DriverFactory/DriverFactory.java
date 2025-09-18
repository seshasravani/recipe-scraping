package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private static ThreadLocal<String> tlBrowser = new ThreadLocal<>();

    public WebDriver init_driver (String browser)
    {
        setBrowser(browser);

        switch (browser.toLowerCase()) {
            case "chrome" : 
            	tlDriver.set(new ChromeDriver());
                break;
            case "edge" :
            	tlDriver.set(new EdgeDriver());
            	break;
            case "firefox":
            	tlDriver.set(new FirefoxDriver());
            	break;
            default: throw new IllegalArgumentException("Please specify correct browser: " + browser);
        }
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        return getDriver();
    }

    public static void setBrowser(String browser) {
        tlBrowser.set(browser);
    }

    public static String getBrowser() {
        return tlBrowser.get();
    }
    public static WebDriver getDriver()
    {
        return tlDriver.get();
    }

    public static void quitDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove();
        }
    }
}
