package ir.appson.sportfeed.proxy;

import android.content.Context;

import java.util.ArrayList;

import ir.appson.sportfeed.dto.FeedDetail;

public class FeedsListProxy extends ProxyBase {
    private static final String url = "http://" + apiServerName + apiUrlBase + "/feed/list/all";
    private static final String cacheKey = "channels";

    public FeedsListProxy(Context context) {
        super(context);
    }

    public ArrayList<FeedDetail> getFromCache() {
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
