package ir.appson.sportfeed.proxy.dto;

import java.util.ArrayList;

public class TripleNewsForFirstPage {
    int channelId;
    String title;
    ArrayList<NewsDetailObject> newsDetailObjects = new ArrayList<>();
    int[] allNewsIdsInThisChannel = new int[0];

    public TripleNewsForFirstPage(int channelId, String channelName, ArrayList<NewsDetailObject> newsDetailObjectsForCurrentChannel) {
        this.channelId = channelId;
        this.title = channelName;
        this.newsDetailObjects = newsDetailObjectsForCurrentChannel;
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

    //It contains 3 news objects
    public ArrayList<NewsDetailObject> getNewsDetailObjects() {
        return newsDetailObjects;
    }

    public void setNewsDetailObjects(ArrayList<NewsDetailObject> newsDetailObjects) {
        this.newsDetailObjects = newsDetailObjects;
    }

    public int[] getAllNewsIdsInThisChannel() {
        return allNewsIdsInThisChannel;
    }

    public void setAllNewsIdsInThisChannel(int[] allNewsIdsInThisChannel) {
        this.allNewsIdsInThisChannel = allNewsIdsInThisChannel;
    }
}