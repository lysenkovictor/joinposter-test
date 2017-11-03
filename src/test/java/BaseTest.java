import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.authorization.LoginPage;
import utils.properties.IProperties;

import java.io.File;
import java.io.IOException;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by victor on 01.11.2017.
 */
public class BaseTest {

    IProperties configProperties = ConfigFactory.create(IProperties.class, System.getProperties());

    @BeforeMethod
    public void setUp() {
        Configuration.timeout = configProperties.TIMEOUT();

        switch (configProperties.BROWSER()) {
            case 0:
                Configuration.browser = "utils.driverprovider.FirefoxDriverProvider";
                break;

            case 1:
                Configuration.browser = "utils.driverprovider.ChromeDriverProvider";
                break;

            default:
                Configuration.browser = "utils.DriverProvider.FirefoxDriverProvider";
        }
        open(configProperties.START_PAGE());
        preconditionForAuthorization();
    }


    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (!result.isSuccess()) {
            screenshot();
        }
        WebDriverRunner.closeWebDriver();
    }

    @Attachment(type = "image/png")
    public byte[] screenshot() throws IOException {
        File screenshot = Screenshots.takeScreenShotAsFile();
        return Files.toByteArray(screenshot);
    }

    @Step("{nameStep}")
    public void addStepToTheReport(String nameStep) {
    }

    public void preconditionForAuthorization() {
        LoginPage login = new LoginPage();
        login.authorize(configProperties.EMAIL_LOGIN(), configProperties.PASSWORD());
    }
}
