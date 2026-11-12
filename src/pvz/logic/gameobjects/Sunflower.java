package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.GameWorld;
import pvz.view.Messages;

public class Sunflower extends Plant {

	public static final int FREQUENCY = 3;

	public static final int MAX_LIFE = 1;

	public static final int COST = 20;

	private int freqCount;

	public Sunflower(Position pos, GameWorld game) {
		super(game, pos, MAX_LIFE);
		freqCount = -1;
	}

	@Override
	public void update() {
		if (!isAlive()) {
			return;
		}
		freqCount++;
		if (freqCount >= FREQUENCY) {
			game.generateSun();
			freqCount = 0;
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
	public String getSymbol() {
	    return Messages.SUNFLOWER_SYMBOL;
	}

	@Override
	public String getName() {
		return Messages.SUNFLOWER_NAME;
	}

	@Override
	public Plant spawn(Position pos, GameWorld game) {
		return new Sunflower(pos, game);
	}
}
