package com.dd.musicplayerdemo.utils;

import android.content.Context;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by my on 2016/11/5.
 */
public class SdCardUtils {

    public static boolean isMounted(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public static byte[] loadByteFromCache(Context context, String fileName) {

        if (!isMounted()){
            return null;
        }

        File root = context.getExternalCacheDir();
        File file = new File(root,fileName);

        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;

        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream();

            int len = 0;
            byte[] b = new byte[1024*8];

            while ((len = fis.read(b))!=-1){
                baos.write(b,0,len);
            }
            baos.flush();
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null) {
                try {
                    fis.close();
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

    public static void saveByteToCache(Context context, byte[] data, String fileName) {

        if (!isMounted()){
            return;
        }

        File root = context.getExternalCacheDir();
        File file = new File(root,fileName);

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file);
            fos.write(data,0,data.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
