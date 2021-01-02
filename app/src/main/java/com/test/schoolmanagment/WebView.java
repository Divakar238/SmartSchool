package com.test.schoolmanagment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


public class WebView extends AppCompatActivity {

    private android.webkit.WebView webView;
    int position;
    ProgressBar progressBar;
    String quizlink,linke,linkk,chatbot;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_web_view);
        webView = (android.webkit.WebView) findViewById(R.id.webview);

        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }


        webView.setWebViewClient(new WebViewClient());


        WebSettings webSettings = webView.getSettings();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSaveFormData(false);
        webSettings.setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setEnableSmoothTransition(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        progressBar=findViewById(R.id.progressbar_Horizontal);
        webView.setWebChromeClient(new ChromeClient());
        Intent intent = getIntent();
        Intent i=getIntent();
        Intent g=getIntent();
        try {
             quizlink = intent.getExtras().getString("quizlin");
             linke = intent.getExtras().getString("classlin");
             linkk = i.getExtras().getString("classlinn");
            chatbot = g.getExtras().getString("livechat");
        }
        catch (NullPointerException e){
            Toast.makeText(this, "Error:404", Toast.LENGTH_SHORT).show();
        }
        webView.loadUrl(chatbot);
        webView.loadUrl(linke);
        webView.loadUrl(linkk);
        webView.loadUrl(quizlink);
        webView.setVisibility(View.VISIBLE);
        webView.setWebChromeClient(new WebChromeClient() {


            // this will be called on page loading progress

            @Override

            public void onProgressChanged(android.webkit.WebView view, int newProgress) {

                super.onProgressChanged(view, newProgress);


                progressBar.setProgress(newProgress);
                //loadingTitle.setProgress(newProgress);
                // hide the progress bar if the loading is complete

                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else{
                    progressBar.setVisibility(View.VISIBLE);

                }

            }

        });

    }

    private class ChromeClient extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        ChromeClient() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
    @Override
    public void onBackPressed () {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


}




