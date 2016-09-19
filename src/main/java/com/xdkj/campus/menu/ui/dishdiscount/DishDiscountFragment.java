package com.xdkj.campus.menu.ui.dishdiscount;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import com.xdkj.campus.menu.MainActivity;
import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.api.APIAddr;
import com.xdkj.campus.menu.api.message.APPDishDiscount;
import com.xdkj.campus.menu.base.BaseFragment;
import com.xdkj.campus.menu.entity.Dish;
import com.xdkj.campus.menu.entity.Order;
import com.xdkj.campus.menu.entity.RequestType;
import com.xdkj.campus.menu.event.NetworkEvent;
import com.xdkj.campus.menu.event.StartBrotherEvent;
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

/***
 * 折扣菜
 * Created by aril_pan@qq.com on 16/8.
 */
public class DishDiscountFragment extends BaseFragment

{
    String shop_id;

    public DishDiscountFragment()
    {
    }

    public static DishDiscountFragment newInstance(String shop_org_id)
    {

        Bundle args = new Bundle();
        args.putString("shop_id", shop_org_id);
        DishDiscountFragment fragment = new DishDiscountFragment();
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
        View view = inflater.inflate(R.layout.fragment_dish_discount, container, false);
        initView(view);
        return view;
    }

    RecyclerView order_recyview;
    private HomeAdapter mAdapter;
    private List<APPDishDiscount.ValueBean.DataBean> mDatas;

    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initView(View view)
    {
        EventBus.getDefault().register(this);
        order_recyview = (RecyclerView)
                view.findViewById(R.id.dish_discount_recyview);
        order_recyview.setLayoutManager(
                new LinearLayoutManager(view.getContext(),
                        LinearLayoutManager.VERTICAL, false));

        mDatas = new ArrayList<APPDishDiscount.ValueBean.DataBean>();
        order_recyview.setAdapter(mAdapter = new HomeAdapter());
//        basicParamInit();
//        initData();
//        initRecyclerView();
        EventBus.getDefault().post(new NetworkEvent(
                RequestType.INDEX_DISH_DISCOUNT,
                shop_id));
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onNetWork(NetworkEvent event)
    {
        Log.e("arilpan", "HotDishFragment 你调用咩?");
        if (RequestType.INDEX_DISH_DISCOUNT == event.reqType)
        {
            Log.e("arilpan", "HotDishFragment equals url=" + event.url + event.id);
            setData(getData(event.url + event.id));
        } else
        {
            Log.e("arilpan", "HotDishFragment what happend?");
        }
    }

    public List<APPDishDiscount.ValueBean.DataBean> getData(String url)
    {
        ResponseBody body = null;
        try
        {
            final JsonAdapter<APPDishDiscount>
                    COM_JSON_ADAPTER = MainActivity.MOSHI.adapter(
                    Types.newParameterizedType(APPDishDiscount.class));
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            body = response.body();

            APPDishDiscount datas_arry =
                    COM_JSON_ADAPTER.fromJson(body.source());

            List<APPDishDiscount.ValueBean.DataBean> datas
                    = datas_arry.getValue().getData();
            for (APPDishDiscount.ValueBean.DataBean data : datas)
            {
                Log.e("arilpan", data.getDiscount_type() +
                        ",code :" + data.getDishes_price());
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

    //List<APPDishDiscount.ValueBean.DataBean> types;
    HashMap<Integer, ArrayList<String>> discountDishesMap;
    List<Integer> discountDishesMapKey;

    public void setData(final List<APPDishDiscount.ValueBean.DataBean> items)
    {
        try
        {
            _mActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    mDatas = items;

                    discountDishesMap = new HashMap<Integer, ArrayList<String>>();
                    if (mDatas != null)
                        for (APPDishDiscount.ValueBean.DataBean oneData : mDatas)
                        {
                            int type = Integer.parseInt(oneData.getDiscount_type());
                            ArrayList<String> ids = discountDishesMap.get(type);
                            if (ids == null)
                            {
                                ids = new ArrayList<String>();
                            }
                            ids.add(oneData.getDishes_id());
                            discountDishesMap.put(type, ids);
                        }
                    discountDishesMapKey = new ArrayList<Integer>();
                    Set<Integer> mapSet = discountDishesMap.keySet();    //获取所有的key值 为set的集合
                    Iterator<Integer> itor = mapSet.iterator();//获取key的Iterator便利
                    while (itor.hasNext())
                    {
                        Integer key = itor.next();//当前key值
                        discountDishesMapKey.add(key);
                    }
                    order_recyview.setAdapter(mAdapter = new HomeAdapter());
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
        // 这里实际项目中推荐使用 EventBus接耦
//        ((TestOne) getParentFragment()).onBackToFirstFragment();
        Log.e("arilpan", "on back press");
        return false;
//        return true;
    }

    /****************************************************************************/
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {
        List<APPDishDiscount.ValueBean.DataBean> itemDishes;
        private OnItemClickListener mClickListener;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    parent.getContext()).inflate(R.layout.fragment_dish_discount_list_item, parent,
                    false));
/**************************設置高度信息****1  2  2* =>都是5个单位，为何*************************************/
            ViewGroup.LayoutParams layoutParams =
                    holder.dish_discount_item_recyview.getLayoutParams();
            layoutParams.height = 35;

            if (viewType > 0)
            {
                layoutParams.height = 35 + viewType * 225;
                int addTotal = 0;
                int add = 1;
                int numItem = viewType;
                while (add != 0)
                {
                    add = numItem / 4;
                    addTotal += add * 30;
                    numItem = add;
                }
                layoutParams.height += addTotal;
                Log.e("arilpan", " dishes size: " + viewType + ",height:" + layoutParams.height);
            }
            holder.dish_discount_item_recyview.setLayoutManager(new LinearLayoutManager
                    (_mActivity.getApplicationContext(), LinearLayoutManager.VERTICAL, true));
            holder.dish_discount_item_recyview.setLayoutParams(layoutParams);
/****************************************************************************/
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.dish_discount_item_recyview.setMinimumHeight(35);

            APPDishDiscount.ValueBean.DataBean order = mDatas.get(position);
//            holder.discount_item_type.setText(order.getTotalPrice());
            int key = discountDishesMapKey.get(position);
            switch (key)
            {
//                case 0:
//                    holder.discount_item_type.setText("零折免费菜品");
//                    break;
                case 1:
                    holder.discount_item_type.setText("一折菜品");
                    break;
                case 2:
                    holder.discount_item_type.setText("二折菜品");
                    break;
                case 3:
                    holder.discount_item_type.setText("三折菜品");
                    break;
                case 4:
                    holder.discount_item_type.setText("四折菜品");
                    break;
                case 5:
                    holder.discount_item_type.setText("五折菜品");
                    break;
                case 6:
                    holder.discount_item_type.setText("六折菜品");
                    break;
                case 7:
                    holder.discount_item_type.setText("七折菜品");
                    break;
                case 8:
                    holder.discount_item_type.setText("八折菜品");
                    break;
                case 9:
                    holder.discount_item_type.setText("九折菜品");
                    break;
                case 10:
                    holder.discount_item_type.setText("十折菜品");
                    break;
                default:
                    holder.discount_item_type.setText("未知折扣");
                    break;
            }


            holder.dish_discount_item_recyview.setAdapter(
                    new RecyclerView.Adapter()
                    {
                        @Override
                        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int
                                viewType)
                        {
                            final MyItemViewHolder itemholder = new MyItemViewHolder(
                                    LayoutInflater.from(parent.getContext()).
                                            inflate(R.layout.discount_list_item,
                                                    parent,
                                                    false));
                            itemholder.itemView.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    int position = itemholder.getAdapterPosition();

                                    if (itemDishes != null && position < itemDishes.size())
                                    {
                                        String dish_id = itemDishes.get(position).getDishes_id();
                                        EventBus.getDefault().post(
                                                new StartBrotherEvent(
                                                        DishDetailFragment.newInstance(dish_id)));
                                    }
//                                   else
//                                    {
//                                        EventBus.getDefault().post(
//                                                new StartBrotherEvent(DishDetailFragment
// .newInstance
//                                                        (APIAddr.dish_id)));
//                                    }
                                }
                            });

                            return itemholder;
                        }

                        @Override
                        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
                        {
                            MyItemViewHolder newholder = (MyItemViewHolder) holder;

                            if (position < itemDishes.size())
                            {
                                APPDishDiscount.ValueBean.DataBean dish = itemDishes.get(position);
                                newholder.dish_name.setText(dish.getDishes_name());
                                newholder.dish_price.setText("￥" + dish.getDishes_price());
                                newholder.dish_old_price.setText(dish.getRack_rate());
                                newholder.dish_desc.setText(dish.getDishes_description());
                            } else
                            {
                                Log.e("arilpan", " will throw java.lang.IndexOutOfBoundsException");
                            }

                            Log.e("arilpan", "item onBindViewHolder position: " + position + " ," +
                                    "size : " + itemDishes.size());
                        }

                        @Override
                        public int getItemViewType(int position)
                        {
                            return 100;
                        }

                        @Override
                        public int getItemCount()
                        {
                            return itemDishes.size();
                        }

                        class MyItemViewHolder extends RecyclerView.ViewHolder
                        {
                            ImageView dish_icon;
                            TextView dish_name;
                            TextView dish_price;
                            TextView dish_old_price;
                            TextView dish_desc;

                            public MyItemViewHolder(View view)
                            {
                                super(view);
                                view.setMinimumHeight(35);
                                dish_icon = (ImageView) view.findViewById(R.id
                                        .dish_image);
                                dish_name = (TextView) view.findViewById(R.id
                                        .dish_name);
                                dish_price = (TextView) view.findViewById(R.id
                                        .dish_price);
                                dish_old_price = (TextView) view.findViewById(R.id
                                        .dish_mall_price);
                                dish_desc = (TextView) view.findViewById(R.id
                                        .dish_desc);
                            }
                        }
                    }
            );
        }


        @Override
        public int getItemViewType(int position)
        {
            itemDishes = new ArrayList<APPDishDiscount.ValueBean.DataBean>();
            if (discountDishesMapKey != null)
            {
                int key = discountDishesMapKey.get(position);
                List<String> ids = discountDishesMap.get(key);
                for (String id : ids)
                {
                    for (APPDishDiscount.ValueBean.DataBean dataBean : mDatas)
                    {
                        if (dataBean.getDishes_id().equals(id))
                        {
                            //todo:speed up
                            itemDishes.add(dataBean);
                        }
                    }
                }
                return discountDishesMap.get(key).size();
            }
            return 0;
        }

        @Override
        public int getItemCount()
        {
            if (discountDishesMap != null)
            {
                return discountDishesMap.size();
            }
            return 0;
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {
            RecyclerView dish_discount_item_recyview;
            TextView discount_item_type;

            public MyViewHolder(View view)
            {
                super(view);
                view.setMinimumHeight(35);
                dish_discount_item_recyview = (RecyclerView) view.findViewById(
                        R.id
                                .dish_discount_list_recyview);
                discount_item_type =
                        (TextView) view.findViewById(R.id.discount_item_type);

            }
        }
    }
}
