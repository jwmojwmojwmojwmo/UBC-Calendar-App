package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCalendar {
    Calendar testCalendar1;
    Calendar testCalendar2;

    @BeforeEach
    void runBefore() {
        testCalendar1 = new Calendar("test");
        testCalendar2 = new Calendar("jwmo");
    }

    @Test
    void testConstructor() {
    }

    @Test
    void testAddCourses() {
    }

    @Test
    void testAddItem() {
    }

    @Test
    void testRemoveItem() {
    }

    @Test
    void testChangeName() {
        assertEquals("test", testCalendar1.getName());
        testCalendar1.changeName("test2");
        assertEquals("test2", testCalendar1.getName());
    }

    @Test
    void testGetItemAt() {
    }

    @Test
    void testGetName() {
        assertEquals("test", testCalendar1.getName());
        assertEquals("jwmo", testCalendar2.getName());
    }

    @Test
    void testGetCourses() {
    }

    @Test
    void testGetExtras() {
    }
}
