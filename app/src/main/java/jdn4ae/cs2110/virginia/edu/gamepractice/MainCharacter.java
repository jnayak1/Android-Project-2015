package jdn4ae.cs2110.virginia.edu.gamepractice;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.Toast;

import java.util.ArrayList;
import java.lang.System;


public class MainCharacter {

    private int moveAmount;
    private GamePractice gamePractice;
    private Bitmap characterBitmap;
    private Bitmap leftCharacterBitmap;
    private Bitmap rightCharacterBitmap;
    private ArrayList items;
    private boolean directionRight;
    private float positionX, positionY;
    private static float jumpAmount = 120;
    private boolean jumped;
    private int ammo;
    private boolean rising;
    private boolean falling;
    private float intialJumpSpeed = 5;
    private Rect r;


    public MainCharacter(float positionX, float positionY, GamePractice gamePractice) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        characterBitmap = BitmapFactory.decodeResource(gamePractice.getResources(),
                R.drawable.character, options);
        rightCharacterBitmap = characterBitmap.copy(Bitmap.Config.ARGB_8888,false);

        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        leftCharacterBitmap = Bitmap.createBitmap(characterBitmap, 0, 0,
                characterBitmap.getWidth(), characterBitmap.getHeight(), matrix, false);

        this.positionX = positionX - (1/2)*characterBitmap.getWidth();
        this.positionY = positionY - (1/2)*characterBitmap.getHeight();
        this.r = new Rect((int)this.positionX,(int)this.positionY,
                (int)this.positionX+characterBitmap.getWidth(),(int)this.positionY + characterBitmap.getHeight() );
        this.gamePractice = gamePractice;
        items = new ArrayList<Item>();
        moveAmount = gamePractice.getMoveAmount();
        directionRight = true;
        jumped = false;
        this.ammo = 10;
        this.rising = false;
        this.falling = false;
    }

    public void moveRight() {
        this.setPositionX(this.getPositionX() + moveAmount);
        this.setDirectionRight(true);
        this.setBitmap(rightCharacterBitmap);
    }

    public void moveLeft(){
        this.setPositionX(this.getPositionX() - moveAmount);
        this.setDirectionRight(false);
        this.setBitmap(leftCharacterBitmap);
    }


    public void jump(){
        float startY = positionY;
        rise(startY);
        fall(startY);
    }

//    public boolean collides(Rect hit) {
//        if (Rect.intersects(this.r,hit)==true) {
//            System.out.println("COLLISION");
//            return true;
//        } else {
//            return false;
//        }
//    }

    public void fall(float baseHeight){
        this.falling = true;
        long startTimeMillis = System.currentTimeMillis();
        long currentTimeMillis = startTimeMillis;
        long futureTimeMillis = currentTimeMillis + 35;
        while( positionY < baseHeight){
            if(futureTimeMillis < System.currentTimeMillis()){
                positionY += getSpeedGravity(startTimeMillis);
                currentTimeMillis = System.currentTimeMillis();
                futureTimeMillis = currentTimeMillis + 35;
            }

        }
        this.setJumped(false);
        this.falling = false;
        this.setPositionY(baseHeight);
    }

    public void rise(float baseHeight){
        this.rising = true;
        long startTimeMillis = System.currentTimeMillis();
        long currentTimeMillis = startTimeMillis;
        long futureTimeMillis = currentTimeMillis + 35;
        this.setJumped(true);
        while( positionY > baseHeight - jumpAmount){
            if(futureTimeMillis < System.currentTimeMillis()){
                positionY -= getSpeedGravity(startTimeMillis);
                currentTimeMillis = System.currentTimeMillis();
                futureTimeMillis = currentTimeMillis + 35;
            }

        }
        this.rising = false;

    }

    private float getSpeedGravity(long startTimeMillis) {
        long currentTimeMillis = System.currentTimeMillis();
        long timeDiff = currentTimeMillis - startTimeMillis;
        long scalingFactor = 70;
        long scaledTime = timeDiff / scalingFactor;
        float gravity = gamePractice.getGravity();
        float speed;
        if(rising){
            speed = Math.abs(intialJumpSpeed - gravity * (scaledTime));
            System.out.println(speed);
        }
        else{
            scaledTime = timeDiff / 300;
            speed = 0 + gravity*(scaledTime);

        }
        return speed;
    }

    public void onDraw(Canvas canvas){
        float top = gamePractice.getSurfaceViewBitMapSRCRect().top;
        float bottom = gamePractice.getSurfaceViewBitMapSRCRect().bottom;
        float left = gamePractice.getSurfaceViewBitMapSRCRect().left;
        float right = gamePractice.getSurfaceViewBitMapSRCRect().right;

        if(this.getRect().intersect(gamePractice.getSurfaceViewBitMapSRCRect())){
            canvas.drawBitmap(this.characterBitmap, this.getPositionX(), this.getPositionY(), null);
        }
    }

    public Rect getR() {
        System.out.println("getR in MainCharacter");
        return this.r;
    }
    public Rect getRect() {
        Rect characterRect = new Rect();
        characterRect.left = (int) (this.getPositionX() - (1/2)*characterBitmap.getWidth());
        characterRect.right = (int) (this.getPositionX() + (1/2)*characterBitmap.getWidth());
        characterRect.top = (int) this.getPositionY() - (1/2) * characterBitmap.getHeight();
        characterRect.bottom = (int) this.getPositionY() + (1/2) * characterBitmap.getHeight();
        return characterRect;


    }

    public void shoot(BulletArrayList bulletArrayList){
        if(!(this.getAmmo() == 0)) {
            Rect characterRect = new Rect(this.getRect());
            float rightX = characterRect.right;
            float centerY = characterRect.exactCenterY();
            Bullet bullet = new Bullet(rightX, centerY, this.gamePractice);
            Bitmap bulletBitmap = bullet.getBitmap();
            float bulletBitmapWidth = bulletBitmap.getWidth();
            if (directionRight) {
                bullet.setPositionX(bullet.getPositionX() +  bulletBitmapWidth);
            } else {
                bullet.setPositionX(bullet.getPositionX() - bulletBitmapWidth);
            }
            bulletArrayList.add(bullet);
            this.ammo -= 1;
        }
    }

    public float getPositionX() { return positionX; }

    public void setPositionX(float positionX) { this.positionX = positionX; }

    public float getPositionY() { return positionY; }

    public void setPositionY(float positionY) {
        this.positionY = positionY; }

    public Bitmap getBitmap() {
        return characterBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.characterBitmap = bitmap;
    }

    public void setDirectionRight(boolean directionRight) {
        this.directionRight = directionRight;
    }

    public boolean isDirectionRight() {
        return directionRight;
    }

    public boolean getJumped(){
        return this.jumped;
    }

    public void setJumped(boolean jumped) {
        this.jumped = jumped;
    }

    public int getAmmo() {
        return ammo;
    }
}

