package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.lang.System;
import java.util.concurrent.TimeUnit;

public class GamePractice extends Activity implements OtherButton {

    private MapSurfaceView mapSurfaceView;
    private MainCharacter mainCharacter;
    private Bitmap mapBitMap;
    private Bitmap updateBitmap;
    private Canvas updateCanvas;
    private Rect surfaceViewBitMapDSTRect;
    private Rect surfaceViewBitMapSRCRect;
    private int moveAmount = 10;
    private float characterX,characterY;
    private float mapBitMapWidth, mapBitMapHeight;
    private float surfaceViewBitMapWidth, surfaceViewBitMapHeight;
    private GhostArrayList ghosts;
    private BulletArrayList bullets;
    private float startTime;
    private float gravity;
    private LRButtonHandler lrButtonHandler;
    private UpButtonHandler upButtonHandler;
    private volatile boolean shootButtonPressed;
    private static long autoGenGhostTimeMillis = 5000;
    private static final long START_UP_TIME = 2000;
    private long beginingTime;
    private long endTime;
    private long timeLeft;
    private MysteryBoxArrayList mysteryBoxes;
    private long moonGravityTimer;

    private boolean musicIsBound = false;
    private MusicService musicService;
    private long bombTimer;
    private float bombX, bombY;
    private Bitmap bombBitmap;
    private Rect bombRect;
    private Rect offScreenRect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        gravity = 10;
        shootButtonPressed = false;
        startTime = System.currentTimeMillis();
        mapSurfaceView = new MapSurfaceView(this);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        mapBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.background, options);
        mapBitMapHeight = mapBitMap.getHeight();
        mapBitMapWidth = mapBitMap.getWidth();
        updateBitmap = mapBitMap.copy(Bitmap.Config.ARGB_8888, true);
        updateCanvas = new Canvas(updateBitmap);
        setContentView(R.layout.game_practice);
        ViewTreeObserver viewTreeObserver = mapSurfaceView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mapSurfaceView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    surfaceViewBitMapWidth = mapSurfaceView.getWidth();
                    surfaceViewBitMapHeight = mapSurfaceView.getHeight();
                    surfaceViewBitMapDSTRect = new Rect(0,0,
                            Math.round(surfaceViewBitMapWidth),Math.round(surfaceViewBitMapHeight));
                    surfaceViewBitMapSRCRect = new Rect(surfaceViewBitMapDSTRect);
                    characterY = (surfaceViewBitMapHeight*3)/4; // determines height of where character is positioned
                    characterX = surfaceViewBitMapWidth /2;
                    mainCharacter = new MainCharacter(characterX,characterY,GamePractice.this);
                    ghosts = new GhostArrayList(new ArrayList<Ghost>(), GamePractice.this);
                    bullets = new BulletArrayList(new ArrayList<Bullet>(), GamePractice.this);
                    mysteryBoxes = new MysteryBoxArrayList(new ArrayList<MysteryBox>(),GamePractice.this);
                }
            });
        }
        lrButtonHandler = new LRButtonHandler();
        upButtonHandler = new UpButtonHandler();

        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        startService(music);

        beginingTime = System.currentTimeMillis();
        endTime = beginingTime + 4*(1000)*(60);
        timeLeft = endTime - beginingTime;
        TextView timeTextView = (TextView) findViewById(R.id.timeTextView);
        timeTextView.setText(Long.toString(timeLeft));
        bombTimer = 0;
        BitmapFactory.Options options4 = new BitmapFactory.Options();
        this.bombBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.bomb,options4);
        bombRect = new Rect();
        offScreenRect = new Rect(-100,-100,-101,-101);
        bombRect = offScreenRect;
        moonGravityTimer = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapSurfaceView.onResumeMapSurfaceView();
        lrButtonHandler.onResumeLRButtonHandler();
        upButtonHandler.onResumeUpButtonHandler();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapSurfaceView.onPauseMapSurfaceView();
        lrButtonHandler.onPauseLRButtonHandler();
        upButtonHandler.onPauseUpButtonHandler();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapSurfaceView.onPauseMapSurfaceView();
        lrButtonHandler.onPauseLRButtonHandler();
        upButtonHandler.onPauseUpButtonHandler();
        musicService.onDestroy();
    }

    @Override
    public void itemButtonClick() {
        //TO DO: method called when item button is pushed
        //should call separate methods for different varieties of mysteryBoxes, once mysteryBoxes are created.
        //bomb: display explosion image, kill ghosts within range, update statistics
        //moon gravity: alter gravity for a sett amount of time - temporary new jump method?
        //speed boost: changes movement for set amount of time.

        //ammo: update ammo count - part of shoot method; call automatically when picked up

        if (!mainCharacter.getItems().isEmpty()) {
            mainCharacter.useItem();
        }
    }

    @Override
    public void shootButtonClick() {
        //TO DO: method called when shoot button is pushed
        shootButtonPressed = true;
    }

    public MapSurfaceView getMapSurfaceView(){
        return mapSurfaceView;
    }

    public MainCharacter getMainCharacter() {
        return this.mainCharacter;
    }

    public int getMoveAmount(){
        return this.moveAmount;
    }

    public Rect getSurfaceViewBitMapSRCRect() {
        return surfaceViewBitMapSRCRect;
    }

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.ServiceBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };

    public void bindService(){
        this.bindService(new Intent(this,MusicService.class), serviceConnection,
                Context.BIND_AUTO_CREATE);
        musicIsBound = true;
    }

    public void unBindService(){
        if(musicIsBound){
            unbindService(serviceConnection);
            musicIsBound = false;
        }
    }

    public void setBombTimer(long l) {
        this.bombTimer = l;
    }

    public void setBombX(float positionX) {
        this.bombX = positionX;
    }

    public void setBombY(float positionY) {
        this.bombY = positionY;
    }

    public class MapSurfaceView extends SurfaceView implements Runnable {
        Thread thread = null;
        SurfaceHolder surfaceHolder;
        volatile boolean running = false;

        public MapSurfaceView(Context context) {
            super(context);
            surfaceHolder = getHolder();
        }
        public void onResumeMapSurfaceView(){
            running = true;
            thread = new Thread(this);
            thread.start();
        }

        public void onPauseMapSurfaceView(){
            boolean retry = true;
            running = false;
            while(retry){
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        @Override
        public void run() {
            while(running){
                if(surfaceHolder.getSurface().isValid()) {
                    // call update method that updates  ArrayList with all the
                    // different objects in it
                    update();
                    Canvas canvas = surfaceHolder.lockCanvas();

                    canvas.drawBitmap(updateBitmap,surfaceViewBitMapSRCRect,
                            surfaceViewBitMapDSTRect,null);


                    surfaceHolder.unlockCanvasAndPost(canvas);


                    try{
                        Thread.sleep(5);

                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }

        private void update() {
            // if ghost is killed or item is picked up, delete from ArrayList
            updateCanvas.drawBitmap(mapBitMap, 0, 0, null);
            if(bombTimer != 0){
                bombTimer -= 1;
                updateCanvas.drawBitmap(bombBitmap,bombX,bombY,null);
            }
            if(moonGravityTimer != 0){
                moonGravityTimer -= 1;
            }
            else{
                mainCharacter.setJumpAmount(120);
            }
            mainCharacter.onDraw(updateCanvas);
            ghosts.onDraw(updateCanvas);
            bullets.onDraw(updateCanvas);
            timeLeft = TimeUnit.MILLISECONDS.toSeconds((endTime - System.currentTimeMillis()));
            mysteryBoxes.onDraw(updateCanvas);


        }
    }

    public class LRButtonHandler implements Runnable {
        Thread thread;
        volatile boolean running;

        public LRButtonHandler() {
            this.thread = null;
            this.running = false;
        }

        public void onResumeLRButtonHandler() {
            running = true;
            thread = new Thread(this);
            thread.start();
        }

        public void onPauseLRButtonHandler() {
            boolean retry = true;
            running = false;
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void run() {
            try {
                Thread.sleep(START_UP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (running) {
                if (MoveButtonsFragment.rightIsPushed) {
                    int left = surfaceViewBitMapSRCRect.left + moveAmount;
                    int top = surfaceViewBitMapSRCRect.top;
                    int right = surfaceViewBitMapSRCRect.right + moveAmount;
                    int bottom = surfaceViewBitMapSRCRect.bottom;

                    surfaceViewBitMapSRCRect.set(left, top, right, bottom);
                    mainCharacter.moveRight();
                }
                if (MoveButtonsFragment.leftIsPushed) {
                    int left = surfaceViewBitMapSRCRect.left - moveAmount;
                    int top = surfaceViewBitMapSRCRect.top;
                    int right = surfaceViewBitMapSRCRect.right - moveAmount;
                    int bottom = surfaceViewBitMapSRCRect.bottom;
                    surfaceViewBitMapSRCRect.set(left, top, right, bottom);
                    mainCharacter.moveLeft();
                }


                try {
                    Thread.sleep(20);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class UpButtonHandler implements Runnable {
        Thread thread;
        volatile boolean running;

        public UpButtonHandler() {
            this.thread = null;
            this.running = false;
        }

        public void onResumeUpButtonHandler() {
            running = true;
            thread = new Thread(this);
            thread.start();
        }

        public void onPauseUpButtonHandler() {
            boolean retry = true;
            running = false;
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void run() {
            try {
                Thread.sleep(START_UP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (running) {
                if(MoveButtonsFragment.upIsPushed){
                    mainCharacter.jump();
                }

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isShootButtonPressed() {
        return shootButtonPressed;
    }

    public void setShootButtonPressed(boolean shootButtonPressed) {
        this.shootButtonPressed = shootButtonPressed;
    }

    public BulletArrayList getBullets() {
        return bullets;
    }

    public MysteryBoxArrayList getMysteryBoxes() {
        return mysteryBoxes;
    }

    public void setBombRect(Rect bombRect) {
        this.bombRect = bombRect;
    }

    public Rect getBombRect() {
        return bombRect;
    }

    public long getBombTimer() {
        return bombTimer;
    }

    public void setMoonGravityTimer(long moonGravityTimer) {
        this.moonGravityTimer = moonGravityTimer;
    }
}


