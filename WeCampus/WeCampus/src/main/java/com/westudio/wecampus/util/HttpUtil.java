package com.westudio.wecampus.util;

import android.os.Bundle;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nankonami on 13-9-14.
 * A common http util to generate the request url and so on.
 */
public class HttpUtil {

    /**
     * Build the url query string according to the param bundle
     * @param params
     * @return
     */
    private static String encodeUrl(Bundle params)
    {
        if(params == null)
            return "";

        StringBuilder sb = new StringBuilder();
        boolean bFirst = true;

        Set<String> setKeys = new HashSet<String>(params.keySet());

        for(String strKey : setKeys)
        {
            if(bFirst)
                bFirst = false;
            else
                sb.append("&");

            try
            {
                sb.append(URLEncoder.encode(strKey, "UTF-8"))
                        .append("=")
                        .append(URLEncoder.encode(params.getString(strKey), "UTF-8"));
            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /**
     * Get the url for request activity
     * @param bundle
     * @return
     */
    public static final String getActivityList(Bundle bundle) {
        return "http://we.tongji.edu.cn/api/call?D=android&Sort=%60created_at%60+DESC&Expire=1&M=Activities.Get&U=201206091707564&Channel_Ids=1%2C2%2C4%2C3&V=3.0&P=1&S=2b844276233bb0206d455b48f8e9419ab1266083";
    }
}
