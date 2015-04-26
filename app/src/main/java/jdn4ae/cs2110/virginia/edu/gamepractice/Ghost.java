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
    private Rect ghostHitRect;
    private Rect ghostShootRect;
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
        ghostHitRect = new Rect((int) positionX,(int) positionY + 10,
                (int) positionX + ghostBitmap.getWidth(),
                (int) positionY - 10 + ghostBitmap.getHeight());
        ghostShootRect = new Rect((int) positionX,(int) positionY - 10,
                (int) positionX + ghostBitmap.getWidth(),
                (int) positionY + 10  + ghostBitmap.getHeight());
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
        this.moveGhostHitRectX((int) this.changeX());
        this.moveGhostHitRectY((int) this.changeY());
        this.moveGhostShootRectX((int) this.changeX());
        this.moveGhostShootRectY((int) this.changeY());
        float futureX = this.futureX();
        float futureY = this.futureY();
        this.setPositionX(futureX);
        this.setPositionY(futureY);
    }


    public void onDraw(Canvas canvas){
        if(Rect.intersects(gamePractice.getSurfaceViewBitMapSRCRect(), this.getGhostHitRect())){
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


    public Rect getGhostHitRect() {
        return this.ghostHitRect;
    }

    public Rect getGhostShootRect() {
        return this.ghostShootRect;
    }

    public void setGhostHitRect(int left, int top, int right, int bottom) {
        this.ghostHitRect.left = left;
        this.ghostHitRect.right = right;
        this.ghostHitRect.top = top;
        this.ghostHitRect.bottom = bottom;
    }

    public void setGhostShootRect(int left, int top, int right, int bottom) {
        this.ghostShootRect.left = left;
        this.ghostShootRect.right = right;
        this.ghostShootRect.top = top;
        this.ghostShootRect.bottom = bottom;
    }

    public void moveGhostHitRectX(int moveAmount){
        this.ghostHitRect.left += moveAmount;
        this.ghostHitRect.right += moveAmount;
    }

    public void moveGhostShootRectX(int moveAmount){
        this.ghostShootRect.left += moveAmount;
        this.ghostShootRect.right += moveAmount;
    }

    public void moveGhostHitRectY(int moveAmount){
        this.ghostHitRect.top += moveAmount;
        this.ghostHitRect.bottom += moveAmount;
    }

    public void moveGhostShootRectY(int moveAmount){
        this.ghostShootRect.top += moveAmount;
        this.ghostShootRect.bottom += moveAmount;
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
            return Rect.intersects(this.ghostShootRect,((Ghost) o).getGhostShootRect());
        }
        return false;
    }

    public boolean collided(BulletArrayList bullets) {
        boolean value = false;
        for(Bullet bullet : bullets){
            if(Rect.intersects(bullet.getRect(),this.getGhostShootRect())){
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
                if(Rect.intersects(this.getGhostShootRect(), bomb.getRect())){
                    value = true;
                }
            }
        }
        return value;
    }
}
