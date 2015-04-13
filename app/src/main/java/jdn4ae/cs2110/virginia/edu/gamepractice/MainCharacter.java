package jdn4ae.cs2110.virginia.edu.gamepractice;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;


public class MainCharacter extends Creature {

    private static final int MOVESPEED = 50;
    private float positionX = 100;
    private float positionY = 400;
    private boolean jumped = false;

    private GamePractice gamePractice;
    final int JUMPSPEED = -175;


    protected Bitmap character;
    protected ArrayList items;

    protected MainCharacter(float positionX, float positionY, GamePractice gamePractice) {
        super(positionX, positionY, gamePractice);


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        character = BitmapFactory.decodeResource(gamePractice.getResources(), R.drawable.character);
        items = new ArrayList();
    }

    protected void onDraw(Canvas canvas) {

        if (positionX < canvas.getWidth()) {
            positionX += 50;
        }
        else {
            setPositionX(100);
        }
        if (positionY < canvas.getHeight()) {
            positionY = getPositionY();
        }
        else {
            setPositionY(400);
        }

        canvas.drawBitmap(character, positionX, positionY, new Paint());
    }


    public void move() {

        if (positionX >= 4900){
            setPositionX(10);
        } else {
            positionX = getPositionX();
        }


        if (positionY >= 3000) {
            setPositionY(400);
        } else {
            positionY = getPositionY();
        }
    }


    public void pickUpItem(Item item) {
        items.add(item);
    }

    public void useItem(Item item) {
        items.remove(item);
    }

    public void shoot() {

    }

    public void jump() {
        if (!jumped) {
            positionX += MOVESPEED;
            positionY += JUMPSPEED;
            jumped = true;
        }

    }

    public void gravity () {
        if (jumped) {
            positionX += MOVESPEED;
            positionY = 100;
            jumped = false;
        }
    }

    public float getPositionX() { return positionX; }

    public void setPositionX(float positionX) { this.positionX = positionX; }

    public float getPositionY() { return positionY; }

    public void setPositionY(float positionY) { this.positionY = positionY; }
}

