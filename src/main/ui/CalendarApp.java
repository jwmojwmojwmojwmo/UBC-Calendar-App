package ui;

import model.Calendar;
import model.CalendarItem;
import model.Day;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import exceptions.InvalidTime;
import exceptions.ItemAlreadyExists;

public class CalendarApp {
    Scanner userInput;
    Calendar calendar;
    Day currentDay;
    DateTimeFormatter format;
    JsonWriter writer;
    JsonReader reader;
    String jsonFilePath;

    // EFFECTS: gets CSV file from user and runs the Calendar
    public CalendarApp() {
        userInput = new Scanner(System.in);
        writer = new JsonWriter();
        reader = new JsonReader();
        format = DateTimeFormatter.ofPattern("HH:mm");
        jsonFilePath = "data\\Calendar.json";
        clearConsole();
        makeNewCalendar();
        getCSV();
        runCalendar();
    }

    // MODIFIES: this, Calendar
    // EFFECTS: makes new calendar with given name
    private void makeNewCalendar() {
        System.out.print("Enter calendar's name:");
        String calendarName = userInput.nextLine();
        calendar = new Calendar(calendarName);
        currentDay = calendar.getDaysOfWeek().get(0);
    }

    // MODIFIES: this, Day, Calendar, CalendarItem
    // EFFECTS: prompts user to enter CSV file
    @SuppressWarnings("methodlength")
    // This method is long because of the explanation required for the user. There
    // is not much actual code.
    private void getCSV() {
        boolean validInput = false;
        do {
            System.out.print("Please enter the CSV's file path. If you need help, enter \"HELP\".");
            System.out.println("If you do not want to upload a CSV, enter \"NEW\":");
            String input = userInput.nextLine();
            if (input.equals("HELP")) {
                System.out.print("1. To download the CSV file of your courses, log in to Workday.");
                System.out.println(" Go to Academics -> Registration and Courses.");
                System.out.print("2. Scroll down to where there is a box called \"Current Classes\".");
                System.out.println("On the right, there is a gear icon.");
                System.out.println("3. Press the gear icon, then press \"Download to Excel\". Download the file.");
                System.out.print("4. Open the file in a spreadsheets application, such as Excel or Google Sheets. ");
                System.out.println("Find \"Save As\" or \"Download\", and save the file as a CSV file.");
                System.out.print("Alternatively, you can Google \"XLSX to CSV\" to find an online converter ");
                System.out.println("that can convert the file into a CSV file.");
                System.out.println("5. Once the file is downloaded, find it and right click -> Copy as Path.");
                System.out.println("6. The copied path will have quotation marks on each side, which must be deleted.");
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
    
    // MODIFIES: this, Day, CalendarItem, Calendar
    // EFFECTS: displays available commands and gets next input from user
    @SuppressWarnings("methodlength")
    // This method is long because of the explanation required for the user. There
    // is not much actual code.
    private void runCalendar() {
        while (true) {
            displayCalendar();
            System.out.println("Enter a number between 1-5 for each day of the week (1 -> Monday, 5 -> Friday).");
            System.out.println("Enter \"n\" to add a new item.");
            System.out.println("Enter the name of an item to edit it.");
            System.out.println("Enter \"c\" to change the name of the calendar.");
            System.out.println(
                    "Enter a day of week in numerical form and a time, separated by commas (ex. \"1, 12:00\"),");
            System.out.println("to find what item is at that time.");
            System.out.println("Enter \"s\" to save the calendar to the ./data directory.");
            System.out.println("Enter \"l\" to load the calendar from the ./data directory.");
            System.out.println("Enter \"q\" to quit.");
            String nextCommand = userInput.nextLine();
            try {
                processCommand(nextCommand);
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                printAndWait("Invalid inputs. Please double-check your inputs.");
            } catch (DateTimeParseException e) {
                printAndWait("Invalid time. Times must be in \"HH:mm\" format.");
            } catch (ItemAlreadyExists e) {
                printAndWait("This name is already taken!");
            } catch (InvalidTime e) {
                printAndWait("End time must be after start time.");
            } catch (IOException e) {
                printAndWait("File saving/loading encountered an error.");
            }
        }
    }

    // MODIFIES: this, Day, Calendar, CalendarItem
    // EFFECTS: processes input from user
    private void processCommand(String command) throws ItemAlreadyExists, InvalidTime, IOException {
        try {
            if (Integer.valueOf(command) >= 1 && Integer.valueOf(command) <= 5) {
                currentDay = calendar.getDaysOfWeek().get(Integer.valueOf(command) - 1);
            }
        } catch (NumberFormatException e) {
            if (command.equals("q")) {
                quitCalendar();
            } else if (command.equals("n")) {
                addNewItem();
            } else if (command.equals("c")) {
                changeCalendarName();
            } else if (command.equals("s")) {
                saveCalendar();
            } else if (command.equals("l")) {
                loadCalendar();
            } else if (calendar.daysOfItem(command).size() != 0) {
                editItem(command);
            } else {
                String[] dayAndTime = command.split(",");
                getItemAt(dayAndTime[0].trim(), dayAndTime[1].trim());
            }
        }
    }

    // EFFECTS: quits calendar and saves if user wants calendar to be saved
    private void quitCalendar() throws FileNotFoundException {
        System.out.println("Would you like to save the calendar? Y for yes, anything else for no.");
        if (userInput.nextLine().toUpperCase().equals("Y")) {
            saveCalendar();
        }
        System.exit(0);
    }

    // EFFECTS: saves the calendar to a .json file
    private void saveCalendar() throws FileNotFoundException {
        writer.write(calendar, jsonFilePath);
        printAndWait("Saved!");
    }

    // MODIFIES: this, Calendar, Day, CalendarItem
    // EFFECTS: saves the calendar to a .json file
    private void loadCalendar() throws IOException {
        calendar = reader.read(jsonFilePath);
        currentDay = calendar.getDaysOfWeek().get(0);
        printAndWait("Calendar loaded!");
    }

    // EFFECTS: outputs the item at the given day and time
    private void getItemAt(String day, String time) {
        printAndWait(calendar.getDaysOfWeek().get(Integer.valueOf(day) - 1)
                .getItemAt(LocalTime.parse(time, format)));
    }

    // MODIFIES: this, Day, CalendarItem, Calendar
    // EFFECTS: edits the given item based on the user's input
    @SuppressWarnings("methodlength")
    // Method is only 2 lines too long and all lines are necessary. 2 lines can be
    // removed by sacrificing
    // the user's understanding, however I do not believe this is good programming
    // design.
    private void editItem(String name) throws ItemAlreadyExists, InvalidTime {
        ArrayList<Day> days = calendar.daysOfItem(name);
        CalendarItem item = days.get(0).getItems().get(days.get(0).getItemCalled(name));
        String oldName = item.getName();
        System.out.println("For any information that should not be changed, type \"same\".");
        System.out.println("If you would like to remove this item, enter \"REMOVE\" into the next line:");
        System.out.print("Enter the new days of the week numerically, separated by commas (ex: \"1, 3, 5\"): ");
        String newDays = userInput.nextLine();
        if (newDays.equals("REMOVE")) {
            calendar.removeItem(oldName);
            return;
        }
        System.out.print("Enter new name: ");
        String newName = userInput.nextLine();
        System.out.print("Enter the new start time: ");
        String newStartTime = userInput.nextLine();
        System.out.print("Enter the new end time: ");
        String newEndTime = userInput.nextLine();
        System.out.print("Enter the new location: ");
        String newLocation = userInput.nextLine();
        item = editItemInfo(newDays, newName, newStartTime, newEndTime, newLocation, item);
        if (newDays.equals("same")) {
            calendar.changeItemInfo(oldName, item);
        } else {
            calendar.changeDays(oldName, item, newDays);
        }
    }

    // MODIFIES: this, Calendar, CalendarItem, Day
    // EFFECTS: edits the item into the new information
    private CalendarItem editItemInfo(String days, String name, String start, String end, String location,
            CalendarItem item) throws ItemAlreadyExists, InvalidTime {
        if (calendar.daysOfItem(name).size() != 0) {
            throw new ItemAlreadyExists();
        }
        if (name.equals("same")) {
            name = item.getName();
        }
        if (start.equals("same")) {
            start = item.getStartTime().toString();
        }
        if (end.equals("same")) {
            end = item.getEndTime().toString();
        }
        LocalTime startTime = LocalTime.parse(start, format);
        LocalTime endTime = LocalTime.parse(end, format);
        if (endTime.isBefore(startTime)) {
            throw new InvalidTime();
        }
        if (location.equals("same")) {
            location = item.getLocation();
        }
        return new CalendarItem(name, startTime, endTime, location);
    }

    // MODIFIES: this, CalendarItem, Calendar, Day
    // EFFECTS: adds a new item to the calendar based on the user's input
    private void addNewItem() throws InvalidTime, ItemAlreadyExists {
        System.out.print("Enter the numeric days of the week of the item, separated by commas (ex: \"1, 3, 5\"): ");
        String days = userInput.nextLine();
        System.out.print("Enter the name of the item: ");
        String itemName = userInput.nextLine();
        if (calendar.daysOfItem(itemName).size() != 0) {
            throw new ItemAlreadyExists();
        }
        System.out.print("Enter the starting time (in HH:mm form): ");
        String itemStartTime = userInput.nextLine();
        System.out.print("Enter the ending time (in HH:mm form): ");
        String itemEndTime = userInput.nextLine();
        if (LocalTime.parse(itemEndTime, format).isBefore(LocalTime.parse(itemStartTime, format))) {
            throw new InvalidTime();
        }
        System.out.print("Enter the name of the location: ");
        String location = userInput.nextLine();
        calendar.applyCourseToEachDay(days, itemName, LocalTime.parse(itemStartTime, format),
                LocalTime.parse(itemEndTime, format), location);
    }

    // MODIFIES: this, Calendar
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
        System.out.println();
        System.out.println("                   "
                + currentDay.getDay().toString().substring(0, 1)
                + currentDay.getDay().toString().toLowerCase().substring(1) + "\'s Schedule:");
        System.out.println();
        System.out.println("|     Event Name     |     Event Time     |     Event Location     |");
        for (CalendarItem item : currentDay.getItems()) {
            System.out.println(String.format("| %-18s | %-8s - %7s | %-22s |", item.getName(), item.getStartTime(),
                    item.getEndTime(), item.getLocation()));
        }
    }

    // EFFECTS: prints out a message and waits for user acknowledgment
    private void printAndWait(String message) {
        System.out.println(message);
        System.out.println("Press enter to continue: ");
        userInput.nextLine();
    }

    // EFFECTS: clears the console
    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
