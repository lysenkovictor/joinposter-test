package utils.properties;

import org.aeonbits.owner.Config;

/**
 * Created by victor on 02.11.2017.
 */

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:config.properties",
        "classpath:testData.properties",
})

public interface IProperties extends Config{
    int BROWSER();
    int TIMEOUT();


    String PASSWORD();
    String EMAIL_LOGIN();
    String NONCASH_ACCOUNT();
    String CASH();
    String BANK_CARD();
    String START_PAGE();

}
