package com.westudio.wecampus.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;

/**
 * Created by martian on 13-9-15.
 */
public class ImageUtil {

    /**
     * Get Circle Bitmap
     * @param bitmap
     * @return
     */
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

    /**
     * Get the size of a bitmap
     * @param bitmap
     * @return
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }

        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * Resize bitmap
     * @param bitmap
     * @param height
     * @param width
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, final int height, final int width) {
        int oldHeight = bitmap.getHeight();
        int oldWidth = bitmap.getWidth();

        float scaleHeight = ((float)height) / oldHeight;
        float scaleWidth = ((float)width) / oldWidth;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
}
