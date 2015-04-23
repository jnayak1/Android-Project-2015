package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Item {
    private static float itemBuffer = 20;
    private MainCharacter maincharacter;
    float positionX,positionY;
    GamePractice gamePractice;
    private Bitmap bitmap;
    private Rect hitbox;

    public Item(MainCharacter maincharacter,float X, float Y, GamePractice gamePractice) {
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

    public void add(Item item) {}

    public void removeFromMC() {
        maincharacter.getItems().remove(this);
    }

    public void removeFromGP(){
        ItemArrayList items = gamePractice.getItems();
        items.remove(this);
    }

    public boolean hasItem() {
        return maincharacter.getItems().contains(this);
    }

    public static void autoGenerate(ItemArrayList itemArrayList, GamePractice gamePractice){
        MainCharacter mainCharacter = gamePractice.getMainCharacter();
        float mainCharacterX = mainCharacter.getPositionX();
        float mainCharacterY = mainCharacter.getPositionY();
        float changeDistanceX = (float)( 2*Math.random() - 1) * 150;
        float changeDistanceY = (float)( Math.random()) * 150;
        float randomX = mainCharacterX + changeDistanceX + itemBuffer;
        float randomY = (mainCharacterY + (changeDistanceY * (-1))) - itemBuffer;
        Item item = new Item(mainCharacter, randomX, randomY, gamePractice);
        itemArrayList.add(item);
    }

    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap,positionX,positionY,null);
    }
}
