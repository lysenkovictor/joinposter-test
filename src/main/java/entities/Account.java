package entities;

import lombok.Builder;
import lombok.Getter;

import java.util.Currency;

/**
 * Created by victor on 02.11.2017.
 */

@Builder
@Getter
public class Account {

    String nameAccount;
    String currencyAccount;
    String typeAccount;
    String balanceStart;
    String rateAcquiring;

}
