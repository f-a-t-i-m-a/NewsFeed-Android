package ir.appson.sportfeed.views.detailnewspage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import ir.appson.sportfeed.ApplicationNEWS;
import ir.appson.sportfeed.R;
import ir.appson.sportfeed.dto.News;
import ir.appson.sportfeed.util.ArrayHelper;
import ir.appson.sportfeed.util.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailNEWSActivity extends AppCompatActivity {
    MyFragmentStatePagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private Tracker mTracker;

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG", "Setting screen name: " + " news detail page");
        mTracker.setScreenName("DetailNEWSActivity " + " news detail page");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail_with_view_pager);
        Bundle bundle = getIntent().getExtras();
        int newsId = bundle.getInt("newsId");
        int[] newsIds = bundle.getIntArray("newsListIds");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager(), newsIds, newsId);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(ArrayHelper.positionInArray(newsIds, newsId));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        forceRTLIfSupported();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_detail_with_view_pager, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        int channelId = getIntent().getExtras().getInt("channelId");
        Intent intent = new Intent();
        intent.putExtra("channelIdFromNewsDetailActivity", channelId);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                // click on 'up' button in the action bar, handle it here
                //When user clicks the up button in action bar the onBackPressed does not get called. So we need to call it manually.
                onBackPressed();
                return true;
            case R.id.menu_item_share:
                shareIt();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareIt() {
        TextView tv = (TextView) findViewById(R.id.textView_newsTitle);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String text = tv.getText() +
                "\n" +
                "http://9090.teammelli.ir/n/" +
                getIntent().getExtras().getInt("newsId");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "News");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class NewsDetailWithViewPagerFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public NewsDetailWithViewPagerFragment() {
        }

        public static NewsDetailWithViewPagerFragment newInstance(int sectionNumber, int newsId) {
            NewsDetailWithViewPagerFragment fragment = new NewsDetailWithViewPagerFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putInt("newsId", newsId);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            int newsId = getArguments().getInt("newsId");
            final View rootView = inflater.inflate(R.layout.fragment_news_detail_with_view_pager, container, false);
            final Call<News> feeds = new RetrofitHelper().getRetrofitForDetail().detail();
            feeds.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    ProgressBar p = (ProgressBar) rootView.findViewById(R.id.progressBar);
                    p.setVisibility(View.GONE);


                    if (response.isSuccessful()) {
                        News a = response.body();
                        TextView t = (TextView) rootView.findViewById(R.id.textView_newsTitle);
                        t.setText(a.News.Title);
                        TextView t1 = (TextView) rootView.findViewById(R.id.textView_newsText);
                        t1.setText(a.News.Summary);
//                        AllNEWSAdapter test = new AllNEWSAdapter(getApplicationContext(), R.layout.single_row, R.id.textViewTitleNewsTitle, a.Feeds.get(0).News);
//                        listView.setAdapter(test);
                    }
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    Log.e("REST", t.getMessage());
                }
            });
            /**/
            return rootView;
        }
    }

    /***************************************************************************************/
    public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
        private int[] newsIds;
        private int newsId;
        private int ffPosition;

        public MyFragmentStatePagerAdapter(FragmentManager fm, int[] newsIds, int newsId) {
            super(fm);
            this.newsIds = newsIds;
            //this is the id of the clicked news. so it should be the center of the swiping pages.
            this.newsId = newsId;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a FirstPageFragment (defined as a static inner class below).
            int id = newsIds[position];
            return NewsDetailWithViewPagerFragment.newInstance(position, id);
        }

        @Override
        public int getCount() {
            // Show 100 total pages.
            return newsIds.length;
        }
    }

}
