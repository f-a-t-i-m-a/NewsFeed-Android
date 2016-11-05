package ir.appson.sportfeed.proxy;

import android.content.Context;
import ir.appson.sportfeed.*;
import ir.appson.sportfeed.proxy.dto.NewsDetailObject;
import ir.appson.sportfeed.proxy.dto.TripleNewsForFirstPage;
import ir.appson.sportfeed.util.ArrayHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeedsWithNewsProxy extends ProxyBase {

    private static final String url =
            "http://" + apiServerName + apiUrlBase + "/feed/list/all/withnews";
    private static final String cacheKey = "channelsWithNews";

    public FeedsWithNewsProxy(Context context) {
        super(context);
    }

    public ArrayList<TripleNewsForFirstPage> getFromCache() {
        try {
            String cachedData = loadFromCache(cacheKey);
            if (cachedData != null)
                return parse(cachedData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<TripleNewsForFirstPage> get(int newsCount) {
        try {

            RestClient client = new RestClient(url);
            client.AddHeader(Application9090.SESSION_ID, getSessionId());
            client.AddHeader(Application9090.USER_AGENT, getUserAgentString());

            client.AddParam("newsCount", "" + newsCount);

            client.Execute(RestClient.RequestMethod.GET);

            if (client.isSuccessful()) {
                String response = client.getResponse();
                storeInCache(cacheKey, response);
                return parse(response);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<TripleNewsForFirstPage> parse(String response) throws JSONException {
        ArrayList<TripleNewsForFirstPage> result = new ArrayList<TripleNewsForFirstPage>();
        ArrayList<Integer> allNewsIds = new ArrayList<>();

        JSONObject json = new JSONObject(response);
        JSONArray feedsJsonArray = json.getJSONArray("Feeds");

        for (int i = 0; i < feedsJsonArray.length(); i++) {

            JSONObject channelJson = feedsJsonArray.getJSONObject(i);
            int channeId = channelJson.getInt("ID");
            String channelName = channelJson.getString("Title");
            JSONArray newsJsonArrayForCurrentChannel = channelJson.getJSONArray("News");
            ArrayList<NewsDetailObject> newsDetailObjectsForCurrentChannel = new ArrayList<>(newsJsonArrayForCurrentChannel.length());
            for (int j = 0; j < newsJsonArrayForCurrentChannel.length(); j++) {
                JSONObject newsJson = newsJsonArrayForCurrentChannel.getJSONObject(j);
                int newsId = newsJson.getInt("ID");
                allNewsIds.add(newsId);
                String newsTitle = newsJson.getString("Title");
                String newsSummary = newsJson.getString("Summary");
                String newsReference = newsJson.getString("NewsSourceShortName");
                String dateTime = newsJson.getString("CreationTime");

                JSONObject newsCoverImagesJsonObject = null;
                int imageId = 0;
                if (newsJson.has("CoverImage"))
                {
                    newsCoverImagesJsonObject = newsJson.getJSONObject("CoverImage");
                    imageId = newsCoverImagesJsonObject.getInt("ID");
                }
                newsDetailObjectsForCurrentChannel.add(new NewsDetailObject(newsId, newsTitle, newsSummary, "", imageId, newsReference, dateTime));

            }
            TripleNewsForFirstPage currentChannelTriple = new TripleNewsForFirstPage(channeId, channelName, newsDetailObjectsForCurrentChannel);

            result.add(currentChannelTriple);
        }

        for (TripleNewsForFirstPage tripleNewsForFirstPage:result){
            tripleNewsForFirstPage.setAllNewsIdsInThisChannel(ArrayHelper.convertIntegers(allNewsIds));
        }

        return result;
    }
}
