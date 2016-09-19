package com.xdkj.campus.menu.ui.place;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.api.APIAddr;
import com.xdkj.campus.menu.fragment.ShopFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class SelectPlaceFragment extends SupportFragment
{
    private static final String ARG_NUMBER = "arg_number";

    TextView mall_1;
    TextView mall_2;

    private String mNumber;



    public static SelectPlaceFragment newInstance(String number)
    {
        SelectPlaceFragment fragment = new SelectPlaceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NUMBER, number);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null)
        {
            mNumber = args.getString(ARG_NUMBER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.select_mall, container, false);
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mall_1 = (TextView) view.findViewById(R.id.mall_1);
        mall_2 = (TextView) view.findViewById(R.id.mall_2);
        String title = "循环Fragment" + mNumber;
        mall_1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                start(CycleFragment.newInstance(mNumber + 1));
                start(ShopFragment.newInstance(APIAddr.shop_one_id));
            }
        });
        mall_2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                start(ShopFragment.newInstance(APIAddr.shop_two_id));
//                startWithPop(CycleFragment.newInstance(mNumber + 1));
            }
        });
    }
}
