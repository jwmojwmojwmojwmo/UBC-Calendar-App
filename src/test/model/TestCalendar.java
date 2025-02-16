package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;

public class TestCalendar {
    Calendar testCalendar1;
    Calendar testCalendar2;
    CalendarItem testCourse;
    CalendarItem testCSVCourse;
    ArrayList<CalendarItem> testList = new ArrayList<CalendarItem>();

    @BeforeEach
    void runBefore() {
        testCalendar1 = new Calendar("test");
        testCalendar2 = new Calendar("jwmo");
        testCourse = new CalendarItem("Math", LocalTime.of(13, 0), LocalTime.of(14, 0), "BUCH");
        testCSVCourse = new CalendarItem("CPSC_V 210-202 - Software Construction", LocalTime.of(11, 0), LocalTime.of(12, 0),
                "LIFE");
    }

    @Test
    void testConstructor() {
        assertEquals("test", testCalendar1.getName());
        assertEquals("jwmo", testCalendar2.getName());
        assertEquals(5, testCalendar1.getDaysOfWeek().size());

    }

    @Test
    void testAddCourses() {
        try {
            assertEquals(new ArrayList<CalendarItem>(), testCalendar1.getDaysOfWeek().get(0).getItems());
            testCalendar1.addCourses("src\\test\\model\\TestCSVFile.csv");
            assertEquals("CPSC_V 210-202", testCalendar1.getDaysOfWeek().get(0).getItems().get(0).getName());
            assertEquals(LocalTime.of(11, 0), testCalendar1.getDaysOfWeek().get(0).getItems().get(0).getStartTime());
            assertEquals(LocalTime.of(12, 0), testCalendar1.getDaysOfWeek().get(0).getItems().get(0).getEndTime());
            assertEquals("LIFE", testCalendar1.getDaysOfWeek().get(0).getItems().get(0).getLocation());
        } catch (FileNotFoundException e) {
        }
    }

    @Test
    void testBrokenAddCourses() {
        try {
            testCalendar2.addCourses("src\\test\\model\\TestBrokenCSVFile.csv");
            for (Day day : testCalendar1.getDaysOfWeek()) {
                assertEquals(0, day.getItems().size());
            }
        } catch (FileNotFoundException e) {
        }
    }

    @Test
    void testDaysOfItem() {
        testCalendar1.getDaysOfWeek().get(0).addItem(testCourse);
        testCalendar1.getDaysOfWeek().get(0).addItem(testCSVCourse);
        testCalendar1.getDaysOfWeek().get(1).addItem(testCourse);
        ArrayList<Day> testList = new ArrayList<Day>();
        testList.add(testCalendar1.getDaysOfWeek().get(0));
        testList.add(testCalendar1.getDaysOfWeek().get(1));
        assertEquals(testList, testCalendar1.daysOfItem("Math"));
        assertEquals(new ArrayList<Day>(), testCalendar2.daysOfItem("Math"));
    }

    @Test
    void testChangeDaysStringDays() {
        testCalendar1.getDaysOfWeek().get(0).addItem(testCourse);
        testCalendar1.getDaysOfWeek().get(1).addItem(testCourse);
        assertEquals("Math", testCalendar1.getDaysOfWeek().get(0).getItems().get(0).getName());
        assertEquals("Math", testCalendar1.getDaysOfWeek().get(1).getItems().get(0).getName());
        testCalendar1.changeDays("Math", new CalendarItem("Science", LocalTime.of(14, 0), LocalTime.of(15,0), "ORCH"), "3,4");
        assertEquals(new ArrayList<CalendarItem>(), testCalendar1.getDaysOfWeek().get(0).getItems());
        assertEquals(new ArrayList<CalendarItem>(), testCalendar1.getDaysOfWeek().get(1).getItems());
        assertEquals("Science", testCalendar1.getDaysOfWeek().get(2).getItems().get(0).getName());
        assertEquals(LocalTime.of(14,0), testCalendar1.getDaysOfWeek().get(2).getItems().get(0).getStartTime());
        assertEquals(LocalTime.of(15,0), testCalendar1.getDaysOfWeek().get(2).getItems().get(0).getEndTime());
        assertEquals("ORCH", testCalendar1.getDaysOfWeek().get(2).getItems().get(0).getLocation());
        assertEquals("Science", testCalendar1.getDaysOfWeek().get(3).getItems().get(0).getName());
        assertEquals(LocalTime.of(14,0), testCalendar1.getDaysOfWeek().get(3).getItems().get(0).getStartTime());
        assertEquals(LocalTime.of(15,0), testCalendar1.getDaysOfWeek().get(3).getItems().get(0).getEndTime());
        assertEquals("ORCH", testCalendar1.getDaysOfWeek().get(3).getItems().get(0).getLocation());
    }

    @Test
    void testChangeItemInfo() {
        testCalendar1.getDaysOfWeek().get(0).addItem(testCourse);
        assertEquals(0, testCalendar1.getDaysOfWeek().get(0).getItemCalled("Math"));
        testCalendar1.changeItemInfo("Math", new CalendarItem("Science", LocalTime.of(14,0), LocalTime.of(15,0), "ORCH"));
        assertEquals("Science", testCalendar1.getDaysOfWeek().get(0).getItems().get(0).getName());
        assertEquals(LocalTime.of(14,0), testCalendar1.getDaysOfWeek().get(0).getItems().get(0).getStartTime());
        assertEquals(LocalTime.of(15,0), testCalendar1.getDaysOfWeek().get(0).getItems().get(0).getEndTime());
        assertEquals("ORCH", testCalendar1.getDaysOfWeek().get(0).getItems().get(0).getLocation());
    }

    @Test
    void testRemoveItem() {
        testCalendar1.getDaysOfWeek().get(0).addItem(testCourse);
        testCalendar1.getDaysOfWeek().get(1).addItem(testCourse);
        testCalendar1.getDaysOfWeek().get(1).addItem(testCSVCourse);
        testCalendar1.removeItem("Math");
        assertEquals(0, testCalendar1.getDaysOfWeek().get(0).getItems().size());
        assertEquals(1, testCalendar1.getDaysOfWeek().get(1).getItems().size());
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
