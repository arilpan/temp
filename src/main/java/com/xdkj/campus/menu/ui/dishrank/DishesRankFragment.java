package com.xdkj.campus.menu.ui.dishrank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import com.xdkj.campus.menu.MainActivity;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.adapter.DishRankAdapter;
import com.xdkj.campus.menu.adapter.WaterFallPagerAdapter;
import com.xdkj.campus.menu.api.message.APPDishDiscount;
import com.xdkj.campus.menu.api.message.APPRank;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.Dish;
import com.xdkj.campus.menu.entity.RequestType;
import com.xdkj.campus.menu.event.NetworkEvent;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.event.TabSelectedEvent;
import com.xdkj.campus.menu.listener.OnItemClickListener;
import com.xdkj.campus.menu.ui.index.DishDetailFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class DishesRankFragment extends BaseFragment implements SwipeRefreshLayout
        .OnRefreshListener
{
    int SECOND = 1;
    String shop_id;

    private boolean mInAtTop = true;
    private int mScrollTotal;
    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecy;
    private DishRankAdapter mAdapter;

    public static DishesRankFragment newInstance(String shop_org_id)
    {
        Bundle args = new Bundle();
        args.putString("shop_id", shop_org_id);
        DishesRankFragment fragment = new DishesRankFragment();
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
        View view = inflater.inflate(R.layout.fragment_dish_switch_layout, container, false);
        initView(view);
        return view;
    }

    /****************************************************************/

    /****************************************************************/
    private void initView(View view)
    {
        EventBus.getDefault().register(this);
        mRecy = (RecyclerView) view.findViewById(R.id.switch_recv_left);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout_left);
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter = new DishRankAdapter(_mActivity);
        mRecy.setHasFixedSize(true);
        mRecy.setLayoutManager(new StaggeredGridLayoutManager(1,
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
            public void onItemClick(int position, View view, RecyclerView.ViewHolder holder)
            {
                if (datas != null)
                {
                    String dish_id = datas.get(position).getDishes_id();
                    EventBus.getDefault().post(
                            new StartBrotherEvent(
                                    DishDetailFragment.newInstance(dish_id)));
                }
                // 通知MainActivity跳转
//                EventBus.getDefault().post(
//                        new StartBrotherEvent(DishDetailFragment.newInstance(1)));
            }
        });

        // Init Datas
        List<APPRank.ValueBean.DataBean> items = new ArrayList<>();
        mAdapter.setDatas(items);

        EventBus.getDefault().post(new NetworkEvent(
                RequestType.INDEX_DISH_RANK,
                shop_id));
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onNetWork(NetworkEvent event)
    {
        if (RequestType.INDEX_DISH_RANK == event.reqType)
        {
            Log.e("arilpan", "dish rank equals url="
                    + event.url + event.id);
            setData(getData(event.url + event.id));
        }
    }

    public List<APPRank.ValueBean.DataBean> getData(String url)
    {
        ResponseBody body = null;
        try
        {
            final JsonAdapter<APPRank>
                    COM_JSON_ADAPTER = MainActivity.MOSHI.adapter(
                    Types.newParameterizedType(APPRank.class));
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
              body = response.body();

            APPRank datas_arry =
                    COM_JSON_ADAPTER.fromJson(body.source());
            datas = datas_arry.getValue().getData();
            for (APPRank.ValueBean.DataBean data : datas)
            {
                Log.e("arilpan", data.getDiscount_type() +
                        ",code :" + data.getDishes_price());
            }
            return datas;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            body.close();
        }
        return null;
    }

    List<APPRank.ValueBean.DataBean> datas;

    public void setData(final List<APPRank.ValueBean.DataBean> items)
    {
        try
        {

            _mActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    if (items == null)
                    {
                        Toast.makeText(getContext(), "请刷新列表", Toast.LENGTH_SHORT).show();
                        return;
                    }
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
                RequestType.INDEX_DISH_RANK,
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
