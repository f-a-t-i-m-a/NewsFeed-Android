package com.khoonat.news.proxy.dto;

import android.graphics.Bitmap;
import com.khoonat.news.util.Iso8601Parser;
import com.khoonat.news.util.JalaliCalendarUtil;

import java.util.Calendar;

public class NewsDetailObject{

    public NewsDetailObject(int newsId, String newsTitle, String newsSummary, String newsText, int imageId, String newsReference, String dateTime) {
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.newsSummary = newsSummary;
        this.newsText = newsText;
        this.newsImageId = imageId;
        this.newsReference = newsReference;

        this.dateTime = Iso8601Parser.toCalendar(dateTime);
    }

    public String toString(){
        return "NewsDetail.toString!!";
    }

    public int getNewsId() {
        return newsId;
    }
    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsReference() {
        return newsReference;
    }


    public String getJalaliDateTime() {
        return JalaliCalendarUtil.ToJalaliDateTimeString(dateTime);
    }


    private int newsId;
    private String newsTitle;
    private String newsSummary;
    private String newsText;
    private Bitmap[] newsImages;
    private int newsImageId;
    private String newsReference;
    private Calendar dateTime;

}