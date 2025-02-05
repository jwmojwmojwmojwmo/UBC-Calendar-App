package model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

//A class that handles reading data from a CSV and passing into Course to create courses
public class CSVReader {
    String courseName;
    String courseInfo;
    ArrayList<DayOfWeek> courseDays;
    LocalTime courseStart;
    LocalTime courseEnd;
    String courseLocation;
    
    public CSVReader(String name, String info) {
        courseName = name;
        courseInfo = info;
    }

    //MODIFIES: this
    //EFFECTS: converts courseInfo into the course's days, start and end times, and location,
    //and passes it to course to create a course
    public void convert() {
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseInfo() {
        return courseInfo;
    }

    public ArrayList<DayOfWeek> getCourseDays() {
        return courseDays;
    }

    public LocalTime getCourseStart() {
        return courseStart;
    }

    public LocalTime getCourseEnd() {
        return courseEnd;
    }

    public String getCourseLocation() {
        return courseLocation;
    }

}
