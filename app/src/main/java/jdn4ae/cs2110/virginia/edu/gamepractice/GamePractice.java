package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;


public class GamePractice extends Activity implements SurfaceHolder.Callback, MoveButton, OtherButton {

    MapSurfaceView mapSurfaceView;
    Bitmap bitmap;
    float characterX,characterY;
    float mapSurfaceViewWidth;
    float mapSurfaceViewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapSurfaceView = new MapSurfaceView(this);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.character);
        setContentView(mapSurfaceView);
        characterX = 0;
        characterY = 0;
        ViewTreeObserver viewTreeObserver = mapSurfaceView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mapSurfaceView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mapSurfaceViewWidth = mapSurfaceView.getWidth();
                    mapSurfaceViewHeight = mapSurfaceView.getHeight();

                    characterY = mapSurfaceViewHeight / 2;
                    characterX = mapSurfaceViewWidth /2;
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapSurfaceView.onResumeMapSurfaceView();
        characterX = mapSurfaceView.getHeight() / 2;
        characterY = mapSurfaceView.getWidth() / 2;

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

                    // draw here
                    canvas.drawColor(Color.RED);
                    canvas.drawBitmap(bitmap,characterX - (bitmap.getWidth() / 2),
                            characterY - (bitmap.getHeight() /2) ,null);


                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    @Override
    public void rightButtonClick() {
        Toast.makeText(GamePractice.this, "rightButtonClicked", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void leftButtonClick() {
        Toast.makeText(GamePractice.this, "leftButtonClicked", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void upButtonClick() {
        Toast.makeText(GamePractice.this, "upButtonClicked", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void downButtonClick() {
        Toast.makeText(GamePractice.this, "downButtonClicked", Toast.LENGTH_SHORT).show();
    }


}
