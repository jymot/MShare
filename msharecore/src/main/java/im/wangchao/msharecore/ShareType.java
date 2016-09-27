package im.wangchao.msharecore;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>Description  : ShareType.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 16/6/14.</p>
 * <p>Time         : 上午11:25.</p>
 */
public interface ShareType {

    /**
     * 文本分享
     */
    int TEXT = 0x01;
    /**
     * 图片分享
     */
    int IMAGE = 0x02;
    /**
     * 网页分享
     */
    int WEB_PAGE = 0x03;
    /**
     * 混合分享
     */
    int MULTIPLE = 0x04;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TEXT, IMAGE, WEB_PAGE, MULTIPLE})
    @interface Type{}
}
