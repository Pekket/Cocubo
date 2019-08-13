package be.pekket.cocubo.util;

import org.springframework.util.StringUtils;

public class MessageUtil {

    public static String createSoupMessage( String dag, String text ) {
        return "De soep " + dag + " is *" + StringUtils.uncapitalize(text) + "* :stew:";
    }

    public static String createWeekendMessage() {
        return "Its weekend, why u at Corda campus? :thinking_face:";
    }

    public static String createClosedMessage() {
        return "Seems like the kitchen is closed. :scream: ";
    }

    public static String createErrorMessage() {
        return "Oops seems the cook is not started yet. Lets wake him up! :male-cook: ";
    }
}
