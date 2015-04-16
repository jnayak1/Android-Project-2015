package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by pnayak1 on 4/16/15.
 */
public class BulletArrayList extends ArrayList<Bullet> {
    public BulletArrayList(Collection<? extends Bullet> collection) {
        super(collection);
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
