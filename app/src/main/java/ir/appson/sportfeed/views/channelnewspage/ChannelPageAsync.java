package ir.appson.sportfeed.views.channelnewspage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import ir.appson.sportfeed.Application9090;
import ir.appson.sportfeed.NewsFeedProxy;
import ir.appson.sportfeed.R;
import ir.appson.sportfeed.proxy.dto.NewsDetailObject;
import ir.appson.sportfeed.util.ArrayHelper;
import ir.appson.sportfeed.util.ImageHelper;

import java.util.ArrayList;

/**
 * Created by fatemeh on 9/10/2015.
 */
public class ChannelPageAsync extends AsyncTask {

    ChannelPageActivity newsFeedActivity;
    ArrayList<NewsDetailObject> newsList = new ArrayList<NewsDetailObject>();

    protected ArrayList<NewsDetailObject> doInBackground(Object[] params) {
        newsFeedActivity = (ChannelPageActivity) params[0];
//        SharedPreferences prefs = newsFeedActivity.getApplicationContext().getSharedPreferences("ir.appson.sportfeed",Context.MODE_PRIVATE);
//        String sessionId = prefs.getString("sessionId", "");
        String sessionId = ((Application9090)newsFeedActivity.getApplicationContext()).getSessionId();

        newsList = NewsFeedProxy.getChannelNewsDetailObjectList(newsFeedActivity.channelId, 100, sessionId);
        newsFeedActivity.newsListIds = ArrayHelper.extractIds(newsList);
        return newsList;
    }

    @Override
    public void onPostExecute(Object newsDetailObjectsList){
        super.onPostExecute(newsDetailObjectsList);
        ((ProgressBar) newsFeedActivity.findViewById(R.id.progressBar_activity_channel_news_feed)).setVisibility(View.GONE);

        ChannelNewsListPageAdapter adapter;
        adapter = new ChannelNewsListPageAdapter(newsFeedActivity.getApplicationContext(), R.layout.single_row, R.id.textViewTitleNewsTitle , newsList);

        newsFeedActivity.list.setAdapter(adapter);
        return;
    }


    class ChannelNewsListPageAdapter extends ArrayAdapter<NewsDetailObject>
    {

        Context context;
        ArrayList<NewsDetailObject> list;
        public ChannelNewsListPageAdapter(Context context, int resource, int textViewResourceId, ArrayList<NewsDetailObject> list) {

            super(context, resource, textViewResourceId, list);
            this.context = context;
            this.list = list;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.single_row, parent, false);

            NewsDetailObject newsDetailObject = list.get(position);

            ImageView myImage = (ImageView) row.findViewById(R.id.imageViewNewsThumbnail);
            TextView title = (TextView) row.findViewById(R.id.textViewTitle);
            TextView newsResource = (TextView) row.findViewById(R.id.textView_single_row_newsResource);
            TextView dateTime = (TextView) row.findViewById(R.id.textView_single_row_dateTime);

            title.setTextColor(Color.BLACK);

            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.thumbnail_loading);
            myImage.setImageBitmap(ImageHelper.getRoundedCornerBitmap(bitmap, getContext().getResources().getDisplayMetrics()));
            new ChannelPageThumbnailImageAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, myImage, newsDetailObject);

            title.setText(newsDetailObject.getNewsTitle());
            newsResource.setText(newsDetailObject.getNewsReference());
            newsResource.setPadding(0, 15, 10, 0);
            dateTime.setText(newsDetailObject.getJalaliDateTime());
            dateTime.setPadding(0, 15, 10, 0);

            //FF here we setTag to the newsId so we can retrieve it in itemClickListener and pass it to the NewsDetailActivity.
            title.setTag(newsDetailObject.getNewsId());
            row.setTag(newsDetailObject.getNewsId());
            return  row;
        }
    }
}
