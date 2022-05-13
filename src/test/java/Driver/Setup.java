package Driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.WebDriverManagerException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public class Setup {

    protected static ThreadLocal<WebDriver> Driver = new ThreadLocal<>();
    private static Properties prop;

    public static void getDriver(String Browser) throws IOException, InterruptedException {
        switch (Browser.toUpperCase(Locale.ROOT)) {
            case "CHROME": {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                Driver.set(new ChromeDriver(options));
                break;
            }
            default:
                throw new WebDriverManagerException(("Browser " + Browser + " not found"));

        }
        try {
            Driver.get().get(getUrl());
        } catch (WebDriverException e) {
            Driver.get().get(getUrl());
        }

    }

    public static WebDriver getDriver() {
        return Driver.get();
    }

    public static String getProperty(String property) throws IOException {
        FileInputStream fis = null;
        prop = null;
        try {
            fis = new FileInputStream("src/test/resources/config.properties");
            prop = new Properties();
            prop.load(fis);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            fis.close();
        }
        return prop.getProperty(property);
    }

    public static String getBrowser() throws IOException {
        return getProperty("browserConfig");
    }

    public static String getUrl() throws IOException {
        return getProperty("URL");
    }

    public static void killDriver() {

        if (Driver != null) {
            Driver.get().close();
            Driver.get().quit();
        }
    }
}
