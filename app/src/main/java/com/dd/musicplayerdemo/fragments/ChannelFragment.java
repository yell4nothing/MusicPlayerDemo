package com.dd.musicplayerdemo.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.dd.musicplayerdemo.ui.ChannelActivity;
import com.dd.musicplayerdemo.R;
import com.dd.musicplayerdemo.adapters.ChannelAdapter;
import com.dd.musicplayerdemo.beans.Diantai;
import com.dd.musicplayerdemo.constants.Uri;
import com.dd.musicplayerdemo.utils.HttpUtils;
import com.dd.musicplayerdemo.utils.NetworkUtils;
import com.dd.musicplayerdemo.utils.SdCardUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelFragment extends Fragment {

    private List<Diantai.ResultBean.ChannellistBean> mData = new ArrayList<>();

    public ChannelFragment() {
        // Required empty public constructor
    }

    private GridView mGridView;
    private BaseAdapter mAdapter;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Diantai diantai = (Diantai) msg.obj;
            if (diantai != null) {
                mData.addAll(diantai.getResult().get(0).getChannellist());
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_main, container, false);

        initGridView(ret);

        initData();

        return ret;
    }

    private void initGridView(View ret) {
        mGridView = (GridView) ret.findViewById(R.id.main_gridview);

        mAdapter = new ChannelAdapter(getContext(),mData);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),ChannelActivity.class);
                intent.putExtra("ch_name",mData.get(position).getCh_name());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        if (NetworkUtils.isConnected(getContext())) {
            if(!loadCache()){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        byte[] data = HttpUtils.loadByte(Uri.SONG_DIANTAI_LIST);
                        if (data != null) {
                            Diantai diantai = JSON.parseObject(new String(data), Diantai.class);
                            Message msg = Message.obtain();
                            msg.obj = diantai;
                            mHandler.sendMessage(msg);
                            //存到外部存储
                            String fileName = "diantai";
                            SdCardUtils.saveByteToCache(getContext(),data,fileName);
                        }
                    }
                }).start();
            }
        }else {
            loadCache();
        }
    }

    private boolean loadCache(){
        String fileName = "diantai";
        byte[] data = SdCardUtils.loadByteFromCache(getContext(),fileName);
        if (data != null) {
            Diantai diantai = JSON.parseObject(new String(data), Diantai.class);
            mData.addAll(diantai.getResult().get(0).getChannellist());
            mAdapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }
}
