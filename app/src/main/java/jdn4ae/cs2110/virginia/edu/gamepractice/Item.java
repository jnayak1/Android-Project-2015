package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.graphics.Canvas;

public class Item {
    private MainCharacter maincharacter;
    float X,Y;
    GamePractice gamePractice;

    public Item(MainCharacter maincharacter,float X, float Y, GamePractice gamePractice) {
        this.maincharacter = maincharacter;
        this.X = X;
        this.Y = Y;
        this.gamePractice = gamePractice;
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
        float randomX = mainCharacterX + changeDistanceX;
        float randomY = mainCharacterY + changeDistanceY;
        Item item = new Item(mainCharacter, randomX, randomY, gamePractice);
        itemArrayList.add(item);
    }

    public void onDraw(Canvas canvas) {

    }
}
