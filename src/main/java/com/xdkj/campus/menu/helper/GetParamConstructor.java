package com.xdkj.campus.menu.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by aril_pan@qq.com on 16-9-2.
 */
public class GetParamConstructor
{
    private Map<String, String> params;

    public GetParamConstructor()
    {
        params = new HashMap<>();
    }

    public void add(String key, String value)
    {
        params.put(key, value);
    }

    public String getString()
    {
        String result = "";
        Iterator iter = params.entrySet().iterator();
        while (iter.hasNext())
        {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            result = result + key + "=" + value + "&";
        }
        result = result + "from=android";
        return result;
    }

}
