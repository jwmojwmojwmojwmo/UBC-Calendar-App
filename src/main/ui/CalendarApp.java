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
            System.out.println(
                    "Please enter the CSV's file path. If you need help, enter \"HELP\". If you do not want to upload a CSV, enter \"NEW\":");
            String input = userInput.nextLine();
            if (input.equals("HELP")) {
                System.out.println(
                        "1. To download the CSV file of your courses, log in to Workday. Go to Academics -> Registration and Courses.");
                System.out.println(
                        "2. Scroll down to the bottom, where there should be a box with the label \"Current Classes\". On the right, there is a gear icon.");
                System.out.println("3. Press the gear icon, then press \"Download to Excel\". Download the file.");
                System.out.println(
                        "4. Open the file in a spreadsheets application, such as Excel or Google Sheets. Navigate to \"Save As\" or \"Download\", and save the file as an CSV file.");
                System.out.println(
                        "Alternatively, you can Google \"XLSX to CSV\" to find an online converter that can convert the file into a CSV file.");
                System.out.println("5. Once the file is downloaded, find it and right click -> Copy as Path.");
                System.out.println("6. The copied string will have quotation marks on each side, you must remove these.");
                System.out.println("7. Once this is complete, you can paste the file path into this program!");
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
        while (true) {
            displayCalendar();
            System.out.println("Enter a number between 1-5 for each day of the week (1 -> Monday, 5 -> Friday).");
            System.out.println("Enter \"n\" to add a new item.");
            System.out.println("Enter the name of an item to edit it.");
            System.out.println("Enter \"c\" to change the name of the calendar.");
            System.out.println(
                    "Enter a day of week in numerical form and a time, separated by commas (ex. \"1, 12:00\"), to find what item is at that time.");
            System.out.println("Enter \"q\" to quit.");
            String nextCommand = userInput.nextLine();
            try {
                processCommand(nextCommand);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                System.out.println("Invalid inputs. Please double check your inputs.");
                waitForUser();  
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time. Times must be in \"HH:mm\" format.");
                waitForUser();
            }
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
                String[] dayAndTime = command.split(",");
                getItemAt(dayAndTime[0].trim(), dayAndTime[1].trim());
            }
        }
    }

    private void getItemAt(String day, String time) {
        System.out.println(Calendar.daysOfWeek.get(Integer.valueOf(day)).getItemAt(LocalTime.parse(time, format))); 
        waitForUser();
    }

    // MODIFIES: this
    // EFFECTS: edits the given item based on the user's input
    private void editItem(String name) {
        ArrayList<Day> days = daysOfItem(name);
        Integer index = days.get(0).getItemCalled(name);
        CalendarItem item = days.get(0).getItems().get(index);
        String oldName = item.getName();
        System.out
                .println("For any information that should not be changed, type \"same\".");
        System.out.println("If you would like to remove this item, enter \"REMOVE\" into the next line:");
        System.out.print("Enter the new days of the week numerically, separated by commas (ex: \"1, 3, 5\"): ");
        String newDays = userInput.nextLine();
        if (newDays.equals("REMOVE")) {
            changeDays(oldName, item, new ArrayList<>());
            return;
        }
        System.out.print("Enter new name: ");
        String newName = userInput.nextLine();
        if (daysOfItem(newName).size() != 0) {
            System.out.print("Name already taken! Enter anything to continue.");
            waitForUser();
            return;
        }
        if (newName.equals("same")) {
            newName = item.getName();
        }
        System.out.print("Enter the new start time: ");
        String newStartTime = userInput.nextLine();
        if (newStartTime.equals("same")) {
            newStartTime = item.getStartTime().toString();
        }
        System.out.print("Enter the new end time: ");
        String newEndTime = userInput.nextLine();
        if (newEndTime.equals("same")) {
            newEndTime = item.getEndTime().toString();
        }
        System.out.print("Enter the new location: ");
        String newLocation = userInput.nextLine();
        if (newLocation.equals("same")) {
            newLocation = item.getLocation();
        }
        item = new CalendarItem(newName, LocalTime.parse(newStartTime, format), LocalTime.parse(newEndTime, format),
                newLocation);
        if (!newDays.equals("same")) {
            changeDays(oldName, item, newDays);
        } else if (newDays.equals("same")) {
            changeDays(oldName, item, days);
        }
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
            waitForUser();
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
        System.out.println("Current time: " + LocalTime.now().format(format));
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
    // EFFECTS: helper method that takes an item, removes it from all days it used
    // to be in, and adds it to all the new days it should be in, which is given as
    // a string
    private void changeDays(String name, CalendarItem item, String days) {
        for (Day day : daysOfItem(name)) {
            day.removeItem(day.getItemCalled(name));
        }
        applyCourseToEachDay(days, item.getName(), item.getStartTime(), item.getEndTime(), item.getLocation());
    }

    // MODIFIES: this
    // EFFECTS: helper method that takes an item, removes it from all days it used
    // to be in, and adds it to all the new days it should be in, which is given as
    // a list of days
    private void changeDays(String name, CalendarItem item, ArrayList<Day> days) {
        for (Day day : daysOfItem(name)) {
            day.removeItem(day.getItemCalled(name));
        }
        applyCourseToEachDay(days, item.getName(), item.getStartTime(), item.getEndTime(), item.getLocation());
    }

    // MODIFIES: this
    // EFFECTS: helper method that creates a new course using a string of given
    // days, based on the parameters
    private void applyCourseToEachDay(String days, String name, LocalTime start, LocalTime end, String location) {
        String[] eachDay = days.split(",");
        for (String string : eachDay) {
            Calendar.daysOfWeek.get(Integer.valueOf(string.trim()) - 1)
                    .addItem(new CalendarItem(name, start, end, location));
        }
    }

    // MODIFIES: this
    // EFFECTS: helper method that creates a new course using a list of given days,
    // based on the parameters
    private void applyCourseToEachDay(ArrayList<Day> days, String name, LocalTime start, LocalTime end,
            String location) {
        for (Day day : days) {
            day.addItem(new CalendarItem(name, start, end, location));
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

    // EFFECTS: waits for user acknowledgment after an error
    private void waitForUser() {
        System.out.println("Press enter to continue: ");
        userInput.nextLine();
    }

    // EFFECTS: clears the console
    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
