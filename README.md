# Router Android中实现页面跳转以及参数自动注入功能

#### 一、功能介绍
1. **支持Class跳转**
2. **支持使用别名跳转**
3. **支持Uri跳转，可自定义动作解析**
4. **支持参数自动注入功能**
5. 支持多种方式配置转场动画

### 二、添加依赖
``` gradle
allprojects {
    repositories {
        jcenter()
        //添加maven
        maven {
            url 'https://dl.bintray.com/zhangchuan622/maven/'
        }
    }

}

dependencies {
    annotationProcessor 'com.ben:proxy-compiler:1.0.1'
    compile 'com.ben:proxy-annotations:1.0.1'
    compile 'com.ben:proxy-api:1.0.1'
    ...
}
// 旧版本gradle插件(< 2.2)，可以使用apt插件'
```
### 三、基本使用
1、初始化
``` java
  //注意跳转前需要先初始化，建议放在Application中初始化
   com.ben.api.router.core.Router.init(this.getApplication());
   
```
2、使用Class跳转
``` java
    RouterBuilder.newInstance(this)
                .build(Test1Activity.class);
```
3、通过指定目标页面别名跳转
``` java
    RouterBuilder.newInstance(this)
                .build("test2Activity");
```
4、通过注解为页面添加别名
``` java
@Router(action = "test2Activity")
public class Test2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
    }
}
```
5、使用Uri跳转
``` java
    Uri uri = Uri.parse(RouterBuilder.ROOT + "://" + "test2Activity");
    RouterBuilder.newInstance(this)
                .build(uri);
```
6、使用Uri跳转，并自定义解析
``` java
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
```
7、携带参数跳转
``` java
  UserInfo userInfo = new UserInfo();
  userInfo.setUserName("admin");

  //如果目标页面使用了自动注入功能 key对应目标页面属性的名字 或者可以通过name指定 详情见Test3Activity
  RouterBuilder.newInstance(this)
          .putString("userPwd", "12345")
          .putParcelable("userInfo", userInfo)
          .build(Test3Activity.class);
```
8、实现参数自动注入功能
``` java

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
```
