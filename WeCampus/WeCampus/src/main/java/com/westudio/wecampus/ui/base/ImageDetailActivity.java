package com.westudio.wecampus.ui.base;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.westudio.wecampus.R;
import com.westudio.wecampus.net.WeCampusApi;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by martian on 13-11-19.
 */
public class ImageDetailActivity extends SherlockFragmentActivity {
    public static final String KEY_IMAGE_URL = "image_url";

    private ImageView image;
    private PhotoViewAttacher attacher;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        url = getIntent().getStringExtra(KEY_IMAGE_URL);

        image = (ImageView) findViewById(R.id.image);
        attacher = new PhotoViewAttacher(image);

        final Drawable defaultDrawable = new ColorDrawable(Color.rgb(229, 255, 255));

        WeCampusApi.requestImage(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if (imageContainer.getBitmap() != null) {
                    image.setImageBitmap(imageContainer.getBitmap());
                    attacher.update();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                image.setImageDrawable(defaultDrawable);
                attacher.update();
            }
        });


    }
}
