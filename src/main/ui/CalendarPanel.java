package ui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import model.Calendar;
import model.CalendarItem;
import model.Day;

public class CalendarPanel {
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
        JOptionPane.showMessageDialog(null, "Save");
    }

    // MODIFIES: this, calendar
    // EFFECTS: Loads a calendar from file
    private void loadCalendar() {
        JOptionPane.showMessageDialog(null, "Load");
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
            newItem();
            calendarFrame.remove(mainPanel);
            drawCalendar();
        });
        editCalendarItem.addActionListener(e -> {
            editItem();
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
    private void newItem() {
        makeDialogueNewItem();
        addInterfaceNewItem();
        int result = JOptionPane.showConfirmDialog(calendarFrame, newItemPanel, "Enter Item Details", 2);
        if (result == JOptionPane.OK_OPTION) {
            String days = convertDays();
            days = String.join(",", days.split("")).trim();
            calendar.applyCourseToEachDay(days, nameItemText.getText(),
                    LocalTime.parse(startText.getText().trim(), format),
                    LocalTime.parse(endText.getText().trim(), format), locationText.getText());
        }
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
    // EFFECTS: adds items to pop-up for user to input new item information
    private void addInterfaceNewItem() {
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

    // MODIFIES: this, calendar
    // EFFECTS: Method to edit an existing item on the calendar
    private void editItem() {

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

    // EFFECTS: Sets up calendar UI by modifying initial calendar UI when no calendar is loaded in
    private void setupCalendar() {
        fileMenu.add(saveItem);
        newCalendarItem = new JMenuItem("New Item");
        editCalendarItem = new JMenuItem("Edit Item");
        changeCalendarName = new JMenuItem("Change Calendar Name");
        editMenu.add(newCalendarItem);
        editMenu.add(editCalendarItem);
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
                button.setEnabled(false);
                button.setBackground(Color.BLUE);
                mainPanel.add(button, c);
            }
        }
    }
}
