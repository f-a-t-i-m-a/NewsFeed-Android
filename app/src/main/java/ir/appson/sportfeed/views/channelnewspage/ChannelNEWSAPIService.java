package ir.appson.sportfeed.views.channelnewspage;

import ir.appson.sportfeed.dto.ChannelNEWSRoot;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by fatemeh on 11/6/2016.
 */
public interface ChannelNEWSAPIService {

    @GET("/api/mobile/feed/{channelID}/news/list/all")
//    @GET("/api/mobile/feed/1/news/list/all")
    Call<ChannelNEWSRoot> feeds(  @Path("channelID") String channelID);
//    Call<ChannelNEWSRoot> feeds();
}


