package ir.appson.sportfeed.views.channelnewspage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import ir.appson.sportfeed.dto.ChannelNEWSRoot;
import ir.appson.sportfeed.util.ArrayHelper;
import ir.appson.sportfeed.util.RetrofitHelper;
import ir.appson.sportfeed.views.detailnewspage.DetailNEWSActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChannelNEWSActivity extends ActionBarActivity {

    static int STATIC_INT = 1;
    static String mNewsChannelTitle = "";
    ListView list;
    int channelId;
    int[] newsListIDs;
    private Tracker mTracker;

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG", "Setting screen name: " + " channel news list page");
        mTracker.setScreenName("ChannelNewsListActivity " + " channel news list page");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_news);
        list = (ListView) findViewById(R.id.listView);
        //FF Added to make the action bar RTL (right to left)
        forceRTLIfSupported();
        //FF
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(ChannelNEWSActivity.this, DetailNEWSActivity.class);
                int newsId = (int) view.getTag();
                Bundle bundle = new Bundle();
                bundle.putInt("channelId", channelId);
                bundle.putInt("newsId", newsId);
                bundle.putIntArray("newsListIDs", newsListIDs);
                myIntent.putExtras(bundle);
                startActivityForResult(myIntent, STATIC_INT);
            }
        });
        //FF to change the title in the action bar
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String feedName = bundle.getString("FeedName");
            mNewsChannelTitle = feedName;
            channelId = bundle.getInt("FeedId");
        }
        setTitle(mNewsChannelTitle);
        //FF for back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);//???


        final Call<ChannelNEWSRoot> feeds = new RetrofitHelper().getRetrofitForChannelNews().feeds(new String(String.valueOf(channelId)));
        feeds.enqueue(new Callback<ChannelNEWSRoot>() {
            @Override
            public void onResponse(Call<ChannelNEWSRoot> call, Response<ChannelNEWSRoot> response) {
                if (response.isSuccessful()) {
                    ProgressBar p = (ProgressBar) findViewById(R.id.progressBar);
                    p.setVisibility(View.GONE);

                    ChannelNEWSRoot a = response.body();
                    ChannelNEWSAdapter test = new ChannelNEWSAdapter(getApplicationContext(), R.layout.single_row, R.id.textViewTitleNewsTitle, a.NewsList);
                    list.setAdapter(test);
                    newsListIDs = ArrayHelper.extractIDs(a.NewsList);
                }
            }

            @Override
            public void onFailure(Call<ChannelNEWSRoot> call, Throwable t) {
                Log.e("REST", t.getMessage());
            }
        });
        // Obtain the shared Tracker instance.
        ApplicationNEWS application = (ApplicationNEWS) getApplication();
        mTracker = application.getDefaultTracker();
    }

    //FF added to make the action bar RTL
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (1): {
                if (resultCode == Activity.RESULT_OK) {
                    int channelId = data.getIntExtra("channelIdFromNewsDetailActivity", -1);
                    // TODO Update your TextView.
                    this.channelId = channelId;
                }
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                // click on 'up' button in the action bar, handle it here
                //When user clicks the up button in action bar the onBackPressed does not get called. So we need to call it manually.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}