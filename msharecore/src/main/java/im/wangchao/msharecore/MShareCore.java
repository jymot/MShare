package im.wangchao.msharecore;

import android.content.Context;

/**
 * <p>Description  : MShareCore.</p>
 * <p/>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 16/6/13.</p>
 * <p>Time         : 下午3:48.</p>
 */
public final class MShareCore {
    public static final String TAG = MShareCore.class.getSimpleName();
    private static volatile MShareCore instance;

    public static MShareCore instance(){
        if (instance == null){
            synchronized (MShareCore.class){
                if (instance == null){
                    instance = new MShareCore();
                }
            }
        }

        return instance;
    }

    private MShareCore(){}

    private Context context;
    private boolean invokeInitialize;

    /**
     * 模块初始化方法，使用分享时，必须优先调用
     */
    public void initialize(Context context){
        this.invokeInitialize = true;
        ShareXmlParser.instance().parse(context);
        this.context = context;
    }

    /**
     * 分享方法
     */
    public MShareCore share(ShareRequest request){

        String platformName = request.platformName();
        SharePlatform platform = getPlatform(platformName);
        if (platform != null){
            SharePlatform.setCurrentPlatformName(platformName);
            platform.share(request);
        }

        return this;
    }

    /**
     * 获取平台对象
     */
    public SharePlatform getPlatform(ShareRequest request){
        String platformName = request.platformName();
        return getPlatform(platformName);
    }

    /**
     * 获取平台对象
     */
    public SharePlatform getPlatform(String platformName){
        if (!this.invokeInitialize){
            throw new RuntimeException("Please call the initialize method.");
        }

        return SharePlatform.getPlatform(context, platformName);
    }
}
