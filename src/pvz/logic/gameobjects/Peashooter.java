package pvz.logic.gameobjects;

import utils.Position;
import pvz.logic.GameWorld;
import pvz.view.Messages;

public class Peashooter extends Plant {

	public static final int MAX_LIFE = 3;

	public static final int FREQUENCY = 1;

	public static final int DAMAGE = 1;

	public static final int COST = 50;

	private int freqCount;

	public Peashooter(Position pos, GameWorld game) {
		super(game, pos, MAX_LIFE);
		freqCount = 0;
	}

	// We set freqCount at -1 so peashooters cant shoot the instant they are planted

	@Override
	public void update() {
		if (!isAlive()) {
			return;
		}
		freqCount++;
		if (freqCount >= FREQUENCY) {
			freqCount = 0;
			Position aux = pos;
			while (game.isInsideBoard(aux) && !game.plantAttack(aux, DAMAGE)) {
			    aux = aux.right();
			}
		}
	}

	@Override
	public int getCost() {
		return COST;
	}

	@Override
	public String getDescription() {
		return Messages.PEASHOOTER_DESCRIPTION.formatted(COST, DAMAGE, MAX_LIFE);
	}

	@Override
	public String getSymbol() {
		return Messages.PEASHOOTER_SYMBOL; // "P"
	}

	@Override
	public String getName() {
		return Messages.PEASHOOTER_NAME;
	}

	@Override
	public Plant copy(Position pos, GameWorld game) {
		return new Peashooter(pos, game);
	}
	
	@Override 
	public int getDamage() {
		return DAMAGE;
	}
}
