package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;


public class Bullet {
    private float positionX, positionY;
    private Bitmap bitmap;
    private GamePractice gamePractice;
    private static float bulletMoveAmount = 5;
    private static int size = 4;

    public Bullet(float positionX, float positionY, GamePractice gamePractice) {
        this.gamePractice = gamePractice;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = size;
        this.bitmap = BitmapFactory.decodeResource(gamePractice.getResources(),R.drawable.bullet,
                options);
        this.positionX = positionX - (1/2) * this.bitmap.getWidth();
        this.positionY = positionY - (1/2) * this.bitmap.getWidth();
    }


    public void onDraw(Canvas canvas){
        canvas.drawBitmap(bitmap,this.positionX,this.positionY,null);
    }


    public void move(){
        float futureX = this.getFutureX();
        this.setPositionX(futureX);
    }

    private float getFutureX() {
        return this.getPositionX() + bulletMoveAmount;
    }


    public Rect getRect(){
        Rect bulletRect = new Rect();
        int left = (int) this.positionX - (1/2) * this.bitmap.getWidth();
        int right = (int) this.positionX + (1/2) * this.bitmap.getWidth();
        int top = (int) this.positionY - (1/2) * this.bitmap.getHeight();
        int bottom = (int) this.positionY + (1/2) * this.bitmap.getHeight();

        bulletRect.set(left,top,right,bottom);
        return bulletRect;
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
