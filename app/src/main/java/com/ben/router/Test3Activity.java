package com.ben.router;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ben.annotations.annotation.Autowrite;
import com.ben.api.router.core.Router;
import com.ben.router.entity.UserInfo;


public class Test3Activity extends AppCompatActivity {

    /**
     * 可以通过name映射指定的key
     */
    @Autowrite(name = "userPwd")
    String password;
    /**
     * 必须实现Parcelable接口
     */
    @Autowrite
    UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        //使用之前必须调用 否则空指针
        Router.inject(this);

        ((TextView) findViewById(R.id.id_auto_params1)).setText("password："+password);
        ((TextView) findViewById(R.id.id_auto_params2)).setText("UserInfo："+userInfo.toString());
    }
}
