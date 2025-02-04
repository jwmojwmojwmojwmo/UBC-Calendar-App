package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

public class TestCalendar {
    Calendar testCalendar1;
    Calendar testCalendar2;
    CalendarItem testCourse;
    Day Monday = new Day(DayOfWeek.MONDAY, new ArrayList<>());

    @BeforeEach
    void runBefore() {
        testCalendar1 = new Calendar("test");
        testCalendar2 = new Calendar("jwmo");
        testCourse = new Course("Math", LocalTime.of(13,0), LocalTime.of(14,0), "BUCH");
        Monday.addItem(testCourse);
    }

    @Test
    void testAddCourses() {
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
