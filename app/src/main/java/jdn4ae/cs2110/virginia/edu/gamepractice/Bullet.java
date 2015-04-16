package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;


public class Bullet {
    private float positionX, positionY;
    private Bitmap bitmap;
    private Bitmap bitmapRight;
    private Bitmap bitmapLeft;
    private GamePractice gamePractice;
    private static float bulletMoveAmount = 20;
    private static int size = 4;
    private boolean directionRight;

    public Bullet(float positionX, float positionY, GamePractice gamePractice) {
        this.gamePractice = gamePractice;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = size;
        options.inMutable = true;
        this.bitmap = BitmapFactory.decodeResource(gamePractice.getResources(),R.drawable.bullet,
                options);

        bitmapRight = bitmap.copy(Bitmap.Config.ARGB_8888,false);

        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        bitmapLeft = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, false);

        this.positionX = positionX - (1/2) * this.bitmap.getWidth();
        this.positionY = positionY - (1/2) * this.bitmap.getHeight();
        this.directionRight = gamePractice.getMainCharacter().isDirectionRight();
        if(!directionRight){
            bitmap = bitmapLeft;
        }
    }


    public void onDraw(Canvas canvas){
        canvas.drawBitmap(bitmap,this.positionX,this.positionY,null);
    }


    public void move(){
        float futureX = this.getFutureX();
        this.setPositionX(futureX);
    }

    private float getFutureX() {
        float futureX = 0;

        if(this.directionRight){
            futureX = this.getPositionX() + bulletMoveAmount;
        }
        else {
            futureX = this.getPositionX() - bulletMoveAmount;
        }
        return futureX;
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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
