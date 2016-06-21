package im.wangchao.msharecore.platform.weichat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import im.wangchao.msharecore.SharePlatform;

/**
 * <p>Description  : WeChatCallBackActivity.</p>
 * <p/>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 16/6/14.</p>
 * <p>Time         : 下午5:57.</p>
 */
public class WeChatCallBackActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private WeChat weChat;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharePlatform weChatPlatform = SharePlatform.getCurrentPlatform(this);

        if (weChatPlatform instanceof WeChat){
            weChat = (WeChat) weChatPlatform;

            api = weChat.getApi();
            api.handleIntent(getIntent(), this);
        }

    }

    @Override protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        if (api != null){
            api.handleIntent(intent, this);
        }
    }

    @Override public void onReq(BaseReq baseReq) {

    }

    @Override public void onResp(BaseResp baseResp) {

        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (weChat != null){
                    weChat.onSuccess();
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (weChat != null) {
                    weChat.onCancel();
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                if (weChat != null){
                    weChat.onFailure();
                }
                break;
            default:
                if (weChat != null){
                    weChat.onFailure();
                }
                break;
        }

        finish();
    }
}
