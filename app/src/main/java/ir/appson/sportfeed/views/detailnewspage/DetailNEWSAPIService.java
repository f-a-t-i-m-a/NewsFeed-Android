package ir.appson.sportfeed.views.detailnewspage;

import ir.appson.sportfeed.dto.DetailNEWSRoot;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by fatemeh on 11/6/2016.
 */
public interface DetailNEWSAPIService {

    @GET("/api/mobile/news/{newsID}/details")
    Call<DetailNEWSRoot> detail(@Path("newsID") String channelID);
}


