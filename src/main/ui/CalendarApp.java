package ui;

public class CalendarApp {

    //EFFECTS: gets CSV file from user
    public CalendarApp() {
        getCSV();
    }

    //MODIFIES: this
    //EFFECTS: prompts user to enter CSV file
    public void getCSV() {
        System.out.println("Please enter the CSV's file path. If you need help, enter \"HELP\". If you do not want to upload a CSV, enter \"NEW\":");
    }
}
