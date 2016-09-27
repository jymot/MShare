package im.wangchao.msharecore.platform.weichat;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;

/**
 * <p>Description  : WeChatMoments.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 16/6/15.</p>
 * <p>Time         : 下午2:53.</p>
 */
public class WeChatMoments extends WeChat{
    public static final String NAME = WeChatMoments.class.getSimpleName();

    @Override protected int getScene() {
        return SendMessageToWX.Req.WXSceneTimeline;
    }
}
