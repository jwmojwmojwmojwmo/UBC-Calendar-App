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
        calendarFrame.remove(welcomeLabel);
        calendarFrame.setTitle(calendar.getName());
        drawCalendar();
    }

    private void drawCalendar() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 60, 15, 60); 
        for (int i = 0; i <= 23; i++) {
            c.gridx = 0;
            c.gridy = i + 1;
            panel.add(new JLabel(Integer.toString(i) + ":00"), c);
        }
        String[] headers = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for (int i = 0; i < headers.length; i++) {
            c.gridx = i;
            c.gridy = 0;
            panel.add(new JLabel(headers[i]), c);
        }
        c.insets = new Insets(0,0,0,0);
        for (Day day : calendar.getDaysOfWeek()) {
            for (CalendarItem item : day.getItems()) {
                c.gridx = day.getDay().getValue();
                c.gridy = item.getStartTime().getHour() + 1;
                c.gridheight = item.getEndTime().getHour() - item.getStartTime().getHour();
                JButton button = new JButton("<html>" + item.getName() + "<br>" 
                + item.getStartTime().toString() + "-" + item.getEndTime().toString() + "<br>" + item.getLocation());
                button.setEnabled(false);
                button.setBackground(Color.BLUE);
                panel.add(button, c);
            }
        }
        calendarFrame.add(panel);
        calendarFrame.revalidate();
        calendarFrame.repaint();
    }
}
