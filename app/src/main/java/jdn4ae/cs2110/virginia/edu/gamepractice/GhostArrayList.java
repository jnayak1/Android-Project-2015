package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by pnayak1 on 4/15/15.
 */
public class GhostArrayList extends ArrayList<Ghost> {
    private GamePractice gamePractice;

    public GhostArrayList(Collection<? extends Ghost> collection, GamePractice gamePractice) {
        super(collection);
        this.gamePractice = gamePractice;
    }

    public void update(){
        for(Ghost ghost : this){
            ghost.move();
        }
    }

    public void onDraw(Canvas canvas) {
        update();
        for(Ghost ghost : this){
            ghost.onDraw(canvas);
        }
    }
}
