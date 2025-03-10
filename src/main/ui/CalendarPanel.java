package ui;

import javax.swing.ImageIcon;
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
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import model.Calendar;

public class CalendarPanel {
    JFrame calendarFrame;
    JPanel newCalendarFrame;
    JMenuItem newItem;
    JMenuItem saveItem;
    JMenuItem loadItem;
    String filePath;
    Calendar calendar;

    final int frameWidth = 1280;
    final int frameHeight = 720;

    public CalendarPanel() {
        calendarFrame = new JFrame("Calendar");
        calendarFrame.setSize(frameWidth, frameHeight);
        calendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calendarFrame.setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        newItem = new JMenuItem("New");
        saveItem = new JMenuItem("Save");
        loadItem = new JMenuItem("Load");
        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        menuBar.add(fileMenu);
        JLabel welcomeLabel = new JLabel("Start a new calendar, or load a previously saved one!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        calendarFrame.setJMenuBar(menuBar);
        calendarFrame.add(welcomeLabel);
        calendarFrame.setVisible(true);
        runCalendar();
    }

    private void runCalendar() {
        newItem.addActionListener(e -> {
            try {
                newCalendar();
            } catch (FileNotFoundException f) {
                JOptionPane.showMessageDialog(calendarFrame, "File not found!");
            }
        });
        saveItem.addActionListener(e -> saveCalendar());
        loadItem.addActionListener(e -> loadCalendar());
    }

    private void newCalendar() throws FileNotFoundException {
        newCalendarFrame = new JPanel();
        newCalendarFrame.setSize(frameWidth, frameHeight);
        JLabel nameLabel = new JLabel("Enter name of calendar: ");
        JTextField nameText = new JTextField(10);
        JLabel csvLabel = new JLabel("Enter CSV file path, or leave blank: ");
        JTextField csvText = new JTextField(30);
        JButton csvButton = new JButton("...");
        csvButton.addActionListener(e -> {
            filePath = fileExplorer();
            csvText.setText(filePath);
        });
        newCalendarFrame.add(nameLabel);
        newCalendarFrame.add(nameText);
        newCalendarFrame.add(csvLabel);
        newCalendarFrame.add(csvText);
        newCalendarFrame.add(csvButton);
        JOptionPane.showMessageDialog(calendarFrame, newCalendarFrame);
        calendar = new Calendar(nameText.getText());
        if (!(filePath.equals(""))) {
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
}
