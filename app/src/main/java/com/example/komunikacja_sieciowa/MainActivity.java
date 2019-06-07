package com.example.komunikacja_sieciowa;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private String www;
    private int MY_PERMISSIONS_REQUEST_INTERNET = 47, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 48;

    /**
     * Obiekt klasy BroadcastReceiver służący do odbierania przesyłanych (transmitowanych) danych nt. pobierania
     */
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getExtras();
            DownloadProgress progress = bundle.getParcelable(FileDownloader.INFO);
            
            TextView downloadProgressLabel = findViewById(R.id.textViewDownloaderValue);
            String downloaded = Integer.toString(progress.bytesDownloaded);
            String size = Integer.toString(progress.size);
            int finished = progress.finished;
            ProgressBar progressBar = findViewById(R.id.determinateBar);
            TextView percentageLabel = findViewById(R.id.textViewDownloadPercentage);
            float percent;

            // dopóki status pobierania pliku wskazuje na nieukończony
            if (finished == 0) {
                percent = 100.0f * progress.bytesDownloaded / progress.size;
                // oblicz procent ukończenia pobierania i zaktualizuj pasek postępu oraz etykietę z wartością wyświetlaną tekstowo
                progressBar.setProgress((int) percent);
                percentageLabel.setText(String.format("%.1f",percent ) + "%");
            }

            downloadProgressLabel.setText(downloaded + "/" + size); // zaktualizuj etykietę wyświetlającą ilość pobranych danych i rozmiar
            if (finished == 1) {
                // po zakończeniu pobierania - wyświetl odpowiedni komunikat i wartość procentową pobierania
                Toast.makeText(getApplicationContext(), "Download finished", Toast.LENGTH_SHORT).show();
                percentageLabel.setText("100%");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListeners();
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_INTERNET);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.INTERNET},
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);


    }

    /**
     * Metoda pomocnicza generująca listenery na odpowiednich przyciskach
     */
    public void addListeners() {
        Button buttonGetFileInfo = findViewById(R.id.buttonGetInfo);
        buttonGetFileInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView url = findViewById(R.id.editTextURL);
                www = url.getText().toString();
                GetFileInfo fileInfo = new GetFileInfo(MainActivity.this);
                fileInfo.execute(new String[]{www});

            }
        });

        Button buttonDownload = findViewById(R.id.buttonGetFile);
        buttonDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView url = findViewById(R.id.editTextURL);
                www = url.getText().toString();
                FileDownloader.runService(MainActivity.this, www);
                Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Metoda służąca do ustawiania tekstu w etykiecie przechowującej rozmiar pliku
     * @param value
     */
    public void setFileSizeLabel(String value) {
        TextView fileSize = findViewById(R.id.textViewFileSizeValue);
        fileSize.setText(value);
    }

    /**
     * Metoda służąca do ustawiania tekstu w etykiecie przechowującej typ pliku
     * @param value
     */
    public void setFileTypeLabel(String value) {
        TextView fileType = findViewById(R.id.textViewFileTypeValue);
        fileType.setText(value);
    }

    @Override // zarejestrowanie odbiorcy
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver, new IntentFilter(
                FileDownloader.RECEIVER));
    }

    @Override // wyrejestrowanie odbiorcy
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
    }


}
