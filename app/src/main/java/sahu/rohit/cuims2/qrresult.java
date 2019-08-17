package sahu.rohit.cuims2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class qrresult extends AppCompatActivity {

    AdView mAdView;
    Button button;
    @SuppressLint("StaticFieldLeak")
    public static TextView result1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrresult);

        MobileAds.initialize(this, "ca-app-pub-7298114047017115~7707992726");
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-7298114047017115/5132071900");

        mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        button = findViewById(R.id.scan);
        result1 = findViewById(R.id.result);

        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            String value = extras.getString("qr");

            result1.setText(value);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "There is Not text", Toast.LENGTH_SHORT).show();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),qrcode_activity.class);
                startActivity(intent);
            }
        });

    }
}
