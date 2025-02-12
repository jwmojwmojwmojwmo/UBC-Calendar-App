package ui;

import model.Calendar;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class CalendarApp {
    Scanner userInput;
    Calendar calendar;
    //EFFECTS: gets CSV file from user
    public CalendarApp() {
        userInput = new Scanner(System.in);
        makeNewCalendar();
        getCSV();
    }

    //MODIFIES: this
    //EFFECTS: makes new calendar with given name
    public void makeNewCalendar() {
        String calendarName = userInput.nextLine();
        calendar = new Calendar(calendarName);
    }

    //MODIFIES: this
    //EFFECTS: prompts user to enter CSV file
    public void getCSV() {
        System.out.println("Please enter the CSV's file path. If you need help, enter \"HELP\". If you do not want to upload a CSV, enter \"NEW\":");
        String input = userInput.nextLine();
        if (input.equals("HELP")) {
            System.out.println("test");
        } else if (input.equals("NEW")) {
            System.out.println("help them");
        } else {
            try {
                calendar.addCourses(input);
            } catch (FileNotFoundException e) {
                System.out.println("Invalid file path");
            }
        }
    }
}
