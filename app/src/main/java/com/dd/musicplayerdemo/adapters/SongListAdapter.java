package com.dd.musicplayerdemo.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.musicplayerdemo.R;
import com.dd.musicplayerdemo.beans.SongList;
import com.dd.musicplayerdemo.cache.BytesLruCache;
import com.dd.musicplayerdemo.callback.ImageCallback;
import com.dd.musicplayerdemo.net.ImageAsyncTask;
import com.dd.musicplayerdemo.utils.SdCardUtils;

import java.util.List;

/**
 * Created by my on 2016/11/5.
 */
public class SongListAdapter extends BaseAdapter {

    private Context context;
    private List<SongList.ResultBean.SonglistBean> data;

    private BytesLruCache mBytesLruCache;

    public SongListAdapter(Context context, List<SongList.ResultBean.SonglistBean> data) {
        this.context = context;
        this.data = data;
        mBytesLruCache = new BytesLruCache((int) (Runtime.getRuntime().maxMemory()/8));
    }

    @Override
    public int getCount() {
        return data==null?0:data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View ret = null;
        ViewHolder holder = null;
        if (convertView != null) {
            ret = convertView;
            holder = (ViewHolder) convertView.getTag();
        }else {
            ret = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
            holder = new ViewHolder();
            holder.cover = (ImageView) ret.findViewById(R.id.list_cover);
            holder.songName  = (TextView) ret.findViewById(R.id.list_song_name);
            holder.artist = (TextView) ret.findViewById(R.id.list_artist);

            ret.setTag(holder);
        }

        holder.songName.setText(data.get(position).getTitle());
        holder.artist.setText(data.get(position).getArtist());

        final String imgPath = data.get(position).getThumb();
        final String fileName = imgPath.substring(imgPath.lastIndexOf("/")+1);
        final byte[] imgData = loadCache(fileName);
        if (imgData != null) {
            holder.cover.setImageBitmap(BitmapFactory.decodeByteArray(imgData,0,imgData.length));
        }else {
            holder.cover.setImageBitmap(Bitmap.createBitmap(80,80, Bitmap.Config.ALPHA_8));
            holder.cover.setTag(imgPath);
            final ViewHolder fHolder = holder;
            new ImageAsyncTask(new ImageCallback() {
                @Override
                public void callbackImg(byte[] data) {
                    if (data != null&&imgPath.equals(fHolder.cover.getTag())) {
                        Log.d("flag", "--------->loadCache: 图片加载自网络");
                        fHolder.cover.setImageBitmap(BitmapFactory.decodeByteArray(data,0,data.length));

                        mBytesLruCache.put(fileName,data);

                        SdCardUtils.saveByteToCache(context,data,fileName);
                    }
                }
            }).execute(imgPath);
        }

        return ret;
    }

    private byte[] loadCache(String fileName) {
        byte[] bytes = mBytesLruCache.get(fileName);
        if (bytes != null) {
            Log.d("flag", "--------->loadCache: 图片加载自内存");
            return bytes;
        }
        bytes = SdCardUtils.loadByteFromCache(context, fileName);
        if (bytes != null) {
            mBytesLruCache.put(fileName,bytes);
            Log.d("flag", "--------->loadCache: 图片加载自外部存储");
            return bytes;
        }
        return null;
    }

    private class ViewHolder{
        public ImageView cover;
        public TextView songName,artist;
    }
}
