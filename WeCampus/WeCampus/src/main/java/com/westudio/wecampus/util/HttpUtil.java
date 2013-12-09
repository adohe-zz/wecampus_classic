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
    public static final String BASE_USER_PATH = "users";
    public static final String BASE_ORGANIZATION_PATH = "organizations";
    public static final String BASE_ACTIVITY_CATEGORY_PATH = "activity_categories";
    public static final String BASE_PROFILE_PATH = "profile";

    public static final String IMAGE_NOT_FOUND = "http://wecampus.net/img/image_not_found.png";

    public static enum ActivityOp {
        LIST, DETAIL, LIKE, DISLIKE, JOIN, FANS, QUIT, PARTICIPATE
    }
    
    public static enum OrganizationOp {
        SEARCH, DETAIL, FOLLOW, UNFOLLOW, FANS, ACTIVITY
    }

    public static enum ProfileOp {
        DETAIL, AVATAR, BACKGROUND, PASSWORD, FPASSWORD
    }

    public static enum UserOp {
        FOLLOW, UNFOLLOW, FANS, FOLLOWERS, FRIENDS,JACTIVITY,FACTIVITY,FORGANIZATION,SEARCH
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
     * GET REQUEST URL OF ACTIVITY BY ID AND OPERATION
     * @param id
     * @param activityOp
     * @return
     */
    public static String getActivityByIdWithOp(final int id, ActivityOp activityOp, final int page) {
        StringBuilder sb = new StringBuilder(HTTP_PROTOCOL);
        sb.append(HOST_NAME).append(SLASH).append(API_VERSION)
                .append(SLASH).append(BASE_ACTIVITY_PATH).append(SLASH);
        switch (activityOp) {
            case LIST:
                sb.append("?page=").append(page);
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
     * Search activity by keywords
     * @param keywords
     * @param page
     * @return
     */
    public static String searchActivityByKeywords(String keywords, int page) {
        StringBuilder sb = new StringBuilder(HTTP_PROTOCOL);

        try {
            sb.append(HOST_NAME).append(SLASH).append(API_VERSION).append(SLASH)
                    .append(BASE_ACTIVITY_PATH).append("?keywords=")
                    .append(URLEncoder.encode(keywords, "UTF-8")).append("&page=").append(page);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * Get the activity of one category
     * @param category
     * @param page
     * @return
     */
    public static String getActivityOfCategory(final String category, final int page) {
        StringBuilder sb = new StringBuilder(HTTP_PROTOCOL);
        sb.append(HOST_NAME).append(SLASH).append(API_VERSION)
                .append(SLASH).append(BASE_ACTIVITY_PATH).append("?category=")
                .append(category).append("&page=").append(page);

        return sb.toString();
    }

    /**
     * GET REQUEST URL OF ORGANIZATION BY ID AND OPERATION
     * @param id
     * @param op
     * @param page
     * @param keyword
     * @return
     */
    public static String getOrganizationByIdWithOp(final int id, OrganizationOp op, final int page, final String keyword) {
        StringBuilder sb = new StringBuilder(HTTP_PROTOCOL);
        sb.append(HOST_NAME).append(SLASH).append(API_VERSION)
                .append(SLASH).append(BASE_ORGANIZATION_PATH).append(SLASH);
        switch(op) {
            case SEARCH:
                try {
                    sb.append("?keywords=").append(URLEncoder.encode(keyword, "UTF-8")).append("&page=").append(page);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case DETAIL:
                sb.append(id);
                break;
            case FOLLOW:
                sb.append(id).append(SLASH).append("follow");
                break;
            case UNFOLLOW:
                sb.append(id).append(SLASH).append("unfollow");
                break;
            case FANS:
                sb.append(id).append(SLASH).append("fans");
                break;
            case ACTIVITY:
                sb.append(id).append(SLASH).append("activities");
                break;
        }

        return sb.toString();
    }

    /**
     * GET REQUEST URL OF PROFILE BY OPERATION
     * @param op
     * @return
     */
    public static String getProfileWithOp(ProfileOp op) {
        StringBuilder sb = new StringBuilder(HTTP_PROTOCOL);
        sb.append(HOST_NAME).append(SLASH).append(API_VERSION)
                .append(SLASH).append(BASE_PROFILE_PATH).append(SLASH);
        switch (op) {
            case DETAIL:
                break;
            case AVATAR:
                sb.append("avatar");
                break;
            case BACKGROUND:
                sb.append("background");
                break;
            case PASSWORD:
                sb.append("modify_password");
                break;
            case FPASSWORD:
                sb.append("forget_password");
                break;
        }

        return sb.toString();
    }

    /**
     * GET REQUEST URL OF USER BY ID WITH OPERATION
     * @param id
     * @param op
     * @param keyword
     * @return
     */
    public static String getUserByIdWithOp(final int id, UserOp op, final String keyword) {
        StringBuilder sb = new StringBuilder(HTTP_PROTOCOL);
        sb.append(HOST_NAME).append(SLASH).append(API_VERSION)
                .append(SLASH).append(BASE_USER_PATH).append(SLASH);

        switch (op) {
            case FOLLOW:
                sb.append(id).append(SLASH).append("follow");
                break;
            case UNFOLLOW:
                sb.append(id).append(SLASH).append("unfollow");
                break;
            case FANS:
                sb.append(id).append(SLASH).append("fans");
                break;
            case FOLLOWERS:
                sb.append(id).append(SLASH).append("followers");
                break;
            case FRIENDS:
                sb.append(id).append(SLASH).append("friends");
                break;
            case JACTIVITY:
                sb.append(id).append(SLASH).append("joined_activities");
                break;
            case FACTIVITY:
                sb.append(id).append(SLASH).append("favorite_activities");
                break;
            case FORGANIZATION:
                sb.append(id).append(SLASH).append("favorite_organizations");
                break;
            case SEARCH:
                try {
                    sb.append("?keywords=").append(URLEncoder.encode(keyword, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
        }

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
