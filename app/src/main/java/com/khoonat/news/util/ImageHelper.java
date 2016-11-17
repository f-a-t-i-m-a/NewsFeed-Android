package com.khoonat.news.util;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class ImageHelper {
    //FF makes the bitmap rounded
    public static Bitmap getRoundedCornerBitmap(ImageView imageView){
        Bitmap bm=((BitmapDrawable)imageView.getDrawable()).getBitmap();
        return getRoundedCornerBitmap(bm, imageView.getContext().getResources().getDisplayMetrics());
    }
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, DisplayMetrics metrics) {

        //If you want to change the thumbnails' size just change these numbers below
//        bitmap = Bitmap.createScaledBitmap(bitmap, 90, 90, false);

        int px = 70 * metrics.densityDpi / 160;

        bitmap = Bitmap.createScaledBitmap(bitmap, px, px, false);
        int radius = bitmap.getWidth()/2;

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = radius;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}