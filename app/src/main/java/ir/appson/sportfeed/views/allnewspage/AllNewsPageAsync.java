package ir.appson.sportfeed.views.allnewspage;

import android.os.AsyncTask;
import android.view.View;
import android.widget.*;

import ir.appson.sportfeed.Application9090;
import ir.appson.sportfeed.NewsFeedProxy;
import ir.appson.sportfeed.R;
import ir.appson.sportfeed.proxy.dto.NewsDetailObject;
import ir.appson.sportfeed.util.ArrayHelper;

import java.util.ArrayList;

public class AllNewsPageAsync extends AsyncTask<Object, Object, ArrayList<NewsDetailObject>> {


    AllNewsPageActivity context;
    ListView listView;

    protected ArrayList<NewsDetailObject> doInBackground(Object[] params) {
        context = (AllNewsPageActivity) params[0];
        listView = (ListView) params[1];
//        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences("ir.appson.sportfeed",Context.MODE_PRIVATE);
//        String sessionId = prefs.getString("sessionId", "");
        String sessionId = ((Application9090)context.getApplicationContext()).getSessionId();

        ArrayList<NewsDetailObject> newsList = NewsFeedProxy.getAllNewsDetailObjectList(100, sessionId);
        context.newsListIds = ArrayHelper.extractIds(newsList);
        return newsList;
    }




}
