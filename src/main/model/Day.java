package model;

import java.time.DayOfWeek;
import java.util.ArrayList;

//Represents a day of the week with a list of items
public class Day {

    private DayOfWeek day;
    private ArrayList<CalendarItem> items;

    public Day(DayOfWeek day, ArrayList<CalendarItem> items) {
    }

    //MODIFIES: this
    //EFFECTS: adds item to this day
    public void addItem(CalendarItem item) {
    }

    //MODIFIES: this
    //REQUIRES: item exists in day
    //EFFECTS: removes item from this day
    public void removeItem(CalendarItem item) {
    }

    //EFFECTS: returns time intervals where there are items on the calendar, or "Not busy today!"
    public String getBusyTimes() {
        return null;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public ArrayList<CalendarItem> getItems() {
        ArrayList<CalendarItem> stringItems = new ArrayList<CalendarItem>();
        for (CalendarItem item : items) {
            stringItems.add(item);
        }
        return stringItems;
    }
}
