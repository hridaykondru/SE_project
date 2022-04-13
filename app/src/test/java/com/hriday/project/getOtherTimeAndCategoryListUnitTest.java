package com.hriday.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import java.util.ArrayList;

public class getOtherTimeAndCategoryListUnitTest {
    @Test
    public void getOtherTimeAndCategoryList_isCorrect() {
        AppCategory appCategory=new AppCategory(null,null,null,null,null,null);
        int otherTime1=-10;
        int otherTime2=10;
        int otherTime3=20;
        App app1=new App(null,"Other",otherTime1,"","");
        App app2=new App(null,"Other",otherTime2,"","");
        App app3=new App(null,"Other",otherTime3,"","");
        app1.category="Not Found";
        app2.category="Not Found";
        app3.category="Not Found";
        ArrayList<App> appCategoryList1=new ArrayList<App>();
        ArrayList<App> appCategoryList2=new ArrayList<App>();
        ArrayList<App> applist1=new ArrayList<App>();
        ArrayList<App> applist2=new ArrayList<App>();
        applist1.add(app1);
        applist1.add(app2);
        applist1.add(app3);
        applist2.add(app2);
        applist2.add(app3);
        assertThrows(IllegalArgumentException.class,()->appCategory.getOtherTimeAndCategoryList(appCategoryList1,applist1));
        assertEquals(otherTime2+otherTime3, appCategory.getOtherTimeAndCategoryList(appCategoryList2,applist2));

    }
}
