package com.xdkj.campus.menu.ui.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.base.BaseFragment;

/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class GreatDishFragment extends BaseFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


//    private OnFragmentInteractionListener mListener;

    public GreatDishFragment() {
        // Required empty public constructor
    }

    public static GreatDishFragment newInstance() {

        Bundle args = new Bundle();
        GreatDishFragment fragment = new GreatDishFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish_detail, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
    }

    @Override
    public boolean onBackPressedSupport() {
        // 这里实际项目中推荐使用 EventBus接耦
//        ((TestOne) getParentFragment()).onBackToFirstFragment();
        Log.e("arilpan", "on back press");
        return false;
//        return true;
    }
}
