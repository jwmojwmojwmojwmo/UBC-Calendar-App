package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;

public class CalendarPanel {
    JFrame calendarFrame;

    public CalendarPanel() {
        calendarFrame = new JFrame("Calendar");
        calendarFrame.setSize(1280, 720);
        calendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calendarFrame.setLayout(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Load");
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
    }

}
