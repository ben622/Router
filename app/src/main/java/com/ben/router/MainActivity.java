package com.ben.router;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ben.api.router.builder.RouterBuilder;
import com.ben.api.router.parameter.IParameter;
import com.ben.api.router.utils.ParamsUtils;
import com.ben.router.entity.UserInfo;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 普通Class跳转
     *
     * @param view
     */
    public void usingClassNavigation(View view) {
        RouterBuilder.newInstance(this)
                .build(Test1Activity.class);
    }

    /**
     * 使用别名跳转
     *
     * @param view
     */
    public void usingAliasNavigation(View view) {
        RouterBuilder.newInstance(this)
                .build("test2Activity");
    }

    /**
     * 通过Uri跳转
     *
     * @param view
     */
    public void usingUriNavigation(View view) {
        //uri跳转类似别名跳转 host必须为指定的别名
       /* Uri uri = Uri.parse(RouterBuilder.ROOT + "://" + "test2Activity");
        RouterBuilder.newInstance(this)
                .build(uri);*/

        //支持uri参数自定义解析满足更多场所需要
        Uri uri = Uri.parse(RouterBuilder.ROOT + "://" + "openapp?action=test2Activity&params1=1");
        RouterBuilder.newInstance(this)
                .setIParameter(new IParameter() {
                    @Override
                    public String parseAction(Uri uri) {
                        //这里的action必须对应目标页面的别名  RouterBuilder.ROOT + "/"
                        return RouterBuilder.ROOT + "/"+uri.getQueryParameter("action") ;
                    }

                    @Override
                    public Map<String, Object> parseParams(Uri uri) {
                        //携带的参数 以key -value 形式返回
                        return ParamsUtils.parseUri(uri);
                    }
                })
                .build(uri);

    }

    /**
     * 携带参数跳转
     *
     * @param view
     */
    public void usingAliasNavigationWithParams(View view) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("admin");

        //如果目标页面使用了自动注入功能 key对应目标页面属性的名字 或者可以通过name指定 详情见Test3Activity
        RouterBuilder.newInstance(this)
                .putString("userPwd", "12345")
                .putParcelable("userInfo", userInfo)
                .build(Test3Activity.class);
    }

    /**
     * 初始化
     *
     * @param view
     */
    public void init(View view) {
        //建议放在Application中初始化
        com.ben.api.router.core.Router.init(this.getApplication());
    }

}
