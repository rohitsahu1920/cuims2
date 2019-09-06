package sahu.rohit.cuims2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class qrcode_activity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
    }


    @Override
    public void handleResult(Result result) {

        qrresult.result1.setText(result.getText());
        String qrresult = result.getText();

        Intent intent = new Intent(this, qrresult.class);
        intent.putExtra("qr",qrresult);
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();

        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }


}
