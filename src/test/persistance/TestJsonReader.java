package persistance;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Calendar;

public class TestJsonReader {
    JsonReader testReader;
    Calendar testCalendar;

    @BeforeEach
    void runBefore() {
        testReader = new JsonReader();
    }

    @Test
    void testReadInvalidFile() {
        try {
            testCalendar = testReader.read("thisfilepathdoesNOTEXIST");
            fail("Exception expected");
        } catch (Exception e) {
        }
    }

    @Test
    void testReadEmpty() {
        try {
            testCalendar = testReader.read("data\\TestEmptySaveFile.json");
            assertEquals("test", testCalendar.getName());
            assertEquals(0, testCalendar.getDaysOfWeek().get(0).getItems().size());
            assertEquals(0, testCalendar.getDaysOfWeek().get(1).getItems().size());
            assertEquals(0, testCalendar.getDaysOfWeek().get(2).getItems().size());
            assertEquals(0, testCalendar.getDaysOfWeek().get(3).getItems().size());
            assertEquals(0, testCalendar.getDaysOfWeek().get(4).getItems().size());
        } catch (Exception e) {
            fail("Exception not expected");
        }
    }

    @Test
    void testRead() {
        try {
            testCalendar = testReader.read("data\\TestRealSaveFile.json");
            assertEquals("test", testCalendar.getName());
            assertEquals(2, testCalendar.getDaysOfWeek().get(0).getItems().size());
            assertEquals("CPSC210", testCalendar.getDaysOfWeek().get(0).getItemAt(0).getName());
            assertEquals(LocalTime.of(11,0), testCalendar.getDaysOfWeek().get(0).getItemAt(0).getStartTime());
            assertEquals(LocalTime.of(12,0), testCalendar.getDaysOfWeek().get(0).getItemAt(0).getEndTime());
            assertEquals("LIFE", testCalendar.getDaysOfWeek().get(0).getItemAt(0).getLocation());
            assertEquals("SCIE113", testCalendar.getDaysOfWeek().get(0).getItemAt(1).getName());
            assertEquals(0, testCalendar.getDaysOfWeek().get(1).getItems().size());
            assertEquals(1, testCalendar.getDaysOfWeek().get(2).getItems().size());
            assertEquals("MATH101", testCalendar.getDaysOfWeek().get(2).getItemAt(0).getName());
            assertEquals(0, testCalendar.getDaysOfWeek().get(3).getItems().size());
            assertEquals(0, testCalendar.getDaysOfWeek().get(4).getItems().size());
        } catch (Exception e) {
            fail("Exception not expected");
        }
    }
}
