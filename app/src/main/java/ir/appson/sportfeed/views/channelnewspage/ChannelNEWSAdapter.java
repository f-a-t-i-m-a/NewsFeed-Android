package ir.appson.sportfeed.views.channelnewspage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ir.appson.sportfeed.R;
import ir.appson.sportfeed.dto.ChannelNEWSObject;

import java.util.ArrayList;

/**
 * Created by fatemeh on 11/7/2016.
 */
public class ChannelNEWSAdapter extends ArrayAdapter<ChannelNEWSObject> {
    Context context;
    ArrayList<ChannelNEWSObject> list;

    public ChannelNEWSAdapter(Context context, int resource, int textViewResourceId, ArrayList<ChannelNEWSObject> list) {
        super(context, resource, textViewResourceId, list);
        this.context = context;
        this.list = list;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_row, parent, false);
        ChannelNEWSObject newsDetail = list.get(position);
                /*DO NOT DELETE*/

/*
        ImageView imageView = (ImageView) row.findViewById(R.id.imageViewNewsThumbnail);
*/
        TextView title = (TextView) row.findViewById(R.id.textViewTitle);
        TextView newsResource = (TextView) row.findViewById(R.id.textView_single_row_newsResource);
        TextView dateTime = (TextView) row.findViewById(R.id.textView_single_row_dateTime);
        title.setPadding(0, 15, 10, 0);
        title.setTextColor(Color.BLACK);
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.thumbnail_loading);
                /*DO NOT DELETE*/

/*        imageView.setImageBitmap(ImageHelper.getRoundedCornerBitmap(bitmap, getContext().getResources().getDisplayMetrics()));*/
        //        new ChannelPageThumbnailImageAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, imageView, newsDetailObject);
        title.setText(newsDetail.Title);
        //        newsResource.setText(newsDetailObject.getNewsReference());
        newsResource.setPadding(0, 15, 10, 0);
        //        dateTime.setText(newsDetailObject.getJalaliDateTime());
        dateTime.setPadding(0, 15, 10, 0);
        //FF here we setTag to the newsId so we can retrieve it in itemClickListener and pass it to the DetailNEWSActivity.
        row.setTag(newsDetail.ID);
        return row;
    }
}
