package com.westudio.wecampus.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.widget.TextView;
import android.text.method.LinkMovementMethod;

/**
 * Created by martian on 13-12-1.
 * 用于处理活动正文的超链接
 */
public class ContentUtil {

    public static void addLinks(TextView view) {
        CharSequence content = view.getText();
        view.setText(convertToSpannableString(content.toString()));
        if (view.getLinksClickable()) {
            view.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public static void addLinks(TextView view, String url) {
        CharSequence content = view.getText();
        view.setText(convertToSpannableString(url));
        if (view.getLinksClickable()) {
            view.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private static SpannableString convertToSpannableString(String txt) {
        String hackTxt;
        if (txt.startsWith("[") && txt.endsWith("]")) {
            hackTxt = txt + " ";
        } else {
            hackTxt = txt;
        }

        SpannableString value = SpannableString.valueOf(txt);
        Linkify.addLinks(value, ContentLinkPatterns.WEB_URL, ContentLinkPatterns.WEB_SCHEME, new Linkify.MatchFilter() {
            @Override
            public boolean acceptMatch(CharSequence charSequence, int i, int i2) {
                if (charSequence.toString().contains("@")) {
                    return false;
                }
                return true;
            }
        }, null);

        URLSpan[] urlSpans = value.getSpans(0, value.length(), URLSpan.class);
        ContentURLSpan contentURLSpan = null;
        for (URLSpan urlSpan : urlSpans) {
            contentURLSpan = new ContentURLSpan(urlSpan.getURL());
            int start = value.getSpanStart(urlSpan);
            int end = value.getSpanEnd(urlSpan);
            value.removeSpan(urlSpan);
            value.setSpan(contentURLSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return value;
    }
}
