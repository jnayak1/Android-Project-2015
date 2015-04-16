package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import java.util.ArrayList;


public class GamePractice extends Activity implements SurfaceHolder.Callback, MoveButton, OtherButton {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
//                    Ghost ghost0 = new Ghost(800,450,GamePractice.this,1);
//                    Ghost ghost1 = new Ghost(100,450,GamePractice.this,2);
//                    Ghost ghost2 = new Ghost(800,100,GamePractice.this,1);
//                    Ghost ghost3 = new Ghost(100,100,GamePractice.this,4);

                    ghosts = new GhostArrayList(new ArrayList<Ghost>());
                    bullets = new BulletArrayList(new ArrayList<Bullet>());

//                    ghosts.add(ghost0);
//                    ghosts.add(ghost1);
//                    ghosts.add(ghost2);
//                    ghosts.add(ghost3);

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
            updateCanvas.drawBitmap(mapBitMap,0,0,null);
            mainCharacter.onDraw(updateCanvas);
            ghosts.onDraw(updateCanvas);
            bullets.onDraw(updateCanvas);
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

        surfaceViewBitMapSRCRect.set(left, top, right, bottom);
        mainCharacter.moveRight();



    }

    @Override
    public void leftButtonClick() {
        Toast.makeText(GamePractice.this, "leftButtonClicked", Toast.LENGTH_SHORT).show();
        // move surfaceViewSRCRect right

        int left = surfaceViewBitMapSRCRect.left - moveAmount;
        int top = surfaceViewBitMapSRCRect.top;
        int right = surfaceViewBitMapSRCRect.right - moveAmount;
        int bottom = surfaceViewBitMapSRCRect.bottom;

        surfaceViewBitMapSRCRect.set(left, top, right, bottom);
        mainCharacter.moveLeft();
        System.out.println(mainCharacter.getPositionX());
    }

    @Override
    public void upButtonClick() {
        Toast.makeText(GamePractice.this, "upButtonClicked", Toast.LENGTH_SHORT).show();
        // move surfaceViewSRCRect down

        mainCharacter.jump();

    }

    @Override
    public void itemButtonClick() {
        //use an item!

    }

    @Override
    public void shootButtonClick() {
        //TO DO: method called when shoot button is pushed
        mainCharacter.shoot(bullets);
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
}
