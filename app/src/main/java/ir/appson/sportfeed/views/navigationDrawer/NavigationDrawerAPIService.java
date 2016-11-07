package ir.appson.sportfeed.views.navigationDrawer;

import ir.appson.sportfeed.proxy.dto.Feeds;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by fatemeh on 11/6/2016.
 */
public interface NavigationDrawerAPIService {

    @GET("/api/mobile/feed/list/all")
    Call<Feeds> feeds();
}


