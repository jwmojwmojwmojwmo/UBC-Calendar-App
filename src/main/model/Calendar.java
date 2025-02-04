package model;

import java.io.*;
import java.time.LocalTime;
import java.util.*;

//Represents a Calendar with a name
public class Calendar {

    private String name;

    public Calendar(String name) {
        this.name = name;
    }

    //REQUIRES: The CSV file of registered courses as created by Workday
    //EFFECTS: Convert CSV file into a list of courses and adds courses to Calendar
    public void addCourses(File courses) {
    }

    //EFFECTS: returns the item at the given day and time, or returns "Nothing found!"
    public String getItemAt(Day day, LocalTime time) {
        return null;
    }
    
    //MODIFIES: this
    //EFFECTS: changes Calendar name to given name
    public void changeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
