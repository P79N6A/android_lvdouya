package com.hhkj.dyedu.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.hhkj.dyedu.BuildConfig;
import com.hhkj.dyedu.http.HttpRequest;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.nohttp.tools.MultiValueMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by zangyi_shuai_ge on 2018/3/15
 * 基于 Logger 的日志类
 */

public class LoggerUtils {

    public static final String TAG = "ZANGYI3";



    public static void i(String message) {
        if (!BuildConfig.LOG_DEBUG) {
            return;
        }
        Logger.i(message);
    }


    /**
     * 打印http访问日志
     */
    public static void printHttpMap(String TAG, HttpRequest request) {
        if (!BuildConfig.LOG_DEBUG) {
            return;
        }

        MultiValueMap<String, Object> multiValueMap = request.getParamKeyValues();
        Map<String, String> map = new HashMap<>();
        Set<String> set = multiValueMap.keySet();
        for (String str : set) {
            String value;
            try {
                value = (String) multiValueMap.getValue(str);
            } catch (Exception e) {
                value = "";
            }
            map.put(str, value);
        }
        map.put("url", request.url());
        json("访问参数----" + TAG, new Gson().toJson(map));
    }


    /**
     * 打印Json
     */
    public static void json(String tag, String json) {

        if (!BuildConfig.LOG_DEBUG) {
            return;
        }

        Log.i(TAG, tag);
        if (json == null) {
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(2);
                Logger.i(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(2);
                Logger.i(message);
                return;
            }
            //都不是打印原始数据
            Logger.i(json);
        } catch (JSONException e) {
            Logger.i(json);
        }
    }
}
