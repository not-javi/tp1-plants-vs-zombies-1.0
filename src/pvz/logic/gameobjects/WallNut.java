package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.GameWorld;
import pvz.view.Messages;



public class WallNut extends Plant {

	public static final int MAX_LIFE = 10;

	public static final int COST = 50;

	public WallNut(Position pos, GameWorld game) {
		super(game, pos, MAX_LIFE);
	}

	@Override
	public void update() {}

	@Override
	public int getCost() {
		return COST;
	}

	@Override
	public String getDescription() {
		return Messages.WALL_NUT_DESCRIPTION.formatted(COST, 0, MAX_LIFE);
	}

	@Override
	public String getSymbol() {
		return Messages.WALL_NUT_SYMBOL;
	}

	@Override
	public String getName() {
		return Messages.WALL_NUT_NAME;
	}

	@Override
	public Plant copy(Position pos, GameWorld game) {
		return new WallNut(pos, game);
	}
	
	@Override 
	public int getDamage() {
		return 0;
	}
}
