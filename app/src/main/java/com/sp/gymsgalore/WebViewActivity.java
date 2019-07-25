package com.sp.gymsgalore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private WebView webView2;
    private TabHost host;
    private Boolean check;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.webview);

        webView = (WebView) findViewById(R.id.web);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.com/search?rlz=1C1CHBF_enSG794SG794&ei=AWI4Xeb-G8Kq8QOMhbCADA&q=gym+exercises+for+beginners+&oq=gym+exercises+for+beginners+&gs_l=psy-ab.3..0l3j0i22i30l7.13027.28968..29253...34.0..0.214.3111.54j0j1......0....1..gws-wiz.....10..0i71j35i39j0i131j0i67j0i20i263j0i131i20i263j0i131i67j0i10j33i21j33i160j0i13j0i13i30.GK7e4AwZHCY&ved=0ahUKEwimg8ur2c3jAhVCVXwKHYwCDMAQ4dUDCAo&uact=5");

        webView2 = (WebView) findViewById(R.id.webview2);
        webView2.setWebViewClient(new WebViewClient());
        webView2.loadUrl("https://www.fitnessblender.com/");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        String databasePath = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(databasePath);

        host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec  = host.newTabSpec("Tab 1");
        spec.setContent(R.id.days_tab);
        spec.setIndicator("Tab 1");
        host.addTab(spec);

        //Tab2
        spec = host.newTabSpec("Tab 2");
        spec.setContent(R.id.notes_tab);
        spec.setIndicator("Tab 2");
        host.addTab(spec);

        host.setCurrentTab(0); //Set default tab to be Tab 1

        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Log.i("CREATION" , "we clicked here");
                System.out.println("we clicked here");
                switch(id)
                {
                    case R.id.list:
                        //System.exit(1);
                        Intent list2 = new Intent(WebViewActivity.this, DailyList.class);
                        startActivity(list2);
                        break;
                    case R.id.notes:
                        Intent notes3 = new Intent(WebViewActivity.this, NoteList.class);
                        startActivity(notes3);
                        break;
                    case R.id.music:
                        Intent music2 = new Intent(WebViewActivity.this, MusicPlayerActivity.class);
                        startActivity(music2);
                        break;
                    case R.id.alarm:
                        /*Intent alarmWV = new Intent(WebViewActivity.this, MusicActivity.class);
                        startActivity(alarmWV);*/
                        System.out.println("we clicked here");
                        Intent alarmIntent = new Intent(WebViewActivity.this, Alarm.class);
                        startActivity(alarmIntent);
                        break;
                    case R.id.exit:
                        moveTaskToBack(true);
                        System.exit(0);
                        break;
                    default: break;
                }
                return true;
            }
        });

    }

    public void onBackPressed1() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void onBackPressed2() {
        if (webView2.canGoBack()) {
            webView2.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private void onForwardPressed1() {
        if (webView.canGoForward()) {
            webView.goForward();
        } else {
            Toast.makeText(this, "Can't go any further",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void onForwardPressed2() {
        if (webView2.canGoForward()) {
            webView2.goForward();
        } else {
            Toast.makeText(this, "Can't go any further",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.weboptions, menu);
                return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                case (R.id.menu_back1):
                    onBackPressed1();
                    break;

                case (R.id.menu_back2):
                    onBackPressed2();
                    break;

                case (R.id.menu_refresh):
                    webView.reload();
                    webView2.reload();
                    break;

                case (R.id.menu_forward1):
                    onForwardPressed1();
                    break;

                case (R.id.menu_forward2):
                    onForwardPressed2();
                    break;
        }
        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
