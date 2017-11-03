package utils.utilmethods;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by victor on 03.11.2017.
 */
public class UtilMethod {

    public String getCurrentDateTime() {
        ZoneId zoneId = ZoneId.of("Europe/Kiev");
        return LocalDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("hhmmssS"));
    }

    public String getDigitsFromString(String stringBalance) {
        String result = "";
        String str = stringBalance.replace(',', '.');
        System.out.println(str);
        Pattern p = Pattern.compile("[-]?[.0-9]+([0-9]+)?");
        Matcher m = p.matcher(str);
        while (m.find()) {
            result += m.group();
        }
        return result;
    }
}

