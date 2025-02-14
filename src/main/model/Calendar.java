package model;


import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Scanner;


//Represents a Calendar with a name
public class Calendar {

    private String name;

    public static ArrayList<Day> daysOfWeek;

    public Calendar(String name) {
        this.name = name;
        daysOfWeek = new ArrayList<Day>();
        daysOfWeek.add(new Day(DayOfWeek.MONDAY));
        daysOfWeek.add(new Day(DayOfWeek.TUESDAY));
        daysOfWeek.add(new Day(DayOfWeek.WEDNESDAY));
        daysOfWeek.add(new Day(DayOfWeek.THURSDAY));
        daysOfWeek.add(new Day(DayOfWeek.FRIDAY));
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
