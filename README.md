# MShare
简单社会化分享

###Gradle:
```gradle
  compile 'im.wangchao:mshare:0.1.2'
```
###混淆
ProGuard rules now ship inside of the library and are included automatically.
```java
    -keep public class * extends im.wangchao.msharecore.SharePlatform
    -dontwarn im.wangchao.**
```
###如何使用
#####1.配置工程
在res目录中创建xml目录，并在xml目录中创建share.xml文件，具体内容如下：
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest>
    <!--
        必要配置
        name : 平台名称
        key : 分享用到的key, 将下面的key替换为您对应平台的appKey或appId
        className : 平台类名全路径

        可自定义配置，如：
        redirectUrl: ''
        名字随意起，在 PlatformEntry 中使用get(自定义配置名称)获取value
    -->
    <platform
        name="WeChat"
        key="xxxxxx"
        className="im.wangchao.msharecore.platform.weichat.WeChat" />

    <platform
        name="WeChatMoments"
        key="xxxxxx"
        className="im.wangchao.msharecore.platform.weichat.WeChatMoments" />

    <platform
        name="Weibo"
        key="xxxxxx"
        redirectUrl="http://com.lakala.com"
        className="im.wangchao.msharecore.platform.weibo.Weibo" />

    <platform
        name="QQ"
        key="xxxxxx"
        className="im.wangchao.msharecore.platform.qq.QQ" />
</manifest>
```

在您项目包名目录下创建wxapi.WXEntryActivity类，并继承WeChatCallBackActivity,然后在AndroidManifest.xml文件中加入如下代码
```xml
<!-- 微信 -->
<activity android:name=".wxapi.WXEntryActivity"
          android:exported="true"/>

<!-- QQ -->
<activity
    android:name="com.tencent.tauth.AuthActivity"
    android:launchMode="singleTask"
    android:noHistory="true">
    <intent-filter>
        <action android:name="android.intent.action.VIEW"/>
        <category android:name="android.intent.category.DEFAULT"/>
        <category android:name="android.intent.category.BROWSABLE"/>
        <data android:scheme="tencent您的AppId"/>
        <!-- example: tencent12312451-->
    </intent-filter>
</activity>
```
#####2.调用分享
初始化，在使用分享功能时，优先调用初始化方法。
```java
MShareCore.instance().initialize(this);
```

微博：
```java
ImageParams image = ImageParams.createBuilder()
                .bitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .build();
ShareParams params = ShareParams.createBuilder()
                .image(image)
                .webUrl("http://www.baidu.com")
                .title("Baidu")
                .text("asdasd http://www.lakala.com sadasdasdasd")
                .build();
ShareRequest.createBuilder().platformName(Weibo.NAME).params(params)
                .callback(new ShareCallback() {
                    @Override public void onSuccess() {

                    }

                    @Override public void onFailure(int errorCode) {

                    }

                    @Override public void onCancel() {

                    }
                }).build().execute();
```

QQ：
```java
ImageParams image = ImageParams.createBuilder()
                .url("你的图片地址")
                .build();
ShareParams params = ShareParams.createBuilder()
                .image(image)
                .webUrl("http://www.baidu.com")
                .title("Baidu")
                .description("description")
                .build();
ShareRequest.createBuilder().platformName(QQ.NAME).params(params)
                .callback(new ShareCallback() {
                    @Override public void onSuccess() {

                    }

                    @Override public void onFailure(int errorCode) {

                    }

                    @Override public void onCancel() {

                    }
                }).build().execute();
```

微信：
```java
ImageParams image = ImageParams.createBuilder()
                        .bitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .build();
ShareParams params = ShareParams.createBuilder()
                        .image(image)
                        .webUrl("http://www.baidu.com")
                        .title("Baidu")
                        .type(ShareType.WEB_PAGE)
                        .build();
ShareRequest request = ShareRequest.createBuilder().platformName(WeChatMoments.NAME).params(params)
                        .callback(new ShareCallback() {
                            @Override public void onSuccess() {

                            }

                            @Override public void onFailure(int errorCode) {

                            }

                            @Override public void onCancel() {

                            }
                        }).build();
                request.execute();
```

#####3.自定义平台
自定义平台只需要继承SharePlatform，并在share.xml中配置该平台即可，具体可以参考默认平台配置。


###联系我
- Email:  magician.of.technique@aliyun.com

### License

    Copyright 2016 Mot. All rights reserved.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

