package com.khoonat.news.util;

import com.khoonat.news.views.allnewspage.AllNEWSAPIService;
import com.khoonat.news.views.channelnewspage.ChannelNEWSAPIService;
import com.khoonat.news.views.detailnewspage.DetailNEWSAPIService;
import com.khoonat.news.views.navigationDrawer.NavigationDrawerAPIService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fatemeh on 11/7/2016.
 */
public class RetrofitHelper {
    private static final String BASE_URL = "https://news.khoonat.com";
    boolean success = false;
    private NavigationDrawerAPIService service;

    public NavigationDrawerAPIService getRetrofitForNav() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        service = retrofit.create(NavigationDrawerAPIService.class);
        return service;
    }

    public AllNEWSAPIService getRetrofitForAllNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        return retrofit.create(AllNEWSAPIService.class);
    }

    public ChannelNEWSAPIService getRetrofitForChannelNews() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        return retrofit.create(ChannelNEWSAPIService.class);
    }

    public DetailNEWSAPIService getRetrofitForDetail() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        return retrofit.create(DetailNEWSAPIService.class);
    }
}
