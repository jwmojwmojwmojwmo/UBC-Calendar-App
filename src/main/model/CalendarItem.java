package model;

import java.time.LocalTime;

//Represents an item on a calendar
public class CalendarItem {

    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;

    public CalendarItem(String name, LocalTime start, LocalTime end, String location) {
        this.name = name;
        startTime = start;
        endTime = end;
        this.location = location;
    }

    //MODIFIES: this
    //EFFECTS: changes name of item to given name
    public void changeName(String name) {
        this.name = name;
    }

    //MODIFIES: this
    //REQUIRES: time is a valid time
    //EFFECTS: changes start time of item to given time
    public void changeStartTime(LocalTime time) {
        startTime = time;
    }

    //MODIFIES: this
    //REQUIRES: time is a valid time after the start time
    //EFFECTS: changes end time of item to given time
    public void changeEndTime(LocalTime time) {
        endTime = time;
    }

    //MODIFIES: this
    //EFFECTS: changes location to given location
    public void changeLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getstartTime() {
        return startTime.toString();
    }

    public String getEndTime() {
        return endTime.toString();
    }

    public String getLocation() {
        return location;
    }
}
