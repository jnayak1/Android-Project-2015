package jdn4ae.cs2110.virginia.edu.gamepractice;


import android.graphics.BitmapFactory;

public class Ghost extends Creature{

    private int size;
    private static float distancePerMove = 20; // all ghosts move same amount per move so static
    private MainCharacter mainCharacter;

    protected Ghost(float positionX, float positionY,
                    GamePractice gamePractice, int size) {

        super(positionX, positionY, gamePractice);
        this.size = size; // max size 8, won't get any bigger after that
        this.mainCharacter = gamePractice.getMainCharacter();


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8 / size; // max size 8, won't get any bigger after that
        this.bitmap = BitmapFactory.decodeResource(gamePractice.getResources(),
                R.drawable.ghost, options);
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

        // get the slope of line
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
