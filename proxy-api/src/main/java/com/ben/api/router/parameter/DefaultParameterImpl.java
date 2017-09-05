package com.ben.api.router.parameter;


import android.net.Uri;

import com.ben.api.router.utils.ParamsUtils;

import java.util.Map;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017-08-07
 * @desc 默认解析
 */
public class DefaultParameterImpl  implements IParameter{

    @Override
    public String parseAction(Uri uri) {
        String host = uri.getHost();
        String scheme = uri.getScheme();
        return scheme + "/" + host;
    }

    @Override
    public Map<String, Object> parseParams(Uri uri) {
        return ParamsUtils.parseUri(uri);
    }
}
