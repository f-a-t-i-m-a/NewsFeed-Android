package ir.appson.sportfeed.views.about;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import ir.appson.sportfeed.Application9090;
import ir.appson.sportfeed.R;

import java.net.URL;


public class AboutUsActivity extends ActionBarActivity {

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
        setContentView(R.layout.activity_about_us);
        //FF Added to make the action bar RTL (right to left)
        forceRTLIfSupported();
        //FF for back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);//???

        ((WebView)findViewById(R.id.webView_activity_about_us)).loadUrl(Application9090.ABOUT_US_URL);
        // Obtain the shared Tracker instance.
        Application9090 application = (Application9090) getApplication();
        mTracker = application.getDefaultTracker();
    }
    //FF added to make the action bar RTL
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void forceRTLIfSupported()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_about_us, menu);
//        return true;
//    }
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
