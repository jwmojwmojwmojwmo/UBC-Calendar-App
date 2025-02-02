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
    }

    @Test
    void testChangeName() {
        assertEquals("Math", testCourse.getName());
        testCourse.changeName("Science");
        assertEquals("Science", testCourse.getName());
    }

    @Test
    void testChangeStartTime() {
    }

    @Test
    void testChangeEndTime() {
    }

    @Test
    void testChangeLocation() {
    }

    @Test
    void testGetName() {
    }

    @Test
    void testGetStartTime() {
    }

    @Test
    void testGetEndTime() {
    }

    @Test
    void getLocation() {
    }
}
