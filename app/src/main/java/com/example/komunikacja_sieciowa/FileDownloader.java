package com.example.komunikacja_sieciowa;

import android.app.IntentService;
import android.content.Intent;

public class FileDownloader extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FileDownloader(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
