package model;

import java.time.LocalTime;

import org.json.JSONObject;

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

    // MODIFIES: this
    // EFFECTS: changes name of item to given name
    public void changeName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // REQUIRES: time is a valid time
    // EFFECTS: changes start time of item to given time
    public void changeStartTime(LocalTime time) {
        startTime = time;
    }

    // MODIFIES: this
    // REQUIRES: time is a valid time after the start time
    // EFFECTS: changes end time of item to given time
    public void changeEndTime(LocalTime time) {
        endTime = time;
    }

    // MODIFIES: this
    // EFFECTS: changes location to given location
    public void changeLocation(String location) {
        this.location = location;
    }

    // MODIFIES: JsonWriter
    // EFFECTS: converts calendarItem to information .json file
    public JSONObject toJson() {
        JSONObject jsonItem = new JSONObject();
        jsonItem.put("name", name);
        jsonItem.put("start", startTime);
        jsonItem.put("end", endTime);
        jsonItem.put("location", location);
        return jsonItem;
    }
    
    public String getName() {
        return name;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }
}
