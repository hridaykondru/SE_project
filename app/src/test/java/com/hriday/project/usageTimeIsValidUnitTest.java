package com.hriday.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class usageTimeIsValidUnitTest {
    usageStats usageStats=new usageStats();

    @Test
    public void usageTimeIsValid_isCorrect() {
        Long usage1 = -9548000l;
        Long usage2 = 9548000l;

        assertEquals(usageStats.usageTimeIsValid(usage1), false);
        assertEquals(usageStats.usageTimeIsValid(usage2), true);
    }
}
