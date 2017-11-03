package entities;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by victor on 02.11.2017.
 */

@Builder
@Getter
public class AccountExpectedResult {
    String countAccount;
    String balanceTotal;
}
