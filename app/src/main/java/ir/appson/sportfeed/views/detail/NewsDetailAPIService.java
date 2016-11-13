package ir.appson.sportfeed.views.detail;

import ir.appson.sportfeed.dto.FeedDetail;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by fatemeh on 11/6/2016.
 */
public interface NewsDetailAPIService {

    @GET("/api/mobile/news/18/details\n")
    Call<FeedDetail> feeds();
}


