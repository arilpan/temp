package com.xdkj.campus.menu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.xdkj.campus.menu.api.APIAddr;
import com.xdkj.campus.menu.ui.dishrank.DishesRankFragment;


/**
 * Created by arilpan@qq.com on 16/8.
 */
public class DishesRankSwitchFragmentAdapter extends FragmentPagerAdapter
{
    //    private String[] mTab = new String[]{"8号餐馆", "北京烤鸭馆"};
    private String[] mTab = new String[]{APIAddr.shop_one_name, APIAddr.shop_two_name};

    public DishesRankSwitchFragmentAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        Log.e("arilpan","it should only have 2 fragment" +position);
        if (position == 0)
        {
            return DishesRankFragment.newInstance(APIAddr.shop_one_id);
        }
        else {
            return DishesRankFragment.newInstance(APIAddr.shop_two_id);
        }
    }

    @Override
    public int getCount()
    {
        Log.e("arilpan","it should only have "+mTab.length+" ---> fragment");
        return mTab.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mTab[position];
    }
}
