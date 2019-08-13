package be.pekket.cocubo.util;

public class NumberUtil {

    public static boolean isNumeric( String strNum ) {
        return strNum.matches("-?\\d+(\\.\\d+)?");
    }
}
