package com.westudio.wecampus.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by martian on 13-9-15.
 */
public class ImageUtil {

    public static final String IMAGE_NOT_FOUND = "http://wecampus.net/img/image_not_found.png";

    /**
     * Get Circle Bitmap
     * @param bitmap
     * @return
     */
    /*public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
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
        //canvas.translate(-25, -6);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }*/

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

    /**
     * 截取正方形bitmap
     * @param bitmap
     * @return
     */
    public static Bitmap cropCenterSquare(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        //截取正方形
        int wh = w > h ? h : w;
        int retX = w > h ? (w - h) / 2 : 0;
        int retY = w > h ? 0 : (h - w) / 2;
        return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
    }

    public static byte[] cropBitmap(Bitmap bitmap) {
        return cropBitmapToSize(bitmap, 32 * 1024);
    }

    /**
     * 截取缩略图到指定大小
     * @param source
     * @param maxSize
     * @return
     */
    public static byte[] cropBitmapToSize(Bitmap source, int maxSize) {
        Bitmap bm = cropCenterSquare(source);
        int w = bm.getWidth();
        int h = bm.getHeight();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        // 额外多减去1K，防止超出范围
        if (b.length > (maxSize)) {
            double i = b.length / (maxSize - 1024);

            double newWidth = w / Math.sqrt(i);
            double newHeight = h / Math.sqrt(i);
            Matrix matrix = new Matrix();
            float scaleWidth = ((float) newWidth) / w;
            float scaleHeight = ((float) newHeight) / h;
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap newBm = Bitmap.createBitmap(bm, 0, 0, w,
                    h, matrix, true);
            baos.reset();
            newBm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        }

        return baos.toByteArray();
    }

    /**
     * 旋转目标图片并保存
     * @param uri 图片uri
     * @param rotation 旋转角度
     */
    public static void rotateImage(Uri uri, float rotation) throws IOException {
        Bitmap source = BitmapFactory.decodeFile(uri.getPath());
        Bitmap target = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(target);
        Matrix matrix = new Matrix();
        matrix.setRotate(rotation, source.getWidth() / 2 ,source.getHeight() / 2);
        canvas.drawBitmap(source, matrix, new Paint());
        FileOutputStream fos = new FileOutputStream(new File(uri.getPath()));
        target.compress(Bitmap.CompressFormat.JPEG, 90, fos);
        fos.close();
    }
}
