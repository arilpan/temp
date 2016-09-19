package com.xdkj.campus.menu.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by aril_pan@qq.com on 16-8-15.
 */
public class SystemInfo
{
    /**
     * whether see the first load page
     *
     * @param context
     * @return
     */
    public static boolean isFirstLoad(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences("app", 0);
        return sp.getBoolean("first_load", true);
    }

    /**
     * cancel first load state
     *
     * @param context
     */
    public static void setFirstLoad(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences("app", 0);
        sp.edit().putBoolean("first_load", false).commit();
    }
}
