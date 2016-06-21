package im.wangchao.msharecore.platform.qq;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import im.wangchao.msharecore.SharePlatform;

/**
 * <p>Description  : QQCallbackActivity.</p>
 * <p/>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 16/6/21.</p>
 * <p>Time         : 下午1:51.</p>
 */
public class QQCallbackActivity extends Activity implements IUiListener{
    private QQ qq;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharePlatform platform = SharePlatform.getCurrentPlatform(this);
        if (platform instanceof QQ){
            qq = (QQ) platform;

            Bundle params = getIntent().getExtras();

            try {
                PackageInfo pkg = getPackageManager().getPackageInfo(getApplication().getPackageName(), 0);
                String appName = pkg.applicationInfo.loadLabel(getPackageManager()).toString();
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, appName);
            } catch (Exception e) {
                //Silent
            }

            Tencent tencent = Tencent.createInstance(qq.appId, this);
            tencent.shareToQQ(this, params, this);
        } else {
            finish();
        }
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, this);
    }

    @Override public void onComplete(Object o) {
        if (qq != null){
            qq.onSuccess();
        }
        finish();
    }

    @Override public void onError(UiError uiError) {
        if (qq != null) {
            qq.onFailure();
        }
        finish();
    }

    @Override public void onCancel() {
        if (qq != null) {
            qq.onCancel();
        }
        finish();
    }
}
