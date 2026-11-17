package pvz.logic.gameobjects;

import java.util.List;

import pvz.logic.GameWorld;
import pvz.logic.actions.ExplosionAction;
import pvz.view.Messages;
import utils.Position;

public class ExplosiveZombie extends Zombie {
	
	private static final int MAX_LIFE = 5;

	private static final int DAMAGE = 1;
	
	private static final int EXPLOSIVE_DAMAGE = 3;

	private static final int WALK_FREQUENCY = 2;


	public ExplosiveZombie(Position pos, GameWorld game) {
		super(game, pos, MAX_LIFE, WALK_FREQUENCY);
	}

	@Override
	public String getDescription() {
		return Messages.ZOMBIE_DESCRIPTION.formatted(getName(), WALK_FREQUENCY, DAMAGE,
				MAX_LIFE);
	}

	@Override
	public String getSymbol() {
	    return Messages.EXPLOSIVE_ZOMBIE_SYMBOL;
	}

	@Override
	public String getName() {
		return Messages.EXPLOSIVE_ZOMBIE_NAME;
	}

	@Override
	public Zombie copy(Position pos, GameWorld game) {
		return new ExplosiveZombie(pos, game);
	}
	
	@Override 
	public int getDamage() {
		return DAMAGE;
	}

	@Override
	public void onExit() {
		super.onExit();
		List<Position> positions = this.pos.areaRad1();
		game.pushAction(ExplosionAction.zombieExplosion(EXPLOSIVE_DAMAGE, positions));
	}
}
