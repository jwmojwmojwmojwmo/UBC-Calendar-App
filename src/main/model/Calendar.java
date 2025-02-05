package model;

import java.io.*;
import java.time.LocalTime;
import java.util.*;

//Represents a Calendar with a name
public class Calendar {

    private String name;

    public Calendar(String name) {
        this.name = name;
    }

    //MOFDIFIES: this
    //REQUIRES: The file path must lead to a CSV of registered courses as created by Workday
    //EFFECTS: Convert CSV file into a list of courses and adds courses to Calendar
    public void addCourses(File courses) throws FileNotFoundException {

        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Enter file path: ");
        String filePath = inputScanner.nextLine();
        inputScanner.close();
        File csvFile = new File(filePath);
        Scanner csvReader = new Scanner(csvFile);

        for(int i = 0; i < 3; i++) {
            csvReader.nextLine();
        }

        while (csvReader.hasNextLine()) {
            String line = csvReader.nextLine();
            String[] rows = line.split(",");
            if (rows.length > 7) {
                CSVReader read = new CSVReader(rows[4].trim(), rows[7].trim());
                read.convert();
            }
        }
        csvReader.close();
    }

    //EFFECTS: returns the item at the given day and time, or returns "Nothing found!"
    public String getItemAt(Day day, LocalTime time) {
        return null;
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
