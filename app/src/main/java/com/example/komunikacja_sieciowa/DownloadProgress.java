package com.example.komunikacja_sieciowa;

import android.os.Parcel;
import android.os.Parcelable;

public class DownloadProgress implements Parcelable {

    public int bytesDownloaded;
    public int size;
    public int finished;

    public DownloadProgress() {
        bytesDownloaded = 0;
        size = 0;
        finished = 0;
    }

    protected DownloadProgress(Parcel in) {
        bytesDownloaded = in.readInt();
        size = in.readInt();
        finished = in.readInt();
    }

    public static final Creator<DownloadProgress> CREATOR = new Creator<DownloadProgress>() {
        @Override
        public DownloadProgress createFromParcel(Parcel in) {
            return new DownloadProgress(in);
        }

        @Override
        public DownloadProgress[] newArray(int size) {
            return new DownloadProgress[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(bytesDownloaded);
        dest.writeInt(size);
        dest.writeInt(finished);
    }
}
