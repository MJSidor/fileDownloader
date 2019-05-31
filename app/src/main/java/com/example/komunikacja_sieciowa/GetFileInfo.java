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

    public GetFileInfo(MainActivity activity) {
        this.mainActivity = activity;
        fileSize = 0;
        fileType = null;
        this.mainActivity.setFileSizeLabel("");
        this.mainActivity.setFileTypeLabel("");
    }


    @Override
    protected String doInBackground(String... strings) {

        String www = strings[0];

        HttpURLConnection connection = null;

        try {
            URL url = new URL(www);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            fileSize = connection.getContentLength();
            fileType = connection.getContentType();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect();
        }

        return "";

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (fileSize > 0) {

            this.mainActivity.setFileSizeLabel(Integer.toString(fileSize));
            this.mainActivity.setFileTypeLabel(fileType);
        }
        else
        {
            this.mainActivity.setFileSizeLabel("Could not access\nspecified file");
            this.mainActivity.setFileTypeLabel("Could not access\nspecified file");
        }


    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
}
