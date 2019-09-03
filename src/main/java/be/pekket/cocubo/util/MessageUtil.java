package be.pekket.cocubo.util;

public class MessageUtil {

    public static String createMenuMessage( String day ) {
        return "Het menu *" + day + "*";
    }

    public static String createSoupMessage( String soup ) {
        return ":stew:   " + soup;
    }

    public static String createDishMessage( String dish ) {
        return ":hamburger:   " + dish;
    }

    public static String createVegiMessage( String vegi ) {
        return ":green_salad:   " + vegi;
    }

    public static String createWppMessage( String wpp ) {
        return ":spaghetti:   " + wpp;
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
