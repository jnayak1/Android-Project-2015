package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by DWillis on 4/10/2015.
 */
public class Bomb extends Item {
        MainCharacter mainCharacter;
        GamePractice gamePractice;
        Bitmap bitmap;
        float positionY, positionX;
        long startTime;
        long endTime;

    public Bomb(MainCharacter maincharacter, GamePractice gamePractice) {
        this.mainCharacter = maincharacter;
        this.gamePractice = gamePractice;
        BitmapFactory.Options options = new BitmapFactory.Options();
        this.bitmap = BitmapFactory.decodeResource(gamePractice.getResources(),R.drawable.bomb,options);
        if(maincharacter.isDirectionRight()){
            this.positionX = maincharacter.getPositionX() + 50;
            this.positionY= maincharacter.getPositionY() - 10;
        }
        else{
            this.positionX= maincharacter.getPositionX() - 50;
            this.positionY= maincharacter.getPositionY() - 10;
        }
        this.startTime = System.currentTimeMillis();
        this.endTime = startTime + 2000;

    }
    public void onDraw(Canvas canvas){
        canvas.drawBitmap(bitmap,positionX,positionY,null);
    }

    public Rect getRect(){
        Rect rect = new Rect();
        rect.left = (int) this.positionX;
        rect.right = (int) this.positionX + this.bitmap.getWidth();
        rect.top = (int) this.positionY;
        rect.bottom = (int) this.positionY + this.bitmap.getHeight();

        return rect;
    }

    @Override
    public void use() {

    }
}
