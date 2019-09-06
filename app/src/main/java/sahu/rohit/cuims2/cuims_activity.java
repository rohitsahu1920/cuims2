package sahu.rohit.cuims2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class cuims_activity extends AppCompatActivity {

    private String address = "https://uims.cuchd.in/uims/";
    private WebView webView;
    private ProgressBar progressBar;
    private FrameLayout frameLayout;
    private SwipeRefreshLayout mySwipe;

    @SuppressLint({"SetJavaScriptEnable", "SetJavaScriptEnabled"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuims_activity);

        mySwipe = findViewById(R.id.swipe_to_refrsh);
        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setMax(100);
        frameLayout = findViewById(R.id.frame_layout);
        webView.setWebViewClient(new HelpClient());

        webView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view,int progress){
                frameLayout.setVisibility(View.VISIBLE);
                progressBar.setProgress(progress);
                setTitle("Loading...");

                if (progress == 100)
                {
                    frameLayout.setVisibility(View.GONE);
                    setTitle(view.getTitle());
                }
                super.onProgressChanged(view, progress);
            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url,
                                        String userAgent,
                                        String contentDisposition,
                                        String mimetype, long contentLength) {

                DownloadManager.Request myRequest = new DownloadManager.Request(Uri.parse(url));
                myRequest.allowScanningByMediaScanner();
                myRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                DownloadManager myManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                myManager.enqueue(myRequest);

                Toast.makeText(getApplicationContext(), "Your file is Downloading...", Toast.LENGTH_SHORT).show();

            }
        });


        mySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mySwipe.setEnabled(false);
                webView.reload();
                mySwipe.setEnabled(true);
                mySwipe.setRefreshing(false);
            }
        });

        this.webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.loadUrl(address);
        webView.getSettings().setSavePassword(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);
        webView.getSettings().setAllowFileAccess(true);

        progressBar.setProgress(10);

        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        this.webView.getSettings().setDomStorageEnabled(true);

        WebView webview = new WebView(this);
        WebSettings ws = webview.getSettings();
        ws.setSaveFormData(true);
        ws.setSavePassword(true);


        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // chromium, enable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.browser, menu);
        return true;
    }

    private class HelpClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            frameLayout.setVisibility(View.VISIBLE);
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
            {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_backword:
                if (webView.canGoBack()) {
                    webView.goBack();
                }
                return true;

            case R.id.item_forword:
                if (webView.canGoForward()) {
                    webView.goForward();
                }
                return true;

            case R.id.item_home:
                webView.loadUrl("https://uims.cuchd.in/uims/");
                return true;

            case R.id.item_google:
                webView.loadUrl("https://www.pdfdrive.com/");
                return true;

            case R.id.item_mu:
                webView.loadUrl("http://www.cuchd.in");
                return true;

            case R.id.item_tp:
                webView.loadUrl("http://www.tutorialspoint.com");
                return true;

            case R.id.google:
                webView.loadUrl("https://www.google.com/");
                return true;

            case R.id.rate:
                webView.loadUrl("https://play.google.com/store/apps/details?id=sahu.rohit.cuims2&hl=en");
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
