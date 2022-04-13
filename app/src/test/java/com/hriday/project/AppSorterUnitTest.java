package com.hriday.project;

import org.junit.Test;

import static org.junit.Assert.*;

import android.app.usage.UsageStats;

import java.util.ArrayList;
import java.util.List;

public class AppSorterUnitTest {
    AppSorter appSorter=new AppSorter();

    @Test
    public void sorting_isCorrect() {
        Long usage1 = 76l;
        Long usage2 = 42l;
        Long usage3 = 100l;
        ArrayList<Long> usageList=new ArrayList<Long>();
        usageList.add(usage1);
        usageList.add(usage2);
        usageList.add(usage3);
        usageList=appSorter.getSortedUsageStatsList(usageList);
        assertEquals(usage2, usageList.get(0));
        assertEquals(usage1, usageList.get(1));
        assertEquals(usage3, usageList.get(2));
    }
}
