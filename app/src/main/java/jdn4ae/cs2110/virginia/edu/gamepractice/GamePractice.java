package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.Toast;


public class GamePractice extends Activity implements SurfaceHolder.Callback, MoveButton, OtherButton {

    MapSurfaceView mapSurfaceView;
    Bitmap characterBitMap;
    Bitmap mapBitMap;
    Rect surfaceViewBitMapDSTRect;
    Rect surfaceViewBitMapSRCRect;
    int moveAmount = 10;
    float characterX,characterY;
    float mapBitMapWidth, mapBitMapHeight;
    float surfaceViewBitMapWidth, surfaceViewBitMapHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapSurfaceView = new MapSurfaceView(this);
        characterBitMap = BitmapFactory.decodeResource(getResources(),R.drawable.character);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        mapBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.background, options);
        mapBitMapHeight = mapBitMap.getHeight();
        mapBitMapWidth = mapBitMap.getWidth();
        setContentView(R.layout.game_practice);
        characterX = 0;
        characterY = 0;
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
                    surfaceViewBitMapSRCRect = new Rect(0,0,500,500);
                    characterY = (surfaceViewBitMapHeight*3)/4; // determines height of where character is positioned
                    characterX = surfaceViewBitMapWidth /2;
                }
            });
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        mapSurfaceView.onResumeMapSurfaceView();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapSurfaceView.onPauseMapSurfaceView();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public class MapSurfaceView extends SurfaceView implements Runnable {
        Thread thread = null;
        SurfaceHolder surfaceHolder;
        volatile boolean running = false;
        float surfaceViewBitMapRectX,surfaceViewBitMapRectY;

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

                    Canvas canvas = surfaceHolder.lockCanvas();
//                    Bitmap alteredBitmap = Bitmap.createBitmap(Math.round(mapBitMapWidth),
//                            Math.round(mapBitMapHeight),mapBitMap.getConfig());

                    // draw here
                    canvas.drawBitmap(mapBitMap,surfaceViewBitMapSRCRect,surfaceViewBitMapDSTRect,null);
                    canvas.drawBitmap(characterBitMap,characterX - (characterBitMap.getWidth() / 2),
                            characterY - (characterBitMap.getHeight() /2) ,null);


                    surfaceHolder.unlockCanvasAndPost(canvas);


                    try{
                        Thread.sleep(40);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void rightButtonClick() {
        Toast.makeText(GamePractice.this, "rightButtonClicked", Toast.LENGTH_SHORT).show();
        // move surfaceViewSRCRect left

        int left = surfaceViewBitMapSRCRect.left + moveAmount;
        int top = surfaceViewBitMapSRCRect.top;
        int right = surfaceViewBitMapSRCRect.right + moveAmount;
        int bottom = surfaceViewBitMapSRCRect.bottom;

        surfaceViewBitMapSRCRect.set(left,top,right,bottom);




    }

    @Override
    public void leftButtonClick() {
        Toast.makeText(GamePractice.this, "leftButtonClicked", Toast.LENGTH_SHORT).show();
        // move surfaceViewSRCRect right

        int left = surfaceViewBitMapSRCRect.left - moveAmount;
        int top = surfaceViewBitMapSRCRect.top;
        int right = surfaceViewBitMapSRCRect.right - moveAmount;
        int bottom = surfaceViewBitMapSRCRect.bottom;

        surfaceViewBitMapSRCRect.set(left,top,right,bottom);
    }

    @Override
    public void upButtonClick() {
        Toast.makeText(GamePractice.this, "upButtonClicked", Toast.LENGTH_SHORT).show();
        // move surfaceViewSRCRect down

        int left = surfaceViewBitMapSRCRect.left;
        int top = surfaceViewBitMapSRCRect.top - moveAmount;
        int right = surfaceViewBitMapSRCRect.right;
        int bottom = surfaceViewBitMapSRCRect.bottom - moveAmount;

        surfaceViewBitMapSRCRect.set(left,top,right,bottom);
    }

    @Override
    public void downButtonClick() {
        Toast.makeText(GamePractice.this, "downButtonClicked", Toast.LENGTH_SHORT).show();
        // move surfaceViewSRCRect up

        int left = surfaceViewBitMapSRCRect.left;
        int top = surfaceViewBitMapSRCRect.top + moveAmount;
        int right = surfaceViewBitMapSRCRect.right;
        int bottom = surfaceViewBitMapSRCRect.bottom + moveAmount;

        surfaceViewBitMapSRCRect.set(left,top,right,bottom);

    }

    public MapSurfaceView getMapSurfaceView(){
        return mapSurfaceView;
    }
}
