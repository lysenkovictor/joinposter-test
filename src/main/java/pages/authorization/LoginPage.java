package pages.authorization;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by victor on 01.11.2017.
 */
public class LoginPage {

    SelenideElement fieldLoginEmail = $(By.name("email"));
    SelenideElement fieldPassword = $(By.name("password"));
    SelenideElement buttonLogin = $(By.xpath((".//input[@type='submit']")));

    public void fillFieldEmail(String email) {
        fieldLoginEmail.setValue(email);
    }

    public void fillFielPassword(String password) {
        fieldPassword.setValue(password);
    }

    public void pressEnter() {
        buttonLogin.click();
    }

    public void authorize(String email, String password) {
        fillFieldEmail(email);
        fillFielPassword(password);
        pressEnter();
    }

}
