package im.wangchao.msharedemo;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import im.wangchao.msharecore.ImageParams;
import im.wangchao.msharecore.MShareCore;
import im.wangchao.msharecore.ShareCallback;
import im.wangchao.msharecore.ShareParams;
import im.wangchao.msharecore.ShareRequest;
import im.wangchao.msharecore.ShareType;
import im.wangchao.msharecore.platform.qq.QQ;
import im.wangchao.msharecore.platform.weibo.Weibo;
import im.wangchao.msharecore.platform.weichat.WeChatMoments;

public class MainActivity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MShareCore.instance().initialize(this);

        Button weibo = (Button) findViewById(R.id.weibo);
        assert weibo != null;
        weibo.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                weibo();
            }
        });

        Button wechat = (Button) findViewById(R.id.wechat);
        assert wechat != null;
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
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
                                Log.e("wcwcwc", "WcChat success");
                            }

                            @Override public void onFailure(int errorCode) {
                                Log.e("wcwcwc", "WcChat failure");
                            }

                            @Override public void onCancel() {
                                Log.e("wcwcwc", "WcChat cancel");
                            }
                        }).build();
                request.execute();
            }
        });

        final Button qq = (Button) findViewById(R.id.qq);
        assert qq != null;
        qq.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                qq();
            }
        });
    }

    private void qq(){
        ImageParams image = ImageParams.createBuilder()
                .url("image url")
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
                        Log.e("wcwcwc", "QQ success");
                    }

                    @Override public void onFailure(int errorCode) {
                        Log.e("wcwcwc", "QQ failure");
                    }

                    @Override public void onCancel() {
                        Log.e("wcwcwc", "QQ cancel");
                    }
                }).build().execute();
    }

    private void weibo(){
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
                        Log.e("wcwcwc", "Weibo success");
                    }

                    @Override public void onFailure(int errorCode) {
                        Log.e("wcwcwc", "Weibo failure");
                    }

                    @Override public void onCancel() {
                        Log.e("wcwcwc", "Weibo cancel");
                    }
                }).build().execute();
    }
}
