package ir.appson.sportfeed.proxy;

import android.content.Context;
import ir.appson.sportfeed.proxy.dto.FeedSummary;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeedsListProxy extends ProxyBase {

    private static final String url = "http://" + apiServerName + apiUrlBase + "/feed/list/all";
    private static final String cacheKey = "channels";

    public FeedsListProxy(Context context) {
        super(context);
    }

    public ArrayList<FeedSummary> getFromCache() {
//        try {
            String cachedData = loadFromCache(cacheKey);
//            if (cachedData != null)
//                return parse(cachedData);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        return null;
    }

}
