package im.wangchao.msharecore;

/**
 * <p>Description  : ShareCallback.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 16/6/14.</p>
 * <p>Time         : 下午2:12.</p>
 */
public interface ShareCallback {
    /**
     * 默认分享失败code
     */
    int ERROR_DEFAULT = 0;
    /**
     * 没有安装平台App，分享失败
     */
    int ERROR_NOT_INSTALLED = -1;

    void onSuccess();

    void onFailure(int errorCode);

    void onCancel();
}
