package im.wangchao.msharecore.platform.weichat;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import im.wangchao.msharecore.ImageParams;
import im.wangchao.msharecore.ShareCallback;
import im.wangchao.msharecore.ShareParams;
import im.wangchao.msharecore.SharePlatform;
import im.wangchao.msharecore.ShareRequest;
import im.wangchao.msharecore.ShareType;

/**
 * <p>Description  : WeChat.</p>
 * <p/>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 16/6/14.</p>
 * <p>Time         : 下午3:27.</p>
 */
public class WeChat extends SharePlatform {
    public static final String NAME = WeChat.class.getSimpleName();

    private IWXAPI api;
    private ShareCallback callback;

    public IWXAPI getApi(){
        return api;
    }

    @Override protected void initialize(Context context) {
        api = WXAPIFactory.createWXAPI(context, entry.key(), true);
        api.registerApp(entry.key());
    }

    @Override protected void share(ShareRequest request) {
        ShareParams params = request.shareParams();
        callback = request.shareCallback();

        /**
         * 如果没有安装微信，那么回调分享失败
         */
        if (!isInstalled()){
            if (callback != null){
                callback.onFailure(ShareCallback.ERROR_NOT_INSTALLED);
            }
            return;
        }

        WXMediaMessage message = new WXMediaMessage();
        switch (params.type()){
            case ShareType.TEXT:
                message.mediaObject = new WXTextObject(params.text());
                message.title = params.text();
                message.description = params.text();

                sendReq(buildTransaction("text"), message);

                break;
            case ShareType.IMAGE:
                ImageParams image = params.image();

                WXImageObject imageObject;
                Bitmap originalBitmap = image.bitmap();

                if (!TextUtils.isEmpty(image.path())){
                    imageObject = new WXImageObject();
                    imageObject.setImagePath(image.path());
                } else {
                    imageObject = new WXImageObject(originalBitmap);
                }

                Bitmap thumbBmp = Bitmap.createScaledBitmap(originalBitmap, 150, 150, true);
                originalBitmap.recycle();

                message.mediaObject = imageObject;
                message.thumbData = ImageParams.bmpToByteArray(thumbBmp, true);

                sendReq(buildTransaction("image"), message);
                break;
            case ShareType.WEB_PAGE:

                WXWebpageObject webpageObject = new WXWebpageObject();
                webpageObject.webpageUrl = params.webUrl();

                Bitmap bmp = params.image().bitmap();
                Bitmap thumbBitmap = Bitmap.createScaledBitmap(bmp, 50, 50, true);
                bmp.recycle();

                message.mediaObject = webpageObject;
                message.title = params.title();
                message.description = params.description();
                message.thumbData = ImageParams.bmpToByteArray(thumbBitmap, true);

                sendReq(buildTransaction("webpage"), message);
                break;
        }
    }

    @Override public boolean isInstalled() {
        return api != null && api.isWXAppInstalled();
    }

    protected int getScene(){
        return SendMessageToWX.Req.WXSceneSession;
    }

    private void sendReq(String transaction, WXMediaMessage message){
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = transaction;
        req.message = message;
        req.scene = getScene();

        api.sendReq(req);
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

    /*package*/final void onCancel(){
        if (callback != null){
            callback.onCancel();
        }
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
