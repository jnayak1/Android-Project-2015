package jdn4ae.cs2110.virginia.edu.gamepractice;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Ghost {

    private int size;
    private static float distancePerMove = 5; // all ghosts move same amount per move so static
    private MainCharacter mainCharacter;
    private Bitmap ghostBitmap;
    private float positionX, positionY;
    private GamePractice gamePractice;
    public final static int MAX_SIZE = 8;
    private static int autoGenRadius = 200;
    private Rect r;


    protected Ghost(float positionX, float positionY,
                    GamePractice gamePractice, int size) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = MAX_SIZE / size; // max size 8, won't get any bigger after that
        this.ghostBitmap = BitmapFactory.decodeResource(gamePractice.getResources(),
                R.drawable.ghost, options);
        this.positionX = positionX - (1/2)*ghostBitmap.getWidth();
        this.positionY = positionY - (1/2)*ghostBitmap.getHeight();
        this.gamePractice = gamePractice;
        this.size = size; // max size 8, won't get any bigger after that
        this.mainCharacter = gamePractice.getMainCharacter();
        this.r = new Rect((int)this.positionX, (int)this.positionY,
                (int) this.positionX + this.ghostBitmap.getWidth(),(int)this.positionY + this.getGhostBitmap().getHeight());
    }

    public float xDiff(){
        float mainCharacterX = mainCharacter.getPositionX();
        return this.getPositionX() - mainCharacterX;
    }
    public float yDiff(){
        float mainCharacterY = mainCharacter.getPositionY();
        return this.getPositionY() - mainCharacterY;
    }
    public float getSlope(){
        float xDiff = this.xDiff();
        float yDiff = this.yDiff();

        return yDiff / xDiff;
    }
    public float getTheta(){
        float slope = this.getSlope();


        return (float) Math.atan(slope);
    }
    public float changeX(){
        float theta = this.getTheta();
        float changeX = distancePerMove * ((float) Math.cos(theta));
        if(this.xDiff() > 0){
            changeX = changeX * (-1);
        }
        return changeX;
    }
    public float changeY(){
        float theta = this.getTheta();
        float changeY = distancePerMove * ((float) Math.sin(theta));
        if(this.xDiff() > 0){
            changeY = changeY * (-1);
        }
        return changeY;
    }
    public float futureX(){
        return this.getPositionX() + this.changeX();
    }
    public float futureY(){
        return this.getPositionY() + this.changeY();
    }

    public void move(){
        float futureX = this.futureX();
        float futureY = this.futureY();
        this.setPositionX(futureX);
        this.setPositionY(futureY);
    }


    public void onDraw(Canvas canvas){
        if(this.getRect().intersect(gamePractice.getSurfaceViewBitMapSRCRect())){
            canvas.drawBitmap(this.getGhostBitmap(), this.getPositionX(),
                    this.getPositionY(), null);
        }
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


    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public Bitmap getGhostBitmap() {
        return ghostBitmap;
    }

    public void setGhostBitmap(Bitmap ghostBitmap) {
        this.ghostBitmap = ghostBitmap;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public Rect getRect(){
        Rect ghostRect = new Rect();
        ghostRect.left = (int) (this.getPositionX() - (1/2)*ghostBitmap.getWidth());
        ghostRect.right = (int) (this.getPositionX() + (1/2)*ghostBitmap.getWidth());
        ghostRect.top = (int) this.getPositionY() - (1/2) * ghostBitmap.getHeight();
        ghostRect.bottom = (int) this.getPositionY() + (1/2) * ghostBitmap.getHeight();

        return ghostRect;
    }
    public Rect getR() {
        System.out.println("getR in ghost");
        return this.r;
    }

    public static boolean ghostCollision(Ghost a, Ghost b){
        return a.getRect().intersect(b.getRect());
    }

    @Override
    public boolean equals(Object o) {
        boolean value = false;
        if(o instanceof Ghost){
            Ghost ghostO = (Ghost) o;
            value = this.getRect().intersect(ghostO.getRect());
        }
        return value;
    }

    public static void autoGenerate(GhostArrayList ghostArrayList, GamePractice gamePractice){
        MainCharacter mainCharacter = gamePractice.getMainCharacter();
        float mainCharacterX = mainCharacter.getPositionX();
        float mainCharacterY = mainCharacter.getPositionY();
        float changeDistanceX = (float)( 2*Math.random() - 1) * autoGenRadius;
        float changeDistanceY = (float)( Math.random()) * autoGenRadius;
        float ghostX = mainCharacterX + changeDistanceX;
        float ghostY = mainCharacterY + changeDistanceY;
        Ghost ghost = new Ghost(ghostX,ghostY,gamePractice,1);
        ghostArrayList.add(ghost);
        System.out.println("ghost generated");
    }
}
