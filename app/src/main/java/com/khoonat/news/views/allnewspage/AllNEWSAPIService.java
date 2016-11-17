package com.khoonat.news.views.allnewspage;

import com.khoonat.news.dto.AllNEWSRoot;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by fatemeh on 11/6/2016.
 */
public interface AllNEWSAPIService {

    @GET("/api/mobile/feed/list/all/withnews")
    Call<AllNEWSRoot> feeds();
}


