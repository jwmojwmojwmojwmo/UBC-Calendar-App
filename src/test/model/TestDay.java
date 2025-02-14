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
    CalendarItem testCourse2 = new Course("Science", LocalTime.of(12,30), LocalTime.of(13,30), "IRC");
    ArrayList<CalendarItem> testList = new ArrayList<CalendarItem>();

    @BeforeEach
    void runBefore() {
        monday = new Day(DayOfWeek.MONDAY);
    }

    @Test
    void testConstructor() {
        assertEquals(DayOfWeek.MONDAY, monday.getDay());
        assertEquals(testList, monday.getItems());
    }

    @Test
    void testAddItem() {
        monday.addItem(testCourse);
        testList.add(testCourse);
        assertEquals(testList, monday.getItems());
    }

    @Test
    void testRemoveItem() {
        monday.addItem(testCourse);
        testList.add(testCourse);
        assertEquals(testList, monday.getItems());
        monday.removeItem(testCourse);
        testList.remove(testCourse);
        assertEquals(testList, monday.getItems());
    }
    
    @Test
    void testRemoveItemAtIndex() {
        monday.addItem(testCourse);
        testList.add(testCourse);
        assertEquals(testList, monday.getItems());
        monday.removeItem(0);
        testList.remove(testCourse);
        assertEquals(testList, monday.getItems());
    }

    @Test
    void testGetItemAt() {
        monday.addItem(testCourse);
        assertEquals("Nothing found!", monday.getItemAt(LocalTime.of(0,0)));
        assertEquals("Nothing found!", monday.getItemAt(LocalTime.of(10,29)));
        assertEquals("Math", monday.getItemAt(LocalTime.of(10,30)));
        assertEquals("Math", monday.getItemAt(LocalTime.of(11,0)));
        assertEquals("Math", monday.getItemAt(LocalTime.of(12,30)));
        assertEquals("Nothing found!", monday.getItemAt(LocalTime.of(12,31)));
    }

    @Test
    void testGetItemCalled() {
        monday.addItem(testCourse);
        assertEquals(0, monday.getItemCalled("Math"));
        assertEquals(-1, monday.getItemCalled("Science"));
        monday.addItem(testCourse2);
        assertEquals(1, monday.getItemCalled("Science"));
    }

    @Test
    void testGetItems() {
        assertEquals(testList, monday.getItems());
        testList.add(testCourse);
        monday.addItem(testCourse);
        assertEquals(testList, monday.getItems());
    }
}