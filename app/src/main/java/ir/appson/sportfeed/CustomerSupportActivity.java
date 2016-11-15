package ir.appson.sportfeed;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class CustomerSupportActivity extends AppCompatActivity {
    private Tracker mTracker;

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG", "Setting screen name: " + " customer support page");
        mTracker.setScreenName("CustomerSupportActivity " + " customer support page");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_support);
        //FF Added to make the action bar RTL (right to left)
        forceRTLIfSupported();
        //FF for back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);//???
        ((WebView)findViewById(R.id.webView_activity_customer_support)).loadUrl(ApplicationNEWS.CUSTOMER_SUPPORT_URL);
        // Obtain the shared Tracker instance.
        ApplicationNEWS application = (ApplicationNEWS) getApplication();
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
//        getMenuInflater().inflate(R.menu.menu_customer_support, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
