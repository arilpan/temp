package com.xdkj.campus.menu.event;

import com.squareup.moshi.JsonAdapter;
import com.xdkj.campus.menu.api.APIAddr;
import com.xdkj.campus.menu.api.APIAddrFactory;
import com.xdkj.campus.menu.entity.RequestType;

import java.util.List;
import java.util.Objects;

/**
 * Created by aril_pan@qq.com on 16-8-23.
 */
public class NetworkEvent
{
    public String url;
    public int reqType;
    public String id;

    public NetworkEvent()
    {
    }

    public NetworkEvent(int reqType, String id)
    {
        this.id = id;
        this.reqType = reqType;
        this.url = APIAddrFactory.createURL(reqType);
    }

    public NetworkEvent(int reqType)
    {
        this(reqType, "");
    }

}
