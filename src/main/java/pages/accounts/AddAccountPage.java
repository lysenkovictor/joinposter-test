package pages.accounts;

import entities.Account;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;

/**
 * Created by victor on 01.11.2017.
 */
public class AddAccountPage extends AccountModifyPage {

    public void addNewAccount(Account account) {
        fillFormDataAccountCashlessPayments(account);
        clickButtonSubmit();

    }
}
