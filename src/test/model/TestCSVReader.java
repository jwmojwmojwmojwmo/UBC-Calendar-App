package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCSVReader {
    String testCourseName = "CPSC210";
    String testCourseInfo = "| Mon Wed Fri | 11:00 a.m. - 12:00 p.m. | LIFE-Floor 2-Room 2201";
    CSVReader testCSVReader;
    ArrayList<DayOfWeek> testDays = new ArrayList<DayOfWeek>();

    @BeforeEach
    void runBefore() {
        testCSVReader = new CSVReader(testCourseName, testCourseInfo);
        testDays.add(DayOfWeek.MONDAY);
        testDays.add(DayOfWeek.WEDNESDAY);
        testDays.add(DayOfWeek.FRIDAY);
    }

    @Test
    void testConstructor() {
        assertEquals(testCourseName, testCSVReader.getCourseName());
        assertEquals(testCourseInfo, testCSVReader.getCourseInfo());
    }

    @Test
    void testConvert() {
        testCSVReader.convert();
        assertEquals(testDays, testCSVReader.getCourseDays());
        assertEquals(LocalTime.of(11,0), testCSVReader.getCourseStart());
        assertEquals(LocalTime.of(12,0), testCSVReader.getCourseEnd());
        assertEquals("LIFE", testCSVReader.getCourseLocation());
    }
}
