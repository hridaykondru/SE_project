package com.hriday.project;
import android.graphics.drawable.Drawable;

//App class used for storing various attributes of the app which will be shown in the UI.
public class App {
    public  Drawable appIcon;
    public  String appName;
    public  int usagePercentage;
    public  String usageDuration;
    public String category;
    public String packageName;


    public App(Drawable appIcon, String appName, int usagePercentage, String usageDuration,String packageName) {
        this.appIcon = appIcon;
        this.appName = appName;
        this.usagePercentage = usagePercentage;
        this.usageDuration = usageDuration;
        this.packageName=packageName;
    }
}
