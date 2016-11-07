package ir.appson.sportfeed.views.allnewspage;

import android.util.Log;
import ir.appson.sportfeed.proxy.dto.FeedSummary;
import ir.appson.sportfeed.proxy.dto.Feeds;
import ir.appson.sportfeed.views.navigationDrawer.NavigationDrawerAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

/**
 * Created by fatemeh on 11/6/2016.
 */

public class AllNewsPageRESTClient {

    private static AllNewsPageRESTClient instance = null;

    private ResultReadyCallback callback;

    private static final String BASE_URL = "https://news.khoonat.com";
    private NavigationDrawerAPIService service;
    boolean success = false;



    public AllNewsPageRESTClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        service = retrofit.create(NavigationDrawerAPIService.class);
    }
    public ArrayList<FeedSummary> getFeeds() {
        final Call<Feeds> feeds = service.feeds();
        feeds.enqueue(new Callback<Feeds>() {
            @Override
            public void onResponse(Call<Feeds> call, Response<Feeds> response) {
                if (response.isSuccessful()) {
                    callback.resultReady(response.body());

                }
            }

            @Override
            public void onFailure(Call<Feeds> call, Throwable t) {
                Log.e("REST", t.getMessage());
            }

        });
        return null;
    }

    public void setCallback(ResultReadyCallback callback) {
        this.callback = callback;
    }


    public static AllNewsPageRESTClient getInstance() {
        if(instance == null) {
            instance = new AllNewsPageRESTClient();
        }
        return instance;
    }

    public interface ResultReadyCallback {
        public void resultReady(Feeds users);
    }

}
