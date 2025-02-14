package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.time.DayOfWeek;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCSVReader {
    String testCourseName = "CPSC210-202 - Software Construction";
    String testCourseInfo1 = "\"2025-01-06 - 2025-02-14 | Mon Wed Fri | 11:00 a.m. - 12:00 p.m. | LIFE-Floor 2-Room 2201";
    String testCourseInfo2 = "\"2025-01-06 - 2025-02-14 | Tue Thu | 11:00 a.m. - 12:00 p.m. | LIFE-Floor 2-Room 2201";
    Course testCourse;
    ReadCSV testCSVReader;

    @BeforeEach
    void runBefore() {
        testCSVReader = new ReadCSV(testCourseName, testCourseInfo1);
        testCourse = new Course("CPSC210-202", LocalTime.of(11, 0), LocalTime.of(12, 0), "LIFE");
    }

    @Test
    void testConstructor() {
        assertEquals("CPSC210-202", testCSVReader.getCourseName());
        assertEquals(testCourseInfo1, testCSVReader.getCourseInfo());
    }

    @Test
    void testConvert() {
        testCSVReader.convert();
        assertTrue(testCSVReader.getCourseDays().contains(DayOfWeek.MONDAY));
        assertTrue(testCSVReader.getCourseDays().contains(DayOfWeek.WEDNESDAY));
        assertTrue(testCSVReader.getCourseDays().contains(DayOfWeek.FRIDAY));
        assertFalse(testCSVReader.getCourseDays().contains(DayOfWeek.TUESDAY));
        assertFalse(testCSVReader.getCourseDays().contains(DayOfWeek.THURSDAY));
        assertEquals(LocalTime.of(11, 0), testCSVReader.getCourseStart());
        assertEquals(LocalTime.of(12, 0), testCSVReader.getCourseEnd());
        assertEquals("LIFE", testCSVReader.getCourseLocation());
        testCSVReader = new ReadCSV(testCourseName, testCourseInfo2);
        testCSVReader.convert();
        assertFalse(testCSVReader.getCourseDays().contains(DayOfWeek.MONDAY));
        assertFalse(testCSVReader.getCourseDays().contains(DayOfWeek.WEDNESDAY));
        assertFalse(testCSVReader.getCourseDays().contains(DayOfWeek.FRIDAY));
        assertTrue(testCSVReader.getCourseDays().contains(DayOfWeek.TUESDAY));
        assertTrue(testCSVReader.getCourseDays().contains(DayOfWeek.THURSDAY));
    }

    @Test
    void pass() {
        Calendar.daysOfWeek.get(0).removeAll();
        Calendar.daysOfWeek.get(2).removeAll();
        Calendar.daysOfWeek.get(4).removeAll();
        assertEquals(new ArrayList<CalendarItem>(), Calendar.daysOfWeek.get(0).getItems());
        assertEquals(new ArrayList<CalendarItem>(), Calendar.daysOfWeek.get(2).getItems());
        assertEquals(new ArrayList<CalendarItem>(), Calendar.daysOfWeek.get(4).getItems());
        testCSVReader.convert();
        assertEquals("CPSC210-202", Calendar.daysOfWeek.get(0).getItems().get(0).getName());
        assertEquals(LocalTime.of(11,0), Calendar.daysOfWeek.get(0).getItems().get(0).getStartTime());
        assertEquals(LocalTime.of(12,0), Calendar.daysOfWeek.get(0).getItems().get(0).getEndTime());
        assertEquals("LIFE", Calendar.daysOfWeek.get(0).getItems().get(0).getLocation());
        assertEquals("CPSC210-202", Calendar.daysOfWeek.get(2).getItems().get(0).getName());
        assertEquals(LocalTime.of(11,0), Calendar.daysOfWeek.get(2).getItems().get(0).getStartTime());
        assertEquals(LocalTime.of(12,0), Calendar.daysOfWeek.get(2).getItems().get(0).getEndTime());
        assertEquals("LIFE", Calendar.daysOfWeek.get(2).getItems().get(0).getLocation());
        assertEquals("CPSC210-202", Calendar.daysOfWeek.get(4).getItems().get(0).getName());
        assertEquals(LocalTime.of(11,0), Calendar.daysOfWeek.get(4).getItems().get(0).getStartTime());
        assertEquals(LocalTime.of(12,0), Calendar.daysOfWeek.get(4).getItems().get(0).getEndTime());
        assertEquals("LIFE", Calendar.daysOfWeek.get(4).getItems().get(0).getLocation());
    }
}
