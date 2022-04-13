package com.hriday.project;

import static android.content.Context.USAGE_STATS_SERVICE;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

// Usage Stats Fragment which fetches the AppsAdapter object
// which contains all the App objects with their usage statistics and displays them
public class UsageStatsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usage_stats, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadStatistics();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
// Function called when onViewCreated is executed to fetch the AppsAdapter object
// from usageStats and display App usage statistics
    public void loadStatistics() {
        usageStats UsageStats;
        UsageStats=new usageStats();
        UsageStats.setContext(getActivity());
        AppsAdapter adapter=UsageStats.getAdapter();
        ListView listView = getView().findViewById(R.id.apps_list);
        listView.setAdapter(adapter);
    }

}