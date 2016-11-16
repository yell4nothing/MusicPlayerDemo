package com.dd.musicplayerdemo.net;

import android.os.AsyncTask;

import com.dd.musicplayerdemo.callback.ImageCallback;
import com.dd.musicplayerdemo.utils.HttpUtils;

/**
 * Created by my on 2016/11/5.
 */
public class ImageAsyncTask extends AsyncTask<String,Void,byte[]>{

    private ImageCallback imageCallback;

    public ImageAsyncTask(ImageCallback imageCallback) {
        this.imageCallback = imageCallback;
    }

    @Override
    protected byte[] doInBackground(String... params) {
        return HttpUtils.loadByte(params[0]);
    }

    @Override
    protected void onPostExecute(byte[] data) {
        super.onPostExecute(data);
        if (data != null) {
            imageCallback.callbackImg(data);
        }
    }
}
