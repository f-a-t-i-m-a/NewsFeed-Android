package com.khoonat.news.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by fatemeh on 11/16/2016.
 */
public class AllNEWSRoot {
    public List<ChannelNEWSRoot> Feeds;

    public ArrayList<ChannelNEWSObject> getList() {
        ArrayList<ChannelNEWSObject> result = new ArrayList<ChannelNEWSObject>();
        if (Feeds != null)
            for (ChannelNEWSRoot channel : Feeds) {
                ArrayList current = channel.getList();
                if (current != null)
                    result.addAll((Collection<ChannelNEWSObject>) current);
            }
        return result;
    }

}
