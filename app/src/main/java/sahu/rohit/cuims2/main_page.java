package sahu.rohit.cuims2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

public class main_page extends AppCompatActivity {

    Button notes,cuims,many,qrcode;
    TextView welcome;
    AdView mAdView;
    LinearLayout mainpage;
    boolean isChromeAppInstalled = false;
    String chromePackageName = "com.android.chrome";
    String result = "false";
    int color = Color.rgb(0, 0, 0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        mainpage = findViewById(R.id.mainpage);
        notes = findViewById(R.id.notes);
        cuims = findViewById(R.id.cuims);
        many = findViewById(R.id.soon);
        welcome = findViewById(R.id.welcome);
        qrcode = findViewById(R.id.qr_code);


        MobileAds.initialize(this, "ca-app-pub-7298114047017115~7707992726");
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-7298114047017115/2788931864");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Welcome Chandigarh University :)", Toast.LENGTH_SHORT).show();
            }
        });

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main_page.this,notes_activity.class);
                startActivity(intent);
            }
        });

        cuims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean ans = check(chromePackageName);
                if (ans == false)
                {
                    Intent intent = new Intent(main_page.this,cuims_activity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                }
                else
                {
                    Uri uri = Uri.parse("https://uims.cuchd.in/uims/");
                    CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                    intentBuilder.setToolbarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                    intentBuilder.addDefaultShareMenuItem();
                    CustomTabsIntent customTabsIntent = intentBuilder.build();
                    customTabsIntent.launchUrl(getApplicationContext(), uri);
                }

            }
        });

        many.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Toast.makeText(getApplicationContext(),"Many more to coming soon...", Toast.LENGTH_SHORT).show();
            }
        });

        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main_page.this, qrresult.class);
                startActivity(intent);

            }
        });

    }



    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_page, menu);
        return true;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public boolean  onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.about:
                Intent intent = new Intent(main_page.this,about.class);
                startActivity(intent);
                return true;

            case  R.id.white:
                color = Color.rgb(255,255,255);
                mainpage.setBackgroundColor(color);
                return true;

            case R.id.red:
                color = Color.rgb(255,0,0);
                mainpage.setBackgroundColor(color);
                return true;

            case  R.id.green:
                color = Color.rgb(0,255,0);
                mainpage.setBackgroundColor(color);
                return true;

            case R.id.black:
                color = Color.rgb(0,0,0);
                mainpage.setBackgroundColor(color);
                return true;

            case R.id.orange:
                color = Color.rgb(255,128,0);
                mainpage.setBackgroundColor(color);
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public boolean check(String chromePackageName)
    {
        try {
            PackageManager pm = getApplicationContext().getPackageManager();
            @SuppressLint("WrongConstant") List<PackageInfo> list = pm.getInstalledPackages(PackageManager.MATCH_DEFAULT_ONLY);
            if ( list != null && 0 < list.size()) {
                for (PackageInfo info : list) {
                    if (chromePackageName.equals(info.packageName)) {
                        isChromeAppInstalled = true;
                        result = "true";
                        break;
                    }
                }
            }
        } catch (Exception ex) {}

       return isChromeAppInstalled;
    }

    /*public void chec2()
    {
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setShowTitle(true);
        final CustomTabsIntent customTabsIntent = intentBuilder.build();
        final List<ResolveInfo> customTabsApps = this.getPackageManager().queryIntentActivities(customTabsIntent, 0);

        if (customTabsApps.size() > 0) {
            CustomTabActivityHelper.openCustomTab(this, customTabsIntent, Uri.parse("https://uims.cuchd.in/uims/"), new cuims_activity());
        }
        else {
            // Chrome not installed. Display a toast or something to notify the user
        }
    }*/


}
