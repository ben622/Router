package com.ben.api.router.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.ben.api.router.builder.RouterBuilder;
import com.ben.api.router.proxy.IRouterProxy;
import com.ben.api.router.utils.ClassUtils;
import com.ben.compiler.utils.Consts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017-08-02
 */
public class Router {
    private static final String TAG = Router.class.getSimpleName();
    private static Context mApplication;
    private static List<String> mClassNames;
    private static Map<String, Class<?>> mRouterMapping = new HashMap<String, Class<?>>();
    /**
     * 是否已经初始化
     */
    public static boolean isInit;

    private RouterBuilder mParamBuilder;

    public Router(RouterBuilder paramBuilder) {
        mParamBuilder = paramBuilder;
    }

    /**
     * 在Application中初始化，扫描当前包下的所有Class支持Multi Dex,暂不支持Install Run
     *
     * @param application
     */
    public static void init(Application application) {
        mApplication = application;
        if (!isInit) {
            try {
                mClassNames = ClassUtils.getFileNameByPackageName(mApplication, application.getPackageName());
                for (String className : mClassNames) {
                    Class<?> classs = Class.forName(className);
                    com.ben.annotations.annotation.Router annotation = classs.getAnnotation(com.ben.annotations.annotation.Router.class);
                    if (annotation != null) {
                        if (mRouterMapping.get(annotation.action()) == null) {
                            if (annotation.action().split("/")[0].equals(RouterBuilder.ROOT)) {
                                mRouterMapping.put(annotation.action(), classs);
                            }else{
                                mRouterMapping.put(RouterBuilder.ROOT + "/" + annotation.action(), classs);
                            }
                        }
                    }
                }
                Log.e(TAG, "init: 路由初始化完成");
                isInit = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static Router getInstance(RouterBuilder builder) {
        if (!isInit) {
            Log.e(TAG, "未初始化,请先在Application中调用[Router.init(this)]");
            return null;
        }
        return new Router(builder);
    }

    public void build() {
        final Context currentContext = mParamBuilder.getContext() == null ? mApplication : mParamBuilder.getContext();
        Class<?> target = null;
        if (mParamBuilder.getTarget() != null) {
            target = mParamBuilder.getTarget();
        } else {
            target = mRouterMapping.get(mParamBuilder.getAction());
            if (target == null) {
                Log.e(TAG, "未找到与@" + mParamBuilder.getAction() + "符合的页面，如果使用别名跳转请检查是否添加了正确的别名注解@Router");
                return;
            }
        }
        Intent intent = new Intent(currentContext, target);
        if (currentContext instanceof Activity) {
            if (mParamBuilder.getFlags() != -1) {
                intent.setFlags(mParamBuilder.getFlags());
            }
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtras(mParamBuilder.getBundle());
        if (mParamBuilder.getRequestCode() != 0) {
            ((Activity) mParamBuilder.getContext()).startActivityForResult(intent, mParamBuilder.getRequestCode(), mParamBuilder.getBundle());
        } else {
           mParamBuilder.getContext().startActivity(intent,mParamBuilder.getBundle());
        }
        if ((mParamBuilder.getActivityEnterAnim() != 0 || mParamBuilder.getActivityExitAnim() != 0) && currentContext instanceof Activity) {
            ((Activity) currentContext).overridePendingTransition(mParamBuilder.getActivityEnterAnim(), mParamBuilder.getActivityExitAnim());
        }
    }


    /**
     * 实现参数自动注入功能
     *
     * @param target
     */
    public static void inject(Object target) {
        try {
            Class<?> targetClass = target.getClass();
            Class<?> proxyClass = Class.forName(targetClass.getName() + Consts.PROXY_ROUTER_SUFFIX);
            IRouterProxy proxy = (IRouterProxy) proxyClass.newInstance();
            proxy.inject(target);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "inject: @" + target.getClass().getName() + "ClassNotFound");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
