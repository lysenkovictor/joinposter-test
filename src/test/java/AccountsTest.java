import entities.Account;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.accounts.AccountsMainPage;
import pages.accounts.EditAccountPage;
import pages.navigate.FinanceMenuPage;
import utils.utilmethods.UtilMethod;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;


/**
 * Created by victor on 01.11.2017.
 */

@Feature("Работа со счетами в пункте меню Финансы->Счета")
public class AccountsTest extends BaseTest {

    private FinanceMenuPage financeMenuPage = new FinanceMenuPage();
    private UtilMethod utilMethod = new UtilMethod();
    private Account account;
    private AccountsMainPage accountsMainPage = new AccountsMainPage();


    @DataProvider
    public static Object[][] getDateAccountForTestDelete() {
        return new Object[][]
                {
                        {Account.builder()
                                .nameAccount("Тест-2.Безналичный счет")
                                .typeAccount("Безналичный счет")
                                .currencyAccount("Гривна")
                                .balanceStart("2000")
                                .rateAcquiring("100")
                                .build()
                        },
                        {Account.builder()
                                .nameAccount("Тест-3.Безналичный счет")
                                .typeAccount("Безналичный счет")
                                .currencyAccount("Гривна")
                                .balanceStart("3000")
                                .rateAcquiring("1")
                                .build()
                        }
                };
    }


    @BeforeMethod(dependsOnGroups = "Account")
    public void preconditionTestMethod() {
        addStepToTheReport("Выполнить переход на страницу  Финансы-> Счета");
        preconditionClearData();
    }

    public void preconditionClearData() {
        FinanceMenuPage financeMenuPage = new FinanceMenuPage();
        AccountsMainPage accountsMainPage = financeMenuPage.openAccountsMainPage();
        accountsMainPage.deleteAllAccount();
    }


    @Test (groups = "Account")
    @Story("Проверить добавление счета с типом: \"Безналичный рассчет\"")
    @Description("Проверка, добавления счета, валюта грива, доступно поле для указания комиссии")
    public void a_1_createAccountNonCash() {

        account = Account.builder()
                .nameAccount("Тест-2.Безналичный счет").typeAccount(configProperties.NONCASH_ACCOUNT()).currencyAccount("Гривна").balanceStart("2000")
                .rateAcquiring("100").build();
        addStepToTheReport("Добавить новый счет c типом: " + account.getTypeAccount());
        accountsMainPage.addNewAccount(account);

        addStepToTheReport("Проверить счетчик количества счетов");
        assertThat(
                "Неверное количество счетов",
                accountsMainPage.getTextQuantityAccount(),
                equalTo("1")
        );

        addStepToTheReport("Проверить, что появился аллерт с текстом об успешном добавлении счета");
        assertThat(accountsMainPage.getTextAlertMassageSuccess(),
                equalTo("Счет успешно добавлен")
        );

        addStepToTheReport("Открыть вновь созданный счет и посмотреть, что все данные сохранены из предусловия");
        EditAccountPage editAccountPage = accountsMainPage.clickLinkEdit(account.getNameAccount());

        addStepToTheReport("Проверить, что выбран тип счета из предусловия : " + account.getTypeAccount());
        assertThat(editAccountPage.getSelectedTypeAccount(), equalTo(account.getTypeAccount()));

        addStepToTheReport("Проверить, что имя счета из предусловия: " + account.getNameAccount());
        assertThat(editAccountPage.getSelectedNameAccount(), equalTo(account.getNameAccount()));

        addStepToTheReport("Проверить, что процент за эквайринг из предусловия = " + account.getRateAcquiring());
        assertTrue(new BigDecimal(editAccountPage.getSelectedRateAcquiring())
                .compareTo(new BigDecimal(account.getRateAcquiring())) == 0);

        addStepToTheReport("Проверить, что сумма начального баланса = " + account.getBalanceStart());
        assertTrue(new BigDecimal(editAccountPage.getSelectedBalanceStart())
                .compareTo(new BigDecimal(account.getBalanceStart())) == 0);

        addStepToTheReport("Проверить, что выбрана валюта из предусловия " + account.getCurrencyAccount());
        assertThat(editAccountPage.getSelectedCurrency(), containsString(account.getCurrencyAccount()));

    }


    @Test (groups = "Account")
    @Story("Проверить добавление с типом: \"Наличные\"")
    @Description("")
    public void a_2_createAccountCash() {

        account = Account.builder()
                .nameAccount("Новый тип счета: Наличные_1").typeAccount(configProperties.CASH()).currencyAccount("Гривна").balanceStart("9999.99").build();

        addStepToTheReport("Выполнить переход на страницу  Финансы-> Счета");
        AccountsMainPage accountsMainPage = financeMenuPage.openAccountsMainPage();

        addStepToTheReport("Добавить новый счет c типом: " + account.getTypeAccount());
        accountsMainPage.addNewAccount(account);

        addStepToTheReport("Проверить счетчик количества счетов");
        assertThat(
                "Неверное количество счетов",
                accountsMainPage.getTextQuantityAccount(),
                equalTo("1")
        );

        addStepToTheReport("Проверить, что появился аллерт с текстом об успешном добавлении счета");
        assertThat(accountsMainPage.getTextAlertMassageSuccess(),
                equalTo("Счет успешно добавлен")
        );

        addStepToTheReport("Проверить, что отображается верный баланс");
        String str = utilMethod.getDigitsFromString(accountsMainPage.getTextBalanceOnAccounts());
        assertTrue(new BigDecimal(str)
                .compareTo(new BigDecimal(account.getBalanceStart())) == 0);

        addStepToTheReport("Открыть вновь созданный счет и посмотреть, что все данные сохранены из предусловия");
        EditAccountPage editAccountPage = accountsMainPage.clickLinkEdit(account.getNameAccount());

        addStepToTheReport("Проверить, что выбран тип счета из предусловия : " + account.getTypeAccount());
        assertThat(editAccountPage.getSelectedTypeAccount(), equalTo(account.getTypeAccount()));

        addStepToTheReport("Проверить, что имя счета из предусловия: " + account.getNameAccount());
        assertThat(editAccountPage.getSelectedNameAccount(), equalTo(account.getNameAccount()));

        addStepToTheReport("Проверить, что сумма начального баланса = " + account.getBalanceStart());
        assertTrue(new BigDecimal(editAccountPage.getSelectedBalanceStart())
                .compareTo(new BigDecimal(account.getBalanceStart())) == 0);

        addStepToTheReport("Проверить, что выбрана валюта из предусловия " + account.getCurrencyAccount());
        assertThat(editAccountPage.getSelectedCurrency(), containsString(account.getCurrencyAccount()));
    }


    @Test (groups = "Account")
    @Story("Проверить добавление счета с типом: \"Банковская карта\"")
    public void a_3_createAccountCard() {

        account = Account.builder()
                .nameAccount("Новый тип счета: Банковская карта_1").typeAccount(configProperties.BANK_CARD()).currencyAccount("Евро").balanceStart("-1000.50").build();

        addStepToTheReport("Добавить новый счет c типом: " + account.getTypeAccount());
        accountsMainPage.addNewAccount(account);

        addStepToTheReport("Проверить счетчик количества счетов");
        assertThat(
                "Неверное количество счетов",
                accountsMainPage.getTextQuantityAccount(),
                equalTo("1")
        );

        addStepToTheReport("Проверить, что появился аллерт с текстом об успешном добавлении счета");
        assertThat(accountsMainPage.getTextAlertMassageSuccess(),
                equalTo("Счет успешно добавлен")
        );

        addStepToTheReport("Открыть вновь созданный счет и посмотреть, что все данные сохранены из предусловия");
        EditAccountPage editAccountPage = accountsMainPage.clickLinkEdit(account.getNameAccount());

        addStepToTheReport("Проверить, что выбран тип счета из предусловия : " + account.getTypeAccount());
        assertThat(editAccountPage.getSelectedTypeAccount(), equalTo(account.getTypeAccount()));

        addStepToTheReport("Проверить, что имя счета из предусловия: " + account.getNameAccount());
        assertThat(editAccountPage.getSelectedNameAccount(), equalTo(account.getNameAccount()));

        addStepToTheReport("Проверить, что сумма начального баланса = " + account.getBalanceStart());
        assertTrue(new BigDecimal(editAccountPage.getSelectedBalanceStart())
                .compareTo(new BigDecimal(account.getBalanceStart())) == 0);

        addStepToTheReport("Проверить, что выбрана валюта из предусловия " + account.getCurrencyAccount());
        assertThat(editAccountPage.getSelectedCurrency(), containsString(account.getCurrencyAccount()));

        addStepToTheReport("Проверить, что поле: процент за эквайринг, скрыто:");
        editAccountPage.getRateAcquiring().shouldNotHave(visible);
    }


    @Test (groups = "Account")
    @Story("Проверить добавление счетов в одной валюте с разными типами")
    public void a_4_createSameCurrencyAccounts() {

        addStepToTheReport("Добавить счета в одной валюте с разными типами ");
        List<Account> listAccount = new ArrayList<>();
        account = Account.builder()
                .nameAccount("Новый тип счета: Банковская карта_1").typeAccount(configProperties.BANK_CARD()).currencyAccount("Доллар").balanceStart("3000").build();
        listAccount.add(account);
        account = Account.builder()
                .nameAccount("Новый тип счета: Банковская карта_2").typeAccount(configProperties.NONCASH_ACCOUNT()).currencyAccount("Доллар").balanceStart("5000").build();
        listAccount.add(account);
        account = Account.builder()
                .nameAccount("Новый тип счета: Банковская карта_3").typeAccount(configProperties.CASH()).currencyAccount("Доллар").balanceStart("-1500.50").build();
        listAccount.add(account);
        BigDecimal totalSum = accountsMainPage.addNewAccountList(listAccount);

        addStepToTheReport("Проверить счетчик количества счетов");
        assertThat(
                "Неверное количество счетов",
                accountsMainPage.getTextQuantityAccount(),
                equalTo(listAccount.size() + "")
        );
        addStepToTheReport("Проверить, что отображается верный баланс");
        String sumTotalStr = utilMethod.getDigitsFromString(accountsMainPage.getTextBalanceOnAccounts());
        assertTrue(new BigDecimal(sumTotalStr)
                .compareTo(totalSum) == 0);
    }


    @Test (groups = "Account")
    @Story("Проверить добавление счетов в разных валютах")
    public void a_5_createDifferentCurrencyAccounts() {

        addStepToTheReport("Выполнить переход на страницу  Финансы-> Счета");

        AccountsMainPage accountsMainPage = new AccountsMainPage();

        addStepToTheReport("Добавить счета в одной валюте с разными типами ");
        List<Account> listAccount = new ArrayList<>();

        account = Account.builder()
                .nameAccount("Новый тип счета: Наличные").typeAccount(configProperties.CASH()).currencyAccount("Доллар").balanceStart("3000").build();
        listAccount.add(account);
        account = Account.builder()
                .nameAccount("Новый тип счета: Банковская карта").typeAccount(configProperties.BANK_CARD()).currencyAccount("Евро").balanceStart("5000").build();
        listAccount.add(account);
        account = Account.builder()
                .nameAccount("Новый тип счета: Безналичный счет").typeAccount(configProperties.NONCASH_ACCOUNT())
                .rateAcquiring("0.5").currencyAccount("Гривна").balanceStart("-1500.50").build();

        listAccount.add(account);
        accountsMainPage.addNewAccountList(listAccount);

        addStepToTheReport("Проверить счетчик количества счетов");
        assertThat(
                "Неверное количество счетов",
                accountsMainPage.getTextQuantityAccount(),
                equalTo(listAccount.size() + "")
        );

        addStepToTheReport("Проверить, что общий баланс не отображается:");
        accountsMainPage.getLabelBalanceOnAccounts().shouldNotBe(exist);
    }


    @Test (groups = "Account")
    @Story("Проверить редактирование счета, смена типа счета")
    public void a_6_editAccount() {

        account = Account.builder()
                .nameAccount("Наличные").typeAccount(configProperties.CASH()).currencyAccount("Гривна").balanceStart("2112.33").build();

        addStepToTheReport("Добавить новый счет c типом: " + account.getTypeAccount());
        accountsMainPage.addNewAccount(account);

        addStepToTheReport("Открыть вновь созданный счет и посмотреть, что все данные сохранены из предусловия");
        EditAccountPage editAccountPage = accountsMainPage.clickLinkEdit(account.getNameAccount());


        addStepToTheReport("Изменить тип счета, отредактировать все поня, и добавить процент за эквайринг ");
        account = Account.builder()
                .nameAccount("Безналичный счет. Обновление").typeAccount(configProperties.NONCASH_ACCOUNT()).currencyAccount("Евро").balanceStart("0")
                .rateAcquiring("0.5").build();

        editAccountPage.fillFormDataAccountCashlessPayments(account);

        addStepToTheReport("Сохранить изменения");
        editAccountPage.clickButtonSubmit();

        addStepToTheReport("Повторно открыть счет для редактиования");
        accountsMainPage.clickLinkEdit(account.getNameAccount());

        addStepToTheReport("Проверить, что тип изменен на " + account.getTypeAccount());
        assertThat(editAccountPage.getSelectedTypeAccount(), equalTo(account.getTypeAccount()));

        addStepToTheReport("Проверить, что изменено имя" + account.getNameAccount());
        assertThat(editAccountPage.getSelectedNameAccount(), equalTo(account.getNameAccount()));

        addStepToTheReport("Проверить, что процент за эквайринг установлен и равен " + account.getRateAcquiring());
        assertTrue(new BigDecimal(editAccountPage.getSelectedRateAcquiring())
                .compareTo(new BigDecimal(account.getRateAcquiring())) == 0);

        addStepToTheReport("Проверить, что сумма начального баланса изменилась на сумму = " + account.getBalanceStart());
        assertTrue(new BigDecimal(editAccountPage.getSelectedBalanceStart())
                .compareTo(new BigDecimal(account.getBalanceStart())) == 0);

        addStepToTheReport("Проверить, что изменена валюта на " + account.getCurrencyAccount());
        assertThat(editAccountPage.getSelectedCurrency(), containsString(account.getCurrencyAccount()));

    }


    @Test(dataProvider = "getDateAccountForTestDelete",
            groups = "Account"
    )
    @Story("Проверить удаление счета")
    public void a_7_deleteAccountTest(Account account) {

        addStepToTheReport("Добавить новый счет c типом: " + account.getTypeAccount());
        accountsMainPage.addNewAccount(account);

        addStepToTheReport("Выполнить удаление счета");
        accountsMainPage.deleteSelectedAccount(account.getNameAccount());

        addStepToTheReport("Подвердить удаление счета");
        accountsMainPage.clickButtonConfirmAlert();

        addStepToTheReport("Проверить, что счет удален:  " + account.getNameAccount());
        accountsMainPage.getNameAccount(account.getNameAccount()).shouldNotHave(visible);

        addStepToTheReport("Проверить счетчик количества счетов");
        assertThat(
                "Неверное количество счетов",
                accountsMainPage.getTextQuantityAccount(),
                equalTo("0")
        );
    }

    @Test(groups = "Account")
    @Story("Проверить поиск договора, по типу счета, по сумме на счету, по названию счета")
    public void a_8_quickSearchAccount() {

        addStepToTheReport("Добавить счета в одной валюте с разными типами ");
        List<Account> listAccount = new ArrayList<>();

        Account  account1 = Account.builder()
                .nameAccount("Первый счет").typeAccount(configProperties.CASH()).currencyAccount("Доллар").balanceStart("1500").build();
        listAccount.add(account1);
        Account  account2 = Account.builder()
                .nameAccount("Второй счет").typeAccount(configProperties.BANK_CARD()).currencyAccount("Евро").balanceStart("1500").build();
        listAccount.add(account2);
        Account  account3 = Account.builder()
                .nameAccount("Третий счет").typeAccount(configProperties.BANK_CARD()).currencyAccount("Гривна").balanceStart("1500").build();
        listAccount.add(account3);
        accountsMainPage.addNewAccountList(listAccount);
        accountsMainPage.searchAccount(account1.getNameAccount());
        accountsMainPage.dataAccountFromTable("Первый счет");
    }

}
