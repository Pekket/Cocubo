package be.pekket.cocubo.model;

import java.util.LinkedList;
import java.util.List;

public class Menu {

    private int weekNumber;
    private List<Day> days = new LinkedList<>();

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber( int weekNumber ) {
        this.weekNumber = weekNumber;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays( List<Day> days ) {
        this.days = days;
    }

    public void addAdd( Day day ) {
        if ( day != null )
            this.days.add(day);
    }

    public Day getDay( int index ) {
        return this.days.get(index);
    }
}
