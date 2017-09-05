package com.ben.api.router.utils;

import android.net.Uri;
import android.text.TextUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017-08-04
 * @desc 跳转参数处理
 */
public class ParamsUtils {
    public static Map<String,Object> parseUri(Uri uri) {
        if (uri == null) {
            return Collections.emptyMap();
        }
        Map<String, Object> dataMap = new HashMap<>();
        String query = uri.getQuery();
        if (TextUtils.isEmpty(query)) {
            return dataMap;
        }
        String[] params = query.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            dataMap.put(keyValue[0], keyValue[1]);
        }
        return dataMap;
    }
}
