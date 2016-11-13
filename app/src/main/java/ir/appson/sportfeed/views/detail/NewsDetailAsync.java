package ir.appson.sportfeed.views.detail;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.appson.sportfeed.Application9090;
import ir.appson.sportfeed.NewsFeedProxy;
import ir.appson.sportfeed.R;
import ir.appson.sportfeed.proxy.dto.NewsDetailObject;

/**
 * Created by fatemeh on 9/11/2015.
 */
public class NewsDetailAsync extends AsyncTask<Object, Object, NewsDetailObject> {
    View parentView;

    @Override
    protected NewsDetailObject doInBackground(Object[] params) {
        Integer newsId = (Integer) params[0];
        RelativeLayout rl = (RelativeLayout) params[1];
        parentView = rl;
//        SharedPreferences prefs = parentView.getContext().getSharedPreferences("ir.appson.sportfeed", Context.MODE_PRIVATE);
//        String sessionId = prefs.getString("sessionId", "");
        String sessionId = ((Application9090) parentView.getContext().getApplicationContext()).getSessionId();
        NewsDetailObject ndo = NewsFeedProxy.getNewsDetail(newsId, sessionId);
        return ndo;
    }

    @Override
    public void onPostExecute(NewsDetailObject newsDetailObject) {
        super.onPostExecute(newsDetailObject);
        ((ProgressBar) parentView.findViewById(R.id.progressBar_fragment_news_detail)).setVisibility(View.GONE);
        TextView title = (TextView) parentView.findViewById(R.id.textView_newsDetailActivity_newsTitle);
        TextView newsSummay = (TextView) parentView.findViewById(R.id.textView_newsDetailActivity_newsSummary);
        TextView newsText = (TextView) parentView.findViewById(R.id.textView_newsDetailActivity_newsDescription);
        TextView newsResource = (TextView) parentView.findViewById(R.id.textView_newsDetailActivity_newsResource);
        TextView dateTime = (TextView) parentView.findViewById(R.id.textView_newsDetailActivity_dateTime);
        ImageView imageView = (ImageView) parentView.findViewById(R.id.imageView_newsDetailActivity);
        if (newsDetailObject.getNewsImageId() == 0)
            imageView.setImageResource(R.drawable.fullsize_noimage);
        else
            new NewsDetailImageAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, imageView, newsDetailObject);
        title.setText(newsDetailObject.getNewsTitle());
        newsSummay.setText(newsDetailObject.getNewsSummary());
        newsText.setText(newsDetailObject.getNewsText());
        newsResource.setText(newsDetailObject.getNewsReference());
        dateTime.setText(newsDetailObject.getJalaliDateTime());
    }
}
