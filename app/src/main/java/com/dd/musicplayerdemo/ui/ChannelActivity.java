package com.dd.musicplayerdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.dd.musicplayerdemo.R;
import com.dd.musicplayerdemo.adapters.SongListAdapter;
import com.dd.musicplayerdemo.beans.SongList;
import com.dd.musicplayerdemo.constants.Uri;
import com.dd.musicplayerdemo.utils.HttpUtils;
import com.dd.musicplayerdemo.utils.SdCardUtils;

import java.util.ArrayList;
import java.util.List;

public class ChannelActivity extends AppCompatActivity {

    private String mCh_name;
    private ListView mListView;

    private BaseAdapter mAdapter;
    private List<SongList.ResultBean.SonglistBean> mData = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            SongList data = (SongList) msg.obj;
            if (data != null) {
                List<SongList.ResultBean.SonglistBean> songlist = data.getResult().getSonglist();
                for (int i = 0; i < songlist.size(); i++) {
                    SongList.ResultBean.SonglistBean songlistBean = songlist.get(i);
                    if (songlistBean.getSongid()!=null) {
                        mData.add(songlistBean);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        mCh_name = getIntent().getStringExtra("ch_name");

        initListView();

        initData();
    }

    private void initListView() {
        mListView = (ListView) findViewById(R.id.channel_list);

        mAdapter = new SongListAdapter(this, mData);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChannelActivity.this, PlayerActivity.class);
                intent.putExtra("pos",position);
                ArrayList<String> songIds = new ArrayList<String>();
                for (int i = 0; i < mData.size(); i++) {
                    songIds.add(mData.get(i).getSongid());
                }
                intent.putStringArrayListExtra("songids",songIds);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        if (mCh_name != null) {
            //if (NetworkUtils.isConnected(this)) {
                //if (!loadCache(mCh_name)) {
                    final String path = String.format(Uri.SONG_LIST, mCh_name);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            byte[] bytes = HttpUtils.loadByte(path);
                            if (bytes != null) {
                                SongList songList = JSON.parseObject(new String(bytes), SongList.class);
                                Message msg = Message.obtain();
                                msg.obj = songList;
                                mHandler.sendMessage(msg);

                                SdCardUtils.saveByteToCache(ChannelActivity.this, bytes, mCh_name);
                            }
                        }
                    }).start();
               // }
            /*}else {
                loadCache(mCh_name);
            }*/
        }
    }

    private boolean loadCache(String ch_name) {
        byte[] bytes = SdCardUtils.loadByteFromCache(this,ch_name);
        if (bytes != null) {
            SongList songList = JSON.parseObject(new String(bytes), SongList.class);
            mData.addAll(songList.getResult().getSonglist());
            mAdapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }
}
