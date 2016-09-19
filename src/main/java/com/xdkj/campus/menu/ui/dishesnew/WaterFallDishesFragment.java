package com.xdkj.campus.menu.ui.dishesnew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import com.xdkj.campus.menu.MainActivity;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.adapter.WaterFallPagerAdapter;
import com.xdkj.campus.menu.api.message.APPNew;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.RequestType;
import com.xdkj.campus.menu.event.NetworkEvent;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.event.TabSelectedEvent;
import com.xdkj.campus.menu.listener.OnItemClickListener;
import com.xdkj.campus.menu.ui.index.DishDetailFragment;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class WaterFallDishesFragment extends BaseFragment implements SwipeRefreshLayout
        .OnRefreshListener
{
    int SECOND = 1;
    String shop_id;

    private boolean mInAtTop = true;
    private int mScrollTotal;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecy;
    private WaterFallPagerAdapter mAdapter;

    public static WaterFallDishesFragment newInstance(String shop_org_id)
    {
        Log.e("arilpan","1233");
        Bundle args = new Bundle();
        args.putString("shop_id", shop_org_id);
        WaterFallDishesFragment fragment = new WaterFallDishesFragment();
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
            shop_id = args.getString("shop_id");
        }
    }

    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dish_switch_layout,
                container, false);
        initView(view);
        return view;
    }

    /****************************************************************/

    /****************************************************************/
    private void initView(View view)
    {
        EventBus.getDefault().register(this);
        datas = new ArrayList<>();

        mRecy = (RecyclerView) view.findViewById(R.id.switch_recv_left);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout_left);
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter = new WaterFallPagerAdapter(_mActivity);
        mRecy.setHasFixedSize(true);
        mRecy.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager
                        .VERTICAL));
        mRecy.setAdapter(mAdapter);

        //滑动事件
        mRecy.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                mScrollTotal += dy;
                if (mScrollTotal <= 0)
                {
                    mInAtTop = true;
                } else
                {
                    mInAtTop = false;
                }
            }
        });

        //点击事件
        mAdapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(int position, View view,
                                    RecyclerView.ViewHolder holder)
            {
                if (datas != null)
                {
                    String dish_id = datas.get(position).getDishes_id();
                    EventBus.getDefault().post(
                            new StartBrotherEvent(
                                    DishDetailFragment.newInstance(dish_id)));
                }
                // 通知MainActivity跳转

            }
        });

        EventBus.getDefault().post(new NetworkEvent(
                RequestType.INDEX_DISH_NEW,
                shop_id));
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onNetWork(NetworkEvent event)
    {
        Log.e("arilpan", "WaterFall 你调用咩?");
        if (RequestType.INDEX_DISH_NEW == event.reqType)
        {
            Log.e("arilpan", "WaterFall equals url="
                    + event.url + event.id);
            setData(getData(event.url + event.id));
        } else
        {
            Log.e("arilpan", "WaterFall what happend?");
        }
    }

    public List<APPNew.ValueBean.DataBean> getData(String url)
    {
        try
        {
            final JsonAdapter<APPNew>
                    COM_JSON_ADAPTER = MainActivity.MOSHI.adapter(
                    Types.newParameterizedType(APPNew.class));

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            ResponseBody body = response.body();

            APPNew datas_arry = COM_JSON_ADAPTER.fromJson(body.source());
            body.close();
            datas = datas_arry.getValue().getData();
            for (APPNew.ValueBean.DataBean data : datas)
            {
                Log.e("arilpan", data.getDiscount_type() +
                        ",code :" + data.getDishes_price());
            }
            return datas;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    List<APPNew.ValueBean.DataBean> datas;

    public void setData(final List<APPNew.ValueBean.DataBean> items)
    {
        try
        {
            _mActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    mAdapter.setDatas(items);
                    //stuff that updates ui
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh()
    {
        EventBus.getDefault().post(new NetworkEvent(
                RequestType.INDEX_DISH_NEW,
                shop_id));
        mRefreshLayout.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mRefreshLayout.setRefreshing(false);
            }
        }, 1500);
    }

    /**
     * Reselected Tab
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event)
    {
        Log.e("arilpan","invock in water fall dish fragment ");
        if (event.position != SECOND)
            return;
        if (mInAtTop)
        {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        } else
        {
            scrollToTop();
        }
    }

    private void scrollToTop()
    {
        mRecy.smoothScrollToPosition(0);
    }

    @Override
    public boolean onBackPressedSupport()
    {
        // 默认flase，继续向上传递
        return super.onBackPressedSupport();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mRecy.setAdapter(null);
    }
}
