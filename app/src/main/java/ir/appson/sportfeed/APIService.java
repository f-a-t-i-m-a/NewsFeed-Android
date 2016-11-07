package ir.appson.sportfeed;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by fatemeh on 11/6/2016.
 */
public interface APIService {

    @GET("/api/mobile/feed/list/all")
    Call<Feeds> feeds();
}


