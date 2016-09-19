package com.xdkj.campus.menu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.xdkj.campus.menu.api.APIAddr;
import com.xdkj.campus.menu.ui.dishesnew.WaterFallDishesFragment;


/**
 * Created by YoKeyword on 16/6/5.
 */
public class DishesSwitchFragmentAdapter extends FragmentPagerAdapter
{
    //    private String[] mTab = new String[]{"8号餐馆", "北京烤鸭馆"};
    private String[] mTab = new String[]{APIAddr.shop_one_name,
            APIAddr.shop_two_name};

    public DishesSwitchFragmentAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        Log.e("arilpan","1233-1");
        if (position == 0)
        {
            return WaterFallDishesFragment.newInstance(APIAddr.shop_one_id);
        } else
        {
            return WaterFallDishesFragment.newInstance(APIAddr.shop_two_id);
        }
    }

    @Override
    public int getCount()
    {
        return mTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mTab[position];
    }
}
