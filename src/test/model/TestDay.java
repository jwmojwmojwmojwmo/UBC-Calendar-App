package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

public class TestDay {
    Day monday;
    LocalTime courseStartTime = LocalTime.of(10, 30);
    LocalTime courseEndTime = LocalTime.of(12,30);
    CalendarItem testCourse = new Course("Math", courseStartTime, courseEndTime, "IRC");
    ArrayList<CalendarItem> testList = new ArrayList<CalendarItem>();

    @BeforeEach
    void runBefore() {
        monday = new Day(DayOfWeek.MONDAY, testList);
    }

    @Test
    void testConstructor() {
        assertEquals(DayOfWeek.MONDAY, monday.getDay());
        assertEquals(testList, monday.getItems());
    }

    @Test
    void testAddItem() {
        assertEquals(testList, monday.getItems());
        monday.addItem(testCourse);
        assertFalse(monday.getItems().equals(testList));
        testList.add(testCourse);
        assertEquals(testList, monday.getItems());
    }

    @Test
    void testRemoveItem() {
        monday.addItem(testCourse);
        testList.add(testCourse);
        assertEquals(testList, monday.getItems());
        monday.removeItem(testCourse);
        assertFalse(monday.getItems().equals(testList));
        testList.remove(testCourse);
        assertEquals(testList, monday.getItems());
    }

    @Test 
    void testGetBusyTimes() {
        assertEquals("Not busy today!", monday.getBusyTimes());
        monday.addItem(testCourse);
        assertEquals("10:30 - 12:30", monday.getBusyTimes());
    }

    @Test
    void testGetItems() {
        assertEquals(testList, monday.getItems());
        testList.add(testCourse);
        assertFalse(monday.getItems().equals(testList));
        monday.addItem(testCourse);
        assertEquals(testList, monday.getItems());
    }
}