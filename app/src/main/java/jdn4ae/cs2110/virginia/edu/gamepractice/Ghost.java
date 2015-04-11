package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.graphics.Bitmap;

/**
 * Created by McKallen on 4/10/2015.
 */
public class Ghost extends Creature{

    int size;
    Bitmap bitmap;
    float positionX, positionY;

    protected Ghost(Bitmap bitmap, float positionX, float positionY, int size) {
        super(bitmap, positionX, positionY);
        this.size = size;
    }


    @Override
    public void move() {

    }

    public void seek(){

    }
}
