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

    public static String createSteakMessage( String steak ) {
        String message = "De steak van de week is *" + steak + "* :cut_of_meat:";

        double random = (int) (Math.random() * (5 + 1));
        if ( random == 3 ) {
            message += "\n\nhttps://media.giphy.com/media/Kbvwy59RTErmo87PtC/giphy.gif";
        }

        return message;
    }

    public static String createWeekendMessage() {
        return "Its weekend, why u at Corda? :thinking_face:";
    }

    public static String createClosedMessage() {
        return "Seems like the kitchen is closed. :scream: ";
    }

    public static String createErrorMessage() {
        return "Oops seems the cook is not started yet. Lets wake him up! :male-cook: ";
    }
}
