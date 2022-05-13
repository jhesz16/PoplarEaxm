package Pages;

import Driver.Setup;
import org.openqa.selenium.WebDriver;

public class GlobalHelpers {

    public static WebDriver driver;

    public GlobalHelpers() {
        this.driver = Setup.getDriver();
    }


}
