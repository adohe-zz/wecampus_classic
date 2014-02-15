package com.westudio.wecampus.util;

/**
 * Created by jgzhu on 14-2-14.
 * 定义一些环境相关的常量
 */
public class Config {
    public static final boolean IS_TEST = false;
    public static String WB_APP_ID;

    public static String WX_APP_ID() {
        if (IS_TEST){
            return "wx849ec04575e33109";
        } else {
            return "wxe8c36962ae11e4eb";
        }
    }

    public static String WB_APP_KEY() {
        if (IS_TEST){
            return "3126350995";
        } else {
            return "2393509052";
        }
    }

}
