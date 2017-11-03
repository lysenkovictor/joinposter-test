package utils.driverprovider;

import com.codeborne.selenide.WebDriverProvider;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by victor on 01.11.2017.
 */
public class ChromeDriverProvider implements WebDriverProvider{

    @Override
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {
        ChromeOptions options = new ChromeOptions();
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        ChromeDriverManager.getInstance().setup();
        return new ChromeDriver(options);
    }
}
