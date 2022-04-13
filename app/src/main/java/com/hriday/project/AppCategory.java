package com.hriday.project;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//AsyncTask class which fetches the app category from Play Store given an ArrayList of App objects,
// and updates the App Category fragment once the AsyncTask has completed.
public class AppCategory extends AsyncTask<Void, Void, ArrayList<App>>{

    public final static String GOOGLE_URL = "https://play.google.com/store/apps/details?id=";
    public static final String ERROR = "error";
    private Activity activity;
    private ArrayList<App> applist;
    private ListView listView;
    private ImageView imageView;
    private TextView textView;
    private ProgressBar progressBar;

    public AppCategory(Activity activity, ArrayList<App> applist, ListView listView, ImageView imageView, TextView textView,ProgressBar progressBar){
        this.activity=activity;
        this.applist=applist;
        this.listView=listView;
        this.imageView=imageView;
        this.textView=textView;
        this.progressBar=progressBar;
    }


    // Gets the app category in the background using the function getCategory
    @Override
    protected ArrayList<App> doInBackground(Void... error) {
        String category;
        Drawable noImage=null;
        for(App app : applist) {
            app.appIcon=noImage;
            app.usageDuration="";
            String query_url = GOOGLE_URL + app.packageName;
            category = getCategory(query_url);
            app.appName=category;
            app.category=category;
            app.packageName="";
        }
        return applist;
    }
    // Function which returns the app category
    // given the query url which is a concatenation of Google Play Store link and the app package name
    private String getCategory(String query_url) {
        boolean network = isNetworkAvailable();
        if (!network) {
            //manage connectivity lost
            return "No network connection";
        } else {
            try {
                Connection.Response response =         Jsoup.connect(query_url).ignoreHttpErrors(true).execute();

                if (response.statusCode() == 200) {
                    Document doc = response.parse();
                    Element link = doc.select("a[itemprop=genre]").first();
                    if (link != null)
                         return link.text();
                    else
                         return ERROR;
                }
                return "Not Found";

            } catch (Exception e) {
               // log(e);
                return "Server Error";
            }
        }
    }
    public String getCategory(String query_url,boolean test) {

            try {
                Connection.Response response =         Jsoup.connect(query_url).ignoreHttpErrors(true).execute();

                if (response.statusCode() == 200) {
                    Document doc = response.parse();
                    Element link = doc.select("a[itemprop=genre]").first();
                    if (link != null)
                        return link.text();
                    else
                        return ERROR;
                }
                return "Not Found";

            } catch (Exception e) {
                return "Server Error";
            }

    }

    // Function that updates the UI once the background execution has completed
    @Override
    protected void onPostExecute(ArrayList<App> applist){
        ArrayList<App> appCategoryList=new ArrayList<App>();
        int otherTime=0;
        App app0=applist.get(0);
        progressBar.setVisibility(View.GONE);
        if(app0.category=="Server Error"){
            imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.no_server));
            imageView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);

        }
        else if(app0.category=="No network connection"){
            imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.no_internet_connection));
            imageView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
        else {
            imageView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            otherTime=getOtherTimeAndCategoryList(appCategoryList,applist);
            appCategoryList=getAppCategoryListWithOtherCategory(otherTime,appCategoryList);
            Collections.sort(appCategoryList,(a1,a2)->Long.compare(a1.usagePercentage, a2.usagePercentage));
            Collections.reverse(appCategoryList);
            AppsAdapter adapter;
            adapter = new AppsAdapter(activity.getApplicationContext(), appCategoryList);

            listView.setAdapter(adapter);
        }

    }
    // Helper function which return the time for the Other category of apps
    // and updates the appCategoryList given the applist
    public int getOtherTimeAndCategoryList(ArrayList<App> appCategoryList,ArrayList<App> applist){
        int otherTime=0;
        for(App app:applist){
            if(app.usagePercentage<0){
                throw new IllegalArgumentException("Usage Percentage must be greater than zero!");
            }
            if(app.category=="Not Found"){
                otherTime+=app.usagePercentage;
            }
            else{
                appCategoryList.add(app);
            }
        }
        return(otherTime);
    }
    // Helper function that adds the Other category in the appCategoryList
    public ArrayList<App> getAppCategoryListWithOtherCategory(int otherTime, ArrayList<App> appCategoryList){
        if(otherTime<0){
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }
        App otherCategory=new App(null,"Other",otherTime,"","");
        Map<String,App> appCategoriesMap=new HashMap<>();
        for(App app:appCategoryList){
            if(!appCategoriesMap.containsKey(app.appName)){
                appCategoriesMap.put(app.appName,app);
            }
            else{
                appCategoriesMap.get(app.appName).usagePercentage+=app.usagePercentage;
            }
        }
        appCategoryList.clear();
        for(Map.Entry<String,App>entry:appCategoriesMap.entrySet()){
            appCategoryList.add(entry.getValue());
        }
        if(otherTime!=0){
            appCategoryList.add(otherCategory);
        }

        return(appCategoryList);
    }
    // Function to check network connectivity
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected()));
    }

    public ArrayList<App> mockDoInBackground(ArrayList<App> applist) {
        String category;
        Drawable noImage=null;
        for(App app : applist) {
            app.appIcon=noImage;
            app.usageDuration="";
            String query_url = GOOGLE_URL + app.packageName;
            category = getCategory(query_url,true);
            app.appName=category;
            app.category=category;
            app.packageName="";
        }
        return applist;
    }
}