package model;


import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONObject;

//Represents a Calendar with a name
public class Calendar {

    private String name;
    private ArrayList<Day> daysOfWeek;

    public Calendar(String name) {
        this.name = name;
        daysOfWeek = new ArrayList<Day>();
        daysOfWeek.add(new Day(DayOfWeek.MONDAY));
        daysOfWeek.add(new Day(DayOfWeek.TUESDAY));
        daysOfWeek.add(new Day(DayOfWeek.WEDNESDAY));
        daysOfWeek.add(new Day(DayOfWeek.THURSDAY));
        daysOfWeek.add(new Day(DayOfWeek.FRIDAY));
    }
    
    // MOFDIFIES: this, Day
    // REQUIRES: The file path must lead to a CSV of registered courses as created
    // by Workday
    // EFFECTS: Convert CSV file into a list of courses and adds courses to Calendar
    public void addCourses(String filePath) throws FileNotFoundException {
        File csvFile = new File(filePath);
        Scanner csvReader = new Scanner(csvFile);

        csvReader.nextLine();

        while (csvReader.hasNextLine()) {
            String line = csvReader.nextLine();
            String[] rows = line.split(",");
            if (rows.length > 4 && rows[4].trim().length() > 15 && rows[1].trim().length() > 3) {
                ReadCSV read = new ReadCSV(rows[1].trim(), rows[4].trim(), daysOfWeek);
                daysOfWeek = read.convert();
            }
        }
        csvReader.close();
    }

    // EFFECTS: helper method that returns a list of the days that the given item is in
    public ArrayList<Day> daysOfItem(String name) {
        ArrayList<Day> daysItemIsIn = new ArrayList<Day>();
        for (Day day : daysOfWeek) {
            for (CalendarItem item : day.getItems()) {
                if (item.getName().equals(name)) {
                    daysItemIsIn.add(day);
                }
            }
        }
        return daysItemIsIn;
    }

    // MODIFIES: this, Day, CalendarItem
    // EFFECTS: helper method that takes an item, removes it from all days it used
    // to be in, and adds it to all the new days it should be in, which is given as
    // a string. Used when the user inputs new days to add the item to.
    public void changeDays(String name, CalendarItem item, String days) {
        for (Day day : daysOfItem(name)) {
            day.removeItem(day.getItemCalled(name));
        }
        applyCourseToEachDay(days, item.getName(), item.getStartTime(), item.getEndTime(), item.getLocation());
    }

    // MODIFIES: this, Day, CalendarItem
    // EFFECTS: helper method that takes an item and edits with new information.
    // Used when the user chooses to keep the days the same when editing
    // information.
    public void changeItemInfo(String name, CalendarItem item) {
        CalendarItem oldItem;
        for (Day day : daysOfItem(name)) {
            oldItem = day.getItemAt(day.getItemCalled(name));
            oldItem.changeName(item.getName());
            oldItem.changeStartTime(item.getStartTime());
            oldItem.changeEndTime(item.getEndTime());
            oldItem.changeLocation(item.getLocation());
        }
    }

    // MODIFIES: this, Day, CalendarItem, Calendar
    // EFFECTS: helper method that creates a new course using a string of given
    // days, based on the parameters
    public void applyCourseToEachDay(String days, String name, LocalTime start, LocalTime end, String location) {
        String[] eachDay = days.split(",");
        for (String string : eachDay) {
            daysOfWeek.get(Integer.valueOf(string.trim()) - 1)
                    .addItem(new CalendarItem(name, start, end, location));
        }
    }


    // MODIFIES: this, Day
    // EFFECTS: removes given item from all days
    public void removeItem(String name) {
        for (Day day: daysOfItem(name)) {
            day.removeItem(day.getItemCalled(name));
        }
    }

    // MODIFIES: JsonWriter
    // EFFECTS: converts calendar to .json file
    public JSONObject toJson() {
        JSONObject jsonCalendar = new JSONObject();
        jsonCalendar.put("name", name);
        for (Day day : daysOfWeek) {
            jsonCalendar.put(day.getDay().toString(), day.toJson());
        }
        return jsonCalendar;
    }

    // MODIFIES: this
    // EFFECTS: changes Calendar name to given name
    public void changeName(String name) {
        this.name = name;
        EventLog.getInstance().logEvent(new Event("Changed calendar name to " + name));
    }

    public String getName() {
        return name;
    }

    public ArrayList<Day> getDaysOfWeek() {
        return daysOfWeek;
    }
}
