package pages.accounts;

import com.codeborne.selenide.SelenideElement;
import entities.Account;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * Created by victor on 01.11.2017.
 */
public class AccountModifyPage {

    protected SelenideElement fieldNameAccount = $(By.id("name"));
    protected SelenideElement dropdownCurrency = $(By.name("currency_id"));
    protected SelenideElement dropdownTypeAccount = $(By.name("type"));
    protected SelenideElement fieldBalanceStart = $(By.name("balance_start"));
    protected SelenideElement fieldRateAcquiring = $(By.name("percent_acquiring"));


    public void fillFieldNameAccount(String nameAccount) {
        fieldNameAccount.setValue(nameAccount);
    }

    public void chooseCurrency(String currency) {
        dropdownCurrency.selectOptionContainingText(currency);
    }

    public void chooseTypeAccount(String typeAccount) {
        dropdownTypeAccount.selectOptionContainingText(typeAccount);
    }

    public void fillFieldBalanceStart(String amountStart) {
        fieldBalanceStart.setValue(amountStart);
    }

    public void fillFieldRateAcquiring(String amount) {
        if(amount != null) {
            fieldRateAcquiring.setValue(amount);
        }
    }

    public String getSelectedNameAccount() {
        return fieldNameAccount.getValue().trim();
    }

    public String getSelectedCurrency() {
        return dropdownCurrency.getSelectedText();
    }

    public String getSelectedTypeAccount() {
      return dropdownTypeAccount.getSelectedText().trim();
    }

    public String getSelectedBalanceStart() {
      return fieldBalanceStart.getValue().trim();
    }

    public String getSelectedRateAcquiring() {
       return fieldRateAcquiring.getValue().trim();
    }

    public void clickButtonSubmit() {
        $(By.xpath(".//input[@type='submit']")).click();
    }

    public SelenideElement getRateAcquiring() {
        return fieldRateAcquiring;
    }


    public void fillFormDataAccountCashOrCard(Account account) {
        fillFieldNameAccount(account.getNameAccount());
        chooseCurrency(account.getCurrencyAccount());
        chooseTypeAccount(account.getTypeAccount());
        fillFieldBalanceStart(account.getBalanceStart());
    }

    public void fillFormDataAccountCashlessPayments(Account account) {
        fillFieldNameAccount(account.getNameAccount());
        chooseCurrency(account.getCurrencyAccount());
        chooseTypeAccount(account.getTypeAccount());
        fillFieldBalanceStart(account.getBalanceStart());
        fillFieldRateAcquiring(account.getRateAcquiring());
    }
}
