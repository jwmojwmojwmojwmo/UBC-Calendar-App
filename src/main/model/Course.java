package model;

import java.time.LocalTime;

//Represents a course with a name, starting and ending times, and location
public class Course extends CalendarItem{

    public Course(String name, LocalTime start, LocalTime end, String location) {
        super(name, start, end, location);
    }
}
