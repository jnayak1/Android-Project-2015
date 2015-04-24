package jdn4ae.cs2110.virginia.edu.gamepractice;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Iterator;

public class Ghost {

    private int size;
    private static float distancePerMove = 5; // all ghosts move same amount per move so static
    private MainCharacter mainCharacter;
    private Bitmap ghostBitmap;
    private float positionX, positionY;
    private Rect ghostRect;
    private GamePractice gamePractice;
    public final static int MAX_SIZE = 8;
    private static int autoGenRadius = 200;
    private static float ghostBuffer = 100;



    protected Ghost(float positionX, float positionY,
                    GamePractice gamePractice, int size) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = MAX_SIZE / size; // max size 8, won't get any bigger after that
        this.ghostBitmap = BitmapFactory.decodeResource(gamePractice.getResources(),
                R.drawable.ghost2, options);
        this.positionX = positionX;
        this.positionY = positionY;
        this.gamePractice = gamePractice;
        this.size = size; // max size 8, won't get any bigger after that
        this.mainCharacter = gamePractice.getMainCharacter();
        ghostRect = new Rect((int) positionX,(int) positionY,
                (int) positionX + ghostBitmap.getWidth(),
                (int) positionY + ghostBitmap.getHeight());
    }

    public float xDiff(){
        float mainCharacterX = mainCharacter.getPositionX();
        return this.getPositionX() - mainCharacterX;
    }
    public float yDiff(){
        float mainCharacterY = mainCharacter.getPositionY();
        return this.getPositionY() - mainCharacterY;
    }
    public float getSlope(){
        float xDiff = this.xDiff();
        float yDiff = this.yDiff();

        return yDiff / xDiff;
    }
    public float getTheta(){
        float slope = this.getSlope();


        return (float) Math.atan(slope);
    }
    public float changeX(){
        float theta = this.getTheta();
        float changeX = distancePerMove * ((float) Math.cos(theta));
        if(this.xDiff() > 0){
            changeX = changeX * (-1);
        }
        return changeX;
    }
    public float changeY(){
        float theta = this.getTheta();
        float changeY = distancePerMove * ((float) Math.sin(theta));
        if(this.xDiff() > 0){
            changeY = changeY * (-1);
        }
        return changeY;
    }
    public float futureX(){
        return this.getPositionX() + this.changeX();
    }
    public float futureY(){
        return this.getPositionY() + this.changeY();
    }

    public void move(){
        this.moveGhostRectX((int) this.changeX());
        this.moveGhostRectY((int) this.changeY());
        float futureX = this.futureX();
        float futureY = this.futureY();
        this.setPositionX(futureX);
        this.setPositionY(futureY);
    }


    public void onDraw(Canvas canvas){
        if(Rect.intersects(gamePractice.getSurfaceViewBitMapSRCRect(), this.getGhostRect())){
            canvas.drawBitmap(this.getGhostBitmap(), this.getPositionX(),
                    this.getPositionY(), null);
        }
    }



    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;

    }

    public float getDistancePerMove() {
        return distancePerMove;
    }

    public static void setDistancePerMove(float distancePerMove) {
        Ghost.distancePerMove = distancePerMove;
    }


    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public Bitmap getGhostBitmap() {
        return ghostBitmap;
    }

    public void setGhostBitmap(Bitmap ghostBitmap) {
        this.ghostBitmap = ghostBitmap;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }


    public Rect getGhostRect() {
        return this.ghostRect;
    }

    public void setGhostRect(int left, int top, int right, int bottom) {
        this.ghostRect.left = left;
        this.ghostRect.right = right;
        this.ghostRect.top = top;
        this.ghostRect.bottom = bottom;
    }

    public void moveGhostRectX(int moveAmount){
        this.ghostRect.left += moveAmount;
        this.ghostRect.right += moveAmount;
    }

    public void moveGhostRectY(int moveAmount){
        this.ghostRect.top += moveAmount;
        this.ghostRect.bottom += moveAmount;
    }

    public static void autoGenerate(GhostArrayList ghostArrayList, GamePractice gamePractice){
        MainCharacter mainCharacter = gamePractice.getMainCharacter();
        float mainCharacterX = mainCharacter.getPositionX();
        float mainCharacterY = mainCharacter.getPositionY();
        float changeDistanceX = (float)( 2*Math.random() - 1) * autoGenRadius + ghostBuffer;
        float changeDistanceY = (float)( Math.random()) * autoGenRadius + ghostBuffer;
        float ghostX = mainCharacterX + changeDistanceX;
        float ghostY = mainCharacterY + changeDistanceY;
        Ghost ghost = new Ghost(ghostX,ghostY,gamePractice,1);
        ghostArrayList.add(ghost);
        System.out.println("ghost generated");
    }


    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o instanceof Ghost){
            Ghost ghostO = (Ghost) o;
            return Rect.intersects(this.ghostRect,((Ghost) o).getGhostRect());
        }
        return false;
    }

    public boolean collided(BulletArrayList bullets) {
        boolean value = false;
        for(Bullet bullet : bullets){
            if(Rect.intersects(bullet.getRect(),this.getGhostRect())){
                value = true;
            }
        }
        return value;
    }

    public boolean collided(ArrayList items) {
        boolean value = false;
        Iterator iterator = items.iterator();
        while(iterator.hasNext()){
            Object item = iterator.next();
            if(item instanceof Bomb){
                Bomb bomb = (Bomb) item;
                if(Rect.intersects(this.getGhostRect(), bomb.getRect())){
                    value = true;
                }
            }
        }
        return value;
    }
}
