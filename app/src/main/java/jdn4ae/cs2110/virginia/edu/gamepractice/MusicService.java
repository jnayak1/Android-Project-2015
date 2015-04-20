package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by pnayak1 on 4/17/15.
 */
public class MusicService extends Service {
    private final IBinder mBinder = new ServiceBinder();
    MediaPlayer mPlayer;
    private int currentPosition = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = MediaPlayer.create(this,R.raw.vortex_rikers_dreams);
        if(mPlayer != null){
            mPlayer.setLooping(true);
            mPlayer.setVolume(100,100);
        }
    }

    public class ServiceBinder extends Binder {
        public MusicService getService(){
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPlayer.start();
        return START_STICKY;
    }

    public void resumeMusic() {
        if(!mPlayer.isPlaying()){
            mPlayer.seekTo(currentPosition);
            mPlayer.start();
        }
    }

    public void pauseMusic() {
        if(mPlayer.isPlaying()){
            mPlayer.pause();
            currentPosition = mPlayer.getCurrentPosition();
        }

    }

    public void stopMusic(){
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPlayer != null) {
            try{
                mPlayer.stop();
                mPlayer.release();
            }finally {
                mPlayer = null;
            }
        }
    }
}
