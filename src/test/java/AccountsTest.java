import entities.Account;
import entities.AccountExpectedResult;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.accounts.AccountsMainPage;
import pages.accounts.AddAccountPage;
import pages.accounts.EditAccountPage;
import pages.navigate.FinanceMenuPage;
import utils.utilmethods.UtilMethod;

import java.math.BigDecimal;

import static com.codeborne.selenide.Condition.visible;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;


/**
 * Created by victor on 01.11.2017.
 */
public class AccountsTest extends BaseTest{

    private FinanceMenuPage financeMenuPage = new FinanceMenuPage();
    private UtilMethod utilMethod = new UtilMethod();

    @DataProvider
    public static Object[][] getDateAccount() {
        return new Object[][]
                {
                        {Account.builder()
                                .nameAccount("Тест-1.Безналичный счет")
                                .typeAccount("Безналичный счет")
                                .currencyAccount("Гривна")
                                .balanceStart("2000")
                                .rateAcquiring("100")
                                .build(),

                        AccountExpectedResult.builder()
                                .balanceTotal("1900")
                                .countAccount("1").build()
                        }
                };
    }


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



    @Test(
            dataProvider = "getDateAccount",
            dependsOnMethods="deleteAllAccountTest"
    )
    public void a_1_createAccount(Account account, AccountExpectedResult accountExpectedResult) {
        addStepToTheReport("Выпоолнить переход на страницу  Финансы-> Счета");
        AccountsMainPage accountsMainPage = financeMenuPage.openAccountsMainPage();

        addStepToTheReport("Нажать на кнопку: Добавить счет ");
        AddAccountPage addAccountPage = accountsMainPage.clickButtonAddAccount();

        addStepToTheReport("Заполнить форму для добавления счета");
        addAccountPage.fillFormDataAccountCashlessPayments(account);

        addStepToTheReport("Нажать на кнопку: Добавить счет");
        addAccountPage.clickButtonSubmit();

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


    @Test(
            dependsOnMethods="deleteAllAccountTest"
    )
    public void a_2_createAccount() {
       Account account1 = Account.builder()
                .nameAccount(utilMethod.getCurrentDateTime())
                .typeAccount(configProperties.NONCASH_ACCOUNT())
                .currencyAccount("Гривна")
                .balanceStart("1000")
                .rateAcquiring("0.1")
                .build();

        addStepToTheReport("Выпоолнить переход на страницу  Финансы-> Счета");
        AccountsMainPage accountsMainPage = financeMenuPage.openAccountsMainPage();

        addStepToTheReport("Нажать на кнопку: Добавить счет ");
        AddAccountPage addAccountPage = accountsMainPage.clickButtonAddAccount();

        addStepToTheReport("Заполнить форму для добавления счета");
        addAccountPage.fillFormDataAccountCashlessPayments(account1);

        addStepToTheReport("Нажать на кнопку: Добавить счет");
        addAccountPage.clickButtonSubmit();

        Account account2 = Account.builder()
                .nameAccount(utilMethod.getCurrentDateTime())
                .typeAccount(configProperties.BANK_CARD())
                .currencyAccount("Гривна")
                .balanceStart("300")
                .build();

        addStepToTheReport("Добавить еще один счет с типом " + account2.getTypeAccount());
        addAccountPage = accountsMainPage.clickButtonAddAccount();

        addStepToTheReport("Заполнить форму для добавления счета:");
        addAccountPage.fillFormDataAccountCashOrCard(account2);

        addStepToTheReport("Проверить, что поле: процент за эквайринг, скрыто:");
        addAccountPage.getRateAcquiring().shouldNotHave(visible);

        addStepToTheReport("Нажать на кнопку: Добавить счет");
        addAccountPage.clickButtonSubmit();

        addStepToTheReport("Проверить, что баланс равен:");
        System.out.println(accountsMainPage.getTextBalanceOnAccounts());
        String str = utilMethod.getDigitsFromString(accountsMainPage.getTextBalanceOnAccounts());
        assertTrue(new BigDecimal(str)
                .compareTo(new BigDecimal(Double.parseDouble(account1.getBalanceStart()) + Double.parseDouble(account2.getBalanceStart()))) == 0);
        addStepToTheReport("Открыть вновь созданный счет и посмотреть, что все данные сохранены из предусловия");
        EditAccountPage editAccountPage = accountsMainPage.clickLinkEdit(account2.getNameAccount());

        addStepToTheReport("Проверить, что выбран тип счета из предусловия : " + account2.getTypeAccount());
        assertThat(editAccountPage.getSelectedTypeAccount(), equalTo(account2.getTypeAccount()));


    }

    @Test
    public void test() {
       System.out.println(utilMethod.getDigitsFromString("Баланс: 0,00 ₴"));
    };

    @Test (
            dataProvider = "getDateAccountForTestDelete"
    )
    public void a_3_createAccountTestForDelete(Account account) {
        addStepToTheReport("Выпоолнить переход на страницу  Финансы-> Счета");
        AccountsMainPage accountsMainPage = financeMenuPage.openAccountsMainPage();

        addStepToTheReport("Нажать на кнопку: Добавить счет ");
        AddAccountPage addAccountPage = accountsMainPage.clickButtonAddAccount();

        addStepToTheReport("Заполнить форму для добавления счета");
        addAccountPage.addNewAccount(account);

        addStepToTheReport("Проверить, что количество счетов изменилось");
        assertThat(
                "Должно быть другое количество счетов в счетчике",
                accountsMainPage.getTextAlertMassageSuccess(),
                equalTo("Счет успешно добавлен")
        );
    }


    @Test(
            dataProvider = "getDateAccountForTestDelete",
            dependsOnMethods={"deleteAllAccountTest","a_3_createAccountTestForDelete"}
    )
    public void a_2_deleteAccountTest(Account account) {
        addStepToTheReport("Выпоолнить переход на страницу  Финансы-> Счета");
        AccountsMainPage accountsMainPage = financeMenuPage.openAccountsMainPage();

        addStepToTheReport("Выполнить удаление счета");
        accountsMainPage.deleteSelectedAccount(account.getNameAccount());

        addStepToTheReport("Подвердить удаление счета");
        accountsMainPage.clickButtonConfirmAlert();

        addStepToTheReport("Проверить, что счет удален:  " +  account.getNameAccount());
        accountsMainPage.getNameAccount(account.getNameAccount()).shouldNotHave(visible);
    }

    @Test
    public void deleteAllAccountTest() {
        AccountsMainPage accountsMainPage = financeMenuPage.openAccountsMainPage();
        accountsMainPage.deleteAllAccount();
    }

}
