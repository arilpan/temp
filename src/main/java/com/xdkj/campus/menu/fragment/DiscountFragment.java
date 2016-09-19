package com.xdkj.campus.menu.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import com.xdkj.campus.menu.MainActivity;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.adapter.RechargeDiscountAdapter;
import com.xdkj.campus.menu.api.APIAddr;
import com.xdkj.campus.menu.api.message.APPRechargeDiscount;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.RequestType;
import com.xdkj.campus.menu.event.NetworkEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by aril_pan@qq.com on 16/8.
 */
public class DiscountFragment extends BaseFragment
{
    public DiscountFragment()
    {
    }

    public static DiscountFragment newInstance(String shop_id)
    {
        Bundle args = new Bundle();
        args.putString("shop_id", shop_id);
        DiscountFragment fragment = new DiscountFragment();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_discount, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    RecyclerView listview;
    TextView mTvBtnSettings;
    SimpleAdapter simpleAdapter;
    String shop_id;
    RechargeDiscountAdapter rda;

    private void initView(View view)
    {
        EventBus.getDefault().register(this);
        ((TextView) view.findViewById(R.id.title_middle)).setText("优惠");
        view.findViewById(R.id.title_ll_left).setOnClickListener(new View
                .OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                _mActivity.onBackPressed();
            }
        });

        final TextView tab1 = (TextView) view.findViewById(R.id.tab1);
        final TextView tab2 = (TextView) view.findViewById(R.id.tab2);

        tab1.setTextColor(Color.rgb(172, 66, 66));
        tab2.setTextColor(Color.rgb(66, 66, 66));

        tab1.setText(APIAddr.shop_one_name);
        tab2.setText(APIAddr.shop_two_name);
        tab1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EventBus.getDefault().post(new NetworkEvent(
                        RequestType.DISCOUNT_CARD_LIST,
                        APIAddr.shop_one_id));
                shop_id = APIAddr.shop_one_id;
                tab1.setTextColor(Color.rgb(172, 66, 66));
                tab2.setTextColor(Color.rgb(66, 66, 66));
            }
        });
        tab2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EventBus.getDefault().post(new NetworkEvent(
                        RequestType.DISCOUNT_CARD_LIST,
                        APIAddr.shop_two_id));
                shop_id = APIAddr.shop_two_id;
                tab2.setTextColor(Color.rgb(172, 66, 66));
                tab1.setTextColor(Color.rgb(66, 66, 66));
            }
        });


        listview = (RecyclerView) view.findViewById(R.id.listview);

        Log.e("arilpan", "优惠:" + shop_id);


        listview.setHasFixedSize(true);
        listview.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager
                        .VERTICAL));
        rda = new RechargeDiscountAdapter(_mActivity);
        listview.setAdapter(rda);

        EventBus.getDefault().post(new NetworkEvent(
                RequestType.DISCOUNT_CARD_LIST,
                shop_id));
    }


    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onNetWork(NetworkEvent event)
    {
        Log.e("arilpan", "优惠: receive event");
        if (RequestType.DISCOUNT_CARD_LIST == event.reqType)
        {
            Log.e("arilpan", "discount card list equals url="
                    + event.url + event.id);
            setData(getData(event.url + event.id));
        }
        Log.e("arilpan", "what happend in the card");
    }

    public List<APPRechargeDiscount.ValueBean> getData(String url)
    {
        ResponseBody body = null;
        try
        {
            final JsonAdapter<APPRechargeDiscount>
                    COM_JSON_ADAPTER = MainActivity.MOSHI.adapter(
                    Types.newParameterizedType(APPRechargeDiscount.class));
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            body = response.body();

            APPRechargeDiscount datas_arry =
                    COM_JSON_ADAPTER.fromJson(body.source());

            datas = datas_arry.getValue();
            for (APPRechargeDiscount.ValueBean data : datas)
            {
                Log.e("arilpan", data.getShop_name() +
                        ",code :" + data.getCard_name());
            }
            return datas;
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            body.close();
        }
        return null;
    }

    List<APPRechargeDiscount.ValueBean> datas;

    public void setData(final List<APPRechargeDiscount.ValueBean> items)
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

                    rda.setDatas(items);

                    //stuff that updates ui
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onBackPressedSupport()
    {
        Log.e("arilpan", "on back press");
        return false;
    }
}
