package com.hriday.project;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;

public class getCategoryUnitTest {

    @Test
    public void appCategory_isCorrect() {
        String GOOGLE_URL = "https://play.google.com/store/apps/details?id=";
        AppCategory appCategory=new AppCategory(null,null,null,null,null,null);
        String app1=GOOGLE_URL+"com.android.chrome";
        String app2=GOOGLE_URL+"com.google.android.youtube";
        assertEquals("Communication", appCategory.getCategory(app1,true));
        assertEquals("Video Players & Editors", appCategory.getCategory(app2,true));

    }
}
