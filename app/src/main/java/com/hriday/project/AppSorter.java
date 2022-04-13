package com.hriday.project;

import android.app.usage.UsageStats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//A helper class which sorts a given list of UsageStats objects using their TotalTimeInForeground attribute
public class AppSorter {

    public List<UsageStats> getSortedUsageStatsList(List<UsageStats> usageStatsList){
        Collections.sort(usageStatsList, (z1, z2) -> Long.compare(z1.getTotalTimeInForeground(), z2.getTotalTimeInForeground()));
        return usageStatsList;
    }
    public ArrayList<Long> getSortedUsageStatsList(ArrayList<Long> usageStatsList){
        Collections.sort(usageStatsList, (z1, z2) -> Long.compare(z1, z2));
        return usageStatsList;
    }
}
