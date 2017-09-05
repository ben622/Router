package com.ben.api.router.parameter;

import android.net.Uri;

import java.util.Map;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017-08-07
 * @desc 参数解析
 */
public interface IParameter {
    String parseAction(Uri uri);
    Map<String, Object> parseParams(Uri uri);
}
