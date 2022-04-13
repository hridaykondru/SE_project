package com.hriday.project;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private AppBarLayout topBar;
    private Button enableBtn;
    private TextView permissionDescriptionTv;
    private ImageButton reloadBtn;
    private UsageStatsFragment UsageStatsFragment;
    private appCategoryStatsFragment appCategoryStatsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);

        // setting up the adapter
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // add the fragments
        UsageStatsFragment=new UsageStatsFragment();
        appCategoryStatsFragment =new appCategoryStatsFragment();
        viewPagerAdapter.add(UsageStatsFragment, "Usage Stats");
        viewPagerAdapter.add(appCategoryStatsFragment, "App Category Stats");
        // Set the adapter
        viewPager.setAdapter(viewPagerAdapter);

        // The Page (fragment) titles will be displayed in the
        // tabLayout hence we need to  set the page viewer
        // we use the setupWithViewPager().
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        enableBtn=findViewById(R.id.enable_btn);
        topBar=findViewById((R.id.appBarLayout2));
        permissionDescriptionTv=findViewById(R.id.permission_description_tv);
        reloadBtn=findViewById(R.id.reloadButton);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getGrantStatus()) {
            showHideWithPermission();
            reloadBtn.setOnClickListener(view->setReloadBtn());


        } else {
            showHideNoPermission();
            enableBtn.setOnClickListener(view -> startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)));
        }
    }

// Function for the functionality of reload button
    private void setReloadBtn(){
        if(UsageStatsFragment.getUserVisibleHint()){
            UsageStatsFragment.loadStatistics();
        }
        else if(appCategoryStatsFragment.getUserVisibleHint()){
            appCategoryStatsFragment.loadStatistics();
        }
    }
// Function to check if all the service APP_OPS_SERVICE is granted
    private boolean getGrantStatus() {
        AppOpsManager appOps = (AppOpsManager) getApplicationContext()
                .getSystemService(Context.APP_OPS_SERVICE);

        int mode = appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getApplicationContext().getPackageName());

        if (mode == AppOpsManager.MODE_DEFAULT) {
            return (getApplicationContext().checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            return (mode == MODE_ALLOWED);
        }
    }
// Function to show/hide UI elements when service permission is not granted
    public void showHideNoPermission() {
        enableBtn.setVisibility(View.VISIBLE);
        permissionDescriptionTv.setVisibility(View.VISIBLE);
        topBar.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);

    }
// Function to show/hide UI elements when service permission is granted
    public void showHideWithPermission() {
        enableBtn.setVisibility(View.GONE);
        permissionDescriptionTv.setVisibility(View.GONE);
        topBar.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
    }
}