
    package jdn4ae.cs2110.virginia.edu.gamepractice;
    import android.graphics.Canvas;
    import java.util.ArrayList;
    import java.util.Collection;

public class ItemArrayList extends ArrayList<Item>{
    private GamePractice gamePractice;
    private static int autoGenCounter = 0;

    public ItemArrayList(Collection<? extends Item> collection, GamePractice gamePractice) {
        super(collection);
        this.gamePractice = gamePractice;
    }

    public void update(){
        if(autoGenCounter == 4){
            Item.autoGenerate(this,gamePractice);
            this.setAutoGenCounter(-1);
        }
        this.setAutoGenCounter(++autoGenCounter);
    }

    public void onDraw(Canvas canvas) {
        this.update();
        for(Item item : this){
            item.onDraw(canvas);
        }
    }

    public void setAutoGenCounter(int autoGenCounter) {
        ItemArrayList.autoGenCounter = autoGenCounter;
    }
}