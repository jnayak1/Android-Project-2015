package jdn4ae.cs2110.virginia.edu.gamepractice;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

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
        this.gamePractice = gamePractice;
        items = new ArrayList<Item>();
        moveAmount = gamePractice.getMoveAmount();
        directionRight = true;

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
        positionY -= jumpAmount;
        fall(startY);

    }

    public void fall(float baseHeight){
        while( positionY < baseHeight){
            if(System.currentTimeMillis()%600 == 0){
                positionY += 10;

            }

        }
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

    public Rect getRect() {
        Rect characterRect = new Rect();
        characterRect.left = (int) (this.getPositionX() - (1/2)*characterBitmap.getWidth());
        characterRect.right = (int) (this.getPositionX() + (1/2)*characterBitmap.getWidth());
        characterRect.top = (int) this.getPositionY() - (1/2) * characterBitmap.getHeight();
        characterRect.bottom = (int) this.getPositionY() + (1/2) * characterBitmap.getHeight();
        return characterRect;
    }

    public void shoot(BulletArrayList bulletArrayList){
        Rect characterRect = new Rect(this.getRect());
        float rightX = characterRect.right;
        float centerY = characterRect.exactCenterY();
        Bullet bullet = new Bullet(rightX,centerY,this.gamePractice);
        Bitmap bulletBitmap = bullet.getBitmap();
        float bulletBitmapWidth = bulletBitmap.getWidth();
        if(directionRight){
            bullet.setPositionX(bullet.getPositionX() + (7/4) * bulletBitmapWidth);
        }
        else{
            bullet.setPositionX(bullet.getPositionX() - (3/4) * bulletBitmapWidth);
        }
        bulletArrayList.add(bullet);
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
}

