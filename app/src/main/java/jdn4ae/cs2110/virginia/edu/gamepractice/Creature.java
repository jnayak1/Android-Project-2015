package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.graphics.Bitmap;

/**
 * Created by pnayak1 on 4/10/15.
 */
public abstract class Creature {

    Bitmap bitmap;
    float positionX, positionY;

    protected Creature(Bitmap bitmap, float positionX, float positionY) {
        this.bitmap = bitmap;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public abstract void move();

}
