package ir.appson.sportfeed;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

/**
 * Created by fatemeh on 11/6/2016.
 */
public interface APIService {
    @POST("/api/user")
    Call<User> createUser(@Body User user);

    @GET("/api/mobile/feed/list/all")
    Call<Object> users();
}


