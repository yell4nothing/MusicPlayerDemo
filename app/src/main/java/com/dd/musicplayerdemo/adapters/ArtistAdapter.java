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
import com.dd.musicplayerdemo.beans.Diantai;
import com.dd.musicplayerdemo.cache.BytesLruCache;
import com.dd.musicplayerdemo.callback.ImageCallback;
import com.dd.musicplayerdemo.net.ImageAsyncTask;
import com.dd.musicplayerdemo.utils.SdCardUtils;

import java.util.List;

/**
 * Created by my on 2016/11/5.
 */
public class ArtistAdapter extends BaseAdapter {

    private Context mContext;
    private List<Diantai.ResultBean.ChannellistBean> data;
    private BytesLruCache mBytesLruCache;

    public ArtistAdapter(Context context, List<Diantai.ResultBean.ChannellistBean> data) {
        this.mContext = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View ret = null;
        ViewHolder holder = null;
        if (convertView != null) {
            ret = convertView;
            holder = (ViewHolder) convertView.getTag();
        }else {
            ret = LayoutInflater.from(mContext).inflate(R.layout.grid_item,parent,false);
            holder = new ViewHolder();
            holder.cover = (ImageView) ret.findViewById(R.id.grid_cover);
            holder.name = (TextView) ret.findViewById(R.id.grid_name);
            ret.setTag(holder);
        }

        holder.name.setText(data.get(position).getName());

        final String imgPath = data.get(position).getAvatar();
        final String fileName = imgPath.substring(imgPath.lastIndexOf("/")+1);
        byte[] imgData = loadCache(fileName);
        if (imgData != null) {
            holder.cover.setImageBitmap(BitmapFactory.decodeByteArray(imgData,0,imgData.length));
        }else {
            holder.cover.setImageBitmap(Bitmap.createBitmap(120,120, Bitmap.Config.ALPHA_8));
            holder.cover.setTag(imgPath);
            final ViewHolder fHolder = holder;
            new ImageAsyncTask(new ImageCallback() {
                @Override
                public void callbackImg(byte[] data) {
                    if (data != null && imgPath.equals(fHolder.cover.getTag())) {
                        Log.d("flag", "--------->callbackImg: 图片加载自网络");
                        fHolder.cover.setImageBitmap(BitmapFactory.decodeByteArray(data,0,data.length));

                        mBytesLruCache.put(fileName,data);

                        SdCardUtils.saveByteToCache(mContext,data,fileName);
                    }
                }
            }).execute(imgPath);
        }
        return ret;
    }

    private byte[] loadCache(String path) {
        byte[] bytes = mBytesLruCache.get(path);
        if (bytes != null) {
            Log.d("flag", "--------->loadCache: 图片加载自内存");
            return bytes;
        }
        bytes = SdCardUtils.loadByteFromCache(mContext, path);
        if (bytes != null) {
            mBytesLruCache.put(path,bytes);
            Log.d("flag", "--------->loadCache: 图片加载自外部存储");
            return bytes;
        }
        return null;
    }

    private class ViewHolder{
        public ImageView cover;
        public TextView name;
    }
}
