package be.pekket.cocubo.util;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class TimeUtil {
    public final static String TODAY = "vandaag";
    private final static String[] MONTHS = {"JANUARI", "FEBRUARI", "MAART", "ARPIL", "MEI", "JUNI", "JULI", "AUGUSTUS", "SEPTEMBER", "OKTOBER", "NOVEMBER", "DECEMBER"};
    private final static String[] DAYS = {"maandag", "dinsdag", "woensdag", "donderdag", "vrijdag"};

    public static int getDay() {
        LocalDate date = LocalDate.now();
        return date.getDayOfWeek().getValue();
    }

    public static int getWeekNumber() {
        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfWeekBasedYear());
    }

    public static int getMonth() {
        LocalDate date = LocalDate.now();
        return date.getMonthValue();
    }

    public static String getMonthName() {
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        return MONTHS[month - 1];
    }

    public static String getDayName(int i) {
        return DAYS[i];
    }

    public static int getYear() {
        LocalDate date = LocalDate.now();
        return date.getYear();
    }

    public static String doubleDiggit( int number ) {
        return number < 10 ? "0" + number : "" + number;
    }
}
