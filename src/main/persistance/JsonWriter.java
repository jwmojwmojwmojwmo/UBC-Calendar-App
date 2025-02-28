package persistance;

import java.io.PrintWriter;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import model.Calendar;

//Represents a tool that writes the calendar into a JSON file
public class JsonWriter {
    private PrintWriter writer;

    public JsonWriter() {
    }

    // MODIFIES: this
    // EFFECTS: writes the given calendar into the .json file at the given file path
    public void write(Calendar calendar, String filePath) throws FileNotFoundException {
        writer = new PrintWriter(new File(filePath));
        JSONObject jsonCalendar = calendar.toJson();
        writer.print(jsonCalendar.toString(1));
        writer.close();
    }
}
