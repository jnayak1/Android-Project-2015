package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class MysteryBox {
    private static float itemBuffer = 20;
    private MainCharacter maincharacter;
    float positionX,positionY;
    GamePractice gamePractice;
    private Bitmap bitmap;
    private Rect hitbox;
    private long startTime;


    public MysteryBox(MainCharacter maincharacter, float X, float Y, GamePractice gamePractice) {
        this.maincharacter = maincharacter;
        this.positionX = X;
        this.positionY = Y;
        this.gamePractice = gamePractice;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 32;
        this.bitmap = BitmapFactory.decodeResource(gamePractice.getResources(),
                R.drawable.item,options);
        this.hitbox = new Rect ((int)this.positionX, (int)this.positionY,
                (int)this.positionX + this.bitmap.getWidth(), (int)this.positionY + this.bitmap.getHeight());
    }

    public void add(MysteryBox mysteryBox) {}

    public void removeFromMC() {
        maincharacter.getItems().remove(this);
    }

    public void removeFromGP(){
        MysteryBoxArrayList items = gamePractice.getMysteryBoxes();
        items.remove(this);
    }

    public boolean hasItem() {
        return maincharacter.getItems().contains(this);
    }

    public static void autoGenerate(MysteryBoxArrayList mysteryBoxArrayList, GamePractice gamePractice){
        MainCharacter mainCharacter = gamePractice.getMainCharacter();
        float mainCharacterX = mainCharacter.getPositionX();
        float mainCharacterY = mainCharacter.getPositionY();
        float changeDistanceX = (float)( 2*Math.random() - 1) * 150;
        float changeDistanceY = (float)( Math.random()) * 150;
        float randomX = mainCharacterX + changeDistanceX + itemBuffer;
        float randomY = (mainCharacterY + (changeDistanceY * (-1))) - itemBuffer;
        MysteryBox mysteryBox = new MysteryBox(mainCharacter, randomX, randomY, gamePractice);
        mysteryBoxArrayList.add(mysteryBox);
    }

    public void onDraw(Canvas canvas) {
        update();
        canvas.drawBitmap(bitmap,positionX,positionY,null);
    }

    private void update() {


    }

    public Rect getRect() {
        return hitbox;
    }
}
