package ir.appson.sportfeed;

import android.util.Log;
import ir.appson.sportfeed.views.navigationDrawer.NavigationDrawerFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fatemeh on 11/6/2016.
 */

public class RESTClient{

    private static RESTClient instance = null;

    private ResultReadyCallback callback;

    private static final String BASE_URL = "https://news.khoonat.com";
    private APIService service;
    boolean success = false;



    public RESTClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        service = retrofit.create(APIService.class);
    }
    public Feeds getFeeds(final NavigationDrawerFragment navigationDrawerFragment) {
        final Call<Feeds> feeds = service.feeds();
        final Feeds[] f = new Feeds[1];
        feeds.enqueue(new Callback<Feeds>() {
            @Override
            public void onResponse(Call<Feeds> call, Response<Feeds> response) {
                if (response.isSuccessful()) {
                    f[0] = response.body();
                    navigationDrawerFragment.populateYourself(response.body());
//                    callback.resultReady(response.body());

                }
            }

            @Override
            public void onFailure(Call<Feeds> call, Throwable t) {
                Log.e("REST", t.getMessage());
            }

        });
        return f[0];
    }

    public void setCallback(ResultReadyCallback callback) {
        this.callback = callback;
    }


    public static RESTClient getInstance() {
        if(instance == null) {
            instance = new RESTClient();
        }
        return instance;
    }

    public interface ResultReadyCallback {
        public void resultReady(Feeds users);
    }

}
