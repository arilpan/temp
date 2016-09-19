package com.xdkj.campus.menu.api;

import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.xdkj.campus.menu.entity.Dish;
import com.xdkj.campus.menu.event.NetworkEvent;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by aril_pan@qq.com on 16-8-23.
 */
public class DishAPI
{
    private NetworkEvent event;

    public DishAPI()
    {
    }

    public DishAPI(NetworkEvent event)
    {
        this.event = event;
    }

    public Objects getData()
    {
        String ENDPOINT = event.url;
        ResponseBody body = null;
        JsonAdapter<List<Dish>> CONTRIBUTORS_JSON_ADAPTER = null;
        try
        {
            //network
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(ENDPOINT)
                    .build();
            Response response = client.newCall(request).execute();
            body = response.body();
            //type convert
            List<Dish> datas =
                    CONTRIBUTORS_JSON_ADAPTER.fromJson(body.source());
            //sort ,selectable
            Collections.sort(datas, new Comparator<Dish>()
            {
                @Override
                public int compare(Dish c1, Dish c2)
                {
                    return c2.getId() - c1.getId();
                }
            });

            Log.e("arilpan", "res : " + body.source());
            for (Dish dish : datas)
            {
                Log.e("arilpan", dish.getImgurl() + ": " +
                        dish.getId());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            body.close();
        }
        return null;
    }


}
