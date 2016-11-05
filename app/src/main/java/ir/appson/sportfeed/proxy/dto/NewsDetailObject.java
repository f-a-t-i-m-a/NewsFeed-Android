package ir.appson.sportfeed.proxy.dto;

import android.graphics.Bitmap;
import ir.appson.sportfeed.util.Iso8601Parser;
import ir.appson.sportfeed.util.JalaliCalendar;
import ir.appson.sportfeed.util.JalaliCalendarUtil;

import java.util.Calendar;
import java.util.Date;


public class NewsDetailObject{
    public NewsDetailObject(int newsId, String newsTitle, String newsSummary, int imageIds) {
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.newsSummary = newsSummary;
        this.newsImageId = imageIds;
    }
    public NewsDetailObject(int newsId, String newsTitle, String newsSummary, String newsText, int imageIds) {
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.newsSummary = newsSummary;
        this.newsText = newsText;
        this.newsImageId = imageIds;
    }
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
    public NewsDetailObject(int newsId, String newsTitle, String newsSummary){
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.newsSummary = newsSummary;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewsSummary() {
        return newsSummary;
    }

    public void setNewsSummary(String newsSummary) {
        this.newsSummary = newsSummary;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsText() {
        return newsText;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }

    public Bitmap[] getNewsImages() {
        return newsImages;
    }

    public void setNewsImages(Bitmap[] newsImages) {
        this.newsImages = newsImages;
    }

    public int getNewsImageId() {
        return newsImageId;
    }

    public void setNewsImageId(int newsImageId) {
        this.newsImageId = newsImageId;
    }

    public String getNewsReference() {
        return newsReference;
    }

    public void setNewsReference(String newsReference) {
        this.newsReference = newsReference;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public String getJalaliDateTime() {
        return JalaliCalendarUtil.ToJalaliDateTimeString(dateTime);
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
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