package model;

import java.io.*;
import java.time.LocalTime;
import java.util.*;

//Represents a Calendar with a name
public class Calendar {

    private String name;

    public Calendar(String name) {
    }

    //REQUIRES: The CSV file of registered courses as created by Workday
    //EFFECTS: Convert CSV file into a list of courses and adds courses to Calendar
    public Course addCourses(File courses) {
        return null;
    }
    
    //MODIFIES: this
    //EFFECTS: adds item to Calendar
    public void addItem(CalendarItem item) {
    }

    //MODIFIES: this
    //EFFECTS: removes item from Calendar
    public void removeItem(CalendarItem item) {
    }

    //MODIFIES: this
    //EFFECTS: changes Calendar name to given name
    public void changeName(String name) {
    }

    //EFFECTS: returns the item at the given day and time, or returns "no item at that time"
    public String getItemAt(Day day, LocalTime time) {
        return null;
    }

    public String getName() {
        return null;
    }

    public ArrayList<String> getCourses() {
        return null;
    }

    public ArrayList<String> getExtras() {
        return null;
    }
}
