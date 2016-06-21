package im.wangchao.msharecore;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description  : SharePlatform.</p>
 * <p/>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 16/6/13.</p>
 * <p>Time         : 下午4:02.</p>
 */
public abstract class SharePlatform {
    private static final Map<String, SharePlatform> cachePlatform = new ConcurrentHashMap<>();
    private static volatile String currentPlatformName;

    protected PlatformEntry entry;
    protected WeakReference<Context> weakContext;

    /*package*/ static void setCurrentPlatformName(String platformName){
        currentPlatformName = platformName;
    }

    /**
     * 获取当前平台对象
     */
    @Nullable public static SharePlatform getCurrentPlatform(Context context){
        return getPlatform(context, currentPlatformName);
    }

    /**
     * 根据平台名称获取平台对象
     */
    @Nullable public static SharePlatform getPlatform(Context context, String platformName){
        try {
            if (cachePlatform.containsKey(platformName) && cachePlatform.get(platformName) != null){
                SharePlatform temp = cachePlatform.get(platformName);
                if (temp.weakContext != null && temp.weakContext.get() != null){
                    return cachePlatform.get(platformName);
                }
            }

            PlatformEntry entry = ShareXmlParser.instance().getPlatform(platformName);
            if (!checkEntry(entry, platformName)){
                return null;
            }

            Class cls = Class.forName(entry.className());
            SharePlatform platform = (SharePlatform)cls.newInstance();
            platform.weakContext = new WeakReference<>(context);
            platform.entry = entry;

            platform.initialize(context);
            cachePlatform.put(platformName, platform);
            return platform;
        } catch (Exception e) {
            Log.e(MShareCore.TAG, e.getMessage(), e);
            return null;
        }
    }

    /**
     * 检查 entry
     */
    private static boolean checkEntry(PlatformEntry entry, String platformName){
        if (entry == null){
            throw new RuntimeException(String.format("You must configure your platform(%s) in the file share.xml", platformName));
        }

        if (TextUtils.isEmpty(entry.key())){
            throw new RuntimeException(String.format("You must configure your platform(%s) key in the file share.xml", platformName));
        }

        if (TextUtils.isEmpty(entry.className())){
            throw new RuntimeException(String.format("You must configure your platform(%s) className in the file share.xml", platformName));
        }

        return true;
    }

    /**
     * 平台初始化方法，实例化平台对象会先调用此方法
     */
    protected abstract void initialize(Context context);

    /**
     * 分享方法
     */
    protected abstract void share(ShareRequest request);

    /**
     * 是否安装平台对应的 App
     */
    public abstract boolean isInstalled();

    public void onDestroy(){}

}
