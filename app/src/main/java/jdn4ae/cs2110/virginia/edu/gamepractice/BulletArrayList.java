package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by pnayak1 on 4/16/15.
 */
public class BulletArrayList extends ArrayList<Bullet> {
    private GamePractice gamePractice;
    private MainCharacter mainCharacter;
    private Set<Rect> bulletRects;

    public BulletArrayList(Collection<? extends Bullet> collection, GamePractice gamePractice) {
        super(collection);
        this.gamePractice = gamePractice;
        this.mainCharacter = gamePractice.getMainCharacter();
        bulletRects = new HashSet<Rect>();
    }

    public void update(){
        for(Bullet bullet : this){
            bullet.move();
        }
    }

    public void onDraw(Canvas canvas) {
        this.update();
        for(Bullet bullet : this){
            bullet.onDraw(canvas);
        }
    }


}
