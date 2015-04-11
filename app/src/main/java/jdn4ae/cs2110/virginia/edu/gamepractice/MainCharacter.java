package jdn4ae.cs2110.virginia.edu.gamepractice;


import android.graphics.Bitmap;

public class MainCharacter extends Creature {
    private int positionX = 100;
    private int positionY = 450;
    private boolean jumped = false;
    private GamePractice gamePractice;
    private int speedX = 0;
    private int speedY = 1;

    protected MainCharacter(Bitmap bitmap, float positionX, float positionY, GamePractice gamePractice) {
        super(bitmap, positionX, positionY, gamePractice);
    }

    public void update() {

        // Moves Character or Scrolls Background accordingly.
        if (speedX < 0) {
            positionX += speedX;
        } else if (speedX == 0) {
            System.out.println("Don't scroll background.");
        } else {
            if (positionX <= 160) {
                positionX += speedX;
            } else {
                System.out.println("Scroll Background ");
            }
        }

        // Updates Y Position

        if (positionY + speedY >= 450) {
            positionY = 450;
        } else {
            positionY += speedY;
        }

        // Handles Jumping
        if (jumped) {
            speedY += 1;

            if (positionY + speedY >= 450) {
                positionY = 450;
                speedY = 0;
                jumped = false;
            }

        }

        // Prevents going beyond X coordinate of 0
        if (positionX + speedX <= 65) {
            positionX = 66;
        }
    }


    public void moveRight() {
        speedX = 4;
    }

    public void moveLeft() {
        speedX = -4;
    }


    public void stop() {
        speedX = 0;
    }

    public void jump() {
        if (!jumped) {
            speedY = -10;
            jumped = true;
        }

    }

    @Override
    public void move() {

    }
}
//
//    public int getPositionX() {
//        return positionX;
//    }
//
//    public void setPositionX(int positionX) {
//        this.positionX = positionX;
//    }
//
//    public int getPositionY() {
//        return positionY;
//    }
//
//    public void setPositionY(int positionY) {
//        this.positionY = positionY;
//    }
//
//    public boolean isJumped() {
//        return jumped;
//    }
//
//    public void setJumped(boolean jumped) {
//        this.jumped = jumped;
//    }
//
//    public int getSpeedX() {
//        return speedX;
//    }
//
//    public void setSpeedX(int speedX) {
//        this.speedX = speedX;
//    }
//
//    public int getSpeedY() {
//        return speedY;
//    }
//
//    public void setSpeedY(int speedY) {
//        this.speedY = speedY;
//    }
//}
//