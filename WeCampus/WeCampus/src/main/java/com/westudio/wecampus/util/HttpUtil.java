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

    public static final String URL_POST_USERS = "http://api.wecampus.net/v1/users";

    public static final String URL_POST_SESSION = "http://api.wecampus.net/v1/sessions";

    public static final String URL_GET_ADVERTISEMENTS = "http://api.wecampus.net/v1/advertisements";

    public static final String URL_GET_ACTIVITIES = "http://api.wecampus.net/v1/activities";

    public static final String URL_GET_SCHOOLS = "http://api.wecampus.net/v1/schools";

    public static final String URL_POST_PROFILE_AVATAR = "http://api.wecampus.net/v1/profile/avatar";

    public static final String URL_PROFILE = "http://api.wecampus.net/v1/profile";

    public static final String URL_ORGANIZATIONS = "http://api.wecampus.net/v1/organizations";

    public static final String HTTP_PROTOCOL = "http://";

    public static final String HOST_NAME = "api.wecampus.net";

    public static final String API_VERSION = "v1";

    public static final String SLASH = "/";

    public static final String BASE_ACTIVITY_PATH = "activities";
    public static final String BASE_USER_PATH = "";
    public static final String BASE_ORGANIZATION_PATH = "organizations";
    public static final String BASE_ACTIVITY_CATEGORY_PATH = "activity_categories";

    public static enum ActivityOp {
        LIST, DETAIL, LIKE, DISLIKE, JOIN, FANS, QUIT, PARTICIPATE
    }

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
     * GET REQUEST URL OF ACTIVITY ID AND OPERATION
     * @param id
     * @param activityOp
     * @return
     */
    public static String getActivityByIdWithOp(final int id, ActivityOp activityOp) {
        StringBuilder sb = new StringBuilder(HTTP_PROTOCOL);
        sb.append(HOST_NAME).append(SLASH).append(API_VERSION)
                .append(SLASH).append(BASE_ACTIVITY_PATH).append(SLASH);
        switch (activityOp) {
            case LIST:
                break;
            case DETAIL:
                sb.append(id);
                break;
            case LIKE:
                sb.append(id).append("/like");
                break;
            case DISLIKE:
                sb.append(id).append("/dislike");
                break;
            case JOIN:
                sb.append(id).append("/join");
                break;
            case QUIT:
                sb.append(id).append("/quit");
                break;
            case FANS:
                sb.append(id).append("/fans");
                break;
            case PARTICIPATE:
                sb.append(id).append("/participants");
                break;
        }

        return sb.toString();
    }

    /**
     * GET REQUEST ACTIVITY CATEGORY URL
     * @return
     */
    public static String getActivityCategory() {
        StringBuilder sb = new StringBuilder(HTTP_PROTOCOL);
        sb.append(HOST_NAME).append(SLASH).append(API_VERSION)
                .append(SLASH).append(BASE_ACTIVITY_CATEGORY_PATH);
        return sb.toString();
    }

    /**
     * Get the abs path of the image
     * @param url
     * @return
     */
    public static String getImageUrl(String url) {
        StringBuilder sb = new StringBuilder(HTTP_PROTOCOL);
        sb.append("wecampus.net").append(SLASH).append(url);
        return sb.toString();
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
