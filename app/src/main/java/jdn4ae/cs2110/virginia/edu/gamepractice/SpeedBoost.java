package jdn4ae.cs2110.virginia.edu.gamepractice;

/**
 * Created by pnayak1 on 4/26/15.
 */
public class SpeedBoost extends Item {

    private GamePractice gamePractice;

    public SpeedBoost(GamePractice gamePractice) {
        this.gamePractice = gamePractice;
    }

    @Override
    public void use() {
        gamePractice.setSpeedBoostTimer(100);
        gamePractice.getMainCharacter().setMoveAmount(20);
        gamePractice.setMoveAmount(20);
    }
}
