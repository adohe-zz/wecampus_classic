package com.westudio.wecampus.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.provider.Browser;
import android.text.ParcelableSpan;
import android.text.style.ClickableSpan;
import android.view.View;

import com.westudio.wecampus.ui.base.WebBrowserActivity;

/**
 * Created by martian on 13-12-1.
 */
public class ContentURLSpan extends ClickableSpan implements ParcelableSpan {
    private final String mURL;

    public ContentURLSpan(String url) {
        mURL = url;
    }

    public ContentURLSpan(Parcel src) {
        mURL = src.readString();
    }

    @Override
    public int getSpanTypeId() {
        return 11;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mURL);
    }

    public String getURL() {
        return mURL;
    }

    @Override
    public void onClick(View view) {
        Uri uri = Uri.parse(getURL());
        Context context = view.getContext();
        if (uri.getScheme().startsWith("http")) {
            Intent intent = new Intent(context, WebBrowserActivity.class);
            intent.putExtra(WebBrowserActivity.EXTRA_URL, uri.toString());
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.getPackageName());
            context.startActivity(intent);
        }
    }
}
