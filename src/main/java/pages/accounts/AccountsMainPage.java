package pages.accounts;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Created by victor on 01.11.2017.
 */
public class AccountsMainPage {

    private SelenideElement buttonAddAccount = $(By.xpath("//button[contains(@class, 'btn btn-sm btn-green')]"));
    private SelenideElement fieldSearch = $(By.name("query"));
    private SelenideElement labelAccount = $(By.className("pull-left"));
    private SelenideElement labelQuantityAccount = $(By.className("quantity"));
    private ElementsCollection linkDeleteAccount = $$(By.xpath(".//a[@class='pseudo-link delete-book-account']"));
    private SelenideElement alertMassageSuccess = $(By.xpath(".//div[@class='alert alert-success alert-dismissable']/*[contains(text(),'Счет')]"));
    private SelenideElement buttonListForDeleteAccounts = $(By.xpath("//div[@class='btn-group ib']"));
    private SelenideElement alertClose = $(By.className("close"));
    private SelenideElement buttonConfirmAlert = $(By.xpath(".//button[@class='btn btn-green btn-confirm']"));
    private SelenideElement textBalanceOnAccounts = $(By.xpath(".//h3[contains(text(), 'Баланс')]"));


    public EditAccountPage clickLinkEdit(String nameAccount) {
        $(By.xpath(".//*[contains(text(), '" + nameAccount + "')]/../td/a")).click();
        return new EditAccountPage();
    }

    public void clickButtonDropdownForDeleteAccount(String nameAccount) {
        $(By.xpath("//*[contains(text(),'" + nameAccount + "')]/..//td//button")).click();
    }

    public void clickLinkDeleteAccount() {
        linkDeleteAccount.stream()
                .filter(el -> el.isDisplayed())
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("element is not Displayes")).click();
    }

    public AddAccountPage clickButtonAddAccount() {
        buttonAddAccount.click();
        return new AddAccountPage();
    }

    public String getTextQuantityAccount() {
        return labelQuantityAccount.getText().trim();
    }

    public SelenideElement getNameAccount(String nameAccount) {
        return $(By.xpath(".//td[contains(text(),'" + nameAccount + "')]"));
    }

    public String getTextAlertMassageSuccess() {
        return alertMassageSuccess.getText();
    }

    public String getTextBalanceOnAccounts() {
        return textBalanceOnAccounts.getText();
    }

    public void deleteSelectedAccount(String nameAccount) {
        clickButtonDropdownForDeleteAccount(nameAccount);
        clickLinkDeleteAccount();
    }

    public void clickButtonConfirmAlert() {
        buttonConfirmAlert.click();
    }

    public void deleteAllAccount() {
        Integer quantityAccount = Integer.parseInt(getTextQuantityAccount().trim());
        if (quantityAccount != 0) {
            for (int i = 0; i < quantityAccount; i++) {
                buttonListForDeleteAccounts.click();
                clickLinkDeleteAccount();
                clickButtonConfirmAlert();
                alertClose.click();
            }
        }
    }


    public void searchAccount(String nameOrTypeOrBalanceAccount) {
        fieldSearch.setValue(nameOrTypeOrBalanceAccount);
    }

}
