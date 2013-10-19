package com.westudio.wecampus.util;

import android.os.Bundle;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
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
        return "http://we.tongji.edu.cn/api/call?D=android&Sort=%60created_at%60+DESC&Expire=1&M=Activities.Get&U=201206091707564&Channel_Ids=1%2C2%2C4%2C3&V=3.0&P=1&S=95d5f4e7e01dabbc8ed272fcf69af2f2c891d611";
    }

    /**
     * Get the url for login
     * @return
     */
    public static final String getLoginUrl() {
        return "http://api.wecampus.net/v1/sessions/new";
    }

    /**
     * Get the url for request school
     * @return
     */
    public static final String getSchoolList() {
        return "http://api.wecampus.net/v1/schools";
    }

    /**
     * Encrypt the password with Hash Algorithm
     * @param password
     * @return
     */
    public static final String encryPwd(String password) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = password.getBytes();
            //使用MD5创建MessageDigest对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                //System.out.println((int)b);
                //将没个数(int)b进行双字节加密
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        }
        catch (Exception e) {
            return null;
        }
    }
}
