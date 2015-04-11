package jdn4ae.cs2110.virginia.edu.gamepractice;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.FloatMath;

public class Ghost extends Creature{

    private int size;
    private static float distancePerMove = 20; // all ghosts move same amount per move so static
    private MainCharacter mainCharacter;

    protected Ghost(float positionX, float positionY,
                    GamePractice gamePractice, int size) {

        super(positionX, positionY, gamePractice);
        this.size = size;
        this.mainCharacter = gamePractice.getMainCharacter();
        
    }

    @Override
    public void move() {
        float futureX = this.seekX();
        float futureY = this.seekY();

        this.setPositionX(futureX);
        this.setPositionY(futureY);
    }

    public float seekX(){
        // get position of MainCharacter (positionX, positionY)
        float mainCharacterX = mainCharacter.getPositionX();
        float mainCharacterY = mainCharacter.getPositionY();

        // return the slope of line
        float m = (this.positionY - mainCharacterY) / (this.positionX - mainCharacterX);

        float futureX = mainCharacter.getPositionX();
        float changeX = (this.getDistancePerMove()) / (float) Math.sqrt(m*m + 1);

        // ghost could be right or left of mainCharacter
        if(this.getPositionX() < mainCharacterX){
            futureX += changeX;
        }
        else{
            futureX -= changeX;
        }

        return futureX;
    }


    public float seekY(){
        // get position of MainCharacter (positionX, positionY)
        float mainCharacterY = mainCharacter.getPositionY();
        float mainCharacterX = mainCharacter.getPositionX();
        // get the slope of line
        float m = (this.positionY - mainCharacterY) / (this.positionX - mainCharacterX);

        float futureY = this.positionY;
        float changeY = (this.getDistancePerMove() * m) / (float) Math.sqrt(m * m + 1);

        // ghost could be right or left of mainCharacter
        if(this.getPositionY() < mainCharacterY){
            futureY += changeY;
        }
        else{
            futureY -= changeY;
        }

        return futureY;
    }



    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float getDistancePerMove() {
        return distancePerMove;
    }

    public static void setDistancePerMove(float distancePerMove) {
        Ghost.distancePerMove = distancePerMove;
    }
}
