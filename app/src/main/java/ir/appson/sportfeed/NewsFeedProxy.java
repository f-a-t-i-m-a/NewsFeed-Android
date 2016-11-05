package ir.appson.sportfeed;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import ir.appson.sportfeed.proxy.RestClient;
import ir.appson.sportfeed.proxy.dto.NewsDetailObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Base64;

import java.util.ArrayList;


public class NewsFeedProxy {

    private static final String apiServerName = "9090.teammelli.ir";
    private static final String apiUrlBase = "/api/mobile";

    private static final String getAllNewsDetailObjectListUrl = "http://" + apiServerName + apiUrlBase + "/news/list/all";
//    private static final String getChannelNewsDetailObjectListUrl = "http://" + apiServerName + apiUrlBase + ;
//    private static final String getNewsDetailUrl = "http://" + apiServerName + apiUrlBase + ;
//    private static final String getNewsImageUrl = "http://" + apiServerName + apiUrlBase + ;
//    private static final String getNewsImageThumbnailUrl = "http://" + apiServerName + apiUrlBase + ;



    public static ArrayList<NewsDetailObject> getAllNewsDetailObjectList(int count, String sessionId) {
        ArrayList<NewsDetailObject> result = new ArrayList<NewsDetailObject>();
        try {

            RestClient client = new RestClient(getAllNewsDetailObjectListUrl);
            client.AddHeader(Application9090.SESSION_ID, sessionId);
            client.AddHeader(Application9090.USER_AGENT, Application9090.userAgent);

            client.Execute(RestClient.RequestMethod.GET);

            String response = client.getResponse();
            JSONObject json = new JSONObject(response);
            JSONArray newsJsonArray = json.getJSONArray("NewsList");

            for (int i = 0; i < newsJsonArray.length(); i++) {
                JSONObject newsJson = newsJsonArray.getJSONObject(i);
                int newsId = newsJson.getInt("ID");
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
                result.add(new NewsDetailObject(newsId, newsTitle, newsSummary, "", imageId, newsReference, dateTime));

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static ArrayList<NewsDetailObject> getChannelNewsDetailObjectList(int channelId, int count, String sessionId) {
        ArrayList<NewsDetailObject> result = new ArrayList<NewsDetailObject>();
        try {

            RestClient client = new RestClient("http://9090.teammelli.ir/api/mobile/feed/"+channelId+"/news/list/all");
            client.AddHeader(Application9090.SESSION_ID, sessionId);
            client.AddHeader(Application9090.USER_AGENT, Application9090.userAgent);
            client.AddParam("startIndex", "0");
            client.AddParam("pageSize", "" + count);
            client.Execute(RestClient.RequestMethod.GET);

            String response = client.getResponse();
            JSONObject json = new JSONObject(response);
            JSONArray newsJsonArray = json.getJSONArray("NewsList");

            for (int i = 0; i < newsJsonArray.length(); i++) {
                JSONObject newsJson = newsJsonArray.getJSONObject(i);
                int newsId = newsJson.getInt("ID");
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
                result.add(new NewsDetailObject(newsId, newsTitle, newsSummary,"", imageId, newsReference, dateTime));

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static NewsDetailObject getNewsDetail(int newsId, String sessionId){
        NewsDetailObject result = null;
        try {

            RestClient client = new RestClient("http://9090.teammelli.ir/api/mobile/news/"+newsId+"/details");
            client.AddHeader(Application9090.SESSION_ID, sessionId);
            client.AddHeader(Application9090.USER_AGENT, Application9090.userAgent);

            client.Execute(RestClient.RequestMethod.GET);

            String response = client.getResponse();
            JSONObject json = new JSONObject(response).getJSONObject("News");
            String newsTitle = json.getString("Title");
            String newsSummary = json.getString("Summary");
            String newsText = json.getString("Text");
            String newsReference = json.getString("NewsSourceShortName");
            String dateTime = json.getString("CreationTime");
            JSONArray newsImagesJsonArray = json.getJSONArray("NewsImages");
            JSONObject newsImage;
            int imageId =0;
            if (newsImagesJsonArray.length()>0) {
                newsImage = newsImagesJsonArray.getJSONObject(0);//we only shhow the first image
                imageId = newsImage.getInt("ID");
            }
            result = new NewsDetailObject(newsId, newsTitle, newsSummary,newsText , imageId, newsReference, dateTime);
        }
        catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Bitmap getNewsImage(int imageId, String sessionId){
        Bitmap result = null;
        try {

            RestClient client = new RestClient("http://9090.teammelli.ir/api/mobile/image/fullsize?ids="+imageId);
            client.AddHeader(Application9090.SESSION_ID, sessionId);
            client.AddHeader(Application9090.USER_AGENT, Application9090.userAgent);

            client.Execute(RestClient.RequestMethod.GET);

            String response = client.getResponse();
            JSONObject json = new JSONObject(response);
            JSONArray images = json.getJSONArray("NewsImages");
            if (images != null && images.length()>0){

                JSONObject newsImage0 = images.getJSONObject(0);
                String imageBase64String = newsImage0.getString("FullSizeBytes");

                byte[] decodedString = Base64.decode(imageBase64String, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                result = decodedByte;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Bitmap getNewsImageThumbnail(int imageId, String sessionId){
        Bitmap result = null;
        try {

            RestClient client = new RestClient("http://9090.teammelli.ir/api/mobile/image/thumbnail?ids="+imageId);
            client.AddHeader(Application9090.SESSION_ID, sessionId);
            client.AddHeader(Application9090.USER_AGENT, Application9090.userAgent);

            client.Execute(RestClient.RequestMethod.GET);

            String response = client.getResponse();
            JSONObject json = new JSONObject(response);
            JSONArray images = json.getJSONArray("NewsImages");
            if (images != null && images.length()>0){

                JSONObject newsImage0 = images.getJSONObject(0);
                String imageBase64String = newsImage0.getString("ThumbnailBytes");

                byte[] decodedString = Base64.decode(imageBase64String, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                result = decodedByte;
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }




}
