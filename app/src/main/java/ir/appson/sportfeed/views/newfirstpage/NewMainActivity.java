package ir.appson.sportfeed.views.newfirstpage;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
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
import ir.appson.sportfeed.dto.ChannelsNamesObject;
import ir.appson.sportfeed.dto.ChannelsNamesRoot;
import ir.appson.sportfeed.util.RetrofitHelper;
import ir.appson.sportfeed.views.about.AboutUsActivity;
import ir.appson.sportfeed.views.allnewspage.AllNEWSActivity;
import ir.appson.sportfeed.views.channelnewspage.ChannelNEWSActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class NewMainActivity extends AppCompatActivity {
    public List<ChannelsNamesObject> mNewsChannelsObjects = new ArrayList<ChannelsNamesObject>();
    ActionBarDrawerToggle mDrawerToggle;
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private int mCurrentSelectedPosition = 0;

    //FF added to make the action bar RTL
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        ActionBar actionBar = getSupportActionBar();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
            }
        };
        // mNewsChannelsObjects will be used when user clicks on an item in drawer.
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // Set the adapter for the list view
        //        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.text_view_navigation_drawer, mPlanetTitles));
        // Set the list's click listener
        final Call<ChannelsNamesRoot> feeds = new RetrofitHelper().getRetrofitForNav().feeds();
        feeds.enqueue(new Callback<ChannelsNamesRoot>() {
            @Override
            public void onResponse(Call<ChannelsNamesRoot> call, Response<ChannelsNamesRoot> response) {
                if (response.isSuccessful()) {
                    populateYourself(response.body());
                    //                    callback.resultReady(response.body());
                }
            }

            @Override
            public void onFailure(Call<ChannelsNamesRoot> call, Throwable t) {
                Log.e("REST", t.getMessage());
            }
        });
        forceRTLIfSupported();
    }

    public void populateYourself(ChannelsNamesRoot c) {
        ArrayList<String> channelsNamesList = new ArrayList<>();
        for (ChannelsNamesObject channelObject : c.Feeds)
            channelsNamesList.add(channelObject.Title);
        channelsNamesList.add(0, getResources().getString(R.string.home_persian));
        channelsNamesList.add(1, getResources().getString(R.string.all_channels_persian));
        channelsNamesList.add(channelsNamesList.size(), getResources().getString(R.string.support_national_team_persian));
        channelsNamesList.add(channelsNamesList.size(), getResources().getString(R.string.suggest_app_to_a_friend_persian));
        channelsNamesList.add(channelsNamesList.size(), getResources().getString(R.string.customer_support_persian));
        channelsNamesList.add(channelsNamesList.size(), getResources().getString(R.string.about_us_persian));
        // mNewsChannelsObjects will be used when user clicks on an item in drawer.
        mNewsChannelsObjects = c.Feeds;
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

    //oncli
    public ChannelsNamesObject getFeedSummary(int positionInList) {
        positionInList -= 2;
        if (0 <= positionInList && positionInList < mNewsChannelsObjects.size()) {
            return mNewsChannelsObjects.get(positionInList);
        }
        return null;
    }

    public void onNavigationDrawerItemSelected(int position) {
        if (getFeedSummary(position) != null && 2 <= position && position <= mNewsChannelsObjects.size() + 1) {
            Intent myIntent = new Intent(NewMainActivity.this, ChannelNEWSActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("FeedName", getFeedSummary(position).Title);
            bundle.putInt("FeedId", getFeedSummary(position).ID);
            myIntent.putExtras(bundle);
            startActivity(myIntent);
        } else if (position == 0) {
        } else if (position == 1) {
            Intent myIntent = new Intent(NewMainActivity.this, AllNEWSActivity.class);
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
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
