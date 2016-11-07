package ir.appson.sportfeed.views.firstPage;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import ir.appson.sportfeed.views.channelnewspage.ChannelPageActivity;
import ir.appson.sportfeed.views.channelnewspage.ChannelPageThumbnailImageAsync;
import ir.appson.sportfeed.views.detail.NewsDetailWithViewPagerActivity;
import ir.appson.sportfeed.R;
import ir.appson.sportfeed.proxy.FeedsWithNewsProxy;
import ir.appson.sportfeed.proxy.dto.NewsDetailObject;
import ir.appson.sportfeed.proxy.dto.TripleNewsForFirstPage;
import ir.appson.sportfeed.util.ImageHelper;

import java.util.ArrayList;

public class FirstPageAsync extends AsyncTask<Object, Object, ArrayList<TripleNewsForFirstPage>> {

    private Context context;
    private LayoutInflater inflater;
    private ScrollView scrollView;

    private FeedsWithNewsProxy proxy;

    public FirstPageAsync(Context context, LayoutInflater inflater, ScrollView scrollView) {
        this.context = context;
        this.inflater = inflater;
        this.scrollView = scrollView;

        this.proxy = new FeedsWithNewsProxy(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        ArrayList<TripleNewsForFirstPage> channels = proxy.getFromCache();
        if (channels != null)
            populateChannelsAndNews(channels);
    }

    @Override
    protected ArrayList<TripleNewsForFirstPage> doInBackground(Object... params) {
        try {
            return proxy.get(3);
        } catch (Exception e) {
            Toast.makeText(context, "No network connection", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<TripleNewsForFirstPage> channels) {
        if (channels == null) {
            return;
        }

        populateChannelsAndNews(channels);
    }

    private void populateChannelsAndNews(ArrayList<TripleNewsForFirstPage> channels) {
        LinearLayout l = (LinearLayout) scrollView.findViewById(R.id.linearLayout_firstPage_channels);
        scrollView.findViewById(R.id.progressBar_fragment_news_list).setVisibility(View.GONE);

        if (l.getChildCount() > 0)
            l.removeAllViews();

        for (TripleNewsForFirstPage channel : channels) {
            View partialChild = inflater.inflate(R.layout.first_page_partial, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 20, 0, 30);
            partialChild.setLayoutParams(params);
            partialChild.setPadding(0, 20, 0, 0);

            final ArrayList<NewsDetailObject> newsDetailObjectsTriple = channel.getNewsDetailObjects();
            final int[] newsIds = channel.getAllNewsIdsInThisChannel();
            final int channelId = channel.getChannelId();
            final String channelName = channel.getTitle();
            if (newsIds.length == 0)
                continue;

            TextView currentTextView = (TextView) partialChild.findViewById(R.id.textView_firstPage_channel_title);
            currentTextView.setText(channel.getTitle());
            currentTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(context, ChannelPageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("newsId", newsIds[0]);
                    bundle.putInt("channelId", channelId);
                    bundle.putInt("FeedId", channelId);
                    bundle.putString("FeedName", channelName);
                    bundle.putIntArray("newsListIds", newsIds);
                    myIntent.putExtras(bundle);
                    context.startActivity(myIntent);
                }
            });

            TextView moreButton = (TextView) partialChild.findViewById(R.id.textView_firstPage_more);
            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(context, ChannelPageActivity.class);
                    Bundle bundle = new Bundle();
                    //We assume that each channel has AT LEAST one news. Hence the newsIds[0] wont ever throw exception.
                    bundle.putInt("newsId", newsIds[0]);
                    bundle.putInt("channelId", channelId);
                    bundle.putInt("FeedId", channelId);
                    bundle.putString("FeedName", channelName);
                    bundle.putIntArray("newsListIds", newsIds);
                    myIntent.putExtras(bundle);
                    context.startActivity(myIntent);
                }
            });

            ImageView newsImage;

            if (newsDetailObjectsTriple.size() > 0)
            {
                LinearLayout rowLayout = ((LinearLayout) partialChild.findViewById(R.id.linearLayout_firstPage_news1));
                rowLayout.setTag(newsDetailObjectsTriple.get(0).getNewsId());
                rowLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent myIntent = new Intent(context, NewsDetailWithViewPagerActivity.class);
                        int newsId = (int) view.getTag();
                        Bundle bundle = new Bundle();
                        bundle.putInt("newsId", newsId);
                        bundle.putIntArray("newsListIds", newsIds);
                        myIntent.putExtras(bundle);
                        context.startActivity(myIntent);
                    }
                });

                rowLayout.setVisibility(View.VISIBLE);
                NewsDetailObject newsDetailObject = newsDetailObjectsTriple.get(0);
                currentTextView = (TextView) partialChild.findViewById(R.id.textView_firstPage_news1_title);
                currentTextView.setText(newsDetailObjectsTriple.get(0).getNewsTitle());
                currentTextView.setPadding(0, 10, 10, 0);
                TextView newsResource = (TextView) partialChild.findViewById(R.id.textView_firstPage_news1_resource);
                TextView dateTime = (TextView) partialChild.findViewById(R.id.textView_firstPage_news1_dateTime);
                newsResource.setText(newsDetailObject.getNewsReference());
                newsResource.setPadding(0, 5, 10, 0);
                dateTime.setText(newsDetailObject.getJalaliDateTime());
                dateTime.setPadding(0, 5, 10, 0);

                newsImage = (ImageView) partialChild.findViewById(R.id.imageView_firstPage_news1);
                newsImage.setImageBitmap(ImageHelper.getRoundedCornerBitmap(newsImage));
                new ChannelPageThumbnailImageAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, newsImage, newsDetailObject);
            }

            if (newsDetailObjectsTriple.size() > 1)
            {
                LinearLayout rowLayout = ((LinearLayout) partialChild.findViewById(R.id.linearLayout_firstPage_news2));
                rowLayout.setTag(newsDetailObjectsTriple.get(1).getNewsId());
                rowLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent myIntent = new Intent(context, NewsDetailActivity.class);
                        Intent myIntent = new Intent(context, NewsDetailWithViewPagerActivity.class);
                        int newsId = (int) view.getTag();
                        Bundle bundle = new Bundle();
                        bundle.putInt("newsId", newsId);
                        bundle.putIntArray("newsListIds", newsIds);
                        myIntent.putExtras(bundle);
                        context.startActivity(myIntent);
                    }
                });

                rowLayout.setVisibility(View.VISIBLE);
                NewsDetailObject newsDetailObject = newsDetailObjectsTriple.get(1);
                currentTextView = (TextView) partialChild.findViewById(R.id.textView_firstPage_news2_title);
                currentTextView.setText(newsDetailObject.getNewsTitle());
                currentTextView.setPadding(0, 10, 10, 0);
                TextView newsResource = (TextView) partialChild.findViewById(R.id.textView_firstpage_news2_resource);
                TextView dateTime = (TextView) partialChild.findViewById(R.id.textView_firstPage_news2_dateTime);
                newsResource.setText(newsDetailObject.getNewsReference());
                newsResource.setPadding(0, 5, 10, 0);
                dateTime.setText(newsDetailObject.getJalaliDateTime());
                dateTime.setPadding(0, 5, 10, 0);

                newsImage = (ImageView) partialChild.findViewById(R.id.imageView_firstPage_news2);
                newsImage.setImageBitmap(ImageHelper.getRoundedCornerBitmap(newsImage));
                new ChannelPageThumbnailImageAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, newsImage, newsDetailObject);
            }

            if (newsDetailObjectsTriple.size() > 2)
            {
                LinearLayout rowLayout = ((LinearLayout) partialChild.findViewById(R.id.linearLayout_firstPage_news3));
                rowLayout.setTag(newsDetailObjectsTriple.get(2).getNewsId());
                rowLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent myIntent = new Intent(context, NewsDetailWithViewPagerActivity.class);
                        int newsId = (int) view.getTag();
                        Bundle bundle = new Bundle();
                        bundle.putInt("newsId", newsId);
                        bundle.putIntArray("newsListIds", newsIds);
                        myIntent.putExtras(bundle);
                        context.startActivity(myIntent);
                    }
                });

                rowLayout.setVisibility(View.VISIBLE);
                NewsDetailObject newsDetailObject = newsDetailObjectsTriple.get(2);
                currentTextView = (TextView) partialChild.findViewById(R.id.textView_firstPage_news3_title);
                currentTextView.setText(newsDetailObject.getNewsTitle());
                currentTextView.setPadding(0, 10, 10, 0);
                TextView newsResource = (TextView) partialChild.findViewById(R.id.textView_firstpage_news3_resource);
                TextView dateTime = (TextView) partialChild.findViewById(R.id.textView_firstPage_news3_dateTime);
                newsResource.setText(newsDetailObject.getNewsReference());
                newsResource.setPadding(0, 5, 10, 0);
                dateTime.setText(newsDetailObject.getJalaliDateTime());
                dateTime.setPadding(0, 5, 10, 0);

                newsImage = (ImageView) partialChild.findViewById(R.id.imageView_firstPage_news3);
                newsImage.setImageBitmap(ImageHelper.getRoundedCornerBitmap(newsImage));
                new ChannelPageThumbnailImageAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, newsImage, newsDetailObject);
            }

            l.addView(partialChild);
        }
    }

}
