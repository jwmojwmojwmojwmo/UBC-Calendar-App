package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCalendarItem {
    CalendarItem testCourse;
    CalendarItem testExtra;
    LocalTime courseStartTime = LocalTime.of(10, 30);
    LocalTime courseEndTime = LocalTime.of(12,30);
    LocalTime extraStartTime = LocalTime.of(17,30);
    LocalTime extraEndTime = LocalTime.of(20,00);

    @BeforeEach
    void runBefore() {
        testCourse = new Course("Math", courseStartTime, courseEndTime, "IRC");
        testExtra = new Extracurricular("Minecraft Club", extraStartTime, extraEndTime, "Minecraft Club House");
    }

    @Test
    void testConstructor() {
        assertEquals("Math", testCourse.getName());
        assertEquals("10:30", testCourse.getstartTime());
        assertEquals("12:30", testCourse.getEndTime());
        assertEquals("IRC", testCourse.getLocation());
    }

    @Test
    void testChangeName() {
        assertEquals("Math", testCourse.getName());
        testCourse.changeName("Science");
        assertEquals("Science", testCourse.getName());
    }

    @Test
    void testChangeStartTime() {
        assertEquals("17:30", testExtra.getstartTime());
        testExtra.changeStartTime(LocalTime.of(9,0));
        assertEquals("9:00", testExtra.getstartTime());
    }

    @Test
    void testChangeEndTime() {
        assertEquals("20:00", testExtra.getEndTime());
        testExtra.changeEndTime(LocalTime.of(23,0));
        assertEquals("23:00", testExtra.getEndTime());
    }

    @Test
    void testChangeLocation() {
        assertEquals("IRC", testCourse.getLocation());
        testCourse.changeLocation("GEOG");
        assertEquals("GEOG", testCourse.getLocation());
    }
}
