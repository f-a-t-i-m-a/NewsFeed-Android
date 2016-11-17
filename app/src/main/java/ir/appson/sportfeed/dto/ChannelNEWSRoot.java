package ir.appson.sportfeed.dto;

import java.util.ArrayList;

/**
 * Created by fatemeh on 11/13/2016.
 */
public class ChannelNEWSRoot {
    public ArrayList<ChannelNEWSObject> NewsList;//for channl news
    public ArrayList<ChannelNEWSObject> News;//for all news

    public ArrayList<ChannelNEWSObject> getList() {
        if (News!=null && News.size()>0)
            return News;
        else if (NewsList!=null && NewsList.size()>0)
            return NewsList;
        return new ArrayList<ChannelNEWSObject>();
    }
}
