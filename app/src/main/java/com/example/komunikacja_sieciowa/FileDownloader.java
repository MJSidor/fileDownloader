package com.example.komunikacja_sieciowa;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloader extends IntentService {

    private int bytesDownloaded;

    private static final String ACTION_DOWNLOAD = "com.example.intent_service.action.download";
    private static final String PARAMETER1 =
            "com.example.intent_service.extra.url";


    public FileDownloader() {
        super("FileDownloader");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FileDownloader(String name) {
        super(name);
    }

    public static void runService(Context context, String parameter) {
        Intent intent = new Intent(context, FileDownloader.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(PARAMETER1, parameter);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {
                final String url = intent.getStringExtra(PARAMETER1);

                execute(url);
            } else {
                Log.e("intent_service", "unknown action");
            }
        }
        Log.d("intent_service", "intent executed successfully");
    }

    private void execute(String strUrl) {
        FileOutputStream outStream = null;
        HttpURLConnection connection = null;
        InputStream inStream = null;

        bytesDownloaded = 0;
        try {
            URL url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);

            File tempFile = new File(url.getFile());
            File outFile = new File(
                    Environment.getExternalStorageDirectory()
                            + File.separator + tempFile.getName());
            if (outFile.exists()) outFile.delete();


            DataInputStream reader = new DataInputStream(connection.getInputStream());
            outStream = new FileOutputStream(outFile.getPath());
            int BLOCK_SIZE = 128;
            byte buffer[] = new byte[BLOCK_SIZE];
            int downloaded = reader.read(buffer, 0, BLOCK_SIZE);
            while (downloaded != -1) {
                outStream.write(buffer, 0, downloaded);
                bytesDownloaded += downloaded;
                downloaded = reader.read(buffer, 0, BLOCK_SIZE);
                Log.d("downloading file:" + outFile.getName(), ": " + Integer.toString(downloaded) + " bytes");
            }
            Log.d("downloaded file: " + outFile.getPath() + outFile.getName(), ": " + Integer.toString(bytesDownloaded) + " bytes");

        } catch (Exception e) {
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) connection.disconnect();
        }

    }

}
