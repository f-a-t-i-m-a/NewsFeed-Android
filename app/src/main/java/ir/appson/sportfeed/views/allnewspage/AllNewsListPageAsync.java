package ir.appson.sportfeed.views.allnewspage;

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
import ir.appson.sportfeed.views.channelnewsfeed.ChannelNewsListPageThumbnailImageAsync;
import ir.appson.sportfeed.NewsFeedProxy;
import ir.appson.sportfeed.R;
import ir.appson.sportfeed.proxy.dto.NewsDetailObject;
import ir.appson.sportfeed.util.ArrayHelper;
import ir.appson.sportfeed.util.ImageHelper;

import java.util.ArrayList;

public class AllNewsListPageAsync extends AsyncTask<Object, Object, ArrayList<NewsDetailObject>> {


    AllNewsListPageActivity context;
    ListView listView;

    protected ArrayList<NewsDetailObject> doInBackground(Object[] params) {
        context = (AllNewsListPageActivity) params[0];
        listView = (ListView) params[1];
//        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences("ir.appson.sportfeed",Context.MODE_PRIVATE);
//        String sessionId = prefs.getString("sessionId", "");
        String sessionId = ((Application9090)context.getApplicationContext()).getSessionId();

        ArrayList<NewsDetailObject> newsList = NewsFeedProxy.getAllNewsDetailObjectList(100, sessionId);
        context.newsListIds = ArrayHelper.extractIds(newsList);
        return newsList;
    }

    @Override
    public void onPostExecute(ArrayList<NewsDetailObject> newsDetailObjectsList){
        super.onPostExecute(newsDetailObjectsList);
        ((ProgressBar) context.findViewById(R.id.progressBar_activity_all_news_list_page)).setVisibility(View.GONE);

        AllNewsListPageAdapter test;
        test = new AllNewsListPageAdapter(context, R.layout.single_row, R.id.textViewTitleNewsTitle , newsDetailObjectsList);

        listView.setAdapter(test);
        return;
    }


    class AllNewsListPageAdapter extends ArrayAdapter<NewsDetailObject>
    {

        Context context;
        ArrayList<NewsDetailObject> list;
        public AllNewsListPageAdapter(Context context, int resource, int textViewResourceId, ArrayList<NewsDetailObject> list) {

            super(context, resource, textViewResourceId, list);
            this.context = context;
            this.list = list;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.single_row, parent, false);

            NewsDetailObject newsDetailObject = list.get(position);

            ImageView imageView = (ImageView) row.findViewById(R.id.imageViewNewsThumbnail);
            TextView title = (TextView) row.findViewById(R.id.textViewTitle);
            TextView newsResource = (TextView) row.findViewById(R.id.textView_single_row_newsResource);
            TextView dateTime = (TextView) row.findViewById(R.id.textView_single_row_dateTime);
            title.setPadding(0,15,10,0);
            title.setTextColor(Color.BLACK);

            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.thumbnail_loading);
            imageView.setImageBitmap(ImageHelper.getRoundedCornerBitmap(bitmap, getContext().getResources().getDisplayMetrics()));
            new ChannelNewsListPageThumbnailImageAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,imageView, newsDetailObject);

            title.setText(newsDetailObject.getNewsTitle());
            newsResource.setText(newsDetailObject.getNewsReference());
            newsResource.setPadding(0, 15, 10, 0);
            dateTime.setText(newsDetailObject.getJalaliDateTime());
            dateTime.setPadding(0, 15, 10, 0);
            //FF here we setTag to the newsId so we can retrieve it in itemClickListener and pass it to the NewsDetailActivity.
            row.setTag(newsDetailObject.getNewsId());
            return  row;
        }
    }
}
