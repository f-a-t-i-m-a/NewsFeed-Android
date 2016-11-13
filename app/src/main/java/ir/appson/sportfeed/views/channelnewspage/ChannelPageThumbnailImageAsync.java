package ir.appson.sportfeed.views.channelnewspage;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import ir.appson.sportfeed.R;
import ir.appson.sportfeed.util.ImageHelper;

/**
 * Created by fatemeh on 9/12/2015.
 */
public class ChannelPageThumbnailImageAsync extends AsyncTask<Object, Object, Bitmap> {
    ImageView imageView;

    @Override
    protected Bitmap doInBackground(Object... params) {
        imageView = (ImageView) params[0];
//        FeedSummary newsDetailObject = (FeedSummary) params[1];
//        if (newsDetailObject.getNewsImageId()<=0)
        return null;
//        String sessionId = ((Application9090)imageView.getContext().getApplicationContext()).getSessionId();
//        Bitmap result = NewsFeedProxy.getNewsImageThumbnail(newsDetailObject.getNewsImageId(), sessionId);
//        return result;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
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
