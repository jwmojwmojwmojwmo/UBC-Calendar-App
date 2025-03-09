package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

public class CalendarPanel {
    JFrame calendarFrame;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem newItem;
    JMenuItem saveItem;
    JMenuItem loadItem;
    JLabel welcomeLabel;
    JPanel newCalendar;

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
        fileMenu.add(newItem);
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        menuBar.add(fileMenu);
        welcomeLabel = new JLabel("Start a new calendar, or load a previously saved one!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        calendarFrame.setJMenuBar(menuBar);
        calendarFrame.add(welcomeLabel);
        calendarFrame.setVisible(true);
        runCalendar();
    }

    private void runCalendar() {
        newItem.addActionListener(e -> newCalendar());
        saveItem.addActionListener(e -> saveCalendar());
        loadItem.addActionListener(e -> loadCalendar());
    }

    private void newCalendar() {
        newCalendar = new JPanel();
        newCalendar.setSize(frameWidth, frameHeight);
        JLabel nameLabel = new JLabel("Enter the name of the calendar");
        JTextField nameText = new JTextField(20);
        newCalendar.add(nameLabel);
        newCalendar.add(nameText);
        JOptionPane.showMessageDialog(null, newCalendar);

    }

    private void saveCalendar() {
        JOptionPane.showMessageDialog(null, "Save");
    }

    private void loadCalendar() {
        JOptionPane.showMessageDialog(null, "Load");
    }

}
