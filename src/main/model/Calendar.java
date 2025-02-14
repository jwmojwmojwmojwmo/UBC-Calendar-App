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

    // MOFDIFIES: this
    // REQUIRES: The file path must lead to a CSV of registered courses as created
    // by Workday
    // EFFECTS: Convert CSV file into a list of courses and adds courses to Calendar
    public void addCourses(String filePath) throws FileNotFoundException {
        File csvFile = new File(filePath);
        Scanner csvReader = new Scanner(csvFile);

        csvReader.nextLine();

        while (csvReader.hasNextLine()) {
            String line = csvReader.nextLine();
            String[] rows = line.split(",");
            if (rows.length > 4 && rows[4].trim().length() > 15 && rows[1].trim().length() > 3) {
                ReadCSV read = new ReadCSV(rows[1].trim(), rows[4].trim());
                System.out.println(rows[1].trim() + rows[4].trim());
                read.convert();
            }
        }
        csvReader.close();
    }

    
    //EFFECTS: helper method that returns 
    public ArrayList<Day> daysOfCourse(String name) {
        ArrayList<Day> daysCourseIsIn = new ArrayList<Day>();
        for (Day day : Calendar.daysOfWeek) {
            for (CalendarItem item : day.getItems()) {
                if (item.getName().equals(name)) {
                    daysCourseIsIn.add(day);
                }
            }
        }
        return daysCourseIsIn;
    }

    // MODIFIES: this
    // EFFECTS: changes Calendar name to given name
    public void changeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
