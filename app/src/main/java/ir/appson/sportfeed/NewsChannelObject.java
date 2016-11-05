package ir.appson.sportfeed;

import java.util.ArrayList;

/**
 * Created by fatemeh on 9/9/2015.
 */
public class NewsChannelObject {
    int channelId;
    String title;
    ArrayList<String> newsTitlesList;

    public NewsChannelObject(int id, String title, ArrayList<String> newsTitilesList) {
        this.channelId = id;
        this.title = title;
        this.newsTitlesList = newsTitilesList;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getNewsList() {
        return newsTitlesList;
    }

    public void setNewsList(ArrayList<String> newsList) {
        this.newsTitlesList = newsList;
    }
}
