package ru.ozon.framework.managers;

import org.apache.commons.exec.OS;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.ozon.framework.constants.APP_PROPS;

public class DriverManager {
    private WebDriver driver;
    private static DriverManager webDriverManager;
    private final PropManager props = PropManager.getInstance();

    private DriverManager() {}

    public static DriverManager getInstance() {
        if (webDriverManager == null) {
            webDriverManager = new DriverManager();
        }
        return webDriverManager;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    public void quitDriver() {
        if (this.driver != null) {
            this.driver.quit();
            this.driver = null;
        }
    }

    private void initDriver() {
        if (OS.isFamilyWindows()) {
            initDriverFor(APP_PROPS.PATH_DRIVER_WINDOWS.toString());
        } else if (OS.isFamilyMac()) {
            initDriverFor(APP_PROPS.PATH_DRIVER_MAC.toString());
        } else if (OS.isFamilyUnix()) {
            initDriverFor(APP_PROPS.PATH_DRIVER_UNIX.toString());
        }
    }

    private void initDriverFor(String osDriverPath) {
        String browserName = props.getProperty(APP_PROPS.TYPE_BROWSER.toString());
        String pathToDriver = osDriverPath + browserName;

        switch (browserName) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", props.getProperty(pathToDriver));
                driver = new FirefoxDriver();
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", props.getProperty(pathToDriver));
                driver = new ChromeDriver();
                break;
            default:
                Assert.fail("Типа браузера '" + browserName + "' не существует во фреймворке");
        }
    }
}
