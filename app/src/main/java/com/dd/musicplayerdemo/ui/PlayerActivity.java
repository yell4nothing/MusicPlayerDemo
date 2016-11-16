package com.dd.musicplayerdemo.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.dd.musicplayerdemo.R;
import com.dd.musicplayerdemo.beans.Song;
import com.dd.musicplayerdemo.cache.BytesLruCache;
import com.dd.musicplayerdemo.callback.ImageCallback;
import com.dd.musicplayerdemo.constants.Uri;
import com.dd.musicplayerdemo.net.ImageAsyncTask;
import com.dd.musicplayerdemo.service.MediaService;
import com.dd.musicplayerdemo.widget.CircleImage;
import com.dd.musicplayerdemo.utils.HttpUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerActivity extends AppCompatActivity {

    private int currentPos;
    private Intent service;
    private ServiceConnection conn;
    private List<String> mSongIds;
    private List<Song> mSongs = new ArrayList<>();

    private ImageView mCover;
    private TextView mSongName;
    private ImageView mPlay;
    private TextView mProgress;

    //lrc
    private TextView mLrc;
    private String mLyric;
    private String[] mLyrics;
    private List<String> lyricTime = new ArrayList<>();
    private List<String> lyricData = new ArrayList<>();
    private int lyricLine = 0;
    //!lrc

    private SeekBar mSeekBar;
    private SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");

    private BytesLruCache mBytesLruCache;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

                    Song.DataBean.SongListBean songListBean = mSongs.get(currentPos).getData().getSongList().get(0);
                    mSongName.setText(songListBean.getSongName());

                    final String imgPath = songListBean.getSongPicRadio();

                    byte[] bytes = mBytesLruCache.get(imgPath);
                    if (bytes != null) {
                        mCover.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                    } else {
                        mCover.setTag(imgPath);
                        mCover.setImageBitmap(Bitmap.createBitmap(200, 200, Bitmap.Config.ALPHA_8));
                        new ImageAsyncTask(new ImageCallback() {
                            @Override
                            public void callbackImg(byte[] data) {
                                if (data != null) {
                                    if (imgPath.equals(mCover.getTag())) {
                                        mCover.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));
                                    }
                                    mBytesLruCache.put(imgPath, data);
                                }
                            }
                        }).execute(imgPath);
                    }

                    mMediaController.initPlay(songListBean.getSongLink());
                    mRotate.start();
                    mPlay.setSelected(true);

                    initLrc(songListBean.getLrcLink());
                    mLrc.setText("加载歌词中...");
                    break;
            }
        }
    };
    private int mDuration;

    private MusicReceiver mReceiver;
    private MediaService.MediaController mMediaController;
    private ObjectAnimator mRotate;

    private void initLrc(final String lrcLink) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                byte[] bytes = HttpUtils.loadByte(lrcLink);
                String lrcStr = new String(bytes);

                //lrc
                mLyric = lrcStr;

                Pattern p = Pattern.compile("\\s+");
                Matcher m = p.matcher(mLyric);
                mLyric= m.replaceAll(" ");

                mLyrics = mLyric.split(" \\[");

                //只存时间和歌词
                for (int i = 0; i < mLyrics.length; i++) {
                    if(mLyrics[i].startsWith("0")){
                        String[] split = mLyrics[i].split("\\]");
                        if(split.length>1){
                            String[] split1 = split[0].split("\\.");
                            lyricTime.add(split1[0]);
                            lyricData.add(split[1]);
                            Log.d("fff","---------------->time: "+i +","+split1[0]+", data: "+split[1]);
                        }
                    }
                }
                //!lrc
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        currentPos = getIntent().getIntExtra("pos", 0);

        mSongIds = getIntent().getStringArrayListExtra("songids");

        mBytesLruCache = new BytesLruCache((int) (Runtime.getRuntime().maxMemory() / 8));

        initView();

        initData();

        initService();

        mReceiver = new MusicReceiver();
        this.registerReceiver(mReceiver, new IntentFilter("com.dd.mediaplayer"));
    }

    private void initView() {
        mCover = (CircleImage) findViewById(R.id.player_cover);
        mSongName = (TextView) findViewById(R.id.player_song_name);
        mSeekBar = (SeekBar) findViewById(R.id.player_seekbar);
        mProgress = (TextView) findViewById(R.id.player_progress);
        mPlay = (ImageView)findViewById(R.id.player_play_pause);
        mLrc = (TextView) findViewById(R.id.lrc);

        mPlay.setSelected(false);

        mRotate = ObjectAnimator.ofFloat(mCover, "rotation", 0, 360);
        mRotate.setDuration(4000);
        mRotate.setRepeatMode(ValueAnimator.RESTART);
        mRotate.setRepeatCount(ValueAnimator.INFINITE);
        mRotate.setInterpolator(new LinearInterpolator());

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mMediaController.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mSongIds.size(); i++) {
                    String path = String.format(Uri.SONG, mSongIds.get(i));
                    byte[] data = HttpUtils.loadByte(path);
                    Song song = JSON.parseObject(new String(data), Song.class);
                    mSongs.add(song);
                }
                mHandler.sendEmptyMessage(0);
            }
        }).start();
    }

    private void initService() {
        service = new Intent();
        service.setClass(PlayerActivity.this, MediaService.class);
        //   service.putExtra("songid",mSongIds.get(currentPos));
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mMediaController = ((MediaService.MediaController) service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        bindService(service, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
        this.unregisterReceiver(mReceiver);
    }

    public void music(View view) {
        switch (view.getId()) {
            case R.id.player_play_pause:
                if (view.isSelected()) {//播放状态，显示暂停图片
                    mMediaController.pause();
                    view.setSelected(false);
                    if (Build.VERSION.SDK_INT >= 19) {
                        mRotate.pause();
                    } else {
                        mRotate.cancel();
                    }
                } else {//暂停状态，显示播放图片
                    mMediaController.replay();
                    view.setSelected(true);
                    if (Build.VERSION.SDK_INT >= 19) {
                        mRotate.resume();
                    } else {
                        mRotate.start();
                    }
                }
                break;
            case R.id.player_pre:
                currentPos--;
                if (currentPos < 0) {
                    currentPos = mSongIds.size() - 1;
                }
                mHandler.sendEmptyMessage(0);
                break;
            case R.id.player_next:
                currentPos++;
                if (currentPos >= mSongIds.size() - 1) {
                    currentPos = 0;
                }
                mHandler.sendEmptyMessage(0);
                break;
        }
    }

    private class MusicReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra("next", false)) {
                Log.d("flag", "--------->onReceive: nextnextnext");
                currentPos++;
                if (currentPos >= mSongIds.size() - 1) {
                    currentPos = 0;
                }
                mHandler.sendEmptyMessage(0);
            } else {
                mDuration = intent.getIntExtra("duration", 0);
                int currentPosition = intent.getIntExtra("currentPosition", 0);

                int progress = (int) (currentPosition * 1000f / mDuration);

                mSeekBar.setProgress(progress);
                mProgress.setText(sdf.format(currentPosition) + "/" + sdf.format(mDuration));


                //lrc
                String needTimeFormat = getNeedTimeFormat(currentPosition);

                Log.d("fff","---------------->needTime: "+needTimeFormat);

                if(lyricTime!=null&&lyricTime.size()>lyricLine){
                    if(needTimeFormat.equals(lyricTime.get(lyricLine))){
                        String data = lyricData.get(lyricLine);

                        mLrc.setText(data);

                        lyricLine++;
                    }
                }
            }
        }

        private String getNeedTimeFormat(int time) {
            String timeFormat = "%02d:%02d";
            int totalSecond = time / 1000;
            int hour = totalSecond / 3600;
            int minute = totalSecond % 3600 / 60;
            int second = totalSecond % 3600 % 60;
            int subSecond = time%1000/10;
            return String.format(timeFormat, minute, second);
        }
        //!lrc
    }
}
