package ir.appson.sportfeed.proxy;

import android.content.Context;
import ir.appson.sportfeed.ApplicationNEWS;
import ir.appson.sportfeed.util.StreamUtil;

import java.io.*;

public class ProxyBase {
    private static final String cacheFileNamePrefix = "cache_";

    protected static final String apiServerName = "9090.teammelli.ir";
//    protected static final String apiServerName = "news.khoonat.com";
    protected static final String apiUrlBase = "/api/mobile";

    protected Context context;

    public ProxyBase(Context context) {
        this.context = context;
    }

    protected String getSessionId() {
        return ((ApplicationNEWS) context.getApplicationContext()).getSessionId();
    }

    protected String getUserAgentString() {
        return ((ApplicationNEWS) context.getApplicationContext()).getUserAgentString();
    }

    protected void storeInCache(String key, String value) {
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(cacheFileNamePrefix + key, Context.MODE_PRIVATE);
            fos.write(value.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected String loadFromCache(String key) {
        FileInputStream fis = null;

        try {
            fis = context.openFileInput(cacheFileNamePrefix + key);
            return new String(StreamUtil.readBytes(fis), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
