package persistance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Calendar;
import model.CalendarItem;
import model.Day;

//Represents a tool that reads a JSON file into a calendar
public class JsonReader {
    DateTimeFormatter format;

    public JsonReader() {
        format = DateTimeFormatter.ofPattern("HH:mm");
    }

    // EFFECTS: reads information from .json file from the file path and returns it
    // in the form of a calendar
    public Calendar read(String filePath) throws IOException {
        String content = Files.readString(Paths.get(filePath));
        JSONObject jsonContent = new JSONObject(content);
        JSONObject item;
        JSONArray arrayOfItems;
        Calendar calendar = new Calendar(jsonContent.getString("name"));
        for (Day day : calendar.getDaysOfWeek()) {
            arrayOfItems = jsonContent.getJSONArray(day.toString());
            for (int i = 0; i < arrayOfItems.length(); i++) {
                item = arrayOfItems.getJSONObject(i);
                day.addItem(new CalendarItem(item.getString("name"), LocalTime.parse(item.getString("start"), format),
                        LocalTime.parse(item.getString("end"), format), item.getString("location")));
            }
        }
        return calendar;
    }
}
