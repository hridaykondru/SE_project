package com.hriday.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import java.util.ArrayList;

public class usageStatsAppCategoryIntegrationTesting {
    @Test
    public void integration_isCorrect() {
        usageStats usageStats=new usageStats();
        AppCategory appCategory=new AppCategory(null,null,null,null,null,null);
        App category1=new App(null,"Communication",45,"","");
        App category2=new App(null,"Video Players & Editors",55,"","");
        ArrayList<App> expectedAppCategoryList=new ArrayList<App>();
        expectedAppCategoryList.add(category1);
        expectedAppCategoryList.add(category2);
        assertEquals(expectedAppCategoryList.get(0).appName,
                appCategory.getAppCategoryListWithOtherCategory(0,appCategory.mockDoInBackground(usageStats.mockGetAppsList())).get(0).appName);
        assertEquals(expectedAppCategoryList.get(0).usagePercentage,
                appCategory.getAppCategoryListWithOtherCategory(0,appCategory.mockDoInBackground(usageStats.mockGetAppsList())).get(0).usagePercentage);
        assertEquals(expectedAppCategoryList.get(1).appName,
                appCategory.getAppCategoryListWithOtherCategory(0,appCategory.mockDoInBackground(usageStats.mockGetAppsList())).get(1).appName);
        assertEquals(expectedAppCategoryList.get(1).usagePercentage,
                appCategory.getAppCategoryListWithOtherCategory(0,appCategory.mockDoInBackground(usageStats.mockGetAppsList())).get(1).usagePercentage);

    }
}
