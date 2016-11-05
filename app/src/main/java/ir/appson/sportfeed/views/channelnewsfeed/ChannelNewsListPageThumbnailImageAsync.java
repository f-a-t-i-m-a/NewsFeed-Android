package ir.appson.sportfeed.views.channelnewsfeed;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import ir.appson.sportfeed.Application9090;
import ir.appson.sportfeed.NewsFeedProxy;
import ir.appson.sportfeed.R;
import ir.appson.sportfeed.proxy.dto.NewsDetailObject;
import ir.appson.sportfeed.util.ImageHelper;

/**
 * Created by fatemeh on 9/12/2015.
 */
public class ChannelNewsListPageThumbnailImageAsync extends AsyncTask<Object, Object, Bitmap> {
    ImageView imageView;
    @Override
    protected Bitmap doInBackground(Object... params) {
        imageView = (ImageView) params[0];
        NewsDetailObject newsDetailObject = (NewsDetailObject) params[1];
        if (newsDetailObject.getNewsImageId()<=0)
            return null;
//        SharedPreferences prefs = imageView.getContext().getApplicationContext().getSharedPreferences("ir.appson.sportfeed", Context.MODE_PRIVATE);
//        String sessionId = prefs.getString("sessionId", "");
        String sessionId = ((Application9090)imageView.getContext().getApplicationContext()).getSessionId();
        Bitmap result = NewsFeedProxy.getNewsImageThumbnail(newsDetailObject.getNewsImageId(), sessionId);
        return result;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap){
        super.onPostExecute(bitmap);
        if (bitmap == null) {
            imageView.setImageResource(R.drawable.thumbnail_noimage);
            imageView.setImageBitmap(ImageHelper.getRoundedCornerBitmap(imageView));
            return;
        }

        DisplayMetrics metrics = imageView.getContext().getResources().getDisplayMetrics();
        imageView.setImageBitmap(ImageHelper.getRoundedCornerBitmap(bitmap, metrics));

        return;
    }

}
