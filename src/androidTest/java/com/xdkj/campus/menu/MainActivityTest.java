package com.xdkj.campus.menu;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by xdkj on 2016/8/1.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mMainActivity;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMainActivity = getActivity();
    }


    public MainActivityTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }
}
