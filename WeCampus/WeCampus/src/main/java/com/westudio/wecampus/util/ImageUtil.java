package com.westudio.wecampus.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

/**
 * Created by martian on 13-9-15.
 */
public class ImageUtil {

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        int x = bitmap.getWidth();
        Bitmap output = Bitmap.createBitmap(x,
                x, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        // draw a rectangle
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        paint.setColor(color);
        // draw a circle
        canvas.drawCircle(x/2, x/2, x/2, paint);
        canvas.translate(-25, -6);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
