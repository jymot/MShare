package im.wangchao.msharecore.platform.weibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.constant.WBConstants;

import im.wangchao.msharecore.SharePlatform;

/**
 * <p>Description  : WeiboCallBackActivity.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 16/6/20.</p>
 * <p>Time         : 下午5:53.</p>
 */
public class WeiboCallBackActivity extends Activity implements IWeiboHandler.Response {

    private IWeiboShareAPI mWeiboShareAPI;
    private Weibo weibo;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharePlatform platform = SharePlatform.getCurrentPlatform(this);
        if (platform instanceof Weibo){
            weibo = (Weibo) platform;
            mWeiboShareAPI = weibo.getApi();
            weibo.doShare(this);
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mWeiboShareAPI != null){
            mWeiboShareAPI.handleWeiboResponse(intent, this); //当前应用唤起微博分享后，返回当前应用
        }
    }

    @Override public void onResponse(BaseResponse baseResponse) {
        switch (baseResponse.errCode){
            case WBConstants.ErrorCode.ERR_OK:
                if (weibo != null){
                    weibo.onSuccess();
                }
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                if (weibo != null) {
                    weibo.onCancel();
                }
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                if (weibo != null){
                    weibo.onFailure();
                }
                break;
        }
        
        finish();
    }
}
