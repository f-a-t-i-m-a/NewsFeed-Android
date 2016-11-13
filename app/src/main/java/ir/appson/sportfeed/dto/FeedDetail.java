package ir.appson.sportfeed.dto;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by fatemeh on 11/13/2016.
 */
public class FeedDetail {
    public Integer ID;
    public Bitmap[] TumbnailBytes;
    public String Title;
    public ArrayList<NewsDetail> News;
}
