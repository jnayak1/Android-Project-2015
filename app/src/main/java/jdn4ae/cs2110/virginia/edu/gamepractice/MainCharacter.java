package jdn4ae.cs2110.virginia.edu.gamepractice;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.lang.System;


public class MainCharacter {

    private int moveAmount;
    private GamePractice gamePractice;
    private Bitmap characterBitmap;
    private ArrayList items;
    private float positionX, positionY;
    private static float jumpAmount = 120;


    public MainCharacter(float positionX, float positionY, GamePractice gamePractice) {
        characterBitmap = BitmapFactory.decodeResource(gamePractice.getResources(), R.drawable.character);
        this.positionX = positionX - (1/2)*characterBitmap.getWidth();
        this.positionY = positionY - (1/2)*characterBitmap.getHeight();
        this.gamePractice = gamePractice;
        items = new ArrayList<Item>();
        moveAmount = gamePractice.getMoveAmount();
    }




    public void moveRight() {
        this.setPositionX(this.getPositionX() + moveAmount);
    }

    public void moveLeft(){
        this.setPositionX(this.getPositionX() - moveAmount);
    }


    public void jump(){
        float startY = positionY;
        positionY -= jumpAmount;
        fall(startY);


    }

    public void fall(float baseHeight){
        while( positionY < baseHeight){
            int n = 1;
            if(System.currentTimeMillis()%1000 == 0){
                positionY += 5;
                System.out.println("fall call");

            }

        }
    }

    public void onDraw(Canvas canvas){
        float top = gamePractice.getSurfaceViewBitMapSRCRect().top;
        float bottom = gamePractice.getSurfaceViewBitMapSRCRect().bottom;
        float left = gamePractice.getSurfaceViewBitMapSRCRect().left;
        float right = gamePractice.getSurfaceViewBitMapSRCRect().right;

        if(((this.positionX < right) && (this.positionX > left)) &&
                ((this.positionY < bottom) && (this.positionY > top))) {
            canvas.drawBitmap(this.characterBitmap, this.getPositionX(), this.getPositionY(), null);
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
        this.characterBitmap = characterBitmap;
    }

}

