package model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;


//Represents a day of the week with a list of items
public class Day {

    private DayOfWeek day;
    private ArrayList<CalendarItem> items;

    public Day(DayOfWeek day) {
        this.day = day;
        items = new ArrayList<CalendarItem>();
    }


    // MODIFIES: this
    // EFFECTS: adds item to this day
    public void addItem(CalendarItem item) {
        items.add(item);
    }

    // MODIFIES: this
    // REQUIRES: item exists in day
    // EFFECTS: removes item from this day
    public void removeItem(CalendarItem item) {
        items.remove(item);
    }

    //EFFECTS: returns the item at the given day and time, or returns "Nothing found!"
    public String getItemAt(LocalTime time) {
        for (CalendarItem item : items) {
            if (time.equals(item.getStartTime()) || time.equals(item.getEndTime()) || 
            (time.isAfter(item.getStartTime()) && time.isBefore(item.getEndTime()))) {
                return item.getName();
            }
        }
        return "Nothing found!";
    }

    //MODIFIES: this
    //EFFECTS: removes all items in this day
    public void removeAll() {
        items = new ArrayList<CalendarItem>();
    }

    public DayOfWeek getDay() {
        return day;
    }

    public ArrayList<CalendarItem> getItems() {
        return items;
    }
}
