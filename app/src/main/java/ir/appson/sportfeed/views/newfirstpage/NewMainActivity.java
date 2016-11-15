package ir.appson.sportfeed.views.newfirstpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ir.appson.sportfeed.CustomerSupportActivity;
import ir.appson.sportfeed.R;
import ir.appson.sportfeed.ReportSuggestionActivity;
import ir.appson.sportfeed.dto.FeedDetail;
import ir.appson.sportfeed.dto.FeedsNew;
import ir.appson.sportfeed.util.RetrofitHelper;
import ir.appson.sportfeed.views.about.AboutUsActivity;
import ir.appson.sportfeed.views.allnewspage.AllNewsPageActivity;
import ir.appson.sportfeed.views.channelnewspage.ChannelPageActivity;
import ir.appson.sportfeed.views.firstPage.FirstPageActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class NewMainActivity extends AppCompatActivity {
    public List<FeedDetail> mNewsChannelsObjects = new ArrayList<FeedDetail>();
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private int mCurrentSelectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        //        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        // mNewsChannelsObjects will be used when user clicks on an item in drawer.
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        // Set the adapter for the list view
        //        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.text_view_navigation_drawer, mPlanetTitles));
        // Set the list's click listener
        final Call<FeedsNew> feeds = new RetrofitHelper().getRetrofitForNav().feeds();
        feeds.enqueue(new Callback<FeedsNew>() {
            @Override
            public void onResponse(Call<FeedsNew> call, Response<FeedsNew> response) {
                if (response.isSuccessful()) {
                    populateYourself((ArrayList<FeedDetail>) response.body().Feeds);
                    //                    callback.resultReady(response.body());
                }
            }

            @Override
            public void onFailure(Call<FeedsNew> call, Throwable t) {
                Log.e("REST", t.getMessage());
            }

        });
    }

    public void populateYourself(List<FeedDetail> feeds) {
        ArrayList<String> channelsNamesList = new ArrayList<>();
        for (FeedDetail channelObject : feeds)
            channelsNamesList.add(channelObject.Title);
        channelsNamesList.add(0, getResources().getString(R.string.home_persian));
        channelsNamesList.add(1, getResources().getString(R.string.all_channels_persian));
        channelsNamesList.add(channelsNamesList.size(), getResources().getString(R.string.support_national_team_persian));
        channelsNamesList.add(channelsNamesList.size(), getResources().getString(R.string.suggest_app_to_a_friend_persian));
        channelsNamesList.add(channelsNamesList.size(), getResources().getString(R.string.customer_support_persian));
        channelsNamesList.add(channelsNamesList.size(), getResources().getString(R.string.about_us_persian));
        // mNewsChannelsObjects will be used when user clicks on an item in drawer.
        mNewsChannelsObjects = feeds;
        mDrawerList.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.text_view_navigation_drawer, channelsNamesList));
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerList != null) {
            mDrawerList.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mDrawerList);
        }
        onNavigationDrawerItemSelected(position);
    }

    public FeedDetail getFeedSummary(int positionInList) {
        positionInList -= 2;
        if (0 <= positionInList && positionInList < mNewsChannelsObjects.size()) {
            return mNewsChannelsObjects.get(positionInList);
        }
        return null;
    }

    public void onNavigationDrawerItemSelected(int position) {
        if (getFeedSummary(position) != null && 2 <= position && position <= mNewsChannelsObjects.size() + 1) {
            Intent myIntent = new Intent(NewMainActivity.this, ChannelPageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("FeedName", getFeedSummary(position).Title);
            bundle.putInt("FeedId", getFeedSummary(position).ID);
            myIntent.putExtras(bundle);
            startActivity(myIntent);
        } else if (position == 0) {
        } else if (position == 1) {
            Intent myIntent = new Intent(NewMainActivity.this, AllNewsPageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("FeedName", getString(R.string.all_news_persian));
            bundle.putInt("FeedId", 0);
            myIntent.putExtras(bundle);
            startActivity(myIntent);
        } else if (position == mNewsChannelsObjects.size() + 2) {
            Intent myIntent = new Intent(NewMainActivity.this, ReportSuggestionActivity.class);
            startActivity(myIntent);
        } else if (position == mNewsChannelsObjects.size() + 3) {
            shareIt();
        } else if (position == mNewsChannelsObjects.size() + 4) {
            Intent myIntent = new Intent(NewMainActivity.this, CustomerSupportActivity.class);
            startActivity(myIntent);
        } else if (position == mNewsChannelsObjects.size() + 5) {
            Intent myIntent = new Intent(NewMainActivity.this, AboutUsActivity.class);
            startActivity(myIntent);
        } else {
            // update the main content by replacing fragments
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, FirstPageActivity.FirstPageFragment.newInstance(position + 1))
                    .commit();
        }
    }

    private void shareIt() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String text =
                "\n" + getResources().getString(R.string.dear_friend_enjoy_this_app);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            mDrawerLayout.openDrawer(mDrawerList);
        }
        return super.onOptionsItemSelected(item);
    }

}
