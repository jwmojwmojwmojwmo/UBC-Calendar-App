package ui;

import model.Calendar;
import model.CalendarItem;
import model.Day;

import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.util.Scanner;

public class CalendarApp {
    Scanner userInput;
    Calendar calendar;
    Day currentDay;

    // EFFECTS: gets CSV file from user
    public CalendarApp() {
        userInput = new Scanner(System.in);
        clearConsole();
        makeNewCalendar();
        getCSV();
        runCalendar();
    }

    // MODIFIES: this
    // EFFECTS: makes new calendar with given name
    public void makeNewCalendar() {
        System.out.print("Enter calendar's name:");
        String calendarName = userInput.nextLine();
        calendar = new Calendar(calendarName);
        currentDay = Calendar.daysOfWeek.get(0);
    }

    // MODIFIES: this
    // EFFECTS: prompts user to enter CSV file
    public void getCSV() {
        System.out.println(
                "Please enter the CSV's file path. If you need help, enter \"HELP\". If you do not want to upload a CSV, enter \"NEW\":");
        String input = userInput.nextLine();
        if (input.equals("HELP")) {
            System.out.println("help text");
        } else if (input.equals("NEW")) {
        } else {
            try {
                calendar.addCourses(input);
            } catch (FileNotFoundException e) {
                System.out.println("Invalid file path");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays available commands and gets next input from user
    public void runCalendar() {
        displayCalendar();
        System.out.println("Enter a number between 1-7 for each day of the week (1 -> Monday, 7 -> Sunday.)");
        System.out.println("Enter \"r\" to remove an item.");
        System.out.println("Enter \"n\" to add a new item.");
        System.out.println("Enter a day of week in numerical form and a time, separated by commas (ex. \"1, 12:00\"), to find what item is at that time.");
        String nextCommand = userInput.nextLine();
        processCommand(nextCommand);
    }

    //MODIFIES: this
    //EFFECTS: processes input from user
    public void processCommand(String command) {
        if (Integer.valueOf(command) >= 1 && Integer.valueOf(command) <= 7) {
        }
    }

    // EFFECTS: displays the calendar in the console
    public void displayCalendar() {
        clearConsole();
        System.out.println(calendar.getName() + " Calendar");
        System.out.println();
        System.out.println("                   " +
                currentDay.getDay().toString().substring(0, 1)
                + currentDay.getDay().toString().toLowerCase().substring(1) + "\'s Schedule:");
        System.out.println();
        System.out.println("|     Event Name     |     Event Time     |     Event Location     |");
        for (CalendarItem item : currentDay.getItems()) {
            System.out.println(String.format("| %-18s | %-8s - %7s | %-22s |", item.getName(), item.getStartTime(), item.getEndTime(), item.getLocation()));
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
