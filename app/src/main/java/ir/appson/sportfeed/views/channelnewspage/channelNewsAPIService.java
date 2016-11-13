package ir.appson.sportfeed.views.channelnewspage;

import ir.appson.sportfeed.dto.FeedDetail;
import ir.appson.sportfeed.dto.FeedsNew;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by fatemeh on 11/6/2016.
 */
public interface ChannelNewsAPIService {

    @GET("/api/mobile/feed/list/all")
    Call<FeedDetail> feeds();
}

