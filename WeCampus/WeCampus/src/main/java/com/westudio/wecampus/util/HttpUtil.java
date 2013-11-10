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
    // constants of request URLs
    public static final String URL_GET_ACTIVITY_LIST = "http://api.wecampus.net/v1/activities";

    public static final String URL_POST_REGISTER = "http://api.wecampus.net/v1/users";

    public static final String URL_POST_SESSION = "http://api.wecampus.net/v1/sessions";


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
        return "http://api.wecampus.net/v1/activities";
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
