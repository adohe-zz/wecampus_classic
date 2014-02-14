package com.westudio.wecampus.util;

/**
 * Created by jgzhu on 14-2-14.
 * 定义一些环境相关的常量
 */
public class Config {
    public static final boolean IS_TEST = false;
    public static String WX_APP_ID;
    public static String WB_APP_ID;

    {
        if (IS_TEST){
            WX_APP_ID = "wx849ec04575e33109";
            WB_APP_ID = "3126350995";
        } else {
            WX_APP_ID = "wxe8c36962ae11e4eb";
            WB_APP_ID = "2393509052";
        }
    }
}
