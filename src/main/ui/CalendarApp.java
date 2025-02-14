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
        boolean validInput = false;
        do {
            System.out.print(
                    "Please enter the CSV's file path. If you need help, enter \"HELP\". If you do not want to upload a CSV, enter \"NEW\":");
            String input = userInput.nextLine();
            if (input.equals("HELP")) {
                System.out.println("To download the CSV file of your courses, log in to Workday. Go to Academics -> Registration and Courses.");
                System.out.println("Scroll down to the bottom, where there should be a box with the label \"Current Classes\". On the right, there is a gear icon.");
                System.out.println("Press the gear icon, then press \"Download to Excel\". Download the file.");
                System.out.println("Open the file in a spreadsheets application, such as Excel or Google Sheets. Navigate to \"Save As\" or \"Download\", and save the file as an CSV file.");
                System.out.println("Alternatively, you can Google \"XLSX to CSV\" to find an online converter that can convert the file into a CSV file.");
                System.out.println("Once the file is downloaded, find it and right click -> Copy as Path.");
                System.out.println("The copied string will have quotation marks on each side, you must remove these."); 
                System.out.println("Once this is complete, you can paste the file path into this program!");
            } else if (input.equals("NEW")) {
                validInput = true;
            } else {
                try {
                    calendar.addCourses(input);
                    validInput = true;
                } catch (FileNotFoundException e) {
                    System.out.println("Invalid file path");
                }
            }
        } while (!validInput);
    }

    // MODIFIES: this
    // EFFECTS: displays available commands and gets next input from user
    public void runCalendar() {
        displayCalendar();
        System.out.println("Enter a number between 1-5 for each day of the week (1 -> Monday, 7 -> Friday).");
        System.out.println("Enter \"r\" to remove an item.");
        System.out.println("Enter \"n\" to add a new item.");
        System.out.println("Enter the name of an item to edit it.");
        System.out.println("Enter \"c\" to change the name of the calendar.");
        System.out.println(
                "Enter a day of week in numerical form and a time, separated by commas (ex. \"1, 12:00\"), to find what item is at that time.");
                System.out.println("Enter \"q\" to quit.");
        String nextCommand = userInput.nextLine();
        processCommand(nextCommand);
    }

    // MODIFIES: this
    // EFFECTS: processes input from user
    public void processCommand(String command) {
        if (command.equals("q")) {
            System.exit(0);
        } else if (Integer.valueOf(command) >= 1 && Integer.valueOf(command) <= 5) {
            currentDay = Calendar.daysOfWeek.get(Integer.valueOf(command) - 1);
        } 
        runCalendar();
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
            System.out.println(String.format("| %-18s | %-8s - %7s | %-22s |", item.getName(), item.getStartTime(),
                    item.getEndTime(), item.getLocation()));
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
