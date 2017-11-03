import utils.utilmethods.UtilMethod;

/**
 * Created by victor on 03.11.2017.
 */
public class Main {
    public static void main(String[] args) {
        UtilMethod utilMethod = new UtilMethod();
        String t = utilMethod.getDigitsFromString("Баланс: 1 300.00 ₴");
        System.out.println(t);

    }
}
