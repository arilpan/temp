package com.xdkj.campus.menu.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xdkj.campus.menu.R;
import com.xdkj.campus.menu.api.APIAddr;
import com.xdkj.campus.menu.entity.Dish;
import com.xdkj.campus.menu.event.StartBrotherEvent;
import com.xdkj.campus.menu.ui.index.DishDetailFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by aril_pan@qq.com on 16-9-1.
 */
public class SelectDishRightAdapter
        extends BaseAdapter
{
    private List<Dish> dishs;//ListView显示的数据

    private int resource;//显示列表项的Layout

    private LayoutInflater inflater;//界面生成器

    private Context context;

    class ViewHolder
    {
    }

    SelectDishRightAdapter(List dishs, int resource, Context context)
    {
        this.dishs = dishs;
        this.resource = resource;
        this.context = context.getApplicationContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return dishs.size();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = inflater.inflate(resource, null);
        }
//
//        ImageView dish_img = (ImageView) convertView.findViewById(R.id.item_image);
//        TextView dish_name = (TextView) convertView.findViewById(R.id.item_left);
//        TextView dish_desc = (TextView) convertView.findViewById(R.id.item_middle);
//        TextView dish_order_btn = (TextView) convertView.findViewById(R.id.item_right);
//
//        Dish dish = dishs.get(position);
//        dish_name.setText(dish.getName());
//        dish_desc.setText(dish.getPrice());
//        dish_order_btn.setText("预约");
//
//        dish_img.setImageResource(R.drawable.zanwu);
//
//        dish_img.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Log.e("arilpan", "选择菜品的item Adapter dish_img +"
//                        + view.getId());
//                SupportFragment sf = findChildFragment(DishDetailFragment.class);
//                if (sf != null)
//                {
//                    sf.pop();
//
//                    Log.e("arilpan", "findChildFragment不空 已经pop");
//                }
//                sf = findFragment(DishDetailFragment.class);
//                if (sf != null)
//                {
//                    sf.pop();
//                    Log.e("arilpan", "findFragment 已经pop");
//                }
//                EventBus.getDefault().post(
//                        new StartBrotherEvent(DishDetailFragment.
//                                newInstance(APIAddr.dish_id)));
////                    start(DishDetailFragment.newInstance(1));
//                //  Toast.makeText(context, good.getGoodProvider(),
//                //  Toast.LENGTH_LONG).show();
//            }
//
//        });
        return convertView;
    }
}