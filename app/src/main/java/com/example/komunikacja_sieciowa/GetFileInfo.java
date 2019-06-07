/**
 * Klasa służąca do pobierania asynchronicznego - w tle -
 * informacji nt. pliku przechowywanego na serwerze
 */

package com.example.komunikacja_sieciowa;

import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

public class GetFileInfo extends AsyncTask<String, String, String> {

    private int fileSize;
    private String fileType;
    private MainActivity mainActivity;

    /**
     * Konstruktor powiązujący obiekt klasy z główną aktywnością
     * @param activity
     */
    public GetFileInfo(MainActivity activity) {
        this.mainActivity = activity;
        fileSize = 0;
        fileType = null;
        this.mainActivity.setFileSizeLabel("");
        this.mainActivity.setFileTypeLabel("");
    }

    /**
     * Metoda wykonująca się podczas działania wątku w tle aplikacji
     * @param strings
     * @return
     */
    @Override
    protected String doInBackground(String... strings) {

        String www = strings[0];

        HttpURLConnection connection = null;

        try {
            // utwórz połączenie http z plikiem
            URL url = new URL(www);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            // pobierz rozmiar i typ pliku
            fileSize = connection.getContentLength();
            fileType = connection.getContentType();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect(); // zamknij połączenie
        }

        return "";

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Metoda wywoływana po zakończeniu wykonywania działania
     * @param s
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        // jeżeli rozmiar pliku > 0 (uzyskano do niego dostęp)
        if (fileSize > 0) {
            // ustaw odpowiednie wartości (rozmiar, typ pliku) w etykietach głównej aktywności
            this.mainActivity.setFileSizeLabel(Integer.toString(fileSize));
            this.mainActivity.setFileTypeLabel(fileType);
        } else {
            // w przeciwnym wypadku - umieść w etykietach informację o niepowodzeniu się próby dostępu do pliku
            this.mainActivity.setFileSizeLabel("Could not access\nspecified file");
            this.mainActivity.setFileTypeLabel("Could not access\nspecified file");
        }


    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
}
