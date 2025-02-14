package ui;

import model.Calendar;
import model.CalendarItem;
import model.Day;

import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class CalendarApp {
    Scanner userInput;
    Calendar calendar;
    Day currentDay;
    String pause;
    DateTimeFormatter format;

    // EFFECTS: gets CSV file from user
    public CalendarApp() {
        userInput = new Scanner(System.in);
        format = DateTimeFormatter.ofPattern("HH:mm");
        clearConsole();
        makeNewCalendar();
        getCSV();
        runCalendar();
    }

    // MODIFIES: this
    // EFFECTS: makes new calendar with given name
    private void makeNewCalendar() {
        System.out.print("Enter calendar's name:");
        String calendarName = userInput.nextLine();
        calendar = new Calendar(calendarName);
        currentDay = Calendar.daysOfWeek.get(0);
    }

    // MODIFIES: this
    // EFFECTS: prompts user to enter CSV file
    private void getCSV() {
        boolean validInput = false;
        do {
            System.out.print(
                    "Please enter the CSV's file path. If you need help, enter \"HELP\". If you do not want to upload a CSV, enter \"NEW\":");
            String input = userInput.nextLine();
            if (input.equals("HELP")) {
                System.out.println(
                        "To download the CSV file of your courses, log in to Workday. Go to Academics -> Registration and Courses.");
                System.out.println(
                        "Scroll down to the bottom, where there should be a box with the label \"Current Classes\". On the right, there is a gear icon.");
                System.out.println("Press the gear icon, then press \"Download to Excel\". Download the file.");
                System.out.println(
                        "Open the file in a spreadsheets application, such as Excel or Google Sheets. Navigate to \"Save As\" or \"Download\", and save the file as an CSV file.");
                System.out.println(
                        "Alternatively, you can Google \"XLSX to CSV\" to find an online converter that can convert the file into a CSV file.");
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
    private void runCalendar() {
        displayCalendar();
        System.out.println("Enter a number between 1-5 for each day of the week (1 -> Monday, 7 -> Friday).");
        System.out.println("Enter \"n\" to add a new item.");
        System.out.println("Enter the name of an item to edit it.");
        System.out.println("Enter \"c\" to change the name of the calendar.");
        System.out.println(
                "Enter a day of week in numerical form and a time, separated by commas (ex. \"1, 12:00\"), to find what item is at that time.");
        System.out.println("Enter \"q\" to quit.");
        String nextCommand = userInput.nextLine();
        try {
            processCommand(nextCommand);
        } catch (NumberFormatException e) {
            System.out.println("Invalid numerical days of week!");
        } catch (IndexOutOfBoundsException e) {
            System.out.println(
                    "Number too big! Days of week are between 1-5, with 1 being Monday.");
            pause = userInput.next();
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date!");
        } finally {
            System.out.println("Enter anything to continue.");
            pause = userInput.next();
        }
    }

    // MODIFIES: this
    // EFFECTS: processes input from user
    private void processCommand(String command) {
        try {
            if (Integer.valueOf(command) >= 1 && Integer.valueOf(command) <= 5) {
                currentDay = Calendar.daysOfWeek.get(Integer.valueOf(command) - 1);
            }
        } catch (NumberFormatException e) {
            if (command.equals("q")) {
                System.exit(0);
            } else if (command.equals("n")) {
                addNewItem();
            } else if (command.equals("c")) {
                changeCalendarName();
            } else if (daysOfItem(command).size() != 0) {
                editItem(command);
            } else {
                System.out.println("Invalid command! Enter anything to continue.");
                pause = userInput.next();
            }
        }
        runCalendar();
    }

    // MODIFIES: this
    // EFFECTS: edits the given item based on the user's input
    private void editItem(String name) {
        System.out
                .println("For any information that should not be changed, type \"same\" without the quotation marks.");
        System.out.print("Enter the new days of the week numerically, separated by commas (ex: \"1, 3, 5\"): ");
        String newDays = userInput.nextLine();
        System.out.print("Enter new name: ");
        String newName = userInput.nextLine();
        if (daysOfItem(newName).size() != 0) {
            System.out.print("Name already taken! Enter anything to continue.");
            pause = userInput.next();
            return;
        }
        System.out.print("Enter the new start time: ");
        String newStartTime = userInput.nextLine();
        System.out.print("Enter the new end time: ");
        String newEndTime = userInput.nextLine();
        System.out.print("Enter the new location: ");
        String newLocation = userInput.nextLine();
        for (Day day : Calendar.daysOfWeek) {
            for (CalendarItem item : day.getItems()) {
                if (item.getName().equals(name)) {
                    if (newName.equals("same")) {
                        continue;
                    } else {
                        item.changeName(newName);
                    }
                    if (newStartTime.equals("same")) {
                        continue;
                    } else {
                        item.changeStartTime(LocalTime.parse(newStartTime, format));
                    }
                    if (newEndTime.equals("same")) {
                        continue;
                    } else {
                        item.changeEndTime(LocalTime.parse(newEndTime, format));
                    }
                    if (newLocation.equals("same")) {
                        continue;
                    } else {
                        item.changeLocation(newLocation);
                    }
                    if (newDays.equals("same")) {
                        continue;
                    } else {
                        changeDays(item, newDays);
                    }
                }
            }
        }
        runCalendar();
    }

    // MODIFIES: this
    // EFFECTS: helper method that takes an item, removes it from all days it used
    // to be in, and adds it to all the new days it should be in
    private void changeDays(CalendarItem item, String days) {
        for (Day day : daysOfItem(item.getName())) {
            day.removeItem(item);
        }
        applyCourseToEachDay(days, item.getName(), item.getStartTime(), item.getEndTime(), item.getLocation());
    }

    // MODIFIES: this
    // EFFECTS: adds a new item to the calendar based on the user's input
    private void addNewItem() {
        System.out.print("Enter the numeric days of the week of the item, separated by commas (ex: \"1, 3, 5\"): ");
        String days = userInput.nextLine();
        System.out.print("Enter the name of the item: ");
        String itemName = userInput.nextLine();
        if (daysOfItem(itemName).size() != 0) {
            System.out.println("Name already taken! Enter anything to continue.");
            pause = userInput.next();
            return;
        }
        System.out.print("Enter the starting time (in HH:mm form): ");
        String itemStartTime = userInput.nextLine();
        System.out.print("Enter the ending time (in HH:mm form): ");
        String itemEndTime = userInput.nextLine();
        System.out.print("Enter the name of the location: ");
        String location = userInput.nextLine();
        applyCourseToEachDay(days, itemName, LocalTime.parse(itemStartTime, format),
                LocalTime.parse(itemEndTime, format), location);
    }

    // MODIFIES: this
    // EFFECTS: changes the calendar's name to user's input
    private void changeCalendarName() {
        System.out.print("Enter new name: ");
        String newName = userInput.nextLine();
        calendar.changeName(newName);
    }

    // EFFECTS: displays the calendar in the console
    private void displayCalendar() {
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

    // MODIFIES: this
    // EFFECTS: helper method that creates a new course based on the parameters in
    // the given days
    private void applyCourseToEachDay(String days, String name, LocalTime start, LocalTime end, String location) {
        String[] eachDay = days.split(",");
        for (String string : eachDay) {
            Calendar.daysOfWeek.get(Integer.valueOf(string.trim()) - 1)
                    .addItem(new CalendarItem(name, start, end, location));
        }
    }

    // EFFECTS: helper method that returns the days that the given item is in
    private ArrayList<Day> daysOfItem(String name) {
        ArrayList<Day> daysItemIsIn = new ArrayList<Day>();
        for (Day day : Calendar.daysOfWeek) {
            for (CalendarItem item : day.getItems()) {
                if (item.getName().equals(name)) {
                    daysItemIsIn.add(day);
                }
            }
        }
        return daysItemIsIn;
    }

    // EFFECTS: clears the console
    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
