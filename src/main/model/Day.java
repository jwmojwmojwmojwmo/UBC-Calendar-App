package model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;

import org.json.JSONArray;

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
        items.sort(Comparator.comparing(CalendarItem::getStartTime));
    }

    // MODIFIES: this
    // REQUIRES: item exists in day
    // EFFECTS: removes item from this day
    public void removeItem(CalendarItem item) {
        items.remove(item);
    }

    // MODIFIES: this
    // REQUIRES: index exists in day
    // EFFECTS: removes item at given index from this day
    public void removeItem(int index) {
        items.remove(index);
    }

    // EFFECTS: returns the item at the given day and time, or returns "Nothing
    // found!"
    public String getItemAt(LocalTime time) {
        for (CalendarItem item : items) {
            if (time.equals(item.getStartTime()) || time.equals(item.getEndTime())
                    || (time.isAfter(item.getStartTime()) && time.isBefore(item.getEndTime()))) {
                return item.getName();
            }
        }
        return "Nothing found!";
    }

    
    public CalendarItem getItemAt(int index) {
        return items.get(index);
    }

    // EFFECTS: returns the index of the item with the given name in the given day,
    // or returns -1
    public Integer getItemCalled(String name) {
        for (int i = 0; i < getItems().size(); i++) {
            if (getItems().get(i).getName().equals(name)) {     
                return i;
            }
        }
        return -1;
    }

    // MODIFIES: this
    // EFFECTS: removes all items in this day
    public void removeAll() {
        items = new ArrayList<CalendarItem>();
    }

    // MODIFIES: JsonWriter
    // EFFECTS: converts day to information .json file
    public JSONArray toJson() {
        JSONArray jsonDay = new JSONArray();
        for (CalendarItem calendarItem : items) {
            jsonDay.put(calendarItem.toJson());
        }
        return jsonDay;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public ArrayList<CalendarItem> getItems() {
        return items;
    }
}
