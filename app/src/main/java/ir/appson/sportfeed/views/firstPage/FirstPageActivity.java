package ir.appson.sportfeed.views.firstPage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import ir.appson.sportfeed.Application9090;
import ir.appson.sportfeed.CustomerSupportActivity;
import ir.appson.sportfeed.R;
import ir.appson.sportfeed.ReportSuggestionActivity;
import ir.appson.sportfeed.views.about.AboutUsActivity;
import ir.appson.sportfeed.views.allnewspage.AllNewsPageActivity;
import ir.appson.sportfeed.views.channelnewspage.ChannelPageActivity;
import ir.appson.sportfeed.views.navigationDrawer.NavigationDrawerFragmentOld;

public class FirstPageActivity extends AppCompatActivity implements NavigationDrawerFragmentOld.NavigationDrawerCallbacks {
    private NavigationDrawerFragmentOld mNavigationDrawerFragmentOld;
    private Tracker mTracker;
    /** Used to store the last screen title. For use in {@link #restoreActionBar()}. */

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG", "Setting screen name: " + " news list page");
        mTracker.setScreenName("FirstPageActivity " + "news list page");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        mNavigationDrawerFragmentOld = (NavigationDrawerFragmentOld) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        mNavigationDrawerFragmentOld.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        //FF added for RTLing NavigationDrawer. Right to Left
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        //To open the navigation drawer in first app rum
        SharedPreferences sp = getSharedPreferences("ir.appson.sportfeed", 0);
        boolean isFirstStart = sp.getBoolean("key", true);
        // we will not get a value  at first start, so true will be returned
        // if it was the first app start
        if (isFirstStart) {
            DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            View mFragmentContainerView = findViewById(R.id.navigation_drawer);
            mDrawerLayout.openDrawer(mFragmentContainerView);
            SharedPreferences.Editor e = sp.edit();
            // we save the value "false", indicating that it is no longer the first appstart
            e.putBoolean("key", false);
            e.commit();
        }
        // Obtain the shared Tracker instance.
        Application9090 application = (Application9090) getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (mNavigationDrawerFragmentOld != null && mNavigationDrawerFragmentOld.getFeedSummary(position) != null && 2 <= position && position <= mNavigationDrawerFragmentOld.mNewsChannelsObjects.size() + 1) {
            Intent myIntent = new Intent(FirstPageActivity.this, ChannelPageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("FeedName", mNavigationDrawerFragmentOld.getFeedSummary(position).Title);
            bundle.putInt("FeedId", mNavigationDrawerFragmentOld.getFeedSummary(position).ID);
            myIntent.putExtras(bundle);
            startActivity(myIntent);
        } else if (position == 0) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, FirstPageFragment.newInstance(position + 1))
                    .commit();
        } else if (position == 1) {
            Intent myIntent = new Intent(FirstPageActivity.this, AllNewsPageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("FeedName", getString(R.string.all_news_persian));
            bundle.putInt("FeedId", 0);
            myIntent.putExtras(bundle);
            startActivity(myIntent);
        } else if (position == mNavigationDrawerFragmentOld.mNewsChannelsObjects.size() + 2) {
            Intent myIntent = new Intent(FirstPageActivity.this, ReportSuggestionActivity.class);
            startActivity(myIntent);
        } else if (position == mNavigationDrawerFragmentOld.mNewsChannelsObjects.size() + 3) {
            shareIt();
        } else if (position == mNavigationDrawerFragmentOld.mNewsChannelsObjects.size() + 4) {
            Intent myIntent = new Intent(FirstPageActivity.this, CustomerSupportActivity.class);
            startActivity(myIntent);
        } else if (position == mNavigationDrawerFragmentOld.mNewsChannelsObjects.size() + 5) {
            Intent myIntent = new Intent(FirstPageActivity.this, AboutUsActivity.class);
            startActivity(myIntent);
        } else {
            // update the main content by replacing fragments
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, FirstPageFragment.newInstance(position + 1))
                    .commit();
        }
    }

    public void onSectionAttached(int number) {
        //        mTitle = mNewsChannelsNames.get(number-1);
    }

    /*
        public void restoreActionBar() {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
        }*/
    private void shareIt() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String text =
                "\n" + getResources().getString(R.string.dear_friend_enjoy_this_app);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    //******************************
    public static class FirstPageFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public FirstPageFragment() {
        }

        public static Fragment newInstance(int sectionNumber) {
            FirstPageFragment fragment = new FirstPageFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ScrollView scrollView = (ScrollView) inflater.inflate(R.layout.fragment_news_list, container, false);
            new FirstPageAsync(getActivity(), inflater, scrollView)
                    .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            if (!((Application9090) getActivity().getApplicationContext()).isSessionStarted()) {
                new SessionStartAsync(getActivity()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                ((Application9090) getActivity().getApplicationContext()).setSessionStarted();
            }
            return scrollView;
        }
/*
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((FirstPageActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }*/
    }
}
