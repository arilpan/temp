package com.xdkj.campus.menu.api;

import com.xdkj.campus.menu.entity.RequestType;

/**
 * Created by aril_pan@qq.com on 16-8-26.
 */
public class APIAddrFactory
{
    public static String createURL(int reqType)
    {
        switch (reqType)
        {
            case RequestType.INDEX_ALL:
                return APIAddr.index_url;
            case RequestType.INDEX_DISH_SELECT_LEFT:
                return APIAddr.select_dish_left_url;
            case RequestType.INDEX_DISH_SELECT_RIGHT:
                return APIAddr.select_dish_right_url;
            case RequestType.INDEX_DISH_RANK:
                return APIAddr.dish_rank_url;
            case RequestType.INDEX_DISH_HOT:
                return APIAddr.dish_hot_more_url;
            case RequestType.INDEX_DISH_DISCOUNT:
                return APIAddr.dish_discount_url;
            case RequestType.INDEX_DISH_NEW:
                return APIAddr.dish_new_to_taste_url;
            case RequestType.INDEX_DISH_SELECT_ROOM:
                return APIAddr.dish_select_room_url;
            case RequestType.INDEX_DISH_DETAIL:
                return APIAddr.dish_detail_url;
            case RequestType.DISCOUNT_LIST:
                return APIAddr.recharge_discount_new_url;
            case RequestType.NEWS_LIST:
                return APIAddr.news_list_url;
            case RequestType.NEWS_DETAIL:
                return APIAddr.news_detail_url;
            case RequestType.ORDER_LIST:
                return APIAddr.order_list_url;
            case RequestType.ORDER_CANCEL:
                return APIAddr.order_cancel_url;
            case RequestType.ORDER_COMMENT:
                return APIAddr.dish_comment_url;
            case RequestType.INDEX_SHOP_ENV:
                return APIAddr.index_shop_env_url;
            case RequestType.DISCOUNT_CARD_LIST:
                return APIAddr.recharge_discount_new_url;
            case RequestType.ORDER_DELETE:
                return APIAddr.order_delete;
            case RequestType.ORDER_UPLOAD:
                return APIAddr.order_create;
            case RequestType.ORDER_LIST_COMMENT:
                return APIAddr.dish_list_comment_url;
//            case RequestType.INDEX_ALL:
//                return  APIAddr.shop_detail_url;
//                break;
        }
        return null;
    }
}
