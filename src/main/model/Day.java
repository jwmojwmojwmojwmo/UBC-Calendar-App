package model;

import java.time.DayOfWeek;
import java.util.ArrayList;

//Represents a day of the week with a list of items
public class Day {

    private DayOfWeek day;
    private ArrayList<CalendarItem> items;

    public Day(DayOfWeek day, ArrayList<CalendarItem> items) {
    }

    //EFFECTS: returns time intervals where there are items on the calendar
    public String getBusyTimes() {
        return null;
    }

    public String getDay() {
        return null;
    }

    public ArrayList<String> getItems() {
        return null;
    }
}
