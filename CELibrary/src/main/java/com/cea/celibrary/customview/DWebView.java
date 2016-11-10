package com.cea.celibrary.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by Devin on 2016/2/24.
 */
public class DWebView extends WebView {

    public static final String URL = "url";
    public static final String TITLE = "title";
    private final String userAgent = "Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; IM-A900S Build/JDQ39) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 ShareJobCompany/";
    private String version;
    private ProgressBar progressBar;

    public DWebView(Context context) {
        super(context);
    }

    public DWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    public void init(ProgressBar progressBar,String version){

        this.progressBar = progressBar;

        WebSettings settings = getSettings();
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMinimumFontSize(settings.getMinimumFontSize() + 8);
        settings.setAllowFileAccess(false);
        settings.setTextSize(WebSettings.TextSize.NORMAL);

        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        getSettings().setUserAgentString(userAgent + version);

        setWebChromeClient(new WebProgressClient());
    }


    class WebProgressClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            progressBar.setProgress(newProgress);
            if (newProgress == 100){
                progressBar.setVisibility(View.GONE);

            }
            super.onProgressChanged(view,newProgress);
        }
    }


}
