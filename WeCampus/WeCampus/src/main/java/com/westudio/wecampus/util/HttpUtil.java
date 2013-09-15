package com.westudio.wecampus.util;

/**
 * Created by nankonami on 13-9-14.
 * A common http util to generate the request url and so on.
 */
public class HttpUtil {

    /**
     * Get the url for request activity
     * @param page
     * @return
     */
    public static final String getActivityList(final int page) {
        return "http://we.tongji.edu.cn/api/call?D=android&Sort=%60created_at%60+DESC&Expire=1&M=Activities.Get&U=201206091707564&Channel_Ids=1%2C2%2C4%2C3&V=3.0&P=1&S=2b844276233bb0206d455b48f8e9419ab1266083";
    }
}
