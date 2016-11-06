package ir.appson.sportfeed.proxy;

import android.content.Context;
import ir.appson.sportfeed.Application9090;
import ir.appson.sportfeed.RESTClient;
import ir.appson.sportfeed.proxy.dto.ChannelObject;
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

    public ArrayList<ChannelObject> getFromCache() {
        try {
            String cachedData = loadFromCache(cacheKey);
            if (cachedData != null)
                return parse(cachedData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<ChannelObject> get() {
        RESTClient.getInstance().getUsers();
       /* try {
            RestClient client = new RestClient(url);
            client.AddHeader(Application9090.SESSION_ID, getSessionId());
            client.AddHeader(Application9090.USER_AGENT, getUserAgentString());
            client.Execute(RestClient.RequestMethod.GET);
            if (client.isSuccessful()) {
                String response = client.getResponse();
                storeInCache(cacheKey, response);
                return parse(response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return null;
    }

    private ArrayList<ChannelObject> parse(String response) throws JSONException {
        ArrayList<ChannelObject> result = new ArrayList<ChannelObject>();
        JSONObject json = new JSONObject(response);
        JSONArray feedsJsonArray = json.getJSONArray("Feeds");
        for (int i = 0; i < feedsJsonArray.length(); i++) {
            JSONObject channelJson = feedsJsonArray.getJSONObject(i);
            int channelId = channelJson.getInt("ID");
            String channelName = channelJson.getString("Title");
            result.add(new ChannelObject(channelId, channelName));
        }
        return result;
    }
}
