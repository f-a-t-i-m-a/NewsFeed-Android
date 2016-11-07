package ir.appson.sportfeed.proxy.dto;

import java.util.ArrayList;

public class FeedSummary {
    ArrayList<NewsDetailObject> News;
    Integer ID;
    public String Title;


    public Integer getID() {
        return ID;
    }

    public String getTitle() {
        return Title;
    }
}
