package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.GameWorld;
import pvz.view.Messages;

public class Sunflower extends Plant {

	public static final int FREQUENCY = 3;

	public static final int MAX_LIFE = 1;

	public static final int SUN_GEN = 10;

	public static final int COST = 20;

	private int freqCount;

	public Sunflower(Position pos, GameWorld game) {
		super(game, pos, MAX_LIFE);
		freqCount = -1;
	}

	public void update() {
		if (!isAlive()) {
			return;
		}
		freqCount++;
		if (freqCount >= FREQUENCY) {
			freqCount = 0;
			game.generateSunCoins(SUN_GEN);
		}
	}

	@Override
	public int getCost() {
		return COST;
	}

	@Override
	public String getDescription() {
		return Messages.SUNFLOWER_DESCRIPTION.formatted(COST, 0, MAX_LIFE);
	}

	@Override
	protected String getSymbol() {
	    return Messages.SUNFLOWER_SYMBOL;
	}

	@Override
	public String getName() {
		return Messages.SUNFLOWER_NAME;
	}

	@Override
	public String getShortcut() {
		return Messages.SUNFLOWER_SYMBOL;
	}

	@Override
	public Plant spawn(Position pos, GameWorld game) {
		return new Sunflower(pos, game);
	}
}
