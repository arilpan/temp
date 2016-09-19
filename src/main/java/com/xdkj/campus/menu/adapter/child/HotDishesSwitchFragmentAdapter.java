package com.xdkj.campus.menu.adapter.child;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xdkj.campus.menu.api.APIAddr;
import com.xdkj.campus.menu.ui.dishhot.HotDishesFragment;


/**
 * Created by arilpan@qq.com on 16/8.
 */
public class HotDishesSwitchFragmentAdapter extends FragmentPagerAdapter
{
//    private String[] mTab = new String[]{"8号餐馆", "北京烤鸭馆"};
private String[] mTab = new String[]{APIAddr.shop_one_name, APIAddr.shop_two_name};
    public HotDishesSwitchFragmentAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        if (position == 0)
        {
            return HotDishesFragment.newInstance(APIAddr.shop_one_id);
        } else
        {
            return HotDishesFragment.newInstance(APIAddr.shop_two_id);
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
