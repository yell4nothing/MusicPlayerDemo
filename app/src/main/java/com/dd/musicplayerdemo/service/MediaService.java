package com.dd.musicplayerdemo.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class MediaService extends Service {

    private MediaPlayer mMediaPlayer;
    private boolean isPlaying = false;
    private Thread mThread;

    public MediaService() {
    }

    @Override
    public void onCreate() {
        initMediaPlayer();
    }

    private void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("flag", "--------->onCompletion: xxxxxxxxxxxxxxxxx");
                Intent intent = new Intent("com.dd.mediaplayer");
                intent.putExtra("next", true);
                MediaService.this.sendBroadcast(intent);
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MediaController();
    }

    public class MediaController extends Binder {

        public void initPlay(String path) {
            isPlaying = false;
            if (mThread != null) {
                mThread.interrupt();
            }
            try {
                //mMediaPlayer.release();
                //initMediaPlayer();
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(path);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
                isPlaying = true;
                notifyProgress();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void replay() {
            mMediaPlayer.start();
            isPlaying = true;
            notifyProgress();
        }

        public void pause() {
            mMediaPlayer.pause();
            isPlaying = false;
        }

        public void setProgress(int progress) {
            int duration = mMediaPlayer.getDuration();
            int currentTime = (int) (progress * 1f / 1000 * duration);
            mMediaPlayer.seekTo(currentTime);
        }
    }

    private void notifyProgress() {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isPlaying) {

                    int duration = mMediaPlayer.getDuration();
                    int currentPosition = mMediaPlayer.getCurrentPosition();
                    int progress = (int) (currentPosition * 1000f / duration);

                    if (progress<=999) {
                        Intent intent = new Intent("com.dd.mediaplayer");
                        intent.putExtra("next", false);
                        intent.putExtra("duration", duration);
                        intent.putExtra("currentPosition", currentPosition);
                        MediaService.this.sendBroadcast(intent);
                    }

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mThread.start();
    }

    @Override
    public void onDestroy() {
        isPlaying = false;
        mMediaPlayer.release();
    }
}
