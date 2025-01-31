package model;

import java.io.*;
import java.util.*;

//Represents a Calendar with a name and list of courses and extracurriculars
public class Calendar {
    private String name;
    private ArrayList<Course> courses;
    private ArrayList<Extracurricular> extras;

    public Calendar() {
    }

    //REQUIRES: The CSV file of registered courses as created by Workday
    //EFFECTS: Convert CSV file into a list of courses and adds courses to Calendar
    public Course addCourses(File courses) {
        return null;
    }
    
    //MODIFIES: this
    //EFFECTS: adds course to Calendar
    public void addCourse(Course course) {
    }

    //MODIFIES: this
    //EFFECTS: removes course from Calendar
    public void removeCourse(Course course) {
    }

    //MODIFIES: this
    //EFFECTS: adds extracurricular to Calendar
    public void addExtra(Extracurricular extra) {
    }

    //MODIFIES: this
    //EFFECTS: removes extracurricular from Calendar
    public void removeExtra(Extracurricular extra) {
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
