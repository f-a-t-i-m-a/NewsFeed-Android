package ir.appson.sportfeed.views.detail;

import com.google.gson.annotations.SerializedName;

import ir.appson.sportfeed.dto.FeedDetail;
import ir.appson.sportfeed.dto.News;
import ir.appson.sportfeed.dto.NewsDetail;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by fatemeh on 11/6/2016.
 */
public interface NewsDetailAPIService {

    @GET("/api/mobile/news/18/details")
    Call<News> detail();
}


