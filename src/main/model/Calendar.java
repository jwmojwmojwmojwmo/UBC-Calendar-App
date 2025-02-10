package model;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

//Represents a Calendar with a name
public class Calendar {

    private String name;

    static Day Monday = new Day(DayOfWeek.MONDAY);
    static Day Tuesday = new Day(DayOfWeek.TUESDAY);
    static Day Wednesday = new Day(DayOfWeek.WEDNESDAY);
    static Day Thursday = new Day(DayOfWeek.THURSDAY);
    static Day Friday = new Day(DayOfWeek.FRIDAY);

    public Calendar(String name) {
        this.name = name;
    }

    //MOFDIFIES: this
    //REQUIRES: The file path must lead to a CSV of registered courses as created by Workday
    //EFFECTS: Convert CSV file into a list of courses and adds courses to Calendar
    public void addCourses(String filePath) throws FileNotFoundException {
        File csvFile = new File(filePath);
        Scanner csvReader = new Scanner(csvFile);

        for (int i = 0; i < 3; i++) {
            csvReader.nextLine();
        }

        while (csvReader.hasNextLine()) {
            String line = csvReader.nextLine();
            String[] rows = line.split(",");
            if (rows.length > 7) {
                ReadCSV read = new ReadCSV(rows[4].trim(), rows[7].trim());
                read.convert();
            }
        }
        csvReader.close();
    }
    
    //MODIFIES: this
    //EFFECTS: changes Calendar name to given name
    public void changeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
