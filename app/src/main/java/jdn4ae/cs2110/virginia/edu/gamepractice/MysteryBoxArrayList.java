
    package jdn4ae.cs2110.virginia.edu.gamepractice;
    import android.graphics.Canvas;
    import android.graphics.Rect;

    import java.util.ArrayList;
    import java.util.Collection;
    import java.util.Iterator;

    public class MysteryBoxArrayList extends ArrayList<MysteryBox>{
    private GamePractice gamePractice;
    private static int autoGenCounter = 0;

    public MysteryBoxArrayList(Collection<? extends MysteryBox> collection, GamePractice gamePractice) {
        super(collection);
        this.gamePractice = gamePractice;
    }

    public void update(){
        if(autoGenCounter > 50){
            MysteryBox.autoGenerate(this, gamePractice);
            autoGenCounter = 0;
        }
        Iterator<MysteryBox> iterator = this.iterator();

        while(iterator.hasNext()){
            MysteryBox mysteryBox = iterator.next();
            if(Rect.intersects(gamePractice.getMainCharacter().getRect(), mysteryBox.getRect())){
                gamePractice.getMainCharacter().addItem();
                iterator.remove();
            }
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
        MysteryBoxArrayList.autoGenCounter = autoGenCounter;
    }
}