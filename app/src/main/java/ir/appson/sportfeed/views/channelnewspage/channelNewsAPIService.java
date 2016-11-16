package ir.appson.sportfeed.views.channelnewspage;

import ir.appson.sportfeed.dto.ChannelNEWSRoot;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by fatemeh on 11/6/2016.
 */
public interface ChannelNEWSApiService {

    @GET("/api/mobile/feed/1/news/list/all")
    Call<ChannelNEWSRoot> feeds();
}


