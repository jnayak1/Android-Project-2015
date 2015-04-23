package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    private int autoGenCounter;
    private final int autoGenTime = 30;
    private MainCharacter mainCharacter;
    private BulletArrayList bullets;

    public GhostArrayList(Collection<? extends Ghost> collection, GamePractice gamePractice) {
        super(collection);
        this.gamePractice = gamePractice;
        autoGenCounter = 0;
        mainCharacter = gamePractice.getMainCharacter();
        bullets = gamePractice.getBullets();

    }

    public void update(){
        if(autoGenCounter > autoGenTime){
            Ghost.autoGenerate(this,gamePractice);
            autoGenCounter = 0;
        }
        for(Ghost ghost : this){
            ghost.move();
            if(this.collided(ghost)){
                System.out.println("collision with ghost");
            }
            if(mainCharacter.ghostCollision(ghost)){
                System.out.println("collision with main character");
            }
        }
        autoGenCounter++;
    }

    public void onDraw(Canvas canvas) {
        update();
        for(Ghost ghost : this){
            ghost.onDraw(canvas);
        }
    }

    public boolean collided(Ghost ghost){
        return (Collections.frequency(this,ghost) > 1);
    }
}
