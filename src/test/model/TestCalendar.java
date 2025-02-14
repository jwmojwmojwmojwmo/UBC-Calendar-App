package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
    Day Monday = new Day(DayOfWeek.MONDAY);

    @BeforeEach
    void runBefore() {
        testCalendar1 = new Calendar("test");
        testCalendar2 = new Calendar("jwmo");
        testCourse = new Course("Math", LocalTime.of(13, 0), LocalTime.of(14, 0), "BUCH");
        testCSVCourse = new Course("CPSC_V 210-202 - Software Construction", LocalTime.of(11, 0), LocalTime.of(12, 0),
                "LIFE");
        Monday.addItem(testCourse);
    }

    @Test
    void testAddCourses() {
        try {
            assertEquals(new ArrayList<CalendarItem>(), Calendar.daysOfWeek.get(0).getItems());
            testCalendar1.addCourses("src\\test\\model\\TestCSVFile.csv");
            assertEquals("CPSC_V 210-202", Calendar.daysOfWeek.get(0).getItems().get(0).getName());
            assertEquals(LocalTime.of(11, 0), Calendar.daysOfWeek.get(0).getItems().get(0).getStartTime());
            assertEquals(LocalTime.of(12, 0), Calendar.daysOfWeek.get(0).getItems().get(0).getEndTime());
            assertEquals("LIFE", Calendar.daysOfWeek.get(0).getItems().get(0).getLocation());
        } catch (FileNotFoundException e) {
        }
    }

    @Test
    void testBrokenAddCourses() {
        try {
            testCalendar2.addCourses("src\\test\\model\\TestBrokenCSVFile.csv");
            for (Day day : Calendar.daysOfWeek) {
                assertEquals(0, day.getItems().size());
            }
        } catch (FileNotFoundException e) {
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
    void testGetName() {
        assertEquals("test", testCalendar1.getName());
        assertEquals("jwmo", testCalendar2.getName());
    }
}
