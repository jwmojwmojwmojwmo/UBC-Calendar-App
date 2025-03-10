package ui;

import javax.swing.JButton;
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

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.FileNotFoundException;

import model.Calendar;

public class CalendarPanel {
    JFrame calendarFrame;
    JPanel newCalendarPanel;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu editMenu;
    JMenuItem newItem;
    JMenuItem saveItem;
    JMenuItem loadItem;
    String filePath = "";
    Calendar calendar;
    JLabel welcomeLabel;
    boolean calendarInitialised = false;

    final int frameWidth = 1280;
    final int frameHeight = 720;

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

    private void newCalendar() throws FileNotFoundException {
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

    private void saveCalendar() {
        JOptionPane.showMessageDialog(null, "Save");
    }

    private void loadCalendar() {
        JOptionPane.showMessageDialog(null, "Load");
    }

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

    private void runCalendar() {
        fileMenu.add(saveItem);
        menuBar.add(editMenu);
        drawCalendar();
    }

    private void drawCalendar() {
        calendarFrame.remove(welcomeLabel);
        calendarFrame.setLayout(new GridLayout(1,6));
        calendarFrame.add(new JLabel("Time"));
        for (int i = 0; i <= 4; i++) {
            calendarFrame.add(new JLabel(calendar.getDaysOfWeek().get(i).getDay().toString()));
        }
        calendarFrame.revalidate();
        calendarFrame.repaint();
    }
}
