package com.westudio.wecampus.ui.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.westudio.wecampus.R;

/**
 * Created by martian on 13-12-1.
 */
public class WebBrowserActivity extends SherlockFragmentActivity {
    public static final String EXTRA_URL = "url";

    private String mUrl;
    private WebView mWebView;
    private ProgressBar mProgressBar;
    private boolean mIsWebViewAvailable;
    private MenuItem mRefreshItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSherlock().setUiOptions(ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
        setContentView(R.layout.activity_webbrowser);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setIcon(new ColorDrawable(Color.TRANSPARENT));
        int splitBackground = getResources().getColor(R.color.split_action_background);
        actionBar.setSplitBackgroundDrawable(new ColorDrawable(splitBackground));

        mUrl = getIntent().getStringExtra(EXTRA_URL);

        if (mWebView != null) {
            mWebView.destroy();
        }

        mWebView = (WebView) findViewById(R.id.webView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mWebView.setOnKeyListener(new View.OnKeyListener() {


            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                }
                return false;
            }

        });
        mWebView.setWebViewClient(new InnerWebViewClient());
        mWebView.setWebChromeClient(new InnerWebChromeClient());
        mIsWebViewAvailable = true;
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        //settings.setDisplayZoomControls(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.webbrowser, menu);
        mRefreshItem = menu.findItem(R.id.refresh);
        mWebView.loadUrl(mUrl);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.back:
                getWebView().goBack();
                break;
            case R.id.forward:
                getWebView().goForward();
                break;
            case R.id.refresh:
                getWebView().reload();
                break;
            case R.id.copy_link:
                ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                cmb.setText(mUrl);
                Toast.makeText(this, R.string.url_is_copied, Toast.LENGTH_SHORT).show();
                break;
            case R.id.open_in_browser:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mUrl));
                startActivity(Intent.createChooser(intent, getString(R.string.open_in_browser)));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadUrl(String url) {
        if (mIsWebViewAvailable) {
            getWebView().loadUrl(mUrl = url);
        }
    }

    public WebView getWebView() {
        return mIsWebViewAvailable ? mWebView : null;
    }

    private class InnerWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //startRefreshAnimation();
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            ActionBar actionBar = getSupportActionBar();
            if (actionBar == null)
                return;
            if (!TextUtils.isEmpty(view.getTitle()))
                actionBar.setTitle(view.getTitle());
            //finishRefreshAnimation();
        }
    }

    private class InnerWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String sTitle) {
            super.onReceivedTitle(view, sTitle);
            if (sTitle != null && sTitle.length() > 0) {

                ActionBar actionBar = getSupportActionBar();
                if (actionBar == null)
                    return;
                if (!TextUtils.isEmpty(view.getTitle()))
                    actionBar.setTitle(view.getTitle());
            }
        }

        public void onProgressChanged(WebView view, int progress) {
            if (!mProgressBar.isShown())
                mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setProgress(progress);
            if (progress == 100)
                mProgressBar.setVisibility(View.INVISIBLE);
        }
    }
}
