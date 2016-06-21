package im.wangchao.msharecore.platform.weibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

import im.wangchao.msharecore.ImageParams;
import im.wangchao.msharecore.ShareCallback;
import im.wangchao.msharecore.ShareParams;
import im.wangchao.msharecore.SharePlatform;
import im.wangchao.msharecore.ShareRequest;

/**
 * <p>Description  : Weibo.</p>
 * <p/>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 16/6/20.</p>
 * <p>Time         : 下午2:56.</p>
 */
public class Weibo extends SharePlatform implements WeiboAuthListener {
    public static final String NAME = Weibo.class.getSimpleName();
    private static final String SHARE_ACTION = "com.sina.weibo.sdk.action.ACTION_SDK_REQ_ACTIVITY";
    private static final String NECESSARY_FIELD_REDIRECT_URL = "redirectUrl";

    private IWeiboShareAPI api;
    private ShareCallback callback;
    private ShareParams params;

    public IWeiboShareAPI getApi(){
        return api;
    }

    protected void doShare(Activity activity){

        AuthInfo authInfo = new AuthInfo(activity, entry.key(), entry.get(NECESSARY_FIELD_REDIRECT_URL), "all");
        // TODO: 16/6/20 save token? 如果需要保存token 那么需要在 onComplete(Bundle bundle) 回调方法中保存，
        // TODO: 16/6/20 如果保存token那么下次分享免登陆，但是如果需要切换分享账号，那么需要登出微博
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(activity);
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }

        SendMultiMessageToWeiboRequest weiboRequest = new SendMultiMessageToWeiboRequest();
        weiboRequest.transaction = String.valueOf(System.currentTimeMillis());
        weiboRequest.multiMessage = getMessage(params);

        api.sendRequest(activity, weiboRequest, authInfo, token, this);
    }

    @Override protected void initialize(Context context) {
        api = WeiboShareSDK.createWeiboAPI(context, entry.key());
        api.registerApp();
    }

    @Override protected void share(ShareRequest request) {
        params = request.shareParams();
        callback = request.shareCallback();

        //不需要判断是否安装微博app，如果没有安装则不调用app进行，使用内部浏览器进行分享

        Context context = weakContext.get();
        context.startActivity(new Intent(SHARE_ACTION));

    }

    private WeiboMultiMessage getMessage(ShareParams params){
        WeiboMultiMessage msg = new WeiboMultiMessage();

        if (!TextUtils.isEmpty(params.text())){
            TextObject text = new TextObject();
            text.text = params.text();
            msg.textObject = text;
        }

        if (!TextUtils.isEmpty(params.webUrl())){
            WebpageObject web = new WebpageObject();
            web.description = params.description();
            web.actionUrl = params.webUrl();
            web.title = params.title();
            msg.mediaObject = web;
        }

        ImageParams imageParams = params.image();
        Bitmap bitmap = imageParams.bitmap();
        if (bitmap != null){
            ImageObject image = new ImageObject();
            image.setImageObject(bitmap);
            bitmap.recycle();
            msg.imageObject = image;

        }

        return msg;
    }

    @Override public boolean isInstalled() {
        return api != null && api.isWeiboAppInstalled();
    }

    @Override public void onComplete(Bundle bundle) {
//        AccessTokenKeeper.writeAccessToken(weakContext.get(), Oauth2AccessToken.parseAccessToken(bundle));
    }

    @Override public void onWeiboException(WeiboException e) {

    }

    @Override final public void onCancel() {
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
