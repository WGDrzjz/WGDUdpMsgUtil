package com.jndv.jnudplibrary;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author: wangguodong
 * Date: 2022/8/8
 * QQ: 1772889689@qq.com
 * WX: gdihh8180
 * Description: 文件存储工具类
 */
public class JNPreferenceUtils {

    private static SharedPreferences getSharedPreferences() {
        return JNUdpManager.getInstance().mContext.getSharedPreferences("JNUdpLibrary", Context.MODE_PRIVATE);
    }

    public static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    public static void saveInt(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(String key) {
        return getSharedPreferences().getInt(key, 0);
    }

}
