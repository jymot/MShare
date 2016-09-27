package im.wangchao.msharecore.platform.qq;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.connect.share.QQShare;

import im.wangchao.msharecore.ShareCallback;
import im.wangchao.msharecore.ShareParams;
import im.wangchao.msharecore.SharePlatform;
import im.wangchao.msharecore.ShareRequest;
import im.wangchao.msharecore.ShareType;

/**
 * <p>Description  : QQ.
 *                   图片分享只支持本地图片
 *                   网页分享只支持URL图片</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 16/6/21.</p>
 * <p>Time         : 上午11:28.</p>
 */
public class QQ extends SharePlatform {
    public static final String NAME = QQ.class.getSimpleName();
    private static final String SHARE_ACTION = "im.wangchao.msharecore.platform.qq.QQCallbackActivity";

    private ShareCallback callback;
    /*package*/ String appId;

    @Override protected void initialize(Context context) {
        this.appId = entry.key();
    }

    @Override protected void share(ShareRequest request) {
        callback = request.shareCallback();

        ShareParams params = request.shareParams();

        Bundle bundle = new Bundle();
        if (params.type() == ShareType.IMAGE){
            bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, params.image().path());
        } else {
            bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            //分享的标题。注：SHARE_TO_QQ_TITLE、SHARE_TO_QQ_IMAGE_URL、SHARE_TO_QQ_SUMMARY不能全为空，最少必须有一个是有值的。
            bundle.putString(QQShare.SHARE_TO_QQ_TITLE, params.title());
            //分享的消息摘要，最长50个字
            bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, params.description());
            bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, params.image().url());
            //这条分享消息被好友点击后的跳转URL。
            bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, params.webUrl());
        }

        Intent intent = new Intent(SHARE_ACTION);
        intent.putExtras(bundle);
        weakContext.get().startActivity(intent);
    }

    @Override public boolean isInstalled() {
        //QQ内部自己处理是否安装客户端的情况
        return false;
    }

    /*package*/final public void onCancel() {
        if (callback != null){
            callback.onCancel();
        }
    }

    /*package*/final void onSuccess(){
        if (callback != null){
            callback.onSuccess();
        }
    }

    /*package*/final void onFailure(){
        if (callback != null){
            callback.onFailure(ShareCallback.ERROR_DEFAULT);
        }
    }
}
