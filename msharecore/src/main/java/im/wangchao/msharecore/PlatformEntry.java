package im.wangchao.msharecore;

import java.util.Map;

/**
 * <p>Description  : PlatformEnrty.</p>
 * <p/>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 16/6/14.</p>
 * <p>Time         : 下午4:49.</p>
 */
public final class PlatformEntry {
    private static final String NECESSARY_FIELD_NAME        = "name";
    private static final String NECESSARY_FIELD_KEY         = "key";
    private static final String NECESSARY_FIELD_CLASS_NAME  = "className";

    private Map<String, String> keyAndValue;

    public PlatformEntry(Map<String, String> keyAndValue){
        this.keyAndValue = keyAndValue;
    }

    /**
     * 平台名称
     */
    public String name(){
        return keyAndValue.get(NECESSARY_FIELD_NAME);
    }

    /**
     * 平台 key
     */
    public String key(){
        return keyAndValue.get(NECESSARY_FIELD_KEY);
    }

    /**
     * 平台类名
     */
    public String className(){
        return keyAndValue.get(NECESSARY_FIELD_CLASS_NAME);
    }

    /**
     * 获取自定义 key <=> value
     */
    public String get(String key){
        return keyAndValue.get(key);
    }

    /**
     * 是否包含该自定义key
     */
    public boolean contains(String key){
        return keyAndValue.containsKey(key);
    }
}
