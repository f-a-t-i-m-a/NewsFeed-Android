package ir.appson.sportfeed;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import org.acra.ACRA;

import java.util.UUID;
//
//@ReportsCrashes(
//        formUri = "http://9090.teammelli.ir/api/mobile/session/crash",
//        reportType = HttpSender.Type.JSON,
//        mode = ReportingInteractionMode.TOAST,
//        resToastText = R.string.crash_toast_text
//)

public class Application9090 extends Application {
    private Tracker mTracker;

    @Deprecated // Should be replaced by non-static getUserAgentString
    public static String userAgent = "ir.appson.sportfeed;0.0.0";

    public static String SESSION_ID = "X-JJ-SessionId";
    public static String USER_AGENT = "X-JJ-UserAgent";
    public static String ABOUT_US_URL = "http://teammelli.ir/download/html/about.html";
    public static String CUSTOMER_SUPPORT_URL = "http://teammelli.ir/download/html/support.html";
    public static String SUPPORT_NATIONAL_TEAM_URL = "http://teammelli.ir/download/html/subscribe.html";

    private String sessionId = "";
    private boolean sessionStarted = false;
    private String userAgentString = "ir.appson.sportfeed;0.0.0";

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initializeUserAgentString();
        initializeSessionId();
        ACRA.init(this);
    }

    public String getUserAgentString() {
        return userAgentString;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isSessionStarted() {
        return sessionStarted;
    }

    public void setSessionStarted() {
        this.sessionStarted = true;
    }

    private void initializeUserAgentString() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;

            userAgentString = getPackageName() + ";" + version;
            userAgent = userAgentString; // TODO Temporary; remove this line after removing the userAgent static field
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initializeSessionId() {
        sessionId = UUID.randomUUID().toString();
    }
}

