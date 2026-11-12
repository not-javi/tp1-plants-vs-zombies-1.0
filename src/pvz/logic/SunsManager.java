package pvz.logic;

public class SunsManager {

	private GameWorld game;
	private int cooldown;
	private static final int COOLDOWN = 5;

	public SunsManager(GameWorld game) {
		this.game = game;
		cooldown = COOLDOWN;
	}

	public void update() {
		cooldown--;
		if (cooldown == 0) {
			game.generateSun();
			cooldown = COOLDOWN;
		}
	}
}
