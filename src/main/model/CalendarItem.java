package model;

import java.time.LocalTime;

//Represents an item on a calendar
public class CalendarItem {

    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;

    public CalendarItem(String name, LocalTime start, LocalTime end, String location) {
    }

    //MODIFIES: this
    //EFFECTS: changes name of item to given name
    public void changeName(String name) {
    }

    //MODIFIES: this
    //REQUIRES: time is a valid time
    //EFFECTS: changes start time of item to given time
    public void changeStartTime(LocalTime time) {
    }

    //MODIFIES: this
    //REQUIRES: time is a valid time after the start time
    //EFFECTS: changes end time of item to given time
    public void changeEndTime(LocalTime time) {
    }

    //MODIFIES: this
    //EFFECTS: changes location to given location
    public void changeLocation(String location) {
    }

    public String getName() {
        return null;
    }

    public String getstartTime() {
        return null;
    }

    public String getEndTime() {
        return null;
    }

    public String getLocation() {
        return null;
    }
}
