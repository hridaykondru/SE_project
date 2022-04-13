package com.hriday.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import java.util.ArrayList;

public class getAppCategoryListWithOtherCategoryUnitTest {
    @Test
    public void appCategoryListWithOtherCategory_isCorrect() {
        AppCategory appCategory=new AppCategory(null,null,null,null,null,null);
        int otherCategory1=-10;
        int otherCategory2=0;
        int otherCategory3=10;
        ArrayList<App> appCategoryList1=new ArrayList<App>();
        ArrayList<App> appCategoryList2=new ArrayList<App>();
        ArrayList<App> appCategoryList3=new ArrayList<App>();
        assertThrows(IllegalArgumentException.class,()->appCategory.getAppCategoryListWithOtherCategory(otherCategory1,appCategoryList1));
        assertEquals(appCategoryList2, appCategory.getAppCategoryListWithOtherCategory(otherCategory2,appCategoryList2));
        appCategoryList3.add(new App(null,"Other",10,"",""));
        assertEquals(appCategoryList3, appCategory.getAppCategoryListWithOtherCategory(otherCategory3,appCategoryList3));

    }
}
