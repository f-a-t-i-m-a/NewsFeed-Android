package ir.appson.sportfeed.util;

import ir.appson.sportfeed.views.allnewspage.AllNewsPageAPIService;
import ir.appson.sportfeed.views.allnewspage.AllNewsPageRESTClient;
import ir.appson.sportfeed.views.channelnewspage.ChannelNewsAPIService;
import ir.appson.sportfeed.views.navigationDrawer.NavigationDrawerAPIService;
import ir.appson.sportfeed.views.navigationDrawer.NavigationDrawerRESTClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fatemeh on 11/7/2016.
 */
public class RetrofitHelper {
    private static final String BASE_URL = "https://news.khoonat.com";
    private static AllNewsPageRESTClient instance = null;
    boolean success = false;
    private NavigationDrawerRESTClient.ResultReadyCallback callback;
    private NavigationDrawerAPIService service;

    public NavigationDrawerAPIService getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        service = retrofit.create(NavigationDrawerAPIService.class);
        return service;
    }

    public AllNewsPageAPIService getRetrofitForAllNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        return retrofit.create(AllNewsPageAPIService.class);
    }

    public ChannelNewsAPIService getRetrofitForChannelNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        return retrofit.create(ChannelNewsAPIService.class);
    }
}
