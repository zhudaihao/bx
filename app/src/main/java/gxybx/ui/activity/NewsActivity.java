package gxybx.ui.activity;

import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import base.BaseActivity;
import butterknife.BindView;
import cn.gxybx.R;
import gxybx.view.CoolIndicator;


public class NewsActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.news_line)
    View news_line;

    @BindView(R.id.coolIndicator)
    CoolIndicator coolIndicator;


    @Override
    public int getResLayout() {
        return R.layout.activity_news;
    }

    @Override
    protected void initView() {
        rb_base_left.setVisibility(View.VISIBLE);

        int id = getIntent().getIntExtra("tag", 0);
        if (id != 1) {
            news_line.setVisibility(View.GONE);
        } else {
            news_line.setVisibility(View.VISIBLE);
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //适配手机大小
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setPluginState(WebSettings.PluginState.ON);

        if (Build.VERSION.SDK_INT >= 11) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 禁止硬件加速
        }
        webView.setBackgroundColor(Color.TRANSPARENT);

        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setUseWideViewPort(true); // a
            webSettings.setLoadWithOverviewMode(true);// b, a和b是成对使用的
        } else {
            webSettings.setSupportZoom(false);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        //去掉滚动条
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);


        //设置使用webView浏览网站
        webView.setWebViewClient(new WebViewClient() {
            //兼容坚果手机
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (error.getPrimaryError() == SslError.SSL_DATE_INVALID
                        || error.getPrimaryError() == SslError.SSL_EXPIRED
                        || error.getPrimaryError() == SslError.SSL_INVALID
                        || error.getPrimaryError() == SslError.SSL_UNTRUSTED) {
                    handler.proceed();
                } else {
                    handler.cancel();
                }
                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http") || url.startsWith("https"))
                    view.loadUrl(url);
                return true;
            }


        });


        //进度
        coolIndicator.setMax(100);
        //进度
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 99) {
                    coolIndicator.setVisibility(View.GONE);
                    coolIndicator.complete();
                } else {
                    coolIndicator.setVisibility(View.VISIBLE);
                    coolIndicator.start();
                }

            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                // return super.onJsAlert(view, url, message, result);
                showToast(message);
                result.confirm();//这里必须调用，否则页面会阻塞造成假死
                return true;

            }

            /**
             * 标题
             */
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title)) {
                    tv_base_title.setText(title);
                }
            }
        });

        rb_base_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = webView.canGoBack();
                if (b) {
                    webView.goBack();
                } else {
                    finishToActivity();
                }
            }
        });


        /**
         * 加载H5
         */
        String url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)) {
            webView.loadUrl(url);
        }

    }

}
