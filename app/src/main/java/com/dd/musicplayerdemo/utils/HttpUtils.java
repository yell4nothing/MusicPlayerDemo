package com.dd.musicplayerdemo.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by my on 2016/11/5.
 */
public class HttpUtils {
    public static byte[] loadByte(String path){

        /*OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(path);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().bytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;*/

        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            if (connection.getResponseCode()== HttpURLConnection.HTTP_OK) {
                is = connection.getInputStream();
                baos = new ByteArrayOutputStream();

                int len = 0;
                byte[] b = new byte[1024*8];

                while ((len = is.read(b))!=-1){
                    baos.write(b,0,len);
                }
                baos.flush();
                return baos.toByteArray();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
