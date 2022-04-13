package com.hriday.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

//The App Category fragment which calls and executes the AsyncTask AppCategory
// which in-turn updates the elements of this fragment.
public class appCategoryStatsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_category_stats, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadStatistics();

    }

    // Function called when onViewCreated is executed to start the execution of the AsyncTask AppCategory
    // which in-turn updates the UI
    public void loadStatistics() {
        ListView listView = getView().findViewById(R.id.apps_list);
        ProgressBar progressBar=getView().findViewById(R.id.progressBar2);
        ImageView imageView = getView().findViewById(R.id.imageView);
        TextView textView=getView().findViewById(R.id.usage_tv);
        imageView.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        usageStats UsageStats;
        UsageStats=new usageStats();
        UsageStats.setContext(getActivity());
        ArrayList<App> applist=UsageStats.getAppsList();
        AppCategory appCategory=new AppCategory(getActivity(),applist,listView,imageView,textView,progressBar);
        appCategory.execute();
    }
}