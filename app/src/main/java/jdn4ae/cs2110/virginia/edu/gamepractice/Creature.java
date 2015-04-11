package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.graphics.Bitmap;

/**
 * Created by pnayak1 on 4/10/15.
 */
public abstract class Creature {

    protected Bitmap bitmap;
    protected float positionX, positionY; // the center of the creature bitmap on the map bitmap
    protected GamePractice gamePractice;

    protected Creature(float positionX, float positionY, GamePractice gamePractice) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.gamePractice = gamePractice;
    }

    public abstract void move();

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }
}
