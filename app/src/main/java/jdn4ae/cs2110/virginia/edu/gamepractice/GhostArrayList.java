package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.content.Intent;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

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
        bullets = gamePractice.getBullets();

        Iterator<Ghost> iterator = this.iterator();

        while(iterator.hasNext()){
            Ghost ghost = iterator.next();
            ghost.move();
            if(this.collided(ghost)){
                System.out.println("collision with ghost");

            }
            if(mainCharacter.collided(ghost)){
                System.out.println("collision with main character");
                Intent intent = new Intent(gamePractice,EndGame.class);

                gamePractice.startActivity(intent);
            }
            if(ghost.collided(gamePractice.getBullets())){
                System.out.println("collision with bullets");
                iterator.remove();
            }
        }
        autoGenCounter++;
    }

    public void onDraw(Canvas canvas) {
        update();
        Iterator<Ghost> iterator = this.iterator();
        while(iterator.hasNext()){
            Ghost ghost = iterator.next();
            ghost.onDraw(canvas);
        }
    }

    public boolean collided(Ghost ghost){
        return (Collections.frequency(this,ghost) > 1);
    }
}
