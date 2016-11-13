package ir.appson.sportfeed.views.allnewspage;

import ir.appson.sportfeed.dto.FeedsNew;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by fatemeh on 11/6/2016.
 */
public interface AllNewsPageAPIService {

    @GET("/api/mobile/feed/list/all/withnews")
    Call<FeedsNew> feeds();
}


