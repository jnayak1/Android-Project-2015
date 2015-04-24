package jdn4ae.cs2110.virginia.edu.gamepractice;

/**
 * Created by pnayak1 on 4/24/15.
 */
public class MoonGravity extends Item {

    MainCharacter mainCharacter;
    GamePractice gamePractice;

    public MoonGravity(MainCharacter mainCharacter, GamePractice gamePractice) {
        this.gamePractice = gamePractice;
        this.mainCharacter = mainCharacter;
    }

    @Override
    public void use() {
        mainCharacter.setJumpAmount(220);
    }
}
