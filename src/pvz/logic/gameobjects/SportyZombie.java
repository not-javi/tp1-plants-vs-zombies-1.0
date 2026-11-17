package pvz.logic.gameobjects;

import pvz.logic.GameWorld;
import pvz.view.Messages;
import utils.Position;

public class SportyZombie extends Zombie {
	
	private static final int MAX_LIFE = 2;

	private static final int DAMAGE = 1;

	private static final int WALK_FREQUENCY = 1;

	public SportyZombie(Position pos, GameWorld game) {
		super(game, pos, MAX_LIFE, WALK_FREQUENCY);
	}

	@Override
	public String getDescription() {
		return Messages.ZOMBIE_DESCRIPTION.formatted(getName(), WALK_FREQUENCY, DAMAGE,
				MAX_LIFE);
	}

	@Override
	public String getSymbol() {
	    return Messages.SPORTY_ZOMBIE_SYMBOL;
	}

	@Override
	public String getName() {
		return Messages.SPORTY_ZOMBIE_NAME;
	}

	@Override
	public Zombie copy(Position pos, GameWorld game) {
		return new SportyZombie(pos, game);
	}
	
	@Override 
	public int getDamage() {
		return DAMAGE;
	}
}
