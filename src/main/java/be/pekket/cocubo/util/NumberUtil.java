package be.pekket.cocubo.util;

public class NumberUtil {

    public static boolean isNumeric( String strNum ) {
        return strNum != null && strNum.matches("-?\\d+(\\.\\d+)?");
    }
}
