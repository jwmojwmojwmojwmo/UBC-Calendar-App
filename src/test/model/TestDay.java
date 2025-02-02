package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class TestDay {
    Day monday;
    ArrayList<CalendarItem> testList = new ArrayList<CalendarItem>();

    @BeforeEach
    void runBefore() {
        monday = new Day(DayOfWeek.MONDAY, testList);
    }

    @Test
    void testConstructor() {
    }

    @Test 
    void testGetBusyTimes() {
    }

    @Test
    void testGetDay() {
    }

    @Test
    void testGetItems() {
    }
}