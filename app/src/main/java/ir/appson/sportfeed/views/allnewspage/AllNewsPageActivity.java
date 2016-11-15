package ir.appson.sportfeed.views.allnewspage;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import ir.appson.sportfeed.ApplicationNEWS;
import ir.appson.sportfeed.R;
import ir.appson.sportfeed.dto.FeedsNew;
import ir.appson.sportfeed.util.ArrayHelper;
import ir.appson.sportfeed.util.RetrofitHelper;
import ir.appson.sportfeed.views.detail.NewsDetailActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllNewsPageActivity extends AppCompatActivity {
    static int STATIC_INT = 1;//This is used for startActivityForResult
    ListView listView;
    int[] newsListIds;
    int channelId;
    private Tracker mTracker;

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG", "Setting screen name: " + " all channels news list page");
        mTracker.setScreenName("AllNewsPageActivity " + " all news list page");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);
        listView = (ListView) findViewById(R.id.listView);
        forceRTLIfSupported();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(AllNewsPageActivity.this, NewsDetailActivity.class);
                int newsId = (int) view.getTag();
                Bundle bundle = new Bundle();
                bundle.putInt("channelId", channelId);
                bundle.putInt("newsId", newsId);
                bundle.putIntArray("newsListIds", newsListIds);
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String feedName = bundle.getString("FeedName");
            channelId = bundle.getInt("FeedId");
            setTitle(feedName);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        final Call<FeedsNew> feeds = new RetrofitHelper().getRetrofitForAllNews().feeds();
        feeds.enqueue(new Callback<FeedsNew>() {
            @Override
            public void onResponse(Call<FeedsNew> call, Response<FeedsNew> response) {
                if (response.isSuccessful()) {
                    ProgressBar p = (ProgressBar) findViewById(R.id.progressBar);
                    p.setVisibility(View.GONE);
                    FeedsNew a = response.body();
                    AllNewsListAdapter test = new AllNewsListAdapter(getApplicationContext(), R.layout.single_row, R.id.textViewTitleNewsTitle, a.Feeds.get(0).News);
                    listView.setAdapter(test);
                    newsListIds = ArrayHelper.extractIds2(a.Feeds.get(0).News);
                }
            }

            @Override
            public void onFailure(Call<FeedsNew> call, Throwable t) {
                Log.e("REST", t.getMessage());
            }
        });
        ApplicationNEWS application = (ApplicationNEWS) getApplication();
        mTracker = application.getDefaultTracker();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}