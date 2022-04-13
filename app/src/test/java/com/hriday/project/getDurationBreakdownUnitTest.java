package com.hriday.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import java.util.ArrayList;

public class getDurationBreakdownUnitTest {
    usageStats usageStats=new usageStats();

    @Test
    public void durationBreakdown_isCorrect() {
        Long millis1 = 0l;
        Long millis2 = -10l;
        Long millis3 = 9548000l;

        assertEquals(usageStats.getDurationBreakdown(millis1), "0 h 0 m 0 s");
        assertThrows(IllegalArgumentException.class,()->usageStats.getDurationBreakdown(millis2));
        assertEquals(usageStats.getDurationBreakdown(millis3), "2 h 39 m 8 s");
    }
}
