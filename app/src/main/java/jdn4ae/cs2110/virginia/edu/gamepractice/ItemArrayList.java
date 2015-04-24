
    package jdn4ae.cs2110.virginia.edu.gamepractice;
    import android.graphics.Canvas;
    import java.util.ArrayList;
    import java.util.Collection;

public class ItemArrayList extends ArrayList<MysteryBox>{
    private GamePractice gamePractice;
    private static int autoGenCounter = 0;

    public ItemArrayList(Collection<? extends MysteryBox> collection, GamePractice gamePractice) {
        super(collection);
        this.gamePractice = gamePractice;
    }

    public void update(){
        if(autoGenCounter > 50){
            MysteryBox.autoGenerate(this, gamePractice);
            autoGenCounter = 0;
        }
        autoGenCounter++;
    }

    public void onDraw(Canvas canvas) {
        this.update();
        for(MysteryBox mysteryBox : this){
            mysteryBox.onDraw(canvas);
        }
    }

    public void setAutoGenCounter(int autoGenCounter) {
        ItemArrayList.autoGenCounter = autoGenCounter;
    }
}