package model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

//A class that handles reading data from a CSV and passing into Course to create courses
public class ReadCSV {
    String courseName;
    String courseInfo;
    ArrayList<DayOfWeek> courseDays;
    LocalTime courseStart;
    LocalTime courseEnd;
    String courseLocation;

    final Map<String, DayOfWeek> dayMap = Map.of(
            "Mon", DayOfWeek.MONDAY,
            "Tue", DayOfWeek.TUESDAY,
            "Wed", DayOfWeek.WEDNESDAY,
            "Thu", DayOfWeek.THURSDAY,
            "Fri", DayOfWeek.FRIDAY);

    public ReadCSV(String name, String info) {
        courseName = name;
        courseInfo = info;
        courseDays = new ArrayList<DayOfWeek>();
    }

    // MODIFIES: this
    // EFFECTS: converts courseInfo into the course's days, start and end times, and
    // location, and passes it to course to create a course
    public void convert() {
        for (Entry<String, DayOfWeek> entry : dayMap.entrySet()) {
            if (courseInfo.contains(entry.getKey())) {
                courseDays.add(entry.getValue());
            }
        }

        int index1 = courseInfo.indexOf("|", courseInfo.indexOf("|") + 1);
        int index2 = courseInfo.indexOf("|", index1 + 1);
        String courseInfoTimes = courseInfo.substring(index1 + 1, index2).trim();
        courseStart = LocalTime.parse(courseInfoTimes.substring(0, courseInfoTimes.indexOf("-"))
                .trim().replace(".", "").toUpperCase(),
                DateTimeFormatter.ofPattern("h:mm a"));
        courseEnd = LocalTime.parse(courseInfoTimes.substring(courseInfoTimes.indexOf("-") + 1)
                .trim().replace(".", "").toUpperCase(),
                DateTimeFormatter.ofPattern("h:mm a"));

        System.out.println(courseInfo.substring(index2 + 1));

        courseLocation = courseInfo.substring(index2 + 1).substring(0, courseInfo.indexOf("-")).trim();

        pass();
    }

    // Passes info from convert to make new courses
    public void pass() {
        for (DayOfWeek day : courseDays) {
            switch (day.getValue()) {
                case 1:
                    Calendar.Monday.addItem(new Course(courseName, courseStart, courseEnd, courseLocation));
                    break;
                case 2:
                    Calendar.Tuesday.addItem(new Course(courseName, courseStart, courseEnd, courseLocation));
                    break;
                case 3:
                    Calendar.Wednesday.addItem(new Course(courseName, courseStart, courseEnd, courseLocation));
                    break;
                case 4:
                    Calendar.Thursday.addItem(new Course(courseName, courseStart, courseEnd, courseLocation));
                    break;
                case 5:
                    Calendar.Friday.addItem(new Course(courseName, courseStart, courseEnd, courseLocation));
                    break;
            }
        }
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseInfo() {
        return courseInfo;
    }

    public ArrayList<DayOfWeek> getCourseDays() {
        return courseDays;
    }

    public LocalTime getCourseStart() {
        return courseStart;
    }

    public LocalTime getCourseEnd() {
        return courseEnd;
    }

    public String getCourseLocation() {
        return courseLocation;
    }

}
