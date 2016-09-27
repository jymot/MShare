package im.wangchao.msharecore;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description  : ShareXmlParser.</p>
 * <p>Author       : wangchao.</p>
 * <p>Date         : 16/6/14.</p>
 * <p>Time         : 下午5:11.</p>
 */
/*package*/ class ShareXmlParser {
    private static volatile ShareXmlParser instance;

    public static ShareXmlParser instance(){
        if (instance == null){
            synchronized (ShareXmlParser.class){
                if (instance == null){
                    instance = new ShareXmlParser();
                }
            }
        }

        return instance;
    }

    private ShareXmlParser(){}

    private final List<PlatformEntry> platformEntries = new Vector<>();
    private final Map<String, String> keyAndValue = new ConcurrentHashMap<>();

    public PlatformEntry getPlatform(String platformName){
        synchronized (platformEntries){
            for (PlatformEntry entry: platformEntries){
                if (entry.name().equals(platformName)){
                    return entry;
                }
            }
        }

        return null;
    }

    public List<PlatformEntry> getPlatformEntries() {
        return new Vector<>(platformEntries);
    }

    public void parse(Context action){
        parse(action, false);
    }

    /**
     * 解析 share.xml
     *
     * @param action Context
     * @param reset  boolean
     */
    public void parse(Context action, boolean reset) {
        if (reset){
            platformEntries.clear();
        }

        if (platformEntries.size() != 0){
            return;
        }

        // First checking the class namespace for share.xml
        int id = action.getResources().getIdentifier("share", "xml", action.getClass().getPackage().getName());
        if (id == 0) {
            // If we couldn't find share.xml there, we'll look in the namespace from AndroidManifest.xml
            id = action.getResources().getIdentifier("share", "xml", action.getPackageName());
            if (id == 0) {
                Log.e(MShareCore.TAG, "res/xml/share.xml is missing!");
                return;
            }
        }
        parse(action.getResources().getXml(id));
    }

    private void parse(XmlPullParser xml) {
        int eventType = -1;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                handleStartTag(xml);
            } else if (eventType == XmlPullParser.END_TAG) {
                handleEndTag(xml);
            }
            try {
                eventType = xml.next();
            } catch (Exception e) {
                Log.e(MShareCore.TAG, e.getMessage(), e);
            }
        }
    }

    private void handleStartTag(XmlPullParser xml) {
        String strNode = xml.getName();
        if (strNode.equals("platform")) {
            for (int i = 0; i < xml.getAttributeCount(); i++){
                keyAndValue.put(xml.getAttributeName(i), xml.getAttributeValue(i));
            }
        }
    }

    private void handleEndTag(XmlPullParser xml) {
        String strNode = xml.getName();
        if (strNode.equals("platform")) {
            PlatformEntry entry = new PlatformEntry(new ConcurrentHashMap<>(keyAndValue));
            platformEntries.add(entry);

            clearVariable();
        }
    }

    private void clearVariable(){
        keyAndValue.clear();
    }
}
