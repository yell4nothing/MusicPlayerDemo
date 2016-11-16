package com.dd.musicplayerdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by my on 2016/11/5.
 */

public class NetworkUtils {

    public  static boolean isConnected(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null) {
            return false;
        }

        switch (activeNetworkInfo.getType()){
            case ConnectivityManager.TYPE_WIFI:
                //Toast.makeText(context, "当前使用WIFI", Toast.LENGTH_SHORT).show();
                return true;
            case ConnectivityManager.TYPE_MOBILE:
               // Toast.makeText(context, "当前使用移动网络", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}
