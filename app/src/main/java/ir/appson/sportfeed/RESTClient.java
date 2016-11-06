package ir.appson.sportfeed;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import ir.appson.sportfeed.proxy.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

/**
 * Created by fatemeh on 11/6/2016.
 */

public class RESTClient{

    private static RESTClient instance = null;

    private ResultReadyCallback callback;

    private static final String BASE_URL = "https://news.khoonat.com";
    private APIService service;
    List<User> users = null;
    boolean success = false;


    public RESTClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        service = retrofit.create(APIService.class);
    }

    public Object getUsers() {
        Call<Object> userlist = service.users();
        userlist.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
//                    users = response.body();
                    callback.resultReady(users);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("REST", t.getMessage());
            }

        });
        return null;
    }

    public void setCallback(ResultReadyCallback callback) {
        this.callback = callback;
    }


    public static RESTClient getInstance() {
        if(instance == null) {
            instance = new RESTClient();
        }
        return instance;
    }

    public interface ResultReadyCallback {
        public void resultReady(List<User> users);
    }

}
