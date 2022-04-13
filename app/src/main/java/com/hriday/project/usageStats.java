package com.hriday.project;

import static android.content.Context.USAGE_STATS_SERVICE;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


// Helper class that returns an AppsAdapter object which contains all the App objects
// along with their usage duration
public class usageStats {

    private Activity activity;
    private AppsAdapter adapter;
    private ArrayList<App> appsList;
    private AppSorter appSorter=new AppSorter();

    public void setContext(Activity mainActivity){
        activity=mainActivity;
    }

    // Load the UsageStats for every app in the appsList
    public void loadStatistics() {
        UsageStatsManager usm = (UsageStatsManager) activity.getSystemService(USAGE_STATS_SERVICE);
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,  System.currentTimeMillis()- 1000*3600*24 ,  System.currentTimeMillis());
        appList = appList.stream().filter(app -> app.getTotalTimeInForeground() > 0).collect(Collectors.toList());

        // Group the usageStats by application and sort them by total time in foreground
        if (appList.size() > 0) {
            Map<String, UsageStats> mySortedMap = new TreeMap<>();
            for (UsageStats usageStats : appList) {
                mySortedMap.put(usageStats.getPackageName(), usageStats);
            }
            showAppsUsage(mySortedMap);
        }
    }
    //Check whether the usage time returned is valid
    public boolean usageTimeIsValid(long usageTime){
        if(usageTime<0){
            return(false);
        }
        else{
            return(true);
        }
    }
    // Function to create the final list of Apps along with their usage stats,
    // and also create the AppsAdapter object with all the Apps stored
    public void showAppsUsage(Map<String, UsageStats> mySortedMap) {
        appsList = new ArrayList<>();

        List<UsageStats> usageStatsList = new ArrayList<>(mySortedMap.values());

       usageStatsList=appSorter.getSortedUsageStatsList(usageStatsList);
        // get total time of apps usage to calculate the usagePercentage for each app
        boolean valid=true;
        for(UsageStats usageStats:usageStatsList){
            valid=valid && usageTimeIsValid(usageStats.getTotalTimeInForeground());
        }
        if(!valid){
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }
        long totalTime = usageStatsList.stream().map(UsageStats::getTotalTimeInForeground).mapToLong(Long::longValue).sum();

        //fill the appsList

        for (UsageStats usageStats : usageStatsList) {
            try {
                String packageName = usageStats.getPackageName();
                Drawable icon = activity.getResources().getDrawable(R.drawable.no_image);
                String[] packageNames = packageName.split("\\.");
                String appName = packageNames[packageNames.length-1].trim();
                


                if(isAppInfoAvailable(usageStats)){
                    ApplicationInfo ai = activity.getApplicationContext().getPackageManager().getApplicationInfo(packageName, 0);
                    icon = activity.getApplicationContext().getPackageManager().getApplicationIcon(ai);
                    appName = activity.getApplicationContext().getPackageManager().getApplicationLabel(ai).toString();
                }

                String usageDuration = getDurationBreakdown(usageStats.getTotalTimeInForeground());
                int usagePercentage = (int) (usageStats.getTotalTimeInForeground() * 100 / totalTime);

                App usageStatDTO = new App(icon, appName, usagePercentage, usageDuration,packageName);
                appsList.add(usageStatDTO);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }


        // reverse the list to get most usage first
        Collections.reverse(appsList);
        // build the adapter
        adapter = new AppsAdapter(activity.getApplicationContext(), appsList);

    }
    // Check if app information is available on device
    private boolean isAppInfoAvailable(UsageStats usageStats) {
        try {
            activity.getApplicationContext().getPackageManager().getApplicationInfo(usageStats.getPackageName(), 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    // Return a broken down form of time in milli seconds in the format hours, minutes, seconds as a string
    public String getDurationBreakdown(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        return (hours + " h " +  minutes + " m " + seconds + " s");
    }
    // Public function which can be used to get the AppsAdapter object with apps stored
    public AppsAdapter getAdapter(){
        loadStatistics();
        return(adapter);
    }
    // Public function which can be used to get the ArrayList of App objects
    public ArrayList<App> getAppsList(){
        loadStatistics();
        return (appsList);
    }
    public ArrayList<App> mockGetAppsList(){
        appsList = new ArrayList<App>();
        App app1=new App(null,"Chrome",45,"","com.android.chrome");
        App app2=new App(null,"Youtube",55,"","com.google.android.youtube");

        appsList.add(app1);
        appsList.add(app2);
        return appsList;
    }
}

