package utils.driverprovider;

import com.codeborne.selenide.WebDriverProvider;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by victor on 01.11.2017.
 */
public class FirefoxDriverProvider implements WebDriverProvider{

    @Override
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
        FirefoxDriverManager.getInstance().setup();
        return new FirefoxDriver();
    }
}
