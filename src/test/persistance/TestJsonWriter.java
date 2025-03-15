package persistance;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Calendar;
import model.CalendarItem;

public class TestJsonWriter {
    JsonWriter testWriter;
    Calendar testCalendar;
    JsonReader testReader;

    @BeforeEach
    void runBefore() {
        testCalendar = new Calendar("test");
        testWriter = new JsonWriter();
        testReader = new JsonReader();
    }

    @Test
    void testWriteInvalidFile() {
        try {
            testWriter.write(testCalendar, "hi=z/data/thisfilepathdoesNOTEXIST");
            fail("Exception expected");
        } catch (Exception e) {
        }
    }

    @Test
    void testWriteEmpty() {
        testWriter = new JsonWriter();
        try {
            testWriter.write(testCalendar,"data/TestEmptySaveFile.json");
            testCalendar = testReader.read("data/TestEmptySaveFile.json");
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
    void testWrite() {
        testCalendar.getDaysOfWeek().get(0).addItem(new CalendarItem("CPSC210", LocalTime.of(11,0), LocalTime.of(12,0), "LIFE"));
        testCalendar.getDaysOfWeek().get(0).addItem(new CalendarItem("SCIE113", LocalTime.of(13,0), LocalTime.of(14,0), "LSK"));
        testCalendar.getDaysOfWeek().get(2).addItem(new CalendarItem("MATH101", LocalTime.of(14,0), LocalTime.of(16,0), "WESB"));
        try {
            testWriter.write(testCalendar, "data/TestRealSaveFile.json");
            testCalendar = testReader.read("data/TestRealSaveFile.json");
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
