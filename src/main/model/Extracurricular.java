package model;

import java.time.LocalTime;

//Represents an extracurricular with a name, day of the week, starting and ending times, and location
public class Extracurricular extends CalendarItem {

    public Extracurricular(String name, LocalTime start, LocalTime end, String location) {
        super(name, start, end, location);
    }

}
