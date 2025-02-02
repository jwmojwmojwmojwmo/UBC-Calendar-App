package model;

import java.io.*;
import java.util.*;

//Represents a Calendar with a name and list of courses and extracurriculars
public class Calendar {
    private String name;
    private ArrayList<Course> courses;
    private ArrayList<Extracurricular> extras;

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

    public String getName(){
        return null;
    }

    public ArrayList<Course> getCourses() {
        return null;
    }

    public ArrayList<Extracurricular> getExtras() {
        return null;
    }
}
