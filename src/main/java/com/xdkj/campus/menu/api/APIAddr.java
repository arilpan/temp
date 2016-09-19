package com.xdkj.campus.menu.api;

/**
 * Created by aril_pan@qq.com on 16-8-25.
 */
public class APIAddr {
    public static int select_mall = 0;
    public final static int ONE = 1;
    public final static int TWO = 2;
    public static String shop_one_id = "";
    public static String shop_two_id = "";

    public static String user_id = "";
    public static String shop_one_name = "";
    public static String shop_two_name = "";
    public static String shop_one_addr = "";
    public static String shop_two_addr = "";

    public static String shop_one_work_time = "";
    public static String shop_two_work_time = "";

    public static String shop_one_icon = "";
    public static String shop_two_icon = "";

    public static String shop_one_phone = "";
    public static String shop_two_phone = "";

    public static String dish_id = "23dbde58-5ab5-41b5-915c-66048e63a5df2324";

    public static int ORDER_CANCLE = 2;
    public static int ORDER_COMPLETE = 1;
    public static int ORDER_UNCOMPLETE = 0;

    public static String IP_PORT = "http://192.168.0.5:8080";
//    public static String IP_PORT = "http://172.16.0.35:8080";

    public static String BASE_PROJECT_URL = IP_PORT + "/GrogshopSystem/app";
    public static String BASE_URL = IP_PORT + "/GrogshopSystem/app/appShop";
    public static String BASE_DISH_URL = IP_PORT + "/GrogshopSystem/app/appDishes";
    public static String BASE_NEWS_URL = IP_PORT + "/GrogshopSystem/app/News";
    public static String BASE_IMG_URL = IP_PORT;

    /**
     * 首页获取店铺信息\菜品信息
     */
    public static String index_url = BASE_URL + "/shop_dishes_index" +
            ".do?iDisplayStart=0&iDisplayLength=10";
    public static String index_shop_env_url = BASE_URL
            + "/shop_Picture_info.do?shop_id=";
    /**
     * 选菜:菜品分类接口
     * 左侧:标签类型列表和菜品类型列表
     */
    public static String select_dish_left_url = BASE_URL +
            "/shop_dishesClassification_info" +
            ".do?org_id=";
    /**
     * 选菜:菜品分类接口
     * 右侧:tagID来区分那个是对应父菜单
     */
    public static String select_dish_right_url = BASE_URL +
            "/shop_ClassificatedDishes_info.do?" +
            "org_id=";

    /**
     * 订单:创建\删除
     */
    public static String order_create = BASE_PROJECT_URL +
            "/appOrder/addAndroidOrder.do?";
    public static String order_delete = BASE_PROJECT_URL +
            "/appOrder/deleteOrders.do?order_id=";
    /**
     * 排行
     */
    public static String dish_rank_url = BASE_URL +
            "/shop_dishes_info.do?iDisplayStart=###" +
            "&iDisplayLength=$$$&org_id=";
    /**
     * 热门--查看更多==>与排行一个接口
     */
    public static String dish_hot_more_url = BASE_URL +
            "/shop_dishes_info.do?iDisplayStart=###" +
            "&iDisplayLength=$$$&org_id=";
    /**
     * 折扣菜品 ?? todo:这儿需要分页？
     */
    public static String dish_discount_url = BASE_URL +
            "/shop_discountDishes_info" +
            ".do?iDisplayStart=###&iDisplayLength=$$$&org_id=";
    /**
     * 新品尝鲜
     */
    public static String dish_new_to_taste_url = BASE_URL +
            "/shop_dishes_info" +
            ".do?label_type=新品尝鲜" +
            "&iDisplayStart=###&iDisplayLength=$$$&org_id=";

    /**
     * 选择包间
     */
    public static String dish_select_room_url = BASE_URL +
            "/shop_room_info.do?org_id=";
    /**
     * 菜品详情
     */
    public static String dish_detail_url = BASE_DISH_URL +
            "/dishes_Detail.do?dishes_id=";
    /**
     * 查看充值优惠:新版=>html5查看页面详情
     */
    public static String recharge_discount_new_url = BASE_URL +
            "/shop_RechargeDiscount_info.do?shop_id=";

    /**
     * 餐厅详情--unuseless
     */
    public static String shop_detail_url = BASE_URL +
            "/shop_info.do?org_id=";

    /**
     * 新闻列表详情
     */
    public static String news_detail_url = BASE_NEWS_URL +
            "/appShowNews.do?news_id=";
    /**
     * 新闻列表
     */
    public static String news_list_url = BASE_NEWS_URL +
            "/appListNews.do?iDisplayStart=###&iDisplayLength=$$$";


    /**
     * 订单列表
     */
    public static String order_list_url = BASE_PROJECT_URL +
            "/appOrder/list_Order.do?user_id=USERID&order_status=";
    public static String order_cancel_url = BASE_PROJECT_URL +
            "/appOrder/cancelOrders.do?order_id=";

    public static String dish_comment_url = BASE_PROJECT_URL +
            "/appEvaluation/saveAddEvaluations.do?user_id=USERID" +
            "&dishes_id=DISHID&content=CONTENT";
    public static String dish_list_comment_url = BASE_PROJECT_URL +
            "/appEvaluation/list_Evaluations.do?user_id=USERID" +
            "&dishes_id=DISHID";

    /**
     * set org id
     *
     * @param url
     * @param orgin
     * @return
     */
    public static String setOrgId(String url, String orgin) {
        return url + "orgin";
    }


}