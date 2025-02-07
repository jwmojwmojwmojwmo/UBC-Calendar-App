package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

public class TestCalendar {
    Calendar testCalendar1;
    Calendar testCalendar2;
    CalendarItem testCourse;
    CalendarItem testCSVCourse;
    ArrayList<CalendarItem> testList = new ArrayList<CalendarItem>();
    Day Monday = new Day(DayOfWeek.MONDAY, testList);

    @BeforeEach
    void runBefore() {
        testCalendar1 = new Calendar("test");
        testCalendar2 = new Calendar("jwmo");
        testCourse = new Course("Math", LocalTime.of(13,0), LocalTime.of(14,0), "BUCH");
        testCSVCourse = new Course("CPSC_V 210-202 - Software Construction", LocalTime.of(11,0), LocalTime.of(12,0), "LIFE");
        Monday.addItem(testCourse);
    }

    @Test
    void testAddCourses() {
        try {
            Monday.removeItem(testCourse);
            File file = new File("src\\test\\model\\TestCSVFile.csv");
            testCalendar1.addCourses(file.getAbsolutePath());
            testList.add(testCSVCourse);
            assertEquals(testList, Monday.getItems());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testConstructor() {
        assertEquals("test", testCalendar1.getName());
        assertEquals("jwmo", testCalendar2.getName());
    }

    @Test
    void testChangeName() {
        testCalendar1.changeName("test2");
        assertEquals("test2", testCalendar1.getName());
    }

    @Test
    void testGetItemAt() {
        assertEquals("Nothing found!", testCalendar1.getItemAt(Monday, LocalTime.of(0,0)));
        assertEquals("Math", testCalendar1.getItemAt(Monday, LocalTime.of(13,0)));
        assertEquals("Math", testCalendar1.getItemAt(Monday, LocalTime.of(14,0)));
        assertEquals("Math", testCalendar1.getItemAt(Monday, LocalTime.of(13,30)));
        assertEquals("Nothing found!", testCalendar1.getItemAt(Monday, LocalTime.of(12,59)));
        assertEquals("Nothing found!", testCalendar1.getItemAt(Monday, LocalTime.of(14,1)));
    }

    @Test
    void testGetName() {
        assertEquals("test", testCalendar1.getName());
        assertEquals("jwmo", testCalendar2.getName());
    }
}
