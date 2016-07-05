package com.android.shengqing.agift.util;

/**
 * Created by sheng on 2016/6/30.
 */
public class URLConstants {
    public static final String URL_GUIDE_CLASSIFY="http://api.liwushuo.com/v2/channels/preset?gender=1&generation=2";
    public static final String BANNER_URL = "http://api.liwushuo.com/v2/banners";
    public static final String RECYCLER_URL="http://api.liwushuo.com/v2/secondary_banners?gender=1&generation=1";
    public static final String HANDPICK_URL="http://api.liwushuo.com/v2/channels/101/items?ad=2&gender=1&generation=2&limit=20&offset=0";
    //海淘 id ：129
    public static final String HAITAO_URL="http://api.liwushuo.com/v2/channels/129/items?ad=2&gender=1&generation=2&limit=20&offset=0";

    //    这是海淘以及其他界面的url的头
    public static final String URL_END = "/items?gender=1&limit=20&offset=0&generation=2";
    //  这是海淘以及其他界面的url的尾部
    public static final String URL_START = "http://api.liwushuo.com/v2/channels/";

}
