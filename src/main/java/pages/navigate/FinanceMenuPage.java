package pages.navigate;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import pages.accounts.AccountsMainPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

/**
 * Created by victor on 01.11.2017.
 */
public class FinanceMenuPage {

    private SelenideElement dropdownMenu = $(By.xpath(".//*[contains(text(),'Финансы')]"));
    private SelenideElement linkAmount = $(By.xpath(".//a[@href='/manage/finance/accounts']"));

    public void openDropdownMenu() {
        dropdownMenu.click();
    }

    public void clickLinkAcount() {
        linkAmount.click();
    }

    public AccountsMainPage openAccountsMainPage() {
        switchTo().defaultContent();
        openDropdownMenu();
        clickLinkAcount();
        return new AccountsMainPage();
    }


}
