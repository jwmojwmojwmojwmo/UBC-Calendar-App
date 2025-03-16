package ui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import exceptions.InvalidTime;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.Calendar;
import model.CalendarItem;
import model.Day;
import persistance.JsonReader;
import persistance.JsonWriter;

public class CalendarPanel {
    JsonWriter writer;
    JsonReader reader;
    JFrame calendarFrame;
    JPanel newCalendarPanel;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu editMenu;
    JMenuItem newItem;
    JMenuItem saveItem;
    JMenuItem loadItem;
    JMenuItem newCalendarItem;
    JMenuItem editCalendarItem;
    JMenuItem changeCalendarName;
    String filePath = "";
    Calendar calendar;
    JLabel welcomeLabel;
    boolean calendarInitialised = false;
    JPanel newItemPanel;
    JLabel daysLabel;
    JCheckBox mon;
    JCheckBox tue;
    JCheckBox wed;
    JCheckBox thu;
    JCheckBox fri;
    JLabel nameItemLabel;
    JTextField nameItemText;
    JLabel startLabel;
    JTextField startText;
    JLabel endLabel;
    JTextField endText;
    JLabel locationLabel;
    JTextField locationText;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
    JPanel mainPanel;

    final int frameWidth = 1280;
    final int frameHeight = 720;

    // MODIFIES: this
    // EFFECTS: Creates the calendar window when no calendar is active
    public CalendarPanel() {
        writer = new JsonWriter();
        reader = new JsonReader();
        calendarFrame = new JFrame("Calendar");
        calendarFrame.setSize(frameWidth, frameHeight);
        calendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calendarFrame.setLayout(new BorderLayout());
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        newItem = new JMenuItem("New");
        saveItem = new JMenuItem("Save");
        loadItem = new JMenuItem("Load");
        editMenu = new JMenu("Edit");
        fileMenu.add(newItem);
        fileMenu.add(loadItem);
        menuBar.add(fileMenu);
        welcomeLabel = new JLabel("Start a new calendar, or load a previously saved one!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        calendarFrame.setJMenuBar(menuBar);
        calendarFrame.add(welcomeLabel);
        calendarFrame.setVisible(true);
        startCalendar();
    }

    // MODIFIES: this
    // EFFECTS: Adds buttons and action listeners to the calendar window when no
    // calendar is active
    private void startCalendar() {
        newItem.addActionListener(e -> {
            try {
                newCalendar();
                runCalendar();
            } catch (FileNotFoundException f) {
                JOptionPane.showMessageDialog(calendarFrame, "File not found!");
            }
        });
        loadItem.addActionListener(e -> {
            loadCalendar();
            try {
                calendarFrame.remove(mainPanel);
            } catch (NullPointerException e1) {
        
            }
            runCalendar();
        });
    }

    // MODIFIES: this, calendar
    // EFFECTS: Makes new calendar
    private void newCalendar() throws FileNotFoundException {
        mainPanel = new JPanel();
        newCalendarPanel = new JPanel();
        newCalendarPanel.setSize(frameWidth, frameHeight);
        JLabel nameLabel = new JLabel("Enter name of calendar: ");
        JTextField nameText = new JTextField(10);
        JLabel csvLabel = new JLabel("Enter CSV file path, or leave blank: ");
        JTextField csvText = new JTextField(30);
        JButton csvButton = new JButton("...");
        csvButton.addActionListener(e -> {
            filePath = fileExplorer();
            csvText.setText(filePath);
        });
        newCalendarPanel.add(nameLabel);
        newCalendarPanel.add(nameText);
        newCalendarPanel.add(csvLabel);
        newCalendarPanel.add(csvText);
        newCalendarPanel.add(csvButton);
        JOptionPane.showMessageDialog(calendarFrame, newCalendarPanel);
        calendar = new Calendar(nameText.getText());
        if (!(filePath.isEmpty())) {
            calendar.addCourses(filePath);
        }
    }

    // MODIFIES: this
    // REQUIRES: a calendar to be loaded in
    // EFFECTS: Saves the current calendar to file
    private void saveCalendar() {
        JOptionPane.showMessageDialog(calendarFrame,
                "Saving with file name: " + calendar.getName() + "\n Use this file name to retrieve this save");
        try {
            writer.write(calendar, "data/" + calendar.getName() + ".json");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(calendarFrame, "An error occured.");
        }
    }

    // MODIFIES: this, calendar
    // EFFECTS: Loads a calendar from file
    private void loadCalendar() {
        JLabel loadLabel = new JLabel("Enter name of calendar or file name given during save");
        JTextField loadText = new JTextField(10);
        JPanel loadPanel = new JPanel();
        loadPanel.add(loadLabel);
        loadPanel.add(loadText);
        int result = JOptionPane.showConfirmDialog(calendarFrame, loadPanel, "Load Calendar", 2);
        if (result == JOptionPane.OK_OPTION) {
            try {
                calendar = reader.read("data/" + loadText.getText() + ".json");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(calendarFrame,
                        "An error occured. Most likely the file name is incorrect.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Helper method that allows the user to choose a file visually
    private String fileExplorer() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        int returnValue = fileChooser.showOpenDialog(calendarFrame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getPath();
        } else {
            return "";
        }
    }

    // MODIFIES: this, calendar
    // EFFECTS: Main method for running the calendar
    private void runCalendar() {
        setupCalendar();
        drawCalendar();
        newCalendarItem.addActionListener(e -> {
            try {
                newItem();
            } catch (InvalidTime e1) {
                JOptionPane.showMessageDialog(calendarFrame, "Invalid time selected!");
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(calendarFrame, "Invalid inputs");
            }
            calendarFrame.remove(mainPanel);
            drawCalendar();
        });
        changeCalendarName.addActionListener(e -> {
            editName();
            calendarFrame.remove(mainPanel);
            drawCalendar();
        });
        saveItem.addActionListener(e -> {
            saveCalendar();
        });
    }

    // MODIFIES: this, calendar
    // EFFECTS: Method to add a new item to calendar
    private void newItem() throws InvalidTime, NumberFormatException {
        makeDialogueNewItem();
        addInterfaceItem();
        int result = JOptionPane.showConfirmDialog(calendarFrame, newItemPanel, "Enter Item Details", 2);
        if (result == JOptionPane.OK_OPTION) {
            if (LocalTime.parse(endText.getText().trim(), format)
                    .isBefore(LocalTime.parse(endText.getText().trim(), format))) {
                throw new InvalidTime();
            }
            String days = convertDays();
            days = String.join(",", days.split("")).trim();
            calendar.applyCourseToEachDay(days, nameItemText.getText(),
                    LocalTime.parse(startText.getText().trim(), format),
                    LocalTime.parse(endText.getText().trim(), format), locationText.getText());
        }
    }

    // MODIFIES: this, calendar
    // EFFECTS: Method to edit an existing item on the calendar
    private void editItem(CalendarItem item) throws InvalidTime {
        makeDialogueEditItem();
        addInterfaceItem();
        JCheckBox same = new JCheckBox("Use same days of week");
        JCheckBox remove = new JCheckBox("Delete this item (overrides anything else on this page)");
        newItemPanel.add(same);
        newItemPanel.add(remove);
        int result = JOptionPane.showConfirmDialog(calendarFrame, newItemPanel, "Enter Item Details", 2);
        if (result == JOptionPane.OK_OPTION) {
            if (remove.isSelected()) {
                calendar.removeItem(item.getName());
            } else {
                CalendarItem newItem = new CalendarItem(item.getName(), item.getStartTime(), item.getEndTime(),
                        item.getLocation());
                newItem = convertToItem(newItem);
                if (same.isSelected()) {
                    calendar.changeItemInfo(item.getName(), newItem);
                } else {
                    String days = convertDays();
                    days = String.join(",", days.split("")).trim();
                    calendar.changeDays(item.getName(), newItem, days);
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates the pop-up for user to input new item information
    private void makeDialogueNewItem() {
        newItemPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        daysLabel = new JLabel("Select days of week of item");
        mon = new JCheckBox("Monday");
        tue = new JCheckBox("Tuesday");
        wed = new JCheckBox("Wednesday");
        thu = new JCheckBox("Thursday");
        fri = new JCheckBox("Friday");
        nameItemLabel = new JLabel("Enter name of item");
        nameItemText = new JTextField(10);
        startLabel = new JLabel("\nEnter starting time, in HH:mm form");
        startText = new JTextField(5);
        endLabel = new JLabel("Enter ending time, in HH:mm form");
        endText = new JTextField(5);
        locationLabel = new JLabel("Enter location");
        locationText = new JTextField(10);
    }

    // MODIFIES: this
    // EFFECTS: adds items to pop-up for user to input item information
    private void addInterfaceItem() {
        newItemPanel.add(daysLabel);
        newItemPanel.add(mon);
        newItemPanel.add(tue);
        newItemPanel.add(wed);
        newItemPanel.add(thu);
        newItemPanel.add(fri);
        newItemPanel.add(nameItemLabel);
        newItemPanel.add(nameItemText);
        newItemPanel.add(startLabel);
        newItemPanel.add(startText);
        newItemPanel.add(endLabel);
        newItemPanel.add(endText);
        newItemPanel.add(locationLabel);
        newItemPanel.add(locationText);
    }

    // MODIFIES: this
    // EFFECTS: Creates the pop-up for user to input edited item information
    private void makeDialogueEditItem() {
        newItemPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        daysLabel = new JLabel("Select days of week of item (select check box at bottom for same)");
        mon = new JCheckBox("Monday");
        tue = new JCheckBox("Tuesday");
        wed = new JCheckBox("Wednesday");
        thu = new JCheckBox("Thursday");
        fri = new JCheckBox("Friday");
        nameItemLabel = new JLabel("Enter name of item (leave blank for same)");
        nameItemText = new JTextField(10);
        startLabel = new JLabel("\nEnter starting time, in HH:mm form (leave blank for same)");
        startText = new JTextField(5);
        endLabel = new JLabel("Enter ending time, in HH:mm form (leave blank for same)");
        endText = new JTextField(5);
        locationLabel = new JLabel("Enter location (leave blank for same)");
        locationText = new JTextField(10);
    }

    // MODIFIES: this, newItem
    // EFFECTS: Helper method to edit an existing item on calendar
    private CalendarItem convertToItem(CalendarItem newItem) throws InvalidTime {
        if (!(nameItemText.getText().trim().equals(""))) {
            newItem.changeName(nameItemText.getText().trim());
        }
        if (!(startText.getText().trim().equals(""))) {
            newItem.changeStartTime(LocalTime.parse(startText.getText().trim(), format));
        }
        if (!(endText.getText().trim().equals(""))) {
            newItem.changeEndTime(LocalTime.parse(endText.getText().trim(), format));
        }
        if (newItem.getEndTime().isBefore(newItem.getStartTime())) {
            throw new InvalidTime();
        }
        if (!(locationText.getText().trim().equals(""))) {
            newItem.changeLocation(locationText.getText().trim());
        }
        return newItem;
    }

    // MODIFIES: this
    // EFFECTS: Helper method that converts checkboxes of days of week into a string
    private String convertDays() {
        String days = "";
        if (mon.isSelected()) {
            days = days + "1";
        }
        if (tue.isSelected()) {
            days = days + "2";
        }
        if (wed.isSelected()) {
            days = days + "3";
        }
        if (thu.isSelected()) {
            days = days + "4";
        }
        if (fri.isSelected()) {
            days = days + "5";
        }
        return days;
    }

    // MODIFIES: this, calendar
    // EFFECTS: Method to edit the name of the calendar
    private void editName() {
        JPanel editNamePanel = new JPanel();
        JLabel nameCalendarLabel = new JLabel("Enter new name of calendar");
        JTextField nameCalendarText = new JTextField(10);
        editNamePanel.add(nameCalendarLabel);
        editNamePanel.add(nameCalendarText);
        int result = JOptionPane.showConfirmDialog(calendarFrame, editNamePanel, "Enter New Name", 2);
        if (result == JOptionPane.OK_OPTION) {
            calendar.changeName(nameCalendarText.getText().trim());
        }
    }

    // EFFECTS: Sets up calendar UI by modifying initial calendar UI when no
    // calendar is loaded in
    private void setupCalendar() {
        fileMenu.add(saveItem);
        newCalendarItem = new JMenuItem("New Item");
        changeCalendarName = new JMenuItem("Change Calendar Name");
        editMenu.add(newCalendarItem);
        editMenu.add(changeCalendarName);
        menuBar.add(editMenu);
        calendarFrame.remove(welcomeLabel);
    }

    // MODIFIES: this
    // EFFECTS: draws the calendar ui
    private void drawCalendar() {
        calendarFrame.setTitle(calendar.getName());
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 60, 15, 60);
        for (int i = 0; i <= 23; i++) {
            c.gridx = 0;
            c.gridy = i + 1;
            mainPanel.add(new JLabel(Integer.toString(i) + ":00"), c);
        }
        String[] headers = { "Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
        for (int i = 0; i < headers.length; i++) {
            c.gridx = i;
            c.gridy = 0;
            mainPanel.add(new JLabel(headers[i]), c);
        }
        drawItemsToCalendar(c);
        calendarFrame.add(mainPanel);
        calendarFrame.revalidate();
        calendarFrame.repaint();
    }

    // MODIFIES: this
    // EFFECTS: helper method that draws items onto the calendar ui
    private void drawItemsToCalendar(GridBagConstraints c) {
        c.insets = new Insets(0, 0, 0, 0);
        for (Day day : calendar.getDaysOfWeek()) {
            for (CalendarItem item : day.getItems()) {
                c.gridx = day.getDay().getValue();
                c.gridy = item.getStartTime().getHour() + 1;
                c.gridheight = item.getEndTime().getHour() - item.getStartTime().getHour();
                JButton button = new JButton("<html>" + item.getName() + "<br>"
                        + item.getStartTime().toString() + "-" + item.getEndTime().toString() + "<br>"
                        + item.getLocation());
                // button.setEnabled(false);
                button.addActionListener(e -> {
                    try {
                        editItem(item);
                        calendarFrame.remove(mainPanel);
                        drawCalendar();
                    } catch (InvalidTime e1) {
                        JOptionPane.showMessageDialog(calendarFrame, "Invalid time selected!");
                    }
                });
                button.setBackground(Color.CYAN);
                mainPanel.add(button, c);
            }
        }
    }
}
